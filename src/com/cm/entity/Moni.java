package com.cm.entity;

import java.util.ArrayList;
import java.util.List;

public class Moni {

	private int id;
	private int pid;
	private String dip;
	private String port;
	private String username;
	private String password;
	private String position;
	private List<Moni> list = new ArrayList<Moni>();
	private int isuse;
	private String name;
	private int type;
	private double n_point;
	private double e_point;

	public double getN_point() {
		return n_point;
	}

	public void setN_point(double n_point) {
		this.n_point = n_point;
	}

	public double getE_point() {
		return e_point;
	}

	public void setE_point(double e_point) {
		this.e_point = e_point;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Moni> getList() {
		return list;
	}

	public void setList(List<Moni> list) {
		this.list = list;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getDip() {
		return dip;
	}

	public void setDip(String dip) {
		this.dip = dip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getIsuse() {
		return isuse;
	}

	public void setIsuse(int isuse) {
		this.isuse = isuse;
	}
}
