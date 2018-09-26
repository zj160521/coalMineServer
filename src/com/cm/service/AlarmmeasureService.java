package com.cm.service;

import com.cm.dao.AlarmmeasureDao;
import com.cm.entity.Alarmmeasure;
import com.cm.entity.vo.AnaloginfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmmeasureService {

	@Autowired
	private AlarmmeasureDao dao;
	
	public int add(Alarmmeasure alarmmeasure){
		return dao.add(alarmmeasure);
	}
	
	public Alarmmeasure getBymeasure(String measure){
		return dao.getBymeasure(measure);
	}
	
	public int getAnaloginfoId(Alarmmeasure alarmmeasure){
		return dao.getAnaloginfoId(alarmmeasure);
	}
	
	public void updateAnaloginfoQueryMeasureId(int measureId, String measuretime,int startId){
		dao.updateAnaloginfoQueryMeasureId(measureId, measuretime, startId);
	}
	
	public List<Alarmmeasure> getAll(){
		return dao.getAll();
	}
	
	public AnaloginfoQuery getAnaloginfoQuery(int startId){
		return dao.getAnaloginfoQuery(startId);
	}
	
	public void updateMeasureId(int measureId,int id){
		dao.updateMeasureId(measureId, id);
	}

	public Integer getAnaloginfoQueryId(Alarmmeasure alarmmeasure){
	    return dao.getAnaloginfoQueryId(alarmmeasure);
    }

    public void updateAnaloginfoQueryMeasure(String measure,String measuretime,int id){
	    dao.updateAnaloginfoQueryMeasure(measure,measuretime,id);
    }
}
