package com.vms.ykt.yktStuWeb.Cqooc;

import java.io.Serializable;

/**{"id":1556134,"username":"137352034060125","email":"137352034060125@example.cqooc.com","phone":"15736547091",
 * "group":["student"],"profile":{}}

 {"username":"137352034060125","name":"魏海旭","role":"3","department":"无","birthday":null,"gender":"男",
 "speciality":null,"year":"0","class":null,"nickName":null,"headimgurl":null,"education":null,"degree":null,
 "job":null,"post":null,"phone":null,"mobile":"15736547091","fax":null,"email":"137352034060125@example.cqooc.com",
 "address":null,"institute":"","schoolName":"重庆水利电力职业技术学院","aboutStudy":"","sort":"1601948082",
 "schoolId":"51","created":1601948082874,"lastUpdated":1653486086876,"isExpert":"2","subjectLevel":null,
 "subjectDisplay":null,"subject2Level":null,"subject2Display":null,"isInitial":"2","titles":"0","md5":null,
 "schoolCode":null,"contact":"","subject3":null,"owner":null,"isLocal":"1","ctype":null,"signNum":27,"staytime":1469,
 "lastlogin":1653481926224,"lastsync":1653486086889,"needPhone":"1","status":"1","stuNo":null,"qqOpenId":null,
 "qqAccessToken":null,"wxOpenId":null,"wxAccessToken":null}
 **/
public class userInfo implements Serializable {

    private String id;

    public String getStaytime() {
        return staytime;
    }

    public void setStaytime(String staytime) {
        this.staytime = staytime;
    }

    public String getSignNum() {
        return signNum;
    }

    public void setSignNum(String signNum) {
        this.signNum = signNum;
    }

    private String staytime;
    private String signNum;

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    private String phone;

    public String getCookie() {
        return cookie;
    }


    private String cookie;

    public String getId() {
        return id;
    }

    public void setId(String ParmsId) {
        id = ParmsId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String ParmsPhone) {
        phone = ParmsPhone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String ParmsUsername) {
        username = ParmsUsername;
    }

    public String getName() {
        return name;
    }

    public void setName(String ParmsName) {
        name = ParmsName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String ParmsEmail) {
        email = ParmsEmail;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String ParmsSchoolName) {
        schoolName = ParmsSchoolName;
    }

    private String username;
    private String name;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String ParmsSchoolId) {
        schoolId = ParmsSchoolId;
    }

    private String schoolId;
    private String email;
    private String schoolName;
}
