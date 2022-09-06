package com.vms.ykt.yktStuMobile.newZJY;

import android.net.wifi.aware.PublishDiscoverySession;

import androidx.databinding.DataBindingUtil;

import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.Util.httpRespnose;
import com.vms.ykt.yktUtil.yktHeaders;

import java.util.HashMap;

public class newZjyApi {

    //修改加课权限
    static String modifyClassAuditStatus = "https://user.icve.com.cn/m/zhzjMobile_modifyClassAuditStatus.action";

    public static String getModifyClassAuditStatus(String classId, String statusCode, String statusCodeCheck) {
        String data = "classId=" + classId + "&statusCode=" + statusCode + "&statusCodeCheck=" + statusCodeCheck;
        String resp = newZjyHttp.post(modifyClassAuditStatus, data);
        return resp;
    }


    //更改分数权重
    static String saveAssessment="https://user.icve.com.cn/zhzj/zhzjTeacher_saveAssessment.action";
    public static String getSaveAssessment(String courseId,String classId){
        String data ="token=MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0Q=&courseId="+courseId+"&classId="+classId+"&total_learn_time_rate=10&total_learn_time_full=100&node_rate=10&node_rate_full=100&effective_time_rate=10&effective_time_full=100&test_rate=10&test_rate_full=100&work_rate=10&work_rate_full=100&pop_quiz_rate=10&pop_quiz_full=100&exam_score_rate=10&exam_score=100&questions_rate=10&questions_rate_full=100&sign_num_rate=20&sign_num_full=100";
        String resp = newZjyHttp.post(saveAssessment, data);
        return resp;
    }

    static String createStuAssess="https://user.icve.com.cn/zhzj/zhzjTeacher_createStuAssess.action";
    public static String getCreateStuAssess(String courseId,String classId){
        //
        String data ="token=MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0Q=&classId="+classId+"&courseId="+courseId;
        String resp =newZjyHttp.post(createStuAssess, data);
        return resp;
    }

    //登陆
    private static String MobileLogin = "https://user.icve.com.cn/m/peMobileLogin_accountLogin.action";

    public static String getMobileLogin(String mobile, String passwd) {
        String data = "mobile=" + mobile + "&passwd=" + passwd;
        String resp = newZjyHttp.post(MobileLogin, data);
        return resp;
    }

    //用户其他信息
    static String UserInfo = "https://user.icve.com.cn/t/m/peMobileUserInfo_getUserInfo.action";

    public static String getUserInfo(String token) {
        String data = "token=" + token;
        String resp = newZjyHttp.post(UserInfo, data);
        return resp;
    }

    //检查登陆状态
    static String checkUser = "https://user.icve.com.cn/m/checkUser.action";

    public static String getCheckUser(String token) {
        String data = "token=" + token;
        String resp = newZjyHttp.post(checkUser, data);
        return resp;
    }

    //课程
    static String myClassList = "https://user.icve.com.cn/m/zhzjMobile_getMyClassList.action";

    public static String getMyClassList(String token) {
        String data = "token=" + token + "&pageSize=100" + "&type=2" + "&curPage=1";
        String resp = newZjyHttp.post(myClassList, data);
        return resp;
    }

    //
    static String RestSsoToken = "https://user.icve.com.cn/m/zhzjMobile_getRestSsoToken.action?token=42019dfb7be1463ebe155de353cab2c1";

    public static String getRestSsoToken(String token) {
        String data = "token=" + token;
        String resp = newZjyHttp.post(RestSsoToken, data);
        return resp;
    }

    //更新请求头
    public static void spocHeader() {
        HashMap<String, Object> vMap = new HashMap<>();
        vMap.put("Host", "spoc-classroom.icve.com.cn");
        vMap.put("X-Requested-With", "com.whaty.qyZHZJ");
        vMap.put("Content-Type", "application/json;charset=UTF-8");
        newZjyHttp.updataHeader(vMap);
        //System.out.println(JSONObject.toJSON(newZjyHttp.getHeader()));
    }

    // 重置请求头
    public static void restHeader() {
        newZjyHttp.setHeader(yktHeaders.getNewZjyMHeader());
    }

    //更新authorization
    public static void upAuthorization(String authorization) {
        newZjyHttp.addHeader("authorization", "Bearer " + authorization);
    }

    //更新rest_token cookie
    public static void upRest_token(String upCookie) {
        String nck="rest_token=" + upCookie;
        addCookie(nck);

    }
    public static void addCookie(String upCookie) {
        String ock=newZjyHttp.getUserCookie();
        if (ock!=null&&!ock.isEmpty()){
            upCookie=upCookie+";"+ock;
        }
        newZjyHttp.addHeader("Cookie",upCookie);
    }

    public static void printHeader() {
        System.out.println(JSONObject.toJSONString(newZjyHttp.getHeader()));
    }

