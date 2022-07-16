package com.vms.ykt.yktStuWeb.icve;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;

import java.io.Serializable;

public class icveApiW implements Serializable {

    public icveHttpW getIcveHttpW() {
        return mIcveHttpW;
    }

    public void setIcveHttpW(icveHttpW icveHttpW) {
        mIcveHttpW = icveHttpW;
    }

    private icveHttpW mIcveHttpW;

    static String studingCourse = "https://www.icve.com.cn/studycenter/MyCourse/studingCourse";
    static String finishCourse = "https://www.icve.com.cn/studycenter/MyCourse/finishCourse";
    static String directoryList = "https://www.icve.com.cn/study/Directory/directoryList";
    static String getJcInfo = "https://www.icve.com.cn/common/common/getJcInfo";

    private void login() {
        /*
        验证码
        GET https://www.icve.com.cn/portal/VerifyCode/index?t=0.4888292704555841 HTTP/1.1
        登录
        POST https://www.icve.com.cn/portal/Register/Login_New HTTP/1.1
        userName=dmVub21tcw%3D%3D&pwd=UG9vcjI1Nzk5ODg2NTM%3D&verifycode=2564
         */
    }

    public String getMyCourseList(zjyUser zjyUser, String url) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("userId=" + zjyUser.getUserId());
        String postParams = postParam.toString();

