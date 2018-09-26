package com.cm.controller;

import com.cm.entity.PositionType;
import com.cm.security.LoginManage;
import com.cm.service.PositionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/position")
@Controller
public class PositionTypeController {

    @Autowired
    private LoginManage loginManage;

    @Autowired
    private ResultObj result;

    @Autowired
    private PositionTypeService typeService;

    @RequestMapping(value = "/alltype", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllPositionType(HttpServletRequest request){
        /*if (!loginManage.isUserLogin(request)){
            return result.setStatus(-1, "no login");
        }*/
        try{
            List<PositionType> all = typeService.getAll();
            result.put("data", all);
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4, "exception");
        }
        return result.setStatus(0, "ok");
    }

    @RequestMapping(value = "/addup", method = RequestMethod.POST)
    @ResponseBody
    public Object addup(@RequestBody PositionType positionType, HttpServletRequest request){
        if (!loginManage.isUserLogin(request)){
            return result.setStatus(-1, "no login");
        }
        try{
            if (positionType.getId() > 0){
                typeService.update(positionType);
            } else {
                PositionType positionType1 = typeService.getByName(positionType.getName());
                if (null != positionType1){
                    return result.setStatus(-4, "该位置类型已经存在");
                }
                typeService.add(positionType);
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
            return result.setStatus(-1, "nologin");
        }
        try{
            typeService.delete(id);
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4, "exception");
        }
        return result.setStatus(0, "ok");
    }
}
