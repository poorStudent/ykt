package com.vms.ykt.yktStuMobile.zjy;

/*
     ******************************

     Create : vms
     Date : 
     Used For : 

     ******************************
*/

import android.util.Log;

import com.alibaba.fastjson.*;


import com.vms.ykt.Util.HttpTool;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktUtil.zjy.zjyTool;


import java.text.*;
import java.util.*;

public class zjyApi {

    private static String TAG = zjyApi.class.getSimpleName();
    public static String appv;
    static String AppVersion = "https://zjy2.icve.com.cn/portal/AppVersion/getLatestVersionInfo";
    static String APPcode = "https://zjyapp.icve.com.cn/newmobileapi/AppVersion/getAppVersion";
    static String baseUrl = "https://zjyapp.icve.com.cn/newMobileAPI";
    static String getUserInfo = "https://zjyapp.icve.com.cn/newmobileapi/mobilelogin/getUserInfo";
    static String TeachList = "/Faceteach/getStuFaceTeachList";

    static String ActivityList = "https://zjyapp.icve.com.cn/newMobileAPI/faceteach/newGetStuFaceActivityList";

    static String JoinActivities = "/faceteach/isJoinActivities";
    static String saveSign = "/FaceTeach/saveStuSignNew";
    static String AllFaceTeachList = "https://zjyapp.icve.com.cn/newmobileapi/faceteach/getAllFaceTeachListByStu";
    static String StuFaceActivityList = "https://zjyapp.icve.com.cn/newmobileapi/faceTeach/getStuFaceActivityList";

    static String AllCourseInfo = "https://zjyapp.icve.com.cn/newMobileAPI/Student/getCourseList_new";
    static String ModuleList = "https://zjyapp.icve.com.cn/newmobileapi/assistTeacher/getModuleListByClassId";
    static String TopicList = "https://zjyapp.icve.com.cn/newmobileapi/assistTeacher/getTopicListByModuleId";
    static String CellList = "https://zjyapp.icve.com.cn/newmobileapi/assistTeacher/getCellListByTopicId";
    static String canOpenCell = "https://zjyapp.icve.com.cn/newmobileapi/assistTeacher/canOpenCell";
    static String CellInfoByCelld = "https://zjyapp.icve.com.cn/newmobileapi/assistTeacher/getCellInfoByCellId";
    static String StuProcessCellLog = "https://zjyapp.icve.com.cn/newmobileapi/Student/newStuProcessCellLog";
    static String CellBBSList = "https://zjyapp.icve.com.cn/newmobileapi/bbs/getCellBBSList";
    static String addCellComment = "https://zjyapp.icve.com.cn/newmobileapi/bbs/addCellComment";
    static String addCellAskAnswer = "https://zjyapp.icve.com.cn/newmobileapi/bbs/addCellAskAnswer";
    static String addCellNote = "https://zjyapp.icve.com.cn/newmobileapi/bbs/addCellNote";
    static String addCellError = "https://zjyapp.icve.com.cn/newmobileapi/bbs/addCellError";
    static String stuEvaluationInfo = "https://zjyapp.icve.com.cn/newmobileapi/faceTeach/getstuEvaluationInfo";
    static String addEvaluationStu = "https://zjyapp.icve.com.cn/newmobileapi/faceTeach/addFaceTeachEvaluationStu";
    static String SelfEvaluationInfo = "https://zjyapp.icve.com.cn/newmobileapi/faceTeach/getFaceTeachSelfEvaluation";
    static String saveSelfEvaluation = "https://zjyapp.icve.com.cn/newmobileapi/faceTeach/saveFaceTeachSelfEvaluation";

    static String getHomeworkList = "https://zjyapp.icve.com.cn/newmobileapi/homework/getHomeworkList_new";


    static String getAndSaveStuAnswerRecord = "https://zjyapp.icve.com.cn/newmobileapi/faceTeach/getAndSaveStuAnswerRecord";
    static String getStuAnswerList = "https://zjyapp.icve.com.cn/newmobileapi/faceTeach/getStuAnswerList";

    public static String getAppVersion0() {
        String appVersion = "2.8.43";
        String data = "versionCode=2.8.43&equipmentAppVersion=2.8.43&equipmentModel=Redmi M2007J17C&equipmentApiVersion=10";
        String resp = "";
        resp = zjyHttpM.post(APPcode, data);
        if (resp==null||!resp.contains("VersionCode")) return appVersion;
        JSONObject json = JSONObject.parseObject(resp);
        appVersion = json.getJSONObject("data").getJSONObject("appVersionInfo").getString("versionCode");
        return appVersion;
    }

