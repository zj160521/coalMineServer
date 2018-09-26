package com.cm.dao;

import com.cm.entity.DevAction;
import com.cm.entity.DevLink;
import com.cm.entity.DevLogicGroup;
import com.cm.entity.vo.BasicDevLinkVo;
import com.cm.entity.vo.DevLinkVo;
import com.cm.entity.vo.DevLogicVo;
import com.cm.entity.vo.DevlinksVo;
import com.cm.entity.vo.GroupLgc;

import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DevLinkDao {
	
	public void addDevLink(List<DevLink> list);
	
	public void addDevLgcGroup(List<DevLogicGroup> list);
	
	public int addLgcGroup(DevLinkVo dlv);
	
	public void updateLgcGroup(DevLinkVo dlv);
	
	public void addDevLgc(List<DevLogicVo> list);
	
	public void updateDevLgc(List<DevLogicVo> list);
	
	public void addDevAction(List<DevAction> list);
	
	public void updateDevAction(List<DevAction> list);
	
	public void addBasisDevLink(@Param("list")List<DevLink> list);
	
	public List<DevLink> getBasisDevlink();
	
	public void updateBasisDevlink(DevLink link);
	
	public void deleteBasisDevlink(DevLink link);
	
	public List<GroupLgc> getAllGroupLgc();
	
	public List<DevAction> getAllAct();
	
	public void delGroup(List<Integer> list);
	
	public void delDecAction(int id);
	
	public void delLgc(int id);
	
	public void delActByGrp(int grpId, int type);

	public List<DevLink> getAllDevLink();
	
	public void deletelgcgroup(@Param("pid")int pid);
	
	public List<DevLink> getDevLinkById(@Param("id")int id,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	public BasicDevLinkVo getBasicDevLinkById(@Param("id") int id);
	
	@SuppressWarnings("rawtypes")
	public List<DevlinksVo> getDevLinkVos();
	
	public List<String> getbygoupid(@Param("pid")int pid);
	
	public List<String> getCutDevScope(@Param("id")int id,@Param("ip")String ip);
}