    //课堂教学 根据courseId
    static String ClassroomByStudent = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/classroom/getClassroomByStudent";

    public static String getClassroomByStudent(String courseId) {
        String data = "{\"params\":{\"courseId\":\"" + courseId + "\"}}";
        String resp = newZjyHttp.post(ClassroomByStudent, data);
        return resp;
    }
    //今日课堂

    public static String getClassroomByDay(String day) {
        String data = "{\"params\":{\"startDate\":\"" + day + "\",\"current\":1,\"size\":10}}";
        String resp = newZjyHttp.post(ClassroomByStudent, data);
        return resp;
    }
    //全部课堂

    public static String getAllClassroom() {
        String data = "{\"params\":{\"current\":1,\"size\":100}}";
        String resp = newZjyHttp.post(ClassroomByStudent, data);
        return resp;
    }

    //全部课堂
    //课堂活动
    static String classActivityB = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/peClassroomActivity/teacher/classroomActivityList";
    static String classActivity = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/peClassroomActivity/student/classroomActivityList";

    public static String getClassActivity(String classroomId, String classroomTypeCode) {
        String data = "{\"params\":{\"classroomId\":\"" + classroomId + "\",\"classroomTypeCode\":" + classroomTypeCode + "}," +
                "\"page\":{\"curPage\":1,\"pageSize\":20,\"totalCount\":0,\"totalPage\":0}}";
        String resp = newZjyHttp.post(classActivity, data);
        return resp;

    }

    //课前
    public static String getClassActivityQ(String classroomId) {
        return getClassActivity(classroomId, "0");
    }

    //课中
    public static String getClassActivityZ(String classroomId) {
        return getClassActivity(classroomId, "1");
    }

    //课h
    public static String getClassActivityH(String classroomId) {
        return getClassActivity(classroomId, "2");
    }

    //活动详情 用来获手势签到
    static String ActivityById = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/peClassroomActivity/getActivityById";

    public static String getActivityById(String activityId) {
        String data = "{\"params\":{\"id\":\"" + activityId + "\"}}";
        String resp = newZjyHttp.post(ActivityById, data);
        return resp;
    }

    //结束开启课堂活动
    static String updateSignStatus = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/peClassroomActivity/teacher/updateSignStatus";

    public static String getUpdateSignStatus(String id, String status) {
        String data = "{\n" +
                "  \"params\": {\n" +
                "    \"id\": \"" + id + "\",\n" +
                "    \"status\": \"" + status + "\"\n" +
                "  }\n" +
                "}";
        String resp = newZjyHttp.post(updateSignStatus, data);
        return resp;
    }

    //课堂讨论 提问改分
    static String updateStudentSignStatus = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/question/teacher/updateStudentSignStatus";

    public static String getUpdateStudentSignStatus(String score, String recordIds) {
        String data = "{\"params\":{\"score\":" + score + ",\"recordIds\":[\"" + recordIds + "\"]}}\n";
        String resp = newZjyHttp.post(updateStudentSignStatus, data);
        return resp;
    }

    //课堂提问list
    static String QuestionStuListB = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/question/getQuestionStuList";

    public static String getQuestionStuListB(String activityId) {
        String data = "{\"params\":{\"activityId\":\"" + activityId + "\"}," +
                "\"page\":{\"curPage\":1,\"pageSize\":20,\"totalCount\":0,\"totalPage\":0}}";
        String resp = newZjyHttp.post(QuestionStuListB, data);
        return resp;
    }

    //提交讨论
    static String saveStuDiscussAnswer = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/discuss/saveStuDiscussAnswer";

    public static String getSaveStuDiscussAnswer(String activityId, String content) {
        String data = "{\"params\":{\"id\":\"" + activityId + "\"," +
                "\"content\":\"" + content + "\",\"imgPathList\":[]}}";
        String resp = newZjyHttp.post(saveStuDiscussAnswer, data);
        return resp;
    }

    //课堂讨论list
    static String PrStuActivityRecordB = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/discuss/getPrStuActivityRecord";
    static String PrStuActivityRecord = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/discuss/getPrStuActivityRecordByStu";

    public static String getPrStuActivityRecord(String activityId) {
        String data = "{\"params\":{\"id\":\"" + activityId + "\"}," +
                "\"page\":{\"curPage\":1,\"pageSize\":20,\"totalCount\":0,\"totalPage\":0}}";
        String resp = newZjyHttp.post(PrStuActivityRecord, data);
        return resp;
    }


    //签到状态
    static String SignResult = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/sign/student/getSignResult";

    public static String getSignResult(String recordIds) {
        String data = "{\"params\":{\"id\":\"" + recordIds + "\"}}";
        String resp = newZjyHttp.post(SignResult, data);
        return resp;
    }