    public static String getAppVersion1() {
        String appVersion = "2.8.43";
        String resp = "";
        resp = zjyHttpM.get(AppVersion);
        if (resp==null||!resp.contains("VersionCode")) return getAppVersion0();
        JSONObject json = JSONObject.parseObject(resp);
        appVersion = json.getJSONObject("appVersionInfo").getString("VersionCode");
        return appVersion;
    }


    public static String getTime() {
        String time = "";
        time = String.valueOf(new Date().getTime()).substring(0, 10) + "000";
        return time;
    }

    public static String getDevice(String appVersion, String time) {
        String device = Tool.md5(deviceM);
        device = Tool.md5(device + "10");
        device = Tool.md5(device + appVersion);
        device = Tool.md5(device + time);
        return device;

    }

    public static String getUserInfo(zjyUser zjyUser) {

        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(getUserInfo, postParams);
        return resp;
    }


    //今日课堂
    public static String getStuFaceTeachList(zjyUser zjyUser) {
        /**
         * 今日课堂列表
         * **/
        SimpleDateFormat vSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String faceData = vSimpleDateFormat.format(new Date());
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("faceDate=" + faceData + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(zjyApi.baseUrl + zjyApi.TeachList, postParams);
        return resp;
    }

    /**
     * public static String getSignActivityList(zjyUser zjyUser, zjyTeachInfo zjyDayTeachInfo,String classState) {
     * String resp = "";
     * StringBuilder postParam = new StringBuilder();
     * postParam.append("equipmentModel=Redmi M2007J17C&");
     * postParam.append("equipmentApiVersion=10&");
     * postParam.append("activityId=" + zjyDayTeachInfo.getId() + "&");
     * postParam.append("stuId" + zjyUser.getUserId() + "&");
     * postParam.append("classState=" + classState + "&");
     * postParam.append("openClassId=" + zjyDayTeachInfo.getOpenClassId() + "&");
     * postParam.append("newToken=" + zjyUser.getNewToken());
     * String postParams = postParam.toString();
     * resp = zjyHttpM.post(ActivityList, postParams);
     * return resp;
     * }
     **/

    //课堂活动
    private static String getActivityList(zjyUser zjyUser, zjyTeachInfo zjyDayTeachInfo, String classState) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("activityId=" + zjyDayTeachInfo.getId() + "&");
        postParam.append("stuId" + zjyUser.getUserId() + "&");
        postParam.append("classState=" + classState + "&");
        postParam.append("openClassId=" + zjyDayTeachInfo.getOpenClassId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(ActivityList, postParams);
        Log.d(TAG, "getActivityList: "+resp);
        return resp;
    }

    public static String getCourseActivityList1(zjyUser zjyUser, zjyTeachInfo zjyDayTeachInfo) {
        return getActivityList(zjyUser, zjyDayTeachInfo, "1");
    }

    public static String getCourseActivityList2(zjyUser zjyUser, zjyTeachInfo zjyDayTeachInfo) {
        return getActivityList(zjyUser, zjyDayTeachInfo, "2");
    }

    public static String getCourseActivityList3(zjyUser zjyUser, zjyTeachInfo zjyDayTeachInfo) {
        return getActivityList(zjyUser, zjyDayTeachInfo, "3");
    }

    //签到
    public static String getJoinActivities(String tid, zjyUser zjyUser, zjyTeachInfo zjyDayTeachInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("equipmentModel=Redmi M2007J17C&");
        postParam.append("equipmentApiVersion=10&");
        postParam.append("activityId=" + zjyDayTeachInfo.getId() + "&");
        postParam.append("openClassId=" + zjyDayTeachInfo.getOpenClassId() + "&");
        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("typeId=" + tid + "&");
        postParam.append("type=1&");
        postParam.append("newToken=" + zjyUser.getNewToken());
        String postParams = postParam.toString();
        resp = zjyHttpM.post(zjyApi.baseUrl + zjyApi.JoinActivities, postParams);
        return resp;
    }

    public static String getSaveSign(String tid, String code, zjyUser zjyUser, zjyTeachInfo zjyDayTeachInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("signId=" + tid + "&");
        postParam.append("activityId=" + zjyDayTeachInfo.getId() + "&");
        postParam.append("openClassId=" + zjyDayTeachInfo.getOpenClassId() + "&");
        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("sourceType=3&");
        postParam.append("type=2&");
        postParam.append("checkInCode=" + code + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(zjyApi.baseUrl + zjyApi.saveSign, postParams);
        return resp;
    }

    //所有课程
    public static String getAllCourseInfo(zjyUser zjyUser, String page) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("page=" + page + "&");
        postParam.append("isPass=0&");
        postParam.append("pageSize=" + 100 + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(zjyApi.AllCourseInfo, postParams);
        return resp;
    }

    //获取课件
    public static String getModuleList(zjyUser zjyUser, zjyCourseIfno zjyCourseIfno) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(zjyApi.ModuleList, postParams);
        return resp;
    }

    public static String getTopicList(zjyUser zjyUser, zjyCourseIfno zjyCourseIfno, zjyModuleListInfo zjyModuleListInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();


        postParam.append("moduleId=" + zjyModuleListInfo.getModuleId() + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(zjyApi.TopicList, postParams);
        return resp;
    }

    public static String getCellListU(zjyUser zjyUser, zjyCourseIfno zjyCourseIfno, String TopicId) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("topicId=" + TopicId + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(zjyApi.CellList, postParams);
        return resp;
    }

    public static String getCellList(zjyUser zjyUser, zjyCourseIfno zjyCourseIfno, zjyTopicList zjyTopicList) {
        return getCellListU(zjyUser, zjyCourseIfno, zjyTopicList.getTopicId());
    }

    //课件是否公开
    public static String getCanOpenCell(zjyUser zjyUser, zjyCourseIfno zjyCourseIfno, zjyModuleListInfo zjyModuleListInfo, zjyTopicList zjyTopicList, zjyCellList zjyCellList) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("upTopicId=" + zjyTopicList.getUpTopicId() + "&");
        postParam.append("upCellId=" + zjyCellList.getUpCellId() + "&");
        postParam.append("isFirstModule=" + zjyModuleListInfo.getIsFirstModule() + "&");
        postParam.append("moduleId=" + zjyModuleListInfo.getModuleId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(zjyApi.canOpenCell, postParams);
        return resp;
    }

    //课件详情
    public static String getCellInfoByCelld(zjyUser zjyUser, zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellList) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("sourceType=2" + "&");
        postParam.append("isTeaSee=0" + "&");
        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("cellId=" + zjyCellList.getCellId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(zjyApi.CellInfoByCelld, postParams);
        return resp;
    }


    //刷课
    public static String getStuProcessCellLog(zjyUser zjyUser, zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellList, zjyInfoByCelld zjyInfoByCelld, long time) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("sourceType=2" + "&");
        String Time = Tool.getStuProcessTime();
        postParam.append("secretKey=" + zjyTool.a(zjyUser.getUserId(), Time, zjyCellList.getCellId()) + "&");
        postParam.append("answerTime=" + Time + "&");
        postParam.append("token=" + zjyInfoByCelld.getToken() + "&");
        postParam.append("picNum=900" + "&");
        postParam.append("studyCellTime=" + (!zjyInfoByCelld.getAudioVideoLong().isEmpty() ? zjyInfoByCelld.getAudioVideoLong() + time : 400.0 + time) + "&");
        postParam.append("maxPicNum=0" + "&");
        postParam.append("studyNewlyPicNum=" + 900 + "&");
        postParam.append("studyNewlyTime=" + (!zjyInfoByCelld.getAudioVideoLong().isEmpty() ? zjyInfoByCelld.getAudioVideoLong() + time : 400.0 + time) + "&");
        postParam.append("maxStudyCellTime=0"  + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId() + "&");
        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("cellLogId=" + zjyInfoByCelld.getCellLogId() + "&");
        postParam.append("cellId=" + zjyCellList.getCellId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        Log.d(TAG, "getStuProcessCellLog: " + postParams);

        resp = zjyHttpM.post(zjyApi.StuProcessCellLog, postParams);
        return resp;
    }

    //评价区
    public static String getCellBBSList(zjyUser zjyUser, zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellList, String activeType) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("sourceType=2" + "&");
        postParam.append("pageSize=20" + "&");
        postParam.append("activeType=" + activeType + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId() + "&");
        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("cellId=" + zjyCellList.getCellId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append(getHeaders(zjyUser));

        String postParams = postParam.toString();
        resp = zjyHttpM.post(zjyApi.CellBBSList, postParams);
        return resp;
    }

    public static String getCellBBSList1(zjyUser zjyUser, zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellList) {
        return getCellBBSList(zjyUser, zjyCourseIfno, zjyCellList, "1");
    }

    //课件评价
    public static String getAddCellBBS(String zjyUrl, zjyUser zjyUser, zjyCourseIfno
            zjyCourseIfno, zjyCellList zjyCellList, String Content) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        HashMap<String, String> postData = new HashMap<>();
        postData.put("OpenClassId", zjyCourseIfno.getOpenClassId());
        postData.put("CourseOpenId", zjyCourseIfno.getCourseOpenId());
        postData.put("CellId", zjyCellList.getCellId());
        postData.put("UserId", zjyUser.getUserId());
        postData.put("Content", Content);
        postData.put("Star", "5.0");
        String postDatas = JSONObject.toJSONString(postData);
        postParam.append("data=" + postDatas + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();

        resp = zjyHttpM.post(zjyUrl, postParams);
        return resp;
    }

    public static String getAddCellComment(zjyUser zjyUser, zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellLists, String Content) {
        return getAddCellBBS(zjyApi.addCellComment, zjyUser, zjyCourseIfno, zjyCellLists, Content);
    }

    public static String getAddCellAskAnswer(zjyUser zjyUser, zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellLists, String Content) {
        return getAddCellBBS(zjyApi.addCellAskAnswer, zjyUser, zjyCourseIfno, zjyCellLists, Content);
    }

    public static String getAddCellNote(zjyUser zjyUser, zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellLists, String Content) {
        return getAddCellBBS(zjyApi.addCellNote, zjyUser, zjyCourseIfno, zjyCellLists, Content);
    }

    public static String getAddCellError(zjyUser zjyUser, zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellLists, String Content) {
        return getAddCellBBS(zjyApi.addCellError, zjyUser, zjyCourseIfno, zjyCellLists, Content);
    }

    /*
     * 获取所有课堂
     */
    public static String getAllFaceTeachList(zjyUser zjyUser, zjyCourseIfno zjyCourseIfno,
                                             int page) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("page=" + page + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(zjyApi.AllFaceTeachList, postParams);
        return resp;
    }

    //课后评价
    public static String getSelfEvaluationInfo(zjyUser zjyUser, zjyTeachInfo zjyAllTeachInfo) {
        /**自我总结**/
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("ActivityId=" + zjyAllTeachInfo.getId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(zjyApi.SelfEvaluationInfo, postParams);
        return resp;
    }

    public static String getStuEvaluationInfo(zjyUser zjyUser, zjyCourseIfno
            zjyCourseIfno, zjyTeachInfo zjyAllTeachInfo) {
        /**课堂评价**/
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("ActivityId=" + zjyAllTeachInfo.getId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();

        resp = zjyHttpM.post(zjyApi.stuEvaluationInfo, postParams);
        return resp;
    }

    public static String getAddEvaluation(String zjyUrl, zjyUser zjyUser, zjyCourseIfno
            zjyCourseIfno, zjyTeachInfo zjyAllTeachInfo, String id, String Content) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();


        HashMap<String, String> postData = new HashMap<>();

        postData.put("Id", id);
        postData.put("DateCreated", zjyAllTeachInfo.getDateCreated());
        postData.put("ActivityId", zjyAllTeachInfo.getId());
        postData.put("OpenClassId", zjyCourseIfno.getOpenClassId());
        postData.put("CourseOpenId", zjyCourseIfno.getCourseOpenId());
        postData.put("SourceType", "2");
        postData.put("UserId", zjyUser.getUserId());
        postData.put("Content", Content);
        postData.put("Star", "5");
        String postDatas = JSONObject.toJSONString(postData);

        postParam.append("data=" + postDatas + "&");
        postParam.append(getHeaders(zjyUser));

        String postParams = postParam.toString();
        resp = zjyHttpM.post(zjyUrl, postParams);
        return resp;
    }

    public static String getAddEvaluationStu(zjyUser zjyUser, zjyCourseIfno
            zjyCourseIfno, zjyTeachInfo zjyAllTeachInfo, String Content) {
        /**课堂评价**/
        String id = "";

        return getAddEvaluation(zjyApi.addEvaluationStu, zjyUser, zjyCourseIfno,
                zjyAllTeachInfo, id, Content);
    }

    public static String getSaveSelfEvaluation(zjyUser zjyUser, zjyCourseIfno
            zjyCourseIfno, zjyTeachInfo zjyAllTeachInfo, String Content) {
        /**自我总结**/
        String resp = getSelfEvaluationInfo(zjyUser, zjyAllTeachInfo);
        if (resp == null) return resp + "";
        JSONObject json = JSONObject.parseObject(resp);
        if (!json.getString("code").equals("1")) {
            return json.toString();
        }
        String id = "";
        try {
            id = json.getJSONObject("selfEvaluationInfo").getString("Id");
        } catch (Exception e) {
            return "获取id失败";
        }
        return getAddEvaluation(zjyApi.saveSelfEvaluation, zjyUser, zjyCourseIfno,
                zjyAllTeachInfo, id, Content);
    }

    public static String getStuAnswerList(zjyUser ZjyUser, String courseOpenId, String
            openClassId, String classTestId) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();


        postParam.append("equipmentModel=Redmi M2007J17C&");
        postParam.append("equipmentApiVersion=10&");
        postParam.append("courseOpenId=" + courseOpenId + "&");
        postParam.append("openClassId=" + openClassId + "&");
        postParam.append("stuId=" + ZjyUser.getUserId() + "&");
        postParam.append("classTestId=" + classTestId + "&");
        postParam.append("newToken=" + ZjyUser.getNewToken());

        String postParams = postParam.toString();
        resp = zjyHttpM.post(getStuAnswerList, postParams);
        return resp;
    }

    //获取取测验
    public static String getAndSaveStuAnswerRecord(zjyUser ZjyUser, String openClassId, String
            classTestId) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("sourceType=2&");
        postParam.append("openClassId=" + openClassId + "&");
        postParam.append("stuId=" + ZjyUser.getUserId() + "&");
        postParam.append("classTestId=" + classTestId + "&");
        postParam.append(getHeaders(ZjyUser));

        String postParams = postParam.toString();
        resp = zjyHttpM.post(getAndSaveStuAnswerRecord, postParams);
        return resp;
    }

    static String getExamList = "https://zjyapp.icve.com.cn/newmobileapi/onlineExam/getExamList_new";

    //获取考试
    public static String getExamList(zjyUser zjyUser, String
            courseOpenId, String openClassId, String page) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("pageSize=10" + "&");
        postParam.append("page=" + page + "&");
        postParam.append("exType=0" + "&");
        postParam.append("examState=0" + "&");
        postParam.append("courseOpenId=" + courseOpenId + "&");
        postParam.append("openClassId=" + openClassId + "&");
        postParam.append(getHeaders(zjyUser));

        String postParams = postParam.toString();
        resp = zjyHttpM.post(getExamList, postParams);
        return resp;
    }

