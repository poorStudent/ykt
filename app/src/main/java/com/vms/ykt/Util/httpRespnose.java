package com.vms.ykt.Util;

import android.util.Pair;

import okhttp3.Cookie;
import okhttp3.Headers;

import java.net.ResponseCache;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class httpRespnose {

    public httpRespnose(Map<String, List<String>> ParmsMHearderFileds, String ParmsCode, byte[] ParmsBytes, String ParmsMResp) {
        mHearderFileds = ParmsMHearderFileds;
        code = ParmsCode;
        RespBytes = ParmsBytes;
        Resp = ParmsMResp;
    }


    public  httpRespnose(){
        this(null,null,null,null);
    }


    private String SetCookie;

    public String getSetCookie() {
        return SetCookie;
    }

    public void setSetCookie(String setCookie) {
        SetCookie = setCookie;
    }


    private String Location;

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }


    private Map<String , List<String>> mHearderFileds;

    public Map<String, List<String>> getHearderFileds() {
        return mHearderFileds;
    }


    public void setHearderFileds(Map<String, List<String>> hearderFileds) {
        mHearderFileds = hearderFileds;
        if (mHearderFileds==null || mHearderFileds.isEmpty())return;
        List<String> cks= mHearderFileds.get("Set-Cookie");
        if (cks==null) return;;
        String ck = "";
        for (String c:cks){
            ck=ck+";"+c;
        }
        String vS = ck.replaceFirst(";", "");
        setSetCookie(vS);
    }


    private  String code;

    public  String getCode() {
        return code;
    }

    public  void setCode(String ParmsCode) {
        code = ParmsCode;
    }


    private byte[] RespBytes;

    public byte[] getRespBytes() {
        return RespBytes;
    }

    public void setRespBytes(byte[] ParmsBytes) {
        RespBytes = ParmsBytes;
    }


    private String Resp;

    public String getResp() {
        return Resp;
    }

    public void setResp(String ParmsMResp) {
        Resp = ParmsMResp;
    }




}
interface mRespnose{
    void bodyString(String resp);
    void bodyBytes(byte[] respBytes);
    void bodyHeaders(Headers headers);
    void bodyHeaderFileds( Map<String, List<String>> headers);
}