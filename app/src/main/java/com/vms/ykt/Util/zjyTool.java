package com.vms.ykt.Util;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class zjyTool {

    public String[] getEmitDevice()  {

        long internetTime = Long.parseLong(Tool.getTime());
        //DateTimeFormatUtil.getInternetTime();//时间戳前10位+000

        if (internetTime == 0) {
            internetTime = System.currentTimeMillis();
            //13位时间戳
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(internetTime);//登录超时会用第一个时间戳
        stringBuilder.append("");
        String stringBuilder2 = stringBuilder.toString();
        //加密的请求头
        String device=getSecretMd5(stringBuilder2);

        return new String[]{internetTime+"",device};
    }

    public static String getSecretMd5(String str) {
        String systemModel = SystemUtil.getSystemModel();//手机名词和厂家
        String systemVersion ="9";   //SystemUtil.getSystemVersion();//手机系统版本
        String verion = getVersion();//软件版本 写死了2.8.43
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        String stringBuilder3 = StringUtils.getMd5(systemModel) +
                systemVersion;
        stringBuilder2.append(StringUtils.getMd5(stringBuilder3));
        stringBuilder2.append(verion);
        stringBuilder.append(StringUtils.getMd5(stringBuilder2.toString()));
        stringBuilder.append(str);
        return StringUtils.getMd5(stringBuilder.toString());
        //标准md5加密
    }

    public static String dateToStamp() throws ParseException {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String format = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        Date parse;
        try {
            parse = simpleDateFormat.parse(format);
        }
        catch (Exception ex) {
            parse = null;
        }
        return String.valueOf(parse.getTime());
    }

    public static String getSecretKey(String cellId, final String time,String UserId) {
        final StringBuilder sb = new StringBuilder();
        sb.append(UserId);
        sb.append(cellId);
        sb.append(time);
        sb.append("123456789");
        String string = sb.toString();
        return getMd5(string).toUpperCase();
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

    public static String getVersion() {
        return "2.8.43";
    }
}
