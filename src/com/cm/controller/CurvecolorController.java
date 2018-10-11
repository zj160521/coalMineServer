package com.cm.controller;

import com.alibaba.fastjson.JSONObject;
import com.cm.entity.CurveColor;
import com.cm.security.LoginManage;
import com.cm.service.CurvecolorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@Scope
@RequestMapping("/curve")
public class CurvecolorController {

    @Autowired
    private LoginManage loginManage;

    @Autowired
    private ResultObj result;

    @Autowired
    private CurvecolorService service;

    @RequestMapping(value = "/addup", method = RequestMethod.POST)
    @ResponseBody
    public Object addup(@RequestBody CurveColor color, HttpServletRequest request){
        if(!loginManage.isUserLogin(request)){
            return result.setStatus(-1,"no login");
        }
        try{
            if(color.getId()>0){
                service.update(color);
                String remark = JSONObject.toJSONString(color);
                String operation2 = "修改曲线颜色";
                loginManage.addLog(request, remark, operation2, 168);
            } else {
                service.add(color);
                String remark = JSONObject.toJSONString(color);
                String operation2 = "增加曲线颜色";
                loginManage.addLog(request, remark, operation2, 168);
            }
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4,"exception");
        }
        return result.setStatus(0,"ok");
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Object getAll(HttpServletRequest request){
        if(!loginManage.isUserLogin(request)){
            return result.setStatus(-1,"no login");
        }
        try{
            CurveColor color = service.getAll();
            result.put("data",color);
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4,"exception");
        }
        return result.setStatus(0,"ok");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable int id,HttpServletRequest request){
        if(!loginManage.isUserLogin(request)){
            return result.setStatus(-1,"no login");
        }
        try{
            service.delete(id);
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4,"exception");
        }
        return result.setStatus(0,"ok");
    }
}
