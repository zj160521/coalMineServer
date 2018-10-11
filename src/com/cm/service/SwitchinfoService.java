package com.cm.service;

import com.cm.dao.SwitchinfoDao;
import com.cm.entity.Switchinfo;
import com.cm.entity.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.UtilMethod;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Service("switchinfoService")
public class SwitchinfoService {
	
	@Autowired
	private SwitchinfoDao dao;

	
	//查询
	public List<Switchinfo> getall(NameTime nameTime) throws ParseException{
		List<Switchinfo> list = dao.getall(nameTime);
		for(Switchinfo l:list){
			l.setZerostate("关");
			l.setOnestate("开");
			if(l.getValue()==0){
				l.setValuestate(l.getZerostate());
			}else{
				l.setValuestate(l.getOnestate());
			}
			if(l.getStarttime()!=null&&l.getEndtime()!=null){
				l.setTimes(UtilMethod.longToTimeFormat(l.getStarttime(), l.getEndtime()).getTimCast());
			}
		}
		return list;
	}
	
	
	public int getcount(NameTime nameTime){
		return dao.getallcount(nameTime);
	}
	
	public List<Switchinfo> getallAna(NameTime nameTime) throws ParseException{
		List<Switchinfo> list = dao.getall(nameTime);
		for(Switchinfo l:list){
			l.setZerostate("关");
			l.setOnestate("开");
			if(l.getValue()==0){
				l.setValuestate(l.getZerostate());
			}else{
				l.setValuestate(l.getOnestate());
			}
			if(l.getStarttime()!=null&&l.getEndtime()!=null){
				l.setTimes(UtilMethod.longToTimeFormat(l.getStarttime(), l.getEndtime()).getTimCast());
			}
			
		}
		return list;
	}
	
	public List<AnalogoutVo> getAnas(NameTime nameTime){
		List<AnalogoutVo> list = dao.getAnas(nameTime);
		for(AnalogoutVo s:list){
			if(s.getStatus()==2){
				s.setDesp("报警");
			}else if(s.getStatus()==3){
				s.setDesp("断电");
			}else if(s.getStatus()==4){
				s.setDesp("复电");
			}
		}
		return list;
	}
	
	public List<SSWarning> getWarnimgRec(SSParaVo para){
		try {
			List<SSWarning> warnimgRec = dao.getWarnimgRec(para);
			if(warnimgRec.size() > 0){
				for(SSWarning rec : warnimgRec){
					if(null != rec.getStarttime()){
                        String sub = sub(rec.getStarttime());
                        rec.setStarttime(sub);
                    }
					if(null != rec.getEndtime()){
                        String sub2 = sub(rec.getEndtime());
                        rec.setEndtime(sub2);
                    }
				}
				return warnimgRec;
			}
			return new ArrayList<SSWarning>();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String sub(String s){
		try {
			return s.substring(0, 19);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<SwitchRecs> getStatusChangeRecs(SSParaVo para){
		try {
			return dao.getStatusChangeRecs(para);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<AnaloginfoQuery> getSensorAlarms(NameTime nameTime) {
	    return dao.getSensorAlarms(nameTime);
    }

    public List<AnaloginfoQuery> getSwitchSensorAlams(NameTime nameTime) {
        return dao.getSwitchSensorAlarms(nameTime);
    }
}
