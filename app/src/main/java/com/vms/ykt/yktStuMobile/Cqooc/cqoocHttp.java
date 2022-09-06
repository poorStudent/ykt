package com.vms.ykt.yktStuMobile.Cqooc;


import com.vms.ykt.Util.Http;
import com.vms.ykt.Util.httpRespnose;

import java.io.Serializable;
import java.util.HashMap;

public class cqoocHttp implements Serializable {

    static HashMap<String ,Object> header ;

    static {
        header=new HashMap<>();
        header.put("Connection","keep-alive");
        header.put("Host","cq-middle.celer.cc");
        //header.put("Origin","http://www.cqooc.com");
        //header.put("Accept: ","*/*");
        header.put("User-Agent","Mozilla/5.0 (Linux; Android 11; M2007J17C Build/RKQ1.200826.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/92.0.4515.131 Mobile Safari/537.36 uni-app Html5Plus/1.0 (Immersed/33.090908)");
    }

    public static HashMap<String, Object> getHeader() {
        return header;
    }

    public static void setHeader(HashMap<String, Object> ParmsHeader) {
        header = ParmsHeader;
    }

    public  String getUserCookie() {
        return userCookie;
    }

    public  void setUserCookie(String userCookies) {
        userCookie = userCookies;
    }

    private  String userCookie="";

    public  String get(String requestUrl){
        return get(requestUrl,null,"");
    }

    public  String get(String requestUrl,String body){
        return get(requestUrl,null,body);
    }

    public  String get(String requestUrl,String referer,String body){

        String resp="";
        if (!userCookie.isEmpty()) header.put("Cookie", getUserCookie());;
        try {
            resp= Http.get(requestUrl, header,referer,body).getResp();;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return resp;
    }

    public  String post(String requestUrl,String body,String referer){
        return post(requestUrl, body, referer,null,null);
    }

    public  String post(String requestUrl,String body,String referer,String userAgent,String origin){

        httpRespnose ret;
        String resp="";

        if (!getUserCookie().isEmpty()){
            header.put("Cookie", getUserCookie());
        }

        //System.out.println(header.get("Cookie"));

        ret= Http.post(requestUrl, header,body,referer,userAgent,origin);
        resp=ret.getResp();

        return resp;
    }
}
