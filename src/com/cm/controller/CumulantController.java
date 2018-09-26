package com.cm.controller;

import com.cm.entity.Cumulant;
import com.cm.security.LoginManage;
import com.cm.service.CumulanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/cumulant")
@Controller
@Scope
public class CumulantController {

    @Autowired
    private CumulanService cumulanService;

    @Autowired
    private LoginManage loginManage;

    @Autowired
    private ResultObj result;

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public Object getAllCumulant(@RequestBody Cumulant cumulant, HttpServletRequest request){
        if(!loginManage.isUserLogin(request)){
            result.clean();
            return result.setStatus(-1,"no login");
        }
        try{
            List<Cumulant> allCumulant = cumulanService.getAllCumulant(cumulant.getSensor_position());
            result.clean();
            result.put("data",allCumulant);
        } catch (Exception e){
            e.printStackTrace();
            return result.setStatus(-4,"exception");
        }
        return result.setStatus(0,"ok");
    }
}