        resp = mIcveHttpW.post(url, postParams, null);
        return resp;

    }

    public String getFinishCourse(zjyUser zjyUser) {
        //已完成课程

        return getMyCourseList(zjyUser, finishCourse);

    }

    public String getStudingCourse(zjyUser zjyUser) {
        //未完成课程
        return getMyCourseList(zjyUser, studingCourse);

    }

    static String studingSmallCourse = "https://www.icve.com.cn/studycenter/mySmallCourse/studingSmallCourse";

    public String getStudingSmallCourse() {
        //未完成课程

        return mIcveHttpW.post(studingSmallCourse, "", null);

    }

    static String finishSmallCourse = "https://www.icve.com.cn/studycenter/mySmallCourse/finishSmallCourse";

    public String getFinishSmallCCourse() {
        //完成课程
        return mIcveHttpW.post(finishSmallCourse, "", null);

    }

    public String getdirectoryList(icveCourseInfo CourseInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + CourseInfo.getId());
        String postParams = postParam.toString();
        resp = mIcveHttpW.get(directoryList, postParams);
        return resp;

    }

    private String directory = "https://www.icve.com.cn/study/directory/directory";

    public String getDirectory(String courseId, String chapterId, String sort) {
//courseId: ew-nab-oi7vfzf4ga-vx4w
//chapterId: yxknab-obyllrs3r1i6lgg
//sort: 0.002
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId + "&");
        postParam.append("chapterId=" + chapterId + "&");
        postParam.append("sort=" + sort);

        String postParams = postParam.toString();
        resp = mIcveHttpW.post(directory, postParams, null);
        return resp;
    }

    public String getJcInfo(zjyUser zjyUser) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("userId=" + zjyUser.getUserId());
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(getJcInfo, postParams, null);
        return resp;

    }

    static String getView = "https://www.icve.com.cn/study/directory/view";

    public String getView(String courseId, String cellId) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId + "&");
        postParam.append("cellId=" + cellId + "&");
        postParam.append("enterType=study");
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(getView, postParams, null);
        return resp;

    }

    static String updateStatus = "https://www.icve.com.cn/study/directory/updateStatus";

    public String getUpdateStatus(String cellId) {
        //视频修改进度
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + cellId + "&");
        postParam.append("learntime=99999999&status=1");
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(updateStatus, postParams, null);
        return resp;

    }

    static String reply = "https://www.icve.com.cn/study/bbs/reply";

    public String getReply(String courseId, String topicId) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId + "&");
        postParam.append("topicId=" + topicId);

        String postParams = postParam.toString();
        resp = mIcveHttpW.post(reply, postParams, null);
        return resp;

    }

    public String getReplyContext(String courseId, String topicId) {
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

    public String addReply(String courseId, String topicId, String content) {
        //添加讨论
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId + "&");
        postParam.append("topicId=" + topicId + "&");
        postParam.append("content=" + content + "&");
        postParam.append("parentId=");

        String postParams = postParam.toString();
        resp = mIcveHttpW.post(addReply, postParams, null);
        return resp;

    }

    static String answerpaper = "https://www.icve.com.cn/study/directory/answerpaper";

    public String answerpaper(String works, String paperItemId, String answer) {
        //提交每道题的答案
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("works=" + works + "&");
        postParam.append("paperItemId=" + paperItemId + "&");
        postParam.append("answer=" + answer);

        String postParams = postParam.toString();
        resp = mIcveHttpW.post(answerpaper, postParams, null);
        return resp;

    }

    static String subPaper = "https://www.icve.com.cn/study/directory/subPaper";

    public String subPaper(String studentWorksId) {
        //提交测验
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("studentWorksId=" + studentWorksId);

        String postParams = postParam.toString();
        resp = mIcveHttpW.post(subPaper, postParams, null);
        return resp;

    }


    static String CourseInfo = "https://www.icve.com.cn/study/Directory/getCourseInfo";

    public String getCourseInfo(String courseId) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId);
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(CourseInfo, postParams, null);
        return resp;

    }

    static String worksInfo = "https://www.icve.com.cn/study/works/index";

    public String getWorkseInfo(String courseId) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId);
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(worksInfo, postParams, null);
        return resp;

    }

    private String WorkPerview = "https://www.icve.com.cn/study/Works/works";

    public String getWorkPerview(String courseId, String assignmentId) {
        //assignmentId: ptkcazokqjzbd2xvpyxwmg
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId + "&");
        postParam.append("assignmentId=" + assignmentId);
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(WorkPerview, postParams, null);
        return resp;

    }

    private String answerOnlineWorks = "https://www.icve.com.cn/study/Works/answerOnlineWorks";

    public String getAnswerOnlineWorks(String answerId, String data) {
        String resp;
        StringBuilder postParam = new StringBuilder();

        postParam.append("answerId=" + answerId + "&");
        postParam.append("data=" + data);
        //data: [{"paperItemId":"zslyammu2ozioo9vcaqjig","answer":"2"}]
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(answerOnlineWorks, postParams, null);
        return resp;
    }



    private String Exam = "https://www.icve.com.cn/study/Exam/index";

    public String getExam(String courseId) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId);
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(Exam, postParams, null);
        return resp;

    }

    private String ExamPerview = "https://www.icve.com.cn/study/Exam/exam";

    public String getExamPerview(String courseId, String examId) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId + "&");
        postParam.append("examId=" + examId);
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(ExamPerview, postParams, null);
        return resp;

    }

    private String answerExam="https://www.icve.com.cn/study/Exam/answerExam";


    public String getAnswerExam(String answerId, String data) {
        String resp;
        StringBuilder postParam = new StringBuilder();

        postParam.append("answerId=" + answerId + "&");
        postParam.append("data=" + data);
        //data: [{"paperItemId":"zslyammu2ozioo9vcaqjig","answer":"2"}]
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(answerExam, postParams, null);
        return resp;
    }

    //微课

    private String microHeadInfo = "https://www.icve.com.cn/Portal/microstudy/getHeadInfo";

    public String getMicroHeadInfo(String courseId) {
        //获取课件
        String resp = "";
        //courseId: kieiahuk7k1cpfsyrscxga
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId);
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(microHeadInfo, postParams, null);
        return resp;
    }

    private String microView = "https://www.icve.com.cn/portal/microstudy/view";

    public String getMicroView(String cellId) {
        //刷课
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + cellId);
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(microView, postParams, null);
        return resp;
    }

    private String microReply = "https://www.icve.com.cn/portal/microstudy/reply";

    public String getMicroReply(String cellId) {
        //获取讨论
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + cellId);
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(microReply, postParams, null);
        return resp;
    }

    private String microsAddComment = "https://www.icve.com.cn/Portal/microstudy/addComment";

    public String getMicrosAddComment(String courseId) {
        //微课评价
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + courseId + "&");
        postParam.append("content=5");
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(microsAddComment, postParams, null);
        return resp;
    }

    private String microUpdateStatus = "https://www.icve.com.cn/portal/microstudy/updateStatus";

    public String getMicroUpdateStatus(String cellId) {
        //视频修改进度
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + cellId + "&");
        postParam.append("learntime=99999999&status=1");
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(microUpdateStatus, postParams, null);
        return resp;

    }

    private String microAddReply = "https://www.icve.com.cn/portal/microstudy/addReply";

    public String getMicroAddReply(String topicId, String content) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("topicId=" + topicId + "&");
        postParam.append("content=<p>" + content + "</p>&");
        postParam.append("courseId=" + "" + "");

        String postParams = postParam.toString();
        resp = mIcveHttpW.post(microAddReply, postParams, null);
        return resp;
    }

    private String microSubPaper = "https://www.icve.com.cn/portal/microstudy/subPaper";

    public String getMicroSubPaper(String studentWorksId, String data) {
        //提交卷子
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("studentWorksId=" + studentWorksId + "&");
        postParam.append("data=" + data);
        //data: [{"paperItemId":"zslyammu2ozioo9vcaqjig","answer":"2"}]
        String postParams = postParam.toString();
        resp = mIcveHttpW.post(microSubPaper, postParams, null);
        return resp;

    }
}
