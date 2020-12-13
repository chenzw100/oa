package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.dao.StockZyRepository;
import com.example.demo.domain.table.StockZy;
import com.example.demo.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping("/p/{page}")
    public String kpw(@PathVariable("page")Integer page) {
        for(int i=page;i<page+10;i++){
            log.info("--------------------------------------------------------------------------------------------page = [" + i + "]");
            jzrcService.infoPages(i);
            log.info("-----------------------------------------------------------------------------------------------完成page = [" + i + "]");
        }
        return "add success";
    }
    @RequestMapping("/p2/{page}/{count}")
    public String kpw(@PathVariable("page")Integer page,@PathVariable("count")Integer count) {
        for(int i=page;i<count;i++){
            log.info("page = [" + i + "]");
            jzrcService.infoPages(i);
            log.info("-------------------------------------------------------------------new--------完成page = [" + i + "]");
        }
        return "add success";
    }


}
