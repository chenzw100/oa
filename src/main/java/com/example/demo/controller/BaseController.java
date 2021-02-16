package com.example.demo.controller;


import com.example.demo.utils.WebContent;
import org.springframework.ui.ModelMap;

public class BaseController {
    protected void loginUser(ModelMap modelMap) {
        modelMap.put("userId", WebContent.getUserId());
        modelMap.put("role", WebContent.getUserInfoLevel());
        modelMap.put("loginName", WebContent.getUserName());
    }
}
