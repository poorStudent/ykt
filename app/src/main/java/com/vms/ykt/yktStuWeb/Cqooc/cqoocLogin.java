package com.vms.ykt.yktStuWeb.Cqooc;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;


import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.net.URLEncoder;

public class cqoocLogin {

    private org.mozilla.javascript.Context rhino;
    private Scriptable scope;
    private String TAG=cqoocLogin.class.getSimpleName();

    private void InitCallJsFunction() {
        rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        try{
            scope = rhino.initStandardObjects();
            // 这两句是设置当前的类做为上下文以及获取当前的类加载器，以便于 rhino 通过反射获取档期类
            ScriptableObject.putProperty(scope,"javaContext", Context.javaToJS(this,scope));
            ScriptableObject.putProperty(scope,"javaLoader", Context.javaToJS(cqoocLogin.class.getClassLoader(),scope));
            //执行 js 代码
            Object x = rhino.evaluateString(scope, SHA256.js2, TAG, 1, null);
        }finally {
            //退出
            //  org.mozilla.javascript.Context.exit();
        }
    }

    public Object callFunction(String functionName,Object[] functionParams){
        Function function = (Function) scope.get(functionName,scope);
        return  function.call(rhino,scope,scope,functionParams);
    }

    public Object callFunction(String functionName){
        return  callFunction(functionName, new String[]{});
    }

    public String LoignIng(String name, String password) {
        InitCallJsFunction();
        String captchaToken = null;
        String cnonce;
        String cn = null;
        String captcha ;
        String hash = null;
        String encodePassword;
        String nonce = getNonce();
        if (nonce == null) {
            return null;
        }
        String pw = nonce;
        try {
            pw += callFunction("sha256", new String[]{password});
            cn = callFunction("cnonce").toString();
            pw += cn;
            hash = callFunction("sha256", new String[]{pw}).toString();
            captcha = captchaToken != null ? "&captchaToken=" + URLEncoder.encode(captchaToken, "utf8") : "";
            cnonce = cn + captcha;
            encodePassword = hash;
            if (encodePassword == null || nonce == null || cnonce == null) {
                Log.d(TAG, "LoignIng: "+"Password为null!");
                return null;
            }
            return login2(name, encodePassword, nonce, cnonce);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    private String login2(String username, String encodePassword, String nonce, String cnonce) {
        String requestUrl = "http://www.cqooc.com/user/login?";
        String request=" http://www.cqooc.com/";
        String body="username="+username.toLowerCase() + "&password=" + encodePassword + "&nonce=" + nonce + "&cnonce=" + cnonce + "";
        requestUrl=requestUrl+body;
        Log.d(TAG, "nonce: "+nonce);
        Log.d(TAG, "cnonce: "+cnonce);
        String loginStr = new cqoocHttp().post(requestUrl,"",request);
        //{"code":31,"msg":"InvalidCaptchaToken or missing captcha token"}
        if (loginStr.contains("{\"code\":31")) {
            Log.d(TAG, "login2: 无效验证码.."+loginStr);
            return null;
        }
        if (!loginStr.contains("ok")) {
            Log.d(TAG, "login2: 登录发生错误"+ loginStr);
            return null;
        }

        return JSONObject.parseObject(loginStr).getString("xsid");
    }

    /**
     * 登陆之前的必要参数获
     *
     * @return {"nonce":"34108F654639A4A7"}
     */
    private String getNonce() {
        String requestUrl = "http://www.cqooc.com/user/login";
        String body= "ts=" + System.currentTimeMillis();
        String request="http://www.cqooc.com/";
        String resp = new cqoocHttp().get(requestUrl,request, body);
        if (resp==null||!resp.contains("nonce")) {
            Log.d(TAG, "getNonce: 获取nonce失败" + resp);
            return "";
        }
        String loginBeforeDto = JSONObject.parseObject(resp).getString("nonce");
        Log.d(TAG, "getNonce: "+loginBeforeDto);
        return loginBeforeDto;
    }

}
