package com.cm.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.cm.entity.Area;
import com.cm.entity.AreaWorker2;
import com.cm.entity.AttendanceMap;
import com.cm.entity.CardWorker;
import com.cm.entity.Cardreder;
import com.cm.entity.Substation2;
import com.cm.entity.Worker;
import com.cm.entity.vo.AreaVo;
import com.cm.service.AreaCardService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import util.LogOut;

@Component("RedisClient")
public class RedisClient {

	public Jedis jedis=null;// 非切片额客户端连接
	public JedisPool jedisPool=null;// 非切片连接池
	public ShardedJedis shardedJedis;// 切片额客户端连接
	public ShardedJedisPool shardedJedisPool;// 切片连接池
	public static RedisClient rc;
	private int i=0;
	public RedisClient() {
		
	}

	public static RedisClient getInstance() {
		if(rc==null){
			synchronized(RedisClient.class){
				if(rc==null){
					rc=new RedisClient();
				}
			}
		}
		return rc;
	}
	
	public synchronized Jedis getJedis() {
		try {
			if(jedisPool==null){
				LogOut.log.debug("redis重连！");
				initialPool();
//				initialShardedPool();
//				shardedJedis = shardedJedisPool.getResource();
			}
			jedis = jedisPool.getResource();
		} catch (Exception e) {
			e.printStackTrace();
			i=i+1;
			if(i==3){
				LogOut.log.debug("redis连接失败！");
				try {
					jedis.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				try {
					jedisPool.destroy();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				jedisPool=null;
				jedis=null;
				i=0;
				return null;
			}
			LogOut.log.info("RedisClient Exception-",e);
			returnBrokenResource(jedis);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			getJedis();
		}
		return jedis;
	}
	
	public synchronized void returnBrokenResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnBrokenResource(jedis);
		}
	}
	
	/**
	 * 初始化非切片池
	 */
	private void initialPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		
		//最大空闲连接数, 默认8个
		config.setMaxIdle(8);
		
		//最大连接数, 默认8个
		config.setMaxTotal(1024);
		
		//获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
		config.setMaxWaitMillis(-1);
		
		//最小空闲连接数, 默认0
		config.setMinIdle(0);
		
		//在获取连接的时候检查有效性, 默认false
		config.setTestOnBorrow(false);

		//每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		config.setNumTestsPerEvictionRun(3);
		
		//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
		config.setBlockWhenExhausted(false);
		
		//逐出连接的最小空闲时间 默认1800000毫秒(30分钟)，设置3分钟
		config.setMinEvictableIdleTimeMillis(180000);
		
		//对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)   
		config.setSoftMinEvictableIdleTimeMillis(180000);
		
