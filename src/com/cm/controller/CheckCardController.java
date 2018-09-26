package com.cm.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.entity.Worker;
import com.cm.security.LoginManage;
import com.cm.security.RedisClient;
import com.cm.service.WorkerService;

import redis.clients.jedis.Jedis;

/**
 * 员工管理
 * 
 * @author hetaiyun
 *
 */

@Scope("prototype")
@Controller
@RequestMapping("/check")
public class CheckCardController {

	@Autowired
	private ResultObj result;

	@Autowired
	private LoginManage loginManage;

	@Autowired
	private WorkerService workerService;
	
	@RequestMapping(value = "/card", method = RequestMethod.POST)
	@ResponseBody
	public Object getAllWorker(@RequestBody Worker worker, HttpServletRequest request) {
		if (!loginManage.isUserLogin(request))
			return result.setStatus(-1, "no login");
		Jedis jedis=null;
		try {
			jedis=RedisClient.getInstance().getJedis();
			String areaCard = jedis.hget("workInArea",worker.getRfcard_id());
			if(areaCard!=null){
				String area=areaCard.split(";")[0];
				if(!"-2".equals(area)&&!"-1".equals(area)&&!"0".equals(area)){
					return result.setStatus(-2, "卡片正在井下，不能配置！");
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return result.setStatus(-2, e1.getMessage());
		}
		try {
			Worker workerBycardId = workerService.getWorkerBycardId(worker.getRfcard_id());
			if(workerBycardId != null)
				return result.setStatus(1, "卡片已配置");
			else
				return result.setStatus(0, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "excption");
		}
		
	}
}
