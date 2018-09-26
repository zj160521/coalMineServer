package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.GD5;

public interface IGD5Dao {

	public List<GD5> getAll(@Param("starttime")String startime,@Param("endtime")String endtime,@Param("ip")String ip,@Param("devid")int devid);
	
}
