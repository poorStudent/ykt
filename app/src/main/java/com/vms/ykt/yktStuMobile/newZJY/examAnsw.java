package com.vms.ykt.yktStuMobile.newZJY;

import java.util.Map;

public class examAnsw {
    public String getAnswStr() {
        return answStr;
    }

    public void setAnswStr(String answStr) {
        this.answStr = answStr;
    }

    public String getAnswHtml() {
        return answHtml;
    }

    public void setAnswHtml(String answHtml) {
        this.answHtml = answHtml;
    }

    public Map<String, String> getAnswMap() {
        return answMap;
    }

    public void setAnswMap(Map<String, String> answMap) {
        this.answMap = answMap;
    }

    String answStr;
    String answHtml;
    Map<String,String> answMap;
}
