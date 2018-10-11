package com.cm.service.kafka;

import net.sf.json.JSONObject;

import com.cm.entity.vo.Coalmine;
import com.cm.security.RedisClient;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 */
public class SSDataService {
	// 原始数据插入数据库速度 /条+1
	public static final int insertNum = 500;

	// 报警速度 /秒
	public static final long warnSpeed = 0;

	// 失效不处理数据 /分
	public static final long noworking = 5;

	public static Map<String, LinkedList<Coalmine>> dataMap = new HashMap<String, LinkedList<Coalmine>>();

	public static RedisClient rc = null;
	Map<String, Object> map = new HashMap<String, Object>();
	Coalmine cm = new Coalmine();
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static StringBuffer sb = new StringBuffer();
	public static StringBuffer sb1 = new StringBuffer();
	public static StringBuffer sb2 = new StringBuffer();
	public static StringBuffer sb3 = new StringBuffer();
	public static StringBuffer sb4 = new StringBuffer();
	public static StringBuffer sb5 = new StringBuffer();
	public static StringBuffer sb6 = new StringBuffer();
	public static StringBuffer sb7 = new StringBuffer();
	public static int i1;
	public static int i2;
	public static int i3;
	public static int i4;
	public static int i5;
	public static int i6;
	public static int i7;
	int devid = 0;
	String ip = null;
	int type = 0;
	List<Integer> cards = null;
	List<Integer> emerges = null;
	List<Integer> battarys = null;
	int did = 0;
	String time = null;
	String response = null;
	String desp = null;
	int debug = 0;
	int level = 0;
	double value = 0;
	int status = 0;
	int can1 = 0;
	int can2 = 0;
	int avg = 0;
	double temperature = 0;
	double pressure = 0;
	double wasi = 0;
	double co = 0;
	double flow_work = 0;
	double flow_standard = 0;
	double flow_pure = 0;
	double battary = 0;
	double percent = 0;
	int cut1 = 0;
	int cut2 = 0;
	List<Integer> is_cut = null;
	List<Integer> feedback = null;
	List<Integer> feedstatus = null;
	List<Integer> cut_dev = null;
	int is_cut2 = 0;
	int feedback2 = 0;
	int feedstatus2 = 0;
	int cut_dev2 = 0;
	int rescale = 0;
	String duration = null;

