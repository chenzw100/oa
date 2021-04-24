package com.example.demo.controller;

import com.example.demo.dao.CompanyInfoRepository;
import com.example.demo.domain.table.CompanyInfo;
import com.example.demo.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @Autowired
    JskService jskService;
    @Autowired
    JztService jztService;
    @Autowired
    CompanyInfoRepository companyInfoRepository;
    @Autowired
    GkService gkService;

    @RequestMapping("/p/{page}.action")
    public String kpw(@PathVariable("page")Integer page,String name,String param) {
        for(int i=page;i<page+10;i++){
            log.info("--------------------------------------------------------------------------------------------page = [" + i + "]");
            //jztService.infoPages(i,param,name);
            gkService.infoPages(i);
            log.info("-----------------------------------------------------------------------------------------------完成page = [" + i + "]");
        }
        return "add success";
    }
    @RequestMapping("/p2/{page}/{count}.action")
    public String kpw(@PathVariable("page")Integer page,@PathVariable("count")Integer count) {
        for(int i=page;i<count;i++){
            log.info("page = [" + i + "]");
            gkService.infoPages(i);
            log.info("-------------------------------------------------------------------new--------完成page = [" + i + "]");
        }
        return "add success";
    }

    @RequestMapping("/test.action")
    public String test() {
            log.info("-------------------------------------------------------------------new--------完成page = [");
        List<CompanyInfo> list = companyInfoRepository.findByName("七冶博盛建筑安装工程有限责任公司");
        List<CompanyInfo> list2 = companyInfoRepository.findByName("433");
        //jskService.doUrl();
        return "add success";
    }


}
