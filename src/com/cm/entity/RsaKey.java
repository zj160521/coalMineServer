package com.cm.entity;

public class RsaKey {

	private int id;
	private String filename;
	private String encryptKey;
	private String undecryptKey;
	private String filltime;
	
	public String getUndecryptKey() {
		return undecryptKey;
	}
	public void setUndecryptKey(String undecryptKey) {
		this.undecryptKey = undecryptKey;
	}
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
	public String getEncryptKey() {
		return encryptKey;
	}
	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}
	public String getFilltime() {
		return filltime;
	}
	public void setFilltime(String filltime) {
		this.filltime = filltime;
	}
	
	
}
