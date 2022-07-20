package com.vms.ykt.Util;

import android.annotation.SuppressLint;

import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateTimeFormatUtil
{
    public static final String DATE_FORMAT_1SS = "yyyy/MM/dd HH:mm";
    public static final String DATE_FORMAT_1SSS = "yyyy/MM/dd HH:mm";
    public static final String DATE_FORMAT_2 = "MM\u6708dd\u65e5";
    public static final String DATE_FORMAT_4 = "yyyy\u5e74MM\u6708dd\u65e5";
    public static final String DATE_FORMAT_5S = "HH:mm:ss";
    public static final String DATE_FORMAT_6 = "MM-dd";
    public static final String DATE_FORMAT_7 = "MM-dd HH:mm";
    public static final String DATE_FORMAT_8 = "yyyy\u5e74MM\u6708dd\u65e5 HH:mm";
    public static final String HH_MM = "HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String StringToDateString(final String source) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse;
        try {
            parse = simpleDateFormat.parse(source);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
            parse = null;
        }
        return String.valueOf(parse.getTime());
    }

    public static boolean comparisonTime(final String s, final String s2) {
        return date2millonSenconds2(stringToDate(getCurrenDateString(s2), s2)) >= date2millonSenconds2(stringToDate(s, s2));
    }

    public static String currentDateTime(final String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date());
    }

    public static long date2millonSenconds2(final Date date) {
        return date.getTime();
    }

    public static String dateToString(final Date date, final String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }

    public static String dateToTime(final String s) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(s.replace("/Date(", "").replace(")/", "")) + 5400000L));
    }

    public static String formatFloatTimeLegth(final double n) {
        final int n2 = (int)(n / 60.0);
        final int i = n2 / 60;
        final int n3 = (int)(n % 60.0);
        final int j = n2 % 60;
        if (i != 0) {
            return String.format("%2d\u5c0f\u65f6%2d\u5206\u949f", i, j);
        }
        if (j == 0) {
            return String.format("%2d\u79d2", n3);
        }
        if (n3 == 0) {
            return String.format("%2d\u5206\u949f", j);
        }
        return String.format("%2d\u5206%2d\u79d2", j, n3);
    }

    public static String formatFloatTimeLegth(final long n) {
        final int n2 = (int)(n / 60L);
        final int i = n2 / 60;
        final int n3 = (int)(n % 60L);
        final int j = n2 % 60;
        if (i != 0) {
            return String.format("%2d\u5c0f\u65f6%2d\u5206\u949f", i, j);
        }
        if (j == 0) {
            return String.format("%2d\u79d2", n3);
        }
        if (n3 == 0) {
            return String.format("%2d\u5206\u949f", j);
        }
        return String.format("%2d\u5206%2d\u79d2", j, n3);
    }

    public static String formatFloatTimeLegthSecond(final long n) {
        final int n2 = (int)(n / 60L);
        return String.format("%d:%d:%d", n2 / 60, n2 % 60, (int)(n % 60L));
    }

    public static long formatHourToMin(final String s) {
        return new Date(s).getTime();
    }

    public static String formatTimeLegth(int i) {
        final int n = i / 1000;
        final int n2 = n / 60;
        i = n2 / 60;
        final int n3 = n % 60;
        final int j = n2 % 60;
        if (i != 0) {
            return String.format("%2d\u5c0f\u65f6%2d\u5206\u949f", i, j);
        }
        if (j == 0) {
            return String.format("%02d\u79d2", n3);
        }
        if (n3 == 0) {
            return String.format("%2d\u5206\u949f", j);
        }
        return String.format("%2d\u5206%02d\u79d2", j, n3);
    }

    public static long formatVideoTimeLength(final String s) {
        String[] array;
        if (s.contains(".")) {
            array = s.split("\\.")[0].split(":");
        }
        else {
            array = s.split(":");
        }
        long n = 0L;
        if (Integer.parseInt(array[0]) > 0) {
            n = 0L + Integer.parseInt(array[0]) * 3600;
        }
        long n2 = n;
        if (Integer.parseInt(array[1]) > 0) {
            n2 = n + Integer.parseInt(array[1]) * 60;
        }
        long n3 = n2;
        if (Integer.parseInt(array[2]) > 0) {
            n3 = n2 + Integer.parseInt(array[2]);
        }
        return n3;
    }

    public static String getCurrenDateString(final String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date());
    }

    public static long getDateToDate(final String source, final String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(source).getTime();
    }

    public static long getDateToTime(final String source) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(source).getTime();
    }

    public static int getDaysByYearMonth(final int value, final int n) {
        final Calendar instance = Calendar.getInstance();
        instance.set(1, value);
        instance.set(2, n - 1);
        instance.set(5, 1);
        instance.roll(5, -1);
        return instance.get(5);
    }

    public static long getHourToTime(final String source) throws ParseException {
        return new SimpleDateFormat("HH:mm").parse(source).getTime();
    }

    public static long getInternetTime() {
        long date;
        try {
            final URLConnection openConnection = new URL("http://www.baidu.com").openConnection();
            openConnection.connect();
            date = openConnection.getDate();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            date = 0L;
        }
        return date;
    }

    @SuppressLint({ "DefaultLocale" })
    public static String getTimeString(int n) {
        final int n2 = n / 60;
        n %= 60;
        String s;
        if (n2 > 0 && n > 0) {
            s = String.format("%d\u5206%d\u79d2", n2, n);
        }
        else if (n2 > 0) {
            s = String.format("%d\u5206", n2);
        }
        else {
            s = String.format("%d\u79d2", n);
        }
        return s;
    }

    public static String getWeek(final String s) {
        return new SimpleDateFormat("E").format(stringToDate(s, "yyyy-MM-dd HH:mm:ss"));
    }

    public static String getWeek(final String s, final String s2) {
        return new SimpleDateFormat("EEEE").format(stringToDate(s, s2));
    }

    public static Date millonSenconds2Date(final long timeInMillis) {
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(timeInMillis);
        return instance.getTime();
    }

    public static Date millonSenconds2Date2(final long date) {
        final Date time = new Date(date);
        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(time);
        return gregorianCalendar.getTime();
    }

    public static Date millonSenconds2DateStatic(final long timeInMillis) {
        final Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(timeInMillis);
        return instance.getTime();
    }

    public static String minusOneDay(final String s, final String s2) {
        final Calendar instance = Calendar.getInstance();
        instance.setTime(stringToDate(s, s2));
        instance.add(5, -1);
        return new SimpleDateFormat("yyyy-MM-dd").format(instance.getTime());
    }

    public static String plusOneDay(final String s, final String s2) {
        final Calendar instance = Calendar.getInstance();
        instance.setTime(stringToDate(s, s2));
        instance.add(5, 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(instance.getTime());
    }

    public static String showDateTime(final String s) {
        final Date stringToDate = stringToDate(s, "yyyy-MM-dd HH:mm:ss");
        final long n = System.currentTimeMillis() - stringToDate.getTime();
        if (n < 60000L) {
            return "\u521a\u521a";
        }
        if (n < 3600000L) {
            final int i = (int)(n / 60000L);
            final StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append("\u5206\u949f\u524d");
            return sb.toString();
        }
        if (n < 86400000L) {
            final int j = (int)(n / 3600000L);
            final StringBuilder sb2 = new StringBuilder();
            sb2.append(j);
            sb2.append("\u5c0f\u65f6\u524d");
            return sb2.toString();
        }
        final String dateToString = dateToString(stringToDate, "yyyy");
        final String currentDateTime = currentDateTime("yyyy");
        if (n < 31536000000L && dateToString.equals(currentDateTime)) {
            return dateToString(stringToDate, "MM\u6708dd\u65e5 HH:mm");
        }
        return dateToString(stringToDate, "yyyy\u5e74MM\u6708dd\u65e5");
    }

    public static String stampToDate(final String source) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse;
        try {
            parse = simpleDateFormat.parse(source);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
            parse = null;
        }
        return new SimpleDateFormat("HH:mm").format(new Date(parse.getTime()));
    }

    public static String stampToDate2(final String source) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse;
        try {
            parse = simpleDateFormat.parse(source);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
            parse = null;
        }
        return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date(parse.getTime()));
    }

    public static String stampToDateString(final String source, final String pattern, final String pattern2) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date parse;
        try {
            parse = simpleDateFormat.parse(source);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
            parse = null;
        }
        return new SimpleDateFormat(pattern2).format(new Date(parse.getTime()));
    }

    public static Date stringToDate(final String source, final String pattern) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        Date parse;
        try {
            parse = simpleDateFormat.parse(source);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            parse = null;
        }
        return parse;
    }

    public static String timeTohour(final String source) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse;
        try {
            parse = simpleDateFormat.parse(source);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
            parse = null;
        }
        return new SimpleDateFormat("HH:mm").format(new Date(parse.getTime()));
    }

    public static String toTime(int n, final boolean b) {
        final int n2 = n / 1000;
        final int n3 = n2 / 60;
        n = n3 / 60;
        final int i = n2 % 60;
        final int j = n3 % 60;
        if (!b) {
            return String.format("%02d:%02d:%02d", n, j, i);
        }
        if (n == 0) {
            return String.format("%02d:%02d", j, i);
        }
        return String.format("%02d:%02d:%02d", n, j, i);
    }

    public long date2millonSenconds(final Date date) {
        return date.getTime();
    }
}