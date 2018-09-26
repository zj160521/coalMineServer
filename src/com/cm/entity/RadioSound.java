package com.cm.entity;

public class RadioSound {
	private int id;
	private int radio_id;
	private String radioName;
	private int user_id;
	private String userName;
	private String sound_path;
	private String filltime;
	private String filename;
	
	public String getRadioName() {
		return radioName;
	}
	public void setRadioName(String radioName) {
		this.radioName = radioName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRadio_id() {
		return radio_id;
	}
	public void setRadio_id(int radio_id) {
		this.radio_id = radio_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getSound_path() {
		return sound_path;
	}
	public void setSound_path(String sound_path) {
		this.sound_path = sound_path;
	}
	public String getFilltime() {
		return filltime;
	}
	public void setFilltime(String filltime) {
		this.filltime = filltime;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}
