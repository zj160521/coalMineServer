package com.cm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.entity.vo.DrainageRealTimeValue;
import com.cm.security.LoginManage;
import com.cm.service.DrainageRealTimeValueService;

@Scope("prototype")
@Controller
@RequestMapping("/realtime")
public class DrainageRealTimeValueController {
	
	@Autowired
	private LoginManage loginManage;
	
	@Autowired
	private ResultObj result;
	
	@Autowired
	private DrainageRealTimeValueService realtimeService;
	
	/**
	 * 查询瓦斯抽放系统实时值
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/value/{id}")
	@ResponseBody
	public Object getAllpipetemperature(@PathVariable int id,HttpServletRequest request){
		if(!loginManage.isUserLogin(request)){
			return result.setStatus(-1, "no login");
		}
		try {
			List<DrainageRealTimeValue> list = realtimeService.getAllrealtimevalue(id);
//			LogOut.log.debug(list);
			result.clean();
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

}
