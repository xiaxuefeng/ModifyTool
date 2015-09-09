package com.prize.modifytool.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 判断字符串是否只包含数值
	 * @param str
	 * @return
	 */
	public static boolean isDigital(String str) {
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher(str);
		boolean result = matcher.matches();
		return result;
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null || str.length()==0){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param original [原始字符串]
	 * @param offset [插入的位置]
	 * @param destination [需要插入的子字符串]
	 * @return
	 */
	public static String insertString(String original,int offset,String destination){
		return new StringBuilder(original).insert(offset,destination).toString();
	}
}
