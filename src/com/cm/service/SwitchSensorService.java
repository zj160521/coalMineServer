package com.cm.service;

import com.cm.dao.ISwitchDao;
import com.cm.entity.DevLink;
import com.cm.entity.SwitchSensor;
import com.cm.entity.vo.DevlinkSensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SwitchSensorService {

	@Autowired
	private ISwitchDao switchDao;
	
	@Autowired
	private DevLinkService service;
	
	//查询所有开关量传感器
	public List<SwitchSensor> getAll(Integer type){
		List<SwitchSensor> list = switchDao.getAllSwitchSensor(type);
		Map<String, List<DevlinkSensor>> map = getdevmap();
		for(SwitchSensor s:list){
			if(s.getPosition()==null){
				s.setPosition("未配置位置");
			}
			List<DevlinkSensor> link = map.get(s.getUid());
			if(link!=null&&link.size()>0){
				String[] devname = new String[link.size()];
				for(int i=0;i<link.size();i++){
					devname[i] = link.get(i).getDsp();
				}
				s.setDevname(devname);
			}
			s.setSensorList(link);
		}
		return list;
	}
	
	//添加开关量传感器
	public int addSwitchSensor(SwitchSensor sensor){
		return switchDao.addSwitchSensor(sensor);
	}
	
	//更新
	public void update(SwitchSensor sensor){
		switchDao.updateSwitchSensor(sensor);
	}
	
	//删除
	public void delete(int id){
		switchDao.delete(id);
	}
	
	//根据id查询
	public SwitchSensor getById(int id){
		return switchDao.getById(id);
	}
	
	//查询新添加设备的id
	public int getaddedId(int stationId,int devid,int type){
		if(null == switchDao.getaddedId(stationId, devid, type)){
			return 0;
		}else{
			return switchDao.getaddedId(stationId, devid, type);
		}
	}
	
	//查询分站下的所有传感器
	public List<SwitchSensor> getbysubid(int subid){
		List<SwitchSensor> list = switchDao.getbysubid(subid);
		Map<String, List<DevlinkSensor>> map = getdevmap();
		for(SwitchSensor s:list){
			if(s.getPosition()==null){
				s.setPosition("未配置位置");
			}
			List<DevlinkSensor> link = map.get(s.getUid());
			if(link!=null&&link.size()>0){
				String[] devname = new String[link.size()];
				for(int i=0;i<link.size();i++){
					devname[i] = link.get(i).getDsp();
				}
				s.setDevname(devname);
			}
			s.setSensorList(link);
		}
		return list;
	}
	
	public List<SwitchSensor> getbyareaid(int areaid){
		List<SwitchSensor> list = switchDao.getbyareaid(areaid);
		Map<String, List<DevlinkSensor>> map = getdevmap();
		for(SwitchSensor s:list){
			if(s.getPosition()==null){
				s.setPosition("未配置位置");
			}
			List<DevlinkSensor> link = map.get(s.getUid());
			if(link!=null&&link.size()>0){
				String[] devname = new String[link.size()];
				for(int i=0;i<link.size();i++){
					devname[i] = link.get(i).getDsp();
				}
				s.setDevname(devname);
			}
			s.setSensorList(link);
		}
		return list;
	}
	
	public List<SwitchSensor> getbyuids(List<String> uids){
		List<SwitchSensor> list = switchDao.getbyuids(uids);
		Map<String, List<DevlinkSensor>> map = getdevmap();
		for(SwitchSensor s:list){
			if(s.getPosition()==null){
				s.setPosition("未配置位置");
			}
			List<DevlinkSensor> link = map.get(s.getUid());
			if(link!=null&&link.size()>0){
				String[] devname = new String[link.size()];
				for(int i=0;i<link.size();i++){
					devname[i] = link.get(i).getDsp();
				}
				s.setDevname(devname);
			}
			s.setSensorList(link);
		}
		return list;
	}
	
	//查询未同步设备
	public List<SwitchSensor> getNoSync(){
		return switchDao.getNoSync();
	}
	
	//根据id修改传感器同步情况
	public void updateConfigById(int configsync,int id){
		switchDao.updateConfigById(configsync, id);
	}
	
	
	public List<Integer> getisuseFeedId(int id,int feedId){
		return switchDao.getisuseFeedid(id, feedId);
	}
	
	public void updateFeedId(SwitchSensor sensor){
		switchDao.updateFeedId(sensor);
	}
	
	public void updateUid(int id,String uid){
		switchDao.updateUid(id, uid);
	}

	public SwitchSensor getByStationAndSensorId(int station,int sensorId){
	    return switchDao.getByStationAndSensorId(station,sensorId);
    }

    public List<SwitchSensor> AllSwitchSensor(){
    	List<SwitchSensor> list = switchDao.AllSwitchSensor();
		Map<String, List<DevlinkSensor>> map = getdevmap();
		for(SwitchSensor s:list){
			if(s.getPosition()==null){
				s.setPosition("未配置位置");
			}
			List<DevlinkSensor> link = map.get(s.getUid());
			if(link!=null&&link.size()>0){
				String[] devname = new String[link.size()];
				for(int i=0;i<link.size();i++){
					devname[i] = link.get(i).getDsp();
				}
				s.setDevname(devname);
			}
			s.setSensorList(link);
		}
		return list;
    }
    
    public Map<String, List<DevlinkSensor>> getdevmap(){
		List<DevLink> links = service.getBasisDevLink();
		Map<String, List<DevlinkSensor>> map = new HashMap<String, List<DevlinkSensor>>();
		for(DevLink l:links){
			List<DevlinkSensor> a = map.get(l.getLogic_uid());
			if(a==null||a.size()==0){
				a = new ArrayList<DevlinkSensor>();
				map.put(l.getLogic_uid(), a);
			}
			DevlinkSensor b = new DevlinkSensor();
			b.setUid(l.getAction_uid());
			b.setSensor_type(l.getAction_type());
			b.setIp(l.getAction_ip());
			b.setSensorId(l.getActDevId());
			if(l.getParam()!=null&&l.getParam()!=""){
				b.setAction(Integer.parseInt(l.getParam()));
			}
			b.setDsp(l.getDsp());
			a.add(b);
		}
		return map;
	}
    
    public List<SwitchSensor> getSensorByAreaId(int areaid){
    	List<SwitchSensor> list = switchDao.getSensorByAreaId(areaid);
		Map<String, List<DevlinkSensor>> map = getdevmap();
		for(SwitchSensor s:list){
			if(s.getPosition()==null){
				s.setPosition("未配置位置");
			}
			List<DevlinkSensor> link = map.get(s.getUid());
			if(link!=null&&link.size()>0){
				String[] devname = new String[link.size()];
				for(int i=0;i<link.size();i++){
					devname[i] = link.get(i).getDsp();
				}
				s.setDevname(devname);
			}
			s.setSensorList(link);
		}
		return list;
    }
    
    public List<SwitchSensor> getControlSensor(){
    	List<SwitchSensor> list = switchDao.getControlSensor();
		for(SwitchSensor s:list){
			if(s.getPosition()==null){
				s.setPosition("未配置位置");
			}
		}
		return list;
    }
    
}
