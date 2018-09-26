package com.cm.service;

import com.cm.dao.IBaseinfoDao;
import com.cm.entity.DevLink;
import com.cm.entity.Sensor;
import com.cm.entity.vo.DevlinkSensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("baseinfoService")
public class BaseinfoService {

	@Autowired
	private IBaseinfoDao baseinfoDao;
	
	@Autowired
	private DevLinkService service;
	
	//添加配置信息
	public int addBaseinfo(Sensor sensor){
		return baseinfoDao.addBaseinfo(sensor);
	}
	
	//查询全部传感器的配置信息
	public List<Sensor> getAllSensor(int type){
		List<Sensor> list = baseinfoDao.getAllSensor(type);
		Map<String, List<DevlinkSensor>> map = getdevmap();
		for(Sensor s:list){
           if (s.getSensor_type() == 41 || s.getSensor_type() == 47 || s.getSensor_type() == 49) {
                s.setHasfloor(1);
           }
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
	
	//查询全部传感器的配置信息
	public List<Sensor> getAllSensor2(){
		List<Sensor> list = baseinfoDao.getAllSensor2();
		Map<String, List<DevlinkSensor>> map = getdevmap();
		for(Sensor s:list){
			if (s.getSensor_type() == 41 || s.getSensor_type() == 47 || s.getSensor_type() == 49) {
                s.setHasfloor(1);
           }
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
	
	//根据id更改某一个传感器的配置信息
	public void updataSensor(Sensor sensor){
		baseinfoDao.updateSensor(sensor);
	}
	
	//根据id删除某一个传感器
	public void deleteSensor(int id){
		baseinfoDao.deleteSensor(id);
	}
	
	//根据id查询传感器
	public Sensor getById(int id){
		return baseinfoDao.getById(id);
	}
	
	//查询所有瓦斯传感器
	public List<Sensor> getAllmashgas(){
		List<Sensor> list = baseinfoDao.getAllmashgas();
		Map<String, List<DevlinkSensor>> map = getdevmap();
		for(Sensor s:list){
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
	
	//更新传感器分站信息
	public void updateSubstation(String ipaddr,int id){
		baseinfoDao.updateSubstation(ipaddr, id);
	}
	
	//查询分站下的所有传感器
	public List<Sensor> getbysubid(int subid){
		List<Sensor> list = baseinfoDao.getbysubid(subid);
		Map<String, List<DevlinkSensor>> map = getdevmap();
		for(Sensor s:list){
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
	
	public List<Sensor> getbyareaid(int areaid){
		List<Sensor> list = baseinfoDao.getbyareaid(areaid);
		Map<String, List<DevlinkSensor>> map = getdevmap();
		for(Sensor s:list){
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
	
	public List<Sensor> getbyuids(List<String> uids){
		List<Sensor> list = baseinfoDao.getbyuids(uids);
		Map<String, List<DevlinkSensor>> map = getdevmap();
		for(Sensor s:list){
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
	public List<Sensor> getNoSync(){
		return baseinfoDao.getNoSync();
	}
	
	//根据id修改传感器同步情况
	public void updateConfigById(int configsync,int id){
		baseinfoDao.updateConfigById(configsync, id);
	}
	
	public Sensor getById2(int id){
		return baseinfoDao.getById2(id);
	}
	
	public void updateUid(int id,String uid){
		baseinfoDao.updateUid(id, uid);
	}

	public List<Sensor> getGD5Sensor(){
	    return baseinfoDao.getGd5Sensors();
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

	public Sensor getByCoId(int CoId){
	    return baseinfoDao.getByCoId(CoId);
    }

    public Sensor getByMethaneId(int methaneId){
	    return baseinfoDao.getBymethaneId(methaneId);
    }
    
    
    public List<Sensor> getSensorByAreaId(int areaid){
    	List<Sensor> list = baseinfoDao.getSensorByAreaId(areaid);
		Map<String, List<DevlinkSensor>> map = getdevmap();
		for(Sensor s:list){
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
}
