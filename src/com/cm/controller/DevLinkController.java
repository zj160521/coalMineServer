package com.cm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import util.ListUtil;
import util.StaticUtilMethod;

import com.cm.dao.DevLinkDao;
import com.cm.entity.DevAction;
import com.cm.entity.vo.DevLinkVo;
import com.cm.entity.vo.DevLogicVo;
import com.cm.entity.vo.GroupLgc;
import com.cm.entity.vo.Ids;
import com.cm.security.LoginManage;

@Scope("prototype")
@Controller
@RequestMapping(value = "/devLink")
public class DevLinkController {
	@Autowired
	private ResultObj result;
	@Autowired
	private LoginManage loginManage;
	@Autowired
	private DevLinkDao dlDao;
	
	@SuppressWarnings("rawtypes")
	private ArrayList addList;
	@SuppressWarnings("rawtypes")
	private ArrayList updateList;
	private static final String RADIO_ACTION = "broadcast";
	private static final String CARDREADER_ACTION = "call";
	private static final String CUTOUT_ACTION = "cutout";
	
	/**
	 * 增加或更新联动组
	 * @param devLink  参数对象
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addupLinkGroup",method=RequestMethod.POST)
	@ResponseBody
	public Object setDevLink(@RequestBody DevLinkVo devLink, HttpServletRequest request){
		if(devLink.getPid() >= 0){
			//插入联动组信息，并获取组ID
			DevLinkVo dlg = getGroup(devLink);
			//初始化列表
			initDataList();
			
			//设备逻辑构建
			List<DevLogicVo> listLgc = devLink.getListLgc();
			if(ListUtil.notEmptyList(listLgc)){
				setExps(listLgc, dlg);//构建联动组表达式
			}
			//增加或更新联动逻辑
			addUpLgcList();
			
			//联动动作构建
			initDataList();
			List<DevAction> listCutOut = devLink.getListCutOut();//开关量
			if(ListUtil.notEmptyList(listCutOut)) setCut(listCutOut, dlg);
			List<DevAction> listRadio = devLink.getListRadio();//语音广播
			dlDao.delActByGrp(dlg.getId(),65);
			if(ListUtil.notEmptyList(listRadio)) setRadio(listRadio, dlg, devLink);
			List<DevAction> listCardReader = devLink.getListCardReader();//读卡器
			dlDao.delActByGrp(dlg.getId(),64);
			if(ListUtil.notEmptyList(listCardReader)) setCardReader(listCardReader, dlg);
			addUpActList();
		}else{
			dlDao.addLgcGroup(devLink);
			result.put("id", devLink.getId());
		}
		
		return result.setStatus(0, "ok");
	}
	
	public void initDataList(){
		addList = new ArrayList<>();
		updateList = new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	public void addUpLgcList(){
		if(ListUtil.notEmptyList(addList))
			dlDao.addDevLgc(addList);
		if(ListUtil.notEmptyList(updateList))
			dlDao.updateDevLgc(updateList);
	}
	
	@SuppressWarnings("unchecked")
	public void addUpActList(){
		if(ListUtil.notEmptyList(addList))
			dlDao.addDevAction(addList);
		if(ListUtil.notEmptyList(updateList))
			dlDao.updateDevAction(updateList);
	}
	
	public DevLinkVo getGroup(DevLinkVo devLink){
		if(devLink.getId() > 0){
			dlDao.updateLgcGroup(devLink);
		}else{
			dlDao.addLgcGroup(devLink);
		}
		
		return devLink;
	}
	
	@SuppressWarnings("unchecked")
	public void setExps(List<DevLogicVo> listLgc, DevLinkVo dlg){
		updateList = isNullList(updateList);
		addList = isNullList(addList);
		int count = 0;
		for(DevLogicVo dlv : listLgc){
			count ++;
			setSwitchRep(dlv);//构建开关量解除逻辑元素
			if(dlv.getSole() == 1)
				dlDao.delLgc(dlv.getId());
			else if(dlv.getId() > 0){
				exps(dlv);
				updateList.add(dlv);
			}else{
				dlv.setGroupId(dlg.getId());
				exps(dlv);
				addList.add(dlv);
			}
			//构建联动组的表达式
			setGrpupExps(listLgc.size(), dlg, count, dlv);
		}
		dlDao.updateLgcGroup(dlg);
	}
	
	public void setGrpupExps(int size, DevLinkVo dlg, int count, DevLogicVo dlv){
		if(count == 1) dlg.setLgcExps(null);
		if(dlv.getSole() == 1) return;//跳过删除了的设备表达式
		if(dlv.getScene() == 2){//情景模式2
			dlg.setLgcExps(dlv.getLgcExps().concat(" and ").concat(dlv.getUid2()));
		}else if(size > 0 && size > dlg.getLeastNum()){//设备数大于条件数
			if(count == 1){
				dlg.setLgcExps("[".concat(dlv.getLgcExps()));
				if(StaticUtilMethod.notNullOrEmptyStr(dlv.getLgcOperator2()) || StaticUtilMethod.notNullOrEmptyStr(dlv.getLgcOperator3()))
					dlg.setRepowerExps(dlv.getRepowerExps());
			}else{
				if (dlg.getLgcExps() != null)
				 dlg.setLgcExps(dlg.getLgcExps().concat(",").concat(dlv.getLgcExps()));
				else dlg.setLgcExps(dlv.getLgcExps());
				if(StaticUtilMethod.notNullOrEmptyStr(dlv.getLgcOperator2()) || StaticUtilMethod.notNullOrEmptyStr(dlv.getLgcOperator3())){
					String reStr = StaticUtilMethod.notNullOrEmptyStr(dlg.getRepowerExps()) ? 
							dlg.getRepowerExps().concat(" and ").concat(dlv.getRepowerExps()) : dlv.getRepowerExps();
					dlg.setRepowerExps(reStr);
				}
			}
			
			if(count == size){
				dlg.setLgcExps(dlg.getLgcExps().concat("].count(True) >= ").concat(dlg.getLeastNum()+""));
			}
		}else if(size == dlg.getLeastNum()){//设备数等于条件数
			if(count == 1){
				dlg.setLgcExps(dlv.getLgcExps());
				if(StaticUtilMethod.notNullOrEmptyStr(dlv.getLgcOperator2()) || StaticUtilMethod.notNullOrEmptyStr(dlv.getLgcOperator3()))
					dlg.setRepowerExps(dlv.getRepowerExps());
			}else{
				String exps = StaticUtilMethod.notNullOrEmptyStr(dlg.getLgcExps()) ? dlg.getLgcExps().concat(",").concat(dlv.getLgcExps()) 
						: dlv.getLgcExps();
				dlg.setLgcExps(exps);
				if(StaticUtilMethod.notNullOrEmptyStr(dlv.getLgcOperator2()) || StaticUtilMethod.notNullOrEmptyStr(dlv.getLgcOperator3())){
					String reStr = StaticUtilMethod.notNullOrEmptyStr(dlg.getRepowerExps()) ? 
							dlg.getRepowerExps().concat(" and ").concat(dlv.getRepowerExps()) : dlv.getRepowerExps();
					dlg.setRepowerExps(reStr);
				}
			}
		}
	}
	
	public void setSwitchRep(DevLogicVo dlv){
		if(dlv.getType() == 25 && !(dlv.getScene() > 0)){
			dlv.setLgcOperator2("==");
			dlv.setValue2(Math.abs(dlv.getValue() - 1));
		}
	}
	
	public void exps(DevLogicVo dlv){
		dlv.setLgcExps(null);
		//监测值表达式构建
		if(StaticUtilMethod.notNullOrEmptyStr(dlv.getDev2()) && dlv.getScene() != 2)
			dlv.setLgcExps("("+dlv.getUid() + dlv.getLgcOperator()+dlv.getUid2()+")");
		else if(StaticUtilMethod.notNullOrEmptyStr(dlv.getLgcOperator()))
			dlv.setLgcExps("("+dlv.getUid() + dlv.getLgcOperator()+dlv.getValue()+")");
		
		//通讯中断表达式构建
		if(dlv.getLgcOperator3() != null && dlv.getLgcOperator3() != "" && dlv.getValue3() != null){
			if(dlv.getLgcExps() != null)
				dlv.setLgcExps(dlv.getLgcExps().concat(" or ("+dlv.getUid() + dlv.getLgcOperator3()+dlv.getValue3()+")"));
			else dlv.setLgcExps("("+dlv.getUid() + dlv.getLgcOperator3()+dlv.getValue3()+")");
			//通讯中断反向表达式构建
			if(dlv.getLgcOperator3().equals("_S==") && dlv.getValue3().equals("5"))
				dlv.setRepowerExps("("+dlv.getUid() + "_S!=" + dlv.getValue3()+")");
		}
		//debug表达式构建
		String debug = dlv.getDebugList();
		if (StaticUtilMethod.notNullOrEmptyStr(debug) && !debug.equals("[]")){
			String str = dlv.getLgcExps() == null ? "("+dlv.getUid() + "_D in " + debug+")" 
					: dlv.getLgcExps() + " or (" +dlv.getUid() + "_D in " + debug+")";
			dlv.setLgcExps(str);
		}
		//监测值变化
		if(dlv.getValue_change() != null){
			if(dlv.getValue_change() == 0 || dlv.getValue_change() == 1 || dlv.getValue_change() == 2){
				String str = dlv.getLgcExps() != null ? dlv.getLgcExps().concat(" or ("+dlv.getUid() + "_C==" + dlv.getValue_change()+")") 
						: "("+dlv.getUid() + "_C==" + dlv.getValue_change()+")";
				dlv.setLgcExps(str);
			}
		}
		//监测值方向	
		if(dlv.getReverting() != null){
			if(dlv.getReverting() == 0 || dlv.getReverting() == 1){
				if(dlv.getLgcExps() != null)
					dlv.setLgcExps(dlv.getLgcExps().concat(" or ("+dlv.getUid() + "_R==" + dlv.getReverting()+")"));
				else
					dlv.setLgcExps("("+dlv.getUid() + "_R==" + dlv.getReverting()+")");
			}
		}
		
		//情景模式
		if(!(dlv.getScene() > 0) && StaticUtilMethod.notNullOrEmptyStr(dlv.getLgcOperator2())){
			if(dlv.getRepowerExps() == null)
				dlv.setRepowerExps("("+dlv.getUid() + dlv.getLgcOperator2()+dlv.getValue2()+")");
			else
				dlv.setRepowerExps(dlv.getRepowerExps() + "and("+dlv.getUid() + dlv.getLgcOperator2()+dlv.getValue2()+")");
		}
			
	}
	
	public ArrayList<?> isNullList(ArrayList<?> list){
		if(list == null)
			list = new ArrayList<>();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public void setCut(List<DevAction> listCutOut, DevLinkVo dlg){
		for(DevAction da : listCutOut){
			if(da.getSole() == 1)
				dlDao.delDecAction(da.getId());
			else if(da.getId() > 0)
				updateList.add(da);
			else{
				da.setGroupId(dlg.getId());
				da.setAction(CUTOUT_ACTION);
				addList.add(da);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setRadio(List<DevAction> listRideo, DevLinkVo dlg, DevLinkVo devLink){
		for(DevAction da : listRideo){
			da.setGroupId(dlg.getId());
			da.setAction(RADIO_ACTION);
			da.setParam(dlg.getFilePath());
			da.setDevType(65);
			addList.add(da);
		}
	}

	@SuppressWarnings("unchecked")
	public void setCardReader(List<DevAction> listCardReader, DevLinkVo dlg){
		for(DevAction da : listCardReader){
			da.setGroupId(dlg.getId());
			da.setAction(CARDREADER_ACTION);
			da.setParam("1");
			da.setDevType(64);
			addList.add(da);
		}
	}
	
	private DevLogicVo dlv = null;
	private DevLinkVo dv = null;
	@SuppressWarnings("rawtypes")
	private Map<String, List> map = null;
	private List<DevLinkVo> resultList = new ArrayList<DevLinkVo>();
	/**
	 * 获取联动组
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllGroup",method=RequestMethod.GET)
	@ResponseBody
	public Object getAllGroup(HttpServletRequest request){
		//获取逻辑组动作map
		getActMap();
		//逻辑组对象构建
		List<GroupLgc> allGroupLgc = dlDao.getAllGroupLgc();
		if(StaticUtilMethod.notNullOrEmptyList(allGroupLgc)){
			int gId = 0;
			int count = 0;
			for(GroupLgc gl : allGroupLgc){
				count ++;
				if(gl.getLgcId() > 0){
					if(!(gId > 0 && gId == gl.getGroupId())){
						if(gId > 0) resultList.add(dv);
						gId = gl.getGroupId();
						setDv(gl);
					}
					addResult(gl, count, allGroupLgc);
				}else{
//					resultList.add(dv);
					setDv(gl);
					resultList.add(dv);
				}
			}
		}
		result.put("data", resultList);
		return result.setStatus(0, "ok");
	}
	
	@SuppressWarnings("rawtypes")
	public void getActMap(){
		List<DevAction> allAct = dlDao.getAllAct();
		map = new HashMap<String, List>();
		if(allAct != null && !allAct.isEmpty())
			cutList(allAct);
	}
	
	public void cutList(List<DevAction> allAct){
		for(DevAction da : allAct){
			if(da.getDevType() == 53 || da.getDevType() == 54 || da.getDevType() == 56 || da.getDevType() == 71){
				setMap(da, "K");
			}else if(da.getDevType() == 65){
				setMap(da, "R");
			}else if(da.getDevType() == 64){
				setMap(da, "C");
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setMap(DevAction da, String type){
		String key = type + da.getGroupId();
		List<DevAction> list = map.get(key);
		if(list == null)
			list = new ArrayList<>();
		list.add(da);
		map.put(key, list);
	}
	
	public void addResult(GroupLgc gl, int count, List<GroupLgc> allGroupLgc){
		setDlv(gl);
		dv.getListLgc().add(dlv);
		if(count == allGroupLgc.size())
			resultList.add(dv);
	}
	
	@SuppressWarnings("unchecked")
	public void setDv(GroupLgc gl){
		dv = new DevLinkVo();
		dv.setId(gl.getGroupId());
		dv.setPid(gl.getPid());
		dv.setAreaId(gl.getAreaId());
		dv.setName(gl.getName());
		dv.setLgcExps(gl.getGroupExps());
		dv.setAlarm(gl.getAlarm());
		dv.setIsUse(gl.getIsUse());
		dv.setFilePath(gl.getFilePath());
		dv.setCrarea(gl.getCrarea());
		dv.setLeastNum(gl.getLeastNum());
		dv.setListLgc(new ArrayList<DevLogicVo>());
		dv.setRepowerExps(gl.getReGroupExps());
		dv.setIsControl(gl.getIsControl());
		if(map.get("K" + gl.getGroupId()) != null)
			dv.setListCutOut(map.get("K" + gl.getGroupId()));
		else
			dv.setListCutOut(new ArrayList<DevAction>());
		if(map.get("R" + gl.getGroupId()) != null)
			dv.setListRadio(map.get("R" + gl.getGroupId()));
		else
			dv.setListRadio(new ArrayList<DevAction>());
		if(map.get("C" + gl.getGroupId()) != null)
			dv.setListCardReader(map.get("C" + gl.getGroupId()));
		else
			dv.setListCardReader(new ArrayList<DevAction>());
	}
	
	//逻辑对象
	public void setDlv(GroupLgc gl){
		dlv = new DevLogicVo();
		dlv.setId(gl.getLgcId());
		dlv.setGroupId(gl.getGroupId());
		dlv.setLgcDevid(gl.getLgcDevId());
		dlv.setDev(gl.getDev());
		dlv.setDev2(gl.getDev2());
		dlv.setLgcExps(gl.getLgcExps());
		dlv.setValue(gl.getValue());
		dlv.setSwitchValueText(gl.getSwitchValueText());
		dlv.setDsp(gl.getDsp());
		dlv.setLgcOperator(gl.getLgcOperator());
		dlv.setType(gl.getType());
		dlv.setComparisonType(gl.getComparisonType());
		dlv.setLgcOperator2(gl.getReOper());
		dlv.setRepowerExps(gl.getReLgcExps());
		dlv.setValue2(gl.getReValue());
		dlv.setScene(gl.getScene());
		dlv.setDev2Id(gl.getDev2Id());
		dlv.setUid(gl.getUid());
		dlv.setUid2(gl.getUid2());
		dlv.setLgcOperator3(gl.getLgcOperator3());
		dlv.setValue3(gl.getValue3());
		dlv.setValue_change(gl.getValue_change());
		dlv.setReverting(gl.getReverting());
		dlv.setDebugList(gl.getDebugList());
	}
	
	/**
	 * 删除联动组
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delLinkGroup",method=RequestMethod.POST)
	@ResponseBody
	public Object delDevLink(@RequestBody Ids ids, HttpServletRequest request){
		if(ListUtil.notEmptyList(ids.getIds()))
			dlDao.delGroup(ids.getIds());
		return result.setStatus(0, "ok");
	}
}
