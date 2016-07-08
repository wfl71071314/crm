package com.atguigu.crm.handler;

import java.util.Map;


public class BaseHandler {

	
	public String encodeParamsToQueryString(Map<String, Object> params) {
		StringBuilder result = new StringBuilder();
		
		for(Map.Entry<String, Object> entry: params.entrySet()){
			String key = entry.getKey();
			Object val = entry.getValue();
			
			if(val == null || val.toString().trim().equals("")){
				continue;
			}
			
			result.append("&")
			      .append("search_")
			      .append(key)
			      .append("=")
			      .append(val);
		}
		
		return result.toString();
	}
	
}
