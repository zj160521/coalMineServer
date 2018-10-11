package com.cm.dao;

import java.util.List;

import com.cm.entity.Switchinfo;
import com.cm.entity.vo.*;
import org.apache.ibatis.annotations.Param;

public interface SwitchinfoDao {
	
		
		//查询
		public List<Switchinfo> getall(NameTime nameTime);
		
		//查询符合记录的总条数
		public int getallcount(NameTime nameTime);
		
		//查询所有记录
		public List<Switchinfo> getallAna(NameTime nameTime);
		
		public List<AnalogoutVo> getAnas(NameTime nameTime);
		
		public List<SSWarning> getWarnimgRec(SSParaVo para);
		
		public List<SwitchRecs> getStatusChangeRecs(SSParaVo para);

		public List<AnaloginfoQuery> getSwitchStateChange(@Param("tableName") String tableName);

        public List<AnaloginfoQuery> getSensorAlarms(NameTime nameTime);

        public List<AnaloginfoQuery> getSwitchSensorAlarms(NameTime nameTime);
}
