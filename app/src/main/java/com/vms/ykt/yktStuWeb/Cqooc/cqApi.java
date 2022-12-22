package com.vms.ykt.yktStuWeb.Cqooc;

import android.content.pm.InstrumentationInfo;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.lang.annotation.Repeatable;
import java.net.ResponseCache;
import java.util.HashMap;

public class cqApi  {
    

    static String UsreInfo2 = "http://www.cqooc.com/user/session";

    public static  String getUsreInfo2(String xsid) {
        String resp = "";
        String body = "xsid=" + xsid + "&ts=" + System.currentTimeMillis();
        String request = "http://www.cqooc.com/";
        resp = cqoocHttp.get(UsreInfo2, request, body)  ;
        return resp;
    }

    static String UsreInfo1 = "http://www.cqooc.com/account/session/api/profile/get";

    public static  String getUsreInfo1() {

        String resp = "";
        String request = "http://www.cqooc.com/";
        String body = "ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(UsreInfo1, request, body)  ;
        return resp;
    }

    static String getCourseInfo = "http://www.cqooc.com/json/mcs";

    public static  String getCourseInfoBaes(String ownerId, String courseType) {
        String resp = "";
        String request = "http://www.cqooc.com/";
        String body = "sortby=id&reverse=true&del=2" + "&courseType=" + courseType + "&ownerId=" + ownerId + "&limit=200" + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(getCourseInfo, request, body)  ;
        return resp;
    }

    public static  String getCourseInfo2(String ownerId) {
        return getCourseInfoBaes(ownerId, "2");
        //在线课
    }

    public static  String getCourseInfo1(String ownerId) {
        return getCourseInfoBaes(ownerId, "1");
        //公开课
    }

    public static  String getCourseInfo3(String ownerId) {
        return getCourseInfoBaes(ownerId, "3");
        //spoc课
    }

    public static  String getCourseInfo4(String ownerId) {
        return getCourseInfoBaes(ownerId, "4");
        //独立云班课
    }

    static String mcsCourseInfo = "http://www.cqooc.com/json/mcs";

    public static  String mcsCourseInfo(String ownerId, String courseId) {
        String resp = "";
        String request = "http://www.cqooc.com/";
        String body = "ownerId=" + ownerId + "&courseId=" + courseId + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(mcsCourseInfo, request, body)  ;
        return resp;
    }

    static String chapters = "http://www.cqooc.com/json/chapters";

    public static  String getModleChapters(String courseId, int limit) {
        //模块和章节消息
        String resp = "";
        String request = " http://www.cqooc.com/learn/mooc/progress?id=" + courseId;
        String body = "limit=" + limit + "&start=1&select=id,title,level,selfId,parentId,status,pubClass,isElective&isPublish=1" + "&courseId=" + courseId + "&sortby=selfId&reverse=false" + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(chapters, request, body)  ;
        return resp;
    }

    static String Chapterslessons = "http://www.cqooc.com/json/mooc/lessons";

    public static  String getLessonsByChap(String courseId, String cpid, int limit) {
        //根据 每个ChaptersID 获取课件
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        String body = "parentId=" + cpid + "&limit=" + limit + "&sortby=selfId&reverse=false&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(Chapterslessons, request, body)  ;
        return resp;
    }

    public static  String getAlllessons(String courseId, int limit, int start) {
        //直接 获取所有课件
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        String body = "limit=" + limit + "&start=" + start + "&sortby=selfId&reverse=false&courseId=" + courseId + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(Chapterslessons, request, body)  ;
        return resp;
    }

    public static  String getFlishLessons(String courseId, String username, int limit, int start) {
        //直接 获取所有已完成课件
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        String body = "limit=" + limit + "&start=" + start + "&courseId=" + courseId + "&select=sectionId&username=" + username + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(learnLogs, request, body)  ;
        return resp;
    }

    static String addScore = "http://www.cqooc.com/course/score/session/api/res/score/add";

