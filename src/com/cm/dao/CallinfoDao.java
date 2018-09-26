package com.cm.dao;

import java.util.List;

import com.cm.entity.Callinfo;
import com.cm.entity.Helpme;
import com.cm.entity.vo.NameTime;

public interface CallinfoDao {
	
	//添加呼叫记录
	public void addCallinfo(Callinfo callinfo);
	
	//查询符合条件的所有呼叫记录
	public List<Callinfo> getallCallinfo(Callinfo callinfo);
	
	//查询最新的的呼叫记录
	public List<Callinfo> getnewCallinfo(NameTime nt);
	
	//查询最新的的求救记录
	public List<Helpme> getHelp(NameTime nt);
	
	//查询正在呼叫的数据记录
	public List<Callinfo> getUnfinish();
	
	//更新呼叫记录结束时间
	public void stopCallinfo(int id);
	
	//查询数据总条数
	public int getAllPage(NameTime nt);
	
	//查询数据总条数
	public int getAllPage2(NameTime nt);
	
	//更新呼叫记录结束时间
	public void helpRemark(NameTime nt);
}
