package com.vms.ykt.Util;

import java.util.HashMap;
import java.util.Map;

public class Http {


    public static String getUserCookie() {
        return userCookie;
    }

    public static void setUserCookie(String userCookies) {
        userCookie = userCookies;
    }

    private static String userCookie = "";


    public static httpRespnose get(String url, HashMap<String, Object> header, String referer, String body) {

        return get(url, header, null, referer, body, 2);
    }

    public static httpRespnose get(String requestUrl, HashMap<String, Object> header, Map<String, Object> query, String referer, String body, int tp) {


        if (!getUserCookie().isEmpty()) {
            if (!header.containsKey("Cookie")) {
                header.put("Cookie", getUserCookie());
            }
        }


        if (referer != null) {
            header.put("Referer", referer);
        }
        httpRespnose resp;
        if (tp == 1) {
            resp = httpTool.getT(requestUrl, header, query, body);
        } else {

            resp = httpTool.getJ(requestUrl, header, query, body);
        }

        return resp;
    }


    public static httpRespnose post(String requestUrl, HashMap<String, Object> header, String body, String referer, String userAgent, String origin) {
      return   post(requestUrl,  header,  body, referer,  userAgent, origin,2);
    }

    public static httpRespnose post(String requestUrl, HashMap<String, Object> header, String body, String referer, String userAgent, String origin, int tp) {

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

        if (tp == 1) {
            ret = httpTool.postT(requestUrl, header, body);
        } else {
            ret = httpTool.postJ(requestUrl, header, body);
        }

        return ret;
    }

}
