package com.vms.ykt.yktStuMobile.newZJY;

import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.Util.Http;
import com.vms.ykt.Util.httpRespnose;
import com.vms.ykt.yktUtil.yktHeaders;

import java.util.ArrayList;
import java.util.HashMap;

public class newZjyHttp {

    static HashMap<String, Object> header;
    private static String userCookie = "";

    static {
        header = yktHeaders.getNewZjyMHeader();
    }


    public static String getUserCookie() {
        return (String) header.get("Cookie");
    }

    public static void setUserCookie(String userCookies) {
        if (userCookies==null || userCookies.isEmpty()) return;
        header.put("Cookie", userCookies);
    }

    public static HashMap<String, Object> getHeader() {
        return header;
    }

    public static void setHeader(HashMap<String, Object> ParmsHeader) {
        header = ParmsHeader;
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

    public static void addHeader(String key, String v) {
        header.put(key, v);
    }

    public static void addHeader(HashMap<String, Object> Header) {
        if (Header == null) return;
        for (String key : Header.keySet()) {
            header.put(key, Header.get(key));
        }
    }

    public static void addCookie(String upCookie) {
        HashMap<String, Object> Header = getHeader();
        if (Header != null && Header.containsKey("Cookie")) {
           String ock = (String) Header.get("Cookie");
            upCookie = upCookie + ";" + ock;
        }
       addHeader("Cookie", upCookie);

    }



    public static String get(String requestUrl) {
        return get(requestUrl, "");
    }

    public static String get(String requestUrl, String body) {
        return get(requestUrl, null, body).getResp();
    }

    public static httpRespnose get(String requestUrl, String referer, String body) {

        httpRespnose resp = Http.get(requestUrl, header, referer, body);
        return resp;
    }

    public static httpRespnose getT(String requestUrl, String referer, String body) {


        httpRespnose resp = Http.get(requestUrl, header, null, referer, body, 1);

        return resp;
    }


    public static String post(String requestUrl, String body) {
        return post(requestUrl, body, null, null).getResp();
    }

    public static httpRespnose post(String requestUrl, String body, String referer, String userAgent) {

        httpRespnose ret = null;

        System.out.println(JSONObject.toJSONString(header));

        ret = Http.post(requestUrl, header, body, referer, userAgent, null);

        return ret;
    }


}
