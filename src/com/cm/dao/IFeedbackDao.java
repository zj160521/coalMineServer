package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.Feedback;
import com.cm.entity.vo.CutFeedVo;

public interface IFeedbackDao {
	// 获取断电状态
	public List<Feedback> getCutStatus(@Param("ip")String ip,@Param("devid")int devid,@Param("feedDevId")int feedDevId,
			@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	public List<CutFeedVo> getCutFeedSensor(@Param("ip")String ip,@Param("devid")int devid);
}
