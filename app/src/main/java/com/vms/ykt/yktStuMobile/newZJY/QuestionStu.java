package com.vms.ykt.yktStuMobile.newZJY;
/*{
  "success": true,
  "data": {
    "pageSize": 10,
    "curPage": 1,
    "totalCount": 1,
    "totalPage": 1,
    "startIndex": 0,
    "items": [
      {
        "score": "3",
        "loginId": "venomms",
        "stuId": "2w7jafiswazbrev468vb5q",
        "signStatus": "",
        "alias": null,
        "id": "ccb408d92c1911ed8a531c34da7af6ec",
        "stuName": "魏海旭",
        "photoLink": "",
        "signStatusCode": "",
        "resourceNum": "0",
        "content": "0"
      }
    ],
    "otherResult": {},
    "searchItem": null,
    "orderItem": null
  },
  "code": "200",
  "message": "查询被提问学生"
}*/
public class QuestionStu {
    private String loginId;
    private String stuId;
    private String signStatus;
    private String stuName;
    private String id; //recordIds
    private String signStatusCode;

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

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignStatusCode() {
        return signStatusCode;
    }

    public void setSignStatusCode(String signStatusCode) {
        this.signStatusCode = signStatusCode;
    }
}
