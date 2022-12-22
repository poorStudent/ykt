package com.vms.ykt.yktUtil.zjy;


import android.content.Context;

import com.vms.ykt.Util.Tool;
import com.vms.ykt.Util.httpRespnose;
import com.vms.ykt.Util.HttpTool;

import java.io.IOException;
import java.util.HashMap;

public class zjyWebLogin {

    static String loginWeb="https://zjy2.icve.com.cn/api/common/login/login";
    static String verifycodeUrl="https://zjy2.icve.com.cn/api/common/VerifyCode/index?t=0.26473944313076414";

    public static String getVerifycode() {
        String resp = "";
        resp = HttpTool.downPic(verifycodeUrl,"test.jpg");
        return resp.replaceFirst(" path=(.*)","");
    }

    /***
     * web 端登录
     * */
    public static String[] login(String userName, String userPwd, Context context) {
        httpRespnose ret=null;
        String ck="";
        String resp="";
        String verifyCode= getVerifycode();
        Tool.toast(context,"验证码已保存到 test.jpg 自行查看");
        String Code="";
        try {
            ret = loginPost(userName,userPwd,Code);
            ck= ret.getSetCookie();
            resp=ret.getResp();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ck=ck.replaceFirst("domain(.*)","");
        return new String[]{resp,ck,verifyCode};
    }

    public static httpRespnose loginPost(String userName, String userPwd, String verifyCode) throws IOException {

        String Referer="https://zjy2.icve.com.cn/portal/login.html";
        HashMap<String,Object> header = new HashMap<>();
        header.put("Referer",Referer);
        StringBuilder postParam = new StringBuilder();
        postParam.append("userName="+userName+"&");
        postParam.append("userPwd="+userPwd+"&");
        postParam.append("verifyCode="+verifyCode);
        String postParams = postParam.toString();

       httpRespnose resp = HttpTool.postT(loginWeb,header, postParams);
        return resp;
    }


}
