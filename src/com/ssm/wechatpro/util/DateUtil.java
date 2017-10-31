package com.ssm.wechatpro.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class DateUtil {

	/**
	 * 对先有日期进行调整 日期迁移后退
	 * @param nowDay 设定现在的日期
	 * @param field  取1加1年,取2加半年,取3加一季度,取4加一周,取5加一天
	 * @param amount 增加量 正数日期将后加，负数日期将前推
	 * @return
	 */
	public static String changeDay(String nowDay, int field, int amount) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		Date date = format.parse(nowDay);
		gc.setTime(date);
		gc.add(field, amount);
		return format.format(gc.getTime());
	}

	/**
	 * 比较两个日期之间相差的天数daysBetween("2016-12-17","2016-12-29")
	 * @param stareTime  开始日期 较小的日期
	 * @param endTime 结束日期，比较大的日期
	 * @return
	 * @throws Exception
	 */
	public static int daysBetween(String stareTime, String endTime) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(stareTime));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(endTime));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 * 获取日志表后缀日期
	 * @return
	 */
	public static String getTimeSixAndToString() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMM");
		String nowTime = df.format(dt);
		return nowTime;
	}
	/**
	 * 获取下个月日志表后缀日期
	 * @return
	 */
	public static String getNextTime() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMM");
		String nowTime = df.format(getLastDate3(dt));
		return nowTime;
	}
	
	private static Date getLastDate3(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, +1);
        return cal.getTime();
    }
  
	/**
	 * 获取上个月日志表后缀日期
	 * @return
	 */
	public static String getTimePrevMonthAndToString() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMM");
		String nowTime = df.format(getLastDate(dt));
		return nowTime;
	}
	
	private static Date getLastDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }
	
	/**
	 * 获取上上个月的日志表中的日期后缀表达式
	 * @return
	 */
	public static String getTimeVorMonthAndToString(){
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMM");
		String nowTime = df.format(getLastDate2(dt));
		return nowTime;
	};
	
	private static Date getLastDate2(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -2);
        return cal.getTime();
    }

	/**
	 * 获取当前日期(2016-12-29 11:23:09)
	 * @return
	 */
	public static String getTimeAndToString() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = df.format(dt);
		return nowTime;
	}
	
	/**
	 * 获取当前的时间
	 * 19:58
	 * @return
	 */
	public static String getNowTime(){
		Date data = new Date();
		DateFormat df = new SimpleDateFormat("HH:mm");
		return df.format(data);
	}
	/**
	 * 获取当前日期(2016-12-29)
	 * @return
	 */
	public static String getTime() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String nowTime = df.format(dt);
		return nowTime;
	}

	/**
	 * 获取当前日期字符串(20161229112321)
	 * @return
	 */
	public static String getToString() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String nowTime = df.format(dt);
		return nowTime;
	}
	/**
	 * 获取当前日期字符串(20161229112321)
	 * @return
	 */
	public static String getTimeToString() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String nowTime = df.format(dt);
		return nowTime;
	}
	
	/**
	 * 获取一周前的时间
	 * @return
	 */
	public static String getTimePreWeekToString(){
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar nowTime = Calendar.getInstance();
		nowTime.setTime(dt);
		nowTime.add(Calendar.DATE, -7);
		String preWeek = df.format(nowTime.getTime());
		return preWeek;
	}
	
	public static String getUUID(){
		 UUID uuid = UUID.randomUUID();  
		 String str = uuid.toString();
		 // 去掉"-"符号  
		 //String temp1 = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);  
		 //获取前5位
		 return str.substring(0, 6);
	}
	public static void main(String[] args) throws Exception {
		String orderNumber = "85550200010320171010e98083";
		String order_log ="wechat_customer_order_log_"+ orderNumber.substring(12,18 );
		String adminId = orderNumber.substring(6, 12);
		String adminIds = String.format("%01d",Integer.parseInt(adminId));
		System.out.println(adminIds);
	}
}
