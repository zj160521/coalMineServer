package com.cm.entity;

import com.cm.entity.vo.DevlinkSensor;

import java.util.ArrayList;
import java.util.List;

public class SwitchSensor {

	private int id;
	private int pid;
	private int station;//分站id
	private String station_name;//分站名称
	private String ipaddr;//分站ip地址
	private int sensorId;//设备编号
	private int control;//是否参与控制 0表示不参与控制，1表示参与控制
	private int input_type;//设备输入类型 0表示2态 1表示三态
	private int sensor_type;//设备类型id
	private String type;//设备类型
	private String type0x;//十六进制设备类型
	private int sensor_position;//位置id
	private String position;//设备位置信息
	private Double n_point;//经度
	private Double e_point;//纬度
	private int alarm_status;//0或者1 表示触发报警时的状态
	private int power_status;//开关量断电状态 0表示断开，1表示接通
	private int break_display;//断开显示id
	private String break_d;//断开显示 //前端不传值
	private int connect_display;//接通显示id
	private String connect_d;//接通显示 //前端不传值
	private int configsync;//是否同步
	private int menu;
	private int area_id;
	private String areaname;
	private String path;
	private int isDrainage;
    private int list_type;
    private int term;//选择设备是否参与联动 1 是 0 否
    private int linkageDeviceId;//联动设备的Id 断电控制器或者断馈电仪
    private int action;// 0或者1 表示触发联动时联动设备需要执行的命令
    private int feedId;//联动设备选择断电控制器时，需要存入 馈电器Id
    private int devlinkId;//联动步骤ID
    private String alais;
    private String path2;
    private int hasfloor;
    private String uid;
    private int drainageId;
    private double x_point;
    private double y_point;
	private int filePath;//语音文件号
    private int position_type_id;
    private String position_type;
    private int islogical;
    private String power_scope;
    private int direction;
    private String device_name;
	private List<DevlinkSensor> sensorList = new ArrayList<DevlinkSensor>();
	private String[] devname = new String[0];//被控设备别名、位置
    private double x2_point;
    private double y2_point;
    private double x3_point;
    private double y3_point;
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

    public double getX3_point() {
        return x3_point;
    }

    public void setX3_point(double x3_point) {
        this.x3_point = x3_point;
    }

    public double getY3_point() {
        return y3_point;
    }

    public void setY3_point(double y3_point) {
        this.y3_point = y3_point;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getPower_scope() {
        return power_scope;
    }

    public void setPower_scope(String power_scope) {
        this.power_scope = power_scope;
    }

    public int getIslogical() {
		return islogical;
	}

	public void setIslogical(int islogical) {
		this.islogical = islogical;
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

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public int getDrainageId() {
        return drainageId;
    }

    public void setDrainageId(int drainageId) {
        this.drainageId = drainageId;
    }

    public int getHasfloor() {
		return hasfloor;
	}
	public void setHasfloor(int hasfloor) {
		this.hasfloor = hasfloor;
	}
	public String getPath2() {
		return path2;
	}
	public void setPath2(String path2) {
		this.path2 = path2;
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
	public String getType0x() {
		return type0x;
	}
	public void setType0x(String type0x) {
		this.type0x = type0x;
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
	public Double getN_point() {
		return n_point;
	}
	public void setN_point(Double n_point) {
		this.n_point = n_point;
	}
	public Double getE_point() {
		return e_point;
	}
	public void setE_point(Double e_point) {
		this.e_point = e_point;
	}
	public int getAlarm_status() {
		return alarm_status;
	}
	public void setAlarm_status(int alarm_status) {
		this.alarm_status = alarm_status;
	}
	public int getPower_status() {
		return power_status;
	}
	public void setPower_status(int power_status) {
		this.power_status = power_status;
	}
	public int getBreak_display() {
		return break_display;
	}
	public void setBreak_display(int break_display) {
		this.break_display = break_display;
	}
	public String getBreak_d() {
		return break_d;
	}
	public void setBreak_d(String break_d) {
		this.break_d = break_d;
	}
	public int getConnect_display() {
		return connect_display;
	}
	public void setConnect_display(int connect_display) {
		this.connect_display = connect_display;
	}
	public String getConnect_d() {
		return connect_d;
	}
	public void setConnect_d(String connect_d) {
		this.connect_d = connect_d;
	}
	public int getConfigsync() {
		return configsync;
	}
	public void setConfigsync(int configsync) {
		this.configsync = configsync;
	}
	public int getMenu() {
		return menu;
	}
	public void setMenu(int menu) {
		this.menu = menu;
	}
	public int getArea_id() {
		return area_id;
	}
	public void setArea_id(int area_id) {
		this.area_id = area_id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getIsDrainage() {
		return isDrainage;
	}
	public void setIsDrainage(int isDrainage) {
		this.isDrainage = isDrainage;
	}
	public int getList_type() {
		return list_type;
	}
	public void setList_type(int list_type) {
		this.list_type = list_type;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
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
	public int getFeedId() {
		return feedId;
	}
	public void setFeedId(int feedId) {
		this.feedId = feedId;
	}
	public int getDevlinkId() {
		return devlinkId;
	}
	public void setDevlinkId(int devlinkId) {
		this.devlinkId = devlinkId;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
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

	public String[] getDevname() {
		return devname;
	}

	public void setDevname(String[] devname) {
		this.devname = devname;
	}
	
}
