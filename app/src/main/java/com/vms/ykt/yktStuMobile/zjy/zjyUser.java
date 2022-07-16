package com.vms.ykt.yktStuMobile.zjy;

import java.io.Serializable;

public class zjyUser implements Serializable {

    private String cookie;
    private String token;
    private String userId;
    private String userName;
    private String teachId;
    private String newToken;
    private String schoolName;
    private String displayName;
    private String appv;
    private zjyUser otherZjyUser;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public zjyUser getOtherZjyUser() {
        return otherZjyUser;
    }

    public void setOtherZjyUser(zjyUser otherZjyUser) {
        this.otherZjyUser = otherZjyUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getAppv() {
        return appv;
    }

    public void setAppv(String appv) {
        this.appv = appv;
    }

    public String getTeachId() {
        return teachId;
    }

    public void setTeachId(String ParmsTeachId) {
        teachId = ParmsTeachId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setNewToken(String newToken) {
        this.newToken = newToken;
    }

    public String getNewToken() {
        return newToken;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolName() {
        return schoolName;
    }
}
