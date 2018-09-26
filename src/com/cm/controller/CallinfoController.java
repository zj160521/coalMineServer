package com.cm.controller;

import com.cm.entity.Callinfo;
import com.cm.entity.Helpme;
import com.cm.entity.Substation;
import com.cm.entity.vo.KafkaMessage;
import com.cm.entity.vo.NameTime;
import com.cm.security.LoginManage;
import com.cm.security.RedisClient;
import com.cm.service.CallinfoService;
import com.cm.service.SubstationService;
import com.cm.service.kafka.KafkaMsgQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import util.LogOut;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;

/**
 * 呼叫信息管理
 * @author hetaiyun
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/callinfo")
public class CallinfoController {

	@Autowired
	private ResultObj result;

	@Autowired
	private LoginManage loginManage;

	@Autowired
	private CallinfoService service;
	
	@Autowired
	private SubstationService subService;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addup", method = RequestMethod.POST)
	@ResponseBody
	public Object addCallinfo(@RequestBody Callinfo callinfo,
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
			if(callinfo.getIds()!=null&&callinfo.getIds().length>0){
				Map<String,List<Integer>> resmap=new HashMap<String,List<Integer>>();
				Map<Integer,String> ipmap=new HashMap<Integer,String>();
				if(callinfo.getCallrange()<4){
					Map<String,String> wmap=jedis.hgetAll("workInArea");
					Map<Integer,String> nmap=new HashMap<Integer,String>();
					for(Entry<String,String> e:wmap.entrySet()){
						for(Integer id:callinfo.getIds()){
							if(e.getKey().equals(id+"")){
								nmap.put(id, e.getValue().split(";")[1]);
							}
						}
					}
					Set<String> set=new HashSet<String>();
					for(Entry<Integer,String> e:nmap.entrySet()){
						set.add(e.getValue());
					}
					//得到读卡器id下对应的所有人员
					for(String s:set){
						List<Integer> list=new ArrayList<Integer>();
						for(Entry<Integer,String> e:nmap.entrySet()){
							if(s.equals(e.getValue())){
								list.add(e.getKey());
							}
						}
						resmap.put(s, list);
					}
					//得到读卡器id对应的ip
					List<Substation> list = subService.getAll();
					for(Substation c:list){
						ipmap.put(c.getId(), c.getIpaddr());
					}
				}
				
				//单人呼叫
				if(callinfo.getCallrange()==1){
					LogOut.log.debug("单人呼叫");
					callinfo.setCalltype(1);
					callsingle(resmap,ipmap,callinfo);
				//多人呼叫
				}else if(callinfo.getCallrange()==2){
					LogOut.log.debug("多人呼叫");
					callinfo.setCalltype(1);
					callmore(resmap,ipmap,callinfo);
				//区域呼叫
				}else if(callinfo.getCallrange()==3){
					LogOut.log.debug("区域呼叫");
					callinfo.setCalltype(2);
					callarea(resmap,ipmap,callinfo);
				}
			}else if(callinfo.getCallrange()==4){
				LogOut.log.debug("全矿呼叫");
				Map<String,String> amap=new HashMap<String,String>();
				amap=jedis.hgetAll("area");
				Map<Integer,String> ipmap=new HashMap<Integer,String>();
				List<Substation> list = subService.getAll();
				for(Substation c:list){
					ipmap.put(c.getId(), c.getIpaddr());
				}
				callinfo.setCalltype(2);
				callallarea(amap,ipmap,callinfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "exception");
		}
		return result.setStatus(0, "ok");
	}

	public void callsingle(Map<String,List<Integer>> resmap,Map<Integer,String> ipmap,Callinfo callinfo){
		for(Entry<String,List<Integer>> e:resmap.entrySet()){
			KafkaMessage km=new KafkaMessage();
			km.setCalltype(1);
			if(e.getValue().size()==1){
				km.setCard1(e.getValue().get(0));
				km.setCard2(0);
				km.setCard3(0);
			}else if(e.getValue().size()==2){
				km.setCard1(e.getValue().get(0));
				km.setCard2(e.getValue().get(1));
				km.setCard3(0);
			}else if(e.getValue().size()==3){
				km.setCard1(e.getValue().get(0));
				km.setCard2(e.getValue().get(1));
				km.setCard3(e.getValue().get(2));
			}
			km.setType(64);
			km.setId(Integer.parseInt(e.getKey().split(":")[1]));
			km.setIp(ipmap.get(Integer.parseInt(e.getKey().split(":")[0])));
			km.setCmd(9);
//			LogOut.log.debug(JSONObject.toJSONString(km));
			KafkaMsgQueue msgQueue = KafkaMsgQueue.getInstance();
			msgQueue.SendMessage(km);
			for(Integer id:e.getValue()){
				callinfo.setCallcard(id+"");
				service.addCallinfo(callinfo);
			}
		}
		
	}
	
	public void callmore(Map<String,List<Integer>> resmap,Map<Integer,String> ipmap,Callinfo callinfo){
		for(Entry<String,List<Integer>> e:resmap.entrySet()){
			if(e.getValue().size()>3){
				for(int i=0;i<e.getValue().size();i=i+3){
					KafkaMessage km=new KafkaMessage();
					km.setCalltype(1);
					if(i<e.getValue().size()){
					    km.setCard1(e.getValue().get(i));
					}
					if(i+1<e.getValue().size()){
					    km.setCard2(e.getValue().get(i+1));
					}
					if(i+2<e.getValue().size()){
					    km.setCard3(e.getValue().get(i+2));
					}
					km.setType(64);
					km.setId(Integer.parseInt(e.getKey().split(":")[1]));
					km.setIp(ipmap.get(Integer.parseInt(e.getKey().split(":")[0])));
					km.setCmd(9);
//					LogOut.log.debug(JSONObject.toJSONString(km));
					KafkaMsgQueue msgQueue = KafkaMsgQueue.getInstance();
					msgQueue.SendMessage(km);
				}
				for(Integer id:e.getValue()){
					callinfo.setCallcard(id+"");
					service.addCallinfo(callinfo);
				}
			}else{
				KafkaMessage km=new KafkaMessage();
				km.setCalltype(1);
				if(e.getValue().size()==1){
					km.setCard1(e.getValue().get(0));
					km.setCard2(0);
					km.setCard3(0);
				}else if(e.getValue().size()==2){
					km.setCard1(e.getValue().get(0));
					km.setCard2(e.getValue().get(1));
					km.setCard3(0);
				}else if(e.getValue().size()==3){
					km.setCard1(e.getValue().get(0));
					km.setCard2(e.getValue().get(1));
					km.setCard3(e.getValue().get(2));
				}
				km.setType(64);
				km.setId(Integer.parseInt(e.getKey().split(":")[1]));
				km.setIp(ipmap.get(Integer.parseInt(e.getKey().split(":")[0])));
				km.setCmd(9);
//				LogOut.log.debug(JSONObject.toJSONString(km));
				KafkaMsgQueue msgQueue = KafkaMsgQueue.getInstance();
				msgQueue.SendMessage(km);
				for(Integer id:e.getValue()){
					callinfo.setCallcard(id+"");
					service.addCallinfo(callinfo);
				}
			}
		}
	}
	
	public void callarea(Map<String,List<Integer>> resmap,Map<Integer,String> ipmap,Callinfo callinfo){
		for(Entry<String,List<Integer>> e:resmap.entrySet()){
			KafkaMessage km=new KafkaMessage();
			km.setCalltype(callinfo.getCalltype());
			km.setType(64);
			km.setId(Integer.parseInt(e.getKey().split(":")[1]));
			km.setIp(ipmap.get(Integer.parseInt(e.getKey().split(":")[0])));
			km.setCmd(9);
			km.setCard1(0);
			km.setCard2(0);
			km.setCard3(0);
//			LogOut.log.debug(JSONObject.toJSONString(km));
			KafkaMsgQueue msgQueue = KafkaMsgQueue.getInstance();
			msgQueue.SendMessage(km);
			for(Integer id:e.getValue()){
				callinfo.setCallcard(id+"");
				service.addCallinfo(callinfo);
			}
		}
	}
	
	public void callallarea(Map<String,String> amap,Map<Integer,String> ipmap,Callinfo callinfo){
		for(Entry<String,String> e:amap.entrySet()){
			KafkaMessage km=new KafkaMessage();
			km.setCalltype(callinfo.getCalltype());
			km.setType(64);
			km.setId(Integer.parseInt(e.getKey().split(":")[1]));
			km.setIp(ipmap.get(Integer.parseInt(e.getKey().split(":")[0])));
			km.setCmd(9);
			km.setCard1(0);
			km.setCard2(0);
			km.setCard3(0);
//			LogOut.log.debug(JSONObject.toJSONString(km));
			KafkaMsgQueue msgQueue = KafkaMsgQueue.getInstance();
			msgQueue.SendMessage(km);
		}
		service.addCallinfo(callinfo);
	}
	
	@RequestMapping(value = "/getall", method = RequestMethod.POST)
	@ResponseBody
	public Object getallCallinfo(@RequestBody Callinfo callinfo,
			HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			if(callinfo.getPagenum()>0){
				int cpage = (callinfo.getPagenum()-1)*15;
				callinfo.setPagenum(cpage);
			}
			List<Callinfo> list = service.getallCallinfo(callinfo);
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}

	@RequestMapping(value = "/getnew", method = RequestMethod.POST)
	@ResponseBody
	public Object getnewCallinfo(HttpServletRequest request,@RequestBody NameTime nameTime) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			if(nameTime.getName()==null){
		    	nameTime.setName("");
		    }
		    nameTime.setName("%"+nameTime.getName()+"%");
		    int nowpage=nameTime.getCur_page();
			int rowsum=nameTime.getPage_rows();
			int start_row=nowpage*rowsum-rowsum;
			nameTime.setCur_page(start_row);
			nameTime.setPage_rows(rowsum);
			
			List<Callinfo> callinfos = service.getnewCallinfo(nameTime);
			int pages=service.getAllPage(nameTime);
			
			result.put("cur_page", nowpage);
		    result.put("page_rows", rowsum);
		    result.put("total_rows", pages);
			result.put("data", callinfos);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");

	}

	@RequestMapping(value = "/getUnfinish", method = RequestMethod.GET)
	@ResponseBody
	public Object getUnfinish(HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			List<Callinfo> callinfos = service.getUnfinish();
			result.put("data", callinfos);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/gethelp", method = RequestMethod.POST)
	@ResponseBody
	public Object gethelp(HttpServletRequest request,@RequestBody NameTime nameTime) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			if(nameTime.getName()==null){
		    	nameTime.setName("");
		    }
		    nameTime.setName("%"+nameTime.getName()+"%");
		    int nowpage=nameTime.getCur_page();
			int rowsum=nameTime.getPage_rows();
			int start_row=nowpage*rowsum-rowsum;
			nameTime.setCur_page(start_row);
			nameTime.setPage_rows(rowsum);
			
			List<Helpme> hps = service.getHelp(nameTime);
			for(Helpme m:hps){
				if(m.getAreaid()==0){
					m.setAreaName("出入口区域");
				}else if(m.getAreaid()==-2){
					m.setAreaName("井上区域");
				}
			}
			int pages=service.getAllPage2(nameTime);
			
			result.put("cur_page", nowpage);
		    result.put("page_rows", rowsum);
		    result.put("total_rows", pages);
			result.put("data", hps);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-4, "exception");
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/helpremark", method = RequestMethod.POST)
	@ResponseBody
	public Object helpremark(HttpServletRequest request,@RequestBody NameTime nameTime) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		if(nameTime.getId()>0&&!"".equals(nameTime.getStarttime())&&nameTime.getStarttime()!=null){
			service.helpRemark(nameTime);
		}
		
		return result.setStatus(0, "ok");
	}
}