    public static  String getAddScore(String courseId, String resID, String score) {
        String resp = "";
        String Referer = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        String body = "{\"resId\":\"" + resID + "\",\"score\":" + score + "," +
                "\"url\":\"" + Referer + "\"}";
        resp = cqoocHttp.post(addScore, body, Referer);
        return resp;
    }

    static String getRes = "http://www.cqooc.com/json/my/res";

    public static  String getRes(String courseId, String resID) {
        //获取资源详情
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        String body = "id=" + resID + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(getRes, request, body)  ;
        return resp;
    }

    static String addLearnLog = "http://www.cqooc.com/learnLog/api/add";

    public static  String getAddLearnLogU(String username, String ownerId, String parentId, String courseId, String sectionId, String chapterId) {
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        HashMap<String ,Object> postData = new HashMap();
        postData.put("username", username);
        postData.put("ownerId", ownerId);
        postData.put("parentId", parentId);
        postData.put("courseId", courseId);
        postData.put("action", "0");
        postData.put("sectionId", sectionId);
        postData.put("chapterId", chapterId);
        postData.put("category", "2");
        String body = JSONObject.toJSONString(postData);
        resp = cqoocHttp.post(addLearnLog, body, request);
        return resp;
    }

    public static  String getAddLearnLog(userInfo userInfo, cqoocCourseInfo cqoocCourseInfo, cellLessonsInfo CellLessonsInfo) {
        //设置为已学习 刷课用 官方限制30s一次
        String resp = getAddLearnLogU(userInfo.getUsername(), userInfo.getId(), cqoocCourseInfo.getId(), cqoocCourseInfo.getCourseId(), CellLessonsInfo.getId(), CellLessonsInfo.getParentId());
        return resp;
    }

    static String updateVideoTime = "http://www.cqooc.com/app/login/api/mooc/lesson/video/curTime/update";

    public static  String UpdateVideoTime(cellLessonsInfo CellLessonsInfo, String curTime) {
        //{{"id":848006,"curTime":"8"}
        //添加视频记录
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + CellLessonsInfo.getCourseId();
        HashMap postData = new HashMap();
        postData.put("id", CellLessonsInfo.getId());
        postData.put("curTime", curTime);
        String body = JSONObject.toJSONString(postData);
        resp = cqoocHttp.post(updateVideoTime, body, request);
        return resp;
    }

    static String curTimeInit = "http://www.cqooc.com/app/login/api/mooc/lesson/video/curTime/get";

    public static  String getCurTimeInit(String courseId, String Id) {
        //
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        String body = "id=" + Id + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(curTimeInit, request, body)  ;
        return resp;
    }

    static String forumAnsw = "http://www.cqooc.com/json/forum/posts";

    public static  String getForumAnsw(String courseId, String forumId) {
        //讨论区他人回复
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        String body = "forumId=" + forumId + "&limit=20&sortby=id&reverse=false&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(forumAnsw, request, body)  ;
        return resp;
    }

    static String addForum = "http://www.cqooc.com/forum/session/api/forum/post/update";

    public static  String getAddForumU(String parentId, String courseId, String toUsername, String ip, String content, String forumId) {
        //{"level":"1","parentId":51377345,"courseId":"334570416","category":"2",
        // "toUsername":"137352008110103","ip":"183.230.27.78","content":"<p><span style=\"color: rgb(51, 51, 51); font-family: &quot;PingFang SC&quot;, &quot;Lantinghei SC&quot;, &quot;Microsoft YaHei&quot;, arial, 宋体, sans-serif, tahoma; font-size: 13px; white-space: normal;\">　继电-接触器控制电路是由各种真正的硬件继电器、接触器组成的，硬件继电器、接触 器触点易磨损。而PLC梯形阁则由许多所谓的软继电器组成。这些软继电器实质上是存储 器中的某一位触发器，可以置“0”或置“1”，软继电器无磨损现象。</span></p>",
        // "forumId":51377345}
        //讨论区添加和回复
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        HashMap postData = new HashMap();
        postData.put("level", "1");
        postData.put("parentId", parentId);
        postData.put("courseId", courseId);
        postData.put("category", "2");
        postData.put("action", "0");
        postData.put("toUsername", toUsername);
        postData.put("ip", ip);
        postData.put("content", content);
        postData.put("forumId", forumId);

        String body = JSONObject.toJSONString(postData);
        resp = cqoocHttp.post(addForum, body, request);
        return resp;
    }

