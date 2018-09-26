package util;

import java.util.List;

public class ListUtil {
	public static boolean notEmptyList(List<?> list){
		return (list != null && list.size() > 0) ? true : false;
	}
}
