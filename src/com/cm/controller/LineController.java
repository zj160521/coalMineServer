package com.cm.controller;

import com.alibaba.fastjson.JSON;
import com.cm.entity.Line;
import com.cm.security.LoginManage;
import com.cm.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/line")
@Controller
@Scope
public class LineController {

    @Autowired
    private LoginManage loginManage;

    @Autowired
    private ResultObj result;

    @Autowired
    private LineService lineService;

    @RequestMapping(value = "/addup", method = RequestMethod.POST)
    @ResponseBody
    public Object addup(@RequestBody Line line, HttpServletRequest request){
        if(!loginManage.isUserLogin(request)){
            return result.setStatus(-1,"no login");
        }
        try{
            if(line.getId() > 0){
                if (line.getType() == 2) {
                    List<Object> list = line.getList();
                    String jsonString = JSON.toJSONString(list);
                    line.setLineString(jsonString);
                }
                lineService.update(line);
            } else {
                if (line.getType() == 2) {
                    List<Object> list = line.getList();
                    String jsonString = JSON.toJSONString(list);
                    line.setLineString(jsonString);
                }
                lineService.add(line);
            }
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4,"exception");
        }
        return result.setStatus(0,"ok");
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public Object getAll(@RequestBody Line line, HttpServletRequest request){
        if(!loginManage.isUserLogin(request)){
            return result.setStatus(-1,"no login");
        }
        try{
            List<Line> lines = lineService.getByType(line.getType());
            for (Line line1 : lines) {
                if (line1.getType() == 2){
                    String lineString = line1.getLineString();
                    List<Object> list = JSON.parseArray(lineString, Object.class);
                    line1.setList(list);
                }
            }
            result.put("data",lines);
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
            return result.setStatus(-1,"ok");
        }
        try{
            lineService.delete(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return result.setStatus(0,"ok");
    }
}