    public static  String getAddForum(cellLessonsInfo CellLessonsInfo, cqoocCourseInfo cqoocCourseInfo, String ip, String content) {

        String resp = getAddForumU(CellLessonsInfo.getForumId(), cqoocCourseInfo.getCourseId(), CellLessonsInfo.getOwner(), ip, content, CellLessonsInfo.getForumId());
        return resp;
    }

    static String forumLog = "http://www.cqooc.com/api/student/mooc/forum";

    public static  String getForumLog(String courseId, String username) {
        ////设置讨论回复log
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        HashMap postData = new HashMap();
        postData.put("username", username);
        postData.put("courseId", courseId);
        String body = JSONObject.toJSONString(postData);
        resp = cqoocHttp.post(forumLog, body, request);
        return resp;
    }

    static String studyTime = "http://www.cqooc.com/account/session/api/study/time";

    public static  String getStudyTime(String courseId) {
        //更新学习时长记录
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        HashMap postData = new HashMap();
        postData.put("courseId", courseId);
        String body = JSONObject.toJSONString(postData);
        resp = cqoocHttp.post(studyTime, body, request);
        return resp;
    }

    static String learnLogs = "http://www.cqooc.com/json/learnLogs";

    //设置log
    public static  String getLearnLogs1(String courseId, String username) {
        //设置log
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        String body = "limit=100&start=1&courseId=" + courseId + "&select=sectionId&username=" + username + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(learnLogs, request, body)  ;
        return resp;
    }

    public static  String getLearnLogs2(String courseId, String sectionId, String username) {
        //设置log
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        String body = "limit=0&sectionId=" + sectionId + "&username=" + username + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(learnLogs, request, body)  ;
        return resp;
    }

    public static  String getLearnLogs3(String courseId, String sectionId, String username) {
        //设置log
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        String body = "sectionId=" + sectionId + "&username=" + username + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(learnLogs, request, body)  ;
        return resp;
    }

    //考试相关
    static String exams = "http://www.cqooc.com/json/exams";

    public static  String getExams(String courseId, int limit, int start) {
        //考试
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        String body = "select=id,title&status=1&courseId=" + courseId + "&limit=" + limit + "&sortby=id&reverse=true&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(exams, request, body)  ;
        return resp;
    }

    private static String exam = "http://www.cqooc.com/json/exam";

    public static  String getExamsInfo(String courseId, String examId) {
        //考试
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/exam/do?pid=" + examId + "&id=" + courseId;
        String body = "id=" + examId + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(exam, request, body)  ;
        return resp;
    }

    private static String examGen="http://www.cqooc.com/exam/api/paper/gen";
    public static  String getExamGen(userInfo userInfo,String courseId, String examId){
        //生成试卷
        //courseId: "334570467"
        //examId: "7725"
        //name: "魏海旭"
        //ownerId: 1556134
        //username: "137352034060125"

        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/exam/do?pid=" + examId + "&id=" + courseId;
        String body ="{\"ownerId\":"+userInfo.getId()+",\"username\":\""+userInfo.getUsername()+"\"," +
                "\"name\":\""+userInfo.getName()+"\",\"examId\":\""+examId+"\",\"courseId\":\""+courseId+"\"}";
        resp = cqoocHttp.post(examGen,body,request);
        return resp;
    }


