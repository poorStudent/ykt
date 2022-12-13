package com.vms.ykt.yktUtil;


import com.vms.ykt.Util.Tool;

import java.util.HashMap;

public class yktHeaders {

    public static HashMap<String, Object> getZjyMHeader() {
        String host = "zjyapp.icve.com.cn";
        String defaultAgent = Tool.getUA();
        String ctype = "application/x-www-form-urlencoded; charset=utf-8";
        HashMap<String, Object> header = new HashMap<>();
        header.put("Connection", "Keep-Alive");
        header.put("Host", host);
        header.put("Content-Type", ctype);
        header.put("User-Agent", defaultAgent);
        //header.put("Accept-Encoding","gzip, deflate");
        return header;
    }

    public static HashMap<String, Object> getZjyWHeader() {

        String host = "security.zjy2.icve.com.cn";
        String Origin = "https://zjy2.icve.com.cn";
        String defaultAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.75 Safari/537.36 Edg/100.0.1185.39";
        String ctype = "application/x-www-form-urlencoded; charset=utf-8";
        HashMap<String, Object> header = new HashMap<>();
        header.put("Connection", "Keep-Alive");
        header.put("Content-Type", ctype);
        header.put("Origin", Origin);
        header.put("User-Agent", defaultAgent);
        header.put("Accept", "application/json, text/javascript, */*; q=0.01");
        // header.put("Accept-Encoding", "gzip, deflate, br");
        header.put("X-Requested-With", "XMLHttpRequest");
        header.put("Host", host);
        return header;
    }

    public static HashMap<String, Object> getNewZjyMHeader() {

        String host = "user.icve.com.cn";
        String Origin = "https://user.icve.com.cn";
        String defaultAgent = "Mozilla/5.0 (Linux; Android 11; M2007J17C Build/RKQ1.200826.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/92.0.4515.131 Mobile Safari/537.36 whatyApp whatyApiApp";
        String ctype = "application/x-www-form-urlencoded; charset=UTF-8";

        HashMap<String, Object> header = new HashMap<>();
        header.put("Connection", "Keep-Alive");
        header.put("Content-Type", ctype);
        header.put("Origin", Origin);
        header.put("User-Agent", defaultAgent);
        //header.put("Sec-Fetch-Site", "same-site");
       // header.put("Sec-Fetch-Dest", "empty");
        //header.put("Sec-Fetch-Mode", "cors");
        //header.put("Accept", "application/json, text/javascript, */*; q=0.01");
        // header.put("Accept-Encoding", "gzip, deflate, br");
       // header.put("X-Requested-With", "XMLHttpRequest");
        header.put("Host", host);
        return header;
    }

    public static HashMap<String, Object> getMoocWHeader() {

        String host = "moocapp.icve.com.cn";
        String defaultAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.75 Safari/537.36 Edg/100.0.1185.39";
        String ctype = "application/x-www-form-urlencoded; charset=utf-8";
        String Origin = "https://moocapp.icve.com.cn";
        HashMap<String, Object> header = new HashMap<>();
        header.put("Connection", "Keep-Alive");
        header.put("Content-Type", ctype);
        header.put("User-Agent", defaultAgent);
        header.put("Accept", "application/json, text/javascript, */*; q=0.01");
        // header.put("Accept-Encoding", "gzip, deflate, br");
        header.put("X-Requested-With", "XMLHttpRequest");
        header.put("Host", host);
        return header;
    }

    public static HashMap<String, Object> getMoocMHeader() {

        String host = "moocapp.icve.com.cn";
        //String defaultAgent = Tool.getUA();
        String defaultAgent ="okhttp/4.5.0";
        String ctype = "application/x-www-form-urlencoded; charset=utf-8";
        HashMap<String, Object> header = new HashMap<>();
        header.put("Connection", "Keep-Alive");
        header.put("Host", host);
        header.put("Content-Type", ctype);
        header.put("User-Agent",defaultAgent);
        //header.put("Accept-Encoding","gzip, deflate");
        return header;
    }

    public static HashMap<String, Object> getIcveWHeader() {

        String host = "www.icve.com.cn";
        String defaultAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.75 Safari/537.36 Edg/100.0.1185.39";
        String ctype = "application/x-www-form-urlencoded; charset=utf-8";
        String Origin = "https://www.icve.com.cn";
        HashMap<String, Object> header = new HashMap<>();
        header.put("Connection", "Keep-Alive");
        header.put("Content-Type", ctype);
        header.put("User-Agent", defaultAgent);
        header.put("Origin", Origin);
        header.put("Accept", "application/json, text/javascript, */*; q=0.01");
        // header.put("Accept-Encoding", "gzip, deflate, br");
        header.put("X-Requested-With", "XMLHttpRequest");
        header.put("Host", host);
        return header;
    }

    public static String appv = "";

    /**
     * public static String geApitHeaders(zjyUser zjyUser) {
     * <p>
     * return getHeaders()+"newToken=" + zjyUser.getNewToken();
     * }
     */
    public static String getHeaders() {
        StringBuilder postParam = new StringBuilder();
        postParam.append("equipmentAppVersion=" + appv + "&");
        postParam.append("equipmentModel=Redmi M2007J17C&");
        postParam.append("equipmentApiVersion=10&");
        return postParam.toString();
    }
}
