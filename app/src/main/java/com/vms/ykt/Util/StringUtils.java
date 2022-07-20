package com.vms.ykt.Util;


import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class StringUtils
{
    public static final int MIN_LEN = 4;

    private static String bufferToHex(final byte[] magnitude) {
        final int length = magnitude.length;
        final String string = new BigInteger(1, magnitude).toString(16);
        final StringBuffer sb = new StringBuffer(32);
        for (int length2 = string.length(), i = 0; i < length * 2 - length2; ++i) {
            sb.append("0");
        }
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(sb.toString());
        sb2.append(string);
        return sb2.toString();
    }

    public static String concat(final String s, final String str) {
        return s.concat(str);
    }

    public static String convert(final String s) {
        return s.replaceAll("[^\\x00-\\xff]", "**");
    }

    public static boolean copyString(final String[] array, final String[] array2) {
        final int n = 0;
        if (array != null && array2 != null) {
            int n2 = 0;
            int n3;
            while (true) {
                n3 = n;
                if (n2 >= array2.length) {
                    break;
                }
                array2[n2] = "";
                ++n2;
            }
            while (n3 < array2.length && n3 < array.length) {
                array2[n3] = array[n3];
                ++n3;
            }
            return true;
        }
        return false;
    }

    public static String cutString(String string, final int n, final String str) {
        if (isEmpty(string)) {
            return "";
        }
        final char[] charArray = string.toCharArray();
        int n2 = 0;
        string = "";
        int n3;
        for (n3 = 0; n2 < charArray.length && n > n3; ++n2) {
            n3 += String.valueOf(charArray[n2]).getBytes().length;
            final StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append(charArray[n2]);
            string = sb.toString();
        }
        if (n != n3) {
            final String string2 = string;
            if (n != n3 - 1) {
                return string2;
            }
        }
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(string);
        sb2.append(str);
        return sb2.toString();
    }

    public static String delString(String s, final String str, final String str2) {
        int n = -str.length();
        String s2 = s;
        int i;
        do {
            i = s2.indexOf(str, n + str.length());
            s = s2;
            if (i > 0) {
                final int index = s2.indexOf(str2, str2.length() + i);
                if (index > 0) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append(s2.substring(0, i));
                    sb.append(s2.substring(index + str2.length()));
                    s = sb.toString();
                }
                else {
                    s = s2.substring(0, i);
                }
            }
            n = i;
            s2 = s;
        } while (i > 0);
        return s;
    }

    public static String delUTF8Dom(final StringBuffer sb) {
        final int length = sb.length();
        int length2 = 4;
        if (length <= 4) {
            length2 = sb.length();
        }
        final int n = 0;
        final String substring = sb.substring(0, length2);
        int n2;
        if ((n2 = substring.indexOf(60)) < 0) {
            n2 = substring.indexOf(123);
        }
        int index;
        if ((index = n2) < 0) {
            index = substring.indexOf(91);
        }
        if (index > 0) {
            for (int i = n; i < index; ++i) {
                sb.setCharAt(i, ' ');
            }
        }
        return sb.toString();
    }

    public static String getContentMD5(final String s) {
        return getContentMD5(s.getBytes());
    }

    private static String getContentMD5(final byte[] input) {
        try {
            final MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(input, 0, input.length);
            return bufferToHex(instance.digest());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String getDateTime(final long date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(date));
    }

    public static String getMd5(String string) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            return "";
        }
        try {
            final byte[] digest = MessageDigest.getInstance("MD5").digest(string.getBytes());
            string = "";
            for (int length = digest.length, i = 0; i < length; ++i) {
                String str2;
                final String str = str2 = Integer.toHexString(digest[i] & 0xFF);
                if (str.length() == 1) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append("0");
                    sb.append(str);
                    str2 = sb.toString();
                }
                final StringBuilder sb2 = new StringBuilder();
                sb2.append(string);
                sb2.append(str2);
                string = sb2.toString();
            }
            return string;
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String getTime(final long n) {
        final int i = (int)(n / 3600L);
        final int n2 = (int)(n / 60L);
        final int j = (int)(n % 60L);
        if (i > 0) {
            return String.format("%02d:%02d:%02d", i, n2, j);
        }
        if (n2 > 0) {
            return String.format("%02d:%02d", n2, j);
        }
        return String.format("%02d", j);
    }

    public static String getTimeCN(final long n) {
        final int i = (int)(n / 3600L);
        final int n2 = (int)(n / 60L);
        final int j = (int)(n % 60L);
        if (i > 0) {
            return String.format("%02d\u5c0f\u65f6%02d\u5206%02d\u79d2", i, n2, j);
        }
        if (n2 > 0) {
            return String.format("%02d\u5206%02d\u79d2", n2, j);
        }
        return String.format("%02d\u79d2", j);
    }

    public static String getTransforTime(final int n) {
        return new SimpleDateFormat("mm:ss").format(new Date(n));
    }

    public static String getUTF8Dom() {
        try {
            return new String(new byte[] { -17, -69, -65 }, "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String getUrlName(final String s) {
        if (isEmpty(s)) {
            return "";
        }
        final int index = s.indexOf("?");
        String substring = s;
        if (index > 0) {
            substring = s.substring(0, index);
        }
        int n;
        if ((n = substring.lastIndexOf("/")) < 0) {
            n = substring.indexOf("\\");
        }
        if (n < 0) {
            return substring;
        }
        return substring.substring(n + 1);
    }

    public static boolean isASCII(final String s) {
        if (isEmpty(s)) {
            return false;
        }
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) >= '\u0080' || s.charAt(i) < '\0') {
                return false;
            }
        }
        return true;
    }

    public static boolean isChinese(final String s) {
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if ('\u4e00' > char1 || char1 >= '\u9fa5') {
                return false;
            }
        }
        return true;
    }

    public static boolean isContains(final String s, final String s2) {
        try {
            return s.contains(s2);
        }
        catch (Exception ex) {
            return false;
        }
    }

    @SuppressLint({ "DefaultLocale" })
    public static boolean isContainsIgnore(final String s, final String s2) {
        try {
            return s.contains(s2) || s.contains(s2.toUpperCase());
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static boolean isEmpty(final EditText editText) {
        return TextUtils.isEmpty((CharSequence)editText.getText().toString().trim());
    }

    public static boolean isEmpty(final CharSequence charSequence) {
        return TextUtils.isEmpty(charSequence);
    }

    public static boolean isEqual(final String s, final String anObject) {
        try {
            return s.equals(anObject);
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static String join(final List<String> list, final String s) {
        if (list != null && list.size() != 0) {
            final StringBuffer sb = new StringBuffer();
            final Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next());
                sb.append(s);
            }
            sb.delete(sb.lastIndexOf(s), sb.length());
            return sb.toString();
        }
        return "";
    }

    public static boolean parseArrayInt(final int[] array, final String s, final String regex) {
        int n = 0;
        if (array != null && !isEmpty(s)) {
            final String[] split = s.split(regex);
            boolean b = false;
            while (n < array.length && n < split.length) {
                array[n] = Integer.parseInt(split[n]);
                ++n;
                b = true;
            }
            return b;
        }
        return false;
    }

    public static boolean parseArrayString(final String[] array, final String s, final String regex) {
        int n = 0;
        if (array != null && !isEmpty(s)) {
            final String[] split = s.split(regex);
            boolean b = false;
            while (n < array.length && n < split.length) {
                array[n] = split[n];
                ++n;
                b = true;
            }
            return b;
        }
        return false;
    }

    public static Date parseDate(final String source) {
        if (TextUtils.isEmpty((CharSequence)source)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat;
        if (source.length() == 10) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        else if (source.length() == 16) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
        else {
            if (source.length() != 19) {
                return null;
            }
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        try {
            return simpleDateFormat.parse(source);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static double parseDouble(final String s, final double n) {
        try {
            return Double.parseDouble(s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return n;
        }
    }

    public static Float parseFloat(final String s, final float f) {
        try {
            return Float.parseFloat(s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return f;
        }
    }

    public static int parseInt(final String s) {
        try {
            return Integer.parseInt(s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public static int parseInt(final String s, final int n) {
        try {
            return Integer.parseInt(s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return n;
        }
    }

    public static long parseLong(final String s, final long n) {
        try {
            return Long.parseLong(s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return n;
        }
    }

    public static String subString(final String s, final String str, final int n) {
        int index = -str.length();
        int n2 = 0;
        int n3;
        do {
            index = s.indexOf(str, index + str.length());
            if (index < 0) {
                break;
            }
            n3 = n2 + 1;
            if (n3 == n) {
                return s.substring(0, index);
            }
        } while (index > 0 && (n2 = n3) < n);
        return s;
    }

    public static String trimEnd(final String s) {
        return s.replaceAll("[\\s]*$", "");
    }
}