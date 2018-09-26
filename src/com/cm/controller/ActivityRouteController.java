package com.cm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;
import util.JedisUtil;
import util.LogOut;

import com.alibaba.fastjson.JSONObject;
import com.cm.entity.Area;
import com.cm.entity.Area2;
import com.cm.entity.Card;
import com.cm.entity.Cardreder;
import com.cm.entity.Coalmine_route;
import com.cm.entity.Worker;
import com.cm.entity.WorkerTrackRecord;
import com.cm.entity.vo.NameTime;
import com.cm.entity.vo.NowRoute;
import com.cm.entity.vo.RouteMsg;
import com.cm.entity.vo.WorkerInAreaRecVo;
import com.cm.security.LoginManage;
import com.cm.security.RedisClient;
import com.cm.service.ActivityRouteService;
import com.cm.service.AreaService;
import com.cm.service.CardrederService;
import com.cm.service.WorkerService;
import com.cm.service.kafka.KafkaUtil;

@Scope("prototype")
@Controller
@RequestMapping("/route")
public class ActivityRouteController {
	@Autowired
	private ResultObj result;
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private ActivityRouteService arService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private WorkerService workerService;
	@Autowired
	private CardrederService cardreaderService;
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping(value = "/getByCard", method = RequestMethod.POST)
	@ResponseBody
	public Object addCallinfo(@RequestBody Coalmine_route rv,
			HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}

		try {
			if (rv.getStarttime() == null || rv.getStarttime().equals("")) {
				return result.setStatus(-2, "请输入开始时间！");
			}
			if (rv.getEndtime() == null || rv.getEndtime().equals("")) {
				return result.setStatus(-2, "请输入结束时间！");
			}
			if (rv.getCard() == 0) {
				return result.setStatus(-2, "请输入卡号！");
			}

			List<Coalmine_route> list = arService.getRouteByCard(rv);

			Set<Integer> set = new HashSet<Integer>();
			for (Coalmine_route v : list) {
				set.add(v.getDev_id());
			}
			List<Coalmine_route> reslist = new ArrayList<Coalmine_route>();
			for (Integer did : set) {
				for (Coalmine_route v : list) {
					if (did.equals(v.getDev_id())) {
						reslist.add(v);
						break;
					}
				}
			}
			result.put("data", reslist);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.setStatus(0, "ok");
	}

	@RequestMapping(value = "/cardroute", method = RequestMethod.POST)
	@ResponseBody
	public Object cardroute(@RequestBody Coalmine_route rv,
			HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}

