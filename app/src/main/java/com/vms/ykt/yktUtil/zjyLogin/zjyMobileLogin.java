package com.vms.ykt.yktUtil.zjyLogin;


import android.util.Log;

import com.vms.ykt.Util.Tool;
import com.vms.ykt.Util.httpRespnose;
import com.vms.ykt.Util.httpTool;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class zjyMobileLogin {

    static String baseUrl = "https://zjyapp.icve.com.cn/newMobileAPI";
    static String login = "/MobileLogin/newSignIn";
    public static String appv = "";

    /**
     * 手机端登录
     */
    public static String[] login(String user, String pass) {

        String[] resp = null;
        StringBuilder postParam = new StringBuilder();
        // postParam.append("clientId=2829e8a1bcd44efd9d2e3cca8b606aea&");
        postParam.append("clientId=null&");
        postParam.append("sourceType=2&");
        postParam.append("userPwd=" + pass.trim() + "&");
        postParam.append("userName=" + user.trim() + "&");
        postParam.append("appVersion=" + appv + "&");
        postParam.append("equipmentAppVersion=" + appv + "&");
        postParam.append("equipmentModel=" + Tool.getDEVICEModle() + "&");
        postParam.append("equipmentApiVersion=10");
        String postParams = postParam.toString();
        //Log.d(TAG, "login: " + postParams);
        resp = loginPost(baseUrl + login, postParams);
        return resp;
    }

    private static String TAG = zjyMobileLogin.class.getSimpleName();

    public static String[] loginPost(String requestUrl, String body) {
        String resp = "";
        String ck = "";
        String emit = "";
        String device = "";
        Map<String, Object> header = new HashMap<>();
        emit = Tool.getTime();
        device = Tool.getDevice(appv, emit);
        Log.d(TAG, "login: " + emit);
        header.put("emit", emit);
        header.put("device", device);
        header.put("Connection", "Keep-Alive");
        header.put("Content-Type", "application/x-www-form-urlencoded");
        header.put("Host", "zjyapp.icve.com.cn");
        header.put("User-Agent", "okhttp/4.5.0");
        httpRespnose ret = null;
        ret = httpTool.postT(requestUrl, header, body);
        resp = ret.getmResp();
        ck = ret.getHeaders().get("Set-Cookie");
        Log.d(TAG, "loginPost: " + ck);
        String reg = "auth=(.+?)(?=;)";
        Matcher m = Pattern.compile(reg).matcher(ck); //进行匹配
        while (m.find()) ck = m.group();
        return new String[]{resp, ck};
    }
}
