package com.cm.controller;

import com.cm.entity.RadioSound;
import com.cm.security.LoginManage;
import com.cm.security.RedisClient;
import com.cm.service.RadioSoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/radioSound")
public class RadioSoundController {

	@Autowired
	private ResultObj result;

	@Autowired
	private LoginManage loginManage;

	@Autowired
	private RadioSoundService service;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(@RequestBody RadioSound radioSound,
			HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		Object per = loginManage.checkPermission(request);
		if (per != null)
			return per;

		Jedis jedis=null;
		try {
			jedis = RedisClient.getInstance().getJedis();
		} catch (Exception e1) {
			e1.printStackTrace();
			return result.setStatus(-2, e1.getMessage());
		}
		
		try {
			if(jedis.get("sound")==null){
				jedis.set("sound", "0");
			}
			service.add(radioSound);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "参数错误！");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Object check(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}

		Jedis jedis=null;
		try {
			jedis = RedisClient.getInstance().getJedis();
		} catch (Exception e1) {
			e1.printStackTrace();
			return result.setStatus(-2, e1.getMessage());
		}
		try {
			if(jedis.get("sound")==null){
				jedis.set("sound", "0");
			}else if(!jedis.get("sound").equals("0")){
				return result.setStatus(-2, "其他用户正在使用此功能！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "系统错误！请重试！");
		}
		
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Object get(@RequestBody RadioSound radioSound,
			HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}

		try {
			List<RadioSound> list=service.get(radioSound);
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "错误！");
		}
		return result.setStatus(0, "ok");
	}

}
