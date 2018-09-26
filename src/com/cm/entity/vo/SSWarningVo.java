package com.cm.entity.vo;

import java.util.List;

public class SSWarningVo {
    private int sensor_position;//位置id
    private String position;//位置
    private String alais;//别名
    private String countTime;//累计时间
    private int totalnum;//累计次数
    private int measureId;//报警措施id
    private String measure;//措施内容
    private String measureTime;//采取措施时间
    private List<SSWarning> list;//所有报警
    private String type;//设备类型
    private int Sensor_type;//设备类型id
    private int atype;
    private String powerArea;//断电区域
    private int sensor_id;//设备id
    private List<String> first;
    private List<String> second;

    public List<String> getFirst() {
        return first;
    }

    public void setFirst(List<String> first) {
        this.first = first;
    }

    public List<String> getSecond() {
        return second;
    }

    public void setSecond(List<String> second) {
        this.second = second;
    }

    public int getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(int sensor_id) {
        this.sensor_id = sensor_id;
    }

    public String getPowerArea() {
        return powerArea;
    }

    public void setPowerArea(String powerArea) {
        this.powerArea = powerArea;
    }

    public int getAtype() {
        return atype;
    }

    public void setAtype(int atype) {
        this.atype = atype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSensor_type() {
        return Sensor_type;
    }

    public void setSensor_type(int sensor_type) {
        Sensor_type = sensor_type;
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

    public String getAlais() {
        return alais;
    }

    public void setAlais(String alais) {
        this.alais = alais;
    }

    public String getCountTime() {
        return countTime;
    }

    public void setCountTime(String countTime) {
        this.countTime = countTime;
    }

    public int getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(int totalnum) {
        this.totalnum = totalnum;
    }

    public int getMeasureId() {
        return measureId;
    }

    public void setMeasureId(int measureId) {
        this.measureId = measureId;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(String measureTime) {
        this.measureTime = measureTime;
    }

    public List<SSWarning> getList() {
        return list;
    }

    public void setList(List<SSWarning> list) {
        this.list = list;
    }
}
