package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.Biterror;
import com.cm.entity.BiterrorResult;

public interface IBiterrorDao {
	
	//查询一个设备的所有误码信息
	public List<Biterror> getBydev(@Param("resultId")int resultId);
	
	//添加设备的误码信息
	public void add(Biterror bit);
	
	//查询某个设备的误码信息条数
	public int count(@Param("resultId")int resultId);
	
	//查询某个设备误码信息的最小id
	public int minimumId(@Param("resultId")int resultId);
	
	//根据id删除
	public void delete(@Param("id")int id);
	
	//根据设备删除数据
	public void deleteBydevice(@Param("resultId")int resultId);
	
	//查询设备的结果信息
	public BiterrorResult getdevice(BiterrorResult result);
	
	//添加设备的结果信息
	public void addBiterrorResult(BiterrorResult result);
	
	//更新设备的结果信息
	public void updateBiterrorResult(BiterrorResult result);
	
	public void batchadd(List<Biterror> list);

}
