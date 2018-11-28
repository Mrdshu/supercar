package com.xw.supercar.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	final static String[] weeks_1 = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四",
        "星期五", "星期六"};
	final static String[] weeks_2 = new String[]{"日", "一", "二", "三", "四", "五", "六"};
	public static final String PATTERN_CLASSICAL = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_CLASSICAL_SIMPLE = "yyyy-MM-dd";
	/**
	 * 计算两个日期相差多少月
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static int getMonthSpace(String date1, String date2) throws ParseException {
		int result_y = 0;
		int result_m = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(sdf.parse(date1));
			c2.setTime(sdf.parse(date2));
			result_y = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
			
			if(result_y !=0 )
			{
				result_m=result_y*12;
			}
			result_m =result_m + (c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH));
			return result_m;
		} catch (Exception e) {
			e.printStackTrace();
			
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
    public static int getTimeDifferDays(Date d1, Date d2) {
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
    public static int getTimeDifferDays(String d1, String d2,String format) {
    	if(StringUtils.isEmpty(format)){
            format = "yyyy-MM-dd";
        }
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		Date sdate = null;
		Date edate = null;
		try {
			sdate = sdf.parse(d1);
			edate=sdf.parse(d2);  
		} catch (ParseException e) {
			e.printStackTrace();
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
    public static boolean verifyDate(String date) {
        if (date == null) {
            return false;
        }
        String macther = "^\\d{4}-((0?([1-9]))|(1[0-2]))-((0?([1-9]))|([1|2]([0-9]))|(3[0|1]))$";
        return RegexUtil.check(macther, date);
    }

    /**
     * 比较两个日期大小
     *
     * @param date1
     * @param date2
     * @return 返回数值是:1或2 返回0表示date1等于date2 返回1表示date1大 返回2表示date2大
     * @throws Exception
     */
    public static int compareDate(String date1, String date2) throws Exception {
        if (!verifyDate(date1) || !verifyDate(date2)) {
            throw new Exception("Date param can't pass verify!");
        }
        String[] d1 = date1.split("-");
        String[] d2 = date2.split("-");
        int flag = 0;
        /** 比较年 */
        if (Integer.parseInt(d1[0]) > Integer.parseInt(d2[0])) {
            flag = 1;
        } else if (Integer.parseInt(d2[0]) > Integer.parseInt(d1[0])) {
            flag = 2;
        } else if (Integer.parseInt(d1[1]) > Integer.parseInt(d2[1])) {
            /** 到这里说明年相等,那继续判断月 */
            flag = 1;
        } else if (Integer.parseInt(d2[1]) > Integer.parseInt(d1[1])) {
            flag = 2;
        } else if (Integer.parseInt(d1[2]) > Integer.parseInt(d2[2])) {
            /** 到这里说明年月相等,那继续判断日 */
            flag = 1;
        } else if (Integer.parseInt(d2[2]) > Integer.parseInt(d1[2])) {
            flag = 2;
        } else {
            /** 到这里,说明年月日都相等了 */
            flag = 0;
        }
        return flag;
    }


    /**
     * 返回当前详细时间 格式如:2007-01-01
     */
    public static String nowyyyyMMdd() {
    	GregorianCalendar gerCalendar = new GregorianCalendar();
    	java.util.Date date = gerCalendar.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	return sdf.format(date);
    }
    
    /**
     * 返回当前详细时间 格式如:2007-01-01 10:23:30
     */
    public static String now() {
        GregorianCalendar gerCalendar = new GregorianCalendar();
        java.util.Date date = gerCalendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
    
    /**
     * 返回当前毫秒级的时间 格式如:2007-01-01 10:23:30:110
     */
    public static String nowTimeMillis() {
    	GregorianCalendar gerCalendar = new GregorianCalendar();
    	java.util.Date date = gerCalendar.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    	return sdf.format(date);
    }

    /**
     * 返回当前日期 格式如:2007-01-01
     */
    public static String date() {
        GregorianCalendar gerCalendar = new GregorianCalendar();
        java.util.Date date = gerCalendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 对当前日期指定加法,返回yyyy-mm-dd
     *
     * @param filed -
     *              exp:Calendar.YEAR [2007-4-19]
     * @param val   -
     *              值
     * @return - add Date exp:add(Calendar.YEAR,-2) result:2005-4-19
     */
    public static String add(int filed, int val) {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        gc.add(filed, val);
        gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc
                .get(Calendar.DAY_OF_MONTH));
        return df.format(gc.getTime());
    }
    
    /**
     * 对指定日期指定加法,返回yyyy-mm-dd
     *
     * @param filed -
     *              exp:Calendar.YEAR [2007-4-19]
     * @param val   -
     *              值
     * @return - add Date exp:add(Calendar.YEAR,-2) result:2005-4-19
     */
    public static String addByDate(String date,int filed, int val) {
    	java.util.Date date_d = getDateStringToDate(date);
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar gc = Calendar.getInstance();
    	gc.setTime(date_d);
    	gc.add(filed, val);
    	gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc
    			.get(Calendar.DAY_OF_MONTH));
    	return df.format(gc.getTime());
    }

    
    
    /**
     * 对指定的日期指定加法,返回yyyy-mm-dd
     *
     * @param filed -
     *              exp:Calendar.YEAR [2007-4-19]
     * @param val   -
     *              值
     * @return - add Date exp:add(Calendar.YEAR,-2) result:2005-4-19
     */
    public static String add(java.util.Date date, int filed, int val) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        gc.add(filed, val);
        gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc
                .get(Calendar.DAY_OF_MONTH));
        return df.format(gc.getTime());
    }

    /**
     * 对指日期指定加法,返回yyyy-mm-dd hh:mm:ss
     *
     * @param date  指定日期 java.util.Date
     * @param filed -
     *              exp:Calendar.HOUR [2007-4-19 9:38:10]
     * @param val   -
     *              值
     * @return - add Precision Date exp:add(Calendar.HOUR,-2) result:2007-4-19
     * 7:38:10
     */
    public static String addPrecision(java.util.Date date, int filed, int val) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        gc.add(filed, val);
        gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc
                .get(Calendar.DAY_OF_MONTH), gc.get(Calendar.HOUR_OF_DAY), gc
                .get(Calendar.MINUTE), gc.get(Calendar.SECOND));
        return df.format(gc.getTime());
    }
    
    /**
     * 对指定的日期指定加法,返回Date
     *
     * @param filed -
     *              exp:Calendar.YEAR [2007-4-19]
     * @param val   -
     *              值
     * @return - add Date exp:add(Calendar.YEAR,-2) result:2005-4-19
     */
    public static Date addTime(java.util.Date date, int filed, int val) {
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        gc.add(filed, val);
        gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc
                .get(Calendar.DAY_OF_MONTH));
        return gc.getTime();
    }

    /**
     * 获取文件名，格式：yyyyMMddHHmmss
     */
    public static String fileName() {
        GregorianCalendar gerCalendar = new GregorianCalendar();
        java.util.Date date = gerCalendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    /**
     * 根据参数转换为java.util.Date类型
     *
     * @param date -
     *             2007-01-10
     * @return java.util.Date
     */
    public static java.util.Date getDateStringToDate(String date) {
        java.util.Date d = null;
        try {
            String[] temp = date.split("-");
            Calendar c = Calendar.getInstance();
            c.set(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]) - 1,
                    Integer.parseInt(temp[2]));
            d = c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 根据参数转换为java.util.Date类型
     *
     * @param date - 字符串开始的时间如: 2007-01-10 10:05:10
     * @return 根据参数转换为java.util.Date类型
     */
    public static Date getDateTimeStringToDate(String date) {
        java.util.Date d = null;
        try {
            String[] temp = date.split(" ");
            String[] date1 = temp[0].split("-");
            String[] time1 = temp[1].split(":");
            Calendar c = Calendar.getInstance();
            c.set(Integer.parseInt(date1[0]), Integer.parseInt(date1[1]) - 1,
                    Integer.parseInt(date1[2]), Integer.parseInt(time1[0]),
                    Integer.parseInt(time1[1]), (int) (Float
                            .parseFloat(time1[2])));
            d = c.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 把2007-01-10 10:05:10转换成2007-01-10
     */
    public static String getDateStringToString(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            String[] temp = date.split(" ");
            String[] date1 = temp[0].split("-");
            c.set(Integer.parseInt(date1[0]), Integer.parseInt(date1[1]) - 1,
                    Integer.parseInt(date1[2]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return df.format(c.getTime());
    }

    /**
     * @param datetime - long格式的时间
     * @return 返回由long转换成String格式的yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimelongToString(long datetime) {
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        java.util.Calendar gc = java.util.Calendar.getInstance();
        gc.setTime(new java.util.Date(datetime));
        return df.format(gc.getTime());
    }
    
    /**
     * @return 返回由long转换成String格式的yyyy-MM-dd HH:mm:ss:SSS
     */
    public static String getDateTimeMillislongToString(long datetime) {
    	java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
    			"yyyy-MM-dd HH:mm:ss:SSS");
    	java.util.Calendar gc = java.util.Calendar.getInstance();
    	gc.setTime(new java.util.Date(datetime));
    	return df.format(gc.getTime());
    }

    /**
     * @param datetime - long格式的时间
     * @return 返回由long转换成String格式的yyyy-MM-dd
     */
    public static String getDatelongToString(long datetime) {
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
                "yyyy-MM-dd");
        java.util.Calendar gc = java.util.Calendar.getInstance();
        gc.setTime(new java.util.Date(datetime));
        return df.format(gc.getTime());
    }

    /**
     * 由java.util.Date转换成String的yyyy-MM-dd
     *
     * @param date - java.util.Date
     * @return 返回String格式的yyyy-MM-dd
     */
    public static String format(Date date, String pattern) {
        if (date == null || "".equals(date)) {
            return "";
        }
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(pattern);
        java.util.Calendar gc = java.util.Calendar.getInstance();
        gc.setTime(date);
        return df.format(gc.getTime());
    }
    
    /**
     * 由java.util.Date转换成String的yyyy-MM-dd
     *
     * @param date - java.util.Date
     * @return 返回String格式的yyyy-MM-dd
     */
    public static String getDateDateToString(Date date) {
        if (date == null || "".equals(date)) {
            return "";
        }
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
                "yyyy-MM-dd");
        java.util.Calendar gc = java.util.Calendar.getInstance();
        gc.setTime(date);
        return df.format(gc.getTime());
    }

    /**
     * 由java.util.Date转换成String的yyyy-MM-dd HH:mm:ss
     *
     * @param date - java.util.Date
     * @return 返回String格式的yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeDateToString(Date date) {
        if (date == null || "".equals(date)) {
            return "";
        }
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        java.util.Calendar gc = java.util.Calendar.getInstance();
        gc.setTime(date);
        return df.format(gc.getTime());
    }

    /**
     * 由java.util.Date转换成String的yyyy-MM-dd HH:mm:ss
     *
     * @param date - java.util.Date
     * @return 返回String格式的yyyy-MM-dd HH:mm:ss
     */
    public static String getDateToString(Date date, String format) {
        if (date == null || "".equals(date)) {
            return null;
        }
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(format);
        java.util.Calendar gc = java.util.Calendar.getInstance();
        gc.setTime(date);
        return df.format(gc.getTime());
    }

    /**
     * 由String转换成long格式的时间
     *
     * @param date - 日期 格式如:2007-10-08
     * @return 返回 2007-10-08 00:00:00 的long
     */
    public static long getDateStringToLong(String date) {
        return DateUtil.getDateTimeStringToDate(date + " 00:00:00").getTime();
    }

    /**
     * 由String转换成long格式的时间
     *
     * @param date - 日期 格式如:2007-10-08 12:01:21
     * @return 返回 2007-10-08 12:01:21 的long
     */
    public static long getDateTimeStringToLong(String date) {
        return DateUtil.getDateTimeStringToDate(date).getTime();
    }

    /**
     * 把long类型的时间转为date
     * @param time
     * @return 返回 2007-10-08 12:01:21的date
     * @throws ParseException
     */
    public static Date formatGetTime(Long time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date(time * 1000));
        Date formatDate = sdf.parse(date);
        return formatDate;
    }

    public static Date formatGetTimePH(Long time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date(time));
        Date formatDate = sdf.parse(date);
        return formatDate;
    }
    
    /**
	 * 获取指定日期的年度
	 * @param date
	 * @return
	 */
	public static int getYearOfDate(Date date) {

		if (date == null) {
			throw new IllegalArgumentException("Invalid argument, date is null");
		}

		return date.getYear() + 1900;
	}
	
	/**
	 * 获取指定日期的月
	 * @param date
	 * @return
	 */
	public static int getMonthOfDate(Date date) {
		if (date == null) {
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
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static int dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }
    
    /**
     * 判断当前日期是星期几
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果(1至7)
     * @Exception 发生异常
     */
    public static int dayForWeek(Date pTime) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(pTime);
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    /**
     * 判断当前日期是改年的第几周
     *
     * @param pTime 日期
     * @return
     * @throws Exception
     */
    public static int dayWeekForYear(String pTime) throws Exception {
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
    public static int getMonthSpace(Date date1, Date date2) {
        int iMonth = 0;
        try {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);
            if (cal2.equals(cal1)){
                return 0;
            }

            int yearDiff = (cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)) * 12;
            iMonth = yearDiff + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return iMonth;
    }

    /**
     * 计算两个日期中天数的间隔
     * @param date1
     * @param date2
     */
    public static int getDaySpace(Date date1, Date date2) {
          long time1 = date1.getTime();
          long time2 = date2.getTime();
          long between_days = (time2 - time1) / (1000 * 3600 * 24);
          return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算账单日距离当前日的月份间隔（计算账单日是属于最近第几期）
     *
     * @param date1
     * @param date2 传当前日
     */
    public static int getMonthDiff(String date1, String date2) {
        int iMonth = 0;
        int flag = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(sdf.parse(date1));
            cal2.setTime(sdf.parse(date2));
            if (cal2.equals(cal1))
                iMonth = 0;

            if (cal2.get(Calendar.DAY_OF_MONTH) < cal1.get(Calendar.DAY_OF_MONTH)) //比较月份中的天数
                flag = 1;

            int yearDiff = (cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)) * 12;
            iMonth = yearDiff + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH) - flag;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return iMonth + 1;
    }

    /**
     * 计算账单日距离当前日的月份间隔（计算账单日是属于最近第几期）
     *
     * @param date1
     * @param date2 传当前日
     */
    public static int getMonthDiff(Date date1, Date date2) {
        int iMonth = 0;
        int flag = 0;
        try {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);
            if (cal2.equals(cal1))
                iMonth = 0;

            if (cal2.get(Calendar.DAY_OF_MONTH) < cal1.get(Calendar.DAY_OF_MONTH)) //比较月份中的天数
                flag = 1;

            int yearDiff = (cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR)) * 12;
            iMonth = yearDiff + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH) - flag ;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return iMonth + 1;
    }
    
    /**
     * 计算日期的天数  (传入日期:2015-11-15，返回：15)
     * @param date
     * @return
     */
    public static int getDay_Of_Month(Date date) {
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
    public static Date parse(String str, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 对指定的日期指定加法,返回yyyy-mm-dd
     *
     * @param filed -
     *              exp:Calendar.YEAR [2007-4-19]
     * @param val   -
     *              值
     * @return - add Date exp:add(Calendar.YEAR,-2) result:2005-4-19
     */
    public static String addAndReturnWeek(java.util.Date date, int filed, int val) {
        SimpleDateFormat df = new SimpleDateFormat("MM/dd");
        Calendar gc = Calendar.getInstance();
        gc.setTime(date);
        gc.add(filed, val);
        gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc
                .get(Calendar.DAY_OF_MONTH));
        
        return df.format(gc.getTime())+weeks_2[gc.get(Calendar.DAY_OF_WEEK) - 1];
    }
    
    
    /**
     * 获取当月的天数
     * @return 返回INT
     */
    public static int getCurrentMonthDays() {
        try {
            String nextMonth = DateUtil.add(Calendar.MONTH, 1).substring(0, 7)+"-01"; //获取下一个月的1号
            String endOfMonth = DateUtil.addByDate(nextMonth, Calendar.DAY_OF_MONTH, -1); //往回倒一天
            return Integer.parseInt(endOfMonth.split("-")[2]); //取当月最后一天的天数
        } catch (Exception e) {
            e.printStackTrace();
            return 30; //默认30
        }
    }
    
    /**
     * 获取当月的天数（date日期所在的月份天数）
     * @return 返回INT
     */
    public static int getCurrentMonthDays(String date) {
        try {
            String nextMonth = DateUtil.add(DateUtil.getDateStringToDate(date),Calendar.MONTH, 1).substring(0, 7)+"-01"; //获取下一个月的1号
            String endOfMonth = DateUtil.addByDate(nextMonth, Calendar.DAY_OF_MONTH, -1); //往回倒一天
            return Integer.parseInt(endOfMonth.split("-")[2]); //取当月最后一天的天数
        } catch (Exception e) {
            e.printStackTrace();
            return 30; //默认30
        }
    }
    
    /**
     * 校验日期字符串是否正确日期格式
     * @param dateStr
     * @param formatStr yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static boolean isValidDate(String dateStr, String formatStr) {
    	if(StringUtils.isEmpty(formatStr)){
    		formatStr = "yyyy-MM-dd";//默认格式
    	}
        boolean convertSuccess=true;
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            format.parse(dateStr);
         } catch (ParseException e) {
        	 // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
             convertSuccess=false;
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
    public static int getOffsetSeconds(Date date1, Date date2) {
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
    public static int getOffsetMinutes(Date date1, Date date2) {
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
    public static int getOffsetHours(Date date1, Date date2) {
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
    public static int getOffsetDays(Date date1, Date date2) {
        return getOffsetHours(date1, date2) / 24;
    }
    
    /**
     * @param date
     * @return
     */
    public static int getHOUR_OF_DAY(Date date) {
        GregorianCalendar gC = new GregorianCalendar();
        gC.setTime(date);
        return gC.get(GregorianCalendar.HOUR_OF_DAY);
    }
}