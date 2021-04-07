package com.example.demo;


import com.example.demo.service.CompanyService;
import com.example.demo.service.CompanyZyService;
import com.example.demo.service.StockZyService;
import com.example.demo.utils.MyChineseWorkDay;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TaskService {
    @Autowired
    CompanyService companyService;
    @Autowired
    StockZyService stockZyService;
    @Autowired
    CompanyZyService companyZyService;
    private static boolean turn =true;
    private static int days= 7;
    private static long dayTime=86400000L;
    //@Scheduled(cron = "10 35 11,13,14 ? * MON-FRI")
    @Scheduled(cron = "0/8 * * * * ? ")
    public void deal(){
        if(turn){
            realyDo(days);
        }
    }
    public void realyDo(int day){
        days=day;
        Date date = new Date();
        long time = date.getTime()-days*dayTime;
        date.setTime(time);
        int comInt = companyService.resetFP(date);
        int zyInt = stockZyService.resetFP(date);
        int comZyInt = companyZyService.resetFP(date);
        System.out.println("处理【"+DateFormatUtils.format(date, "yyyyMMdd HH:mm:ss")+","+days+"天前】日期之前的数据-----------人才数据条数"+zyInt+"，公司（手机）条数:"+comInt+",公司（名字）条数:"+comZyInt);
    }
}
