package com.cm.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.ListUtil;
import util.UtilMethod;

import com.cm.controller.ResultObj;
import com.cm.dao.DevLinkDao;
import com.cm.entity.DevAction;
import com.cm.entity.vo.DevLinkVo;
import com.cm.entity.vo.DevLogicVo;
import com.cm.security.LoginManage;

@Service
public class LinkService {

	@Autowired
	private ResultObj result;
	@Autowired
	private DevLinkDao dlDao;
	@Autowired
	private LoginManage loginManage;
	
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
	 * @return
	 */
	public Object setDevLink(DevLinkVo devLink, HttpServletRequest request){
//		try {
			if(devLink.getPid() >= 0){
				//插入联动组信息，并获取组ID
				DevLinkVo dlg = getGroup(devLink, request);
				//初始化列表
				initDataList();
				
				//设备逻辑构建
				List<DevLogicVo> listLgc = devLink.getListLgc();
				if(ListUtil.notEmptyList(listLgc)){
					setExps(listLgc, dlg);//构建联动组表达式
				}
				//增加或更新联动逻辑
				addUpLgcList(request);
				
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
//		} catch (Exception e) {
//			e.printStackTrace();
//			return result.setStatus(-1, "出错了！");
//		}
	}
	
	public void initDataList(){
		addList = new ArrayList<>();
		updateList = new ArrayList<>();
	}
	
	@SuppressWarnings("unchecked")
	public void addUpLgcList(HttpServletRequest request){
		if(ListUtil.notEmptyList(addList)){
			dlDao.addDevLgc(addList);
		}
			
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
	
	public DevLinkVo getGroup(DevLinkVo devLink, HttpServletRequest request){
		if(devLink.getId() > 0){
			dlDao.updateLgcGroup(devLink);
			if (UtilMethod.notEmptyList(devLink.getListLgc())) {//添加日志
				if (devLink.getListLgc().get(0).getScene() > 0 ){
					loginManage.addLog(request, JSONObject.fromObject(devLink).toString(), "修改情景模式：" + devLink.getName(), 171);
				} else {
					loginManage.addLog(request, JSONObject.fromObject(devLink).toString(), "修改联动控制：" + devLink.getName(), 171);
				}
			}
		}else{
			dlDao.addLgcGroup(devLink);
			if (UtilMethod.notEmptyList(devLink.getListLgc())) {//添加日志
				if (devLink.getListLgc().get(0).getScene() > 0 ){
					loginManage.addLog(request, JSONObject.fromObject(devLink).toString(), "增加情景模式：" + devLink.getName(), 1542);
				} else {
					loginManage.addLog(request, JSONObject.fromObject(devLink).toString(), "增加联动控制：" + devLink.getName(), 1542);
				}
			}
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
			if(dlv.getSole() == 1){//删除逻辑设备
				dlDao.delLgc(dlv.getId());
				if (dlv.getScene() > 0) {//情景模式包含设备2(dev2),特殊处理
					Integer id = dlDao.getDevLinkScene1(dlv.getId());
					if (id != null ) {
						dlDao.delLgc(id);
					}
				}
			}else if(dlv.getId() > 0){//更新逻辑设备
				setLogicExps(dlv);
				updateList.add(dlv);
				if (dlv.getScene() > 0) {//情景模式包含设备2(dev2),特殊处理
					Integer id = dlDao.getDevLinkScene1(dlv.getId());
					if (id!= null){
						DevLogicVo dlv2 = generateDlv2(dlv);
						dlv2.setId(id);
						updateList.add(dlv2);
					} 
				}
			}else{//添加逻辑设备
				dlv.setGroupId(dlg.getId());
				setLogicExps(dlv);
				if (dlv.getScene() > 0) {//情景模式包含设备2(dev2),特殊处理
					dlDao.addSingleDevLgc(dlv);
					DevLogicVo dlv2 = generateDlv2(dlv);
					addList.add(dlv2);
				} else {
					addList.add(dlv);
				}
			}
			//构建联动组的表达式
			setGrpupExps(listLgc.size(), dlg, count, dlv);
		}
		dlDao.updateLgcGroup(dlg);
	}
	
	public DevLogicVo generateDlv2 (DevLogicVo dlv) {
		DevLogicVo dlv2 = new DevLogicVo();
		dlv2.setGroupId(dlv.getGroupId());
		dlv2.setLgcOperator2(dlv.getId()+"");
		dlv2.setDev(dlv.getDev2());
		dlv2.setDev2(dlv.getDev());
		dlv2.setUid(dlv.getUid2());
		dlv2.setUid2(dlv.getUid());
		dlv2.setLgcOperator(null);
		dlv2.setLgcDevid(0);
		dlv2.setLgcOperator3("no return");
		return dlv2;
	}
	
	public void setGrpupExps(int size, DevLinkVo dlg, int count, DevLogicVo dlv){
		if(count == 1) {
			dlg.setLgcExps(null);
			dlg.setRepowerExps(null);
		}
		if(dlv.getSole() == 1) {
			if(count == size) dlg.setLgcExps(dlg.getLgcExps().concat("].count(True) >= ").concat(dlg.getLeastNum()+""));
			return;//跳过删除了的设备表达式
		}
		if(dlv.getScene() == 2){//情景模式2
			dlg.setLgcExps(dlv.getLgcExps().concat("and(").concat(dlv.getUid2()).concat("_C == 0)"));
		}else if(size > 0 && size >= dlg.getLeastNum()){//设备数大于条件数
			if(count == 1 || dlg.getLgcExps() == null){
				dlg.setLgcExps("[".concat(dlv.getLgcExps()));
				if(UtilMethod.notEmptyStr(dlv.getLgcOperator2()) || UtilMethod.notEmptyStr(dlv.getLgcOperator3())
						|| (UtilMethod.notEmptyStr(dlv.getDebugList()) && !dlv.getDebugList().equals("[]")))
					dlg.setRepowerExps(dlv.getRepowerExps());
			}else{
				if (dlg.getLgcExps() != null) dlg.setLgcExps(dlg.getLgcExps().concat(",").concat(dlv.getLgcExps()));
				else dlg.setLgcExps(dlv.getLgcExps());
				if(UtilMethod.notEmptyStr(dlv.getLgcOperator2()) || UtilMethod.notEmptyStr(dlv.getLgcOperator3())
						|| (UtilMethod.notEmptyStr(dlv.getDebugList()) && !dlv.getDebugList().equals("[]"))){
					String reStr = UtilMethod.notEmptyStr(dlg.getRepowerExps()) ? 
							dlg.getRepowerExps().concat(" and ").concat(dlv.getRepowerExps()) : dlv.getRepowerExps();
					dlg.setRepowerExps(reStr);
				}
			}
			
			if(count == size){
				dlg.setLgcExps(dlg.getLgcExps().concat("].count(True) >= ").concat(dlg.getLeastNum()+""));
			}
		}
	}
	
	public void setSwitchRep(DevLogicVo dlv){
		if(UtilMethod.notEmptyStr(dlv.getLgcOperator()) && dlv.getType() == 25 && !(dlv.getScene() > 0)){
			dlv.setLgcOperator2("==");
			dlv.setValue2(Math.abs(dlv.getValue() - 1));
		}
	}
	
	public void setLogicExps(DevLogicVo dlv){
		dlv.setLgcExps(null);
		dlv.setRepowerExps(null);
		//监测值表达式构建
		if(UtilMethod.notEmptyStr(dlv.getDev2()) && dlv.getScene() != 2){//设备之间比较
			dlv.setLgcExps("("+dlv.getUid() + dlv.getLgcOperator()+dlv.getUid2()+")");
		} else if (UtilMethod.notEmptyStr(dlv.getLgcOperator()))//设备与值比较
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
		if (UtilMethod.notEmptyStr(debug) && !debug.equals("[]")){
			String str = dlv.getLgcExps() == null ? "("+dlv.getUid() + "_D in " + debug+")" 
					: dlv.getLgcExps() + " or (" +dlv.getUid() + "_D in " + debug+")";
			dlv.setLgcExps(str);
			String re = dlv.getRepowerExps() == null ? "("+dlv.getUid() + "_D not in " + debug+")" 
					: dlv.getRepowerExps() + " and ("+dlv.getUid() + "_D not in " + debug+")";
			dlv.setRepowerExps(re);
		}
		//监测值变化
		if(dlv.getValue_change() != null){
			if(dlv.getValue_change() == 0 || dlv.getValue_change() == 1 || dlv.getValue_change() == 2 || dlv.getValue_change() == 3){
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
		
		//值反向表达式
		if(!(dlv.getScene() > 0) && UtilMethod.notEmptyStr(dlv.getLgcOperator2())){
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
}
