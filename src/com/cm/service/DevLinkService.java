package com.cm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.DevLinkDao;
import com.cm.entity.DevLink;
import com.cm.entity.vo.DevlinksVo;

import java.util.List;

@Service("devLinkService")
public class DevLinkService {
	
	@Autowired
	private DevLinkDao dao;
	
	public void addBasisDevLink(List<DevLink> list){
		 dao.addBasisDevLink(list);
	}
	public List<DevLink> getBasisDevLink(){
		return dao.getBasisDevlink();
	}
	
	public void updateBasisDevlink(DevLink link){
		dao.updateBasisDevlink(link);
	}
	
	public void deleteBasisDevlink(DevLink link){
		dao.deleteBasisDevlink(link);
	}

	public List<DevLink> getAllDevLink(){
	    return dao.getAllDevLink();
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DevlinksVo> getDevLinkVos(){
		List<DevlinksVo> list = dao.getDevLinkVos();
		for(DevlinksVo l:list){
			List<String> a = dao.getbygoupid(l.getId());
			l.setUids(a);;
		}
		return list;
	}
	
}
