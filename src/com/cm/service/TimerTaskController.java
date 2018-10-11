package com.cm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import util.LogOut;

import com.cm.controller.BackupController;
import com.cm.controller.WebSocketController;
import com.cm.entity.AreaPersonNum;
import com.cm.entity.Coalmine_route;
import com.cm.entity.WorkerInAreaRec;
import com.cm.security.RedisClient;
import com.cm.service.RecomputeService;
import com.cm.service.kafka.AllConsumerThread;
import com.cm.service.kafka.KafkaUtil;


@Component
public class TimerTaskController {
	@Autowired
	private RecomputeService recomservice;
	@Autowired
	private BackupController bc;
	
	private Jedis j=RedisClient.getInstance().getJedis();
	
	public static long checkSelf=0;
	
	/**
	 * 定时器任务，检查数据库备份是否启动
	 */
	@Scheduled(cron="0 0/10 * * * ? ") 
	public void checkDbBack(){
		LogOut.log.debug("检查数据库备份");
		bc.startCron();
	}
	
	/**
	 * 定时器任务，检查kafka连接
	 */
	@Scheduled(cron="0/20 * *  * * ? ") 
	public void check(){
//		LogOut.log.debug("检查心跳");
		long start=System.currentTimeMillis();
		long time=start - checkSelf;
		if(checkSelf!=0&&time>10000){
			checkSelf=0;
			LogOut.log.debug("kafka双机切换失去连接");
			AllConsumerThread.shutdown();
			KafkaUtil.kc=null;
		}
	}
	
	/**
	 * 定时器任务，发送人员人数信息
	 */
	// 10秒执行一次
	@Scheduled(cron="0/10 * *  * * ? ") 
	public void send(){
		try {
			Map<String,String> areamap=j.hgetAll("areaTimeout");
			Map<String,String> areamap2=j.hgetAll("areaMap");
			areamap.put("-2", "0;0;0;0;0;0");
			Map<String,String> workermap=j.hgetAll("workInArea");
			//计算井下总人数
			int n=0;
			for(Entry<String,String> e2:workermap.entrySet()){
				if(!"-1".equals(e2.getValue().split(";")[0])&&!"0".equals(e2.getValue().split(";")[0])&&!"-2".equals(e2.getValue().split(";")[0])){
					n=n+1;
				}
			}
			//把区域为0的换成真实id
			for(Entry<String,String> e2:workermap.entrySet()){
				String ac[]=e2.getValue().split(";");
				String e_area="0";
				if(ac[0].equals("0")){
					e_area=areamap2.get(ac[1]);
					workermap.put(e2.getKey(), e_area+";"+ac[1]+";"+ac[2]+";"+ac[3]+";"+ac[4]+";"+ac[5]);
				}
			}
			Map<String,String> areaname=j.hgetAll("areaName");
			areaname.put("-2", "井上区域");
			AreaPersonNum res=new AreaPersonNum();
			List<AreaPersonNum> list=new ArrayList<AreaPersonNum>();
			for(Entry<String,String> e:areamap.entrySet()){
				AreaPersonNum a=new AreaPersonNum();
				int i=0;
				for(Entry<String,String> e2:workermap.entrySet()){
					if(e.getKey().equals(e2.getValue().split(";")[0])){
						i=i+1;
					}
				}
				if(e.getKey().equals("1")){
					res.setAreaid(e.getKey());
					res.setAreaname(areaname.get(e.getKey()));
					res.setMax_allow(Integer.parseInt(e.getValue().split(";")[1]));
				}else{
					a.setAreaid(e.getKey());
					a.setAreaname(areaname.get(e.getKey()));
					a.setMax_allow(Integer.parseInt(e.getValue().split(";")[1]));
					a.setPersonNum(i);
					list.add(a);
				}
			}
			
			res.setPersonNum(n);
			res.setList(list);
			res.setCmd("downholeTotal");
			JSONObject json = JSONObject.fromObject(res);
			LogOut.log.debug(json.toString());
			sendMsg(json.toString());
		} catch (Exception e) {
			j=RedisClient.getInstance().getJedis();
			e.printStackTrace();
		}
	}
	
	private void sendMsg(String str) {
		WebSocketController.pushMsg(str);
	}
	
