package com.xw.supercar.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateUtil
{
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	final static String[] weeks_1 = new String[] { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
	final static String[] weeks_2 = new String[] { "日", "一", "二", "三", "四", "五", "六" };
	public static final String PATTERN_CLASSICAL = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_CLASSICAL_SIMPLE = "yyyy-MM-dd";

	/**
	 * 将所有格式的日期都转换成统一的yyyy-MM-dd字符串
	 * @param obj 时间
	 * @author fuji_xu 2018-10-17
	 */
	public static String allConvertToDateString(Object obj){
		try{
			if(obj == null){
				return null;
			}
			//如果为字符串
			if(obj instanceof String){
				//如果为时间戳
				if(StringUtils.isNumeric((String)obj)){
					return getDatelongToString(Long.valueOf((String)obj));
				}
				//如果为yyyy-MM-dd HH:mm:ss格式
				else if(DateUtil.isValidDate((String)obj,"yyyy-MM-dd HH:mm:ss")){
					return getDateStringToString((String)obj);
				}
				//如果为yyyy-MM-dd格式
				else if(DateUtil.isValidDate((String)obj,"yyyy-MM-dd")){
					return (String)obj;
				}
			}
			//如果为时间戳格式
			else if(obj instanceof Long){
				return getDatelongToString((Long)obj);
			}
			//如果为日期格式
			else if(obj instanceof Date){
				return getDateDateToString((Date)obj);
			}
		}catch(Exception e){
			return null;
		}
		return null;
	}

	/**
	 * 计算两个日期相差多少月
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static int getMonthSpace(String date1, String date2) throws ParseException
	{
		int result_y = 0;
		int result_m = 0;
		if(StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2)){
		    return result_m;
        }
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(sdf.parse(date1));
			c2.setTime(sdf.parse(date2));
			result_y = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);

			if (result_y != 0)
			{
				result_m = result_y * 12;
			}
			result_m = result_m + (c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH));
			return result_m;
		} catch (Exception e)
		{
			logger.error(e.getMessage());

		}
		return result_m;
	}

	/**
	 * 计算d1到d2相差多少天
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getTimeDifferDays(Date d1, Date d2)
	{
		long time1 = d1.getTime();
		long time2 = d2.getTime();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算d1到d2相差多少天
	 *
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getTimeDifferDays(String d1, String d2, String format)
	{
	    if(StringUtils.isEmpty(d1) || StringUtils.isEmpty(d2) ){
	        return -1;
        }

		if (StringUtils.isEmpty(format))
			format = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date sdate = null;
		Date edate = null;
		try
		{
			sdate = sdf.parse(d1);
			edate = sdf.parse(d2);
		} catch (ParseException e)
		{
			logger.error(e.getMessage());
			return -1;
		}

		return getTimeDifferDays(sdate, edate);
	}

	/**
	 * 验证日期是否合法
	 *
	 * @param date
	 * @return 验证通过返回true date格式:2007-01-01
	 */
	public static boolean verifyDate(String date)
	{
		if (date == null)
		{
			return false;
		}
		String macther = "^\\d{4}-((0?([1-9]))|(1[0-2]))-((0?([1-9]))|([1|2]([0-9]))|(3[0|1]))$";
		return RegexUtil.check(macther, date);
	}

	/**
	 * 验证日期时间是否合法
	 *
	 * @param dateString
     * @return 验证通过返回true date格式:2007-01-01 12:12:12
	 */
	public static boolean verifyDateString(String dateString)
	{
		if (dateString == null)
		{
			return false;
		}
		String macther = "^\\d{4}-((0?([1-9]))|(1[0-2]))-((0?([1-9]))|([1|2]([0-9]))|(3[0|1])) ((0?([0-9]))|(1([0-9]))|(2[0-3])):(([0-5]?([0-9]))):(([0-5]?([0-9])))$";
		return RegexUtil.check(macther, dateString);
	}

	/**
	 * 计算相差的月份数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int computeMonthSpace(Date date1, Date date2)
	{
		int result = 0;
		int flag = 0;// 日期标记
		try
		{
			if (date1 == null || date2 == null)
			{
				return -1;
			}

			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(date1);

			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(date2);

			if (objCalendarDate2.equals(objCalendarDate1))
				return 0;
			// 确保日期顺序
			if (objCalendarDate1.after(objCalendarDate2))
			{
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			// 判断日期
			if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH))
				flag = 1;
			// 计算月份差
			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR))
				result = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR)) * 12 + objCalendarDate2.get(Calendar.MONTH) - flag) - objCalendarDate1.get(Calendar.MONTH);
			else
				result = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH) - flag;
		} catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		return result;
	}

	/**
	 * 计算相差的月份数
	 * 
	 * @param strPreDate
     * @param strDate
     * @return -1 前一个日期大于后一个；-2 参数为空； -3 参数长度不对； -4 参数转换数字错误； other 相差的月份数
	 */
	public static int computeMonthSpace(String strPreDate, String strDate)
	{
		// 参数为空
		if (StringUtils.isEmpty(strDate) || StringUtils.isEmpty(strPreDate))
		{
			return -2;
		}

		// 长度不等于6位
		if (strPreDate.length() != 6 || strDate.length() != 6)
		{
			return -3;
		}

		try
		{
			if (Integer.parseInt(strDate) < Integer.parseInt(strPreDate))
			{
				return -1;
			}
			int pre_year = Integer.parseInt(strPreDate.substring(0, 4));
			int pre_month = Integer.parseInt(strPreDate.substring(4));

			int year = Integer.parseInt(strDate.substring(0, 4));
			int month = Integer.parseInt(strDate.substring(4));

			return ((year - pre_year) * 12 + month) - pre_month;
		} catch (Exception e)
		{
			// 转换为数字错误
			return -4;
		}

	}

	/**
	 * 比较两个日期大小
	 *
	 * @param date1 被比较的时间
	 * @param date2	比较的时间
	 * @param diffUnit 返回的相差时间单位
	 * @return 根据diffUnit：'d'、'h'、'm'、's'来返回相差的时间
	 * @author fuji_xu
	 * @throws Exception Date不能为空
	 * 2018-08-14 11:12
	 */
	public static long compareDate(Date date1, Date date2,String diffUnit) throws Exception
	{
		if (date1 == null || date2 == null)
		{
			throw new Exception("Date参数不能为空");
		}
		long diffTime;
		Timestamp ts1 = new Timestamp(date1.getTime());
		Timestamp ts2 = new Timestamp(date2.getTime());
		diffTime = (ts2.getTime() - ts1.getTime())/1000;//秒
		long dividendNumber;
		//返回天单位
		if("d".equals(diffUnit)){
			dividendNumber = 60*60*24;
		}
		//返回时单位
		else if("h".equals(diffUnit)){
			dividendNumber = 60*60;
		}
		//返回分单位
		else if("m".equals(diffUnit)){
			dividendNumber = 60;
		}
		//返回秒单位
		else if("s".equals(diffUnit)){
			dividendNumber = 1;
		}else{
			throw new Exception("请提供有效的返回单位,diffUnit应为d/h/m/s");
		}

		return diffTime/dividendNumber;
	}

	/**
	 * 比较两个日期大小
	 *
	 * @param dateString1 被比较的时间 格式为yyyy-mm-dd
	 * @param dateString2	比较的时间
	 * @param diffUnit 返回的相差时间单位
	 * @return 根据diffUnit：'d'、'h'、'm'、's'来返回相差的时间
	 * @author fuji_xu
	 * @throws Exception 抛出Date param can't pass verify!
	 * 2018-08-14 11:12
	 */
	public static long compareDate(String dateString1, String dateString2,String diffUnit) throws Exception
	{
		if (!verifyDateString(dateString1) || !verifyDateString(dateString2))
		{
			throw new Exception("Date param can't pass verify!");
		}
		Date date1 = getDateTimeStringToDate(dateString1);
		Date date2 = getDateTimeStringToDate(dateString2);
		return compareDate(date1, date2,diffUnit);
	}

	/**
	 * 比较两个日期大小
	 *
	 * @param date1
	 * @param date2
	 * @return 返回数值是:1或2 返回0表示date1等于date2 返回1表示date1大于date2，date1日期越后，
	 *         返回2表示date2大于date1，date2日期越后
	 * @throws Exception
	 */
	public static int compareDate(String date1, String date2) throws Exception
	{
		if (!verifyDate(date1) || !verifyDate(date2))
		{
			throw new Exception("Date param can't pass verify!");
		}
		String[] d1 = date1.split("-");
		String[] d2 = date2.split("-");
		int flag = 0;
		/** 比较年 */
		if (Integer.parseInt(d1[0]) > Integer.parseInt(d2[0]))
		{
			flag = 1;
		} else if (Integer.parseInt(d2[0]) > Integer.parseInt(d1[0]))
		{
			flag = 2;
		} else if (Integer.parseInt(d1[1]) > Integer.parseInt(d2[1]))
		{
			/** 到这里说明年相等,那继续判断月 */
			flag = 1;
		} else if (Integer.parseInt(d2[1]) > Integer.parseInt(d1[1]))
		{
			flag = 2;
		} else if (Integer.parseInt(d1[2]) > Integer.parseInt(d2[2]))
		{
			/** 到这里说明年月相等,那继续判断日 */
			flag = 1;
		} else if (Integer.parseInt(d2[2]) > Integer.parseInt(d1[2]))
		{
			flag = 2;
		} else
		{
			/** 到这里,说明年月日都相等了 */
			flag = 0;
		}
		return flag;
	}

	/**
	 * 比较两个日期大小
	 * 
	 * @param date1
	 * @param date2
	 * @return 返回数值是:1或2 返回0表示date1等于date2 返回1表示date1大 返回2表示date2大
	 * @throws Exception
	 * @autohr shuzheng_wang 2017-11-27 09:58
	 */
	public static int compareDate(Date date1, Date date2) throws Exception
	{
		String date1String = getDateDateToString(date1);
		String date2String = getDateDateToString(date2);

		return compareDate(date1String, date2String);
	}

	/**
	 * 时间比较
	 * 
	 * @param date1
     * @param date2
     * @return 1 date1在date2之前，-1 date1在date2之后，0相等
	 */
	public static int compareDateHHMMSS(String date1, String date2)
	{
		try
		{
			DateFormat df = new SimpleDateFormat(PATTERN_CLASSICAL);
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime())
			{
				return 1;
			} else if (dt1.getTime() < dt2.getTime())
			{
				return -1;
			} else
			{// 相等
				return 0;
			}
		} catch (Exception e)
		{
			logger.error("compareDateHHMMSS error", e);
			return 0;
		}

	}

	/**
	 * 返回当前详细时间 格式如:2007-01-01
	 */
	public static String nowyyyyMMdd()
	{
		GregorianCalendar gerCalendar = new GregorianCalendar();
		Date date = gerCalendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * 返回当前详细时间 格式如:2007-01-01 10:23:30
	 */
	public static String now()
	{
		GregorianCalendar gerCalendar = new GregorianCalendar();
		Date date = gerCalendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 返回当前毫秒级的时间 格式如:2007-01-01 10:23:30:110
	 */
	public static String nowTimeMillis()
	{
		GregorianCalendar gerCalendar = new GregorianCalendar();
		Date date = gerCalendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		return sdf.format(date);
	}

	/**
	 * 返回当前日期 格式如:2007-01-01
	 */
	public static String date()
	{
		GregorianCalendar gerCalendar = new GregorianCalendar();
		Date date = gerCalendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * 对当前日期指定加法,返回yyyy-mm-dd
	 *
	 * @param filed
	 *            - exp:Calendar.YEAR [2007-4-19]
	 * @param val
	 *            - 值
	 * @return - add Date exp:add(Calendar.YEAR,-2) result:2005-4-19
	 */
	public static String add(int filed, int val)
	{
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		gc.add(filed, val);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH));
		return df.format(gc.getTime());
	}

	/**
	 * 对指定日期指定加法,返回yyyy-mm-dd
	 *
	 * @param filed
	 *            - exp:Calendar.YEAR [2007-4-19]
	 * @param val
	 *            - 值
	 * @return - add Date exp:add(Calendar.YEAR,-2) result:2005-4-19
	 */
	public static String addByDate(String date, int filed, int val)
	{
		Date date_d = getDateStringToDate(date);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar gc = Calendar.getInstance();
		gc.setTime(date_d);
		gc.add(filed, val);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH));
		return df.format(gc.getTime());
	}

	/**
	 * 对指定的日期指定加法,返回yyyy-mm-dd
	 *
	 * @param filed
	 *            - exp:Calendar.YEAR [2007-4-19]
	 * @param val
	 *            - 值
	 * @return - add Date exp:add(Calendar.YEAR,-2) result:2005-4-19
	 */
	public static String add(Date date, int filed, int val)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		gc.add(filed, val);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH));
		return df.format(gc.getTime());
	}

	/**
	 * 对指日期指定加法,返回yyyy-mm-dd hh:mm:ss
	 *
	 * @param date
	 *            指定日期 java.util.Date
	 * @param filed
	 *            - exp:Calendar.HOUR [2007-4-19 9:38:10]
	 * @param val
	 *            - 值
	 * @return - add Precision Date exp:add(Calendar.HOUR,-2) result:2007-4-19
	 *         7:38:10
	 */
	public static String addPrecision(Date date, int filed, int val)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		gc.add(filed, val);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH), gc.get(Calendar.HOUR_OF_DAY), gc.get(Calendar.MINUTE), gc.get(Calendar.SECOND));
		return df.format(gc.getTime());
	}

	/**
	 * 对指定的日期指定加法,返回Date
	 *
	 * @param filed
	 *            - exp:Calendar.YEAR [2007-4-19]
	 * @param val
	 *            - 值
	 * @return - add Date exp:add(Calendar.YEAR,-2) result:2005-4-19
	 */
	public static Date addTime(Date date, int filed, int val)
	{
		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		gc.add(filed, val);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH));
		return gc.getTime();
	}

	/**
	 * 获取文件名，格式：yyyyMMddHHmmss
	 */
	public static String fileName()
	{
		GregorianCalendar gerCalendar = new GregorianCalendar();
		Date date = gerCalendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}

	/**
	 * 根据参数转换为java.util.Date类型
	 *
	 * @param date
	 *            - 2007-01-10
	 * @return java.util.Date
	 */
	public static Date getDateStringToDate(String date)
	{
		Date d = null;
		try
		{
			String[] temp = date.split("-");
			Calendar c = Calendar.getInstance();
			c.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]) - 1, Integer.parseInt(temp[2]));
			d = c.getTime();
		} catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		return d;
	}

	/**
	 * 根据参数转换为java.util.Date类型
	 *
	 * @param date
	 *            - 字符串开始的时间如: 2007-01-10 10:05:10
	 * @return 根据参数转换为java.util.Date类型
	 */
	public static Date getDateTimeStringToDate(String date)
	{
		if (StringUtils.isEmpty(date))
			return null;

		Date d = null;
		try
		{
			String[] temp = date.split(" ");
			String[] date1 = temp[0].split("-");
			String[] time1 = temp[1].split(":");
			Calendar c = Calendar.getInstance();
			c.set(Integer.parseInt(date1[0]), Integer.parseInt(date1[1]) - 1, Integer.parseInt(date1[2]), Integer.parseInt(time1[0]), Integer.parseInt(time1[1]), (int) (Float.parseFloat(time1[2])));
			d = c.getTime();
		} catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		return d;
	}

	/**
	 * 把2007-01-10 10:05:10转换成2007-01-10
	 */
	public static String getDateStringToString(String date)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try
		{
			String[] temp = date.split(" ");
			String[] date1 = temp[0].split("-");
			c.set(Integer.parseInt(date1[0]), Integer.parseInt(date1[1]) - 1, Integer.parseInt(date1[2]));
		} catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		return df.format(c.getTime());
	}

	/**
	 * @param datetime
	 *            - long格式的时间
	 * @return 返回由long转换成String格式的yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTimelongToString(long datetime)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar gc = Calendar.getInstance();
		gc.setTime(new Date(datetime));
		return df.format(gc.getTime());
	}

	/**
     * 把毫秒级的long转换成String
	 * @return 返回由long转换成String格式的yyyy-MM-dd HH:mm:ss:SSS
     * @param datetime
	 */
	public static String getDateTimeMillislongToString(long datetime)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		Calendar gc = Calendar.getInstance();
		gc.setTime(new Date(datetime));
		return df.format(gc.getTime());
	}

	/**
	 * @param datetime
	 *            - long格式的时间(单位纳秒)
	 * @return 返回由long转换成String格式的yyyy-MM-dd
	 */
	public static String getDatelongToString(long datetime)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar gc = Calendar.getInstance();
		gc.setTime(new Date(datetime));
		return df.format(gc.getTime());
	}

	/**
	 * @param datetime
	 *            - long格式的时间（毫秒）
	 * @return 返回由long转换成String格式的yyyy-MM-dd
	 */
	public static String getDateMIlongToString(long datetime)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar gc = Calendar.getInstance();
		datetime = datetime * 1000;
		gc.setTime(new Date(datetime));
		return df.format(gc.getTime());
	}

	/**
	 * @param datetime
	 *            - long格式的时间（毫秒）
	 * @return 返回由long转换成String格式的yyyy-MM-dd
	 */
	public static String getDateMIlongToString(String datetime)
	{
		if (StringUtils.isEmpty(datetime))
			return "";

		long datetimeLong = Long.valueOf(datetime);
		return getDateMIlongToString(datetimeLong);
	}

	/**
	 * 由java.util.Date转换成String的yyyy-MM-dd
	 *
	 * @param date
	 *            - java.util.Date
	 * @return 返回String格式的yyyy-MM-dd
	 */
	public static String format(Date date, String pattern)
	{
		if (date == null || "".equals(date))
		{
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		return df.format(gc.getTime());
	}

	/**
	 * 由java.util.Date转换成String的yyyy-MM-dd
	 *
	 * @param date
	 *            - java.util.Date
	 * @return 返回String格式的yyyy-MM-dd
	 */
	public static String getDateDateToString(Date date)
	{
		if (date == null || "".equals(date))
		{
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		return df.format(gc.getTime());
	}

	/**
	 * 由java.util.Date转换成String的yyyy-MM-dd HH:mm:ss
	 *
	 * @param date
	 *            - java.util.Date
	 * @return 返回String格式的yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTimeDateToString(Date date)
	{
		if (date == null || "".equals(date))
		{
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		return df.format(gc.getTime());
	}

	/**
	 * 由java.util.Date转换成String的yyyy-MM-dd HH:mm:ss
	 *
	 * @param date
	 *            - java.util.Date
	 * @return 返回String格式的yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateToString(Date date, String format)
	{
		if (date == null || "".equals(date))
		{
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		return df.format(gc.getTime());
	}

	/**
	 * 由String转换成long格式的时间
	 *
	 * @param date
	 *            - 日期 格式如:2007-10-08
	 * @return 返回 2007-10-08 00:00:00 的long
	 */
	public static long getDateStringToLong(String date)
	{
		return DateUtil.getDateTimeStringToDate(date + " 00:00:00").getTime();
	}

	/**
	 * 由String转换成long格式的时间
	 *
	 * @param date
	 *            - 日期 格式如:2007-10-08 12:01:21
	 * @return 返回 2007-10-08 12:01:21 的long
	 */
	public static long getDateTimeStringToLong(String date)
	{
		return DateUtil.getDateTimeStringToDate(date).getTime();
	}

	/**
	 * 把long类型的时间转为date
	 *
	 * @param time
	 * @return 返回 2007-10-08 12:01:21的date
	 * @throws ParseException
	 */
	public static Date formatGetTime(Long time) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date(time * 1000));
		Date formatDate = sdf.parse(date);
		return formatDate;
	}

	public static Date formatGetTimePH(Long time) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date(time));
		Date formatDate = sdf.parse(date);
		return formatDate;
	}

	/**
	 * 获取指定日期的年度
	 *
	 * @param date
	 * @return
	 */
	public static int getYearOfDate(Date date)
	{

		if (date == null)
		{
			throw new IllegalArgumentException("Invalid argument, date is null");
		}

		return date.getYear() + 1900;
	}

	/**
	 * 计算两个日期中 年数的间隔
	 *
	 * @param date1
	 *            小的 日期
	 * @param date2
	 *            大的 日期
	 * @author shuzheng_wang
	 */
	public static int getYearSpace(Date date1, Date date2)
	{
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		int space = year2 - year1;

		return space;
	}

    /**
     * 计算两个日期中 年数的间隔，精确到天
     *
     * @param date1
     *            小的 日期
     * @param date2
     *            大的 日期
     * @author shuzheng_wang
     */
    public static int getYearSpace2(Date date1, Date date2)
    {
        int year = 0;

        try {
            Calendar now = Calendar.getInstance();
            now.setTime(date2);// 当前时间

            Calendar from = Calendar.getInstance();
            from.setTime(date1);

            year = now.get(Calendar.YEAR) - from.get(Calendar.YEAR);
            if (now.get(Calendar.DAY_OF_YEAR) < from.get(Calendar.DAY_OF_YEAR)) {
                year-- ;
            }

        } catch (Exception e) {//兼容性更强,异常后返回数据
            logger.error("getYearSpace() 出现异常", e);
        }

        return year;
    }

	/**
	 * 获取指定日期的月
	 *
	 * @param date
	 * @return
	 */
	public static int getMonthOfDate(Date date)
	{
		if (date == null)
		{
			throw new IllegalArgumentException("Invalid argument, date is null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 获取指定日期的月
	 *
	 * @param dateString
     * @return
	 */
	public static int getMonthOfDate(String dateString)
	{
		Date date = getDateTimeStringToDate(dateString);

		if (date == null)
		{
			throw new IllegalArgumentException("Invalid argument, date is null");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 判断当前日期是星期几
	 *
	 * @param pTime
	 *            修要判断的时间
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static int dayForWeek(String pTime) throws Exception
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1)
		{
			dayForWeek = 7;
		} else
		{
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 判断当前日期是星期几
	 *
	 * @param pTime
	 *            修要判断的时间
	 * @return dayForWeek 判断结果(1至7)
	 * @Exception 发生异常
	 */
	public static int dayForWeek(Date pTime) throws Exception
	{
		Calendar c = Calendar.getInstance();
		c.setTime(pTime);
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1)
		{
			dayForWeek = 7;
		} else
		{
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 判断当前日期是改年的第几周
	 *
	 * @param pTime
	 *            日期
	 * @return
	 * @throws Exception
	 */
	public static int dayWeekForYear(String pTime) throws Exception
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.setTime(format.parse(pTime));
		int week = ca.get(Calendar.WEEK_OF_YEAR);
		return week;
	}

	/**
	 * 计算两个日期中月份的间隔
	 *
	 * @param date1
	 * @param date2
	 */
	public static int getMonthSpace(Date date1, Date date2)
	{
		int iMonth = 0;
		try
		{
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(date1);
			cal2.setTime(date2);
			if (cal2.equals(cal1))
				return 0;

			int yearDiff = (cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)) * 12;
			iMonth = yearDiff + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);

		} catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		return iMonth;
	}

	/**
	 * 计算两个日期中天数的间隔
	 *
	 * @param date1
	 * @param date2
	 */
	public static int getDaySpace(Date date1, Date date2)
	{
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算账单日距离当前日的月份间隔（计算账单日是属于最近第几期）
	 *
	 * @param date1
	 * @param date2
	 *            传当前日
	 */
	public static int getMonthDiff(String date1, String date2)
	{
	    if(StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2)){
            return -1;
        }
//		if(StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2)){
//			return -1;
//		}
		int iMonth = 0;
		int flag = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(sdf.parse(date1));
			cal2.setTime(sdf.parse(date2));
			if (cal2.equals(cal1))
				iMonth = 0;

			if (cal2.get(Calendar.DAY_OF_MONTH) < cal1.get(Calendar.DAY_OF_MONTH)) // 比较月份中的天数
				flag = 1;

			int yearDiff = (cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)) * 12;
			iMonth = yearDiff + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH) - flag;

		} catch (Exception e)
		{
			logger.error(e.getMessage());
			return -1;
		}
		return iMonth + 1;
	}



	/**
	 * 计算账单日距离当前日的月份间隔（计算账单日是属于最近第几期）
	 *
	 * @param date1
	 * @param date2
	 *            传当前日
	 */
	public static int getMonthDiff(Date date1, Date date2)
	{
		int iMonth = 0;
		int flag = 0;
		try
		{
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(date1);
			cal2.setTime(date2);
			if (cal2.equals(cal1))
				iMonth = 0;

			if (cal2.get(Calendar.DAY_OF_MONTH) < cal1.get(Calendar.DAY_OF_MONTH)) // 比较月份中的天数
				flag = 1;

			int yearDiff = (cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)) * 12;
			iMonth = yearDiff + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH) - flag;

		} catch (Exception e)
		{
			logger.error(e.getMessage());
			return -1;
		}
		return iMonth + 1;
	}


	/**
	 * 计算账单日距离当前日的月份间隔
	 *
	 * @param date1
	 * @param date2
	 *            传当前日
	 */
	public static int getMonthDiffCommon(Date date1, Date date2)
	{
		int iMonth = 0;
		int flag = 0;
		try
		{
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(date1);
			cal2.setTime(date2);
			if (cal2.equals(cal1))
				iMonth = 0;

			if (cal2.get(Calendar.DAY_OF_MONTH) < cal1.get(Calendar.DAY_OF_MONTH)) // 比较月份中的天数
				flag = 1;

			int yearDiff = (cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)) * 12;
			iMonth = yearDiff + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH) - flag;

		} catch (Exception e)
		{
			logger.error(e.getMessage());
			return -1;
		}
		return iMonth;
	}

	/**
	 * 计算日期的天数 (传入日期:2015-11-15，返回：15)
	 *
	 * @param date
	 * @return
	 */
	public static int getDay_Of_Month(Date date)
	{
		GregorianCalendar gC = new GregorianCalendar();
		gC.setTime(date);
		return gC.get(GregorianCalendar.DAY_OF_MONTH);
	}

	/**
	 * 根据指定格式将指定字符串解析成日期
	 *
	 * @param str
	 *            指定日期
	 * @param pattern
	 *            指定格式
	 * @return 返回解析后的日期
	 */
	public static Date parse(String str, String pattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try
		{
			return sdf.parse(str);
		} catch (ParseException e)
		{
			// logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 对指定的日期指定加法,返回yyyy-mm-dd
	 *
	 * @param filed
	 *            - exp:Calendar.YEAR [2007-4-19]
	 * @param val
	 *            - 值
	 * @return - add Date exp:add(Calendar.YEAR,-2) result:2005-4-19
	 */
	public static String addAndReturnWeek(Date date, int filed, int val)
	{
		SimpleDateFormat df = new SimpleDateFormat("MM/dd");
		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		gc.add(filed, val);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH));

		return df.format(gc.getTime()) + weeks_2[gc.get(Calendar.DAY_OF_WEEK) - 1];
	}

	/**
	 * 获取当月的天数
	 * 
	 * @return 返回INT
	 */
	public static int getCurrentMonthDays()
	{
		try
		{
			String nextMonth = DateUtil.add(Calendar.MONTH, 1).substring(0, 7) + "-01"; // 获取下一个月的1号
			String endOfMonth = DateUtil.addByDate(nextMonth, Calendar.DAY_OF_MONTH, -1); // 往回倒一天
			return Integer.parseInt(endOfMonth.split("-")[2]); // 取当月最后一天的天数
		} catch (Exception e)
		{
			logger.error(e.getMessage());
			return 30; // 默认30
		}
	}

	/**
	 * 获取当月的天数（date日期所在的月份天数）
	 * 
	 * @return 返回INT
	 */
	public static int getCurrentMonthDays(String date)
	{
		try
		{
			String nextMonth = DateUtil.add(DateUtil.getDateStringToDate(date), Calendar.MONTH, 1).substring(0, 7) + "-01"; // 获取下一个月的1号
			String endOfMonth = DateUtil.addByDate(nextMonth, Calendar.DAY_OF_MONTH, -1); // 往回倒一天
			return Integer.parseInt(endOfMonth.split("-")[2]); // 取当月最后一天的天数
		} catch (Exception e)
		{
			logger.error(e.getMessage());
			return 30; // 默认30
		}
	}

	/**
	 * 校验日期字符串是否正确日期格式
	 * 
	 * @param dateStr
	 * @param formatStr
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static boolean isValidDate(String dateStr, String formatStr)
	{
		if (StringUtils.isBlank(formatStr))
		{
			formatStr = "yyyy-MM-dd";// 默认格式
		}
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try
		{
			format.parse(dateStr);
		} catch (ParseException e)
		{
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}

	/**
	 * 获取时间date1与date2相差的秒数
	 * 
	 * @param date1
	 *            起始时间
	 * @param date2
	 *            结束时间
	 * @return 返回相差的秒数
	 */
	public static int getOffsetSeconds(Date date1, Date date2)
	{
		int seconds = (int) ((date2.getTime() - date1.getTime()) / 1000);
		return seconds;
	}

	/**
	 * 获取时间date1与date2相差的分钟数
	 * 
	 * @param date1
	 *            起始时间
	 * @param date2
	 *            结束时间
	 * @return 返回相差的分钟数
	 */
	public static int getOffsetMinutes(Date date1, Date date2)
	{
		return getOffsetSeconds(date1, date2) / 60;
	}

	/**
	 * 获取时间date1与date2相差的小时数
	 * 
	 * @param date1
	 *            起始时间
	 * @param date2
	 *            结束时间
	 * @return 返回相差的小时数
	 */
	public static int getOffsetHours(Date date1, Date date2)
	{
		return getOffsetMinutes(date1, date2) / 60;
	}

	/**
	 * 获取时间date1与date2相差的天数数
	 * 
	 * @param date1
	 *            起始时间
	 * @param date2
	 *            结束时间
	 * @return 返回相差的天数
	 */
	public static int getOffsetDays(Date date1, Date date2)
	{
		return getOffsetHours(date1, date2) / 24;
	}

	/**
	 * 获取date中的小时
	 * 
	 * @param date
	 * @return
	 */
	public static int getHOUR_OF_DAY(Date date)
	{
		GregorianCalendar gC = new GregorianCalendar();
		gC.setTime(date);
		return gC.get(GregorianCalendar.HOUR_OF_DAY);
	}

	/***
	 * 获取2018-01-24 23:59:59 固定格式的字符串日期
	 * 
	 * @param DateStr
	 *            20180124 2018/1/24 2018/01/24 2018-01-24 2018-1-24 2018年1月24日
	 * @return 2018-01-24
	 */
	public static String getDesignFormatDateStr(String DateStr)
	{
		if (DateStr == null)
		{
			return null;
		}
		if (DateStr.matches("\\d{8}"))
		{
			DateStr = DateStr.substring(0, 4) + "-" + DateStr.substring(4, 6) + "-" + DateStr.substring(6, 8);
		}
		if (DateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}") || DateStr.matches("\\d{4}/\\d{1,2}/\\d{1,2}") || DateStr.matches("\\d{4}年\\d{1,2}月\\d{1,2}日"))
		{
			String[] split = DateStr.split("[-|年|/|月|日]");
			String retStr = "";
			for (int i = 0; i < split.length; i++)
			{
				if (split[i].length() < 2)
				{
					split[i] = "0" + split[i];
				}
				retStr = retStr + split[i];
			}
			DateStr = retStr.substring(0, 4) + "-" + retStr.substring(4, 6) + "-" + retStr.substring(6, 8);
		}
		return DateStr;
	}

	/**
	 * 根据出生年月日来计算当前用户的年龄
	 * @param birthDay
	 * @return
	 */
	public static int calendarAgeByBirthDay(String birthDay)
	{
		if(birthDay.contains("-"))
		{
			birthDay=birthDay.replace("-", "");
		
		}
		Date bitDate=null;
		try {
			bitDate = new SimpleDateFormat("yyyyMMdd").parse(birthDay);
		} catch (ParseException e) {
			logger.error("paser BirthDay is fail,birthDay={}|e={}",birthDay,e);
			return -1;
		}
		 
        //由出生日期获得年龄  
		Calendar cal = Calendar.getInstance();  
        if (cal.before(bitDate)) {  
            throw new IllegalArgumentException(  
                    "The birthDay is before Now.It's unbelievable!");  
        }  
        int yearNow = cal.get(Calendar.YEAR);  
        int monthNow = cal.get(Calendar.MONTH);  
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
        cal.setTime(bitDate);   
  
        int yearBirth = cal.get(Calendar.YEAR);  
        int monthBirth = cal.get(Calendar.MONTH);  
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);   
  
        int age = yearNow - yearBirth;  
  
        if (monthNow <= monthBirth) {  
            if (monthNow == monthBirth) {  
                if (dayOfMonthNow < dayOfMonthBirth) age--;  
            }else{  
                age--;  
            }  
        }
        return age;
	}
	
	/**
	 * 几天前
	 * @param date
	 * @return
	 */
	public static String agoMouth(Date date,int days)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
         
        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, - days);
        Date d = c.getTime();
        String day = format.format(d);
        return day;
	}
	
	public static Date convertStringToDate(String date)
	{
		try
		{
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		} catch (ParseException e)
		{
			logger.info(e.getMessage());
			return null;
		}
	}
	
	/**
	 * 将yyyyMMdd->yyyy.MM.dd 格式的字符串
	 * @param tempStr
     * @return
	 */
	public static String convertyyyyMMddToDian(String tempStr)
	{
		String [] strSplit=tempStr.split("-");
		String temp1="";		
		Pattern  pattern=Pattern.compile("^\\d{8}$");
		Matcher matche=pattern.matcher(strSplit[1]);
		String temp0=strSplit[0].substring(0,4).concat(".").concat(strSplit[0].substring(4,6)).concat(".").concat(strSplit[0].substring(6,8));
		if(matche.matches())
		{
			temp1=strSplit[1].substring(0,4).concat(".").concat(strSplit[1].substring(4,6)).concat(".").concat(strSplit[1].substring(6,8));
		}else{
			temp1=strSplit[1];
		}
		return temp0.concat("-").concat(temp1);
	}
	

	/**
	 * 获取指定的时间(精确到小时)
	 * @param days
	 * @param hour
	 * @return
	 */
	public static  String getFormatStringDate(int days,int hour,boolean isStart)
	{
		String dateString ="";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.DATE, - days);
        Date d = c.getTime();
        if(isStart)
        {
           dateString = format.format(d).concat(" "+(hour-1)).concat(":00:00");
        }else {
            dateString = format.format(d).concat(" "+(hour-1)).concat(":59:59");
		}
        return dateString;
	}


	/**
	 * 获取指定的时间(精确到小时)
	 * @param days
	 * @param hour
	 * @return
	 */
	public static  String getFormatStringMimute(int days,int hour,boolean isStart)
	{
		String dateString ="";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.DATE, - days);
        Date d = c.getTime();
        if(isStart)
        {
           dateString = format.format(d).concat(" "+(hour-1)).concat(String.format(":%s:00", new Date().getMinutes()));
        }else {
            dateString = format.format(d).concat(" "+(hour)).concat(String.format(":%s:%s", new Date().getMinutes(),new Date().getSeconds()));
		}
        return dateString;
	}


	public static void main(String[] args) throws Exception {
//		String dateString ="";
//		String dateStringE ="";
//		int hour=new Date().getHours();
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar c = Calendar.getInstance();
//
//        c.setTime(new Date());
//        c.add(Calendar.DATE, - 1);
//        Date d = c.getTime();
//        dateString = format.format(d).concat(" "+(hour-1)).concat(String.format(":%s:00", new Date().getMinutes(),new Date().getSeconds()));
//        dateStringE = format.format(d).concat(" "+(hour)).concat(String.format(":%s:%s", new Date().getMinutes(),new Date().getSeconds()));

    }
}