package com.atguigu.crm.orm;


import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * ���Թ�����. 
 * ��װ�����ԵĲ�ѯ��Ϣ
 */
public class PropertyFilter {

	//������
	private String propertyName;
	//����ֵ
	private Object propertyVal;
	//Ŀ�����Ե�����
	private Class propertyType;
	//�Ƚϵķ�ʽ
	private MatchType matchType;
	
	public PropertyFilter(String fieldName, Object fieldValue){
		//LIKES_title
		String str1 = StringUtils.substringBefore(fieldName, "_"); //LIKES
		String matchTypeCode = StringUtils.substring(str1, 0, str1.length() - 1); //LIKE
		String propertyTypeCode = StringUtils.substring(str1, str1.length() - 1); //S
		
		this.matchType = Enum.valueOf(MatchType.class, matchTypeCode); //LIKE ��Ӧ��ö�ٶ���
		PropertyType propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode);
		this.propertyType = propertyType.getPropertyType();
		
		this.propertyName = StringUtils.substringAfterLast(fieldName, "_");
		
		this.propertyVal = fieldValue;
	}
	
	public enum MatchType{
		EQ, GT, GE, LT, LE, LIKE;
	}
	
	public enum PropertyType{
		I(Integer.class), F(Float.class), S(String.class), D(Date.class), O(Object.class);
		
		private Class propertyType;
		
		private PropertyType(Class propertyType){
			this.propertyType = propertyType;
		}
		
		public Class getPropertyType() {
			return propertyType;
		}
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Object getPropertyVal() {
		return propertyVal;
	}

	public Class getPropertyType() {
		return propertyType;
	}

	public MatchType getMatchType() {
		return matchType;
	}
	
	
}