    private static String examPreview="http://www.cqooc.com/exam/get/api/paper/get";
    public static  String getExamPreview(String courseId, String examId){
        //题目和答案
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/exam/do?pid=" + examId + "&id=" + courseId;
        String body = "examId=" + examId + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(examPreview, request, body)  ;
        return resp;
    }

    //提交考试答案
    private static String examSubmit="http://www.cqooc.com/exam/do/api/submit";

    public static  String getExamSubmit(userInfo userInfo,String courseId, String examId,String id,Object answers){
        //{ownerId: 1556134, username: "137352034060125", name: "魏海旭", examId: "7725", id: "2516184",…}
        //answers: {q963904: "0", q963409: "0", q963411: "0", q963344: "2", q963599: "2", q963374: "0", q963818: "3",…}
        //courseId: "334570467"
        //examId: "7725"
        //id: "2516184"
        //name: "魏海旭"
        //ownerId: 1556134
        //username: "137352034060125"
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/exam/do?pid=" + examId + "&id=" + courseId;
        HashMap<Object,Object> bodys= new HashMap<>();
        bodys.put("courseId", courseId);
        bodys.put("ownerId", Integer.parseInt(userInfo.getId()));
        bodys.put("examId", examId);
        bodys.put("id", id);
        bodys.put("name", userInfo.getName());
        bodys.put("username", userInfo.getUsername());
        bodys.put("answers", answers);
        String body = JSONObject.toJSONString(bodys);
        resp = cqoocHttp.post(examSubmit,body,request);
        return resp;
    }

    //作业相关
    static String tasks = "http://www.cqooc.com/json/tasks";

    public static  String getTasks(String courseId, int limit, int start) {
        //作业
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        String body = "limit=" + limit + "&start=" + start + "&isPublish=1&courseId=" + courseId + "&sortby=id&reverse=true&select=id,title,unitId,submitEnd,pubClass,status&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(tasks, request, body)  ;
        return resp;
    }

    public static  String getTasksInfo(String courseId, String taskId) {
        //作业详情
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/task/do?tid=" + taskId + "&id=" + courseId;
        String body = "id=" + taskId + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(tasks, request, body)  ;
        return resp;
    }

    static String openTasks = "http://www.cqooc.com/json/task/result/search";

    public static  String getOpenTasks(String courseId, String taskId) {
        //作业已做详情
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/task/do?tid=" + taskId + "&id=" + courseId;
        String body = "taskId=" + taskId + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(openTasks, request, body)  ;
        return resp;
    }

    //作业提交答案
    private static String taskAdd = "http://www.cqooc.com/task/api/result/add";

    public static  String getTaskAdd(userInfo UserInfo, Object answers, cqoocCourseInfo varCourseInfo, examTask examTask) {
        //attachment: ""
        //classId: ""
        //content: "<p><span style=\"color: rgb(102, 102, 102); font-family: 宋体; font-size: 14px; white-space: normal;\">移动互联网的发展对网络舆情带来哪些影响</span></p>"
        //courseId: "334570518"
        //name: "魏海旭"
        //ownerId: 1556134
        //status: "2"
        //taskId: "59746"
        //username: "137352034060125"
        String resp = "";
        String Referer = "http://www.cqooc.com/learn/mooc/task/do?tid=" + examTask.getId() + "&id=" + varCourseInfo.getCourseId();
        HashMap<Object, Object> bodys = new HashMap<>();
        bodys.put("attachment", "");
        bodys.put("classId", varCourseInfo.getClassId());
        bodys.put("courseId", varCourseInfo.getCourseId());
        bodys.put("content", answers);
        bodys.put("name", UserInfo.getName());
        bodys.put("ownerId", Integer.parseInt(UserInfo.getId()));
        bodys.put("status", "2");
        bodys.put("taskId", examTask.getId());
        bodys.put("username", UserInfo.getUsername());
        String body = JSONObject.toJSONString(bodys);
        resp = cqoocHttp.post(taskAdd, body, Referer);
        return resp;
    }

