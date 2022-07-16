package com.vms.ykt.yktStuWeb.zjy;

/*
     ******************************

     Create : XuanRan
     Date : 
     Used For : 

     ******************************
*/

import com.vms.ykt.Util.Http;
import com.vms.ykt.Util.httpRespnose;
import com.vms.ykt.yktUtil.yktHeaders;


import java.io.Serializable;
import java.util.HashMap;

public class zjyHttpW implements Serializable
{


    static HashMap<String ,Object> header ;
    static {
    header= yktHeaders.getZjyWHeader();
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

      /**  if(referer!=null){
            header.put("Referer",referer);
        }**/

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

        httpRespnose ret=null;
        String resp="";

      if (!getUserCookie().isEmpty()){
            header.put("Cookie", getUserCookie());
        }

            ret= Http.post(requestUrl, header,body,referer,userAgent,origin);
            resp=ret.getmResp();


        return resp;
    }


}
