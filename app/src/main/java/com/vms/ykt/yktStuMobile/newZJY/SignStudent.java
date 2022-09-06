package com.vms.ykt.yktStuMobile.newZJY;
/*{"success":true,"data":{"pageSize":100,"curPage":1,"totalCount":1,"totalPage":1,"startIndex":0,
"items":[{"score":null,"loginId":"5201314ma","stuId":"xt1ravis7rj1qztmxv7ea",
"signStatus":"缺勤","alias":null,"id":"ff70218d2c2511ed8a531c34da7af6ec",
"stuName":"徐浩铭","photoLink":null,"signStatusCode":"7"}],"otherResult":{},
"searchItem":null,"orderItem":null},"code":"200","message":null}
 */
public class SignStudent {
    private String score;
    private String loginId;
    private String stuId;
    private String signStatus;
    private String id;
    private String stuName;
    private String signStatusCode;

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
