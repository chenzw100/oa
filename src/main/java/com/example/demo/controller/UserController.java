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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    public Log log = LogFactory.getLog(UserController.class);
    @Autowired
    UserService userService;
    @RequestMapping("/index.html")
    public String index(){
        return "user/list";
    }

    @RequestMapping("/ck.html")
    public String ck(){
        return "user/cklist";
    }

    @RequestMapping("/password.html")
    public String password(){
        return "user/password";
    }
    @RequestMapping("/gerenpwd.html")
    public String gerenpwd(){
        return "user/gerenpwd";
    }

    @RequestMapping("/list")
    @ResponseBody
    public String list(Integer page, Integer rows, User user){
        Page<User> list =userService.findALl(page,rows,user);
        for(User u :list){
            u.setPassword(null);
        }
        Map map = new HashMap<>();
        map.put("total",list.getTotalElements());
        map.put("rows",list.getContent());
        return JSON.toJSONString(map);
    }
    @RequestMapping("/listpass")
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
    @RequestMapping("/listgeren")
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
    @RequestMapping("/update")
    @ResponseBody
    public String update(User user){
        /*StockZy db =stockZyService.getById(stockZy.getId());
        db.setCustomerWx(stockZy.getCustomerWx());
        db.setCustomerYx(stockZy.getCustomerYx());*/
        user.setPassword(MD5Cipher.string2MD5(user.getPassword()));
        userService.saveOrUpdate(user);
        return "success";
    }
    @RequestMapping("/save")
    @ResponseBody
    public String save(User user){
        /*StockZy db =stockZyService.getById(stockZy.getId());
        db.setCustomerWx(stockZy.getCustomerWx());
        db.setCustomerYx(stockZy.getCustomerYx());*/
        user.setPassword(MD5Cipher.string2MD5(user.getPassword()));
        userService.saveOrUpdate(user);
        return "success";
    }

}