	// 持久化数据
	@SuppressWarnings("unchecked")
	public void insert(String data) throws Exception {

		init();
		map = JSONObject.fromObject(data);

		for (Entry<String, Object> e : map.entrySet()) {
			if (e.getKey().equals("cmd")) {
				return;
			} else if (e.getKey().equals("devid")) {
				devid = Integer.parseInt(e.getValue().toString());
			} else if (e.getKey().equals("ip")) {
				ip = e.getValue().toString();
			} else if (e.getKey().equals("type")) {
				type = Integer.parseInt(e.getValue().toString());
			} else if (e.getKey().equals("card")) {
				cards = (List<Integer>) e.getValue();
			} else if (e.getKey().equals("id")) {
				if (!e.getValue().toString().equals("null"))
					did = Integer.parseInt(e.getValue().toString());
				else
					return;
			} else if (e.getKey().equals("battary")) {
				if (type == 64) {
					battarys = (List<Integer>) e.getValue();
				} else {
					try {
						battary = Double.parseDouble(e.getValue().toString());
					} catch (Exception e1) {
						battarys = (List<Integer>) e.getValue();
						e1.printStackTrace();
					}
				}
			} else if (e.getKey().equals("value")) {
				if (!e.getValue().toString().equals("null"))
					value = Double.parseDouble(e.getValue().toString());
				else
					return;
			} else if (e.getKey().equals("level")) {
				level = Integer.parseInt(e.getValue().toString());
			} else if (e.getKey().equals("time")) {
				time = e.getValue().toString();
			} else if (e.getKey().equals("response")) {
				response = new String(((String) e.getValue()).getBytes(),
						"utf8");
			} else if (e.getKey().equals("desp")) {
				desp = new String(((String) e.getValue()).getBytes(), "utf8");
			} else if (e.getKey().equals("debug")) {
				debug = Integer.parseInt(e.getValue().toString());
			} else if (e.getKey().equals("status")) {
				status = Math.abs(Integer.parseInt(e.getValue().toString()));
			} else if (e.getKey().equals("can1")) {
				can1 = Integer.parseInt(e.getValue().toString());
			} else if (e.getKey().equals("can2")) {
				can2 = Integer.parseInt(e.getValue().toString());
			} else if (e.getKey().equals("avg")) {
				avg = Integer.parseInt(e.getValue().toString());
			} else if (e.getKey().equals("temperature")) {
				temperature = Double.parseDouble(e.getValue().toString());
			} else if (e.getKey().equals("pressure")) {
				pressure = Double.parseDouble(e.getValue().toString());
			} else if (e.getKey().equals("wasi")) {
				wasi = Double.parseDouble(e.getValue().toString());
			} else if (e.getKey().equals("co")) {
				co = Double.parseDouble(e.getValue().toString());
			} else if (e.getKey().equals("flow_work")) {
				flow_work = Double.parseDouble(e.getValue().toString());
			} else if (e.getKey().equals("flow_standard")) {
				flow_standard = Double.parseDouble(e.getValue().toString());
			} else if (e.getKey().equals("flow_pure")) {
				flow_pure = Double.parseDouble(e.getValue().toString());
			} else if (e.getKey().equals("emerge")) {
				emerges = (List<Integer>) e.getValue();
			} else if (e.getKey().equals("percent")) {
				percent = Double.parseDouble(e.getValue().toString());
			} else if (e.getKey().equals("cut1")) {
				cut1 = Integer.parseInt(e.getValue().toString());
			} else if (e.getKey().equals("cut2")) {
				cut2 = Integer.parseInt(e.getValue().toString());
			} else if (e.getKey().equals("rescale")) {
				rescale = Integer.parseInt(e.getValue().toString());
			} else if (e.getKey().equals("duration")) {
				duration = e.getValue().toString();
			} else if (e.getKey().equals("is_cut")) {
				try {
					is_cut = (List<Integer>) e.getValue();
				} catch (Exception e1) {
					is_cut2 = Integer.parseInt(e.getValue().toString());
					// LogOut.log.info("数据格式异常：", e1);
				}
			} else if (e.getKey().equals("feedback")) {
				try {
					feedback = (List<Integer>) e.getValue();
				} catch (Exception e1) {
					feedback2 = Integer.parseInt(e.getValue().toString());
					// LogOut.log.info("数据格式异常：", e1);
				}
			} else if (e.getKey().equals("feedstatus")) {
				try {
					feedstatus = (List<Integer>) e.getValue();
				} catch (Exception e1) {
					feedstatus2 = Integer.parseInt(e.getValue().toString());
					// LogOut.log.info("数据格式异常：", e1);
				}
			} else if (e.getKey().equals("cut_dev")) {
				try {
					cut_dev = (List<Integer>) e.getValue();
				} catch (Exception e1) {
					cut_dev2 = Integer.parseInt(e.getValue().toString());
					// LogOut.log.info("数据格式异常：", e1);
				}
			}
		}

		addCoalmine(cm);
		if (cm.getDevid() > 0 && type == 0x20 || type == 0x21 || type == 0x22
				|| type == 0x23 || type == 0x50 || type == 0x24 || type == 0x25
				|| type == 0x26 || type == 0x27 || type == 0x28 || type == 0x29
				|| type == 0x2A || type == 0x2B || type == 0x2C || type == 0x3D
				|| type == 0x2E || type == 0x2F || type == 0x30 || type == 0x31
				|| type == 0x32 || type == 0x33 || type == 0x34 || type == 0x35
				|| type == 0x36 || type == 0x37 || type == 0x38 || type == 0x45
				|| type == 0x46 || type == 0x47) {
			
			if (dataMap.containsKey(ip + "-" + devid)) {
				Coalmine c = (Coalmine) cm.clone();
				dataMap.get(ip + "-" + devid).add(c);
			} else {
				LinkedList<Coalmine> dataList = new LinkedList<Coalmine>() {
					private static final long serialVersionUID = 1L;
					private static final int capacity = 60;

					@Override
					public boolean add(Coalmine e) {
						// 超过长度，移除最后一个
						if (size() + 1 > capacity) {
							super.removeFirst();
						}
						return super.add(e);
					}
				};
				Coalmine c = (Coalmine) cm.clone();
				dataList.add(c);
				dataMap.put(ip + "-" + devid, dataList);
			}
		}
	}

	// 原始数据构建类对象
	public void addCoalmine(Coalmine cm) {
		cm.setLevel(level);
		cm.setValue(value);
		cm.setDesp(desp);
		cm.setDev_id(did);
		cm.setDevid(devid);
		cm.setIp(ip);
		cm.setResponse(response);
		cm.setStatus(status);
		cm.setType(type);
		cm.setResponsetime(time);
		cm.setDebug(debug);
		cm.setCan1(can1);
		cm.setCan2(can2);
		cm.setPercent(percent);
		cm.setBattary(battary);
		cm.setCut1(cut1);
		cm.setCut2(cut2);
		cm.setRescale(rescale);
		cm.setFeedback(feedback2);
		cm.setDuration(duration);
	}

	public void init() {
		devid = 0;
		ip = null;
		type = 0;
		cards = null;
		emerges = null;
		battarys = null;
		did = 0;
		time = null;
		response = null;
		desp = null;
		debug = 0;
		level = 0;
		value = 0;
		status = 0;
		can1 = 0;
		can2 = 0;
		avg = 0;
		temperature = 0;
		pressure = 0;
		wasi = 0;
		co = 0;
		flow_work = 0;
		flow_standard = 0;
		flow_pure = 0;
		battary = 0;
		percent = 0;
		cut1 = 0;
		cut2 = 0;
		is_cut = null;
		feedback = null;
		feedstatus = null;
		cut_dev = null;
		is_cut2 = 0;
		feedback2 = 0;
		feedstatus2 = 0;
		cut_dev2 = 0;
		rescale = 0;
		duration = null;
	}
}
