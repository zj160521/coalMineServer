package com.cm.security;

import java.io.FileInputStream;
import java.util.Properties;

import util.LogOut;

public class ConfigUtil {

	private static ConfigUtil instance = null;
	
	private String kafka = "192.168.0.124:9092";
	private String redis_ip = "192.168.0.124";
	private String redis_port = "6379";
	private String zookeeper = "192.168.0.124:2181";
	private String sys_ip = "192.168.0.231";
	private String sys_peerip = "192.168.0.232";
	private String db_user = "root";
	private String db_pass = "123456";
	public static ConfigUtil getInstance() {
	    if (instance == null) {
	        synchronized (ConfigUtil.class) {
	            if (instance == null){
	            	ConfigUtil t = new ConfigUtil();
	                instance = t;
	            }
	        }
	    }
	    return instance;
	}
	
	private ConfigUtil() {
        Properties props = new Properties();
        try {
        	FileInputStream in = new FileInputStream("/etc/mineserver.cfg");
        	props.load(in);
        	in.close();
        	
        	String kip = props.getProperty("kafka.ip", "192.168.0.124");
        	String kport = props.getProperty("kafka.port", "9092");
        	
        	kafka = kip + ":" + kport;
        	
        	redis_ip = props.getProperty("redis.ip", "192.168.0.124");
        	redis_port = props.getProperty("redis.port", "6379");
        	sys_ip=props.getProperty("sys.ip", "192.168.0.231");
        	sys_peerip=props.getProperty("sys.peerip", "192.168.0.232");
        	
        	db_user = props.getProperty("db.username", "coalmine");
        	db_pass = props.getProperty("db.password", "!@#qwe");
        	
        	String zip = props.getProperty("zookeeper.ip", "192.168.0.124");
        	String zport = props.getProperty("zookeeper.port", "2181");
        	
        	zookeeper = zip + ":" + zport;
            
        } catch (Exception e) {
            LogOut.log.debug("/etc/mineserver.cfg not found.");
        }
    }


	public String getKafka() {
		return kafka;
	}

	public String getRedis_ip() {
		return redis_ip;
	}

	public String getRedis_port() {
		return redis_port;
	}

	public String getZookeeper() {
		return zookeeper;
	}

	public String getSys_ip() {
		return sys_ip;
	}

	public void setSys_ip(String sys_ip) {
		this.sys_ip = sys_ip;
	}

	public String getSys_peerip() {
		return sys_peerip;
	}

	public void setSys_peerip(String sys_peerip) {
		this.sys_peerip = sys_peerip;
	}

	public String getDb_user() {
		return db_user;
	}

	public void setDb_user(String db_user) {
		this.db_user = db_user;
	}

	public String getDb_pass() {
		return db_pass;
	}

	public void setDb_pass(String db_pass) {
		this.db_pass = db_pass;
	}
	
}
