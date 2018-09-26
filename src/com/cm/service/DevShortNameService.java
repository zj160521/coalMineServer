package com.cm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class DevShortNameService {

	public static String getShortName(String devTypeName){
		List<String> listWord = new ArrayList<String>();
		listWord.add("传感器");
		listWord.add("人员定位");
		listWord.add("分站");
		listWord.add("多参数传感器");
		listWord.add("矿用本安型");
		
		String cutOffWord = null;
		for(String cutWord : listWord){
			cutOffWord = cutOffWord(devTypeName, cutWord);
			if(cutOffWord != null){
				return cutOffWord;
			}
		}
		return devTypeName;
	}
	
	public static String cutOffWord(String devTypeName,String cutWord){
		if(devTypeName.contains(cutWord)){
			String replace = devTypeName.replace(cutWord, "");
			return replace;
		}else{
			return null;
		}
		
	}
}
