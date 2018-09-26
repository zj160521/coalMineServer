package com.cm.entity.vo;

public class StationSensorVo {
	private double station_n_point;//经度
	private double Station_e_point;//纬度
	private String alais;
	private int id;
	private int stationId;//分站id
	private String station_name;//分站名称
	private String ipaddr;//分站ip地址
	private int sensorId;//设备编号
	private String type;//设备类型
	private int control;//是否参与控制 0表示不参与控制，1表示参与控制
	private int sensor_type;//设备类型id
	private int type0x;//十六进制设备类型
	private int min_range;//最小量程
	private int max_range;//最大量程
	private int min_frequency;//最小频率
	private int max_frequency;//最大频率
	private int sensor_position;//设备位置id
	private String position;//设备位置
	private String sensorUnit;//单位
	private double n_point;//经度
	private double e_point;//纬度
	private double error_band;//误差范围
	private double limit_warning;//上限预警
	private double floor_warning;//下限预警
	private double limit_alarm;//上限报警
	private double floor_alarm;//下限报警
	private double limit_power;//上限断电值
	private double floor_power;//下限断电值
	private double limit_repower;//上限复电值
	private double floor_repower;//下限复电值
	private String create_time;//创建时间
	private String update_time;//修改时间
	public double getStation_n_point() {
		return station_n_point;
	}
	public void setStation_n_point(double station_n_point) {
		this.station_n_point = station_n_point;
	}
	public double getStation_e_point() {
		return Station_e_point;
	}
	public void setStation_e_point(double station_e_point) {
		Station_e_point = station_e_point;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStationId() {
		return stationId;
	}
	public void setStationId(int stationId) {
		this.stationId = stationId;
	}
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	public int getSensorId() {
		return sensorId;
	}
	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getControl() {
		return control;
	}
	public void setControl(int control) {
		this.control = control;
	}
	public int getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(int sensor_type) {
		this.sensor_type = sensor_type;
	}
	public int getType0x() {
		return type0x;
	}
	public void setType0x(int type0x) {
		this.type0x = type0x;
	}
	public int getMin_range() {
		return min_range;
	}
	public void setMin_range(int min_range) {
		this.min_range = min_range;
	}
	public int getMax_range() {
		return max_range;
	}
	public void setMax_range(int max_range) {
		this.max_range = max_range;
	}
	public int getMin_frequency() {
		return min_frequency;
	}
	public void setMin_frequency(int min_frequency) {
		this.min_frequency = min_frequency;
	}
	public int getMax_frequency() {
		return max_frequency;
	}
	public void setMax_frequency(int max_frequency) {
		this.max_frequency = max_frequency;
	}
	public int getSensor_position() {
		return sensor_position;
	}
	public void setSensor_position(int sensor_position) {
		this.sensor_position = sensor_position;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSensorUnit() {
		return sensorUnit;
	}
	public void setSensorUnit(String sensorUnit) {
		this.sensorUnit = sensorUnit;
	}
	public double getN_point() {
		return n_point;
	}
	public void setN_point(double n_point) {
		this.n_point = n_point;
	}
	public double getE_point() {
		return e_point;
	}
	public void setE_point(double e_point) {
		this.e_point = e_point;
	}
	public double getError_band() {
		return error_band;
	}
	public void setError_band(double error_band) {
		this.error_band = error_band;
	}
	public double getLimit_warning() {
		return limit_warning;
	}
	public void setLimit_warning(double limit_warning) {
		this.limit_warning = limit_warning;
	}
	public double getFloor_warning() {
		return floor_warning;
	}
	public void setFloor_warning(double floor_warning) {
		this.floor_warning = floor_warning;
	}
	public double getLimit_alarm() {
		return limit_alarm;
	}
	public void setLimit_alarm(double limit_alarm) {
		this.limit_alarm = limit_alarm;
	}
	public double getFloor_alarm() {
		return floor_alarm;
	}
	public void setFloor_alarm(double floor_alarm) {
		this.floor_alarm = floor_alarm;
	}
	public double getLimit_power() {
		return limit_power;
	}
	public void setLimit_power(double limit_power) {
		this.limit_power = limit_power;
	}
	public double getFloor_power() {
		return floor_power;
	}
	public void setFloor_power(double floor_power) {
		this.floor_power = floor_power;
	}
	public double getLimit_repower() {
		return limit_repower;
	}
	public void setLimit_repower(double limit_repower) {
		this.limit_repower = limit_repower;
	}
	public double getFloor_repower() {
		return floor_repower;
	}
	public void setFloor_repower(double floor_repower) {
		this.floor_repower = floor_repower;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	
}
