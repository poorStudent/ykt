package com.vms.ykt.yktStuMobile.newZJY;

/*
{"success":true,"data":{"pageSize":20,"curPage":1,"totalCount":3,"totalPage":1,"startIndex":0,
"items":[
{"questionContent":null,"flagClassroomType":"41828108a8f711eca8977c10c99ef73b","detailTypeCode":"2",
"fkItemId":null,"examName":null,"typeName":"签到","classroomId":"402883e682ff3ab10183023f608908d4",
"typeCode":"1","recordId":"dcbf9d172b6711ed8a531c34da7af6ec","resourceUrl":null,
"classroomName":"2022-09-03周六的课堂教学","name":"签到","recordSignCode":"1","id":"dcb8d89d2b6711edafa20c42a12c121a",
"activityType":"92f11034fe3e11ebabc2fa346ba4cb00","courseId":"39e272199dab487ba6f8f76115cbfd2c",
"startDate":1662196060000,"status":"2","resourceType":null},
{"questionContent":null,"flagClassroomType":"41828108a8f711eca8977c10c99ef73b","detailTypeCode":"2","fkItemId":null,"examName":null,"typeName":"签到","classroomId":"402883e682ff3ab10183023f608908d4","typeCode":"1","recordId":"47338c4e2b8011ed8a531c34da7af6ec","resourceUrl":null,"classroomName":"2022-09-03周六的课堂教学","name":"签到","recordSignCode":"7","id":"472ae66b2b8011ed9bed0c42a18d88e0","activityType":"92f11034fe3e11ebabc2fa346ba4cb00","courseId":"39e272199dab487ba6f8f76115cbfd2c","startDate":1662206546000,"status":"1","resourceType":null},{"questionContent":null,"flagClassroomType":"41828108a8f711eca8977c10c99ef73b","detailTypeCode":"1","fkItemId":null,"examName":null,"typeName":"签到","classroomId":"402883e682ff3ab10183023f608908d4","typeCode":"1","recordId":"242e6eb62ba211ed8a531c34da7af6ec","resourceUrl":null,"classroomName":"2022-09-03周六的课堂教学","name":"签到","recordSignCode":"7","id":"242624d12ba211ed9fb00c42a131c456","activityType":"92f11034fe3e11ebabc2fa346ba4cb00","courseId":"39e272199dab487ba6f8f76115cbfd2c","startDate":1662221090000,"status":"1","resourceType":null}],"otherResult":{},"searchItem":null,"orderItem":null},"code":"200","message":null}
Class transformation time: 0.040779317s for 1606 classes or 2.539185367372354E-5s per class

 */
public class classActivity {
private String id;
private String activityType;
private String status;
private String courseId;
private String startDate;
private String questionContent;
private String flagClassroomType;
private String detailTypeCode;
private String fkItemId;
private String examName;
private String typeName;
private String classroomId;
private String typeCode;
private String recordId;
private String recordSignCode;
private String resourceUrl;
private String resourceType;
private String classroomName;

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFkItemId() {
        return fkItemId;
    }

    public void setFkItemId(String fkItemId) {
        this.fkItemId = fkItemId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordSignCode() {
        return recordSignCode;
    }

    public void setRecordSignCode(String recordSignCode) {
        this.recordSignCode = recordSignCode;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getFlagClassroomType() {
        return flagClassroomType;
    }

    public void setFlagClassroomType(String flagClassroomType) {
        this.flagClassroomType = flagClassroomType;
    }

    public String getDetailTypeCode() {
        return detailTypeCode;
    }

    public void setDetailTypeCode(String detailTypeCode) {
        this.detailTypeCode = detailTypeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
