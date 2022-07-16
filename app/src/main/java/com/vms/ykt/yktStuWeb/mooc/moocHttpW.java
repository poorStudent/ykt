package com.vms.ykt.yktStuWeb.mooc;


import com.vms.ykt.Util.Http;
import com.vms.ykt.Util.httpRespnose;
import com.vms.ykt.yktUtil.yktHeaders;


import java.io.Serializable;
import java.util.HashMap;

public class moocHttpW implements Serializable {

    static HashMap<String ,Object> header ;

    static {
        header= yktHeaders.getMoocWHeader();
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

        if (!getUserCookie().isEmpty()){
            header.put("Cookie", getUserCookie());
        }
            resp= Http.get(requestUrl, header,referer,body);
        return resp;
    }

    public  String post(String requestUrl,String body,String referer){
        return post(requestUrl, body, referer,null,null);
    }
    public  String post(String requestUrl,String body,String referer,String userAgent,String origin){

        httpRespnose ret=null;
        String resp="";

        if (!getUserCookie().isEmpty()){
            header.put("Cookie", getUserCookie());
        }

            //  System.out.println(header.get("Cookie"));
            ret= Http.post(requestUrl, header,body,referer,userAgent,origin);
            resp=ret.getmResp();


        return resp;
    }

}
