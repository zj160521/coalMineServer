package util;

import java.util.ArrayList;
import java.util.List;

import com.cm.controller.ResultObj;
import com.cm.entity.vo.Searchform;

public class PageHelper {
	
	public static List<Object> pageHelper(List<?> resultList, Searchform searchform){
		
		ArrayList<Object> list = new ArrayList<Object>();
		
		int recordNum = (searchform.getCur_page()-1) * searchform.getPage_rows();
		for(int i = 0; i < searchform.getPage_rows(); i++){
			if(i + recordNum >= resultList.size()) break;
			Object object = resultList.get(i + recordNum);
			list.add(object);
		}
		
		int totalPages = 0;
		
		totalPages = (resultList.size()%searchform.getPage_rows() > 0) ? resultList.size()/searchform.getPage_rows() + 1 
				: resultList.size()/searchform.getPage_rows();
		
		searchform.setTotal_pages(totalPages);
		searchform.setTotal_record(resultList.size());
		
		return list;
	}
	
	public static Object controllerReturnMethod(List<?> resultList , Searchform searchform, ResultObj result){
		if (resultList == null || resultList.isEmpty()) {
			searchform.setTotal_pages(0);
			searchform.setTotal_record(0);
			result.put("data", new ArrayList<>());
			result.put("searchform", searchform);
			return result.setStatus(0, "ok");
		} else {
			List<Object> list = PageHelper.pageHelper(resultList, searchform);
			result.put("data", list);
			result.put("searchform", searchform);
		}
		return result.setStatus(0, "ok");
	}
}
