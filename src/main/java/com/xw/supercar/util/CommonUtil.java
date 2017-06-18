package com.xw.supercar.util;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  公共工具类
 *  @author wangsz
 */
public class CommonUtil {
	
	/**
	 * 获取异常的详细信息，转换为字符串
	 * @author wangsz  Jan 16, 2017 2:14:57 PM
	 */
	public static String getExceptionInfo(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		// 关闭存在优化，以后再写
		closeIO(sw, null, null, null);
		closeIO(pw, null, null, null);
		
		//保证异常信息长度在1000以内
		String exceptionInfo = sw.toString();
		if(exceptionInfo.length() >= 1000){
			exceptionInfo = exceptionInfo.substring(0,1000);
		}
		return exceptionInfo;
	}
	
	/**
	 * 关闭io流
	 *
	 * @author wangsz  Jan 17, 2017 3:04:20 PM
	 */
	public static void closeIO(Writer writer,Reader reader,InputStream in,OutputStream out) {
		try {
			if(writer != null)
				writer.close();
			if(reader != null)
				reader.close();
			if(in != null)
				in.close();
			if(out != null)
				out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将日期按指定格式转换为字符串
	 * @param format 格式，如：yyyy/MM/dd HH:mm:ss
	 * @param date 时间
	 * @return
	 *
	 * @author wangsz  Mar 21, 2017 10:13:47 AM
	 */
	public static String transferDateToString(String format,Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String time = dateFormat.format(date);
		
		return time;
	}
	
	/**
	 * 生成指定长度的随机数
	 * @param length 随机数长度
	 * 
	 * @author wangsz  Mar 21, 2017 10:31:15 AM
	 */
	public static String generateRandom(int length) {
		int a = 1;
		for (int i = 0; i < length; i++) {
			a *= 10;
		}
		return (int)(Math.random()*a) + "";
	}
	
	/**
	 * @param message cannot be null
	 * @return MD5 encoded string with all capital letters.
	 */
	public static String getMD5(String message) {
		MessageDigest messageDigest = null;
		StringBuffer md5StrBuff = new StringBuffer();
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(message.getBytes("UTF-8"));

			byte[] byteArray = messageDigest.digest();
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
					md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
				else
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return md5StrBuff.toString().toUpperCase();
	}
	
	/**
	 * 比较两个集合，获取增加的元素集合以及删除的元素集合
	 * @param oldElements 被比较的集合
	 * @param newElements 比较的集合
	 * @param addElements  比较后增加的元素集合
	 * @param deleteElements  比较后删除的元素集合
	 *
	 * @author wangsz  Mar 29, 2017 4:23:32 PM
	 */
	public void compareList(List<String> oldElements,List<String> newElements,List<String> addElements,List<String> deleteElements) {
		if(oldElements == null || newElements == null || addElements == null || deleteElements == null)
			return ;
		
		Map<String, String> olElementsMap = new HashMap<String, String>();
		List<String> oldCopyElements = new ArrayList<String>(oldElements);
		//将oldElements转换为Map
		for (String oldElement : oldElements) {
			olElementsMap.put(oldElement, "");
		}
		//迭代newElements，若oldElements没有的元素则放入增量集合
		for (String newElement : newElements) {
			if(olElementsMap.get(newElement) == null)
				addElements.add(newElement);
			else 
				oldCopyElements.remove(newElement);
		}
		
		deleteElements.addAll(oldCopyElements);
	}
	
}
