package com.cm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cm.dao.ICoalmineDao;
import com.cm.entity.vo.Coalmine;
import com.cm.service.ConfigService;
import com.cm.service.CreateTableService;
import com.cm.service.kafka.SSDataService;

@Scope("prototype")
@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	private ResultObj result;
	@Autowired
	ICoalmineDao dao;
	@Autowired
	private CreateTableService ctService;
	@Autowired
	private ConfigService cfgService;
	
	@RequestMapping(value = "/f", method = RequestMethod.GET)
	@ResponseBody
	public Object testtest(HttpServletRequest request) throws Exception{
		String str = "";
		Map<String, LinkedList<Coalmine>> dataMap = SSDataService.dataMap;
		for (Entry<String, LinkedList<Coalmine>> etr : dataMap.entrySet()) {
			LinkedList<Coalmine> value = etr.getValue();
			for (Coalmine cl : value) {
				str += cl.getIp()+" : "+cl.getDevid() + "-" +cl.getResponsetime()+"  ";
			}
		}
		result.put("str", str);
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/rddel", method = RequestMethod.GET)
	@ResponseBody
	public Object deltest(HttpServletRequest request) throws Exception{
//		JedisUtil.getJedis().lpush("ni", "wo");
//		RedisOperation.delete("ni");
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	@ResponseBody
	public Object insertTest(HttpServletRequest request) throws Exception{
		int num = 20000;
		PLinsert(num);
		DTinsert(num);
		spellMyselfInsert(num);
		return result.setStatus(0, "ok");
	}
	
	private void PLinsert(int num){
		long currentTimeMillis = System.currentTimeMillis();
		List<Coalmine> list = new ArrayList<>();
		for(int i = 0; i < num; i++){
			Coalmine coal = new Coalmine();
			coal.setDebug(5);
			coal.setDesp("test");
			coal.setDev_id(1);
			coal.setDevid(10);
			coal.setIp("192.168.0.23");
			coal.setLevel(1);
			coal.setRescale(0);
			coal.setResponse("2018-09-17");
			coal.setResponsetime("2018-09-17 02:15:27");
			coal.setStatus(0);
			coal.setType(56);
			coal.setValue(0.1);
			list.add(coal);
		}
//		dao.insertList(list);
		long cast = System.currentTimeMillis() - currentTimeMillis;
		result.put("pl==timeCast=====:", cast);
	}
	
	private void DTinsert(int num){
		long currentTimeMillis = System.currentTimeMillis();
		for(int i = 0; i < num; i++){
			Coalmine coal = new Coalmine();
			coal.setDebug(5);
			coal.setDesp("test");
			coal.setDev_id(1);
			coal.setDevid(10);
			coal.setIp("192.168.0.23");
			coal.setLevel(1);
			coal.setRescale(0);
			coal.setResponse("2018-09-17");
			coal.setResponsetime("2018-09-17 02:15:27");
			coal.setStatus(0);
			coal.setType(56);
			coal.setValue(0.1);
//			dao.insert(coal);
		}
		long cast = System.currentTimeMillis() - currentTimeMillis;
		result.put("dt==timeCast=====:", cast);
	}
	
	private void spellMyselfInsert(int num){
		long currentTimeMillis = System.currentTimeMillis();
		String sql = "INSERT INTO `t_A` (ip,dev_id,devid ,type ,value ,status ,level ,response,responsetime ,debug ,"
				+ "can1 ,can2 ,desp,percent,battary,cut1,cut2,is_cut,feedback,feedstatus,rescale,duration) VALUES ";
		for(int i = 0; i < num; i++){
			String value = null;
			if (i != num-1){
				value = "('192.168.0.23','1','10','56','0.61','0','1','2018-09-17','2018-09-17 02:15:27','5','0','0','test',"
						+ "'0','0','0','0',null, '0', null, '0', null),";
			}else{
				value = "('192.168.0.23','1','10','56','0.61','0','1','2018-09-17','2018-09-17 02:15:27','5','0','0','test',"
						+ "'0','0','0','0',null, '0', null, '0', null)";
			}
			sql+=value;
		}
		ctService.createTble(getMap(sql));
		long cast = System.currentTimeMillis() - currentTimeMillis;
		result.put("sms==timeCast=====:", cast);
	}
	
	public Map<String, String> getMap(String sql){
		Map<String, String> map = new HashMap<String, String>();
		map.put("sql", sql);
		return map;
	}
}
