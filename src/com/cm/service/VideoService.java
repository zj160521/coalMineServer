package com.cm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.IVideoDao;
import com.cm.entity.NVR;
import com.cm.entity.Video;

@Service
public class VideoService {

	@Autowired
	private IVideoDao videoDao;
	
	//查询所有nvr和摄像头
	public List<NVR> getAll(){
		return videoDao.getAll();
	}
	
	//添加NVR
	public void addNVR(NVR nvr){
		videoDao.addNVR(nvr);
	}
	
	//修改NVR
	public void updateNVR(NVR nvr){
		videoDao.updateNVR(nvr);
	}
	
	//判断ip地址是否已经使用
	public boolean dipIsuse(String dip){
		List<String> dipIsuse = videoDao.dipIsuse();
		int count = 0;
		for (String string : dipIsuse) {
			if(dip.equals(string)){
				count =+ 1;
			}
		}
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
	
	//根据id查询NVR
	public NVR getById(int id){
		return videoDao.getById(id);
	}
	
	//批量添加摄像头
	public void batchaddVideo(List<Video> videoes){
		videoDao.batchaddVideo(videoes);
	}
	
	//修改摄像头配置信息
	public void updateVideo(Video video){
		videoDao.updateVideo(video);
	}
	
	//删除
	public void delete(int id){
		videoDao.deleteNVR(id);
	}
	
	public List<Video> getAllVideo(){
		List<Video> list = videoDao.getAllVideo();
		for(Video l:list){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
		}
		return list;
	}
}
