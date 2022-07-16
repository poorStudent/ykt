package com.vms.ykt.yktStuWeb.Cqooc;

import java.io.Serializable;

/**
 * {"meta":{"total":"12","start":"1","size":"12"},"data":
 * [{"id":"336682102","ownerId":"1556134","name":"魏海旭","courseId":"334570416",
 * "course":{"id":"334570416","title":"PLC控制技术（第五次开课）","coursePicUrl":"\/image?id=211246","
 * editStatus":"1","isQuality":null,"courseManager":"黄才彬","courseType":"2","openType":"1",
 * "school":"重庆水利电力职业技术学院","userType":"2","status":"1","isBuild":"1","startDate":1652889600000,
 * "endDate":1656950400000,"cmbody":{"data":[{"id":"1274851","title":"黄才彬"}]},
 * "publishDate":"1653275780331","allSchool":"重庆水利电力职业技术学院","allSchoolId":"51",
 * "schema":"2","certId":null,"spocSchool":null,"spocSchoolTitle":null,"spocPhone":null,
 * "endingView":1,"scoreRate":0,"rateUnit":2},"title":"PLC控制技术（第五次开课）",
 * "created":1653293922724,"lastUpdated":1653294603951,"courseType":"2",
 * "username":"137352034060125","userInfo":{"username":"137352034060125","name":"魏海旭",
 * "gender":"男","speciality":null,"headimgurl":null,"email":"137352034060125@example.cqooc.com",
 * "schoolName":"重庆水利电力职业技术学院","signNum":24,"staytime":1266},"score":0,"status":"2",
 * "progress":"2","remarks":null,"body":null,"del":"2","pass":"0","openType":"1","learn":null,
 * "classId":"26629","classInfo":{"title":"工业机器人20级1班专本","teacher":"黄才彬","status":"1",
 * "cover":null,"oNum":0},"classTitle":"工业机器人20级1班专本","forumNum":0,"testScore":0,
 * "scoreBody":null,"score2":"0","chooseTime":1653293922739,"dropTime":null,"type":"1",
 * "tusername":null,"tInfo":null},
* **/
public class cqoocCourseInfo implements Serializable {
private String id;
private String signNum;

    public String getSignNum() {
        return signNum;
    }

    public void setSignNum(String signNum) {
        this.signNum = signNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getStaytime() {
        return staytime;
    }

    public void setStaytime(String staytime) {
        this.staytime = staytime;
    }

    private String username;
private String ownerId;
private String staytime;

    public String getId() {
        return id;
    }

    public void setId(String ParmsId) {
        id = ParmsId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String ParmsCourseId) {
        courseId = ParmsCourseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String ParmsTitle) {
        title = ParmsTitle;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String ParmsCourseType) {
        courseType = ParmsCourseType;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String ParmsScore) {
        score = ParmsScore;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String ParmsProgress) {
        progress = ParmsProgress;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String ParmsClassId) {
        classId = ParmsClassId;
    }

    public String getScoreBody() {
        return scoreBody;
    }

    public void setScoreBody(String ParmsScoreBody) {
        scoreBody = ParmsScoreBody;
    }

    public String getTestScore() {
        return testScore;
    }

    public void setTestScore(String ParmsTestScore) {
        testScore = ParmsTestScore;
    }

    public String getForumNum() {
        return forumNum;
    }

    public void setForumNum(String ParmsForumNum) {
        forumNum = ParmsForumNum;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String ParmsEndDate) {
        endDate = ParmsEndDate;
    }

    private String courseId;
private String title;
private String courseType;
private String score;

    public String getCourseManager() {
        return courseManager;
    }

    public void setCourseManager(String courseManager) {
        this.courseManager = courseManager;
    }

    private String courseManager;

    public String getClassTitle() {
        return classTitle;
    }

    public void setClassTitle(String classTitle) {
        this.classTitle = classTitle;
    }

    private String classTitle;
private String progress;
private String classId;
private String scoreBody;
private String testScore;
private String forumNum;
private String endDate;

    public String getCoursePicUrl() {
        return coursePicUrl;
    }

    public void setCoursePicUrl(String ParmsCoursePicUrl) {
        coursePicUrl = ParmsCoursePicUrl;
    }

    private String coursePicUrl;
}
