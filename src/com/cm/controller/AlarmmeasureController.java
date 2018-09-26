package com.cm.controller;

import com.cm.entity.Alarmmeasure;
import com.cm.security.LoginManage;
import com.cm.service.AlarmmeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Scope
@Controller
@RequestMapping("/alarm")
public class AlarmmeasureController {

	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private AlarmmeasureService service;
	
	@RequestMapping(value="/measure",method=RequestMethod.POST)
	@ResponseBody
	public Object addAlarmMeasure(@RequestBody Alarmmeasure measure,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
            Integer id = service.getAnaloginfoQueryId(measure);
            if(null == id){
                return result.setStatus(0,"ok");
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
            String now = df.format(new Date());
            service.updateAnaloginfoQueryMeasure(measure.getMeasure(),now,id);
        } catch (Exception e) {
			e.printStackTrace();
			result.setStatus(-4, "exception");
		}
		
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public Object getAll(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try{
			List<Alarmmeasure> all = service.getAll();
			result.clean();
			result.put("data", all);
		} catch(Exception e){
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
}
