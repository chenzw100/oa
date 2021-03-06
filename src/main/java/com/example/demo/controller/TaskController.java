package com.example.demo.controller;

import com.example.demo.TaskService;
import com.example.demo.domain.excel.Person;
import com.example.demo.domain.table.StockZy;
import com.example.demo.exception.NormalException;
import com.example.demo.service.StockZyService;
import com.example.demo.service.ZjskpwService;
import com.example.demo.utils.FileExcelUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class TaskController extends BaseController{
    public Log log = LogFactory.getLog(TaskController.class);
    @Autowired
    TaskService taskService;
    @Autowired
    ZjskpwService zjskpwService;
    private static Map<String, String> cookies= new HashMap<>();

    public static Map<String, String> getCookies(String cookiesStr) {
        if(cookies.size()!=0){
            return cookies;
        }
        String parameStr[] = cookiesStr.split("; ");
        Map<String, String> cookies = new HashMap<>();
        for(String coo : parameStr){
            String parame[] =coo.split("=");
            cookies.put(parame[0],parame[1]);
        }
        return cookies;
    }
    @RequestMapping("/exe.action")
    public String index(ModelMap modelMap){
        loginUser(modelMap);
        return "exe";
    }
    @ResponseBody
    @RequestMapping("exet.action")
    public String exet(String co,int start,int end) {
        log.info(start+","+end+",设置，"+co);
        for(int i=start;i<end+1;i++){
            log.info("-----------------------进行页面处理page = [" + i + "]");
            boolean result = zjskpwService.infoPages(i,co);
            if(!result){
                return "处理第"+i+"页异常";
            }
            log.info("-------------------------------------------------------------------new--------完成page = [" + i + "]");
        }
        return "处理"+end+"页完成";
    }
    @ResponseBody
    @RequestMapping("realyDo.action")
    public String realyDo(int day) {
        log.info("设置，"+day+"天以前执行一下数据重新分配");
        taskService.realyDo(day);
        return "已设置:"+day+"天，重启服务后需要重新设置需要的天数";
    }

}
