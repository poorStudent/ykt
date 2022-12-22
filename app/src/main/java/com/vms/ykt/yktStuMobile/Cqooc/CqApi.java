package com.vms.ykt.yktStuMobile.Cqooc;


import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;

public class CqApi {
    

    static String baseUrl = "baseUrl=http://www.cqooc.com&";

    static String Url = "http://cq-middle.celer.cc/";

    public static  String Login(String username, String password) {
        String path = "path=/app/nologin/api/login";
        StringBuilder vBuilder = new StringBuilder();
        vBuilder.append(baseUrl);
        vBuilder.append(path);
        vBuilder.append("&xsid=");
        HashMap<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);
        body.put("type", "1");
        vBuilder.append("&data=");
        vBuilder.append(JSONObject.toJSONString(body));
        vBuilder.append("&method=POST");
        String resp =cqoocHttp.post(Url, vBuilder.toString(), null);
        return resp;
    }

    public static String getUserSession(String xsid){
        String path ="path=/user/session";
        StringBuilder vBuilder = new StringBuilder();
        vBuilder.append(baseUrl);
        vBuilder.append(path);
        vBuilder.append("&xsid=");
        vBuilder.append(xsid);
        vBuilder.append("&data={\"params\":{}}");
        vBuilder.append("&method=GET");
        String resp =cqoocHttp.post(Url, vBuilder.toString(), null);
        return resp;
    }

    public static String getUserInfo(String xsid){
        String path ="path=/app/login/api/userinfo/get";
        StringBuilder vBuilder = new StringBuilder();
        vBuilder.append(baseUrl);
        vBuilder.append(path);
        vBuilder.append("&xsid=");
        vBuilder.append(xsid);
        vBuilder.append("&data={}");
        vBuilder.append("&method=GET");
        String resp =cqoocHttp.post(Url, vBuilder.toString(), null);
        return resp;
    }
}
