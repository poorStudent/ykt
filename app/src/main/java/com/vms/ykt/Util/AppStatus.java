package com.vms.ykt.Util;

import android.util.Log;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AppStatus {
    private static final String TAG = AppStatus.class.getSimpleName();

    public static void AppStatusInit() {
        String s1 = "";
        httpRespnose resp = null;
        String ret = "";
        EncrypDES vEncrypDES = null;

        try {
            vEncrypDES = new EncrypDES();
            resp = HttpTool.getJ(vEncrypDES.decrypt("90a56f8f06527516c7d4656fe2250b800f734c3de961cc5e1db75ac6da3a86f01029f17d1590a4760b831a84ceb2c587764ed271cffd60c48636057269ca5809"), null, null,null);
            ret = Tool.decodeUnicode(resp.getResp());
            Tool.logi( "",ret);
            ret=getAppStatus(ret, "\"html_content\":\"(.+?)\"");
            Log.d(TAG, "AppStatusInit: " + ret);
            s1 = getAppStatus(ret, "@all@(.+?)@all@").trim();
            AppStatus.setAll(vEncrypDES.decrypt(s1));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            s1 = getAppStatus(ret, "@zjysk@(.+?)@zjysk@").trim();
            AppStatus.setZjysk(vEncrypDES.decrypt(s1));
            Log.d(TAG, "AppStatusInit: " + s1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

        s1 = getAppStatus(ret, "@zjyzy@(.+?)@zjyzy@").trim();
            Log.d(TAG, "AppStatusInit: " + s1);
        AppStatus.setZjyks(vEncrypDES.decrypt(s1));

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

        s1 = getAppStatus(ret, "@zjyks@(.+?)@zjyks@").trim();
            Log.d(TAG, "AppStatusInit: " + s1);
        AppStatus.setZjyks(vEncrypDES.decrypt(s1));

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
        s1 = getAppStatus(ret, "@cqooc@(.+?)@cqooc@").trim();
        AppStatus.setCqooc(vEncrypDES.decrypt(s1));
        Log.d(TAG, "AppStatusInit: " + s1);

        s1 = getAppStatus(ret, "@yunhei@(.+?)@yunhei@").trim();
        yunhei.clear();
        for (String string : s1.split("\\|")) {
            yunhei.add(string);
            Log.d(TAG, "AppStatusInit: " + string);

        }
        s1 = getAppStatus(ret, "@yunbai@(.+?)@yunbai@").trim();
        yunbai.clear();
        for (String string : s1.split("\\|")) {
            yunbai.add(string);
            Log.d(TAG, "AppStatusInit: " + string);

        }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String all;

    public static ArrayList<String> getYunhei() {
        return yunhei;
    }

    public static void setYunhei(ArrayList<String> yunhei) {
        AppStatus.yunhei = yunhei;
    }

    private static ArrayList<String> yunhei = new ArrayList<>();

    public static ArrayList<String> getYunbai() {
        return yunbai;
    }

    public static void setYunbai(ArrayList<String> yunbai) {
        AppStatus.yunbai = yunbai;
    }

    private static ArrayList<String> yunbai = new ArrayList<>();

    public static String getAll() {
        return all;
    }

    public static void setAll(String all) {
        AppStatus.all = all;
    }

    public static String getZjysk() {
        return zjysk;
    }

    public static void setZjysk(String zjysk) {
        AppStatus.zjysk = zjysk;
    }

    public static String getZjyzy() {
        return zjyzy;
    }

    public static void setZjyzy(String zjyzy) {
        AppStatus.zjyzy = zjyzy;
    }

    public static String getZjyks() {
        return zjyks;
    }

    public static void setZjyks(String zjyks) {
        AppStatus.zjyks = zjyks;
    }

    public static String getCqooc() {
        return cqooc;
    }

    public static void setCqooc(String cqooc) {
        AppStatus.cqooc = cqooc;
    }

    private static String zjysk;
    private static String zjyzy;
    private static String zjyks;
    private static String cqooc;

    private static String getAppStatus(String resp, String reg) {
        Pattern p = Pattern.compile(reg); //编译对象
        Matcher m = p.matcher(resp); //进行匹配
        String Status1 = "";
        while (m.find()) {
            Status1 = m.group(1);
        }
        return Status1;
    }


}
