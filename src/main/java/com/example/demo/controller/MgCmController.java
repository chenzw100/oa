package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.domain.table.Company;
import com.example.demo.domain.table.User;
import com.example.demo.exception.NormalException;
import com.example.demo.service.CompanyService;
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
@RequestMapping("/mgcm")
public class MgCmController extends BaseController{
    public Log log = LogFactory.getLog(MgCmController.class);
    @Autowired
    CompanyService companyService;
    @Autowired
    UserService userService;
    @RequestMapping("/mglist.html")
    public String index(ModelMap modelMap){
        loginUser(modelMap);
        return "mgcm/mglist";
    }
    @RequestMapping("/rw.html")
    public String rw(ModelMap modelMap){
        loginUser(modelMap);
        return "mgcm/rwlist";
    }
    @RequestMapping("/lq.html")
    public String lq(ModelMap modelMap){
        loginUser(modelMap);
        return "mgcm/rwlqist";
    }
    @RequestMapping("/geren.html")
    public String geren(ModelMap modelMap){
        loginUser(modelMap);
        return "mgcm/geren";
    }

    @RequestMapping("/list.action")
    @ResponseBody
    public String list(Integer page, Integer rows, Company Company){
        String userInfoLevel = WebContent.getUserInfoLevel();
        if(!"管理员".equals(userInfoLevel)){
            Company.setOptId(WebContent.getUserId());
        }
        Page<Company> list =companyService.findALl(page,rows,Company);
        Map map = new HashMap<>();
        map.put("total",list.getTotalElements());
        map.put("rows",list.getContent());

        return JSON.toJSONString(map);
    }
    @RequestMapping("exportExcel.action")
    public void exportExcel(HttpServletResponse response,Company Company) throws NormalException {

        String userInfoLevel = WebContent.getUserInfoLevel();
        if(!"管理员".equals(userInfoLevel)){
            Company.setOptId(WebContent.getUserId());
        }
        List<Company> export =companyService.findExport(Company);

        //导出操作
        FileExcelUtil.exportExcel(export,"名单","人才数据",Company.class,"人才数据.xls",response);
    }
    @RequestMapping("/fenpei.action")
    @ResponseBody
    public String fenpei(Integer page,Integer rows,Company Company){
        String userInfoLevel = WebContent.getUserInfoLevel();
        if(!"管理员".equals(userInfoLevel)){
            Company.setOptId(WebContent.getUserId());
        }
        if(Company.getFen() == null){
            Company.setFen("是");
        }
        Page<Company> list =companyService.fenpeiList(page,rows,Company);
        Map map = new HashMap<>();
        map.put("total",list.getTotalElements());
        map.put("rows",list.getContent());
        return JSON.toJSONString(map);
    }
    @RequestMapping(value = "/update.action",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String update(Company Company){
        Company.setModified(new Date());
        if(StringUtils.isNotEmpty(Company.getCustomerWx())){
            if(Company.getCustomerWx().equals("是")){
                Company.setCustomerZf("否");
            }
        }
        if(StringUtils.isNotEmpty(Company.getCustomerYx())){
            if(Company.getCustomerYx().equals("是")){
                Company.setCustomerZf("否");
            }
        }
        companyService.saveOrUpdate(Company);
        return "success";
    }
    @RequestMapping("/updates.action")
    @ResponseBody
    public Map updates(Long[] ids, Long userId){

        /*for(String id :ids){
            Company db =companyService.getById(Long.parseLong(id));
            db.setOptId(userId);
            User u = userService.getById(userId);
            db.setOptName(u.getName());
            db.setFenDate(new Date());
            companyService.saveOrUpdate(db);
        }*/
        User u = userService.getById(userId);
        companyService.fenPei(userId,u.getName(),ids);
        Map map = new HashMap();
        map.put("200","success");
        return map;
    }
    @RequestMapping("/updatesByLq.action")
    @ResponseBody
    public Map updatesByLq(String[] ids){
        for(String id :ids){
            Company db =companyService.getById(Long.parseLong(id));
            if(db.getOptId()!=null){
                continue;
            }
            db.setOptId(WebContent.getUserId());
            db.setOptName(WebContent.getUserName());
            db.setFenDate(new Date());
            companyService.saveOrUpdate(db);
        }
        Map map = new HashMap();
        map.put("200","success");
        return map;
    }
    @RequestMapping(value = "/destroy.action")
    @ResponseBody
    public String destroy(Company Company){
        Company.setModified(new Date());
        companyService.delete(Company);
        return "success";
    }
    @RequestMapping("/importex.html")
    public String importex(ModelMap modelMap)  {
        loginUser(modelMap);
        return "mgcm/import";
    }
    @ResponseBody
    @RequestMapping("importExcel.action")
    public String importExcel(MultipartFile file) throws NormalException {
        List<Company> personList = FileExcelUtil.importExcel(file, Company.class);
        System.out.println("导入数据一共【"+personList.size()+"】行");
        int i =0;
        for(Company stockZy :personList){
            i++;
            System.out.println(i+"《===============第，导入数据的电话【"+stockZy.getPhone()+"】");
            if(StringUtils.isEmpty(stockZy.getName())){
                System.out.println(i+"《===============第，导入数据的电话【"+stockZy.getPhone()+"】，没有姓名信息");
            }
            Company stockZy1 = companyService.findByPhone(stockZy.getPhone());
            if(stockZy1==null){
                stockZy.setModified(new Date());
                stockZy.setCustomerZf("否");
                stockZy.setCalled("否");
                try {
                    companyService.saveOrUpdate(stockZy);
                }catch (Exception e){
                    log.error("失败，可能重复"+e.getMessage(),e);
                }
            }else {
                log.info("==========================【该公司手机好已经存在:"+stockZy.getPhone());
            }
        }
        //TODO 保存数据库
        return "导入数据一共【"+personList.size()+"】行,已存在的手机号未再次导入";
    }
    @ResponseBody
    @RequestMapping("repeatDelete.action")
    public String repeatDelete() {
        Integer count = companyService.repeatDelete();
        return "clean_count:"+count;
    }

}
