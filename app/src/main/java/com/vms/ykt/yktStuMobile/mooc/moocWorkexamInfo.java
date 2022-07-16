package com.vms.ykt.yktStuMobile.mooc;
/**{"code":1,"pagination":{"totalCount":0,"pageSize":10,"pageIndex":1},"list":[]
 * ,"homeStartEndTime":{"EndTime":"2022/7/3 23:59:59","StartTime":"2022/2/28 0:00:00"},"isButton":true,
 * "isReturn":true,"currentTime":"2022-04-25 00:16:57","workExam":{"id":"t4wjat6ujbllkkl4vabr0q",
 * "title":"五种句型概述 （new）","replyCount":2,"stuWorkCount":0,"isLook":false,
 * "remark":"请在规定时间内完成","paperType":1}}**/
public class moocWorkexamInfo {
    private String replyCount;

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String ParmsReplyCount) {
        replyCount = ParmsReplyCount;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String ParmsEndTime) {
        EndTime = ParmsEndTime;
    }

    public String getStuWorkCount() {
        return stuWorkCount;
    }

    public void setStuWorkCount(String ParmsStuWorkCount) {
        stuWorkCount = ParmsStuWorkCount;
    }

    public String getIsLook() {
        return isLook;
    }

    public void setIsLook(String ParmsIsLook) {
        isLook = ParmsIsLook;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String ParmsStartTime) {
        StartTime = ParmsStartTime;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String ParmsCurrentTime) {
        currentTime = ParmsCurrentTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String ParmsTitle) {
        title = ParmsTitle;
    }

    private String EndTime;
    private String stuWorkCount;
    private String isLook;
    private String isReturn;
    private String StartTime;
    private String currentTime;
    private String title;

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String ParmsIsReturn) {
        isReturn = ParmsIsReturn;
    }

    public String getId() {
        return id;
    }

    public void setId(String ParmsId) {
        id = ParmsId;
    }

    public String getIsButton() {
        return isButton;
    }

    public void setIsButton(String ParmsIsButton) {
        isButton = ParmsIsButton;
    }

    private String id;
    private String isButton;
}
