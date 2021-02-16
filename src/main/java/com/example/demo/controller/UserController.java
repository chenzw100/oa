package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.domain.table.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.MD5Cipher;
import com.example.demo.utils.WebContent;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
    public Log log = LogFactory.getLog(UserController.class);
    @Autowired
    UserService userService;
    @RequestMapping("/list.html")
    public String index(ModelMap modelMap){
        loginUser(modelMap);
        return "user/list";
    }

    @RequestMapping("/ck.html")
    public String ck(){
        return "user/cklist";
    }
    @RequestMapping("/ckcm.html")
    public String ckcm(){
        return "user/ckcm";
    }

    @RequestMapping("/ckcompany.html")
    public String ckcompany(){
        return "user/ckcompany";
    }

    @RequestMapping("/password.html")
    public String password(ModelMap modelMap){
        loginUser(modelMap);
        return "user/password";
    }
    @RequestMapping("/gerenpwd.html")
    public String gerenpwd(ModelMap modelMap){
        loginUser(modelMap);
        return "user/gerenpwd";
    }

    @RequestMapping("/list.action")
    @ResponseBody
    public String list(Integer page, Integer rows, User user){
        if("".equals(user.getEnable())){
            user.setEnable(null);
        }
        if("".equals(user.getName())){
            user.setName(null);
        }
        Page<User> list =userService.findALl(page,rows,user);
        for(User u :list){
            u.setPassword(null);
        }
        Map map = new HashMap<>();
        map.put("total",list.getTotalElements());
        map.put("rows",list.getContent());
        return JSON.toJSONString(map);
    }
    @RequestMapping("/listpass.action")
    @ResponseBody
    public String listpass(User user){
        Map map = new HashMap<>();
        if(StringUtils.isBlank(user.getName())){
            map.put("total",0);
            map.put("rows",0);
            return JSON.toJSONString(map);
        }
        List<User> us =userService.findUserByName(user.getName());
        for(User u :us){
            u.setPassword(null);
        }
        map.put("total",us.size());
        map.put("rows",us);
        return JSON.toJSONString(map);
    }
    @RequestMapping("/listgeren.action")
    @ResponseBody
    public String listgeren(){
        Map map = new HashMap<>();
        List<User> us=userService.findUserById(WebContent.getUserId());
        for(User u :us){
            u.setPassword(null);
        }
        map.put("total",us.size());
        map.put("rows",us);
        return JSON.toJSONString(map);
    }
    @RequestMapping("/update.action")
    @ResponseBody
    public String update(User user){
        if(user.getId()==1){
            if(WebContent.getUserId().longValue()!=user.getId().longValue()){
                return "success";
            }
        }
        User userDb =userService.getById(user.getId());
        user.setPassword(userDb.getPassword());
        userService.saveOrUpdate(user);
        return "success";
    }
    @RequestMapping("/updatePwd.action")
    @ResponseBody
    public String updatePwd(User user){
        if(user.getId()==1){
            if(WebContent.getUserId().longValue()!=user.getId().longValue()){
                return "success";
            }
        }
        user.setPassword(MD5Cipher.string2MD5(user.getPassword()));
        userService.saveOrUpdate(user);
        return "success";
    }
    @RequestMapping("/save.action")
    @ResponseBody
    public String save(User user){
        if(user.getPassword()==null){
            user.setPassword("123456");
        }
        if(user.getInfoLevel()==null){
            user.setInfoLevel("员工");
        }
        user.setPassword(MD5Cipher.string2MD5(user.getPassword()));
        userService.saveOrUpdate(user);
        return "success";
    }

}
