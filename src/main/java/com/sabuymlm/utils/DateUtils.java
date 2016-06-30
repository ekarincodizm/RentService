/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabuymlm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author sumrit
 */
public class DateUtils {

    private static final Calendar cal = Calendar.getInstance();
    private static SimpleDateFormat parserSDF;

    public static Date parseDate(String str, String pattern, Locale locale) throws ParseException {
        parserSDF = new SimpleDateFormat(pattern, locale);
        return parserSDF.parse(str);
    }

    public static Date addDate(Date date, Integer number) {
        cal.setTime(date);
        cal.add(Calendar.DATE, number);
        return cal.getTime();
    }

    public static Date addMonth(Date date, Integer number) {
        cal.setTime(date);
        cal.add(Calendar.MONTH, number);
        return cal.getTime();
    }

    public static Date addYear(Date date, Integer number) {
        cal.setTime(date);
        cal.add(Calendar.YEAR, number);
        return cal.getTime();
    }

    public static Date setDayOfMonth(Date date, Integer day) {
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public static int getDayOfMonth(Date date) {
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isLastDayOfMonth(Date date) {
        cal.setTime(date);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        return maxDay == currentDay;
    }

    public static Date getLastDate(Date date) {
        cal.setTime(date);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, maxDay);
        return cal.getTime();
    }

    public static Date getFirstDate(Date date) {
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        return cal.getTime();
    }

    public static Date getTruncateDate(Date date) {
        return org.apache.commons.lang.time.DateUtils.truncate(date, Calendar.DATE);
    }

    public static Date getEndOfDate(Date date) {
        cal.setTime(getTruncateDate(addDate(date, 1)));
        cal.setTimeInMillis(cal.getTimeInMillis() - 1000);
        return cal.getTime();
    }

    public static int compare(Date sourceDate, Date compareDate) {
        if (sourceDate == null || compareDate == null) {
            return -1;
        }
        long d1 = Long.parseLong(Format.formatDateEn("yyyyMMdd", sourceDate));
        long d2 = Long.parseLong(Format.formatDateEn("yyyyMMdd", compareDate));
        if (d1 == d2) {
            return 0;
        } else if (d1 < d2) {
            return 1;
        } else if (d1 > d2) {
            return 2;
        }
        return -1;
    }

    public static boolean equal(Date sourceDate, Date compareDate) {
        return (compare(sourceDate, compareDate) == 0);
    }

    public static boolean lessThan(Date sourceDate, Date compareDate) {
        return (compare(sourceDate, compareDate) == 1);
    }

    public static boolean greaterThan(Date sourceDate, Date compareDate) {
        return (compare(sourceDate, compareDate) == 2);
    }

    public static boolean lessThanEqual(Date sourceDate, Date compareDate) {
        return ((compare(sourceDate, compareDate) == 0) || (compare(sourceDate, compareDate) == 1));
    }

    public static boolean greaterThanEqual(Date sourceDate, Date compareDate) {
        return ((compare(sourceDate, compareDate) == 0) || (compare(sourceDate, compareDate) == 2));
    }

    public static boolean isPayCicleDate(Date startdate, Date enddate) {
        boolean isTrue = false;
        if ((startdate != null && enddate != null) && (Format.formatDateEn("yyyyMM", startdate).equals(Format.formatDateEn("yyyyMM", enddate)))) {
            int start = getDayOfMonth(startdate);
            int end = getDayOfMonth(enddate);
            int lastDate = getDayOfMonth(getLastDate(enddate));
            if ((start == 1 & end == 15) | (start == 16 & end == lastDate)) {
                isTrue = true;
            }
        }
        return isTrue;
    }

    public static boolean isPayStartCicleDate(Date startdate) {
        boolean isTrue = false;
        int start = getDayOfMonth(startdate);
        if (start == 1 | start == 16) {
            isTrue = true;
        }
        return isTrue;
    }

    public static long dateDiff(Date dateIni, Date dateFin) {
        long MILLISECS_PER_DAY = 24 * 60 * 60 * 1000;
        long days = 0l;
        if(dateIni != null && dateFin != null ) {
            try {
                days = (dateFin.getTime() - dateIni.getTime()) / MILLISECS_PER_DAY;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return days;
    }
}
