package com.example.demo.controller;

import com.example.demo.domain.table.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.MD5Cipher;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * (1)发送post请求，代替了RequestMapping（value="/login", method="post"）
 * (2)跳转页面时使用  return ”redirect:/ dashboard“，而不是 return ”dashboard“ .
 *  return ”redirect:/ dashboard“，会直接调用controller，
 *  return ”dashboard“ 只会访问目录下的文件
 */

@Controller
public class LoginController {
    @RequestMapping("login")
    public String gologin() {
        return "login";
    }
    @Autowired
    UserService userService;
    @PostMapping(value = "/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Map<String, Object> map,
                        HttpSession session) {

        //验证用户名和密码，输入正确，跳转到dashboard
        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
            String pwd =MD5Cipher.string2MD5(password);

            User u=userService.findUserByNameAndPassword(username,pwd);
            if(u==null){
                session.invalidate();
                map.put("msg", "用户名密码错误");
                return "login";
            }
            session.setAttribute("userName", u.getName());
            System.out.println("----" + username);
            map.put("age", 30);
            return "redirect:/index";

        } else  //输入错误，清空session，提示用户名密码错误
        {
            session.invalidate();
            map.put("msg", "用户名密码错误");
            return "login";
        }
    }


    @RequestMapping("index")
    public String goMain(Map<String, Object> map) {
        map.put("name", "zhangfang");
        map.put("age", 28);
        map.put("sex", "女");
        return "index";

    }
}
