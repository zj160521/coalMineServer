package com.cm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cm.entity.NVR;
import com.cm.entity.Video;

public interface IVideoDao {

	//查询所有nvr和摄像头
	public List<NVR> getAll();
	
	//添加NVR
	public void addNVR(NVR nvr);
	
	//修改NVR
	public void updateNVR(NVR nvr);
	
	//删除NVR
	public void deleteNVR(int id);
	
	//判断ip地址是否已经使用
	public List<String> dipIsuse();
	
	//根据id查询NVR
	public NVR getById(@Param("id")int id);
	
	//批量添加摄像头
	public void batchaddVideo(@Param("videoes")List<Video> videoes);
	
	//修改摄像头配置信息
	public void updateVideo(Video video);
	
	//查询所有video
	public List<Video> getAllVideo();
}
