package com.cm.entity;

public class Files implements Comparable<Files>{
    private String name;
    private String uncryptpath;
    private String encryptpath;
    private String logpath;
    private String ftp_ip;
    private String ftp_port;

    public String getFtp_ip() {
        return ftp_ip;
    }

    public void setFtp_ip(String ftp_ip) {
        this.ftp_ip = ftp_ip;
    }

    public String getFtp_port() {
        return ftp_port;
    }

    public void setFtp_port(String ftp_port) {
        this.ftp_port = ftp_port;
    }

    public String getLogpath() {
        return logpath;
    }

    public void setLogpath(String logpath) {
        this.logpath = logpath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUncryptpath() {
        return uncryptpath;
    }

    public void setUncryptpath(String uncryptpath) {
        this.uncryptpath = uncryptpath;
    }

    public String getEncryptpath() {
        return encryptpath;
    }

    public void setEncryptpath(String encryptpath) {
        this.encryptpath = encryptpath;
    }

    @Override
    public int compareTo(Files o) {
        return this.name.compareTo(o.name);
    }
}
