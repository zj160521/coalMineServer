package com.cm.entity.vo;

public class KafkaMessage implements Cloneable {
    @Override
    public String toString() {
        return "KafkaMessage [msgid=" + msgid + ", cmd=" + cmd + ", lifesecond=" + lifesecond + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private int msgid;
    private int id;
    private String ip;
    private int type;
    private int cmd;
    private int calltype;
    private int card1;
    private int card2;
    private int card3;
    private int station;
    private Integer lifesecond = 0;
    private Double max_range;
    private Double min_range;
    private Double min_frequency;
    private Double max_frequency;
    private Double error_band;
    private Double limit_warning;
    private Double floor_warning;
    private Double limit_alarm;
    private Double floor_alarm;
    private Double limit_power;
    private Double floor_power;
    private Double limit_repower;
    private Double floor_repower;
    private Integer alarm_status;
    private Integer is_power;
    private Integer status;
    private String response;
    private String desp;
    private Integer objtype;
    private String sdata;
    private String rdata;
    private int correct;
    private int node;
    private int amount;
    private int switch_oper;
    private int sound;
    private int volume;
    private long sendtime = 0;

    public long getSendtime() {
        return sendtime;
    }

    public void setSendtime(long sendtime) {
        this.sendtime = sendtime;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getSwitch_oper() {
        return switch_oper;
    }

    public void setSwitch_oper(int switch_oper) {
        this.switch_oper = switch_oper;
    }

    public int getMsgid() {
        return msgid;
    }

    public void setMsgid(int msgid) {
        this.msgid = msgid;
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

    public int getType() {
        return type;
    }

    public void plusLifesecond(int offset) {
        this.lifesecond += offset;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public Integer getLifesecond() {
        return lifesecond;
    }

    public void setLifesecond(Integer lifesecond) {
        this.lifesecond = lifesecond;
    }

    public Double getMax_range() {
        return max_range;
    }

    public void setMax_range(Double max_range) {
        this.max_range = max_range;
    }

    public Double getMin_range() {
        return min_range;
    }

    public void setMin_range(Double min_range) {
        this.min_range = min_range;
    }

    public Double getMin_frequency() {
        return min_frequency;
    }

    public void setMin_frequency(Double min_frequency) {
        this.min_frequency = min_frequency;
    }

    public Double getMax_frequency() {
        return max_frequency;
    }

    public void setMax_frequency(Double max_frequency) {
        this.max_frequency = max_frequency;
    }

    public Double getError_band() {
        return error_band;
    }

    public void setError_band(Double error_band) {
        this.error_band = error_band;
    }

    public Double getLimit_warning() {
        return limit_warning;
    }

    public void setLimit_warning(Double limit_warning) {
        this.limit_warning = limit_warning;
    }

    public Double getFloor_warning() {
        return floor_warning;
    }

    public void setFloor_warning(Double floor_warning) {
        this.floor_warning = floor_warning;
    }

    public Double getLimit_alarm() {
        return limit_alarm;
    }

    public void setLimit_alarm(Double limit_alarm) {
        this.limit_alarm = limit_alarm;
    }

    public Double getFloor_alarm() {
        return floor_alarm;
    }

    public void setFloor_alarm(Double floor_alarm) {
        this.floor_alarm = floor_alarm;
    }

    public Double getLimit_power() {
        return limit_power;
    }

    public void setLimit_power(Double limit_power) {
        this.limit_power = limit_power;
    }

    public Double getFloor_power() {
        return floor_power;
    }

    public void setFloor_power(Double floor_power) {
        this.floor_power = floor_power;
    }

    public Double getLimit_repower() {
        return limit_repower;
    }

    public void setLimit_repower(Double limit_repower) {
        this.limit_repower = limit_repower;
    }

    public Double getFloor_repower() {
        return floor_repower;
    }

    public void setFloor_repower(Double floor_repower) {
        this.floor_repower = floor_repower;
    }

    public Integer getAlarm_status() {
        return alarm_status;
    }

    public void setAlarm_status(Integer alarm_status) {
        this.alarm_status = alarm_status;
    }

    public Integer getIs_power() {
        return is_power;
    }

    public void setIs_power(Integer is_power) {
        this.is_power = is_power;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public Integer getObjtype() {
        return objtype;
    }

    public void setObjtype(Integer objtype) {
        this.objtype = objtype;
    }

    public String getSdata() {
        return sdata;
    }

    public void setSdata(String sdata) {
        this.sdata = sdata;
    }

    public String getRdata() {
        return rdata;
    }

    public void setRdata(String rdata) {
        this.rdata = rdata;
    }

    public int getCalltype() {
        return calltype;
    }

    public void setCalltype(int calltype) {
        this.calltype = calltype;
    }

    public int getCard1() {
        return card1;
    }

    public void setCard1(int card1) {
        this.card1 = card1;
    }

    public int getCard2() {
        return card2;
    }

    public void setCard2(int card2) {
        this.card2 = card2;
    }

    public int getCard3() {
        return card3;
    }

    public void setCard3(int card3) {
        this.card3 = card3;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

}
