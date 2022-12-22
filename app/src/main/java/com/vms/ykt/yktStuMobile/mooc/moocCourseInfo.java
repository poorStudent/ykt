package com.vms.ykt.yktStuMobile.mooc;

import java.io.Serializable;

/**{"code":1,"list":[
 * {"id":"xeprai6szrrngohybh5goq","cid":"SYYNB512002","courseName":"实用英语语法",
 * "thumbnail":"https://file.icve.com.cn/ssykt/467/543/74F87DE4B0813E48A9B4A9E6DFD80AB3.jpg",
 * "courseOpenId":"gisjat6ujjljmrruz461sg","courseOpenName":"第四次开课","process":0,"studentCount":"16207",
 * "isFinished":"0","stuId":"2w7jafiswazbrev468vb5q","schoolName":"宁波职业技术学院"},
**/

public class moocCourseInfo  {
    private String id;
    private String cid;

    public String getId() {
        return id;
    }

    public String getCid() {
        return cid;
    }

    public String getCourseOpenId() {
        return courseOpenId;
    }

    public String getCourseOpenName() {
        return courseOpenName;
    }

    public String getProcess() {
        return process;
    }

    public String getStudentCount() {
        return studentCount;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getStuId() {
        return stuId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getIsFinished() {
        return isFinished;
    }

    private String courseOpenId;
    private String courseOpenName;
    private String process;

    public String getCourseName() {
        return courseName;
    }

    public void setId(String ParmsId) {
        id = ParmsId;
    }

    public void setCid(String ParmsCid) {
        cid = ParmsCid;
    }

    public void setCourseOpenId(String ParmsCourseOpenId) {
        courseOpenId = ParmsCourseOpenId;
    }

    public void setCourseOpenName(String ParmsCourseOpenName) {
        courseOpenName = ParmsCourseOpenName;
    }

    public void setProcess(String ParmsProcess) {
        process = ParmsProcess;
    }

    public void setCourseName(String ParmsCourseName) {
        courseName = ParmsCourseName;
    }

    public void setStudentCount(String ParmsStudentCount) {
        studentCount = ParmsStudentCount;
    }

    public void setSchoolName(String ParmsSchoolName) {
        schoolName = ParmsSchoolName;
    }

    public void setStuId(String ParmsStuId) {
        stuId = ParmsStuId;
    }

    public void setThumbnail(String ParmsThumbnail) {
        thumbnail = ParmsThumbnail;
    }

    public void setIsFinished(String ParmsIsFinished) {
        isFinished = ParmsIsFinished;
    }

    private String courseName;
    private String studentCount;
    private String schoolName;
    private String stuId;
    private String thumbnail;
    private String isFinished;

//DateCreated: "2022-01-07"
//Id: "eqfakwrwy1azazvcxqtuq"
//classify: 0
//courseName: "电路基础"
//courseOpenId: "j7fbabaurl5ocrj9ibrfxw"
//courseOpenName: "第六次开课"
//courseType: 0
//coverUrl: "ssykt/g@366EDA2C00F4A54ED2E01CC3023F859A.jpg"
//process: 0
//stuCount: "19395"
//stuId: "2w7jafiswazbrev468vb5q"
//thumbnail: "https://file.icve.com.cn/ssykt/217/950/366EDA2C00F4A54ED2E01CC3023F859A.jpg"
    private String DateCreated;
    private String classify;
}
