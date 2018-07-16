package com.amazon.test.testVO;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	// yyyy-MM-dd日期格式
	public static String convertDate(Date date) {
		return convertDate(date, "yyyy-MM-dd");
	} 

	public static String convertDate(Date date, String format) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		return dateFormatter.format(date);
	}

	// yyyy-MM-dd日期格式
	public static Date convertDate(String str) {
		return convertDate(str, "yyyy-MM-dd");
	}
	

	public static Date convertDate(String str, String format) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.parse(str);
		} catch (ParseException e) {
			throw new IllegalArgumentException(str+ " is not valid date format.");
		}
	}

	public static Date getYearAfter(Date date, int days) {
		GregorianCalendar start = new GregorianCalendar();
		start.setTimeInMillis(date.getTime());
		start.add(GregorianCalendar.YEAR, days);
		return new Date(start.getTimeInMillis());
	}

	public static Date getYearBefore(Date date, int days) {
		GregorianCalendar start = new GregorianCalendar();
		start.setTimeInMillis(date.getTime());
		start.add(GregorianCalendar.YEAR, days * -1);
		return new Date(start.getTimeInMillis());
	}

	public static Date getMonthAfter(Date date, int days) {
		GregorianCalendar start = new GregorianCalendar();
		start.setTimeInMillis(date.getTime());
		start.add(GregorianCalendar.MONTH, days);
		return new Date(start.getTimeInMillis());
	}

	public static Date getMonthBefore(Date date, int days) {
		GregorianCalendar start = new GregorianCalendar();
		start.setTimeInMillis(date.getTime());
		start.add(GregorianCalendar.MONTH, days * -1);
		return new Date(start.getTimeInMillis());
	}

	public static Date getDateAfter(Date date, int days) {
		GregorianCalendar start = new GregorianCalendar();
		start.setTimeInMillis(date.getTime());
		start.add(GregorianCalendar.DATE, days);
		return new Date(start.getTimeInMillis());
	}

	public static Date getDateBefore(Date date, int days) {
		GregorianCalendar start = new GregorianCalendar();
		start.setTimeInMillis(date.getTime());
		start.add(GregorianCalendar.DATE, days * -1);
		return new Date(start.getTimeInMillis());
	}

	/**
	 * 仅保留日期，其它的清0， 如2010-7-27 00:00:00.000
	 * 
	 * @param date
	 * @return
	 */
	public static Date getRealDate(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(date.getTime());
		gc.set(GregorianCalendar.HOUR_OF_DAY, 0);
		gc.set(GregorianCalendar.MINUTE, 0);
		gc.set(GregorianCalendar.SECOND, 0);
		gc.set(GregorianCalendar.MILLISECOND, 0);
		return new Date(gc.getTimeInMillis());
	}

	/**
	 * 获取日期所在当前的最大值 ，如2010-7-27 23:59:59.000
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFullDate(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(date.getTime());
		gc.set(GregorianCalendar.HOUR_OF_DAY, 23);
		gc.set(GregorianCalendar.MINUTE, 59);
		gc.set(GregorianCalendar.SECOND, 59);
		gc.set(GregorianCalendar.MILLISECOND, 0);
		return new Date(gc.getTimeInMillis());
	}

	/**
	 * 获取日期所在当前的最大值 ，如2010-7-27 10:10:25.000
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFullMillisecondDate(){
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(GregorianCalendar.MILLISECOND, 0);
		return new Date(gc.getTimeInMillis());
	}
	
	/**
	 * 获取本年的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstYearDate(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(date.getTime());
		gc.set(GregorianCalendar.DAY_OF_YEAR, 1);
		return new Date(gc.getTimeInMillis());
	}

	/**
	 * 获取本月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstMonthDate(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(date.getTime());
		gc.set(GregorianCalendar.DAY_OF_MONTH, 1);
		return new Date(gc.getTimeInMillis());
	}

	/**
	 * 获取本周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstWeekDate(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(date.getTime());
		gc.set(GregorianCalendar.DAY_OF_WEEK, 1);
		return new Date(gc.getTimeInMillis());
	}
	
	/**
	 * Date 转换为XMLGregorianCalendar类型 
	 * 
	 * @param date
	 * @return
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendar(Date date){
			if (date==null) {
				return null;
			}
		 	Calendar calendar = Calendar.getInstance();
		 	calendar.setTime(date);
		    DatatypeFactory dtf = null;
		    try {
		     dtf = DatatypeFactory.newInstance();
		    } catch (DatatypeConfigurationException e) {
		    	e.printStackTrace();
		    }
		    
		    XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
		    dateType.setYear(calendar.get(Calendar.YEAR));
		    dateType.setMonth(calendar.get(Calendar.MONTH)+1);
		    dateType.setDay(calendar.get(Calendar.DAY_OF_MONTH));
		    dateType.setHour(calendar.get(Calendar.HOUR_OF_DAY));
		    dateType.setSecond(calendar.get(Calendar.SECOND));
		    dateType.setMinute(calendar.get(Calendar.MINUTE));
		    return dateType;
	}
	
	public static Date getDateByXMLGregorianCalendar(XMLGregorianCalendar date){
		try {
			return date.toGregorianCalendar().getTime();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 根据季度获取季度的开始月份
	 * @param quarter
	 * 			季度
	 * @return 每个季度的开始月
	 */
	public static Integer getStartMonth(Integer quarter){
		switch (quarter) {
			case 1:
				return 1;
			case 2:
				return 4;
			case 3:
				return 7;
			case 4:
				return 10;
			default:
				  throw new IllegalArgumentException("The quarter must 1-4");
		}
	}
	
	
	/**
	 * 2个时间相隔的天数取绝对值
	 * 
	 * @param a
	 * 		第一个时间
	 * @param b
	 * 		第二个时间
	 * @return
	 */
	  public static  long  dayDiffer(Date a,Date b){
	        long  d = Math.abs(b.getTime() - a.getTime());
	        long  day = d/(1000 * 60 * 60 * 24);
	        return day ;
	}
	  
	  /**
	   * 2个时间相隔的时数取绝对值(不包括星期天)
	   * @param a
	   * 	第一个时间
	   * @param b
	   * 	第二个时间
	   * @param ts
	   * 	不能超过的时数
	   * @return
	   *     abs(a-b)是否大于ts
	   */
	  public static boolean timeDiffer(Date a, Date b, int ts)
	  {
		  if(a == null || b == null)
		  {
			  return false;
		  }
		  long t = ts * 60 * 60 * 1000L;
		  long s = Math.abs(b.getTime() - a.getTime());		  
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(a);
		  int w1 = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		  calendar.setTime(b);
		  int w2 = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		  s = w1 == w2 ? s : s - 24 * 60 * 60 * 1000L;
		  if(s > t)
		  {		  
			  return true;
		  }
		  else
		  {
			  return false;
		  }
	  }
	  
	  public static Date dateAdd(int datePart, int amount, Date source)
	  {
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(source);
	      calendar.add(datePart, amount);
	      
	      return calendar.getTime();
	  }
}
