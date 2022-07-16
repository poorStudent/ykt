package com.vms.ykt.yktStuWeb.Cqooc;


import com.vms.ykt.Util.Http;
import com.vms.ykt.Util.httpRespnose;

import java.io.Serializable;
import java.util.HashMap;

public class cqoocHttp implements Serializable {

    static HashMap<String ,Object> header ;

    static {
        header=new HashMap<>();
        header.put("Connection","keep-alive");
        header.put("Host","www.cqooc.com");
        header.put("Origin","http://www.cqooc.com");
        //header.put("Accept: ","*/*");
        header.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.13; rv:73.0) Gecko/20100101 Firefox/73.0");
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

    public String get(String requestUrl){
        return get(requestUrl,null,"");
    }

    public  String get(String requestUrl,String body){
        return get(requestUrl,null,body);
    }

    public  String get(String requestUrl,String referer,String body){

        String resp="";
        if (!userCookie.isEmpty()) header.put("Cookie", getUserCookie());;
        try {
            resp= Http.get(requestUrl, header,referer,body);
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
        resp=ret.getmResp();

        return resp;
    }
}
