package com.vms.ykt.yktStuMobile.newZJY;


import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.Util.httpRespnose;
import com.vms.ykt.yktUtil.yktHeaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class newZjyApi {


    //获取 RestSsoToken authorization pageToken
    static String RestSsoToken = "https://user.icve.com.cn/m/zhzjMobile_getRestSsoToken.action?token=";

    public static String getRestSsoToken(String token) {
        String RestSsoTokens = RestSsoToken + token;
        String resp = newZjyHttp.post(RestSsoTokens, "");
        return resp;
    }


    //更新 authorization
    private static String TokenByPageToken = "https://spoc-classroom.icve.com.cn/rest-sso/open/getTokenByPageToken";

    public static String getTokenByPageToken(String pageToken) {
        String data = "{\"params\":{\"pageToken\":\"" +
                pageToken +
                "\"}}";
        String resp = newZjyHttp.post(TokenByPageToken, data);
        return resp;
    }


    //更新请求头
    public static void upHeader2() {

        newZjyHttp.addHeader("Content-Type", "application/json;charset=UTF-8");
        newZjyHttp.addHeader("Host", "spoc-classroom.icve.com.cn");
        //System.out.println(JSONObject.toJSON(newZjyHttp.getHeader()));
    }
    //更新请求头
    public static void upHeader1() {

        newZjyHttp.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        newZjyHttp.addHeader("Host", "user.icve.com.cn");
        printHeader();

        //System.out.println(JSONObject.toJSON(newZjyHttp.getHeader()));
    }

    // 重置请求头
    public static void restHeader() {
        newZjyHttp.setHeader(yktHeaders.getNewZjyMHeader());
    }

    //更新authorization
    public static void upAuthorization(String authorization) {
        newZjyHttp.addHeader("authorization", "Bearer " + authorization);
        printHeader();
    }
    //更新UNTYXLCOOKIE
    public static void upUNTYXLCOOKIE( String UNTYXLCOOKIE) {
        newZjyHttp.addCookie(UNTYXLCOOKIE);
    }
    //更新rest_token cookie
    public static void upRest_token(String upCookie) {
        String nck = "rest_token=" + upCookie;
        newZjyHttp.addCookie(nck);

    }


    public static void printHeader() {
        System.out.println(JSONObject.toJSONString(newZjyHttp.getHeader()));
    }

    public static void printHeader(Map<String, List<String>> map) {
        System.out.println(JSONObject.toJSONString(map));
    }

    public static void print(Object map) {
        System.out.println(map);
    }


    //活动id
    private static String NamespaceAndSiteCode="https://spoc-classroom.icve.com.cn/classroom-teaching-api/enumConst/getByNamespaceAndSiteCode";

    public static String getNamespaceAndSiteCode(String namespace) {
        String data = "{\"params\":{\"namespace\":\""+namespace+"\"}}";
        String resp = newZjyHttp.post(NamespaceAndSiteCode, data);
        return resp;
    }

    public static String getFlagClassroomType() {
     return getNamespaceAndSiteCode("FlagClassroomType");
    }

    public static String getFlagActivityType() {
        return getNamespaceAndSiteCode("FlagActivityType");
    }


    public static String getFlagQuestionType() {
        return getNamespaceAndSiteCode("FlagQuestionType");
    }


    public static String getFlagDiscussType() {
        return getNamespaceAndSiteCode("FlagDiscussType");
    }


    //创建活动
    private static String saveActivity = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/peClassroomActivity/teacher/saveActivity";

    //提问抢答
    public static String getSaveActivityTw1(String classroomId, String courseId,String content,String studentCount) {
        String data = "{\"params\":{\"name\":\"提问\",\"activityType\":\"f7f3cef4fe3e11ebabc2fa346ba4cb00\"," +
                "\"classroomId\":\""+classroomId+"\"," +
                "\"flagClassroomType\":\"41828108a8f711eca8977c10c99ef73b\"," +
                "\"status\":1,\"detailType\":\"f52334671f3e11ec99cf0c42a14757d6\"," +
                "\"isFilter\":\"0\"," +
                "\"content\":\""+content+"\"," +
                "\"checkedStu\":[]," +
                "\"studentCount\":"+studentCount+"," +
                "\"courseId\":\""+courseId+"\"}}";
        String resp = newZjyHttp.post(saveActivity, data);
        return resp;
    }

    //抽人提问
    public static String getSaveActivityTw2(String classroomId, String courseId,String content,String checkedStu) {
        String data = "{\"params\":{\"name\":\"提问\",\"activityType\":\"f7f3cef4fe3e11ebabc2fa346ba4cb00\"," +
                "\"classroomId\":\""+classroomId+"\"," +
                "\"flagClassroomType\":\"41828108a8f711eca8977c10c99ef73b\"," +
                "\"status\":1,\"detailType\":\"f51be0711f3e11ec99cf0c42a14757d6\"," +
                "\"isFilter\":\"0\"," +
                "\"content\":\""+content+"\"," +
                "\"checkedStu\":"+checkedStu+"," +
                "\"studentCount\":null," +
                "\"courseId\":\""+courseId+"\"}}";
        String resp = newZjyHttp.post(saveActivity, data);
        return resp;
    }

    //讨论
    public static String getSaveActivityTl(String classroomId, String courseId,String content) {
        String data = "{\"params\":{\"name\":\"讨论\",\"activityType\":\"fd650823fe3e11ebabc2fa346ba4cb00\"," +
                "\"classroomId\":\""+classroomId+"\"," +
                "\"flagClassroomType\":\"41828108a8f711eca8977c10c99ef73b\"," +
                "\"status\":1,\"detailType\":\"30ae0e1c2ff811ec99cf0c42a14757d6\"," +
                "\"content\":\""+content+"\"" +
                ",\"imgPathList\":[],\"deletePathList\":[]," +
                "\"courseId\":\""+courseId+"\"}}";
        String resp = newZjyHttp.post(saveActivity, data);
        return resp;
    }

    //抢答
    private static String saveRushToAnswer = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/question/saveRushToAnswer";

    public static String getSaveRushToAnswer(String activityId) {
        String data = "{\"params\":{" +
                "\"activityId\":\"067a252144bd11edafa20c42a12c121a\"," +
                "\"content\":\"111\"," +
                "\"maxAnswerNum\":1}}";
        String resp = newZjyHttp.post(saveRushToAnswer, data);
        return resp;
    }

    //创建分组讨论
    private static String createGroupActivity = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/grouppk/createGroupActivity";

    public static String getCreateGroupActivity(String classroomId, String courseId,String groupCount) {
        String data = "{\"params\":{\"name\":\"小组PK\",\"activityType\":\"bde4bee12f5c11eda2b7fa346ba4cb00\"," +
                "\"classroomId\":\""+classroomId+"\"," +
                "\"flagClassroomType\":\"41828108a8f711eca8977c10c99ef73b\",\"status\":1," +
                "\"courseId\":\""+courseId+"\"," +
                "\"groupCount\":\""+groupCount+"\",\"isAutomaticAllocation\":1}}";
        String resp = newZjyHttp.post(createGroupActivity, data);
        return resp;
    }

    //讨论组打分
    private static String rateTheGroup = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/grouppk/rateTheGroup";

    public static String getRateTheGroup(String groupId,String score) {
        String data = "{\"params\":{\"groupId\":\""+groupId+"\",\"score\":"+score+"}}";
        String resp = newZjyHttp.post(rateTheGroup, data);
        return resp;
    }

    //分组讨论详情

    //老师api
    private static String CurrentActiveAllGroups = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/grouppk/getCurrentActiveAllGroups";
    //学生api
    private static String GroupActivityAndStuInfo = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/grouppk/getGroupActivityAndStuInfo";

    public static String getAllGroups(String activityId) {
        String data = "{\"params\":{\"activityId\":\""+activityId+"\"}}";
        String resp = newZjyHttp.post(GroupActivityAndStuInfo, data);
        return resp;
    }

    //自己分组详情
    private static String GroupInfoByUser = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/grouppk/getGroupInfoByUser";

    public static String getGroupInfoByUser(String activityId) {
        String data = "{\"params\":{\"activityId\":\""+activityId+"\"}}";
        String resp = newZjyHttp.post(GroupInfoByUser, data);
        return resp;
    }


    //获取课堂内所有学生信息
    private static String StudentsQuestioned = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/question/teacher/getStudentsQuestioned";

    public static String getStudentsQuestioned(String classroomId) {
        String data = "{\"params\":{\"classroomId\":\""+classroomId+"\"," +
                "\"isFilter\":\"0\",\"searchContent\":\"\"}}";
        String resp = newZjyHttp.post(StudentsQuestioned, data);
        return resp;
    }

    //删除活动
    private static String delActivity = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/peClassroomActivity/teacher/delActivity";

    public static String getDelActivity(String classroomId, String activityId) {
        String data = "{\"params\":{" +
                "\"id\":\""+activityId+"\"," +
                "\"classroomId\":\""+classroomId+"\"}}";
        String resp = newZjyHttp.post(delActivity, data);
        return resp;
    }

    //修改加课权限
    static String modifyClassAuditStatus = "https://user.icve.com.cn/m/zhzjMobile_modifyClassAuditStatus.action";

    public static String getModifyClassAuditStatus(String classId, String statusCode, String statusCodeCheck) {
        String data = "classId=" + classId + "&statusCode=" + statusCode + "&statusCodeCheck=" + statusCodeCheck;
        String resp = newZjyHttp.post(modifyClassAuditStatus, data);
        return resp;
    }


    //更改分数权重
    static String saveAssessment = "https://user.icve.com.cn/zhzj/zhzjTeacher_saveAssessment.action";

    public static String getSaveAssessment(String courseId, String classId) {
        String data = "token=MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0Q=&courseId=" + courseId +
                "&classId=" + classId +
                "&total_learn_time_rate=10&total_learn_time_full=100" +
                "&node_rate=10&node_rate_full=100" +
                "&effective_time_rate=10&effective_time_full=100" +
                "&test_rate=10&test_rate_full=100" +
                "&work_rate=10&work_rate_full=100" +
                "&pop_quiz_rate=10&pop_quiz_full=100" +
                "&exam_score_rate=10&exam_score=100" +
                "&questions_rate=10&questions_rate_full=100" +
                "&sign_num_rate=20&sign_num_full=100";
        String resp = newZjyHttp.post(saveAssessment, data);
        return resp;
    }

    static String createStuAssess = "https://user.icve.com.cn/zhzj/zhzjTeacher_createStuAssess.action";

    public static String getCreateStuAssess(String courseId, String classId) {
        //
        String data = "token=MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0Q=&classId=" + classId + "&courseId=" + courseId;
        String resp = newZjyHttp.post(createStuAssess, data);
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

    //用户其他信息
    static String ssoUser = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/ssoUser/user/info";

    public static String getSsoUser() {
        //need authorization
        String data = "{}";
        String resp = newZjyHttp.post(ssoUser, data);
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


    //课堂教学 根据courseId
    static String ClassroomByStudent = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/classroom/getClassroomByStudent";

    public static String getClassroomByStudent(String courseId) {
        String data = "{\"params\":{\"courseId\":\"" + courseId + "\"}}";
        String resp = newZjyHttp.post(ClassroomByStudent, data);
        return resp;
    }
    //今日课堂

    public static String getClassroomByDay(String day) {
        String data = "{\"params\":{\"startDate\":\"" + day + "\",\"current\":1,\"size\":20}}";
        String resp = newZjyHttp.post(ClassroomByStudent, data);
        return resp;
    }
    //全部课堂

    public static String getAllClassroom() {
        String data = "{\"params\":{\"current\":1,\"size\":100}}";
        String resp = newZjyHttp.post(ClassroomByStudent, data);
        return resp;
    }

    //课堂
    //课堂活动
    static String classActivityB = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/peClassroomActivity/teacher/classroomActivityList";
    static String classActivity = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/peClassroomActivity/student/classroomActivityList";

    public static String getClassActivity(String classroomId, String classroomTypeCode) {
        String data = "{\"params\":{\"classroomId\":\"" + classroomId + "\",\"classroomTypeCode\":" + classroomTypeCode + "}," +
                "\"page\":{\"curPage\":1,\"pageSize\":30,\"totalCount\":0,\"totalPage\":0}}";
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
                "\"page\":{\"curPage\":1,\"pageSize\":60,\"totalCount\":0,\"totalPage\":0}}";
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
    //
    private static String renewStuTopicInfo="https://spoc-classroom.icve.com.cn/classroom-teaching-api/discuss/renewStuTopicInfo";
    public static String getRenewStuTopicInfo(String activityId,String recordId, String content) {
        String data = "{\"params\":{\"id\":\"" + activityId + "\",\"recordId\":\""+recordId+"\"," +
                "\"content\":\"" + content + "\",\"imgPathList\":[]}}";
        //{"params":{"activityId":"9d6e868a292611ed94150c42a164ae44","content":"1111111","imgPathList":[]}}
        String resp = newZjyHttp.post(renewStuTopicInfo, data);
        return resp;
    }

    //课堂讨论list
    static String PrStuActivityRecordB = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/discuss/getPrStuActivityRecord";
    static String PrStuActivityRecord = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/discuss/getPrStuActivityRecordByStu";

    public static String getPrStuActivityRecord(String activityId) {
        String data = "{\"params\":{\"id\":\"" + activityId + "\"}," +
                "\"page\":{\"curPage\":1,\"pageSize\":60,\"totalCount\":0,\"totalPage\":0}}";
        String resp = newZjyHttp.post(PrStuActivityRecordB, data);
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
    static String signLearn = "https://course.icve.com.cn/learnspace/sign/signLearn.action";

    public static String getSignLearn(String courseId, String loginId) {
        String data = "sign=mobile&loginType=true&courseId=" + courseId +
                "&loginId=" + loginId + "&siteCode=zhzj&domain=spoc.icve.com.cn";
        newZjyHttp.addHeader("Host", "course.icve.com.cn");
        httpRespnose ret = newZjyHttp.get(signLearn, null, data);
        return JSONObject.toJSONString(ret.getHearderFileds());
    }

    //获取课件
    static String learnspace = "https://course.icve.com.cn/learnspace/learn/weixin/app/index.action";

    public static String getLearnspace(String courseId) {
        String data = "params.courseId=" + courseId + "___";
        String resp = newZjyHttp.get(learnspace, data);
        return resp;
    }

    //老师api获取课件全部
    static String ScormCourseItemByName = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/courseWare/getScormCourseItemByName";

    public static String getScormCourseItemByName(String courseId) {
        String data = "{\"params\":{\"courseId\":\"" + courseId + "\"}}";
        printHeader();
        String resp = newZjyHttp.post(ScormCourseItemByName, data);
        return resp;
    }

    //未知
    static String CourseNodeInfo = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/courseWare/getCourseNodeInfo";

    public static String getCourseNodeInfo(String courseId) {
        String data = "{\"params\":{\"courseId\":\"" + courseId + "\"}}";
        String resp = newZjyHttp.post(CourseNodeInfo, data);
        return resp;
    }


    //更新学习项目
    static String updateLearningItem = "https://course.icve.com.cn/learnspace/course/study/learningTime_updateLearningItem.action";

    public static String getUpdateLearningItem(String itemId) {
        String data = "itemId=" + itemId;
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

    //视频学习时长查询
    static String queryVideoLearnRecord = "https://course.icve.com.cn/learnspace/u/scorm/queryVideoLearnRecord.json";

    public static String getQueryVideoLearnRecord(String itemId, String courseId) {
        String data = "page.searchItem.videoTotalTime=00" +
                "&page.searchItem.itemId=d91162a1f33c4a918378f14f33833d6a" + itemId +
                "&page.searchItem.courseId=39e272199dab487ba6f8f76115cbfd2c" + courseId;
        String resp = newZjyHttp.post(queryVideoLearnRecord, data);
        return resp;
    }

    //视频时间保存
    static String saveLearningItem = "https://course.icve.com.cn/learnspace/u/scorm/saveLearningItem.json";

    public static String getSaveLearningItem(String videoTime, String courseId, String itemId) {
        String data = "entity.videoTime=" + videoTime + "&entity.courseId=" + courseId + "&" +
                "entity.itemId=" + itemId;
        String resp = newZjyHttp.post(saveLearningItem, data);
        return resp;
    }

    //视频记录进度
    static String saveVideoForBatch = "https://course.icve.com.cn/learnspace/u/scorm/saveVideoLearnDetailRecordForBatch.json";

    public static String getSaveVideoForBatch(String itemId, String courseId) {
        String data = "entity.itemRecords=[{\"itemId\":\"" + itemId + "\"," +
                "\"resourceTotalTime\":\"00:30:00\",\"studyRecords\":[{\"endTime\":\"00:30:00\"," +
                "\"saveStudyTimeFlag\":\"9999\",\"startTime\":\"00:00:01\"}],\"studyTime\":\"9999\"," +
                "\"videoIndex\":\"0\"}]&entity.courseId=" + courseId;
        String resp = newZjyHttp.post(saveVideoForBatch, data);
        return resp;
    }

    //检查项目
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

    //学习记录
    static String saveCourseItem = "https://course.icve.com.cn/learnspace/course/study/learningTime_saveCourseItemLearnRecord.action";

    public static String getSaveCourseItem(String courseId, String studyTime, String itemId) {
        String data = "courseId=" + courseId +
                "&studyTime=" + studyTime +
                "&itemId=" + itemId + "&recordType=0";
        String resp = newZjyHttp.post(saveCourseItem, data);
        return resp;
    }

    //正在学习的条目
    static String queryLearningItem = "https://course.icve.com.cn/learnspace/learn/learnCourseware/queryLearningItem.json";

    public static String getQueryLearningItem(String courseId) {
        String data = "params.courseId=" + courseId + "___";
        String resp = newZjyHttp.post(queryLearningItem, data);
        return resp;
    }


    //保存疑问
    static String saveQuestionInfo = "https://course.icve.com.cn/learnspace/learn/learnAnswer/saveQuestionInfo.json";

    public static String getSaveQuestionInfo(String courseId, String body) {
        String data = "body=" + body + "&courseId=" + courseId + "___&originalImgPaths=";
        String resp = newZjyHttp.post(saveQuestionInfo, data);
        return resp;
    }

    //保存笔记
    static String saveNote = "https://course.icve.com.cn/learnspace/learn/learnNote/saveNote.json";

    public static String getSaveNote(String courseId, String content, String title) {
        String data = "params.courseId=" + courseId + "___" +
                "&params.content=" + content + "&params.title=" + title;
        String resp = newZjyHttp.post(saveNote, data);
        return resp;
    }


    //考试 作业 测试
    static String exam_list_data = "https://course.icve.com.cn/learnspace/learn/weixin/common/exam/exam_list_data.action";

    //columnTypeId
    //81805198bddb4cdc84afbc3a1774f57
    //6049aaaac97845d1a8a790f397962b0a 在线作业
    public static String getExam_list_data(String courseId, String columnTypeId) {
        String data = "params.courseId=" + courseId +
                "&params.columnTypeId=" + columnTypeId +
                "&params.curPage=1&params.pageSize=100&params.filterType=all";
        String resp = newZjyHttp.post(saveNote, data);
        return resp;
    }

    //测验
    public static String getExam_list_data_t(String courseId) {
        return getExam_list_data(courseId, "");
    }

    //作业
    public static String getExam_list_data_w(String courseId) {
        return getExam_list_data(courseId, "");
    }

    //考试
    public static String getExam_list_data_e(String courseId) {
        return getExam_list_data(courseId, "");
    }


    //web api
    //获取 考试 作业 测试 详情
    private static String studentExam_getPaperStructure="https://spoc-exam.icve.com.cn/student/exam/studentExam_getPaperStructure.action";

    public static String getExamPaperStructure() {
        //{"retCode":"0","data":{"402883ab82fee62c0183204009171f77":{"isTemplatePaper":"0","courseNameAlias":"课程","courseName":"789456","examId":"402883ab82fee62c0183204009171f77","examScore":100,"structure":[{"id":"6d39be8eb4e447759ec62e8cf75d9389","name":"单选题","count":2,"total":100}],"paperId":"3960ed80fd384bf9a1ace9f7e23ddbd9"}}}
        String data = "examIds=402883ab82fee62c0183204009171f77";
        String resp = newZjyHttp.post(studentExam_getPaperStructure, data);
        return resp;
    }

    /*
    学生api

    个人进度详情
    get 请求
    https://course.icve.com.cn/learnspace/course/study/learnRecord_stuLearnRecord.action
    courseId=39e272199dab487ba6f8f76115cbfd2c___&userId=2w7jafiswazbrev468vb5q&isShowHistory=1&templateType=8

     获取 Signature
    <input type='hidden' name='_loginId' value='venomms' />
           <input type='hidden' name='apiKey' value='zhzj_platform' />
          <input type='hidden' name='_name' value='魏海旭' />
          <input type='hidden' name='Signature' value='MCwCFFPxxgHGhly8mrUZEnP/Tm0kTqS4AhQZeJCKF394bKzePcKVgeybaBRu7Q==' />

    get 请求
     https://course.icve.com.cn/learnspace/course/exam/courseExamAction_intoCourseExamList.action
     params.courseId=39e272199dab487ba6f8f76115cbfd2c___&params.columnTypeId=6049aaaac97845d1a8a790f397962b0a&params.examConfigTypeId=2

     */

    //疑似 USERSESSIONID

    static String enterMain = "https://spoc-exam.icve.com.cn/platformwebapi/testpaper/bankcontent_enterMain.action";

    public static String getEnterMain() {
        String data = "Signature=MC0CFQCUBcyPDTwxJYuYcyB83%2Fj8HBCyEAIUdY1WmRJq%2FK9%2Bef46NEFTeD0RE0w%3D&params.sourceDomain=spoc&_loginId=Debug&params.bankCode=Debug&apiKey=zhzj_platform&_name=%E5%90%B4%E4%BA%A6&params.bankName=%E6%88%91%E7%9A%84%E7%A9%BA%E9%97%B4&params.roleBusinessId=a603905470e2a5b8c13e96b579ef0dba&params.businessType=init&params.parentBankCode=persional_space&_roleCode=teacher&timestamp=1662690451047&";
        String resp = newZjyHttp.get(enterMain, data);
        return resp;
        /*
         * params.kckjToExamFlag=kckjToExamFlag&params.examConfigTypeId=2&params.examCodes=20220903232323367559%2C2022100116441943245&_loginId=venomms&apiKey=zhzj_platform&_name=%E9%AD%8F%E6%B5%B7%E6%97%AD&Signature=MCwCFFPxxgHGhly8mrUZEnP%2FTm0kTqS4AhQZeJCKF394bKzePcKVgeybaBRu7Q%3D%3D&params.configTypeId=6049aaaac97845d1a8a790f397962b0a&params.courseId=39e272199dab487ba6f8f76115cbfd2c&_roleCode=student&timestamp=1665067551683
         * https://spoc-exam.icve.com.cn/platformwebapi/student/exam/studentExam_studentExamInfoThird.action
         * */
    }

    //移动 api 老师相关

    //学生信息列表
    static String ClassTraineeList = "https://user.icve.com.cn/m/zhzjMobile_getClassTraineeList.action";

    public static String getClassTraineeList(String classId,String token) {
        //9b933b5e625e459ba9df0ea29e9e50ed
        String data = "pageSize=500&curPage=1&token=" +token+
                "&classId="+classId;
        String resp = newZjyHttp.post(ClassTraineeList, data);
        return resp;
    }

    //审核管理相关
    static String ClassTraineeAuditList = "https://user.icve.com.cn/m/zhzjMobile_getClassTraineeAuditList.action";

    public static String getClassTraineeAuditList(String classId,String token) {
        String data = "pageSize=500&curPage=1&token=" +token+
                "&classId="+classId;
        String resp = newZjyHttp.post(ClassTraineeAuditList, data);
        return resp;
    }

    //
    static String ClassAuditStatus = "https://user.icve.com.cn/m/zhzjMobile_getClassAuditStatus.action";

    public static String getClassAuditStatus(String classId,String token) {
        String data = "token=" +token+ "&classId="+classId;
        String resp = newZjyHttp.post(ClassAuditStatus, data);
        return resp;
    }

    //加课二维码
    static String QrCode = "https://user.icve.com.cn/patch/nlpx/getQrCode.action";

    public static String getQrCode() {
        String data = "url={\"code\":\"1001\",\"service\":\"enterClass\",\"param\":" +
                "{\"url\":\"qrCodeResult.html?classId=957639a938cc4e63b0953e132e0df096\"}}" +
                "&pic=";
        String resp = newZjyHttp.post(QrCode, data);
        return resp;
    }

    //添加课堂
    static String saveClassroom = "https://user.icve.com.cn/t/m/zhzjPeMobileCourse_saveClassroom.action";

    public static String getSaveClassroom(String courseId,String title,String tk) {

        String classId=Tool.md5(title);
        String data = "classId=&" +classId+
                "courseId="+courseId+"&title=" +title+
                "&startDate="+ Tool.getCurrentData() +"&token="+tk;
        String resp = newZjyHttp.post(saveClassroom, data);
        return resp;
    }


    //web 老师 api

    //删除课堂
    static String delClassroom = "https://user.icve.com.cn/zhzj/zhzjTeacher_delClassroom.action";

    public static String getDelClassroom(String Id) {
        String data = "id=" + Id +
                "&loginId=Debug" +
                "&token=aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA%3D%3D";
        String resp = newZjyHttp.post(delClassroom, data);
        return resp;
    }

    //学生成绩 总学习时长得分 资源完成情况得分 视频有效时长得分 离线作业得分 在线作业得分...
    static String StudentAssessList = "https://user.icve.com.cn/patch/zhzj/teacherStatistics_getStudentAssessList.action";

    public static String getStudentAssessList(String date, String classId) {
        String data = "date=2022-08-3+~+2022-9-3" +
                "&keyName=&curPage=1&pageSize=100&" +
                "classId=957639a938cc4e63b0953e132e0df096" +
                "&token=MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0=";
        String resp = newZjyHttp.post(StudentAssessList, data);
        return resp;
    }

    //学生统计 学习进度 在线时长 出勤率...
    static String StudentStatisticsList = "https://user.icve.com.cn/patch/zhzj/teacherStatistics_getStudentStatisticsList.action";

    public static String getStudentStatisticsList(String date, String classId) {
        String data = "data=info&date=2022-08-6+~+2022-9-6" +
                "&keyName=&curPage=1&pageSize=10&" +
                "classId=957639a938cc4e63b0953e132e0df096" +
                "&token=aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA%3D%3D";
        String resp = newZjyHttp.post(StudentStatisticsList, data);
        return resp;
    }

    //学生详情列表
    static String BySqlCode = "https://user.icve.com.cn/learning/u/userDefinedSql/getBySqlCode.json";

    public static String getBySqlCode(String courseId) {
        String data = "data=info&page.searchItem.queryId=getTeacherCLssTraineeListSPOC&" +
                "page.searchItem.courseId=39e272199dab487ba6f8f76115cbfd2c&" +
                "page.searchItem.keyname=&page.curPage=1&page.pageSize=10";
        String resp = newZjyHttp.post(BySqlCode, data);
        return resp;
    }

    //学习进度
    static String learnRecord = "https://course.icve.com.cn/learnspace/course/study/learnRecord_teacherCount.action?pager.pageSize=100&pager.curPage=1";

    public static String getLearnRecord(String courseId) {
        String data = "courseId=" + courseId + "&params.userName=&params.loginId=&params.classId=&params.label=";
        String resp = newZjyHttp.post(learnRecord, data);
        return resp;
    }

    // 进度详情
    static String stuLearnRecord = "https://course.icve.com.cn/learnspace/course/study/learnRecord_stuLearnRecord.action";

    public static String getStuLearnRecord(String courseId, String userId) {
        String data = "courseId=" + courseId + "&userId=" + userId;
        String resp = newZjyHttp.get(stuLearnRecord, data);
        return resp;
    }

    // 设置课程公告
    static String saveCourseNotice = "https://course.icve.com.cn/learnspace/learn/learnCourseNotice/saveCourseNotice.json";

    public static String getSaveCourseNotice(String courseId) {
        String data = "params.courseId=39e272199dab487ba6f8f76115cbfd2c___&params.title=11111" +
                "&params.note=%3Cp%3E11111111%3C%2Fp%3E&params.isTop=true";
        String resp = newZjyHttp.post(saveCourseNotice, data);
        return resp;
    }

    //老师课件设计
    static String TeachingDesign = "https://course.icve.com.cn/learnspace/course/design/courseTeachingDesign_intoTeachingDesign.action";

    //https://course.icve.com.cn/learnspace/course/design/courseTeachingDesign_intoTeachingDesign.action?courseId=39e272199dab487ba6f8f76115cbfd2c&editState=1
    public static String getTeachingDesign() {
        String data = "courseId=39e272199dab487ba6f8f76115cbfd2c&params.cloudRoleId=&" +
                "params.cloudMetaId=&params.examRoleId=";
        String resp = newZjyHttp.get(TeachingDesign, data);
        return resp;
    }

    //查询
    static String queryChapterItem = "https://course.icve.com.cn/learnspace/course/design/designCourseItem_queryChapterItem.action";

    public static String getQueryChapterItem() {
        String data = "courseId=39e272199dab487ba6f8f76115cbfd2c";
        String resp = newZjyHttp.get(queryChapterItem, data);
        return resp;
    }

    // 获取查询全部课件
    static String queryChildItem = "https://course.icve.com.cn/learnspace/course/design/designCourseItem_queryChildItem.action";

    public static String getQueryChildItem() {
        String data = "itemId=99ffe49b4da24327b98c6f6409868ddb&courseId=39e272199dab487ba6f8f76115cbfd2c";
        String resp = newZjyHttp.post(queryChildItem, data);
        return resp;
    }

    // 删除课件

    static String delItemById = "https://course.icve.com.cn/learnspace/course/design/designCourseItem_delItemById.action";

    public static String getDelItemById() {
        String data = "itemId=03fa7ab6e8564cc9baba3f4d90ad5589&courseId=39e272199dab487ba6f8f76115cbfd2c";
        String resp = newZjyHttp.post(delItemById, data);
        return resp;
    }

    // 添加节
    static String addSectionCourseItem = "https://course.icve.com.cn/learnspace/course/design/designCourseItem_addSectionCourseItem.action";

    public static String getAddSectionCourseItem() {
        String data = "courseId=39e272199dab487ba6f8f76115cbfd2c&parentId=5fe19e246d524044b8af39a2afda14e3&" +
                "itemTitle=%E6%96%B0%E8%AE%B2%E8%AF%BE&sectionIndex=3";
        String resp = newZjyHttp.post(addSectionCourseItem, data);
        return resp;
    }


    //web登陆测试
    static String findUser = "https://user.icve.com.cn/patch/nlpx/findUser.action";
    static String auth = "https://spoc-sso.icve.com.cn/auth";

    //static String findUser = "https://user.icve.com.cn/patch/nlpx/findUser.action";
    public static String webLogin() {
        String data = "loginId=Debug";
        newZjyHttp.addCookie("whatysns=b9db789f2acaccfcdbfe216e44e16a70");
        httpRespnose resp = newZjyHttp.post(findUser, data, null, null);
        newZjyHttp.addCookie(resp.getSetCookie());
        //newZjyHttp.addHeader("Host", "spoc-sso.icve.com.cn");
        newZjyHttp.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36 Edg/105.0.1343.27");
        data = "siteCode=zhzj" +
                "&errorPage=https%3A%2F%2Fuser.icve.com.cn%2Fsso%2FssoLogin_loginError.action%3FbackURL%3D%2Findex.jsp" +
                "&password=af30447f10d38ce427778f5fc1fc31ea&username=Debug";
        printHeader();
        resp = newZjyHttp.post(auth, data, null, null);
        //print(resp.getResp());
        resp = newZjyHttp.post(resp.getLocation(), null, null, null);
        printHeader(resp.getHearderFileds());

        return "";
    }

    //token转换
    static String generateEnterCloudUrl = "https://user.icve.com.cn/zhzj/zhzjTeacher_generateEnterCloudUrl.action";

    public static String getGenerateEnterCloudUrl(String token) {
        String data = "token=aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA%3D%3D" + token;
        String resp = newZjyHttp.post(generateEnterCloudUrl, data);
        return resp;
    }


    //老师api 考试等
    static String courseExamList = "https://user.icve.com.cn/zhzj/zhzjTeacher_courseExamList.action";

    public static String getCourseExamList(String token) {
        String data = "token=aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA%3D%3D" +
                "&searchName=&pageNum=1&type=随堂测验" +
                "&examConfigTypeId=3&pageSize=100";
        //在线作业
        //token=aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA%3D%3D&searchName=&pageNum=1&type=在线作业&examConfigTypeId=2&pageSize=5
        //课程考试
        //token=aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA%3D%3D&searchName=&pageNum=1&type=课程考试&examConfigTypeId=1&pageSize=5
        String resp = newZjyHttp.post(courseExamList, data);
        return resp;
    }



    //未阅的作业 考试 测验
    static String UnCheckPaperList = "https://spoc-exam.icve.com.cn/teacher/exampaper/papercheck_queryUnCheckPaperList.action?pager.pageSize=100&pager.curPage=1&pager.searchTotalSize=true";

    public static String getUnCheckPaperList() {
        String data = "fetchTotalSize=true&" +
                "params.examId=4028824d82feea2d018303f382b2217f&" +
                "params.classId=&params.searchKeyWord=&" +
                "pager.condition.paperOrUserName=&" +
                "pager.condition.ipAddress=&" +
                "pager.condition.examId=4028824d82feea2d018303f382b2217f&" +
                "pager.condition.classId=";
        String resp = newZjyHttp.post(UnCheckPaperList, data);
        return resp;
    }

    //已阅的作业 考试 测验
    static String CheckedPaperList = "https://spoc-exam.icve.com.cn/teacher/exampaper/papercheck_queryCheckedPaperList.action?pager.pageSize=100&pager.curPage=1&pager.searchTotalSize=true";

    public static String getCheckedPaperList() {
        String data = "fetchTotalSize=true&" +
                "params.examId=4028824d82feea2d018303f382b2217f&" +
                "params.classId=&pager.condition.examId=4028824d82feea2d018303f382b2217f&" +
                "pager.condition.classId=&" +
                "pager.condition.paperOrUserName=&" +
                "pager.condition.paperStatus=-1";
        String resp = newZjyHttp.post(CheckedPaperList, data);
        return resp;
    }


    //作业 考试 测验的成绩列表
    static String ScorePaperList = "https://spoc-exam.icve.com.cn/teacher/exampaper/papercheck_queryScorePaperList.action?pager.pageSize=100&pager.curPage=1&pager.searchTotalSize=true";

    public static String getScorePaperList() {
        String data = "fetchTotalSize=true&" +
                "params.examId=4028824d82feea2d018303f382b2217f&" +
                "params.classId=&pager.condition.userNameOrLoginId=&" +
                "pager.condition.checkUserName=";
        String resp = newZjyHttp.post(ScorePaperList, data);
        return resp;
    }

    //已做题的id
    static String RecordPaper = "https://spoc-exam.icve.com.cn/student/exam/examrecord_getRecordPaperStructure.action";

    public static String getRecordPaper() {
        String data = "recordId=4e283d8c6d9c474188f0629ef9a8e39a";
        String resp = newZjyHttp.post(RecordPaper, data);
        return resp;
    }

    //已做题的答案解析
    static String RecordContent = "https://spoc-exam.icve.com.cn/student/exam/examrecord_getRecordContentByPage.action";

    public static String getRecordContent() {
        String data = "recordId=4e283d8c6d9c474188f0629ef9a8e39a&" +
                "examBatchId=4028824d82feea2d018303f382b2217f&" +
                "contentIds=6d39be8eb4e447759ec62e8cf75d9389%2C11540cffebd64092940de3701ddcaa69%2C14ef32b18a734650b4aca25836504412&" +
                "params.monitor=";
        String resp = newZjyHttp.post(RecordContent, data);
        return resp;
    }

    //答案
    static String PaperStructureForPreview = "https://spoc-exam.icve.com.cn/testpaper/paper_getPaperStructureForPreview.action";

    public static String getPaperStructureForPreview(String paperId) {
        String data = "params.paperId=2d66ef24b663465089eafd7d48039da9";
        String ck = "USERSESSIONID=402883e682f4d8ea0182f7123f521677#interface#batchCode#attachData";
        newZjyHttp.addCookie(ck);
        newZjyHttp.addHeader("Host", "spoc-exam.icve.com.cn");
        //printHeader();
        String resp = newZjyHttp.post(PaperStructureForPreview, data);
        return resp;
    }

    //答案
    static String PaperStructure = "https://spoc-exam.icve.com.cn/exam/statistics/examPaperContentStatistics_getPaperStructure.actionn";

    public static String getPaperStructure() {
        String data = "paperId=2d66ef24b663465089eafd7d48039da9";
        String ck = "USERSESSIONID=402883e682f4d8ea0182f7123f521677#interface#batchCode#attachData;";

        // newZjyApi.addCookie(ck);
        newZjyHttp.addHeader("Host", "spoc-exam.icve.com.cn");
        //printHeader();
        String resp = newZjyHttp.post(PaperStructure, data);
        return resp;
    }

    //答案
    static String ExamPaperStatisticsDetail = "https://spoc-exam.icve.com.cn/exam/statistics/examPaperContentStatistics_getExamPaperStatisticsDetail.action";

    public static String getExamPaperStatisticsDetail() {
        String data = "examId=4028824e8379229701837e77e40d2faf&" +
                "paperId=2d66ef24b663465089eafd7d48039da9";
        String ck = "USERSESSIONID=402883e682f4d8ea0182f7123f521677#interface#batchCode#attachData;";
        //USERSESSIONID=402883e682f4d8ea0182f7123f521677#interface#batchCode#attachData

        //newZjyApi.addCookie(ck);
        newZjyHttp.addHeader("Host", "spoc-exam.icve.com.cn");
        //printHeader();
        String resp = newZjyHttp.post(ExamPaperStatisticsDetail, data);
        return resp;
    }


    //重批改分
    static String saveScore = "https://spoc-exam.icve.com.cn/teacher/exampaper/papercheck_saveScoreAndFinishCheck.action";

    public static String getSaveScore() {
        String data = "recordId=4e283d8c6d9c474188f0629ef9a8e39a&" +
                "score=4243f48390cf49b29981154fced1dd37%4050&" +
                "score=644ace139b4e4f84b7690c6c7e5a3311%4050";
        String resp = newZjyHttp.post(saveScore, data);
        return resp;
    }

    //题库答案
    static String questionManage = "https://spoc-exam.icve.com.cn/question/questionManage_getQuestionList.action?pager.pageSize=100&pager.curPage=1&pager.searchTotalSize=true";

    public static String getQuestionManage(String bankId) {
        String data = "params.qTypeIds=&params.qTitle=&params.attrValue=%7B%7D&" +
                "params.bankId=217343996a7546eb9a2d47801791adb3" +
                "&params.tags=&params.courseId=";
        String resp = newZjyHttp.post(questionManage, data);
        return resp;
    }
}

