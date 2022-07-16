package com.vms.ykt.Util;

import okhttp3.Headers;

import java.util.List;
import java.util.Map;

public class httpRespnose {
    public httpRespnose(Map<String, List<String>> ParmsMHearderFileds, Headers ParmsHeaders, String ParmsCode, byte[] ParmsBytes, String ParmsMResp) {
        mHearderFileds = ParmsMHearderFileds;
        MHeaders = ParmsHeaders;
        code = ParmsCode;
        MBytes = ParmsBytes;
        mResp = ParmsMResp;
    }

    public  httpRespnose(){
        this(null,null,null,null,null);
    }
    public Map<String, List<String>> getmHearderFileds() {
        return mHearderFileds;
    }

    public void setmHearderFileds(Map<String, List<String>> ParmsMHearderFileds) {
        mHearderFileds = ParmsMHearderFileds;
    }

    private Map<String , List<String>> mHearderFileds;
    private Headers MHeaders;

    public  String getCode() {
        return code;
    }

    public  void setCode(String ParmsCode) {
        code = ParmsCode;
    }

    private  String code;
    public byte[] getBytes() {
        return MBytes;
    }

    public void setBytes(byte[] ParmsBytes) {
        MBytes = ParmsBytes;
    }

    private byte[] MBytes;

    public Headers getHeaders() {
        return MHeaders;
    }

    public void setHeaders(Headers ParmsHeaders) {
        MHeaders = ParmsHeaders;
    }

    public String getmResp() {
        return mResp;
    }

    public void setmResp(String ParmsMResp) {
        mResp = ParmsMResp;
    }

    private String mResp;

}
interface mRespnose{
    void bodyString(String resp);
    void bodyBytes(byte[] respBytes);
    void bodyHeaders(Headers headers);
    void bodyHeaderFileds( Map<String, List<String>> headers);
}