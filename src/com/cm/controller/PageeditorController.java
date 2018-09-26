package com.cm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cm.entity.Pageeditor;
import com.cm.security.LoginManage;
import com.cm.service.PageeditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Scope
@Controller
@RequestMapping("/editor")
public class PageeditorController {

    @Autowired
    private LoginManage loginManage;

    @Autowired
    private ResultObj result;

    @Autowired
    private PageeditorService pageeditorService;

    @RequestMapping(value = "/addup", method = RequestMethod.POST)
    @ResponseBody
    public Object addup(@RequestBody Pageeditor editor, HttpServletRequest request){
        if(!loginManage.isUserLogin(request)){
            return result.setStatus(-1,"no login");
        }
        try{
            if (editor.getId()>0){
                List<Object> list = editor.getList();
                String jsonString = JSON.toJSONString(list);
                editor.setStr(jsonString);
                pageeditorService.update(editor);
            }else {
                List<Object> list = editor.getList();
                String jsonString = JSON.toJSONString(list);
                editor.setStr(jsonString);
                pageeditorService.add(editor);
            }
        } catch (Exception e){
            e.printStackTrace();
            result.clean();
            return result.setStatus(-4,"exception");
        }
        result.clean();
        return result.setStatus(0,"ok");
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Object getById(HttpServletRequest request){
        /*if(!loginManage.isUserLogin(request)){
            return result.setStatus(-1,"no login");
        }*/
        try{
            List<Pageeditor> all = pageeditorService.getAll();
            for (Pageeditor pageeditor : all) {
                String str = pageeditor.getStr();
                List<Object> list = JSONObject.parseArray(str, Object.class);
                pageeditor.setList(list);
            }
            result.clean();
            result.put("data",all);
        } catch (Exception e){
            e.printStackTrace();
            result.clean();
            return result.setStatus(-4,"exception");
        }
        return result.setStatus(0,"ok");
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public Object getByTypeAndName(@RequestBody Pageeditor pageeditor,HttpServletRequest request){
        /*if(!loginManage.isUserLogin(request)){
            result.clean();
            return result.setStatus(-1,"no login");
        }*/
        try{
            Pageeditor editor = pageeditorService.getByTypeAndName(pageeditor.getType());
            if(null == editor){
                result.clean();
                return result.setStatus(0,"no data");
            }
            String str = editor.getStr();
            List<Object> list = JSONObject.parseArray(str, Object.class);
            editor.setList(list);
            result.clean();
            result.put("data",editor);
        } catch (Exception e){
            e.printStackTrace();
            result.clean();
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
            pageeditorService.delete(id);
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4,"exception");
        }
        return result.setStatus(0, "ok");
    }
}
