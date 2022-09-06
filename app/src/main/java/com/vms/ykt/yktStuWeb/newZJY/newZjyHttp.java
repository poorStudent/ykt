package com.vms.ykt.yktStuWeb.newZJY;

import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.Util.Http;
import com.vms.ykt.Util.httpRespnose;
import com.vms.ykt.Util.httpTool;
import com.vms.ykt.yktUtil.yktHeaders;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.SecretKeyFactorySpi;

public class newZjyHttp {

    static HashMap<String, Object> header;

    static {
        header=new HashMap<>();
        restHeard();
    }

    public static void  restHeard(){
        header.put("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        header.put("Host","user.icve.com.cn");
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.5112.102 Safari/537.36 Edg/104.0.1293.70");
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

    public static String get(String requestUrl) {
        return get(requestUrl,"");
    }

    public static String get(String requestUrl, String body) {
        return get(requestUrl, null, body).getResp();
    }

    public static httpRespnose get(String requestUrl, String referer, String body) {

        if (!getUserCookie().isEmpty()) {

            header.put("Cookie", getUserCookie());

        }

        httpRespnose ret = Http.get(requestUrl, header,referer, body);

        return ret;
    }

    public static httpRespnose getT(String requestUrl, String referer, String body) {

        if (!getUserCookie().isEmpty()) {

            header.put("Cookie", getUserCookie());

        }

        httpRespnose ret = Http.get(requestUrl, header,null,referer, body,1);

        return ret;
    }



    public static String post(String requestUrl, String body) {
        return post(requestUrl, body, null, null).getResp();
    }

    public static httpRespnose post(String requestUrl, String body, String referer, String userAgent) {
        return  post(requestUrl, body, referer, userAgent, null);

    }

    public static httpRespnose post(String requestUrl, String body, String referer, String userAgent,String origin) {

        httpRespnose ret = null;

        if (!getUserCookie().isEmpty()) {
            header.put("Cookie", getUserCookie());
        }
        ret = Http.post(requestUrl, header, body, referer, userAgent, origin);


        return ret;
    }

    public static httpRespnose postT(String requestUrl, String body, String referer, String userAgent) {

        httpRespnose ret = null;

        if (!getUserCookie().isEmpty()) {
            header.put("Cookie", getUserCookie());
        }
        //ret = Http.post(requestUrl, header, body, referer, userAgent, null);
        ret = Http.post(requestUrl, header, body, referer, userAgent, null,1);


        return ret;
    }


}