	/**
	 * 定时器任务，重算补传数据
	 */
	// 10分钟执行一次
//	@Scheduled(cron="0 */5 * * * ? ") 
	public void recompute(){
		LogOut.log.debug("计算补传数据");
		List<Coalmine_route> lists=recomservice.getReimportByTime();
		if(!j.exists("flag")){
			j.set("flag", "0");
		}
		String flag=j.get("flag");
		if(lists.size()==0&&"0".equals(flag)){
			List<Coalmine_route> list=recomservice.getReimport();
			if(list!=null&&list.size()>0){
				List<Coalmine_route> list1=recomservice.getCardGroup();
				j.set("flag", "1");
				for(Coalmine_route c:list1){
					try {
						compute(c);
					} catch (Exception e) {
						e.printStackTrace();
						LogOut.log.info("reimport异常", e);
						j.set("flag", "0");
					}
				}
				recomservice.reimportDel();
				j.set("flag", "0");
			}
		}
	}
	
	public void compute(Coalmine_route c){
		String time=c.getResponsetime().split(" ")[0].replace("-", "_");
		String time2=c.getResponsetime().split(" ")[0];
		Map<String,String> map=new HashMap<String,String>();
		String sql="select * from t_coalMine_route_"+time+" where card="+c.getCard();
		map.put("sql", sql);
		List<Coalmine_route> list=recomservice.getDayRoute(map);
		Map<String,String> map2=new HashMap<String,String>();
		String sql2="select * from t_coalMine_route where date(responsetime)='"+time2+"' and card="+c.getCard();
		map2.put("sql", sql2);
		List<Coalmine_route> list2=recomservice.getDayRoute(map2);
		list.addAll(list2);
		
		//得到所有记录
		Collections.sort(list);
		Map<String,String> area = j.hgetAll("area");
		Map<String, String> smap = j.hgetAll("substation");
		Map<String,String> workinarea=new HashMap<String,String>();
		List<WorkerInAreaRec> wiars=new ArrayList<WorkerInAreaRec>();
		for(Coalmine_route cr:list){
			compute2(cr,wiars,workinarea,area,smap);
		}
		//删除
		recomservice.del(time2, c.getCard());
		//批量插入
		int count=0;
		List<WorkerInAreaRec> newlist=new ArrayList<WorkerInAreaRec>();
		for(WorkerInAreaRec sp:wiars) {
			if(count > 0 && count % 50 == 0) {
				newlist.add(sp);
				recomservice.addAll(newlist);
				newlist.clear();
			}
			else {
				newlist.add(sp);
			}
			count++;
		}
		if(newlist.size() > 0){
			recomservice.addAll(newlist);
		}
		Map<String,String> map3=new HashMap<String,String>();
		String sql3="insert into t_coalMine_route_"+time+" (ip,dev_id,devid,area_id,personNum,type,card,totalPN,status,debug,battary,responsetime,response,desp,default_allow,emphasis,worker_id,can1,can2)select ip,dev_id,devid,area_id,personNum,type,card,totalPN,status,debug,battary,responsetime,response,desp,default_allow,emphasis,worker_id,can1,can2 from t_coalMine_route where date(responsetime)='"+time2+"' and card="+c.getCard();
		map3.put("sql", sql3);
		recomservice.addold(map3);
		
	}
	