		ConfigUtil cfg = ConfigUtil.getInstance();
		jedisPool = new JedisPool(config, cfg.getRedis_ip(), Integer.parseInt(cfg.getRedis_port()));
	}

	/**
	 * 初始化切片池
	 */
	private void initialShardedPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(5);
		config.setMaxWaitMillis(1000l);
		config.setTestOnBorrow(false);
		// slave链接
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		ConfigUtil cfg = ConfigUtil.getInstance();
		shards.add(new JedisShardInfo(cfg.getRedis_ip(), Integer.parseInt(cfg.getRedis_port()), "master"));

		// 构造池
		shardedJedisPool = new ShardedJedisPool(config, shards);
	}

	// 创建和更新地区和读卡器关系表
	public void setAreaMap(AreaCardService as) throws Exception {
		if(jedis==null){
			getJedis();
		}
		jedis.del("area");
		jedis.del("areaMap");
		List<AreaVo> list = as.getAreaTable();
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> map2 = new HashMap<String, String>();
		for (AreaVo av : list) {
			map2.put(av.getSubstation_id() + ":" + av.getReaderId(), "" + av.getAreaId());
			if (av.getIs_exit() == 1) {
				map.put(av.getSubstation_id() + ":" + av.getReaderId(), "" + 0);
			} else {
				map.put(av.getSubstation_id() + ":" + av.getReaderId(), "" + av.getAreaId());
			}
		}
		if (list.size() > 0) {
			jedis.hmset("area", map);
			jedis.hmset("areaMap", map2);
		}
	}

	// 创建人员与区域关系表，只创建一次
	public void checkWorkerInAreaTable(AreaCardService as) throws Exception {
		if(jedis==null){
			getJedis();
		}
		Set<String> set = jedis.hkeys("workInArea");
		if (set.size() == 0) {
			List<String> list = as.getAllCard();
			Map<String, String> map = new HashMap<String, String>();
			for (String s : list) {
				map.put(s, "-1;0:0;0;0;0;0");
			}
			jedis.hmset("workInArea", map);
		}
	}

	// 创建人员与区域关系表，只创建一次
	public void fluseWorkerInAreaTable(AreaCardService as) throws Exception {
		if(jedis==null){
			getJedis();
		}
		jedis.del("workInArea");
		List<String> list = as.getAllCard();
		Map<String, String> map = new HashMap<String, String>();
		for (String s : list) {
			map.put(s, "-1;0:0;0;0;0;0");
		}
		jedis.hmset("workInArea", map);
	}

	// 区域超时配置表
	public void setAreaTimeout(AreaCardService as) throws Exception {
		if(jedis==null){
			getJedis();
		}
		jedis.del("areaTimeout");
		List<Area> list = as.getAllArea();
		Map<String, String> map = new HashMap<String, String>();
		for (Area a : list) {
			map.put("" + a.getId(), a.getMax_time() + ";" + a.getMax_allow() + ";" + a.getDefault_allow() + ";"
					+ a.getEmphasis() + ";" + a.getWorker_timeout()+";"+a.getIs_exit());
		}
		if (list.size() > 0) {
			jedis.hmset("areaTimeout", map);
		}
	}

	// 区域名称配置表
	public void setAreaName(AreaCardService as) throws Exception {
		if(jedis==null){
			getJedis();
		}
		jedis.del("areaName");
		List<Area> list = as.getAllArea();
		Map<String, String> map = new HashMap<String, String>();
		for (Area a : list) {
			map.put("" + a.getId(), a.getAreaname());
		}
		if (list.size() > 0) {
			jedis.hmset("areaName", map);
		}
	}

	// 区域超时配置表
	public void setCardrederName(AreaCardService as) throws Exception {
		if(jedis==null){
			getJedis();
		}
		jedis.del("cardreaderName");
		List<Cardreder> list = as.getCardreaderName();
		Map<String, String> map = new HashMap<String, String>();
		for (Cardreder a : list) {
			map.put(a.getSubstation_id() + ":" + a.getCid(), a.getAddr());
		}
		if (list.size() > 0) {
			jedis.hmset("cardreaderName", map);
		}
	}

	// 区域人员关系表
	public void setAreaWorker(AreaCardService as) throws Exception {
		if(jedis==null){
			getJedis();
		}
		jedis.del("areaWorker");
		List<AreaWorker2> list = as.getAreaWorker();
		Map<String, String> map = new HashMap<String, String>();
		for (AreaWorker2 a : list) {
			map.put("" + a.getId(), a.getArea_id() + ";" + a.getCard_id());
		}
		if (list.size() > 0) {
			jedis.hmset("areaWorker", map);
		}
	}

	// 区域人员关系表
	public void setCardWorker(AreaCardService as) throws Exception {
		if(jedis==null){
			getJedis();
		}
		jedis.del("cardWorker");
		List<CardWorker> list = as.getCradWorker();
		Map<String, String> map = new HashMap<String, String>();
		for (CardWorker a : list) {
			map.put("" + a.getCard_id(),"" + a.getId());
		}
		if (list.size() > 0) {
			jedis.hmset("cardWorker", map);
		}
	}

	// 门禁读卡器
	public void setExitCardreader(AreaCardService as) throws Exception {
		if(jedis==null){
			getJedis();
		}
		jedis.del("exitCardreader");
		List<Cardreder> list = as.getExitCardreader();
		Map<String, String> map = new HashMap<String, String>();
		for (Cardreder a : list) {
			map.put(a.getSubstation_id() + ":" + a.getCid(), a.getSubstation_id() + ":" + a.getCid());
		}
		if (list.size() > 0) {
			jedis.hmset("exitCardreader", map);
		}
	}

	// 创建人员与区域关系表，只创建一次
	public void setWorkerExitTable(AreaCardService as) throws Exception {
		if(jedis==null){
			getJedis();
		}
		Set<String> set = jedis.hkeys("workerExitMap");
		if (set.size() == 0) {
			List<String> list = as.getAllCard();
			Map<String, String> map = new HashMap<String, String>();
			for (String s : list) {
				map.put(s, "0");
			}
			if (list.size() > 0) {
				jedis.hmset("workerExitMap", map);
			}
		}
	}

	public void setWorkerLatestTable(AreaCardService as) throws Exception {
		if(jedis==null){
			getJedis();
		}
		Set<String> set = jedis.hkeys("workerLatestTime");
		if (set.size() == 0) {
			List<String> list = as.getAllCard();
			Map<String, String> map = new HashMap<String, String>();
			for (String s : list) {
				map.put(s, "0");
			}
			if (list.size() > 0) {
				jedis.hmset("workerLatestTime", map);
			}
		}
	}

	public void setSubstation(AreaCardService as) throws Exception {
		if(jedis==null){
			getJedis();
		}
		jedis.del("substation");
		List<Substation2> list = as.getSubstation();
		Map<String, String> map = new HashMap<String, String>();
		for (Substation2 a : list) {
			map.put("" + a.getIpaddr(), "" + a.getId());
		}
		if (list.size() > 0) {
			jedis.hmset("substation", map);
		}
	}

	public Map<String, String> getWorkerInAreaTable() throws Exception {
		if(jedis==null){
			getJedis();
		}
		Map<String, String> map = jedis.hgetAll("workInArea");
		return map;
	}

	public void setEntranceCard(AreaCardService as) throws Exception {
		if(jedis==null){
			getJedis();
		}
		jedis.del("entrance");
		List<Worker> list = as.getEntranceCard();
		Map<String, String> map = new HashMap<String, String>();
		for (Worker a : list) {
			map.put(a.getEntranceGuardNum(), a.getRfcard_id());
		}
		if (list.size() > 0) {
			jedis.hmset("entrance", map);
		}
	}

	public void setAttendance(AreaCardService as) throws Exception {
		if(jedis==null){
			getJedis();
		}
		jedis.del("attendance");
		List<AttendanceMap> list = as.getAttendance();
		Map<String, String> map = new HashMap<String, String>();
		for (AttendanceMap am : list) {
			map.put(String.valueOf(am.getCard()), am.getClasses());
		}
		if (list.size() > 0) {
			jedis.hmset("attendance", map);
		}
	}
	
	public void setWorkerName(AreaCardService as) throws Exception {
		if(jedis==null){
			getJedis();
		}
		jedis.del("workerName");
		List<Worker> list = as.getWorkerName();
		Map<String, String> map = new HashMap<String, String>();
		for (Worker am : list) {
			map.put(String.valueOf(am.getId()), am.getName()+";"+am.getDepartname()+";"+am.getDuty()+";"+am.getWorktypename());
		}
		if (list.size() > 0) {
			jedis.hmset("workerName", map);
		}
	}
}
