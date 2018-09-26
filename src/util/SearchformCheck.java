package util;

import com.cm.controller.ResultObj;
import com.cm.entity.vo.Searchform;

public class SearchformCheck {
	
	public static boolean searchformCheck(Searchform searchform, ResultObj result){
		if(searchform.getStarttime() == null 
				|| searchform.getStarttime().isEmpty() 
				|| searchform.getEndtime() == null 
				|| searchform.getEndtime().isEmpty())
			return true;
		else
			return false;
	}
}
