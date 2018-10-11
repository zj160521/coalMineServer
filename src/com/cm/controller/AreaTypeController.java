package com.cm.controller;

import com.alibaba.fastjson.JSONObject;
import com.cm.entity.AreaType;
import com.cm.security.LoginManage;
import com.cm.service.AreaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/areatype")
public class AreaTypeController {

    @Autowired
    private LoginManage loginManage;

    @Autowired
    private ResultObj result;

    @Autowired
    private AreaTypeService areaTypeService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllAreaType(){
        try{
            List<AreaType> allAreaType = areaTypeService.getAllAreaType();
            result.put("data", allAreaType);
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4, "exception");
        }
        return result.setStatus(0, "ok");
    }

    @RequestMapping(value = "/addup", method = RequestMethod.POST)
    @ResponseBody
    public Object addup(@RequestBody AreaType areaType, HttpServletRequest request){
        if(!loginManage.isUserLogin(request)){
            return result.setStatus(-1, "no login");
        }
        try{
            if (areaType.getId() > 0){
                if (areaType.getType_id() != 0){
                    return result.setStatus(-4, "系统预置类型不能修改");
                }
                areaTypeService.update(areaType);
                String remark = JSONObject.toJSONString(areaType);
                String operation2 = "修改区域类型";
                loginManage.addLog(request, remark, operation2, 15121);
            } else {
                AreaType areaType1 = areaTypeService.getByName(areaType.getName());
                if (null != areaType1){
                    return result.setStatus(-4, "该区域类型已经存在");
                }
                areaTypeService.add(areaType);
                String remark = JSONObject.toJSONString(areaType);
                String operation2 = "增加区域类型";
                loginManage.addLog(request, remark, operation2, 15121);
            }
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4, "exception");
        }
        return result.setStatus(0, "ok");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable int id, HttpServletRequest request){
        if (!loginManage.isUserLogin(request)){
            return result.setStatus(-1, "no login");
        }
        try{
            AreaType areaType = areaTypeService.getById(id);
            if (areaType.getType_id() != 0){
                return result.setStatus(-4, "系统预置类型不能删除");
            }

            AreaType type = areaTypeService.getById(id);
            String remark = JSONObject.toJSONString(type);
            String operation2 = "删除区域类型";
            loginManage.addLog(request, remark, operation2, 15122);
            areaTypeService.delete(id);
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4, "exception");
        }
        return result.setStatus(0, "ok");
    }
}
