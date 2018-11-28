package com.xw.supercar.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexUtil {

	//返回完全匹配比较的boolean值
	/**
	 * 匹配则返回true，否则返回false
	 * 
	 * @param Matcher
	 *            匹配规则 text 要比较的字符串
	 */
	public static boolean check(String matcher, String text) {
	    if(matcher==null || text==null){
	        return false;
	    }
		//匹配规则
		Pattern p = Pattern.compile(matcher);
		Matcher m = p.matcher(text);
		return m.matches();
	}
	
	/**
	 * 	根据身份证，得到性别
	 * 	15位身份证号，倒数第一位能看出性别，男单数女双数。
		18位身份证号，倒数第二位能看出性别，男单数女双数。	
	 */
	public static String getGenderByCertNo(String cert_no){
		String strGender = "";
		if(cert_no == null || (cert_no.length() != 15 && cert_no.length() != 18)){
			return "";
		}
		if(cert_no.length() == 15){
			strGender = cert_no.substring(14);
		}else if(cert_no.length() == 18){
			strGender = cert_no.substring(16, 17);
		}
		
		if(check("^[02468]$", strGender)){
			return "female";
		}else{
			return "male";
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getGenderByCertNo("320311770706002"));
	}
}