package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.domain.table.StockZy;
import com.example.demo.service.StockZyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/mg")
public class MgController {
    public Log log = LogFactory.getLog(MgController.class);
    @Autowired
    StockZyService stockZyService;
    @RequestMapping("/index.html")
    public String index(){
        return "mg/mglist";
    }

    @RequestMapping("/list")
    @ResponseBody
    public String list(Integer page,Integer rows,StockZy stockZy){
        Page<StockZy> list =stockZyService.findALl(page,rows,stockZy);
        Map map = new HashMap<>();
        map.put("total",list.getTotalElements());
        map.put("rows",list.getContent());
        return JSON.toJSONString(map);
    }
    @RequestMapping("/update")
    @ResponseBody
    public String update(StockZy stockZy){
        /*StockZy db =stockZyService.getById(stockZy.getId());
        db.setCustomerWx(stockZy.getCustomerWx());
        db.setCustomerYx(stockZy.getCustomerYx());*/
        stockZyService.saveOrUpdate(stockZy);
        return "success";
    }

}
