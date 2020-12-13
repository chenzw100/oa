package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.domain.table.StockZy;
import com.example.demo.domain.table.User;
import com.example.demo.service.StockZyService;
import com.example.demo.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mg")
public class MgController {
    public Log log = LogFactory.getLog(MgController.class);
    @Autowired
    StockZyService stockZyService;
    @Autowired
    UserService userService;
    @RequestMapping("/index.html")
    public String index(){
        return "mg/mglist";
    }

    @RequestMapping("/rw.html")
    public String rw(){
        return "mg/rwlist";
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
    @RequestMapping("/updates")
    @ResponseBody
    public Map updates(String[] ids, Long userId){
        /*StockZy db =stockZyService.getById(stockZy.getId());
        db.setCustomerWx(stockZy.getCustomerWx());
        db.setCustomerYx(stockZy.getCustomerYx());*/
        for(String id :ids){
            StockZy db =stockZyService.getById(Long.parseLong(id));
            db.setOptId(userId);
            User u = userService.getById(userId);
            db.setOptName(u.getName());
            stockZyService.saveOrUpdate(db);
        }
        Map map = new HashMap();
        map.put("200","success");
        return map;
    }

}
