package com.cm.entity;

public class GD5Report {

	private int id;
	private String ip;
	private int devid;//设备地址id
	private int can1;//0表示正常，1表示CAN1断开
	private int can2;//0表示正常，1表示CAN2断开
	private String responsetime;//上报时间
	private int avg;//0表示瞬时值，1表示平均值
	private double temperature;//温度
	private double pressure;//压力
	private double wasi;//瓦斯浓度
	private double co;//一氧化碳浓度
	private double flow_work;//工况混合瞬时流量
	private double flow_standard;//标况混合瞬时流量
	private double flow_pure;//标况瞬时纯流量
	private double flow_work_sum;//工况混合累积量
	private double flow_standard_sum;//标况混合累积量
	private double flow_pure_sum;//标况纯流量累计
	private String position;//位置
	private int reporttype;//1表示时报表，2表示日报表，3表示旬报表，4,表示月报表，5表示季报表，6表示年报表
	private String starttime;
	private String endtime;
	private String drainagetime;//抽放时间
    private int sensor_type;
    private String type;
    private String sqlday;

    public String getSqlday() {
        return sqlday;
    }

    public void setSqlday(String sqlday) {
        this.sqlday = sqlday;
    }

    public int getSensor_type() {
        return sensor_type;
    }

    public void setSensor_type(int sensor_type) {
        this.sensor_type = sensor_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDrainagetime() {
		return drainagetime;
	}
	public void setDrainagetime(String drainagetime) {
		this.drainagetime = drainagetime;
	}
	public int getReporttype() {
		return reporttype;
	}
	public void setReporttype(int reporttype) {
		this.reporttype = reporttype;
	}
	public String getEndtime() {
		return endtime;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getDevid() {
		return devid;
	}
	public void setDevid(int devid) {
		this.devid = devid;
	}
	public int getCan1() {
		return can1;
	}
	public void setCan1(int can1) {
		this.can1 = can1;
	}
	public int getCan2() {
		return can2;
	}
	public void setCan2(int can2) {
		this.can2 = can2;
	}
	public String getResponsetime() {
		return responsetime;
	}
	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}
	public int getAvg() {
		return avg;
	}
	public void setAvg(int avg) {
		this.avg = avg;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getPressure() {
		return pressure;
	}
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}
	public double getWasi() {
		return wasi;
	}
	public void setWasi(double wasi) {
		this.wasi = wasi;
	}
	public double getCo() {
		return co;
	}
	public void setCo(double co) {
		this.co = co;
	}
	public double getFlow_work() {
		return flow_work;
	}
	public void setFlow_work(double flow_work) {
		this.flow_work = flow_work;
	}
	public double getFlow_standard() {
		return flow_standard;
	}
	public void setFlow_standard(double flow_standard) {
		this.flow_standard = flow_standard;
	}
	public double getFlow_pure() {
		return flow_pure;
	}
	public void setFlow_pure(double flow_pure) {
		this.flow_pure = flow_pure;
	}
	public double getFlow_work_sum() {
		return flow_work_sum;
	}
	public void setFlow_work_sum(double flow_work_sum) {
		this.flow_work_sum = flow_work_sum;
	}
	public double getFlow_standard_sum() {
		return flow_standard_sum;
	}
	public void setFlow_standard_sum(double flow_standard_sum) {
		this.flow_standard_sum = flow_standard_sum;
	}
	public double getFlow_pure_sum() {
		return flow_pure_sum;
	}
	public void setFlow_pure_sum(double flow_pure_sum) {
		this.flow_pure_sum = flow_pure_sum;
	}
	
}
