package com.cm.entity;

import java.util.ArrayList;
import java.util.List;


public class Substation<T> {

	private Integer id;
	private String station_name;//分站名称
	private String ipaddr;//分站ip
	private Double n_point;//经度
	private Double e_point;//纬度
	private int positionId;
	private String position;
	private String path;
	private int type;
	private List<Sensor> sensors;//模拟量传感器列表
	private List<SwitchSensor> switches;//开关量传感器列表
	private List<Cardreder> cardreders = new ArrayList<Cardreder>();
	private List<T> lists = new ArrayList<T>();
	private String label;
	private String alais;
	private String uid;
	private double x_point;
	private double y_point;
	private String start_adr;
	private String sys_info;
	private int chksum_len;
	private int chksum;
	private String s_ip;
	private String s_nm;
	private String s_gw;
	private String dns_ip;
	private String ser_ip;
	private int ser_port;
	private String dev_en;
	private String send_msg_time;
	private int send_msg_time1;
	private int send_msg_time2;
	private int send_msg_time3;
	private String rcv;
	private int dev_id;
	private int can1_baud_rate;
	private int can2_baud_rate;
	private int rs485_baud_rate;
	private int can1_mount_cnt;
	private int can2_mount_cnt;
	private int rs485_mount_cnt;
	private int rs485_led;

    public int getSend_msg_time1() {
        return send_msg_time1;
    }

    public void setSend_msg_time1(int send_msg_time1) {
        this.send_msg_time1 = send_msg_time1;
    }

    public int getSend_msg_time2() {
        return send_msg_time2;
    }

    public void setSend_msg_time2(int send_msg_time2) {
        this.send_msg_time2 = send_msg_time2;
    }

    public int getSend_msg_time3() {
        return send_msg_time3;
    }

    public void setSend_msg_time3(int send_msg_time3) {
        this.send_msg_time3 = send_msg_time3;
    }

    public int getCan1_mount_cnt() {
        return can1_mount_cnt;
    }

    public void setCan1_mount_cnt(int can1_mount_cnt) {
        this.can1_mount_cnt = can1_mount_cnt;
    }

    public int getCan2_mount_cnt() {
        return can2_mount_cnt;
    }

    public void setCan2_mount_cnt(int can2_mount_cnt) {
        this.can2_mount_cnt = can2_mount_cnt;
    }

    public int getRs485_mount_cnt() {
        return rs485_mount_cnt;
    }

    public void setRs485_mount_cnt(int rs485_mount_cnt) {
        this.rs485_mount_cnt = rs485_mount_cnt;
    }

    public String getStart_adr() {
        return start_adr;
    }

    public void setStart_adr(String start_adr) {
        this.start_adr = start_adr;
    }

    public String getSys_info() {
        return sys_info;
    }

    public void setSys_info(String sys_info) {
        this.sys_info = sys_info;
    }

    public int getChksum_len() {
        return chksum_len;
    }

    public void setChksum_len(int chksum_len) {
        this.chksum_len = chksum_len;
    }

    public int getChksum() {
        return chksum;
    }

    public void setChksum(int chksum) {
        this.chksum = chksum;
    }

    public String getS_ip() {
        return s_ip;
    }

    public void setS_ip(String s_ip) {
        this.s_ip = s_ip;
    }

    public String getS_nm() {
        return s_nm;
    }

    public void setS_nm(String s_nm) {
        this.s_nm = s_nm;
    }

    public String getS_gw() {
        return s_gw;
    }

    public void setS_gw(String s_gw) {
        this.s_gw = s_gw;
    }

    public String getDns_ip() {
        return dns_ip;
    }

    public void setDns_ip(String dns_ip) {
        this.dns_ip = dns_ip;
    }

    public String getSer_ip() {
        return ser_ip;
    }

    public void setSer_ip(String ser_ip) {
        this.ser_ip = ser_ip;
    }

    public int getSer_port() {
        return ser_port;
    }

    public void setSer_port(int ser_port) {
        this.ser_port = ser_port;
    }

    public String getDev_en() {
        return dev_en;
    }

    public void setDev_en(String dev_en) {
        this.dev_en = dev_en;
    }

    public String getSend_msg_time() {
        return send_msg_time;
    }

    public void setSend_msg_time(String send_msg_time) {
        this.send_msg_time = send_msg_time;
    }

    public String getRcv() {
        return rcv;
    }

    public void setRcv(String rcv) {
        this.rcv = rcv;
    }

    public int getDev_id() {
        return dev_id;
    }

    public void setDev_id(int dev_id) {
        this.dev_id = dev_id;
    }

    public int getCan1_baud_rate() {
        return can1_baud_rate;
    }

    public void setCan1_baud_rate(int can1_baud_rate) {
        this.can1_baud_rate = can1_baud_rate;
    }

    public int getCan2_baud_rate() {
        return can2_baud_rate;
    }

    public void setCan2_baud_rate(int can2_baud_rate) {
        this.can2_baud_rate = can2_baud_rate;
    }

    public int getRs485_baud_rate() {
        return rs485_baud_rate;
    }

    public void setRs485_baud_rate(int rs485_baud_rate) {
        this.rs485_baud_rate = rs485_baud_rate;
    }

    public int getRs485_led() {
        return rs485_led;
    }

    public void setRs485_led(int rs485_led) {
        this.rs485_led = rs485_led;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAlais() {
        return alais;
    }

    public void setAlais(String alais) {
        this.alais = alais;
    }

    public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public List<Sensor> getSensors() {
		return sensors;
	}
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
	public List<SwitchSensor> getSwitches() {
		return switches;
	}
	public void setSwitches(List<SwitchSensor> switches) {
		this.switches = switches;
	}
	public List<Cardreder> getCardreders() {
		return cardreders;
	}
	public void setCardreders(List<Cardreder> cardreders) {
		this.cardreders = cardreders;
	}
	public List<T> getLists() {
		return lists;
	}
	public void setLists(List<T> lists) {
		this.lists = lists;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getPositionId() {
		return positionId;
	}
	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	
}
