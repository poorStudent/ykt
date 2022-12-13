package com.vms.ykt.yktStuMobile.newZJY;

import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.Util.Http;
import com.vms.ykt.Util.httpRespnose;
import com.vms.ykt.yktUtil.yktHeaders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (userCookies == null || userCookies.isEmpty()) return;
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
            if (!((String) Objects.requireNonNull(Header.get("Cookie"))).equals("")) {
                HashMap<String, Object> nckMap = parseCookie(upCookie);
                String ock = (String) Header.get("Cookie");
                HashMap<String, Object> ockMap = parseCookie(ock);
                for (String key : nckMap.keySet()) {
                    if (ockMap.containsKey(key)) {
                        //ockMap.remove(key);
                    }
                    ockMap.put(key, nckMap.get(key));
                }
                StringBuilder ck = new StringBuilder();
                for (String key : ockMap.keySet()) {
                    String v = (String) ockMap.get(key);
                    ck.append(";");
                    ck.append(key);
                    ck.append("=");
                    ck.append(v);
                }
                upCookie = ck.toString().replaceFirst(";", "");
            }
        }
        addHeader("Cookie", upCookie);
        //System.out.println(JSONObject.toJSONString(Header));
    }


    public static HashMap<String, Object> parseCookie(String resp) {
        HashMap<String, Object> cks = new HashMap<>();
        //String pattern = "([^=].+?)=(\"??.*\"??);?";
        String pattern = "([^=\\s;]+)=([^=\\s;]+)";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(resp);
        while (m.find()) {
            cks.put(m.group(1), m.group(2));
        }

        return cks;
    }

    public static void upCookie(String ck) {
        HashMap<String, Object> Header = getHeader();
        if (Header != null && Header.containsKey("Cookie")) {
            String upCookie = String.valueOf(Header.get("Cookie")) + ";" + ck;
            addHeader("Cookie", upCookie);
        }
    }

    public static void restCookie(String ck) {
        addHeader("Cookie", ck);

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

        //System.out.println(JSONObject.toJSONString(header));

        ret = Http.post(requestUrl, header, body, referer, userAgent, null);

        return ret;
    }


}
