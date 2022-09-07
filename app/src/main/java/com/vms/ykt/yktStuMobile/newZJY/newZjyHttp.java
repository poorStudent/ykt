package com.vms.ykt.yktStuMobile.newZJY;

import com.vms.ykt.Util.Http;
import com.vms.ykt.Util.httpRespnose;
import com.vms.ykt.yktUtil.yktHeaders;

import java.util.ArrayList;
import java.util.HashMap;

public class newZjyHttp {

    static HashMap<String, Object> header;

    static {
        header = yktHeaders.getNewZjyMHeader();
    }

    public static HashMap<String, Object> getHeader() {
        return header;
    }

    public static void setHeader(HashMap<String, Object> ParmsHeader) {
        header = ParmsHeader;
    }

    public static void updataHeader(HashMap<String, Object> Header) {
        if (Header == null) return;
        for (String key : Header.keySet()) {
            header.put(key, Header.get(key));
        }
    }

    public static void removeHeader(ArrayList<String> Header) {
        if (Header == null) return;
        for (String key : Header) {
            header.remove(key);
        }
    }

    public static void removeHeader(String Header) {

            header.remove(Header);

    }

    public static void addHeader(String key , String v) {
            header.put(key,v);
    }

    public static String getUserCookie() {
        return userCookie;
    }

    public static void setUserCookie(String userCookies) {
        userCookie = userCookies;
    }

    private static String userCookie = "";


    public static String get(String requestUrl) {
        return get(requestUrl, "");
    }

    public static String get(String requestUrl, String body) {
        return get(requestUrl, null, body).getResp();
    }

    public static httpRespnose get(String requestUrl, String referer, String body) {

        if (!getUserCookie().isEmpty()) {

            header.put("Cookie", getUserCookie());

        }

        httpRespnose resp = Http.get(requestUrl, header, referer, body);

        return resp;
    }
      public static httpRespnose getT(String requestUrl, String referer, String body) {

        if (!getUserCookie().isEmpty()) {

            header.put("Cookie", getUserCookie());

        }

        httpRespnose resp = Http.get(requestUrl, header,null, referer, body,1);

        return resp;
    }


    public static String post(String requestUrl, String body) {
        return post(requestUrl, body, null, null).getResp();
    }

    public static httpRespnose post(String requestUrl, String body, String referer, String userAgent) {

        httpRespnose ret = null;


        if (!getUserCookie().isEmpty()) {
            header.put("Cookie", getUserCookie());
        }


        ret = Http.post(requestUrl, header, body, referer, userAgent, null);

        return ret;
    }


}
