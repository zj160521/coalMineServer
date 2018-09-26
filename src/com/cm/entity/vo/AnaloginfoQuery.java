package com.cm.entity.vo;

public class AnaloginfoQuery {
	
	private int id;
	private int sensor_id;//传感器主键id
	private int sensorId;
	private int startId;
	private int endId;
	private String starttime;//开始时间
	private String endtime;//结束时间
	private String ip;//ip地址
	private String pid;//传感器通信地址
	private int sensor_type;//传感器类型id
	private String sensortype;//传感器类型
	private String position;//传感器所在位置
	private double limit_alarm;//报警门限
	private double limit_power;//断电门限
	private double limit_repower;//复电门限
	private double floor_alarm;//下限报警
	private double floor_power;//下限断电
	private double floor_repower;//下限复电
	private double maxvalues;//最大值
	private String maxtime;//最大值时间
	private double avgvalue;//平均值
	private double minvalue;//最小值
	private String times;//报警时长
	private String unit;//单位
	private int status;//状态
	private String response;//结果状态描述
	private String desp;//返回结果详情
	private String calibration;//调校
	private String measures;//措施
	private String measurestime;//措施时刻
	private String alais;//别名
	private int powervalue;//断电命令值
	private int feedId;//馈电器Id;
	private int feedback;
	private String powercom;//断电命令
	private String powerposition;//断电区域
	private String feedstatus;//馈电状态
	private String[] feedstatuslist;
	private String feedstarttime;
	private String feedendtime;
	private int state;
	private String action_ip;//控制设备ip
	private int action_type;//控制设备类型
	private int action_devid;//控制设备id
	private String action_uid;//控制设备uid
	private int debug;
	private String feeduid;
	private int alarm_status;
	private String alarmstatus;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(int sensor_id) {
		this.sensor_id = sensor_id;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public int getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(int sensor_type) {
		this.sensor_type = sensor_type;
	}

	public String getSensortype() {
		return sensortype;
	}
	public void setSensortype(String sensortype) {
		this.sensortype = sensortype;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public double getLimit_alarm() {
		return limit_alarm;
	}
	public void setLimit_alarm(double limit_alarm) {
		this.limit_alarm = limit_alarm;
	}

	public double getMaxvalues() {
		return maxvalues;
	}
	public void setMaxvalues(double maxvalues) {
		this.maxvalues = maxvalues;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getStartId() {
		return startId;
	}
	public void setStartId(int startId) {
		this.startId = startId;
	}
	public int getEndId() {
		return endId;
	}
	public void setEndId(int endId) {
		this.endId = endId;
	}
	public String getMaxtime() {
		return maxtime;
	}
	public void setMaxtime(String maxtime) {
		this.maxtime = maxtime;
	}
	public String getCalibration() {
		return calibration;
	}
	public void setCalibration(String calibration) {
		this.calibration = calibration;
	}
	public String getMeasures() {
		return measures;
	}
	public void setMeasures(String measures) {
		this.measures = measures;
	}
	public double getAvgvalue() {
		return avgvalue;
	}
	public void setAvgvalue(double avgvalue) {
		this.avgvalue = avgvalue;
	}
	public double getLimit_power() {
		return limit_power;
	}
	public void setLimit_power(double limit_power) {
		this.limit_power = limit_power;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	public int getPowervalue() {
		return powervalue;
	}
	public void setPowervalue(int powervalue) {
		this.powervalue = powervalue;
	}
	public int getFeedId() {
		return feedId;
	}
	public void setFeedId(int feedId) {
		this.feedId = feedId;
	}
	public String getPowercom() {
		return powercom;
	}
	public void setPowercom(String powercom) {
		this.powercom = powercom;
	}
	public String getFeedstatus() {
		return feedstatus;
	}
	public void setFeedstatus(String feedstatus) {
		this.feedstatus = feedstatus;
	}
	public String getMeasurestime() {
		return measurestime;
	}
	public void setMeasurestime(String measurestime) {
		this.measurestime = measurestime;
	}
	public String getPowerposition() {
		return powerposition;
	}
	public void setPowerposition(String powerposition) {
		this.powerposition = powerposition;
	}
	public int getSensorId() {
		return sensorId;
	}
	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}
	public double getLimit_repower() {
		return limit_repower;
	}
	public void setLimit_repower(double limit_repower) {
		this.limit_repower = limit_repower;
	}
	public String getFeedstarttime() {
		return feedstarttime;
	}
	public void setFeedstarttime(String feedstarttime) {
		this.feedstarttime = feedstarttime;
	}
	public String getFeedendtime() {
		return feedendtime;
	}
	public void setFeedendtime(String feedendtime) {
		this.feedendtime = feedendtime;
	}
	
	public double getMinvalue() {
		return minvalue;
	}
	public void setMinvalue(double minvalue) {
		this.minvalue = minvalue;
	}
	public String getAction_ip() {
		return action_ip;
	}
	public void setAction_ip(String action_ip) {
		this.action_ip = action_ip;
	}
	public int getAction_type() {
		return action_type;
	}
	public void setAction_type(int action_type) {
		this.action_type = action_type;
	}
	public int getAction_devid() {
		return action_devid;
	}
	public void setAction_devid(int action_devid) {
		this.action_devid = action_devid;
	}
	public String getAction_uid() {
		return action_uid;
	}
	public void setAction_uid(String action_uid) {
		this.action_uid = action_uid;
	}
	public String getFeeduid() {
		return feeduid;
	}
	public void setFeeduid(String feeduid) {
		this.feeduid = feeduid;
	}
	public String[] getFeedstatuslist() {
		return feedstatuslist;
	}
	public void setFeedstatuslist(String[] feedstatuslist) {
		this.feedstatuslist = feedstatuslist;
	}
	public int getDebug() {
		return debug;
	}
	public void setDebug(int debug) {
		this.debug = debug;
	}
	public double getFloor_alarm() {
		return floor_alarm;
	}
	public void setFloor_alarm(double floor_alarm) {
		this.floor_alarm = floor_alarm;
	}
	public double getFloor_power() {
		return floor_power;
	}
	public void setFloor_power(double floor_power) {
		this.floor_power = floor_power;
	}
	public double getFloor_repower() {
		return floor_repower;
	}
	public void setFloor_repower(double floor_repower) {
		this.floor_repower = floor_repower;
	}
	public int getFeedback() {
		return feedback;
	}
	public void setFeedback(int feedback) {
		this.feedback = feedback;
	}
	public int getAlarm_status() {
		return alarm_status;
	}
	public void setAlarm_status(int alarm_status) {
		this.alarm_status = alarm_status;
	}
	public String getAlarmstatus() {
		return alarmstatus;
	}
	public void setAlarmstatus(String alarmstatus) {
		this.alarmstatus = alarmstatus;
	}
	
	
}
