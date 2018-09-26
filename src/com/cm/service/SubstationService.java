package com.cm.service;

import com.cm.dao.ISubstationDao;
import com.cm.entity.Sensor;
import com.cm.entity.Substation;
import com.cm.entity.vo.StationSensorVo;
import com.sun.org.apache.bcel.internal.generic.LLOAD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SubstationService {

	@Autowired
	private ISubstationDao substationDao;

	@Autowired
	private StaticService staticService;
	
	//查询所有分站
	@SuppressWarnings("rawtypes")
	public List<Substation> getAll(){
		List<Substation> list = substationDao.getAll();
		for(Substation l:list){
			if(l.getPosition()==null){
				l.setPosition("未配置位置");
			}
		}
		return list;
	}
	
	//添加分站
	@SuppressWarnings("rawtypes")
	public void addup(Substation station){
		substationDao.addup(station);
	}
	
	//更新分站
	@SuppressWarnings("rawtypes")
	public void update(Substation station){
		substationDao.update(station);
	}
	
	//删除分站
	public void delete(int id){
		substationDao.delete(id);
	}

	@SuppressWarnings("rawtypes")
	public Substation getSubbyid(int id){
		return substationDao.getSubbyid(id);
	}
	
	public Sensor getRepetition(int station,int sensorId){
		return substationDao.getRepetition(station, sensorId);
	}

	
	//查询分站上是否有重复地址的传感器
	public String isuse(int station,int sensorId){
		Sensor sensor = substationDao.getRepetition(station, sensorId);
		if(null==sensor){
			return null;
		}else{
            String v = staticService.getV(sensor.getSensor_type());
            return "该地址被"+sensor.getSensorId()+"号设备"+v+"占用";
		}
	}
	
	//查询分站上传感器个数
	public Integer sensorCount(int station){
		return substationDao.sensorCount(station);
		
	}
	
	public List<StationSensorVo> getAllSensor(){
		return substationDao.getAllSensor();
	}
	
	//查询所有分站上开关量传感器
	@SuppressWarnings("rawtypes")
	public List<Substation> getAllSwitchSensor(){
		return substationDao.getAllSwitchSensor();
	}
	
	//根据ip地址查询分站
	@SuppressWarnings("rawtypes")
	public Substation getByIp(String ip){
		return substationDao.getByIp(ip);
	}
	
	public List<String> getAllipaddr(){
		return substationDao.getAllipaddr();
	}

	public void updateUid(int id,String uid){
	    substationDao.updateUid(id, uid);
    }

    public String getUid(int sensorId, String ipaddr) {
	    return substationDao.getUid(sensorId, ipaddr);
    }

}
