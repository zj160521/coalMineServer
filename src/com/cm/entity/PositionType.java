package com.cm.entity;

public class PositionType {
    private int id;
    private String name;
    private double alarm;
    private double cut;
    private double repower;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAlarm() {
        return alarm;
    }

    public void setAlarm(double alarm) {
        this.alarm = alarm;
    }

    public double getCut() {
        return cut;
    }

    public void setCut(double cut) {
        this.cut = cut;
    }

    public double getRepower() {
        return repower;
    }

    public void setRepower(double repower) {
        this.repower = repower;
    }
}
