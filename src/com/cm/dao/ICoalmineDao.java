package com.cm.dao;

import com.cm.entity.Coalmine;
import com.cm.entity.vo.CoalmineVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICoalmineDao {
	public List<CoalmineVo> getDataByTime(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("tableName") String tableName);
	public void insert(Coalmine coal);
	public void insertList(List<Coalmine> list);
}
