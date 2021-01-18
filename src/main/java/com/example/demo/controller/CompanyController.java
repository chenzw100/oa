package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.domain.table.CompanyInfo;
import com.example.demo.domain.table.StockZy;
import com.example.demo.domain.table.User;
import com.example.demo.exception.NormalException;
import com.example.demo.service.CompanyInfoService;
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
@RequestMapping("/company")
public class CompanyController {
    public Log log = LogFactory.getLog(CompanyController.class);
    @Autowired
    CompanyInfoService companyInfoService;
    @Autowired
    UserService userService;
    @RequestMapping("/list.html")
    public String index(ModelMap modelMap){
        modelMap.put("userId",WebContent.getUserId());
        return "company/list";
    }

    @RequestMapping("/list.action")
    @ResponseBody
    public String list(Integer page, Integer rows, CompanyInfo companyInfo){
        Page<CompanyInfo> list =companyInfoService.findALl(page,rows,companyInfo);
        Map map = new HashMap<>();
        map.put("total",list.getTotalElements());
        map.put("rows",list.getContent());
        return JSON.toJSONString(map);
    }
    @RequestMapping("exportExcel.action")
    public void exportExcel(HttpServletResponse response,CompanyInfo companyInfo) throws NormalException {
        List<CompanyInfo> export =companyInfoService.findExport(companyInfo);
        //导出操作
        FileExcelUtil.exportExcel(export,"名单","公司数据",CompanyInfo.class,"公司数据.xls",response);
    }

}
