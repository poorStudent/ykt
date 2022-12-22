package com.vms.ykt.yktStuMobile.zjy;

import java.io.Serializable;

/**{"code":1,"msg":"请求成功！",
 * "homeworkList":[
 * {"homeworkId":"axzjax6uyzlbhwb4prww","homeworkTermTimeId":"erbuax6utzfccd46exnig",
 * "title":"P129  1,2,3,4,7","homeworkType":4,"ztWay":3,"remark":"","startDate":"2022-04-21 00:00",
 * "endDate":"2022-04-25 19:00","readStuCount":0,"unReadStuCount":0,"unSubmitCount":0,"replyCount":1,
 * "stuAnwerHomeworkCount":0,"stuHomeworkState":"未做","isTakeHomework":true,"isSetTime":1,"isForbid":0,
 * "IsGrouped":0,"paperType":2,"isShowStuEva":0},
 * {"homeworkId":"xthjawauk6niks2qhqsmdg","homeworkTermTimeId":"92nkawau965cih7sofow7w","title":"汇编指令","homeworkType":4,"ztWay":3,"remark":"","startDate":"2022-03-28 00:00","endDate":"2022-03-31 19:00","readStuCount":0,"unReadStuCount":0,"unSubmitCount":0,"replyCount":1,"stuAnwerHomeworkCount":1,"stuHomeworkState":"80.00","isTakeHomework":true,"isSetTime":1,"isForbid":0,"IsGrouped":0,"paperType":2,"isShowStuEva":0},{"homeworkId":"budhavguxrplnpnz6bntaw","homeworkTermTimeId":"m3hhavguakfbfvxkbh26vw","title":"40页13题","homeworkType":4,"ztWay":3,"remark":"","startDate":"2022-03-07 19:52","endDate":"2022-03-07 20:54","readStuCount":0,"unReadStuCount":0,"unSubmitCount":0,"replyCount":1,"stuAnwerHomeworkCount":1,"stuHomeworkState":"95.00","isTakeHomework":true,"isSetTime":1,"isForbid":0,"IsGrouped":0,"paperType":2,"isShowStuEva":0}],
 * "hkPageSize":3}**/
public class HomeworkInfo  {
    private String homeworkId;
    private String homeworkTermTimeId;
    private String title;
    private String remark;
    private String replyCount;
    private String homeworkType;
    private String ztWay;
    private String endDate;
    private String paperType;
    private String stuHomeworkState;
    private String Id;
    private String termId;
    private String stuAnwerHomeworkCount;
    private String dateCreated;
    private String stuEndDate;
    private String startDate;
    private String stuStartDate;


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getHomeworkTermTimeId() {
        return homeworkTermTimeId;
    }

    public void setHomeworkTermTimeId(String homeworkTermTimeId) {
        this.homeworkTermTimeId = homeworkTermTimeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public String getHomeworkType() {
        return homeworkType;
    }

    public void setHomeworkType(String homeworkType) {
        this.homeworkType = homeworkType;
    }

    public String getZtWay() {
        return ztWay;
    }

    public void setZtWay(String ztWay) {
        this.ztWay = ztWay;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public String getStuHomeworkState() {
        return stuHomeworkState;
    }

    public void setStuHomeworkState(String stuHomeworkState) {
        this.stuHomeworkState = stuHomeworkState;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getStuAnwerHomeworkCount() {
        return stuAnwerHomeworkCount;
    }

    public void setStuAnwerHomeworkCount(String stuAnwerHomeworkCount) {
        this.stuAnwerHomeworkCount = stuAnwerHomeworkCount;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStuEndDate() {
        return stuEndDate;
    }

    public void setStuEndDate(String stuEndDate) {
        this.stuEndDate = stuEndDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStuStartDate() {
        return stuStartDate;
    }

    public void setStuStartDate(String stuStartDate) {
        this.stuStartDate = stuStartDate;
    }

    public String getStuHomeworkId() {
        return stuHomeworkId;
    }

    public void setStuHomeworkId(String stuHomeworkId) {
        this.stuHomeworkId = stuHomeworkId;
    }

    private String stuHomeworkId;

    public String getId() {
        if (Id==null){
            return homeworkId;
        }
        return Id;
    }

    public String getHomeworkId() {
        if (Id==null){
            return homeworkId;
        }
        return Id;
    }
/**{
 "code": 1,
 "courseOpenId": "b8j0aikutoxccbrq7hcw",
 "openClassId": "uv70aikuirxmsdqcjvv69a",
 "list": [
 {
 "type": 2,
 "Id": "wvraikunrdcptirj6uudq",
 "dateCreated": "/Date(1650900547000)/",
 "homeworkTermTimeId": "rnibaykuayvhn51tu5rcg",
 "paperType": 2,
 "Title": "11",
 "Remark": "11",
 "termId": "qvezaocnhapfyoq9i9ztua",
 "stuStartDate": "/Date(1650844800000)/",
 "stuEndDate": "/Date(1650931140000)/",
 "dataState": 3,
 "isLateSubmit": 0,
 "homeworkType": 4,
 "ztWay": 3,
 "stuHomeworkCount": 0,
 "replyCount": 3,
 "limitTime": 0,
 "stuHomeworkId": "",
 "getScore": 0,
 "State": 0,
 "IsDisplayAnswer": 1
 }],
 "openClassType": 1,
 "pagination": {
 "totalCount": 4,
 "pageSize": 10,
 "pageIndex": 1
 }
 }**/
private String getScore;

}
