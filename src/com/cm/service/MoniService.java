package com.cm.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cm.controller.ResultObj;
import com.cm.dao.IMoniDao;
import com.cm.entity.Moni;
import com.cm.entity.Permission;
import com.cm.entity.User;
import com.cm.security.LoginManage;

@Service("moniService")
public class MoniService {

	@Autowired
	private IMoniDao mdao;
	@Autowired
	private ResultObj result;
	@Autowired
	private LoginManage loginManage;

	public Object getAllNvrAndDvr(HttpServletRequest request) {
		Object ret = loginManage.isLogin(request);
		if (ret != null)
			return ret;
		
		// 所有的nvr
		List<Moni> allList = mdao.getAllNvr();

		// nvr对应的摄像头
		List<Moni> list = mdao.getAllByPid();

		List<Moni> reslist = new ArrayList<Moni>();
		for (Moni m : allList) {
			Moni moni = new Moni();
			moni.setId(m.getId());
			moni.setPid(m.getPid());
			moni.setDip(m.getDip());
			moni.setPort(m.getPort());
			moni.setUsername(m.getUsername());
			moni.setPassword(m.getPassword());
			moni.setPosition(m.getPosition());
			moni.setIsuse(m.getIsuse());
			moni.setName(m.getName());
			moni.setType(m.getType());
			List<Moni> monilist = new ArrayList<Moni>();
			for (Moni mm : list) {
				if (m.getId() == mm.getPid()) {
					monilist.add(mm);
				}
			}
			moni.setList(monilist);
			reslist.add(moni);
		}
		if (allList == null || allList.isEmpty() || allList.size() < 1) {
			result.put("data", reslist);
			return result.setStatus(0, "no data");
		} else {
			result.put("data", reslist);
		}
		return result.setStatus(0, "ok");
	}

	// 添加和修改
	@Transactional
	public Object addup(HttpServletRequest request, Moni moni) {
		Object ret = loginManage.isLogin(request);
		if (ret != null)
			return ret;

		try {
			if (moni.getId() > 0) {
				mdao.update(moni);
			} else {
				if (moni.getType() == 0) {
					int count = mdao.getMoniDipByDip(moni.getDip());
					if (count > 0) {
//						LogOut.log.debug("=======================2");
						return result.setStatus(-2, "此IP地址已存在,请重新输入");
					}
					moni.setIsuse(1);
					moni.setType(0);
					mdao.add(moni);
					moni.setPid(moni.getId() * 3);
					mdao.update(moni);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-3, "exception");
		}

		return result.setStatus(0, "操作成功！");
	}

	// 向nvr添加摄像头
	public Object addDvr(HttpServletRequest request, Moni moni) {

		Object ret = loginManage.isLogin(request);
		if (ret != null)
			return ret;
		
		try {
			int count = mdao.getMoniDipByDip(moni.getDip());
			if (count > 0) {
//				LogOut.log.debug("============================3");
				return result.setStatus(-2, "此IP地址已存在,请重新输入");
			}
			Moni mm = mdao.getMoni(moni.getId());
			moni.setPid(mm.getId());
			moni.setPort(mm.getPort());
			moni.setUsername(mm.getUsername());
			moni.setPassword(mm.getPassword());
			moni.setIsuse(1);
			moni.setType(1);
			mdao.add(moni);

		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-3, "exception");
		}

		return result.setStatus(0, "操作成功！");
	}

	// 删除
	public Object delete(Moni moni, HttpServletRequest request) {
		Object ret = loginManage.isLogin(request);
		if (ret != null)
			return ret;
		
		try {
			Moni mm = mdao.getMoni(moni.getId());
			if (mm.getType() == 0) {
				mdao.delete(mm.getId());
				mdao.deleteByPid(mm.getId());
			} else {
				mdao.delete(mm.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-3, "exception");
		}
		return result.setStatus(0, "删除成功");
	}

	// 判断ip地址是否重复

	/*public Object checkDipAndName(Moni moni, HttpServletRequest request) {
		Object ret = loginManage.isLogin(request);
		if (ret != null)
			return ret;
		Object per = loginManage.checkPermission(request, 131);
		if (per != null)
			return per;

		try {
			int count = mdao.getMoniDipByDip(moni.getDip());
			if (count > 0) {
				return result.setStatus(-2, "此IP地址已存在,请重新输入");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-3, "exception");
		}
		return result.setStatus(0, "ok");
	}*/

}
