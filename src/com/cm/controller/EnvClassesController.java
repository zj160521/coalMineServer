package com.cm.controller;

import com.alibaba.fastjson.JSONObject;
import com.cm.entity.EnvClasses;
import com.cm.security.LoginManage;
import com.cm.service.EnvClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/envclasses")
public class EnvClassesController {

    @Autowired
    private EnvClassesService classesService;

    @Autowired
    private LoginManage loginManage;

    @Autowired
    private ResultObj result;

    @RequestMapping(value = "/addup", method = RequestMethod.POST)
    @ResponseBody
    public Object addup(@RequestBody EnvClasses[] envClasses, HttpServletRequest request){
        if (!loginManage.isUserLogin(request)){
            return result.setStatus(-1, "no login");
        }
        try{
            List<EnvClasses> all = classesService.getAll();
            if (!all.isEmpty()) {
                return result.setStatus(-4, "配置新的班次需将之前的班次清除");
            }
            List<EnvClasses> list = new ArrayList<>();
            for (int i = 0; i < envClasses.length; i++) {
                list.add(envClasses[i]);
            }
            if (null != list && !list.isEmpty()) {
                EnvClasses first = list.get(0);
                EnvClasses last = list.get(list.size() - 1);
                if (!first.getStart().equals(last.getEnd())) {
                    EnvClasses classes = new EnvClasses();
                    classes.setStart(last.getEnd());
                    classes.setEnd(first.getStart());
                    classes.setName("未配置班次");
                    classes.setStatus(2);
                    list.add(classes);
                }
                for (EnvClasses classes : list) {
                    if (classes.getStatus()!=2) {
                        classes.setStatus(1);
                    }
                }
                classesService.batchadd(list);
            } else {
                return result.setStatus(-4, "请配置正确的班次");
            }
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4,"exception");
        }
        return result.setStatus(0, "ok");
    }

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    @ResponseBody
    public Object getAll(HttpServletRequest request) {
        if (!loginManage.isUserLogin(request)){
            return result.setStatus(-1, "no login");
        }
        try{
            List<EnvClasses> all = classesService.getAll();
            result.put("data", all);
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4, "exception");
        }
        return result.setStatus(0, "ok");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(HttpServletRequest request){
        if (!loginManage.isUserLogin(request)){
            return result.setStatus(-1, "no login");
        }
        try{
            List<EnvClasses> list = classesService.getAll();
            String remark = JSONObject.toJSONString(list);
            String operation2 = "删除班次配置";
            loginManage.addLog(request, remark, operation2, 1520);
            classesService.delete();
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4,"exception");
        }
        return result.setStatus(0, "ok");
    }

}
