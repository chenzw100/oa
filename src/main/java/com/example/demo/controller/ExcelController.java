package com.example.demo.controller;

import com.example.demo.domain.excel.Person;
import com.example.demo.domain.excel.Professional;
import com.example.demo.domain.table.StockZy;
import com.example.demo.exception.NormalException;
import com.example.demo.service.StockZyService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ExcelController extends BaseController{
    public Log log = LogFactory.getLog(ExcelController.class);
    @Autowired
    StockZyService stockZyService;
    @RequestMapping("export.action")
    public void export(HttpServletResponse response) throws NormalException {

        //模拟从数据库获取需要导出的数据
        List<Person> personList = new ArrayList<>();
        Person person1 = new Person("路飞","1",new Date());
        Person person2 = new Person("娜美","2", new Date());
        Person person3 = new Person("索隆","1", new Date());
        Person person4 = new Person("小狸猫","1", new Date());
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);

        //导出操作
        FileExcelUtil.exportExcel(personList,"花名册","草帽一伙",Person.class,"d:海贼王.xls",response);
    }
    @RequestMapping("exportExcel.action")
    public void exportExcel(HttpServletResponse response,StockZy stockZy) throws NormalException {

        //模拟从数据库获取需要导出的数据
        List<StockZy> export = stockZyService.findFirst10();
        //stockZy.setCustomerWx("是");
        //List<StockZy> export =stockZyService.findExport(stockZy);

        //导出操作
        FileExcelUtil.exportExcel(export,"名单","人才数据",StockZy.class,"人才数据.xls",response);
    }

    @RequestMapping("import")
    public String export(ModelMap modelMap)  {
        loginUser(modelMap);
        return "export";
    }

    @ResponseBody
    @RequestMapping("importExcel.action")
    public String importExcel(MultipartFile file) throws NormalException {
        //String filePath = "F:\\海贼王.xls";
        //解析excel，
        //List<StockZy> personList = FileExcelUtil.importExcel(filePath,1,1,StockZy.class);
        //也可以使用MultipartFile,使用
       //List<Person> personList = FileExcelUtil.importExcel(file, Person.class);
       List<StockZy> personList = FileExcelUtil.importExcel(file, StockZy.class);
        System.out.println("导入数据一共【"+personList.size()+"】行");
        int i =0;
        for(StockZy stockZy :personList){
            i++;
            System.out.println(i+"《===============第，导入数据的电话【"+stockZy.getPhone()+"】");
            if(StringUtils.isEmpty(stockZy.getName())){
                System.out.println(i+"《--------------------第，导入数据的电话【"+stockZy.getPhone()+"】，没有姓名信息");
            }
            stockZy.setModified(new Date());
            stockZy.setCustomerZf("否");
            stockZy.setCalled("否");
            try {
                stockZyService.saveOrUpdate(stockZy);
            }catch (Exception e){
                log.error("失败，可能重复"+e.getMessage(),e);
            }
            /*StockZy stockZy1 = stockZyService.findByPhone(stockZy.getPhone());
            if(stockZy1==null){
                stockZy.setModified(new Date());
                stockZy.setCustomerZf("否");
                stockZy.setCalled("否");
                try {
                    stockZyService.saveOrUpdate(stockZy);
                }catch (Exception e){
                    log.error("失败，可能重复"+e.getMessage(),e);
                }
            }else {
                log.info("==========================【该手机好已经存在:"+stockZy.getPhone());
            }*/
        }
        //TODO 保存数据库
        return "导入数据一共【"+personList.size()+"】行,已存在的手机号未再次导入";
    }
    @RequestMapping("importExcel2")
    public void importExcel2() throws NormalException {
        String filePath = "C:\\Users\\chenzhiwei\\Downloads\\d_海贼王.xls";
        //解析excel，
        List<Person> personList = FileExcelUtil.importExcel(filePath,1,1,Person.class);
        //也可以使用MultipartFile,使用 FileUtil.importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass)导入
        System.out.println("导入数据一共【"+personList.size()+"】行");


        //TODO 保存数据库
    }
    @ResponseBody
    @RequestMapping("repeatDelete.action")
    public String repeatDelete() {
        Integer count = stockZyService.repeatDelete();
        return "clean_count:"+count;
    }

}
