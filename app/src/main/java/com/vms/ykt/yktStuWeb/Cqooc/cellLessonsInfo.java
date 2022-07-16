package com.vms.ykt.yktStuWeb.Cqooc;
/**{"meta":{"total":"11","start":"1","size":"11"},"data":[
 * {"id":"848004","parentId":"325711",
 * "chapter":{"id":"325711","title":"项目一 三相异步电动机点动\/连续运行控制","status":"1"},
 * "courseId":"334570416","category":"1","title":"1.1.1初识PLC","resId":"219393",
 * "resource":{"id":"219393","title":"1.1.1初识PLC","authorName":"黄才彬","resSort":"1",
 * "resMediaType":"doc","resSize":"6929 KB","viewer":"1","oid":"219393",
 * "username":"137352008110103","status":"converted","resMediaType_lk_display":"Word文档",
 * "pages":"15","newSourceID":"40288120-6daead38-016d-be683186-02b7","duration":null,
 * "dimension":null,"resourceType_lk_display":"教学课件","isVideo":"2",
 * "newSourceDIR":"2019\/10\/12"},"testId":null,"test":"Not a number:","forumId":null,
 * "forum":{"id":"","title":"介绍xin","status":"2",
 * "content":"<p style=\"margin-top: 0px; margin-bottom: 0px; caret-color: rgb(102, 102, 102); color: rgb(102, 102, 102); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑, 宋体, Helvetica, &quot;STHeiti STXihei&quot;, &quot;Microsoft JhengHei&quot;, Arial; font-size: 13px; white-space: normal;\"><span style=\"font-family: 宋体; font-size: 19px;\">1.&nbsp;<\/span><span style=\"font-family: 宋体; font-size: 19px;\">请分别用introduce和introduction两个单词造句。<\/span><\/p><p style=\"margin-top: 0px; margin-bottom: 0px; caret-color: rgb(102, 102, 102); color: rgb(102, 102, 102); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑, 宋体, Helvetica, &quot;STHeiti STXihei&quot;, &quot;Microsoft JhengHei&quot;, Arial; font-size: 13px; white-space: normal;\"><span style=\"font-family: 宋体; font-size: 19px;\">2.&nbsp;<\/span><span style=\"font-family: 宋体; font-size: 19px;\">请问介绍信正文开头怎么写？<\/span><\/p><p style=\"margin-top: 0px; margin-bottom: 0px; caret-color: rgb(102, 102, 102); color: rgb(102, 102, 102); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑, 宋体, Helvetica, &quot;STHeiti STXihei&quot;, &quot;Microsoft JhengHei&quot;, Arial; font-size: 13px; white-space: normal;\"><span style=\"font-family: 宋体; font-size: 19px;\">3.&nbsp;<\/span><span style=\"font-family: 宋体; font-size: 19px;\">介绍信的正文内容包括哪些关键要素？<\/span><\/p><p style=\"margin-top: 0px; margin-bottom: 0px; caret-color: rgb(102, 102, 102); color: rgb(102, 102, 102); font-family: &quot;Microsoft YaHei&quot;, 微软雅黑, 宋体, Helvetica, &quot;STHeiti STXihei&quot;, &quot;Microsoft JhengHei&quot;, Arial; font-size: 13px; white-space: normal;\"><span style=\"font-family: 宋体; font-size: 19px;\">4.&nbsp;<\/span><span style=\"font-family: 宋体; font-size: 19px;\">介绍信结尾需要向读信人表示感谢吗？为什么？<\/span><\/p><p><br\/><\/p>"},
 * "ownerId":"654171","created":1652838894828,"lastUpdated":1652838894828,"owner":"137352008110103","chapterId":"325710","selfId":"1","isLeader":"1","time":0},
 * **/
public class cellLessonsInfo {
private cellForumInfo MCellForumInfo;
private cellResourceInfo MCellResourceInfo;
private String id;
private String test;
private String forumId;
private String testId;
private String resId;
private String category;
private String parentId;//cid
private String courseId;
private String ownerId;
private String owner;
private String title;
private String selfId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String ParmsTitle) {
        title = ParmsTitle;
    }

    public cellForumInfo getCellForumInfo() {
        return MCellForumInfo;
    }

    public void setCellForumInfo(cellForumInfo ParmsCellForumInfo) {
        MCellForumInfo = ParmsCellForumInfo;
    }

    public cellResourceInfo getCellResourceInfo() {
        return MCellResourceInfo;
    }

    public void setCellResourceInfo(cellResourceInfo ParmsCellResourceInfo) {
        MCellResourceInfo = ParmsCellResourceInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String ParmsId) {
        id = ParmsId;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String ParmsTest) {
        test = ParmsTest;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String ParmsForumId) {
        forumId = ParmsForumId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String ParmsTestId) {
        testId = ParmsTestId;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String ParmsResId) {
        resId = ParmsResId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String ParmsCategory) {
        category = ParmsCategory;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String ParmsParentId) {
        parentId = ParmsParentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String ParmsCourseId) {
        courseId = ParmsCourseId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ParmsOwnerId) {
        ownerId = ParmsOwnerId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String ParmsOwner) {
        owner = ParmsOwner;
    }

    public String getSelfId() {
        return selfId;
    }

    public void setSelfId(String ParmsSelfId) {
        selfId = ParmsSelfId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String ParmsChapterId) {
        chapterId = ParmsChapterId;
    }

    private String chapterId;//mid

}
