package com.example.demo.controller;

import com.example.demo.service.*;
import com.example.demo.utils.HttpClientUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JzsController {
    public Log log = LogFactory.getLog(JzsController.class);
    @Autowired
    KpwHrService kpwHrService;
    @Autowired
    KpwService kpwService;
    @Autowired
    Gk27Service gk27Service;
    @Autowired
    JZwService jZwService;
    @Autowired
    KcsjService kcsjService;
    @Autowired
    StockZyService stockZyService;
    @Autowired
    JzrcService jzrcService;
    @Autowired
    XgjService xgjService;

    @RequestMapping("/p/{page}.action")
    public String kpw(@PathVariable("page")Integer page) {
        for(int i=page;i<page+10;i++){
            log.info("--------------------------------------------------------------------------------------------page = [" + i + "]");
            xgjService.infoPages(i);
            log.info("-----------------------------------------------------------------------------------------------完成page = [" + i + "]");
        }
        return "add success";
    }
    @RequestMapping("/p2/{page}/{count}.action")
    public String kpw(@PathVariable("page")Integer page,@PathVariable("count")Integer count) {
        for(int i=page;i<count;i++){
            log.info("page = [" + i + "]");
            xgjService.infoPages(i);
            log.info("-------------------------------------------------------------------new--------完成page = [" + i + "]");
        }
        return "add success";
    }

    @RequestMapping("/test.action")
    public String test() {
            log.info("-------------------------------------------------------------------new--------完成page = [");
        xgjService.doUrl();
        return "add success";
    }


}
