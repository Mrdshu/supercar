package com.xw.supercar.util;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

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
	 * 获取时间戳+随机数的 16位有规律数字
	 * @return
	 *
	 * @author wsz 2017-09-18
	 */
	public static String getTimeStampRandom() {
		String format = "yyyyMMddHHmmss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String time = dateFormat.format(new Date());
		time = time + genRandomPwd(2);
				
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
	 * 生成固定长度的随机密码（由字符串，数字以及特殊字符构成）
	 * @author wangsz
	 */
	public static String genRandomPwd(int pwd_len) {
		final int maxNum = 58;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
				'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z' , '1', '2', '3', '4', '5', '6', '7', '8', '9' ,'@','#','.'};
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum))%57; // 生成的数最大为58-1
			pwd.append(str[i]);
			count++;
		}
		return pwd.toString();
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
	 * 计算两个日期(字符串)之间相差的天数 
     * @param startTime 较小的时间 (字符串)
     * @param endTime  较大的时间 (字符串)
     * @param format  字符串日期格式，默认为yyyy-MM-dd
     * @return 相差天数   -1为获取失败
     * @throws ParseException
	 * @autohr shuzheng_wang  2017-11-23 14:55
	 */
	public static int daysBetween(String startTime,String endTime,String format){
		if(StringUtils.isEmpty(format))
			format = "yyyy-MM-dd";
		SimpleDateFormat sdf=new SimpleDateFormat(format);  
		Date sdate = null;
		Date edate = null;
		try {
			sdate = sdf.parse(startTime);
			edate=sdf.parse(endTime);  
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}  
        
        return daysBetween(sdate, edate);
	}
	
	/**
     * 计算两个日期之间相差的天数  
     * @param startTime 较小的时间 
     * @param endTime  较大的时间 
     * @return 相差天数  -1为获取失败
     * @throws ParseException
     * @autohr shuzheng_wang  2017-11-23 13:45
     */
    public static int daysBetween(Date startTime,Date endTime)  
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        try {
			startTime=sdf.parse(sdf.format(startTime));
			endTime=sdf.parse(sdf.format(endTime));  
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(startTime);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(endTime);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }
	
	public static void main(String[] args) {
		System.out.println(getTimeStampRandom());
	}
	
}
