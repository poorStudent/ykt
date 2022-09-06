package com.vms.ykt.yktStuMobile.zjy;

/*
     ******************************

     Create : XuanRan
     Date : 
     Used For : 

     ******************************
*/

import com.vms.ykt.Util.Http;
import com.vms.ykt.Util.httpRespnose;
import com.vms.ykt.yktUtil.yktHeaders;


import java.util.HashMap;

public class zjyHttpM {

    static String emit = "";
    static String device = "";

    static HashMap<String, Object> header;

    static {
        header = yktHeaders.getZjyMHeader();
    }

    public static HashMap<String, Object> getHeader() {
        return header;
    }

    public static void setHeader(HashMap<String, Object> ParmsHeader) {
        header = ParmsHeader;
    }


    public static String getUserCookie() {
        return userCookie;
    }

    public static void setUserCookie(String userCookies) {
        userCookie = userCookies;
    }

    private static String userCookie = "";

    public static void setInfo(String e, String d) {
        emit = e;
        device = d;
    }


    public static String get(String requestUrl) {
        return get(requestUrl, null, "");
    }

    public static String get(String requestUrl, String body) {
        return get(requestUrl, null, body);
    }

    public static String get(String requestUrl, String referer, String body) {

        String resp = "";
        if (!getUserCookie().isEmpty()) {

            header.put("Cookie", getUserCookie());

        }


        resp = Http.get(requestUrl, header, referer, body).getResp();;

        return resp;
    }

    public static String post(String requestUrl, String body) {
        return post(requestUrl, body, null, null);
    }

    public static String post(String requestUrl, String body, String referer, String userAgent) {

        httpRespnose ret = null;
        String resp = "";

        if (!getUserCookie().isEmpty()) {
            header.put("Cookie", getUserCookie());
        }

        ret = Http.post(requestUrl, header, body, referer, userAgent, null);
        resp = ret.getResp();


        return resp;
    }


}
