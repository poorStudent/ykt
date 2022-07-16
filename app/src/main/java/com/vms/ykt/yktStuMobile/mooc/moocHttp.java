package com.vms.ykt.yktStuMobile.mooc;


import com.vms.ykt.Util.Http;
import com.vms.ykt.Util.httpRespnose;
import com.vms.ykt.yktUtil.yktHeaders;

import java.util.HashMap;

public class moocHttp {
    public static HashMap<String, Object> getHeader() {
        return header;
    }

    public static void setHeader(HashMap<String, Object> ParmsHeader) {
        header = ParmsHeader;
    }

    static HashMap<String, Object> header;

    static {
        header = yktHeaders.getMoocMHeader();
    }

    public static String getUserCookie() {
        return userCookie;
    }

    public static void setUserCookie(String userCookies) {
        userCookie = userCookies;
    }

    private static String userCookie = "";


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

        try {
            resp = Http.get(requestUrl, header, referer, body);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return resp;
    }

    public static String post(String requestUrl, String body, String referer) {
        return post(requestUrl, body, referer, null, null);
    }

    public static String post(String requestUrl, String body, String referer, String userAgent, String origin) {

        httpRespnose ret = null;
        String resp = "";

        if (!getUserCookie().isEmpty()) {
            header.put("Cookie", getUserCookie());
        }

        //  System.out.println(header.get("Cookie"));
        ret = Http.post(requestUrl, header, body, referer, userAgent, origin);
        resp = ret.getmResp();


        return resp;
    }

}
