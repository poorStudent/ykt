package com.vms.ykt.yktStuMobile.zjy;

import java.io.Serializable;

/**{"Id":"zgi7axeuo6rkvjg7vz99qg","courseOpenId":"z8pjaeuulrbdnflicdpg",
 * "courseName":"西门子sinamics g120/s120变频技术与应用","className":"工业机器人20级1班(专本)",
 * "Title":"2022-04-14周四的课堂教学","openClassId":"mty7au2uq6hay0t4yghww",
 * "dateCreated":"2022-04-14 19:08",
 * "teachDate":"2022-04-14","classSection":"","Address":"","state":3},**/
public class zjyTeachInfo implements Serializable {
    private String dateCreated;
    private String courseOpenId;
    private String openClassId;
    private String state;
    private String Title;
    private String teachDate;
    private String Id;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    private String className;
    private String courseName;
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCourseOpenId() {
        return courseOpenId;
    }

    public void setCourseOpenId(String courseOpenId) {
        this.courseOpenId = courseOpenId;
    }

    public String getOpenClassId() {
        return openClassId;
    }

    public void setOpenClassId(String openClassId) {
        this.openClassId = openClassId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTeachDate() {
        return teachDate;
    }

    public void setTeachDate(String teachDate) {
        this.teachDate = teachDate;
    }



    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String ParmsCourseName) {
        courseName = ParmsCourseName;
    }


}