	public void compute2(Coalmine_route cr,List<WorkerInAreaRec> wiars,Map<String,String> workinarea,Map<String,String> areaMap,Map<String, String> smap){
		String subid=smap.get(cr.getIp());
		String area=areaMap.get(subid+":"+cr.getDevid());
		if(area==null){
			area="-2";
		}
		String areaCard = workinarea.get(String.valueOf(cr.getCard()));
//		LogOut.log.debug(areaCard);
		String[] ac = null;
		if (areaCard == null) {
			workinarea.put(String.valueOf(cr.getCard()), area + ";" + subid+":"+cr.getDevid() + ";" + cr.getResponsetime()
			+ ";" + cr.getBattary() + ";" + "0" + ";" + "0");
			return;
		}
		
		String a=j.hget("workInArea", String.valueOf(cr.getCard()));
    	String wa[]=null;
    	if(a!=null){
    		wa=a.split(";");
    	}
		
		// 判断redis中是否有此人的配置信息
		if (areaCard != null) {
			ac = areaCard.split(";");
			// 有此人的信息，对比信息是否改变，一共三种情况

			String intime = null;
			if (ac[4].equals("0")) {
				intime = cr.getResponsetime();
			} else {
				intime = ac[4];
			}

			// 区域变化和读卡器变化
			if (!ac[0].equals(area)) {
				LogOut.log.debug("区域变化和读卡器变化");

				// 判断是否为入井数据
				if (ac[0].equals("-1") && ac[1].equals("0:0")) {

					LogOut.log.debug("入井前到达出入口------------");
					// 修改redis
					workinarea.put(String.valueOf(cr.getCard()), area + ";" + subid+":"+cr.getDevid() + ";" + cr.getResponsetime()
							+ ";" + cr.getBattary() + ";" + "0" + ";" + "0");

					if(wa!=null){
						int res=cr.getResponsetime().compareTo(wa[2]);
						if(res>0||res==0){
							j.hset("workInArea", String.valueOf(cr.getCard()), area + ";" + subid+":"+cr.getDevid() + ";" + cr.getResponsetime()
							+ ";" + cr.getBattary() + ";" + "0" + ";" + "0");
						}
					}else{
						j.hset("workInArea", String.valueOf(cr.getCard()), area + ";" + subid+":"+cr.getDevid() + ";" + cr.getResponsetime()
						+ ";" + cr.getBattary() + ";" + "0" + ";" + "0");
					}
					//不插入入口区域变化信息
					return;
				}
				WorkerInAreaRec w2=new WorkerInAreaRec();
				// 有改变的值，插入到mysql
				w2.setArea_id(Integer.parseInt(ac[0]));
				w2.setCard_id(String.valueOf(cr.getCard()));
				w2.setDev_id(Integer.parseInt(ac[1].split(":")[1]));
				w2.setStatus(1);
				w2.setSub_id(Integer.parseInt(ac[1].split(":")[0]));
				w2.setWorker_id(cr.getWorker_id());
				w2.setResponsetime(cr.getResponsetime());
				wiars.add(w2);

				WorkerInAreaRec w=new WorkerInAreaRec();
				w.setArea_id(Integer.parseInt(area));
				w.setCard_id(String.valueOf(cr.getCard()));
				w.setDev_id(cr.getDevid());
				w.setStatus(2);
				w.setSub_id(Integer.parseInt(subid));
				w.setWorker_id(cr.getWorker_id());
				w.setResponsetime(cr.getResponsetime());
				wiars.add(w);
				
				if(wa!=null){
					int res=cr.getResponsetime().compareTo(wa[2]);
					if(res>0||res==0){
						j.hset("workInArea", String.valueOf(cr.getCard()), area + ";" + subid+":"+cr.getDevid() + ";" + cr.getResponsetime()
						+ ";" + cr.getBattary() + ";" + wa[4] + ";" + "0");
					}
				}else{
					j.hset("workInArea", String.valueOf(cr.getCard()), area + ";" + subid+":"+cr.getDevid() + ";" + cr.getResponsetime()
					+ ";" + cr.getBattary() + ";" + intime + ";" + "0");
				}
				
				// 修改redis
				workinarea.put(String.valueOf(cr.getCard()), area + ";" + subid+":"+cr.getDevid() + ";" + cr.getResponsetime()
				+ ";" + cr.getBattary() + ";" + intime + ";" + "0");
				
				// 判断是否有读卡器变化，但区域没变
			} else if ((!ac[1].equals(subid+":"+cr.getDevid())) && (ac[0].equals(area))) {
				LogOut.log.debug("读卡器变化，但区域没变");
				WorkerInAreaRec w2=new WorkerInAreaRec();
				// 有改变的值，插入到mysql
				w2.setArea_id(Integer.parseInt(ac[0]));
				w2.setCard_id(String.valueOf(cr.getCard()));
				w2.setDev_id(Integer.parseInt(ac[1].split(":")[1]));
				w2.setStatus(1);
				w2.setSub_id(Integer.parseInt(ac[1].split(":")[0]));
				w2.setWorker_id(cr.getWorker_id());
				w2.setResponsetime(cr.getResponsetime());
				wiars.add(w2);

				WorkerInAreaRec w=new WorkerInAreaRec();
				w.setArea_id(Integer.parseInt(area));
				w.setCard_id(String.valueOf(cr.getCard()));
				w.setDev_id(cr.getDevid());
				w.setStatus(2);
				w.setSub_id(Integer.parseInt(subid));
				w.setWorker_id(cr.getWorker_id());
				w.setResponsetime(cr.getResponsetime());
				wiars.add(w);
				
				if(wa!=null){
					int res=cr.getResponsetime().compareTo(wa[2]);
					if(res>0||res==0){
						j.hset("workInArea", String.valueOf(cr.getCard()), area + ";" + subid+":"+cr.getDevid() + ";" + cr.getResponsetime()
						+ ";" + cr.getBattary() + ";" + wa[4] + ";" + "0");
					}
				}else{
					j.hset("workInArea", String.valueOf(cr.getCard()), area + ";" + subid+":"+cr.getDevid() + ";" + cr.getResponsetime()
					+ ";" + cr.getBattary() + ";" + intime + ";" + "0");
				}
				
				// 修改redis
				workinarea.put(String.valueOf(cr.getCard()), area + ";" + subid+":"+cr.getDevid() + ";" + cr.getResponsetime()
				+ ";" + cr.getBattary() + ";" + intime + ";" + "0");
			} 
		}
	}
}