		if (rv.getCards() == null || rv.getCards().length == 0) {
			rv.setCards(null);
		}
		List<Coalmine_route> list = arService.cardroute(rv);
		Set<Integer> set = new HashSet<Integer>();
		List<Coalmine_route> res = new ArrayList<Coalmine_route>();
		if (null != list && list.size() > 0) {
			for (Coalmine_route cr : list) {
				set.add(cr.getCard());
			}
			for (Integer card : set) {
				Coalmine_route c=new Coalmine_route();
				List<Coalmine_route> cc = new ArrayList<Coalmine_route>();
				for (Coalmine_route cr : list) {
					if (card == cr.getCard()) {
						c.setCard(cr.getCard());
						c.setWorkerName(cr.getWorkerName());
						c.setDepartName(cr.getDepartName());
						cc.add(cr);
					}
				}
				c.setList(cc);
				res.add(c);
			}
		}
		result.put("data", res);

		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/areaNow", method = RequestMethod.POST)
	@ResponseBody
	public Object areaNow(@RequestBody Coalmine_route rv,HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		
		int aid=rv.getId();
		List<Area2> areas=arService.getAllArea();
		Area2 no=new Area2();
		no.setId(-1);
		no.setAreaname("休息区");
		areas.add(no);
		Area2 no2=new Area2();
		no2.setId(-2);
		no2.setAreaname("井上区域");
		areas.add(no2);
		Area2 no3=new Area2();
		no3.setId(0);
		no3.setAreaname("出入口区域");
		areas.add(no3);
		List<Area2> list=new ArrayList<Area2>();
		if(aid==0){
			list=areas;
		}else{
			for(Area2 a:areas){
				if(a.getId()==aid){
					list.add(a);
				}
			}
		}
		Map<String, String> redismap;
		Jedis jedis;
		try {
			jedis =RedisClient.getInstance().getJedis();
			redismap = jedis.hgetAll("workInArea");
		} catch (Exception e1) {
			e1.printStackTrace();
			return result.setStatus(-2, e1.getMessage());
		}
		try {
			List<Worker> workers=arService.getAllWorker();
			Map<String,Worker> workmap=new HashMap<String,Worker>();
			for(Worker w:workers){
				if(w.getRfcard_id()!=null&&!w.getRfcard_id().equals("")){
					workmap.put(w.getRfcard_id(), w);
				}
			}
			List<Area2> newlist=new ArrayList<Area2>();
			for(Area2 a:list){
                if(a.getIs_exit()!=1){
                	newlist.add(a);
				}
			}
			for(Area2 a:newlist){
				List<Worker> ws=new ArrayList<Worker>();
				for(Entry<String,String> e:redismap.entrySet()){
					if((a.getId()+"").equals(e.getValue().split(";")[0])){
						Worker wor=workmap.get(e.getKey());
						if(wor!=null){
							String battary=e.getValue().split(";")[3];
							wor.setBattary(battary);
							wor.setIn_time(e.getValue().split(";")[4]);
							wor.setStatus(e.getValue().split(";")[5]);
							ws.add(wor);
						}else{
							wor=new Worker();
							wor.setRfcard_id(e.getKey());
							String battary=e.getValue().split(";")[3];
							wor.setBattary(battary);
							wor.setIn_time(e.getValue().split(";")[4]);
							wor.setStatus(e.getValue().split(";")[5]);
							ws.add(wor);
						}
					}
				}
				a.setWorkers(ws);
			}
			result.put("data", newlist);
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "错误！");
		}finally{
			JedisUtil.returnResource(jedis);
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/getallbycard",method = RequestMethod.POST)
	@ResponseBody
	public Object getByCard(@RequestBody NameTime nameTime,HttpServletRequest request){
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			if (nameTime.getCard_id()==null||nameTime.getCard_id().isEmpty()) {
				return result.setStatus(-2, "请输入卡号！");
			}
			List<WorkerTrackRecord> list = arService.getbyCard(nameTime);
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.setStatus(0, "ok");
	}
	
	@RequestMapping(value = "/gettrack",method = RequestMethod.POST)
	@ResponseBody
	public Object getTrack(@RequestBody WorkerTrackRecord record,HttpServletRequest request){
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			List<WorkerInAreaRecVo> list = arService.getTrajectory(record);
			Map<String,String> map=new HashMap<String,String>();
			map.put("lastest", "0");
			for(WorkerInAreaRecVo v:list){
				if(v.getAlais().equals(map.get("lastest"))){
					v.setSame(true);
					map.put("lastest", v.getAlais());
				}else{
					map.put("lastest", v.getAlais());
				}
			}
			result.put("data", list);
			if(list.size()>0){
				result.put("starttime",list.get(0).getStartTime());
				result.put("endtime", list.get(list.size()-1).getStartTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.setStatus(0, "ok");
	}
	
	//实时列表
	@RequestMapping(value = "/getNowRoute",method = RequestMethod.POST)
	@ResponseBody
	public Object getNowRoute(@RequestBody NowRoute r,HttpServletRequest request){
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
			Worker worker=new Worker();
			List<Worker> workers=workerService.getAllWorker(worker);
			Map<String,Worker> workermap=new HashMap<String,Worker>();
			for(Worker w:workers){
				workermap.put(w.getRfcard_id(), w);
			}
			List<Area> areas=areaService.getAll();
			Map<Integer,Area> areamap=new HashMap<Integer,Area>();
			for(Area a:areas){
				areamap.put(a.getId(), a);
			}
			Map<String,Cardreder> cardredermap=new HashMap<String,Cardreder>();
			Cardreder cardreder=new Cardreder();
			List<Cardreder> cardreaders = cardreaderService.getallCardreder(cardreder);
			for(Cardreder c:cardreaders){
				cardredermap.put(c.getSubstation_id()+":"+c.getCid(), c);
			}
			
			Map<String,String> wmap=jedis.hgetAll("workInArea");
			List<NowRoute> reslist=new ArrayList<NowRoute>();
			if(r.getDevid().equals("0")){
				for(Entry<String,String> e:wmap.entrySet()){
					Worker work=workermap.get(e.getKey());
					String[] ac=e.getValue().split(";");
					NowRoute nr=new NowRoute();
//					nr.setDepart_id(work.getDepart_id());
					nr.setRfcard_id(e.getKey());
					nr.setBattary(ac[3]);
					nr.setStatus(ac[5]);
					if(ac[0].equals("-1")){
						nr.setAreaname("休息区");
						if(ac[2].equals("0")){
							nr.setOut_time("-");
						}else{
							nr.setOut_time(ac[2]);
						}
						nr.setDoorStatus("-");
					}else{
						nr.setOut_time("-");
						nr.setAreaid(ac[0]);
						nr.setDevid(ac[1].split(":")[1]);
						if(null!=cardredermap.get(ac[1])){
							nr.setDevname(cardredermap.get(ac[1]).getPosition()+"/"+cardredermap.get(ac[1]).getAddr());
						}
						nr.setReachtime(ac[2]);
						if(!"0".equals(ac[4])){
							nr.setIntime(ac[4]);
							if("0".equals(ac[0])){
								nr.setStaytime(getStaytime(ac[4],ac[2]));
							}else{
								nr.setStaytime(getStaytime(ac[4],sdf.format(new Date())));
							}
						}else{
							nr.setIntime("-");
							nr.setStaytime("-");
						}
						if(Integer.parseInt(ac[0])==0){
							nr.setAreaname("出入口区域");
							if(!"0".equals(ac[4])){
								if(ac[2].equals("0")){
									nr.setOut_time("-");
								}else{
									nr.setOut_time(ac[2]);
								}
							}
						}else if(Integer.parseInt(ac[0])==-2){
							nr.setAreaname("井上区域");
							nr.setIntime("-");
							nr.setStaytime("-");
						}else{
							Area a=areamap.get(Integer.parseInt(ac[0]));
							if(a!=null){
								nr.setAreaname(a.getAreaname());
								nr.setEmphasis(a.getEmphasis());
								nr.setDefault_allow(a.getDefault_allow());
							}
						}
						String status=jedis.hget("workerExitMap", e.getKey());
						if(null!=status&&"2".equals(status)){
							nr.setDoorStatus("正常");
						}else{
							nr.setDoorStatus("-");
						}
					}
					if(work==null){
						reslist.add(nr);
					}else{
						if(null!=work.getDepartname()){
							nr.setDepartname(work.getDepartname());
						}
						nr.setName(work.getName());
						nr.setNum(work.getNum());
						reslist.add(nr);
					}
				}
				result.put("data", reslist);
			}else{
				for(Entry<String,String> e:wmap.entrySet()){
					Worker work=workermap.get(e.getKey());
					String[] ac=e.getValue().split(";");
					if(ac[1].equals(r.getSubstation_id()+":"+r.getDevid())){
						NowRoute nr=new NowRoute();
						if(work!=null){
							nr.setDepart_id(work.getDepart_id());
							nr.setDepartname(work.getDepartname());
							nr.setName(work.getName());
							nr.setNum(work.getNum());
						}
						nr.setRfcard_id(e.getKey());
						nr.setBattary(ac[3]);
						nr.setStatus(ac[5]);
						if(ac[0].equals("-1")){
							nr.setAreaname("休息区");
							if(ac[2].equals("0")){
								nr.setOut_time("-");
							}else{
								nr.setOut_time(ac[2]);
							}
							nr.setDoorStatus("-");
						}else{
							nr.setOut_time("-");
							nr.setAreaid(ac[0]);
							Area a=areamap.get(Integer.parseInt(ac[0]));
							if(a!=null){
								nr.setAreaname(a.getAreaname());
								nr.setEmphasis(a.getEmphasis());
								nr.setDefault_allow(a.getDefault_allow());
							}
							nr.setDevid(ac[1].split(":")[1]);
							if(null!=cardredermap.get(ac[1])){
								nr.setDevname(cardredermap.get(ac[1]).getPosition()+"/"+cardredermap.get(ac[1]).getAddr());
							}
							nr.setReachtime(ac[2]);
							if("0".equals(ac[4])){
								nr.setIntime("-");
								nr.setStaytime("-");
							}else{
								nr.setIntime(ac[4]);
								if("0".equals(ac[0])){
									nr.setStaytime(getStaytime(ac[4],ac[2]));
								}else{
									nr.setStaytime(getStaytime(ac[4],sdf.format(new Date())));
								}
							}
							String status=jedis.hget("workerExitMap", e.getKey());
							if(null!=status&&"2".equals(status)){
								nr.setDoorStatus("正常");
							}else{
								nr.setDoorStatus("-");
							}
						}
						reslist.add(nr);
					}
				}
				result.put("data", reslist);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LogOut.log.debug(e.toString());
			return result.setStatus(-2, "error!");
		}
		
		return result.setStatus(0, "ok");
	}
	
	//获取某个人两天的轨迹
	@RequestMapping(value = "/get2dayRoute",method = RequestMethod.POST)
	@ResponseBody
	public Object get2dayRoute(@RequestBody NowRoute r,HttpServletRequest request){
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		
		if(null!=r.getRfcard_id()){
			Coalmine_route rv=new Coalmine_route();
			rv.setCard(Integer.parseInt(r.getRfcard_id()));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date=new Date();
			String endtime=sdf.format(date);
			Date sdate=new Date(date.getTime()-(1000 * 24 * 60 * 60)*2);
			String starttime=sdf.format(sdate);
			rv.setStarttime(starttime);
			rv.setEndtime(endtime);
			List<Coalmine_route> list = arService.getRouteByCard(rv);
			result.put("data", list);
		}else{
			return result.setStatus(-2, "请填写人员卡号！");
		}
		
		return result.setStatus(0, "ok");
	}
	
	//清空redis里人员位置信息
	@RequestMapping(value = "/clearRedis", method = RequestMethod.POST)
	@ResponseBody
	public Object clearRedis(@RequestBody Card card,HttpServletRequest request) {
		if (!loginManage.isUserLogin(request)) {
			return result.setStatus(-1, "no login");
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str = format.format(new Date());
			List<Integer> b=new ArrayList<Integer>();
			b.add(0);
			b.add(0);
			List<Integer> c=new ArrayList<Integer>();
			c.add(card.getId());
			c.add(0);
			RouteMsg msg=new RouteMsg();
			msg.setAlarm_drop(0);
			msg.setIp("0");
			msg.setDevid(0);
			msg.setType(64);
			msg.setCan1(0);
			msg.setCan2(0);
			msg.setCard(c);
			msg.setDebug(0);
			msg.setDesp("");
			msg.setEmerge(b);
			msg.setId(0);
			msg.setBattary(b);
			msg.setLevel(0);
			msg.setResponse("");
			msg.setStatus(0);
			msg.setTime(str);
			KafkaProducer<String,String> producer = KafkaUtil.getProducer("websocket");
			String value = JSONObject.toJSONString(msg);
			LogOut.log.debug("强制出井："+value);
			ProducerRecord<String,String> record = new ProducerRecord<String, String>("websocket", value);
			producer.send(record, new Callback() {
				public void onCompletion(RecordMetadata metadata, Exception e) {
					if(null!=e)
						e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-2, "错误！");
		}
		
		return result.setStatus(0, "ok");
	}
	
	public String getStaytime(String time,String endtime) throws ParseException{
        Date nowDate=sdf.parse(time);
        Date endDate=sdf.parse(endtime);
		long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
	}
}
