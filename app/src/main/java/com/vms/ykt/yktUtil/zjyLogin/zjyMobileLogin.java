package com.vms.ykt.yktUtil.zjyLogin;


import android.util.Log;

import com.vms.ykt.Util.SystemUtil;
import com.vms.ykt.Util.httpRespnose;
import com.vms.ykt.Util.httpTool;
import com.vms.ykt.yktUtil.zjyTool;

import java.util.Arrays;
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
        appv = "2.8.43";
        StringBuilder postParam = new StringBuilder();
        postParam.append("clientId=2829e8a1bcd44efd9d2e3cca8b606aea&");
        // postParam.append("clientId=null&");
        postParam.append("sourceType=2&");
        postParam.append("userPwd=" + pass.trim() + "&");
        postParam.append("userName=" + user.trim() + "&");
        postParam.append("appVersion=" + appv + "&");
        postParam.append("equipmentAppVersion=" + appv + "&");
        postParam.append("equipmentModel=" + SystemUtil.getSystemModel() + "&");
        postParam.append("equipmentApiVersion=9");
        String postParams = postParam.toString();
        Log.d(TAG, "login: " + postParams);
        resp = loginPost(baseUrl + login, postParams);
        return resp;
    }

    private static String TAG = zjyMobileLogin.class.getSimpleName();

    public static String[] loginPost(String requestUrl, String body) {
        String resp ;
        String ck = null;
        Map<String, Object> header = new HashMap<>();
        String[] vEmitDevice = zjyTool.getEmitDevice();
        String emit = vEmitDevice[0];
        String device = vEmitDevice[1];
        header.put("emit", emit);
        header.put("device", device);
        header.put("Connection", "Keep-Alive");
        header.put("Content-Type", "application/x-www-form-urlencoded");
        header.put("Host", "zjyapp.icve.com.cn");
        header.put("User-Agent", "okhttp/4.5.0");
        httpRespnose ret = null;
        ret = httpTool.postJ(requestUrl, header, body);
        resp = ret.getResp();
        if (resp != null && !resp.isEmpty()) {
            ck = Arrays.toString(ret
                    .getHearderFileds().get("Set-Cookie").toArray());
            Log.d(TAG, "loginPost: " + ck);
            String reg = "auth=(.+?)(?=;)";
            Matcher m = Pattern.compile(reg).matcher(ck); //进行匹配
            while (m.find()) ck = m.group();
        }
        return new String[]{resp, ck};
    }
}
