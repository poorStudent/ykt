package com.vms.ykt.yktStuMobile.newZJY;
/*{"success":true,"data":{"pageSize":100,"curPage":1,"totalCount":1,"totalPage":1,"startIndex":0,
"items":[{"score":null,"loginId":"5201314ma","stuId":"xt1ravis7rj1qztmxv7ea",
"signStatus":"缺勤","alias":null,"id":"ff70218d2c2511ed8a531c34da7af6ec",
"stuName":"徐浩铭","photoLink":null,"signStatusCode":"7"}],"otherResult":{},
"searchItem":null,"orderItem":null},"code":"200","message":null}
 */
// {
//        "score": "3",
//        "loginId": "venomms",
//        "stuId": "2w7jafiswazbrev468vb5q",
//        "signStatus": "",
//        "alias": null,
//        "id": "ccb408d92c1911ed8a531c34da7af6ec",
//        "stuName": "魏海旭",
//        "photoLink": "",
//        "signStatusCode": "",
//        "resourceNum": "0",
//        "content": "0"
//      }
//{"success":true,"data":
// [{"id":"1578989773544435714","title":"小组1","sequence":1,
// "fkActivityId":"3e8c6d59479811ed8cb21c34da7acf94","starRating":0,"status":0,
// "siteCode":"zhzj","rowCreateDate":1665295472000,"rowUpdateDate":1665295472000,
// "groupMembers":[{"loginId":"5201314ma","photo":"https://user.icve.com.cn/incoming/photo/zhzj6c09c8d080c84e85938db8c8e249ccb3.jpeg",
// "userName":"徐浩铭","ssoUserId":"xt1ravis7rj1qztmxv7ea"}],"groupMemberCount":null},
// {"id":"1578989773548630017","title":"小组2","sequence":2,"fkActivityId":"3e8c6d59479811ed8cb21c34da7acf94","starRating":0,"status":0,"siteCode":"zhzj","rowCreateDate":1665295472000,"rowUpdateDate":1665295472000,"groupMembers":[{"loginId":"venomms","photo":null,"userName":"魏海旭","ssoUserId":"2w7jafiswazbrev468vb5q"}],"groupMemberCount":null}],"code":"200","message":"查询成功"}

public class SignAndQuestionStu {
    private String score;
    private String loginId;
    private String stuId;
    private String signStatus;
    private String id;
    private String stuName;
    private String signStatusCode;
    private String resourceNum;
    private String content;
    private String title;
    private String status;
    private String starRating;
    private String groupMembers;

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers) {
        this.groupMembers = groupMembers;
    }

    public String getResourceNum() {
        return resourceNum;
    }

    public void setResourceNum(String resourceNum) {
        this.resourceNum = resourceNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getSignStatusCode() {
        return signStatusCode;
    }

    public void setSignStatusCode(String signStatusCode) {
        this.signStatusCode = signStatusCode;
    }
}