    //签到以及补签
    static String teacherSignStatus = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/sign/teacher/updateStudentSignStatus";

    static String studentSignStatus = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/sign/student/updateSignStatus";

    public static String getStudentSignStatus(String recordIds, String activityId) {
        String data = "{\"params\":{\"id\":\"" + recordIds + "\"," +
                "\"activityId\":\"" + activityId + "\"}}";

        String resp = newZjyHttp.post(studentSignStatus, data);
        return resp;
    }

    public static String getStudentSignStatusC(String recordIds, String activityId, String signGesture) {
        String data = "{\"params\":{\"id\":\"" + recordIds + "\"," +
                "\"activityId\":\"" + activityId + "\",\"signGesture\":\"" + signGesture + "\"}}";

        String resp = newZjyHttp.post(studentSignStatus, data);
        return resp;
    }

    //老师补签 signStatus
    //3b9018e3fe3f11ebabc2fa346ba4cb00 已签
    //4404145efe3f11ebabc2fa346ba4cb00 迟到
    //4bbba639fe3f11ebabc2fa346ba4cb00 事假
    //55297083fe3f11ebabc2fa346ba4cb00 公家
    //5d7d6e8afe3f11ebabc2fa346ba4cb00 早退
    //64fb86a0fe3f11ebabc2fa346ba4cb00 病假
    //6da69011fe3f11ebabc2fa346ba4cb00 缺勤
    public static String getTeacherSignStatus(String recordIds, String signStatus) {
        String data = "{\"params\":{\"id\":\"" + recordIds + "\"," +
                "\"signStatus\":\"" + signStatus + "\"}}";
        String resp = newZjyHttp.post(teacherSignStatus, data);
        return resp;
    }

    //已签列表
    static String SignStudent = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/sign/teacher/getSignStudent";

    public static String getSignStudent(String activityId) {
        String data = "{\"params\":{\"id\":\"" + activityId + "\",\"signStatus\":\"1\"}," +
                "\"page\":{\"curPage\":1,\"pageSize\":100,\"totalCount\":0,\"totalPage\":0}}";
        String resp = newZjyHttp.post(SignStudent, data);
        return resp;
    }

    //未签列表
    static String UnSignStudent = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/sign/teacher/getUnSignStudent";

    public static String getUnSignStudent(String activityId) {
        String data = "{\"params\":{\"id\":\"" + activityId + "\"}," +
                "\"page\":{\"curPage\":1,\"pageSize\":100,\"totalCount\":0,\"totalPage\":0}}";
        String resp = newZjyHttp.post(UnSignStudent, data);
        return resp;
    }

    //获取ucokie
    static String signLearn="https://course.icve.com.cn/learnspace/sign/signLearn.action";
    public static String getSignLearn(String courseId ,String loginId){
        String data="sign=mobile&loginType=true&courseId="+courseId+
                "&loginId="+loginId+"&siteCode=zhzj&domain=spoc.icve.com.cn";
        newZjyHttp.addHeader("Host", "course.icve.com.cn");
        httpRespnose ret = newZjyHttp.get(signLearn, null,data);
        return JSONObject.toJSONString(ret.getHearderFileds());
    }
    //获取课件
    static String learnspace = "https://course.icve.com.cn/learnspace/learn/weixin/app/index.action";

    public static String getLearnspace(String courseId) {
        String data = "params.courseId="+courseId+"___";
        String resp = newZjyHttp.get(learnspace, data);
        return resp;
    }

    //更新学习项目
    static String updateLearningItem = "https://course.icve.com.cn/learnspace/course/study/learningTime_updateLearningItem.action";

    public static String getUpdateLearningItem(String itemId) {
        String data = "itemId=" + itemId;
        //newZjyHttp.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        String resp = newZjyHttp.post(updateLearningItem, data);
        return resp;
    }

    //视频信息
    static String queryVideo = "https://course.icve.com.cn/learnspace/learn/weixinCourseware/queryVideoResources.json";

    public static String getQueryVideo(String itemId) {
        String data = "params.itemId=" + itemId;
        String resp = newZjyHttp.post(queryVideo, data);
        return resp;
    }

    //保存学习项目
    static String saveLearningItem = "https://course.icve.com.cn/learnspace/u/scorm/saveLearningItem.json";

    public static String getSaveLearningItem(String videoTime, String courseId, String itemId) {
        String data = "entity.videoTime="+videoTime+"&entity.courseId=" + courseId + "&" +
                "entity.itemId=" + itemId;
        String resp = newZjyHttp.post(saveLearningItem, data);
        return resp;
    }

    //记录进度
    static String saveVideoForBatch = "https://course.icve.com.cn/learnspace/u/scorm/saveVideoLearnDetailRecordForBatch.json";

