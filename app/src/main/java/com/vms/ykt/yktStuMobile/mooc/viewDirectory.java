package com.vms.ykt.yktStuMobile.mooc;

import com.alibaba.fastjson.JSONArray;

/**
 * {"code":1,"courseName":"实用英语语法","title":"1.1 五种基本句型概述 new",
 * "resUrl":"{\"extension\":\"mp4\",\"category\":\"video\",\"urls\":{\"status\":\"https://upload.icve.com.cn:9990/ssykt/g@D35F8BDE8684AAFF6386844F1B8F8264.mp4/status\",
 * \"preview\":\"https://file.icve.com.cn/ssykt/845/994/D35F8BDE8684AAFF6386844F1B8F8264.mp4\",
 * \"download\":\"https://file.icve.com.cn/ssykt/845/994/D35F8BDE8684AAFF6386844F1B8F8264.mp4?response-content-disposition=attachment;filename=D35F8BDE8684AAFF6386844F1B8F8264.mp4\",
 * \"preview_oss_ori\":\"https://file.icve.com.cn/ssykt/845/994/D35F8BDE8684AAFF6386844F1B8F8264.mp4\",
 * \"oss_ori_internal_url\":\"https://icve.oss-cn-hangzhou-internal.aliyuncs.com/ssykt/845/994/D35F8BDE8684AAFF6386844F1B8F8264.mp4\",
 * \"preview_oss_gen\":\"https://file.icve.com.cn/ssykt_gen/845/994/D35F8BDE8684AAFF6386844F1B8F8264.mp4\",
 * \"oss_gen_internal_url\":\"https://icve.oss-cn-hangzhou-internal.aliyuncs.com/ssykt_gen/845/994/D35F8BDE8684AAFF6386844F1B8F8264.mp4\",
 * \"owa_url\":\"\"},
 * \"isH5\":0,\"h5PreviewUrl\":\"\",\"args\":{\"360p\":false,\"480p\":false,\"720p\":true},\"status\":2}",
 * "resourceUrl":"ssykt/g@D35F8BDE8684AAFF6386844F1B8F8264.mp4","isDownLoad":false,
 * "downUrl":"","currentTime":0,"rarList":[],
 * "content":null,"isFinish":false,"isHasQuestion":false,"videoQuestionList":[]}
 * **/
public class viewDirectory {
    private String currentTime;

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String ParmsCurrentTime) {
        currentTime = ParmsCurrentTime;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String ParmsIsFinish) {
        isFinish = ParmsIsFinish;
    }

    public String getIsHasQuestion() {
        return isHasQuestion;
    }

    public void setIsHasQuestion(String ParmsIsHasQuestion) {
        isHasQuestion = ParmsIsHasQuestion;
    }

    public JSONArray getRarList() {
        return rarList;
    }

    public void setRarList(JSONArray ParmsRarList) {
        rarList = ParmsRarList;
    }

    public JSONArray getVideoQuestionList() {
        return videoQuestionList;
    }

    public void setVideoQuestionList(JSONArray ParmsVideoQuestionList) {
        videoQuestionList = ParmsVideoQuestionList;
    }

    private String isFinish;
    private String isHasQuestion;
    private JSONArray rarList;
    private JSONArray videoQuestionList;

    /*{
  "code": 1,
  "courseName": "第六次开课",
  "resUrl": "{\"extension\":\"doc\",\"category\":\"office\",\"urls\":{\"status\":\"https://upload.icve.com.cn:9990/doc/g@6AF2D2803BC0D2579D55448F9305CD9F.doc/status?time=637911778294041877&token=6099F0E232F09A7E5FD80EC1AE4FEB18\",\"preview\":\"https://file.icve.com.cn/file_doc/427/180/6AF2D2803BC0D2579D55448F9305CD9F.doc\",\"download\":\"https://file.icve.com.cn/file_doc/427/180/6AF2D2803BC0D2579D55448F9305CD9F.doc?response-content-disposition=attachment;filename=6AF2D2803BC0D2579D55448F9305CD9F.doc\",\"preview_oss_ori\":\"https://file.icve.com.cn/file_doc/427/180/6AF2D2803BC0D2579D55448F9305CD9F.doc\",\"oss_ori_internal_url\":\"https://icve.oss-cn-hangzhou-internal.aliyuncs.com/file_doc/427/180/6AF2D2803BC0D2579D55448F9305CD9F.doc\",\"preview_oss_gen\":\"https://file.icve.com.cn/file_gen_doc/427/180/6AF2D2803BC0D2579D55448F9305CD9F.doc\",\"oss_gen_internal_url\":\"https://icve.oss-cn-hangzhou-internal.aliyuncs.com/file_gen_doc/427/180/6AF2D2803BC0D2579D55448F9305CD9F.doc\",\"owa_url\":\"/op/view.aspx?src=https://icve.oss-cn-hangzhou-internal.aliyuncs.com/file_doc/427/180/6AF2D2803BC0D2579D55448F9305CD9F.doc\"}}",
  "downLoadUrl": "https://file.icve.com.cn/file_doc/427/180/6AF2D2803BC0D2579D55448F9305CD9F.doc?response-content-disposition=attachment;filename=1-学习指导.doc",
  "courseCell": {
    "Id": "p7nbabaue6dag9d6wekaq",
    "DateCreated": "/Date(1641555920000)/",
    "CourseOpenId": "j7fbabaurl5ocrj9ibrfxw",
    "TopicId": "ornbabauoazooydbhj3na",
    "ParentId": "ornbabaug5piobjrf6pnsa",
    "CellName": "1-学习指导",
    "CategoryName": "文档",
    "CellType": 1,
    "ResourceUrl": "doc/g@6AF2D2803BC0D2579D55448F9305CD9F.doc",
    "ExternalLinkUrl": "",
    "CellContent": "doc/g@6AF2D2803BC0D2579D55448F9305CD9F.doc",
    "RarJsonData": "",
    "ztWay": 0,
    "SpaceCount": 0,
    "IsAllowDownLoad": false,
    "KnowledgeIds": "",
    "KnowledgeTitle": "",
    "SortOrder": 1,
    "FromType": 2,
    "ImpProjectId": "",
    "ImpProjectName": "",
    "ImpDocId": "",
    "ImpDocTitle": "",
    "ResId": "",
    "NewSortOrder": 0,
    "FromId": "t34ayurarhj3r3wwwbjna",
    "VideoTimeLong": 0,
    "DocSize": 28672,
    "PageCount": 1,
    "DateModified": "/Date(-62135596800000)/",
    "VideoQuestionCount": 0,
    "PlayType": 0,
    "FromMOOCCellId": "cbgbafgtz6zme7vdeoxuq",
    "DocId": "",
    "GreenScan": "pass",
    "GreenScanScene": "",
    "GreenScanSceneDetail": "",
    "TableName": "MOOC_CourseProcessCell"
  },
  "isDownLoad": false,
  "rarList": [],
  "VideoPercent": 90,
  "currentTime": 0,
  "isFinish": false,
  "videoQuestionList": [],
  "userId": "2w7jafiswazbrev468vb5q",
  "userType": 1
}*/
}
