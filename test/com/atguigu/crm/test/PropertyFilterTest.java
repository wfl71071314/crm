package com.atguigu.crm.test;

import java.util.Date;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.orm.PropertyFilter.MatchType;

public class PropertyFilterTest {

	public static void main(String[] args) {
		String fieldName = "LIKES_custName";
		Object fieldVal = "A";
		
		PropertyFilter filter = new PropertyFilter(fieldName, fieldVal);
		
		System.out.println(filter.getPropertyName());
		System.out.println(filter.getPropertyVal());
		System.out.println(filter.getMatchType());
		System.out.println(filter.getPropertyType());
		
		String code = "LIKE";
		//Enum.valueOf 方法可以把一个字符串转为对应的枚举类的对象!
		MatchType matchType = Enum.valueOf(MatchType.class, code);
		System.out.println(matchType.getClass());
		
		DateConverter dateConverter = new DateConverter();
		dateConverter.setPatterns(new String[]{"yyyy-MM-dd","yyyy-MM-dd hh:mm:ss"});
		ConvertUtils.register(dateConverter, Date.class);
		
		String dateStr = "1990-11-11";
		Object date = ConvertUtils.convert(dateStr, Date.class);
		System.out.println(date);
	}
	
}