    public static String getSaveVideoForBatch(String itemId,String courseId) {
        String data = "entity.itemRecords=[{\"itemId\":\"" + itemId + "\"," +
                "\"resourceTotalTime\":\"00:60:00\",\"studyRecords\":[{\"endTime\":\"00:60:00\"," +
                "\"saveStudyTimeFlag\":\"0\",\"startTime\":\"00:00:00\"}],\"studyTime\":\"99999\"," +
                "\"videoIndex\":\"0\"}]&entity.courseId="+courseId;
        String resp = newZjyHttp.post(saveVideoForBatch, data);
        return resp;
    }

    //查询项目
    static String checkSingleItemLearn = "https://course.icve.com.cn/learnspace/course/study/learningTime_checkSingleItemLearn.action";

    public static String getCheckSingleItemLearn(String itemId) {
        String data = "itemId=7e51f1d0d9024310b2fccff71fabc956" + itemId;
        ;
        String resp = newZjyHttp.post(checkSingleItemLearn, data);
        return resp;
    }

    //保存课程学习时长
    static String saveLearningTime = "https://course.icve.com.cn/learnspace/course/study/learningTime_saveLearningTime.action";

    public static String getSaveLearningTime(String courseId, String studyTime) {
        String data = "courseId=" + courseId + "___&studyTime=" + studyTime;
        String resp = newZjyHttp.get(saveLearningTime, data);
        return resp;
    }

    //查询课程学习时间
    static String queryLearningTime = "https://course.icve.com.cn/learnspace/course/study/learningTime_queryLearningTime.action";

    public static String getQueryLearningTime(String courseId) {
        String data = "courseId=" + courseId + "___";
        String resp = newZjyHttp.post(queryLearningTime, data);
        return resp;
    }

    //查询项目详情
    static String queryCourseItemInfo = "https://course.icve.com.cn/learnspace/course/study/learningTime_queryCourseItemInfo.action";

    public static String getQueryCourseItemInfo(String itemId) {
        String data = "itemId=" + itemId;

        String resp = newZjyHttp.post(queryCourseItemInfo, data);
        return resp;
    }

    //非视频学习记录
    static String saveCourseItem = "https://course.icve.com.cn/learnspace/course/study/learningTime_saveCourseItemLearnRecord.action";

    public static String getSaveCourseItem(String courseId, String studyTime, String itemId) {
        String data = "courseId=" + courseId +
                "&studyTime=" + studyTime +
                "&itemId=" + itemId + "&recordType=0";
        String resp = newZjyHttp.post(saveCourseItem, data);
        return resp;
    }

    //test api

    static String TeachingDesign="https://course.icve.com.cn/learnspace/course/design/courseTeachingDesign_intoTeachingDesign.action";
    //https://course.icve.com.cn/learnspace/course/design/courseTeachingDesign_intoTeachingDesign.action?courseId=39e272199dab487ba6f8f76115cbfd2c&editState=1
    public static String getTeachingDesign(){
        String data ="courseId=39e272199dab487ba6f8f76115cbfd2c&params.cloudRoleId=&" +
                "params.cloudMetaId=&params.examRoleId=";
        String resp = newZjyHttp.get(TeachingDesign, data);
        return resp;
    }
    //test api

    static String queryChapterItem="https://course.icve.com.cn/learnspace/course/design/designCourseItem_queryChapterItem.action";
    public static String getQueryChapterItem(){
        String data ="courseId=39e272199dab487ba6f8f76115cbfd2c";
        String resp = newZjyHttp.get(queryChapterItem, data);
        return resp;
    }
    //test api

    static String queryChildItem="https://course.icve.com.cn/learnspace/course/design/designCourseItem_queryChildItem.action";
    public static String getQueryChildItem(){
        String data ="itemId=99ffe49b4da24327b98c6f6409868ddb&courseId=39e272199dab487ba6f8f76115cbfd2c";
        String resp = newZjyHttp.post(queryChildItem, data);
        return resp;
    }

    //test api

    static String delItemById="https://course.icve.com.cn/learnspace/course/design/designCourseItem_delItemById.action";
    public static String getDelItemById(){
        String data ="itemId=03fa7ab6e8564cc9baba3f4d90ad5589&courseId=39e272199dab487ba6f8f76115cbfd2c";
        String resp = newZjyHttp.post(delItemById, data);
        return resp;
    }
    //test api

    static String addSectionCourseItem="https://course.icve.com.cn/learnspace/course/design/designCourseItem_addSectionCourseItem.action";
    public static String getAddSectionCourseItem(){
        String data ="courseId=39e272199dab487ba6f8f76115cbfd2c&parentId=5fe19e246d524044b8af39a2afda14e3&" +
                "itemTitle=%E6%96%B0%E8%AE%B2%E8%AF%BE&sectionIndex=3";
        String resp = newZjyHttp.post(addSectionCourseItem, data);
        return resp;
    }
}
