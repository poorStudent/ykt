package com.vms.ykt.yktStuMobile.zjy;

import java.io.Serializable;

/**
 /**{"code":1,"msg":"请求成功！","isEvaluation":0,"faceEvaluation":0,"dataList":[
 * {"Id":"g33aiiuhpjj8knf9roqlg","Title":"2022-05-01 14:59的签到","DateCreated":"2022/5/1 14:59:30",
 * "CreatorId":"utbzaikuaovc89bj200x1a","DataType":"签到","State":2,"SignType":2,"Gesture":"01258",
 * "AskType":-1,"ViewAnswer":0,"resourceUrl":null,"cellType":0,"categoryName":null,"moduleId":null,
 * "cellSort":0,"hkOrExamType":0,"paperType":0,"termTimeId":null,"isForbid":0,"fixedPublishTime":null,
 * "examStuId":null,"examWays":0,"isAuthenticate":0,"isAnswerOrPreview":0,"isPreview":0,
 * "StuStartDate":null,"StuEndDate":null},
 {"isAnswerOrPreview":0,"isPreview":0,"DateCreated":"2022/5/30 17:06:55","AskType":-1,"isForbid":0,"examWays":0,
 "Gesture":"277859ud","DataType":"签到","SignType":3,"paperType":0,"CreatorId":"utbzaikuaovc89bj200x1a",
 "Title":"2022-05-30 17:06的签到","isAuthenticate":0,"ViewAnswer":0,"cellSort":0,"hkOrExamType":0,"cellType":0,
 "State":2,"Id":"mw0aaawuzojfbqnpgxjkyg"}
 {"isAnswerOrPreview":1,"isPreview":1,"DateCreated":"2022/5/30 17:06:38","AskType":-1,"isForbid":0,"examWays":1,
 "StuEndDate":"2022/5/3 23:21:00","Gesture":"","fixedPublishTime":"","DataType":"考试","SignType":0,"paperType":0,
 "CreatorId":"","Title":"uiu","isAuthenticate":1,"ViewAnswer":0,"cellSort":0,"hkOrExamType":1,
 "termTimeId":"cpmayiu36vie6w6sujka","cellType":0,"State":3,"StuStartDate":"2022/5/1 19:24:00",
 "Id":"qdezaiiuhljdmsuq8fy07w"}
 {"isAnswerOrPreview":2,"isPreview":1,"DateCreated":"2022/5/30 17:06:27","AskType":-1,"isForbid":0,"examWays":1,
 "StuEndDate":"2022/6/15 22:22:00","Gesture":"","fixedPublishTime":"","DataType":"作业","SignType":0,"paperType":2,
 "CreatorId":"","Title":"密密麻麻","isAuthenticate":0,"ViewAnswer":0,"cellSort":0,"hkOrExamType":1,
 "termTimeId":"zjjwazgu251k5sjiqo9w","cellType":0,"State":2,"StuStartDate":"2022/5/10 22:22:00",
 "Id":"80zgayquqyph76sxctzrg"}
 {"isAnswerOrPreview":0,"isPreview":0,"DateCreated":"2022/5/30 17:06:18","AskType":-1,"isForbid":0,"examWays":0,
 "Gesture":"","DataType":"测验","SignType":0,"paperType":0,"CreatorId":"utbzaikuaovc89bj200x1a","Title":"2022-05-30  17:06的测验",
 "isAuthenticate":0,"ViewAnswer":2,"cellSort":0,"hkOrExamType":0,"cellType":0,"State":2,"Id":"leezaawur51kwhanoggga"}
 *  **/
public class zjyCouresActivitInfo implements Serializable {
    private String Id;
    private String Title;
    private String CreatorId;
    private String DataType;
    private String State;
    private String Gesture;
    private String cellType;
    private String moduleId;
    private String termTimeId;
    private String examStuId;
    private String categoryName;

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    private String DateCreated;

    public String getId() {
        return Id;
    }

    public void setId(String ParmsId) {
        Id = ParmsId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String ParmsTitle) {
        Title = ParmsTitle;
    }

    public String getCreatorId() {
        return CreatorId;
    }

    public void setCreatorId(String ParmsCreatorId) {
        CreatorId = ParmsCreatorId;
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String ParmsDataType) {
        DataType = ParmsDataType;
    }

    public String getState() {
        return State;
    }

    public void setState(String ParmsState) {
        State = ParmsState;
    }

    public String getGesture() {
        return Gesture;
    }

    public void setGesture(String ParmsGesture) {
        Gesture = ParmsGesture;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String ParmsCellType) {
        cellType = ParmsCellType;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String ParmsModuleId) {
        moduleId = ParmsModuleId;
    }

    public String getTermTimeId() {
        return termTimeId;
    }

    public void setTermTimeId(String ParmsTermTimeId) {
        termTimeId = ParmsTermTimeId;
    }

    public String getExamStuId() {
        return examStuId;
    }

    public void setExamStuId(String ParmsExamStuId) {
        examStuId = ParmsExamStuId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String ParmsCategoryName) {
        categoryName = ParmsCategoryName;
    }

}
