package com.cm.entity;

public class Cumulant {

    private int id;
    private int sensor_id;
    private String ip;
    private int sensorId;
    private int type;
    private double flow_work;
    private double flow_standard;
    private double flow_pure;
    private int status;//1表示总累计量，2表示年累计量，3表示月累计量，4表示日累计量
    private String responsetime;
    private int sensor_position;
    private String position;
    private String alais;

    public String getAlais() {
        return alais;
    }

    public void setAlais(String alais) {
        this.alais = alais;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSensor_position() {
        return sensor_position;
    }

    public void setSensor_position(int sensor_position) {
        this.sensor_position = sensor_position;
    }

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResponsetime() {
        return responsetime;
    }

    public void setResponsetime(String responsetime) {
        this.responsetime = responsetime;
    }
}
