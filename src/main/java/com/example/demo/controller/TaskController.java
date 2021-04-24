package com.example.demo.controller;

import com.example.demo.TaskService;
import com.example.demo.domain.excel.Person;
import com.example.demo.domain.table.StockZy;
import com.example.demo.exception.NormalException;
import com.example.demo.service.StockZyService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TaskController extends BaseController{
    public Log log = LogFactory.getLog(TaskController.class);
    @Autowired
    TaskService taskService;
    @RequestMapping("/exe.action")
    public String index(ModelMap modelMap){
        loginUser(modelMap);
        return "exe";
    }
    @ResponseBody
    @RequestMapping("exet.action")
    public String exet(String co) {
        log.info("设置，"+co);

        return "已设置";
    }
    @ResponseBody
    @RequestMapping("realyDo.action")
    public String realyDo(int day) {
        log.info("设置，"+day+"天以前执行一下数据重新分配");
        taskService.realyDo(day);
        return "已设置:"+day+"天，重启服务后需要重新设置需要的天数";
    }

}
