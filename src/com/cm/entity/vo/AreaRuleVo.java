package com.cm.entity.vo;

import java.util.List;

import com.cm.entity.AreaRule;

public class AreaRuleVo {
	private int areaId;
	private String areaName;
	private String defaultAllow;//限制区域
	private String emphasis;//重点区域
	private List<AreaRule> ruleList;
	
	public String getDefaultAllow() {
		return defaultAllow;
	}
	public void setDefaultAllow(String defaultAllow) {
		this.defaultAllow = defaultAllow;
	}
	public String getEmphasis() {
		return emphasis;
	}
	public void setEmphasis(String emphasis) {
		this.emphasis = emphasis;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public List<AreaRule> getRuleList() {
		return ruleList;
	}
	public void setRuleList(List<AreaRule> ruleList) {
		this.ruleList = ruleList;
	}
	
	
}
