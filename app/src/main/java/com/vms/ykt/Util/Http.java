package com.vms.ykt.Util;

import java.util.HashMap;

public class Http {


    public static String getUserCookie() {
        return userCookie;
    }

    public static void setUserCookie(String userCookies) {
        userCookie = userCookies;
    }

    private static String userCookie = "";

    public static String get(String requestUrl, HashMap<String, Object> header, String referer, String body) {

        String resp = "";


        if (!getUserCookie().isEmpty()) {
            if (!header.containsKey("Cookie")) {
                header.put("Cookie", getUserCookie());
            }
        }


        if (referer != null) {
            header.put("Referer", referer);
        }

        resp = httpTool.getJ(requestUrl, header, body).getmResp();

        return resp;
    }

    public static httpRespnose post(String requestUrl, HashMap<String, Object> header, String body, String referer, String userAgent, String origin) {

        httpRespnose ret = null;
        String resp = "";


        if (!getUserCookie().isEmpty()) {
            if (!header.containsKey("Cookie")) {
                header.put("Cookie", getUserCookie());
            }
        }

        if (referer != null) {
            header.put("Referer", referer);
        }
        if (origin != null) {
            header.put("Origin", origin);
        }

        if (userAgent != null) {
            header.put("User-Agent", userAgent);
        }

        //  System.out.println(header.get("Cookie"));
        ret = httpTool.postJ(requestUrl, header, body);

        return ret;
    }

}
