package com.cm.entity;

import java.util.ArrayList;
import java.util.List;

public class EnvArea {

    private int id;//区域id
    private String areaname;//区域名称
    private int max_allow;//最大允许人数
    private int max_time;//允许时长
    private int default_allow;//是否为限制区域，1非限制区域，2限制区域
    private int is_exit;//是否为出入口
    private String remark;//区域说明
    private int emphasis;//是否为限制区域，1非重点区域即为普通区域，2重点区域
    private int worker_timeout;//井下人员超时时长
    private String adjoin;
    private String lineString;
    private int typeid;
    private int area_type_id;
    private String area_type_name;
    private String path;
    private int m_area_id;
    private int s_area_id;
    private int type;
    private int area_sensor_id;
    private List<Area> areas = new ArrayList<>();
    private List<Sensor> sensors = new ArrayList<>();
    private List<SwitchSensor> switches;
    private List<Object> list;
    private List<Cardreder> cardreders;//读卡器集合
    private List<Worker> workers;//员工集合
    private List<Sensor> allsensors;
    private List<AreaRule> areaRuleList;//区域规则集合
    private List<AreaSensor> areaSensors;
    private List<Integer> adjoinAreaIds;
    private double n_point;
    private double e_point;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public int getMax_allow() {
        return max_allow;
    }

    public void setMax_allow(int max_allow) {
        this.max_allow = max_allow;
    }

    public int getMax_time() {
        return max_time;
    }

    public void setMax_time(int max_time) {
        this.max_time = max_time;
    }

    public int getDefault_allow() {
        return default_allow;
    }

    public void setDefault_allow(int default_allow) {
        this.default_allow = default_allow;
    }

    public int getIs_exit() {
        return is_exit;
    }

    public void setIs_exit(int is_exit) {
        this.is_exit = is_exit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getEmphasis() {
        return emphasis;
    }

    public void setEmphasis(int emphasis) {
        this.emphasis = emphasis;
    }

    public int getWorker_timeout() {
        return worker_timeout;
    }

    public void setWorker_timeout(int worker_timeout) {
        this.worker_timeout = worker_timeout;
    }

    public String getAdjoin() {
        return adjoin;
    }

    public void setAdjoin(String adjoin) {
        this.adjoin = adjoin;
    }

    public String getLineString() {
        return lineString;
    }

    public void setLineString(String lineString) {
        this.lineString = lineString;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getArea_type_id() {
        return area_type_id;
    }

    public void setArea_type_id(int area_type_id) {
        this.area_type_id = area_type_id;
    }

    public String getArea_type_name() {
        return area_type_name;
    }

    public void setArea_type_name(String area_type_name) {
        this.area_type_name = area_type_name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getArea_sensor_id() {
        return area_sensor_id;
    }

    public void setArea_sensor_id(int area_sensor_id) {
        this.area_sensor_id = area_sensor_id;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
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

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public List<Cardreder> getCardreders() {
        return cardreders;
    }

    public void setCardreders(List<Cardreder> cardreders) {
        this.cardreders = cardreders;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public List<Sensor> getAllsensors() {
        return allsensors;
    }

    public void setAllsensors(List<Sensor> allsensors) {
        this.allsensors = allsensors;
    }

    public List<AreaRule> getAreaRuleList() {
        return areaRuleList;
    }

    public void setAreaRuleList(List<AreaRule> areaRuleList) {
        this.areaRuleList = areaRuleList;
    }

    public List<AreaSensor> getAreaSensors() {
        return areaSensors;
    }

    public void setAreaSensors(List<AreaSensor> areaSensors) {
        this.areaSensors = areaSensors;
    }

    public List<Integer> getAdjoinAreaIds() {
        return adjoinAreaIds;
    }

    public void setAdjoinAreaIds(List<Integer> adjoinAreaIds) {
        this.adjoinAreaIds = adjoinAreaIds;
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
}
