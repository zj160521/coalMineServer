package com.cm.entity;

public class Video {

	private Integer id;
	private Integer pid;
	private Integer recorderid;
	private String dip;
	private String position;
	private String name;
	private Double n_point;
	private Double e_point;
	private int list_type;

	public int getList_type() {
		return list_type;
	}

	public void setList_type(int list_type) {
		this.list_type = list_type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getRecorderid() {
		return recorderid;
	}

	public void setRecorderid(Integer recorderid) {
		this.recorderid = recorderid;
	}

	public String getDip() {
		return dip;
	}

	public void setDip(String dip) {
		this.dip = dip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getN_point() {
		return n_point;
	}

	public void setN_point(Double n_point) {
		this.n_point = n_point;
	}

	public Double getE_point() {
		return e_point;
	}

	public void setE_point(Double e_point) {
		this.e_point = e_point;
	}

}