    public static String getExamList_us(zjyUser ZjyUser, zjyCourseIfno zjyCourseIfno, String
            page) {
        return getExamList(ZjyUser, zjyCourseIfno.getCourseOpenId(), zjyCourseIfno.getOpenClassId(), page);
    }

    static String getExamIsAuthenticate = "https://zjyapp.icve.com.cn/newmobileapi/onlineExam/getExamIsAuthenticate";

    //考试详情
    public static String getExamIsAuthenticate(zjyUser zjyUser, zjyCourseIfno
            zjyCourseIfno, ExamInfo ExamInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("examId=" + ExamInfo.getExamId() + "&");
        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(getExamIsAuthenticate, postParams);
        return resp;
    }

    static String getNewExamPreviewNew = "https://zjyapp.icve.com.cn/newmobileapi/onlineExam/getNewExamPreviewNew";

    //打开考试
    public static String getNewExamPreviewNew(zjyUser zjyUser, zjyCourseIfno
            zjyCourseIfno, ExamInfo ExamInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("examId=" + ExamInfo.getExamId() + "&");
        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("sourceType=2&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(getNewExamPreviewNew, postParams);
        return resp;
    }

    static String getMqttTokenByExam = "https://zjyapp.icve.com.cn/newmobileapi/superviseonlineexam/getMqttTokenByExam";

    public static String getMqttTokenByExam(zjyUser zjyUser, zjyCourseIfno
            zjyCourseIfno, ExamInfo ExamInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("examId=" + ExamInfo.getExamId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(getMqttTokenByExam, postParams);
        return resp;
    }

    //获取作业
    public static String getHomeworkList(zjyUser zjyUser, String
            courseOpenId, String openClassId, String page) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("pageSize=20" + "&");
        postParam.append("page=" + page + "&");
        postParam.append("homeworkType=0" + "&");
        postParam.append("hkState=0" + "&");
        postParam.append("courseOpenId=" + courseOpenId + "&");
        postParam.append("openClassId=" + openClassId + "&");
        postParam.append(getHeaders(zjyUser));

        String postParams = postParam.toString();
        resp = zjyHttpM.post(getHomeworkList, postParam.toString());
        return resp;
    }

    public static String getHomeworkList_us(zjyUser ZjyUser, zjyCourseIfno
            zjyCourseIfno, String page) {
        return getHomeworkList(ZjyUser, zjyCourseIfno.getCourseOpenId(), zjyCourseIfno.getOpenClassId(), page);
    }

    static String getHomeworkStuRecord = "https://zjyapp.icve.com.cn/newmobileapi/homework/getHomeworkStuRecord";

    //作业详情
    public static String getHomeworkStuRecord(zjyUser zjyUser, zjyCourseIfno
            zjyCourseIfno, HomeworkInfo zjyHomeworkInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("homeworkId=" + zjyHomeworkInfo.getHomeworkId() + "&");
        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("homeworkTermTimeId=" + zjyHomeworkInfo.getHomeworkTermTimeId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(getHomeworkStuRecord, postParams);
        return resp;
    }

    //打开作业
    static String getPreview = "https://zjyapp.icve.com.cn/newmobileapi/homework/getPreview";

    public static String getPreview(zjyUser zjyUser, zjyCourseIfno zjyCourseIfno, HomeworkInfo
            zjyHomeworkInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("homeworkId=" + zjyHomeworkInfo.getHomeworkId() + "&");
        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("homeworkTermTimeId=" + zjyHomeworkInfo.getHomeworkTermTimeId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = zjyHttpM.post(getPreview, postParams);
        return resp;
    }

    //作业考试答案
    static String getAnswerList = "http://app.fuyaoup.cn/answer/icve/getAnswerList";

    public static String getAskAnswer(String qids, String Token) {
        String resp = "";

        StringBuilder postParam = new StringBuilder();

        postParam.append("list=" + qids);
        String postParams = postParam.toString();

        HashMap<String, Object> header = new HashMap<>();
        header.put("Connection", "Keep-Alive");
        header.put("Host", "app.fuyaoup.cn");
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("token", Token);

        resp = HttpTool.postJ(getAnswerList, header, postParams).getResp();
        return resp;
    }

    static String getAnswer = "http://zjy.coolcr.cn/zjy/workExam/preview";

    public static String getAskAnswer(String qids) {
        String resp = "";

        StringBuilder postParam = new StringBuilder();

        postParam.append("code=hvvQqHCiEcMw" + "&");
        postParam.append("list=" + qids + "&");
        postParam.append("device=39");
        String postParams = postParam.toString();

        HashMap<String, Object> header = new HashMap<>();
        header.put("Connection", "Keep-Alive");
        header.put("Host", "zjy.coolcr.cn");
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

        resp = HttpTool.postJ(getAnswer, header, postParams).getResp();
        return resp;
    }

    static String getToken = "http://app.fuyaoup.cn/authorise/binding";

    public static String getToken() {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("equiment=aa992fbd44734348&code=ususgsgdtdgd");
        String postParams = postParam.toString();
        HashMap<String, Object> header = new HashMap<>();
        header.put("Connection", "Keep-Alive");
        header.put("Host", "app.fuyaoup.cn");
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

        resp = HttpTool.postJ(getToken, header, postParams).getResp();
        return resp;
    }

    private static String deviceM = Tool.getDEVICEModle();

    //标头
    public static String getHeaders(zjyUser zjyUser) {
        StringBuilder postParam = new StringBuilder();
        postParam.append("equipmentAppVersion=" + zjyUser.getAppv() + "&");
        postParam.append("equipmentModel=" + deviceM + "&");
        postParam.append("equipmentApiVersion=10&");
        postParam.append("newToken=" + zjyUser.getNewToken());
        return postParam.toString();
    }


}
