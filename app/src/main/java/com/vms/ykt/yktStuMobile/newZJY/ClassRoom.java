package com.vms.ykt.yktStuMobile.newZJY;
/*{"success":true,"data":{"data":{"records":[
{"courseName":"789","activityNum":2,"className":"456","id":"402883e682ff3ab10183023f608908d4",
"title":"2022-09-03周六的课堂教学","courseId":"39e272199dab487ba6f8f76115cbfd2c","startDate":1662134400000},
{"courseName":"789","activityNum":2,"className":"456","id":"402883e482f4e0a00182f72db4bb4efc","title":"2022-09-01周四的课堂教学","courseId":"39e272199dab487ba6f8f76115cbfd2c","startDate":1661961600000},{"courseName":"789","activityNum":2,"className":"456","id":"402883e482f4e0a00182f745b8185008","title":"2022-09-01周四的课堂教学2","courseId":"39e272199dab487ba6f8f76115cbfd2c","startDate":1661961600000}],"total":0,"size":10,"current":1,"orders":[],"hitCount":false,"searchCount":true,"pages":0}},"code":"200","message":"查询成功"}
 */
public class ClassRoom {
    private String courseName;
    private String className;
    private String id;
    private String title;
    private String courseId;
    private String startDate;
    private String activityNum;

    public String getActivityNum() {
        return activityNum;
    }

    public void setActivityNum(String activityNum) {
        this.activityNum = activityNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
