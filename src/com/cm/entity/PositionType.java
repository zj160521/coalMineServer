package com.cm.entity;

public class PositionType {
    private int id;
    private String name;
    private double alarm;
    private double cut;
    private double repower;
    private String alarm_operator;
    private String power_operator;
    private String repower_operator;
    private String valueText;

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public String getAlarm_operator() {
        return alarm_operator;
    }

    public void setAlarm_operator(String alarm_operator) {
        this.alarm_operator = alarm_operator;
    }

    public String getPower_operator() {
        return power_operator;
    }

    public void setPower_operator(String power_operator) {
        this.power_operator = power_operator;
    }

    public String getRepower_operator() {
        return repower_operator;
    }

    public void setRepower_operator(String repower_operator) {
        this.repower_operator = repower_operator;
    }

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
