package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.vo.DrainageRealTimeValue;

public interface IDrainageRealTimeValueDao {

	//查询瓦斯抽放实时值
	public List<DrainageRealTimeValue> getAllrealtimevalue(@Param("id")int id);
}
