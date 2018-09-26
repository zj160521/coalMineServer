package com.cm.dao;

import java.util.List;


import com.cm.entity.Worktype;

public interface WorktypeDao {
	
	    //添加
		public void addWorktype(Worktype worktype);
		
		//查询所有
		public List<Worktype> getAllWorktype();
		
		//修改
		public void updateWorktype(Worktype worktype);
		
		//删除
		public void deleteWorktype(int id);
		
		//查询编号是否已存在
		public int getWorktypeBywid(String  wid);
		
		//查询名称是否已存在
		public int getWorktypeByname(Worktype worktype);
}
