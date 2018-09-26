package com.cm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.entity.Cardreder;
import com.cm.entity.Sensor;
import com.cm.entity.SwitchSensor;
import com.cm.security.LoginManage;
import com.cm.service.BaseinfoService;
import com.cm.service.CardrederService;
import com.cm.service.SwitchSensorService;

@RequestMapping("/device")
@Controller
@Scope("prototype")
public class AlldeviceController {
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private BaseinfoService baseinfoService;
	
	@Autowired
	private SwitchSensorService sensorService;
	
	@Autowired
	private CardrederService cardrederService;
	
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public Object getAlldevice(HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<Sensor> sensors = baseinfoService.getAllSensor(0);
			List<SwitchSensor> switches = sensorService.getAll(0);
			List<Cardreder> cardreder = cardrederService.getallCardreder(null);
			List<Object> list = new ArrayList<Object>();
			for (Sensor s : sensors) {
				list.add(s);
			}
			for (SwitchSensor s : switches) {
				list.add(s);
			}
			for (Cardreder c : cardreder) {
				list.add(c);
			}
			result.clean();
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

}
