package com.vms.ykt.yktStuWeb.mooc;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuMobile.mooc.courseInfoForTeach;
import com.vms.ykt.yktStuMobile.mooc.moocCellInfo;
import com.vms.ykt.yktStuMobile.mooc.moocCourseInfo;
import com.vms.ykt.yktStuMobile.mooc.moocHttp;
import com.vms.ykt.yktStuMobile.mooc.moocModInfo;
import com.vms.ykt.yktStuMobile.mooc.moocTopicInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public  class moocApiW {


    static String getCourseOpenList = "https://moocapp.icve.com.cn/portal/course/getCourseOpenList";
    static String getMyCourse = "https://moocapp.icve.com.cn/portal/Course/getMyCourse";
    static String getUserInf = "https://moocapp.icve.com.cn/portal/LoginMooc/getUserInf";
    static String getMyInfo = "https://mooc.icve.com.cn/portal/info/getMyInfo";

    static String addStuView = "https://moocapp.icve.com.cn/study/discussion/addStuViewTopicRemember";

    public static  void login() {
        /*
        验证码
        GET https://mooc.icve.com.cn/portal/LoginMooc/getVerifyCode?ts=1655544067388 HTTP/1.1
        登录
        POST https://mooc.icve.com.cn/portal/LoginMooc/loginSystem HTTP/1.1
        userName=venomms&password=Poor2579988653&verifycode=2539
        */
    }

    public static  String getUserInf() {
        String resp = "";
        resp = moocHttpW.get(getUserInf);
        return resp;
    }

    public static  String getMyInfo() {
        String resp = "";
        resp = moocHttpW.get(getMyInfo);
        return resp;
    }

    public static  String getCourseOpenList() {
        String resp = "";
        String ref = "https://moocapp.icve.com.cn/profile.html";
        resp = moocHttpW.get(getCourseOpenList, ref);
        return resp;
    }

    public static  String getMyCourse(String isFinished, String page) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("isFinished=" + isFinished + "&");
        postParam.append("page=" + page + "&");
        postParam.append("pageSize=15");
        String postParams = postParam.toString();
        resp = moocHttpW.get(getMyCourse, postParams);
        return resp;
    }

    public static  String getaddStuView(String courseOpenId, String topicId) {
        //courseOpenId=6sg2ag6tfzzgek2os3uaog&topicId=saqasiukrffm1azjxjvag
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseOpenId=" + courseOpenId + "&");
        postParam.append("topicId=" + topicId);
        String postParams = postParam.toString();
        resp = moocHttpW.get(addStuView, postParams);
        return resp;
    }

    //获取所有模块
    private static String getProcessList = "https://mooc.icve.com.cn/study/learn/getProcessList";

    public static  String getProcessList(moocCourseInfo moocCourseInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseOpenId=" + moocCourseInfo.getCourseOpenId());
        String postParams = postParam.toString();
        resp = moocHttpW.post(getProcessList, postParams);
        return resp;
    }

    //获取所有章节
    private static String getTopicByModuleId = "https://mooc.icve.com.cn/study/learn/getTopicByModuleId";

    public static  String getTopicByModuleId(moocCourseInfo moocCourseInfo, moocModInfo modInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("moduleId=" + modInfo.getId() + "&");
        postParam.append("courseOpenId=" + moocCourseInfo.getCourseOpenId());
        String postParams = postParam.toString();
        resp = moocHttpW.post(getTopicByModuleId, postParams  );
        return resp;
    }

    //获取所有课件
    private static String getCellByTopicId = "https://mooc.icve.com.cn/study/learn/getCellByTopicId";

    public static  String getCellByTopicId(moocCourseInfo moocCourseInfo, moocTopicInfo topicInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("topicId=" + topicInfo.getId() + "&");
        postParam.append("courseOpenId=" + moocCourseInfo.getCourseOpenId());
        String postParams = postParam.toString();
        resp = moocHttpW.post(getCellByTopicId, postParams );
        return resp;
    }

    //获取课件详情
    private static String viewDirectory = "https://mooc.icve.com.cn/study/learn/viewDirectory";

    public static  String viewDirectory(moocCourseInfo moocCourseInfo, moocCellInfo moocCellInfo) {

        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + moocCellInfo.getId() + "&");
        postParam.append("moduleId=" + moocCellInfo.getModId() + "&");
        postParam.append("fromType=stu&");
        postParam.append("courseOpenId=" + moocCourseInfo.getCourseOpenId());
        String postParams = postParam.toString();
        resp = moocHttpW.post(viewDirectory, postParams );
        return resp;
    }

    //刷课件
    private static String StuProcessCell = "https://mooc.icve.com.cn/study/learn/statStuProcessCellLogAndTimeLong";

    public static  String StuProcessCell(moocCourseInfo moocCourseInfo, moocCellInfo moocCellInfo, String videoLong) {

        //courseId:
        //courseOpenId: j7fbabaurl5ocrj9ibrfxw
        //moduleId: ornbabautkljslvrbfp6ia
        //cellId: p7nbabaue6dag9d6wekaq
        //videoTimeTotalLong: 0
        //sourceForm: 1030

        if (videoLong.contains("null")) {
            videoLong = "";
        } else {
            videoLong = Tool.getRandomInt(888, 999) + videoLong;
        }
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseId=" + moocCellInfo.getId() + "&");
        postParam.append("cellId=" + moocCellInfo.getId() + "&");
        postParam.append("moduleId=" + moocCellInfo.getModId() + "&");
        postParam.append("videoTimeTotalLong=" + videoLong + "&");
        postParam.append("auvideoLength=" + videoLong + "&");
        //postParam.append("sourceForm=1030&");
        postParam.append("courseOpenId=" + moocCourseInfo.getCourseOpenId());
        String postParams = postParam.toString();
        resp = moocHttpW.post(StuProcessCell, postParams );
        return resp;
    }


    //获取评论区详情
    private static String getCellCommentData = "https://mooc.icve.com.cn/study/learn/getCellCommentData";

    public static  String getCellCommentData(moocCourseInfo moocCourseInfo, moocCellInfo moocCellInfo, String dType) {

        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + moocCellInfo.getId() + "&");
        postParam.append("dType=" + dType + "&");
        postParam.append("courseOpenId=" + moocCourseInfo.getCourseOpenId());
        String postParams = postParam.toString();
        resp = moocHttpW.post(getCellCommentData, postParams );
        return resp;
    }


    //课件评论
    private static String saveAllReply = "https://mooc.icve.com.cn/study/learn/saveAllReply";

    public static  String saveAllReply(moocCourseInfo moocCourseInfo, moocCellInfo moocCellInfo, String SignType, String CategoryId, String Content) {

        //replyData: {"ResId":"","replyToUserId":"","replyToDisplayName":"","Content":"<p>哦哦哦呕噢噢</p>",
        // "CourseOpenId":"j7fbabaurl5ocrj9ibrfxw","CategoryId":"bbszhtlq-j7fbabaurl5ocrj9ibrfxw",
        // "cellId":"p7nbabaue6dag9d6wekaq","star":0,"SignType":4}
        //urlList: []
        //isOpen: 1

        String resp = "";
        HashMap<Object, Object> postParam = new HashMap<>();
        postParam.put("ResId", moocCellInfo.getResId());
        postParam.put("replyToUserId", "");
        postParam.put("replyToDisplayName", "");
        postParam.put("Content", Content);
        postParam.put("courseOpenId", moocCourseInfo.getCourseOpenId());
        postParam.put("CategoryId", CategoryId);
        postParam.put("cellId", moocCellInfo.getId());
        postParam.put("star", 5);
        postParam.put("SignType", SignType);
        String json = JSONObject.toJSONString(postParam);
        StringBuilder postParams = new StringBuilder();
        postParams.append("replyData=" + json + "&");
        postParams.append("urlList=[]");
        String postParamss = postParams.toString();
        resp = moocHttpW.post(saveAllReply, postParamss );
        return resp;
    }


    //讨论区所有板块
    private static String getCourseCategory = "https://mooc.icve.com.cn/study/discussion/getCourseCategory";

    public static  String getCourseCategory(moocCourseInfo moocCourseInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("courseOpenId=" + moocCourseInfo.getCourseOpenId());
        String postParams = postParam.toString();
        resp = moocHttpW.post(getCourseCategory, postParams );
        return resp;
    }

    //获取 测验 作业 考试
    static String WorkExamList = "https://mooc.icve.com.cn/study/workExam/getWorkExamList";

    public static  String getWorkExamList(String courseOpenId, String page, String Type) {
        //workExamType=0&courseOpenId=gisjat6ujjljmrruz461sg
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("workExamType=" + Type + "&");
        postParam.append("courseOpenId=" + courseOpenId + "&");
        postParam.append("page=" + page);
        String postParams = postParam.toString();
        resp = moocHttpW.get(WorkExamList, postParams);
        return resp;
    }


    //记录 测验 作业 考试 每道题答案
    private static String onlineHomeworkAnswer = "https://mooc.icve.com.cn/study/workExam/onlineHomeworkAnswer";

    private String getOnlineHomeworkAnswer(String uniqueId, String questionId, String workExamType, String answer) {
        //studentWorkId:
        //questionId: ipb6akwr8rlfhgyqvi2ktg
        //workExamType: 1
        //online: 1
        //answer: 0
        //paperStuQuestionId:
        //questionType: 1
        //userId:
        //uniqueId: siijacqu0bdow8uf7jlnza
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        //postParam.append("studentWorkId=" + studentWorkId + "&");
        postParam.append("questionId=" + questionId + "&");
        postParam.append("workExamType=" + workExamType + "&");
        postParam.append("online=" + 1 + "&");
        postParam.append("answer=" + answer + "&");
        //postParam.append("paperStuQuestionId=" + paperStuQuestionId + "&");
        //postParam.append("userId=" + userId + "&");
        postParam.append("uniqueId=" + uniqueId);
        String postParams = postParam.toString();
        resp = moocHttpW.post(onlineHomeworkAnswer, postParams );
        return resp;
    }

    //提交 测验 作业 考试
    private static String workExamSave = "https://mooc.icve.com.cn/study/workExam/workExamSave";

    private String getWorkExamSave(String courseOpenId, String uniqueId, String useTime, String workExamType) {
        //uniqueId: siijacqu0bdow8uf7jlnza
        //workExamId: e7nbabaujrbfg4gasxnpg
        //workExamType: 1
        //courseOpenId: j7fbabaurl5ocrj9ibrfxw
        //paperStructUnique:
        //useTime: 405
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("uniqueId=" + uniqueId + "&");
        postParam.append("workExamType=" + workExamType + "&");
        postParam.append("courseOpenId=" + courseOpenId + "&");
        // postParam.append("paperStructUnique=" + paperStructUnique + "&");
        postParam.append("useTime=" + useTime);
        String postParams = postParam.toString();
        resp = moocHttpW.post(workExamSave, postParams);
        return resp;
    }

    //获取老师id
    static String recommendCourseToMe = "https://moocapp.icve.com.cn/portal/Course/recommendCourseToMe";

    private static String getCourseToMe(int page) {

        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("page=" + page + "&pageSize=15");
        String postParams = postParam.toString();
        resp = moocHttpW.get(recommendCourseToMe, postParams);
        return resp;
    }

    public static  List<courseInfoForTeach> getCourseForTeach() {
        List<courseInfoForTeach> varForTeachList = new ArrayList<>();
        int page = 1;
        while (true) {
            String resp = getCourseToMe(page);
            page++;
            if (resp == null || !resp.contains("list")) break;
            JSONObject json = JSONObject.parseObject(resp);
            if (!json.getString("code").equals("1")) break;
            JSONArray varArray = json.getJSONArray("list");
            if (varArray.isEmpty()) break;
            List<courseInfoForTeach> varForTeachLists = JSONArray.parseArray(varArray.toString(), courseInfoForTeach.class);
            varForTeachList.addAll(varForTeachLists);
            if (page == 3) break;
        }///
        return varForTeachList;
    }

        /*

        //作业

        homework_getStudyHomeworkList: _config.prefix + 'homeWork/getStudyHomeworkList',
        homework_detail: _config.prefix + 'homeWork/detail',
        homework_getHomeworkData: _config.prefix + 'homeWork/getHomeworkData',
        homework_moocStudyPreview: _config.prefix + 'homeWork/moocStudyPreview',
        homework_onlineHomeworkAnswer: _config.prefix + 'homeWork/onlineHomeworkAnswer',
        homework_onlineHomeworkCheckSpace: _config.prefix + 'homeWork/onlineHomeworkCheckSpace',
        homework_onlineHomeworkMatch: _config.prefix + 'homeWork/onlineHomeworkMatch',
        homework_onlineHomeworkSubAnswer: _config.prefix + 'homeWork/onlineHomeworkSubAnswer',
        homework_onlineHomeworkSave: _config.prefix + 'homeWork/onlineHomeworkSave',
        homework_uploadStudyFile: _config.prefix + 'workExam/uploadStudyFile',
        homework_delFileAnswer: _config.prefix + 'homeWork/delFileAnswer',
        homework_history: _config.prefix + 'homeWork/history',
        homework_keep: _config.prefix + 'homeWork/keep',
        homework_submitDraftHomework: _config.prefix + 'homeWork/submitDraftHomework',
        homework_previewDocHomework: _config.prefix + 'homeWork/previewDocHomework',
        homework_getStudyUpLoadView: _config.prefix + 'homeWork/getStudyUpLoadView',
        homework_uploadDocHomework: _config.prefix + 'homeWork/uploadDocHomework',
        homework_saveDocHomework: _config.prefix + 'homeWork/saveDocHomework',
        homework_docHomeworkHistory: _config.prefix + 'homeWork/docHomeworkHistory',
        homework_getFileHwData: _config.prefix + 'homeWork/getFileHwData',
        homework_getHomeworkMarkList: _config.prefix + 'homeWork/getHomeworkMarkList',
        homework_getClassList: _config.prefix + 'homeWork/getClassList',
        homework_saveWarnMessage: _config.prefix + 'homeWork/saveWarnMessage',
        homework_exportHomeworkScoreData: _config.prefix + 'homeWork/exportHomeworkScoreData',
        homework_onlineHomeworkSaveDraft: _config.prefix + 'homeWork/onlineHomeworkSaveDraft',

*/

        /*
    //测验
    testwork_getStudyHomeworkList: _config.prefix + 'testWork/getStudyHomeworkList',
    testwork_detail: _config.prefix + 'testWork/detail',
    testwork_getHomeworkData: _config.prefix + 'testWork/getHomeworkData',
    testwork_moocStudyPreview: _config.prefix + 'testWork/moocStudyPreview',
    testwork_onlineHomeworkAnswer: _config.prefix + 'testWork/onlineHomeworkAnswer',
    testwork_onlineHomeworkCheckSpace: _config.prefix + 'testWork/onlineHomeworkCheckSpace',
    testwork_onlineHomeworkMatch: _config.prefix + 'testWork/onlineHomeworkMatch',
    testwork_onlineHomeworkSubAnswer: _config.prefix + 'testWork/onlineHomeworkSubAnswer',
    testwork_onlineHomeworkSave: _config.prefix + 'testWork/onlineHomeworkSave',
    testwork_uploadStudyFile: _config.prefix + 'testWork/uploadStudyFile',
    testwork_delFileAnswer: _config.prefix + 'testWork/delFileAnswer',
    testwork_history: _config.prefix + 'testWork/history',
    testwork_keep: _config.prefix + 'testWork/keep',
    testwork_submitDraftHomework: _config.prefix + 'testWork/submitDraftHomework',
    testwork_previewDocHomework: _config.prefix + 'testWork/previewDocHomework',
    testwork_getStudyUpLoadView: _config.prefix + 'testWork/getStudyUpLoadView',
    testwork_uploadDocHomework: _config.prefix + 'testWork/uploadDocHomework',
    testwork_saveDocHomework: _config.prefix + 'testWork/saveDocHomework',
    testwork_docHomeworkHistory: _config.prefix + 'testWork/docHomeworkHistory',
    testwork_getFileHwData: _config.prefix + 'testWork/getFileHwData',
    testwork_getHomeworkMarkList: _config.prefix + 'testWork/getHomeworkMarkList',
    testwork_getClassList: _config.prefix + 'testWork/getClassList',
    testwork_saveWarnMessage: _config.prefix + 'testWork/saveWarnMessage',
    testwork_exportHomeworkScoreData: _config.prefix + 'testWork/exportHomeworkScoreData',
    testwork_onlineHomeworkSaveDraft: _config.prefix + 'testWork/onlineHomeworkSaveDraft',

    //考试
    onlineExam_getStudyOnlineExamList: _config.prefix + 'onlineExam/getStudyOnlineExamList',//--
    onlineExam_preview: _config.prefix + 'onlineExam/preview',
    onlineExam_onlineExamAnswer: _config.prefix + 'onlineExam/onlineExamAnswer',
    onlineExam_onlineExamSubAnswer: _config.prefix + 'onlineExam/onlineExamSubAnswer',
    onlineExam_onlineExamCheckSpace: _config.prefix + 'onlineExam/onlineExamCheckSpace',
    onlineExam_onlineExamMatch: _config.prefix + 'onlineExam/onlineExamMatch',
    onlineExam_delFileAnswer: _config.prefix + 'onlineExam/delFileAnswer',
    onlineExam_uploadFile: _config.prefix + 'onlineExam/uploadFile',
    onlineExam_onlineExamSave: _config.prefix + 'onlineExam/onlineExamSave',
    onlineExam_history: _config.prefix + 'onlineExam/history',
    onlineExam_getCheckScoreList: _config.prefix + 'onlineExam/getCheckScoreList',

    //作业-测验考试
    workExam_getWorkExamList: _config.prefix + 'workExam/getWorkExamList',
    workExam_getWorkExamData: _config.prefix + 'workExam/getWorkExamData',
    workExam_detail: _config.prefix + 'workExam/detail',
    workExam_workExamPreview: _config.prefix + 'workExam/workExamPreview',
    workExam_onlineWorkExamSaveDraft: _config.prefix + 'workExam/onlineWorkExamSaveDraft',
    workExam_workExamSave: _config.prefix + 'workExam/workExamSave',
    workExam_onlineHomeworkAnswer: _config.prefix + 'workExam/onlineHomeworkAnswer',
    workExam_delFileAnswer: _config.prefix + 'workExam/delFileAnswer',
    workExam_uploadStudyFile: _config.prefix + 'workExam/uploadStudyFile',
    workExam_onlineHomeworkSubAnswer: _config.prefix + 'workExam/onlineHomeworkSubAnswer',
    workExam_onlineHomeworkMatch: _config.prefix + 'workExam/onlineHomeworkMatch',
    workExam_onlineHomeworkCheckSpace: _config.prefix + 'workExam/onlineHomeworkCheckSpace',
    workExam_submitDraftHomework: _config.prefix + 'workExam/submitDraftHomework',
    workExam_onlineHomeworkSaveDraft: _config.prefix + 'workExam/onlineHomeworkSaveDraft',
    workExam_keep: _config.prefix + 'workExam/keep',
    workExam_history: _config.prefix + 'workExam/history',
    workExam_examPreview: _config.prefix + 'workExam/examPreview',
    workExam_onlineExamSubAnswer: _config.prefix + 'workExam/onlineExamSubAnswer',
    workExam_onlineExamAnswer: _config.prefix + 'workExam/onlineExamAnswer',
    workExam_onlineExamCheckSpace: _config.prefix + 'workExam/onlineExamCheckSpace',
    workExam_onlineExamMatch: _config.prefix + 'workExam/onlineExamMatch',
    workExam_uploadFile: _config.prefix + 'workExam/uploadFile',
    workExam_examDelFileAnswer: _config.prefix + 'workExam/examDelFileAnswer',
    workExam_onlineExamSave: _config.prefix + 'workExam/onlineExamSave',
    workExam_examHistory: _config.prefix + 'workExam/examHistory',
    workExam_addStudentWorkExamRecord: _config.prefix + 'workExam/addStudentWorkExamRecord',
    workExam_addStuWorkExamRecod: _config.prefix + 'workExam/addStuWorkExamRecod',
    workExam_saveDraftWorkXZ: _config.prefix + 'workExam/saveDraftWorkXZ',
     */

}