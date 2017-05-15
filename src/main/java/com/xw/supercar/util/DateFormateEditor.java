package com.xw.supercar.util;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * 日期格式化工具类
 * 类名: DateFormateEditor.java
 * 创建人: kim 
 * 创建时间：2016年4月21日 下午2:17:24 
 * @version 1.0.0
 */
public class DateFormateEditor extends PropertyEditorSupport{

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(StringUtils.isNotEmpty(text)){
			String pattern = "yyyy-MM-dd";
			if(text.indexOf(":")!=-1)pattern = "yyyy-MM-dd HH:mm:ss";
			try {
				setValue(new SimpleDateFormat(pattern).parse(text));
			} catch (ParseException e) {
				setValue(null);
			}
		}else{
			setValue(null);
		}
	}
}
