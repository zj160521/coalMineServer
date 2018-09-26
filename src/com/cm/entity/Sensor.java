package com.cm.entity;

import com.cm.entity.vo.DevlinkSensor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Sensor implements Serializable,Cloneable {

	private static final long serialVersionUID = -2276844842562549426L;
	private int id;
	private int pid;
	private int station;// 分站id
	private String station_name;// 分站名称
	private String ipaddr;// 分站ip地址
	private int sensorId;// 设备编号
	private int control;// 是否参与控制 0表示不参与控制，1表示参与控制
	private int input_type;// 设备输入类型
	private String type;// 设备类型
	private String simpleType;//设备简称
	private int sensor_type;// 设备类型id
	private String type0x;// 十六进制设备类型
	private double min_range;// 二级报警
	private double middle_rang;//
	private double max_range;// 一级报警
	private double min_frequency;// 四级报警
	private double max_frequency;// 三级报警
	private int sensor_position;// 设备位置id
	private String position;// 设备位置信息
	private String sensorUnit;// 单位
	private double n_point;// 经度
	private double e_point;// 纬度
	private double error_band;// 
	private double limit_warning;// 上限预警
	private double floor_warning;// 下限预警
	private double limit_alarm;// 上限报警
	private double floor_alarm;// 下限报警
	private double limit_power;// 上限断电值
	private double floor_power;// 下限断电值
	private double limit_repower;// 上限复电值
	private double floor_repower;// 下限复电值
	private int iscalc;// 是否参与计算
	private int configsync;// 是否同步
	private int drainageId;
	private String drainageName;
	private int menu;
	private String path;
	private int hasfloor;
	private int area_id;
	private String areaname;
	private Map<String, String> map;
	private List<Coalmine> coalmines = new ArrayList<Coalmine>();
	private List<Double> values = new ArrayList<Double>();
	private int isDrainage;
	private double value;
	private String filltime;
	private int list_type;
	private String alais;
	private int alarm_status;
	private int linkageDeviceId;
	private int action;
	private String logic_exps;
	private int devlinkId;
	private int floorDevlinkId;
	private int floorLinkId;
	private int flooraction;
	private int period;
	private double fluctuation_range;
	private String path2;
	private String uid;
	private double x_point;
	private double y_point;
	private int aomId;//声光报警器ID
	private int broadcastId;//广播ID
	private int filePath;//语音文件号
	private List<DevlinkSensor> sensorList = new ArrayList<DevlinkSensor>();
    private double temperature;//温度
    private double pressure;//压力
    private double wasi;//瓦斯浓度
    private double co;//一氧化碳浓度
    private double flow_work;//工况混合流量
    private double flow_standard;//标况混合流量
    private double flow_pure;//标况纯流量
	private String[] devname = new String[0];//被控设备别名、位置
    private int coId;
    private int methaneId;
    private String calibration;
    private int ismethane;
    private int upgrade3;
    private int upgrade2;
    private int upgrade1;
    private int position_type_id;
    private String position_type;
    private int area_sensor_id;
    private double upper_level1;
    private double upper_level2;
    private double upper_level3;
    private double upper_level4;
    private double floor_level1;
    private double floor_level2;
    private double floor_level3;
    private double floor_level4;
    private String keywords;
    private int m_area_id;
    private int s_area_id;
    private int islogical;
    private int direction;
    private double x2_point;
    private double y2_point;
    private double y3_point;
    private double x3_point;
    private double n2_point;
    private double e2_point;
    private double n3_point;
    private double e3_point;

    public double getN2_point() {
        return n2_point;
    }

    public void setN2_point(double n2_point) {
        this.n2_point = n2_point;
    }

    public double getE2_point() {
        return e2_point;
    }

    public void setE2_point(double e2_point) {
        this.e2_point = e2_point;
    }

    public double getN3_point() {
        return n3_point;
    }

    public void setN3_point(double n3_point) {
        this.n3_point = n3_point;
    }

    public double getE3_point() {
        return e3_point;
    }

    public void setE3_point(double e3_point) {
        this.e3_point = e3_point;
    }

    public double getX2_point() {
        return x2_point;
    }

    public void setX2_point(double x2_point) {
        this.x2_point = x2_point;
    }

    public double getY2_point() {
        return y2_point;
    }

    public void setY2_point(double y2_point) {
        this.y2_point = y2_point;
    }

    public double getY3_point() {
        return y3_point;
    }

    public void setY3_point(double y3_point) {
        this.y3_point = y3_point;
    }

    public double getX3_point() {
        return x3_point;
    }

    public void setX3_point(double x3_point) {
        this.x3_point = x3_point;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getIslogical() {
		return islogical;
	}

	public void setIslogical(int islogical) {
		this.islogical = islogical;
	}

	public int getM_area_id() {
        return m_area_id;
    }

    public void setM_area_id(int m_area_id) {
        this.m_area_id = m_area_id;
    }

    public int getS_area_id() {
        return s_area_id;
    }

    public void setS_area_id(int s_area_id) {
        this.s_area_id = s_area_id;
    }

    public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public double getUpper_level1() {
        return upper_level1;
    }

    public void setUpper_level1(double upper_level1) {
        this.upper_level1 = upper_level1;
    }

    public double getUpper_level2() {
        return upper_level2;
    }

    public void setUpper_level2(double upper_level2) {
        this.upper_level2 = upper_level2;
    }

    public double getUpper_level3() {
        return upper_level3;
    }

    public void setUpper_level3(double upper_level3) {
        this.upper_level3 = upper_level3;
    }

    public double getUpper_level4() {
        return upper_level4;
    }

    public void setUpper_level4(double upper_level4) {
        this.upper_level4 = upper_level4;
    }

    public double getFloor_level1() {
        return floor_level1;
    }

    public void setFloor_level1(double floor_level1) {
        this.floor_level1 = floor_level1;
    }

    public double getFloor_level2() {
        return floor_level2;
    }

    public void setFloor_level2(double floor_level2) {
        this.floor_level2 = floor_level2;
    }

    public double getFloor_level3() {
        return floor_level3;
    }

    public void setFloor_level3(double floor_level3) {
        this.floor_level3 = floor_level3;
    }

    public double getFloor_level4() {
        return floor_level4;
    }

    public void setFloor_level4(double floor_level4) {
        this.floor_level4 = floor_level4;
    }

    public int getArea_sensor_id() {
        return area_sensor_id;
    }

    public void setArea_sensor_id(int area_sensor_id) {
        this.area_sensor_id = area_sensor_id;
    }

    public int getPosition_type_id() {
        return position_type_id;
    }

    public void setPosition_type_id(int position_type_id) {
        this.position_type_id = position_type_id;
    }

    public String getPosition_type() {
        return position_type;
    }

    public void setPosition_type(String position_type) {
        this.position_type = position_type;
    }

    public int getUpgrade3() {
        return upgrade3;
    }

    public void setUpgrade3(int upgrade3) {
        this.upgrade3 = upgrade3;
    }

    public int getUpgrade2() {
        return upgrade2;
    }

    public void setUpgrade2(int upgrade2) {
        this.upgrade2 = upgrade2;
    }

    public int getUpgrade1() {
        return upgrade1;
    }

    public void setUpgrade1(int upgrade1) {
        this.upgrade1 = upgrade1;
    }

    public int getIsmethane() {
        return ismethane;
    }

    public void setIsmethane(int ismethane) {
        this.ismethane = ismethane;
    }

    public String getCalibration() {
        return calibration;
    }

    public void setCalibration(String calibration) {
        this.calibration = calibration;
    }

    public int getCoId() {
        return coId;
    }

    public void setCoId(int coId) {
        this.coId = coId;
    }

    public int getMethaneId() {
        return methaneId;
    }

    public void setMethaneId(int methaneId) {
        this.methaneId = methaneId;
    }




    
    public String getSimpleType() {
		return simpleType;
	}
	public void setSimpleType(String simpleType) {
		this.simpleType = simpleType;
	}
	public double getX_point() {
        return x_point;
    }
    public void setX_point(double x_point) {
        this.x_point = x_point;
    }

    public double getY_point() {
        return y_point;
    }

    public void setY_point(double y_point) {
        this.y_point = y_point;
    }

    public double getMiddle_rang() {
        return middle_rang;
    }

    public void setMiddle_rang(double middle_rang) {
        this.middle_rang = middle_rang;
    }

    public String getPath2() {
		return path2;
	}
	public void setPath2(String path2) {
		this.path2 = path2;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getStation() {
		return station;
	}
	public void setStation(int station) {
		this.station = station;
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
	public int getControl() {
		return control;
	}
	public void setControl(int control) {
		this.control = control;
	}
	public int getInput_type() {
		return input_type;
	}
	public void setInput_type(int input_type) {
		this.input_type = input_type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(int sensor_type) {
		this.sensor_type = sensor_type;
	}
	public String getType0x() {
		return type0x;
	}
	public void setType0x(String type0x) {
		this.type0x = type0x;
	}
	public double getMin_range() {
		return min_range;
	}
	public void setMin_range(double min_range) {
		this.min_range = min_range;
	}
	public double getMax_range() {
		return max_range;
	}
	public void setMax_range(double max_range) {
		this.max_range = max_range;
	}
	public double getMin_frequency() {
		return min_frequency;
	}
	public void setMin_frequency(double min_frequency) {
		this.min_frequency = min_frequency;
	}
	public double getMax_frequency() {
		return max_frequency;
	}
	public void setMax_frequency(double max_frequency) {
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
	public int getIscalc() {
		return iscalc;
	}
	public void setIscalc(int iscalc) {
		this.iscalc = iscalc;
	}
	public int getConfigsync() {
		return configsync;
	}
	public void setConfigsync(int configsync) {
		this.configsync = configsync;
	}
	public int getDrainageId() {
		return drainageId;
	}
	public void setDrainageId(int drainageId) {
		this.drainageId = drainageId;
	}
	public String getDrainageName() {
		return drainageName;
	}
	public void setDrainageName(String drainageName) {
		this.drainageName = drainageName;
	}
	public int getMenu() {
		return menu;
	}
	public void setMenu(int menu) {
		this.menu = menu;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getHasfloor() {
		return hasfloor;
	}
	public void setHasfloor(int hasfloor) {
		this.hasfloor = hasfloor;
	}
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public List<Coalmine> getCoalmines() {
		return coalmines;
	}
	public void setCoalmines(List<Coalmine> coalmines) {
		this.coalmines = coalmines;
	}
	public List<Double> getValues() {
		return values;
	}
	public void setValues(List<Double> values) {
		this.values = values;
	}
	public int getIsDrainage() {
		return isDrainage;
	}
	public void setIsDrainage(int isDrainage) {
		this.isDrainage = isDrainage;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getFilltime() {
		return filltime;
	}
	public void setFilltime(String filltime) {
		this.filltime = filltime;
	}
	public int getList_type() {
		return list_type;
	}
	public void setList_type(int list_type) {
		this.list_type = list_type;
	}
	public String getAlais() {
		return alais;
	}
	public void setAlais(String alais) {
		this.alais = alais;
	}
	public int getAlarm_status() {
		return alarm_status;
	}
	public void setAlarm_status(int alarm_status) {
		this.alarm_status = alarm_status;
	}
	public int getLinkageDeviceId() {
		return linkageDeviceId;
	}
	public void setLinkageDeviceId(int linkageDeviceId) {
		this.linkageDeviceId = linkageDeviceId;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public String getLogic_exps() {
		return logic_exps;
	}
	public void setLogic_exps(String logic_exps) {
		this.logic_exps = logic_exps;
	}
	public int getDevlinkId() {
		return devlinkId;
	}
	public void setDevlinkId(int devlinkId) {
		this.devlinkId = devlinkId;
	}
	public int getFloorDevlinkId() {
		return floorDevlinkId;
	}
	public void setFloorDevlinkId(int floorDevlinkId) {
		this.floorDevlinkId = floorDevlinkId;
	}
	public int getFloorLinkId() {
		return floorLinkId;
	}
	public void setFloorLinkId(int floorLinkId) {
		this.floorLinkId = floorLinkId;
	}
	public int getFlooraction() {
		return flooraction;
	}
	public void setFlooraction(int flooraction) {
		this.flooraction = flooraction;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public double getFluctuation_range() {
		return fluctuation_range;
	}
	public void setFluctuation_range(double fluctuation_range) {
		this.fluctuation_range = fluctuation_range;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getAomId() {
		return aomId;
	}
	public void setAomId(int aomId) {
		this.aomId = aomId;
	}
	public int getBroadcastId() {
		return broadcastId;
	}
	public void setBroadcastId(int broadcastId) {
		this.broadcastId = broadcastId;
	}
	public int getFilePath() {
		return filePath;
	}
	public void setFilePath(int filePath) {
		this.filePath = filePath;
	}
	public List<DevlinkSensor> getSensorList() {
		return sensorList;
	}
	public void setSensorList(List<DevlinkSensor> sensorList) {
		this.sensorList = sensorList;
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
    public String[] getDevname() {
		return devname;
	}
	public void setDevname(String[] devname) {
		this.devname = devname;
	}
	@Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
