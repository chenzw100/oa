package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.domain.table.CompanyZy;
import com.example.demo.domain.table.User;
import com.example.demo.exception.NormalException;
import com.example.demo.service.CompanyZyService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/companyzy")
public class CompanyZyController {
    public Log log = LogFactory.getLog(CompanyZyController.class);
    @Autowired
    CompanyZyService stockZyService;
    @Autowired
    UserService userService;
    @RequestMapping("/list.html")
    public String index(ModelMap modelMap){
        modelMap.put("userId",WebContent.getUserId());
        return "companyzy/list";
    }
    @RequestMapping("/rw.html")
    public String rw(){
        return "companyzy/rwlist";
    }
    @RequestMapping("/lq.html")
    public String lq(){
        return "companyzy/rwlqist";
    }
    @RequestMapping("/geren.html")
    public String geren(){
        return "companyzy/geren";
    }

    @RequestMapping("/list.action")
    @ResponseBody
    public String list(Integer page, Integer rows, CompanyZy stockZy){
        String userInfoLevel = WebContent.getUserInfoLevel();
        if(!"管理员".equals(userInfoLevel)){
            stockZy.setOptId(WebContent.getUserId());
        }
        Page<CompanyZy> list =stockZyService.findALl(page,rows,stockZy);
        Map map = new HashMap<>();
        map.put("total",list.getTotalElements());
        map.put("rows",list.getContent());

        return JSON.toJSONString(map);
    }
    @RequestMapping("exportExcel.action")
    public void exportExcel(HttpServletResponse response,CompanyZy stockZy) throws NormalException {

        String userInfoLevel = WebContent.getUserInfoLevel();
        if(!"管理员".equals(userInfoLevel)){
            stockZy.setOptId(WebContent.getUserId());
        }
        List<CompanyZy> export =stockZyService.findExport(stockZy);

        //导出操作
        FileExcelUtil.exportExcel(export,"名单","资质公司数据",CompanyZy.class,"资质公司数据.xls",response);
    }
    @RequestMapping("/fenpei.action")
    @ResponseBody
    public String fenpei(Integer page,Integer rows,CompanyZy stockZy){
        String userInfoLevel = WebContent.getUserInfoLevel();
        if(!"管理员".equals(userInfoLevel)){
            stockZy.setOptId(WebContent.getUserId());
        }
        if(stockZy.getFen() == null){
            stockZy.setFen("是");
        }
        Page<CompanyZy> list =stockZyService.fenpeiList(page,rows,stockZy);
        Map map = new HashMap<>();
        map.put("total",list.getTotalElements());
        map.put("rows",list.getContent());
        return JSON.toJSONString(map);
    }
    @RequestMapping(value = "/update.action",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String update(CompanyZy stockZy){
        stockZy.setModified(new Date());
        if(StringUtils.isNotEmpty(stockZy.getCustomerWx())){
            if(stockZy.getCustomerWx().equals("是")){
                stockZy.setCustomerZf("否");
            }
        }
        if(StringUtils.isNotEmpty(stockZy.getCustomerYx())){
            if(stockZy.getCustomerYx().equals("是")){
                stockZy.setCustomerZf("否");
            }
        }
        stockZyService.saveOrUpdate(stockZy);
        return "success";
    }
    @RequestMapping("/updates.action")
    @ResponseBody
    public Map updates(Long[] ids, Long userId){


        User u = userService.getById(userId);
        stockZyService.fenPei(userId,u.getName(),ids);
        Map map = new HashMap();
        map.put("200","success");
        return map;
    }
    @RequestMapping("/updatesByLq.action")
    @ResponseBody
    public Map updatesByLq(String[] ids){
        for(String id :ids){
            CompanyZy db =stockZyService.getById(Long.parseLong(id));
            if(db.getOptId()!=null){
                continue;
            }
            db.setOptId(WebContent.getUserId());
            db.setOptName(WebContent.getUserName());
            db.setFenDate(new Date());
            stockZyService.saveOrUpdate(db);
        }
        Map map = new HashMap();
        map.put("200","success");
        return map;
    }
    @RequestMapping(value = "/destroy.action")
    @ResponseBody
    public String destroy(CompanyZy stockZy){
        stockZy.setModified(new Date());
        stockZyService.delete(stockZy);
        return "success";
    }

    @ResponseBody
    @RequestMapping("importExcel.action")
    public String importExcel(MultipartFile file) throws NormalException {
        List<CompanyZy> personList = FileExcelUtil.importExcel(file, CompanyZy.class);
        System.out.println("导入数据一共【"+personList.size()+"】行");
        int i =0;
        for(CompanyZy stockZy :personList){
            i++;
            System.out.println(i+"《===============第，导入数据的电话【"+stockZy.getName()+"】");
            if(StringUtils.isEmpty(stockZy.getName())){
                System.out.println(i+"《===============第，导入数据的电话【"+stockZy.getName()+"】，没有公司名称信息");
            }
            stockZyService.saveOrUpdate(stockZy);

        }
        return "导入数据一共【"+personList.size()+"】行";
    }
    @RequestMapping("/import")
    public String export(ModelMap modelMap)  {
        modelMap.put("userId", WebContent.getUserId());
        return "companyzy/export";
    }
}
