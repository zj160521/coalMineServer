package com.cm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cm.dao.PersonAlarmSearchDao;
import com.cm.entity.Person;
import com.cm.entity.PersonAlarmSearch;
import com.cm.entity.vo.OverManVo;
import com.cm.entity.vo.OvertimeAlarmVo;
import com.cm.entity.vo.WorkerAreaWarnVo;
import com.cm.entity.vo.WorkerExitWarnVo;
import com.cm.entity.vo.WorkerUnconnectionVo;

@Service("personAlarmSearchService")
public class PersonAlarmSearchService {
	
	@Autowired
	private PersonAlarmSearchDao dao;
	
	
		//查询区域限制报警人员
		public List<WorkerAreaWarnVo> getWorkerAreaWarn(PersonAlarmSearch search){
			List<WorkerAreaWarnVo> list = dao.getWorkerAreaWarn(search);
			for(WorkerAreaWarnVo l:list){
				if(l.getName()==null||l.getName()==""){
					l.setName("临时卡");
				}
				if(l.getIsuse()>1){
					l.setName(l.getName()+"(离职)");
				}
			}
			return list;
		}
		
		public List<WorkerAreaWarnVo> getallWorkerAreaWarn(PersonAlarmSearch search){
			List<WorkerAreaWarnVo> list = dao.getWorkerAreaWarn(search);
			for(WorkerAreaWarnVo l:list){
				if(l.getName()==null||l.getName()==""){
					l.setName("临时卡");
				}
				if(l.getIsuse()>1){
					l.setName(l.getName()+"(离职)");
				}
			}
			return list;
		}
		
		public int getWorkerAreaWarnCount(PersonAlarmSearch search){
			return dao.getWorkerAreaWarnCount(search);
		}
		
		//查询门禁异常报警人员
		public List<WorkerExitWarnVo> getWorkerExitWarn(PersonAlarmSearch search){
			List<WorkerExitWarnVo> list = dao.getWorkerExitWarn(search);
			for(WorkerExitWarnVo l:list){
				if(l.getName()==null||l.getName()==""){
					l.setName("临时卡");
				}
				if(l.getIsuse()>1){
					l.setName(l.getName()+"(离职)");
				}
			}
			return list;
		}
		
		public List<WorkerExitWarnVo> getallWorkerExitWarn(PersonAlarmSearch search){
			List<WorkerExitWarnVo> list = dao.getallWorkerExitWarn(search);
			for(WorkerExitWarnVo l:list){
				if(l.getName()==null||l.getName()==""){
					l.setName("临时卡");
				}
				if(l.getIsuse()>1){
					l.setName(l.getName()+"(离职)");
				}
			}
			return list;
		}
		
		public int getWorkerExitWarnCount(PersonAlarmSearch search){
			return dao.getWorkerExitWarnCount(search);
		}
		
		//查询失联报警人员
		public List<WorkerUnconnectionVo> getWorkerUnconnection(PersonAlarmSearch search){
			List<WorkerUnconnectionVo> list = dao.getWorkerUnconnection(search);
			for(WorkerUnconnectionVo l:list){
				if(l.getName()==null||l.getName()==""){
					l.setName("临时卡");
				}
				if(l.getIsuse()>1){
					l.setName(l.getName()+"(离职)");
				}
			}
			return list;
		}
		
		public List<WorkerUnconnectionVo> getallWorkerUnconnection(PersonAlarmSearch search){
			List<WorkerUnconnectionVo> list = dao.getWorkerUnconnection(search);
			for(WorkerUnconnectionVo l:list){
				if(l.getName()==null||l.getName()==""){
					l.setName("临时卡");
				}
				if(l.getIsuse()>1){
					l.setName(l.getName()+"(离职)");
				}
			}
			return list;
		}
		
		public int getWorkerUnconnectionCount(PersonAlarmSearch search){
			return dao.getWorkerUnconnectionCount(search);
		}
		
		
		//查询区域、井下超时报警人员
		public List<OvertimeAlarmVo> getOvertimeAlarm(PersonAlarmSearch search){
			List<OvertimeAlarmVo> list = dao.getOvertimeAlarm(search);
			for(OvertimeAlarmVo l:list){
				l.setTimeout_time(l.getTimeout_time()+"分钟");
				if(l.getName()==null||l.getName()==""){
					l.setName("临时卡");
				}
				if(l.getIsuse()>1){
					l.setName(l.getName()+"(离职)");
				}
			}
			return list;
		}
		
		public List<OvertimeAlarmVo> getallOvertimeAlarm(PersonAlarmSearch search){
			List<OvertimeAlarmVo> list = dao.getallOvertimeAlarm(search);
			for(OvertimeAlarmVo l:list){
				l.setTimeout_time(l.getTimeout_time()+"分钟");
				if(l.getName()==null||l.getName()==""){
					l.setName("临时卡");
				}
				if(l.getIsuse()>1){
					l.setName(l.getName()+"(离职)");
				}
			}
			return list;
		}
		
		public int getOvertimeAlarmCount(PersonAlarmSearch search){
			return dao.getOvertimeAlarmCount(search);
		}
		
		//查询区域超员、井下超员报警人员
		public List<OverManVo> getOverMan(PersonAlarmSearch search){
			List<OverManVo> list = dao.getOverMan(search);
			for(OverManVo l:list){
				l.setOverNum(l.getPersonNum()-l.getMaxAllow());
				if(l.getCards()!=null){
					String[] cards = l.getCards().split(",");
					List<Person> list2 = dao.getWorkerName(cards);
					Map<Integer, Person> map = new HashMap<Integer, Person>();
					for(Person s:list2){
						map.put(s.getCard_id(), s);
						
					}
					List<Person> cardss = new ArrayList<Person>();
					for(int i=0;i<cards.length;i++){
						Person person = new Person();
						int card_id = Integer.parseInt(cards[i]);
						person.setCard_id(card_id);
						Person name = map.get(card_id);
						if(name==null){
							person.setName("临时卡");
						}else{
							person.setName(name.getName());
						}
						cardss.add(person);
					}
					
					l.setList(cardss);
				}
			}
			return list;
		}
		
		public List<OverManVo> getallOverMan(PersonAlarmSearch search){
			List<OverManVo> list = dao.getallOverMan(search);
			for(OverManVo l:list){
				if(l.getCards()!=null){
					String [] cards = l.getCards().split(",");
					l.setCardss(cards);
				}
			}
			return list;
		}
		
		public int getOverManCount(PersonAlarmSearch search){
			return dao.getOverManCount(search);
		}
}
