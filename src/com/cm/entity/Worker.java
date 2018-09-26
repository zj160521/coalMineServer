package com.cm.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工实体类
 * @author hetaiyun
 *
 */
public class Worker {
	
	private int id;
	private String num;//工号
	private String name;//姓名
	private String rfcard_id;//卡号
	private int gender;//性别
	private int proplesid;//民族
	private String propname;
	private int posta;//政治面貌
	private int depart_id;//部门
	private String departname;
	private int worktype_id;//工种
	private String worktypename;
	private int job_id;//职称
	private String jobname;
	private int shifts;//班制
	private int work_time;//每班工作时间
	private int work_hour_cal;//工时计算
	private String birthday;//生日
	private String idnumber;//身份证号
	private String grad_sch;//毕业学校
	private String phone;//联系电话
	private String mobilephone;//手机
	private String bloodtype;//血型
	private String marriage;//婚姻
	private String certificate;//证件名称及号码
	private String effectivedate;//证件有效日期 
	private String address;//家庭地址
	private int duty_id;//职务ID
	private String duty;//职务名称
	private int shift;//班次
	private int num_month;//每月下井次数
	private String entry_time;//入职时间
	private int contract_way;//合同方式
	private String contname;
	private int height;//身高
	private int weight;//体重
	private int edu_back;//学历
	private String eduname;
	private String majors;//专业
	private String lamp_brand;//灯牌号
	private int isuse;//在职状态
	private int grade;//级别
	private String gradename;
	private int workplace_id;//工作地点ID
	private String workplace;//工作地点
	private String entranceGuardNum;//门禁卡号
	private int[] ids;
	private String[] rfcard_ids;
	private List<Integer> departs = new ArrayList<Integer>();
	private String battary;
	private String in_time;
	private String status;
	private int classes_id;
	private String week;
	private String dayrange;
	private List<Unattendance> list;
	private int counts;
	
	public String[] getRfcard_ids() {
		return rfcard_ids;
	}
	public void setRfcard_ids(String[] rfcard_ids) {
		this.rfcard_ids = rfcard_ids;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	public List<Unattendance> getList() {
		return list;
	}
	public void setList(List<Unattendance> list) {
		this.list = list;
	}
	public int getClasses_id() {
		return classes_id;
	}
	public void setClasses_id(int classes_id) {
		this.classes_id = classes_id;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getDayrange() {
		return dayrange;
	}
	public void setDayrange(String dayrange) {
		this.dayrange = dayrange;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIn_time() {
		return in_time;
	}
	public void setIn_time(String in_time) {
		this.in_time = in_time;
	}
	public String getBattary() {
		return battary;
	}
	public void setBattary(String battary) {
		this.battary = battary;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRfcard_id() {
		return rfcard_id;
	}
	public void setRfcard_id(String rfcard_id) {
		this.rfcard_id = rfcard_id;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getProplesid() {
		return proplesid;
	}
	public void setProplesid(int proplesid) {
		this.proplesid = proplesid;
	}
	public String getPropname() {
		return propname;
	}
	public void setPropname(String propname) {
		this.propname = propname;
	}
	public int getPosta() {
		return posta;
	}
	public void setPosta(int posta) {
		this.posta = posta;
	}
	public int getDepart_id() {
		return depart_id;
	}
	public void setDepart_id(int depart_id) {
		this.depart_id = depart_id;
	}
	public String getDepartname() {
		return departname;
	}
	public void setDepartname(String departname) {
		this.departname = departname;
	}
	public int getWorktype_id() {
		return worktype_id;
	}
	public void setWorktype_id(int worktype_id) {
		this.worktype_id = worktype_id;
	}
	public String getWorktypename() {
		return worktypename;
	}
	public void setWorktypename(String worktypename) {
		this.worktypename = worktypename;
	}
	public int getJob_id() {
		return job_id;
	}
	public void setJob_id(int job_id) {
		this.job_id = job_id;
	}
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public int getShifts() {
		return shifts;
	}
	public void setShifts(int shifts) {
		this.shifts = shifts;
	}
	public int getWork_time() {
		return work_time;
	}
	public void setWork_time(int work_time) {
		this.work_time = work_time;
	}
	public int getWork_hour_cal() {
		return work_hour_cal;
	}
	public void setWork_hour_cal(int work_hour_cal) {
		this.work_hour_cal = work_hour_cal;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getIdnumber() {
		return idnumber;
	}
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	public String getGrad_sch() {
		return grad_sch;
	}
	public void setGrad_sch(String grad_sch) {
		this.grad_sch = grad_sch;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getDuty_id() {
		return duty_id;
	}
	public void setDuty_id(int duty_id) {
		this.duty_id = duty_id;
	}

	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public int getShift() {
		return shift;
	}
	public void setShift(int shift) {
		this.shift = shift;
	}
	public int getNum_month() {
		return num_month;
	}
	public void setNum_month(int num_month) {
		this.num_month = num_month;
	}
	public String getEntry_time() {
		return entry_time;
	}
	public void setEntry_time(String entry_time) {
		this.entry_time = entry_time;
	}
	public int getContract_way() {
		return contract_way;
	}
	public void setContract_way(int contract_way) {
		this.contract_way = contract_way;
	}
	public String getContname() {
		return contname;
	}
	public void setContname(String contname) {
		this.contname = contname;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getEdu_back() {
		return edu_back;
	}
	public void setEdu_back(int edu_back) {
		this.edu_back = edu_back;
	}
	public String getEduname() {
		return eduname;
	}
	public void setEduname(String eduname) {
		this.eduname = eduname;
	}
	public String getMajors() {
		return majors;
	}
	public void setMajors(String majors) {
		this.majors = majors;
	}
	public String getLamp_brand() {
		return lamp_brand;
	}
	public void setLamp_brand(String lamp_brand) {
		this.lamp_brand = lamp_brand;
	}
	public int getIsuse() {
		return isuse;
	}
	public void setIsuse(int isuse) {
		this.isuse = isuse;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getGradename() {
		return gradename;
	}
	public void setGradename(String gradename) {
		this.gradename = gradename;
	}
	public int[] getIds() {
		return ids;
	}
	public void setIds(int[] ids) {
		this.ids = ids;
	}
	public List<Integer> getDeparts() {
		return departs;
	}
	public void setDeparts(List<Integer> departs) {
		this.departs = departs;
	}
	public int getWorkplace_id() {
		return workplace_id;
	}
	public void setWorkplace_id(int workplace_id) {
		this.workplace_id = workplace_id;
	}
	public String getWorkplace() {
		return workplace;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}
	public String getEntranceGuardNum() {
		return entranceGuardNum;
	}
	public void setEntranceGuardNum(String entranceGuardNum) {
		this.entranceGuardNum = entranceGuardNum;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getBloodtype() {
		return bloodtype;
	}
	public void setBloodtype(String bloodtype) {
		this.bloodtype = bloodtype;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public String getEffectivedate() {
		return effectivedate;
	}
	public void setEffectivedate(String effectivedate) {
		this.effectivedate = effectivedate;
	}

	
	
}
