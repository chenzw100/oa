package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.domain.table.StockZy;
import com.example.demo.domain.table.User;
import com.example.demo.exception.NormalException;
import com.example.demo.service.StockZyService;
import com.example.demo.service.UserService;
import com.example.demo.utils.FileExcelUtil;
import com.example.demo.utils.WebContent;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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
    @RequestMapping("/mglist.html")
    public String index(){
        return "mg/mglist";
    }

    @RequestMapping("/rw.html")
    public String rw(){
        return "mg/rwlist";
    }
    @RequestMapping("/geren.html")
    public String geren(){
        return "mg/geren";
    }

    @RequestMapping("/list.action")
    @ResponseBody
    public String list(Integer page, Integer rows, StockZy stockZy, ModelMap modelMap){
        String userInfoLevel = WebContent.getUserInfoLevel();
        if(!"管理员".equals(userInfoLevel)){
            stockZy.setOptId(WebContent.getUserId());
        }
        Page<StockZy> list =stockZyService.findALl(page,rows,stockZy);
        Map map = new HashMap<>();
        map.put("total",list.getTotalElements());
        map.put("rows",list.getContent());
        return JSON.toJSONString(map);
    }
    @RequestMapping("exportExcel.action")
    public void exportExcel(HttpServletResponse response,StockZy stockZy) throws NormalException {

        String userInfoLevel = WebContent.getUserInfoLevel();
        if(!"管理员".equals(userInfoLevel)){
            stockZy.setOptId(WebContent.getUserId());
        }
        List<StockZy> export =stockZyService.findExport(stockZy);

        //导出操作
        FileExcelUtil.exportExcel(export,"名单","人才数据",StockZy.class,"人才数据.xls",response);
    }
    @RequestMapping("/fenpei.action")
    @ResponseBody
    public String fenpei(Integer page,Integer rows,StockZy stockZy){
        String userInfoLevel = WebContent.getUserInfoLevel();
        if(!"管理员".equals(userInfoLevel)){
            stockZy.setOptId(WebContent.getUserId());
        }
        if(stockZy.getFen() == null){
            stockZy.setFen("是");
        }
        Page<StockZy> list =stockZyService.findAllAndOptIdIsNotNull(page,rows,stockZy);
        Map map = new HashMap<>();
        map.put("total",list.getTotalElements());
        map.put("rows",list.getContent());
        return JSON.toJSONString(map);
    }
    @RequestMapping(value = "/update.action",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String update(StockZy stockZy){
        stockZy.setModified(new Date());
        if(StringUtils.isNotEmpty(stockZy.getCustomerWx())){
            if(stockZy.getCustomerWx().equals("是")){
                stockZy.setCustomerZf(null);
            }
        }
        if(StringUtils.isNotEmpty(stockZy.getCustomerYx())){
            if(stockZy.getCustomerYx().equals("是")){
                stockZy.setCustomerZf(null);
            }
        }
        stockZyService.saveOrUpdate(stockZy);
        return "success";
    }
    @RequestMapping("/updates.action")
    @ResponseBody
    public Map updates(String[] ids, Long userId){

        for(String id :ids){
            StockZy db =stockZyService.getById(Long.parseLong(id));
            db.setOptId(userId);
            User u = userService.getById(userId);
            db.setOptName(u.getName());
            db.setFenDate(new Date());
            stockZyService.saveOrUpdate(db);
        }
        Map map = new HashMap();
        map.put("200","success");
        return map;
    }
    @RequestMapping(value = "/destroy.action")
    @ResponseBody
    public String destroy(StockZy stockZy){
        stockZy.setModified(new Date());
        stockZyService.delete(stockZy);
        return "success";
    }
}
