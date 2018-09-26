package com.cm.controller.strategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cm.entity.AreaType;
import com.cm.entity.PositionStrategy;
import com.cm.entity.PositionStrategy2;
import com.cm.service.AreaTypeService;
import com.cm.service.StategyService;

@Component
public class StrategyA implements Strategy {
	
	@Autowired
	private StategyService service;
	@Autowired
    private AreaTypeService areaTypeService;
	
    @Override
    public Object dofilter(Object o) {
    	PositionStrategy2 p=(PositionStrategy2) o;
        List<PositionStrategy2> list=service.getStrategy(p);
        Set<Integer> set=new HashSet<Integer>();
        for(PositionStrategy2 ps:list){
        	set.add(ps.getArea_type_id());
        }
        List<PositionStrategy> reslist=new ArrayList<PositionStrategy>();
        for(Integer i:set){
        	PositionStrategy ps=new PositionStrategy();
        	List<PositionStrategy2> pslist=new ArrayList<PositionStrategy2>();
        	ps.setArea_type_id(i);
        	for(PositionStrategy2 ps2:list){
        		if(i==ps2.getArea_type_id()){
        			ps.setType_id(ps2.getType_id());
        			ps.setArea_type(ps2.getArea_type());
        			ps.setPath(ps2.getPath());
        			pslist.add(ps2);
        		}
        	}
        	ps.setList(pslist);
        	reslist.add(ps);
        }
        return reslist;
    }

	@Override
	@Transactional
	public void addAreaCon(Object o) {
		PositionStrategy ps=(PositionStrategy) o;
		if(ps!=null&&!"".equals(ps.getArea_type())&&ps.getArea_type()!=null){
			List<PositionStrategy2> list=new ArrayList<PositionStrategy2>();
			AreaType at=new AreaType();
			String areaType=ps.getArea_type();
			at.setName(areaType);
			at.setType_id(0);
			areaTypeService.add(at);
			int area_type_id= at.getId();
			for(PositionStrategy2 ps2:ps.getList()){
				ps2.setArea_type_id(area_type_id);
				list.add(ps2);
			}
			service.addAreaPos(list);
		}
	}

	@Override
	public void delAreaCon(Object o) {
		
	}
	
	
}
