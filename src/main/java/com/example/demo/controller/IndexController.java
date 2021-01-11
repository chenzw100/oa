package com.example.demo.controller;

import com.example.demo.service.StockZyService;
import com.example.demo.service.UserService;
import com.example.demo.utils.WebContent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {
    public Log log = LogFactory.getLog(IndexController.class);
    @Autowired
    StockZyService stockZyService;
    @Autowired
    UserService userService;
    @RequestMapping("/welcome.html")
    public String index(ModelMap modelMap){
        modelMap.put("userName",WebContent.getUserName());
        return "welcome";
    }


}
