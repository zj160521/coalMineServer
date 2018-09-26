package com.cm.dao;

import com.cm.entity.Alarmmeasure;
import com.cm.entity.vo.AnaloginfoQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlarmmeasureDao {
	
	public int add(Alarmmeasure alarm);
	
	public Alarmmeasure getBymeasure(@Param("measure")String measure);
	
	public int getAnaloginfoId(Alarmmeasure alarmmeasure);
	
	public void updateAnaloginfoQueryMeasureId(@Param("measureId")int measureId,@Param("measuretime")String measuretime,@Param("id")int id);
	
	public List<Alarmmeasure> getAll();
	
	public AnaloginfoQuery getAnaloginfoQuery(@Param("id")int startId);
	
	public void updateMeasureId(@Param("measureId")int measureId,@Param("id")int id);

	public Integer getAnaloginfoQueryId(Alarmmeasure alarmmeasure);

	public void updateAnaloginfoQueryMeasure(@Param("measure")String measure,@Param("measuretime")String measuretime,@Param("id")int id);

}
