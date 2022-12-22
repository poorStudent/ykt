package com.vms.ykt.yktStuWeb.icve;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;

import java.io.Serializable;

public  class icveApiW {



    static String studingCourse = "https://www.icve.com.cn/studycenter/MyCourse/studingCourse";
    static String finishCourse = "https://www.icve.com.cn/studycenter/MyCourse/finishCourse";
    static String directoryList = "https://www.icve.com.cn/study/Directory/directoryList";
    static String getJcInfo = "https://www.icve.com.cn/common/common/getJcInfo";

    private static  void login() {
        /*
        验证码
        GET https://www.icve.com.cn/portal/VerifyCode/index?t=0.4888292704555841 HTTP/1.1
        登录
        POST https://www.icve.com.cn/portal/Register/Login_New HTTP/1.1
        userName=dmVub21tcw%3D%3D&pwd=UG9vcjI1Nzk5ODg2NTM%3D&verifycode=2564
         */
    }

    public static  String getMyCourseList(zjyUser zjyUser, String url) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("userId=" + zjyUser.getUserId());
        String postParams = postParam.toString();

        resp = icveHttpW.post(url, postParams);
        return resp;

    }

    public static  String getFinishCourse(zjyUser zjyUser) {
        //已完成课程

        return getMyCourseList(zjyUser, finishCourse);

    }

    public static  String getStudingCourse(zjyUser zjyUser) {
        //未完成课程
        return getMyCourseList(zjyUser, studingCourse);

    }

    static String studingSmallCourse = "https://www.icve.com.cn/studycenter/mySmallCourse/studingSmallCourse";

    public static  String getStudingSmallCourse() {
        //未完成课程

        return icveHttpW.post(studingSmallCourse, "");

    }

    static String finishSmallCourse = "https://www.icve.com.cn/studycenter/mySmallCourse/finishSmallCourse";

    public static  String getFinishSmallCCourse() {
        //完成课程
        return icveHttpW.post(finishSmallCourse, "");

    }

    public static  String getdirectoryList(icveCourseInfo CourseInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + CourseInfo.getId());
        String postParams = postParam.toString();
        resp = icveHttpW.get(directoryList, postParams);
        return resp;

    }

    private static  String directory = "https://www.icve.com.cn/study/directory/directory";

    public static  String getDirectory(String courseId, String chapterId, String sort) {
//courseId: ew-nab-oi7vfzf4ga-vx4w
//chapterId: yxknab-obyllrs3r1i6lgg
//sort: 0.002
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId + "&");
        postParam.append("chapterId=" + chapterId + "&");
        postParam.append("sort=" + sort);

        String postParams = postParam.toString();
        resp = icveHttpW.post(directory, postParams);
        return resp;
    }

    public static  String getJcInfo(zjyUser zjyUser) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("userId=" + zjyUser.getUserId());
        String postParams = postParam.toString();
        resp = icveHttpW.post(getJcInfo, postParams);
        return resp;

    }

    static String getView = "https://www.icve.com.cn/study/directory/view";

    public static  String getView(String courseId, String cellId) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId + "&");
        postParam.append("cellId=" + cellId + "&");
        postParam.append("enterType=study");
        String postParams = postParam.toString();
        resp = icveHttpW.post(getView, postParams);
        return resp;

    }

    static String updateStatus = "https://www.icve.com.cn/study/directory/updateStatus";

    public static  String getUpdateStatus(String cellId) {
        //视频修改进度
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + cellId + "&");
        postParam.append("learntime=99999999&status=1");
        String postParams = postParam.toString();
        resp = icveHttpW.post(updateStatus, postParams);
        return resp;

    }

    static String reply = "https://www.icve.com.cn/study/bbs/reply";

    public static  String getReply(String courseId, String topicId) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId + "&");
        postParam.append("topicId=" + topicId);

        String postParams = postParam.toString();
        resp = icveHttpW.post(reply, postParams);
        return resp;

    }

    public static  String getReplyContext(String courseId, String topicId) {
        String resp = "";
        String cont = "";
        resp = getReply(courseId, topicId);
        if (resp == null) return cont;
        JSONObject varJSONObject = JSON.parseObject(resp);
        if (!varJSONObject.getString("code").equals("1")) return cont;
        JSONArray varArray = varJSONObject.getJSONArray("replylist");
        if (varArray.isEmpty()) return cont;
        varArray.getJSONObject(varArray.size() - 1).getString("Content");
        return cont;
    }

    static String addReply = "https://www.icve.com.cn/study/bbs/addReply";

    public static  String addReply(String courseId, String topicId, String content) {
        //添加讨论
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId + "&");
        postParam.append("topicId=" + topicId + "&");
        postParam.append("content=" + content + "&");
        postParam.append("parentId=");

        String postParams = postParam.toString();
        resp = icveHttpW.post(addReply, postParams);
        return resp;

    }

    static String answerpaper = "https://www.icve.com.cn/study/directory/answerpaper";

    public static  String answerpaper(String works, String paperItemId, String answer) {
        //提交每道题的答案
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("works=" + works + "&");
        postParam.append("paperItemId=" + paperItemId + "&");
        postParam.append("answer=" + answer);

        String postParams = postParam.toString();
        resp = icveHttpW.post(answerpaper, postParams);
        return resp;

    }

    static String subPaper = "https://www.icve.com.cn/study/directory/subPaper";

    public static  String subPaper(String studentWorksId) {
        //提交测验
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("studentWorksId=" + studentWorksId);

        String postParams = postParam.toString();
        resp = icveHttpW.post(subPaper, postParams);
        return resp;

    }


    static String CourseInfo = "https://www.icve.com.cn/study/Directory/getCourseInfo";

    public static  String getCourseInfo(String courseId) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId);
        String postParams = postParam.toString();
        resp = icveHttpW.post(CourseInfo, postParams);
        return resp;

    }

    static String worksInfo = "https://www.icve.com.cn/study/works/index";

    public static  String getWorkseInfo(String courseId) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId);
        String postParams = postParam.toString();
        resp = icveHttpW.post(worksInfo, postParams);
        return resp;

    }

    private static  String WorkPerview = "https://www.icve.com.cn/study/Works/works";

    public static  String getWorkPerview(String courseId, String assignmentId) {
        //assignmentId: ptkcazokqjzbd2xvpyxwmg
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId + "&");
        postParam.append("assignmentId=" + assignmentId);
        String postParams = postParam.toString();
        resp = icveHttpW.post(WorkPerview, postParams);
        return resp;

    }

    private static  String answerOnlineWorks = "https://www.icve.com.cn/study/Works/answerOnlineWorks";

    public static  String getAnswerOnlineWorks(String answerId, String data) {
        String resp;
        StringBuilder postParam = new StringBuilder();

        postParam.append("answerId=" + answerId + "&");
        postParam.append("data=" + data);
        //data: [{"paperItemId":"zslyammu2ozioo9vcaqjig","answer":"2"}]
        String postParams = postParam.toString();
        resp = icveHttpW.post(answerOnlineWorks, postParams);
        return resp;
    }



    private static  String Exam = "https://www.icve.com.cn/study/Exam/index";

    public static  String getExam(String courseId) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId);
        String postParams = postParam.toString();
        resp = icveHttpW.post(Exam, postParams);
        return resp;

    }

    private static  String ExamPerview = "https://www.icve.com.cn/study/Exam/exam";

    public static  String getExamPerview(String courseId, String examId) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId + "&");
        postParam.append("examId=" + examId);
        String postParams = postParam.toString();
        resp = icveHttpW.post(ExamPerview, postParams);
        return resp;

    }

    private static  String answerExam="https://www.icve.com.cn/study/Exam/answerExam";


    public static  String getAnswerExam(String answerId, String data) {
        String resp;
        StringBuilder postParam = new StringBuilder();

        postParam.append("answerId=" + answerId + "&");
        postParam.append("data=" + data);
        //data: [{"paperItemId":"zslyammu2ozioo9vcaqjig","answer":"2"}]
        String postParams = postParam.toString();
        resp = icveHttpW.post(answerExam, postParams);
        return resp;
    }

    //微课

    private static  String microHeadInfo = "https://www.icve.com.cn/Portal/microstudy/getHeadInfo";

    public static  String getMicroHeadInfo(String courseId) {
        //获取课件
        String resp = "";
        //courseId: kieiahuk7k1cpfsyrscxga
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId);
        String postParams = postParam.toString();
        resp = icveHttpW.post(microHeadInfo, postParams);
        return resp;
    }

    private static  String microView = "https://www.icve.com.cn/portal/microstudy/view";

    public static  String getMicroView(String cellId) {
        //刷课
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + cellId);
        String postParams = postParam.toString();
        resp = icveHttpW.post(microView, postParams);
        return resp;
    }

    private static  String microReply = "https://www.icve.com.cn/portal/microstudy/reply";

    public static  String getMicroReply(String cellId) {
        //获取讨论
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + cellId);
        String postParams = postParam.toString();
        resp = icveHttpW.post(microReply, postParams);
        return resp;
    }

    private static  String microsAddComment = "https://www.icve.com.cn/Portal/microstudy/addComment";

    public static  String getMicrosAddComment(String courseId) {
        //微课评价
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId + "&");
        postParam.append("content=5");
        String postParams = postParam.toString();
        resp = icveHttpW.post(microsAddComment, postParams);
        return resp;
    }

    private static  String microUpdateStatus = "https://www.icve.com.cn/portal/microstudy/updateStatus";

    public static  String getMicroUpdateStatus(String cellId) {
        //视频修改进度
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + cellId + "&");
        postParam.append("learntime=99999999&status=1");
        String postParams = postParam.toString();
        resp = icveHttpW.post(microUpdateStatus, postParams);
        return resp;

    }

    private static  String microAddReply = "https://www.icve.com.cn/portal/microstudy/addReply";

    public static  String getMicroAddReply(String topicId, String content) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("topicId=" + topicId + "&");
        postParam.append("content=<p>" + content + "</p>&");
        postParam.append("courseId=" + "" + "");

        String postParams = postParam.toString();
        resp = icveHttpW.post(microAddReply, postParams);
        return resp;
    }

    private static  String microSubPaper = "https://www.icve.com.cn/portal/microstudy/subPaper";

    public static  String getMicroSubPaper(String studentWorksId, String data) {
        //提交卷子
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("studentWorksId=" + studentWorksId + "&");
        postParam.append("data=" + data);
        //data: [{"paperItemId":"zslyammu2ozioo9vcaqjig","answer":"2"}]
        String postParams = postParam.toString();
        resp = icveHttpW.post(microSubPaper, postParams);
        return resp;

    }
}
