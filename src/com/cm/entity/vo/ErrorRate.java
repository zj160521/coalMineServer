package com.cm.entity.vo;

import java.util.List;

public class ErrorRate {

	private Integer percent;
	private List<KafkaMessage> list;
	public Integer getPercent() {
		return percent;
	}
	public void setPercent(Integer percent) {
		this.percent = percent;
	}
	public List<KafkaMessage> getList() {
		return list;
	}
	public void setList(List<KafkaMessage> list) {
		this.list = list;
	}
	
}