    //private String updat="http://www.cqooc.com/account/session/api/study/log/updat";


    static String papers = "http://www.cqooc.com/json/exam/papers";

    public static  String getPapers(String courseId, int limit, int start) {
        //所有测验 TEST
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/progress?id=" + courseId;
        String body = "limit=" + limit + "&start=" + start + "&courseId=" + courseId + "&select=id,title&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(tasks, request, body)  ;
        return resp;
    }

    //测验详情
    static String paperInfo = "http://www.cqooc.com/test/api/paper/info";

    public static  String getPaperInfo(String courseId, String tid) {
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/structure?id=" + courseId;
        String body = "id=" + tid + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(paperInfo, request, body)  ;
        return resp;
    }

    static String openPaper = "http://www.cqooc.com/test/api/paper/get";

    public static  String getOpenPaperU(String courseId, String tid, String sid, String cid, String mid) {
        //打开测验
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/testing/do?tid=" + tid + "&id=" + courseId + "&sid=" + sid + "&cid=" + cid + "&mid=" + mid;
        String body = "id=" + tid + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(openPaper, request, body)  ;
        return resp;
    }

    public static  String getOpenPaper(cqoocCourseInfo varCqoocCourseInfo, cellLessonsInfo varCellLessonsInfo) {
        return getOpenPaperU(varCqoocCourseInfo.getCourseId(), varCellLessonsInfo.getTestId(), varCellLessonsInfo.getId(), varCellLessonsInfo.getParentId(), varCqoocCourseInfo.getId());
    }


    //获取已做测验详情
    static String testResult = "http://www.cqooc.com/json/test/result/search";

    public static  String getTestResultU(String courseId, String tid, String sid, String cid, String mid) {
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/testing/do?tid=" + tid + "&id=" + courseId + "&sid=" + sid + "&cid=" + cid + "&mid=" + mid;
        String body = "testID=" + tid + "&ts=" + System.currentTimeMillis();
        resp = cqoocHttp.get(testResult, request, body)  ;
        return resp;
    }

    public static  String getTestResult(cqoocCourseInfo varCourseInfo, cellLessonsInfo varCellLessonsInfo) {
        return getTestResultU(varCourseInfo.getCourseId(), varCellLessonsInfo.getTestId(), varCellLessonsInfo.getId(), varCellLessonsInfo.getParentId(), varCourseInfo.getId());
    }

    //提交测验
    static String testAdd = "http://www.cqooc.com/test/api/result/add";

    public static  String getTestAddU(userInfo UserInfo, Object answers, String classid, String courseId, String tid, String sid, String cid, String mid) {
        String resp = "";
        String request = "http://www.cqooc.com/learn/mooc/testing/do?tid=" + tid + "&id=" + courseId + "&sid=" + sid + "&cid=" + cid + "&mid=" + mid;
        HashMap<Object, Object> post = new HashMap<>();
        post.put("classId", classid);
        post.put("courseId", courseId);
        post.put("name", UserInfo.getName());
        post.put("paperId", tid);
        post.put("username", UserInfo.getUsername());
        post.put("ownerId", UserInfo.getId());
        String body = JSONObject.toJSONString(post).replaceFirst("\\{", ",");
        body = "{" + "\"answers\":{" + answers + "}" + body;
        System.out.println(body);
        resp = cqoocHttp.post(testAdd, body, request);
        return resp;
    }

    public static  String getTestAdd(userInfo UserInfo, Object answers, cqoocCourseInfo varCourseInfo, cellLessonsInfo varCellLessonsInfo) {
        return getTestAddU(UserInfo, answers, varCourseInfo.getClassId(), varCourseInfo.getCourseId(), varCellLessonsInfo.getTestId(), varCellLessonsInfo.getId(), varCellLessonsInfo.getParentId(), varCourseInfo.getId());
    }


}
