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
public class CompanyZyController extends BaseController{
    public Log log = LogFactory.getLog(CompanyZyController.class);
    @Autowired
    CompanyZyService companyZyService;
    @Autowired
    UserService userService;
    @RequestMapping("/list.html")
    public String index(ModelMap modelMap){
        loginUser(modelMap);
        return "companyzy/list";
    }
    @RequestMapping("/rw.html")
    public String rw(ModelMap modelMap){
        loginUser(modelMap);
        return "companyzy/rwlist";
    }
    @RequestMapping("/lq.html")
    public String lq(ModelMap modelMap){
        loginUser(modelMap);
        return "companyzy/rwlqist";
    }
    @RequestMapping("/geren.html")
    public String geren(ModelMap modelMap){
        loginUser(modelMap);
        return "companyzy/geren";
    }

    @RequestMapping("/list.action")
    @ResponseBody
    public String list(Integer page, Integer rows, CompanyZy stockZy){
        String userInfoLevel = WebContent.getUserInfoLevel();
        if(!"管理员".equals(userInfoLevel)){
            stockZy.setOptId(WebContent.getUserId());
        }
        Page<CompanyZy> list =companyZyService.findALl(page,rows,stockZy);
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
        List<CompanyZy> export =companyZyService.findExport(stockZy);

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
        Page<CompanyZy> list =companyZyService.fenpeiList(page,rows,stockZy);
        Map map = new HashMap<>();
        map.put("total",list.getTotalElements());
        map.put("rows",list.getContent());
        return JSON.toJSONString(map);
    }
    @RequestMapping(value = "/update.action",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String update(CompanyZy companyZy){
        companyZy.setModified(new Date());
        if(StringUtils.isNotEmpty(companyZy.getCustomerWx())){
            if(companyZy.getCustomerWx().equals("是")){
                companyZy.setCustomerZf("否");
            }
        }
        if(StringUtils.isNotEmpty(companyZy.getCustomerYx())){
            if(companyZy.getCustomerYx().equals("是")){
                companyZy.setCustomerZf("否");
            }
        }
        companyZyService.saveOrUpdate(companyZy);
        return "success";
    }
    @RequestMapping("/updates.action")
    @ResponseBody
    public Map updates(Long[] ids, Long userId){


        User u = userService.getById(userId);
        companyZyService.fenPei(userId,u.getName(),ids);
        Map map = new HashMap();
        map.put("200","success");
        return map;
    }
    @RequestMapping("/updatesByLq.action")
    @ResponseBody
    public Map updatesByLq(String[] ids){
        for(String id :ids){
            CompanyZy db =companyZyService.getById(Long.parseLong(id));
            if(db.getOptId()!=null){
                continue;
            }
            db.setOptId(WebContent.getUserId());
            db.setOptName(WebContent.getUserName());
            db.setFenDate(new Date());
            companyZyService.saveOrUpdate(db);
        }
        Map map = new HashMap();
        map.put("200","success");
        return map;
    }
    @RequestMapping(value = "/destroy.action")
    @ResponseBody
    public String destroy(CompanyZy stockZy){
        stockZy.setModified(new Date());
        companyZyService.delete(stockZy);
        return "success";
    }

    @ResponseBody
    @RequestMapping("importExcel.action")
    public String importExcel(MultipartFile file) throws NormalException {
        List<CompanyZy> personList = FileExcelUtil.importExcel(file, CompanyZy.class);
        System.out.println("导入数据一共【"+personList.size()+"】行");
        int i =0;
        for(CompanyZy companyZy :personList){
            i++;
            System.out.println(i+"《===============第，导入数据的公司【"+companyZy.getName()+"】");
            if(StringUtils.isEmpty(companyZy.getName())){
                System.out.println(i+"《----------第，导入数据的公司【"+companyZy.getName()+"】，没有公司名称信息");
            }else {
                try {
                    companyZyService.saveOrUpdate(companyZy);
                }catch (Exception e){
                    log.error("失败，可能重复"+e.getMessage(),e);
                }
                /*CompanyZy companyZy1 = companyZyService.findByName(companyZy.getName());
                if(companyZy1==null){
                    try {
                        companyZyService.saveOrUpdate(companyZy);
                    }catch (Exception e){
                        log.error("失败，可能重复"+e.getMessage(),e);
                    }
                }else {
                    log.info(i+"《===============第，导入数据的公司【"+companyZy.getName()+"】，已经存在了");
                }*/
            }

        }
        return "导入数据一共【"+personList.size()+"】行";
    }
    @RequestMapping("/import")
    public String export(ModelMap modelMap)  {
        loginUser(modelMap);
        return "companyzy/export";
    }
    @ResponseBody
    @RequestMapping("repeatDelete.action")
    public String repeatDelete() {
        Integer count = companyZyService.repeatDelete();
        return "clean_count:"+count;
    }
}
