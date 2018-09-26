package com.cm.entity;

public class Filecontent {

    private int id;
    private String filename;
    private String str;
    private String filltime;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getFilltime() {
        return filltime;
    }

    public void setFilltime(String filltime) {
        this.filltime = filltime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
