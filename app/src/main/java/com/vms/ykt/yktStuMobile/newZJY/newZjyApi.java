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
        //printHeader();

        //System.out.println(JSONObject.toJSON(newZjyHttp.getHeader()));
    }

    // 重置请求头
    public static void restHeader() {
        newZjyHttp.setHeader(yktHeaders.getNewZjyMHeader());
    }

    //更新authorization
    public static void upAuthorization(String authorization) {
        newZjyHttp.addHeader("authorization", "Bearer " + authorization);
        //printHeader();
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

    public static String getSaveAssessment(String courseId, String classId
            ,String learn_time,String learn_time_full, String node_rate,String node_rate_full
            ,String effective_time,String effective_time_full,String test_rate,String test_rate_full
            ,String work_rate ,String work_rate_full ,String pop_quiz_rate ,String pop_quiz_full
            ,String exam_score_rate,String exam_score,String questions_rate,String questions_rate_full
            ,String sign_num_rate ,String sign_num_full ,String discuss_rate ,String discuss_rate_full
            ,String groupPK_rate,String groupPK_rate_full) {
        String data = "token=MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0Q=&courseId=" + courseId +
                "&classId=" + classId +
                "&total_learn_time_rate="+learn_time+"&total_learn_time_full="+learn_time_full +
                "&node_rate="+node_rate+"&node_rate_full=" +node_rate_full+
                "&effective_time_rate="+effective_time+"&effective_time_full=" +effective_time_full+
                "&test_rate="+test_rate+"&test_rate_full=" +test_rate_full+
                "0&work_rate="+work_rate+"&work_rate_full=" +work_rate_full+
                "&pop_quiz_rate="+pop_quiz_rate+"&pop_quiz_full=" +pop_quiz_full+
                "&exam_score_rate="+exam_score_rate+"&exam_score="+exam_score +
                "&questions_rate="+questions_rate+"&questions_rate_full=" +questions_rate_full+
                "&sign_num_rate="+sign_num_rate+"&sign_num_full=" +sign_num_full+
                "&discuss_rate="+discuss_rate+"&discuss_rate_full=" +discuss_rate_full+
                "&groupPK_rate="+groupPK_rate+"&groupPK_rate_full="+groupPK_rate_full;
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


    ///Usersessionidm

    static String Usersessionidm="https://course.icve.com.cn/learnspace/course/exam/weixinCourseExamAction_examListGoExam.action";

    public static String getUsersessionidm(String courseId,String examCode){
        String data="params.appIntoType=examInfoConfirm" +
                "&params.examCode=" +examCode+
                "&params.courseId=" +courseId+
                "&params.intoDevice=weixin&params.filterType=all";

        httpRespnose vHttpRespnose =  newZjyHttp.get(Usersessionidm,null,data);
        //printHeader(vHttpRespnose.getHearderFileds());
        String Location=vHttpRespnose.getLocation();
        //System.out.println(Location);
        vHttpRespnose= newZjyHttp.get(Location,null,null);
       // String ck=vHttpRespnose.getSetCookie();
        String USERSESSIONID=vHttpRespnose.getCookieV("USERSESSIONID");

        return USERSESSIONID;
    };

    public static void upUsersessionid(String Usersessionid){
        newZjyHttp.upCookie(Usersessionid);
    }

    //考试 作业 测试
    static String exam_list_data = "https://course.icve.com.cn/learnspace/learn/weixin/common/exam/exam_list_data.action";

    //columnTypeId
    //81805198bddb4cdc84afbc3a1774f576 //考试
    //6049aaaac97845d1a8a790f397962b0a 在线作业
    //ed134338b38640aa89cd34497437a97d 测试作业
    public static String getExam_list_data(String courseId, String columnTypeId) {
        String data = "params.courseId=" + courseId +
                "&params.columnTypeId=" + columnTypeId +
                "&params.curPage=1&params.pageSize=100&params.filterType=all";
        String resp = newZjyHttp.post(exam_list_data, data);
        return resp;
    }

    //测验
    public static String getExam_list_data_t(String courseId) {
        return getExam_list_data(courseId, "ed134338b38640aa89cd34497437a97d");
    }

    //作业
    public static String getExam_list_data_w(String courseId) {
        return getExam_list_data(courseId, "6049aaaac97845d1a8a790f397962b0a");
    }

    //考试
    public static String getExam_list_data_e(String courseId) {
        return getExam_list_data(courseId, "81805198bddb4cdc84afbc3a1774f576");
    }

    //附件作业
    static String homework="https://course.icve.com.cn/homework-api/mobile/student/homework/list";
    public static String getHomework(String courseId){
        String data = "{\"params\":{\"groupId\":\""+courseId+"\"," +
                "\"columnId\":\"1fb6d397336642eca759b285074fb63e\"}," +
                "\"page\":{\"curPage\":1,\"pageSize\":100,\"totalCount\":0,\"totalPage\":0}}";
        String resp = newZjyHttp.post(homework, data);
        return resp;
    }


    //获取 考试 作业 测试 的id详情
    static String ConfirmPagePaperStructure="https://spoc-exam.icve.com.cn/student/exam/studentExam_getConfirmPagePaperStructure.action";

    public static String getConfirmPagePaperStructure(String batchId){
        String data="params.batchId="+batchId;
        String resp = newZjyHttp.post(ConfirmPagePaperStructure,data);
        return resp;
    }

    //web api
    // 学生api

    //获取课件
    static String courseware_index="https://course.icve.com.cn/learnspace/learn/learn/templateeight/courseware_index.action";
    public static String getCourseware_index(String courseId){
        String data="params.courseId="+courseId+"___";
        String resp = newZjyHttp.get(courseware_index,data);
        return resp;
    }

    // 考试 作业 测试

    static String studentExam_studentExamListData="https://spoc-exam.icve.com.cn/student/exam/studentExam_studentExamListData.action?pager.pageSize=100&pager.curPage=1&pager.searchTotalSize=true";
    public static String getExam_list_dataw(String courseId,String configTypeId){
        String data="params.examName=&params.examType=" +
                "&params.examConfigTypeId=2" +
                "&params.courseId="+courseId +
                "&params.configTypeId="+configTypeId;


        String resp = newZjyHttp.post(studentExam_studentExamListData,data);

        return resp;
    }

    //测验
    public static String getExam_list_data_tw(String courseId) {
        return getExam_list_dataw(courseId, "ed134338b38640aa89cd34497437a97d");
    }

    //作业
    public static String getExam_list_data_ww(String courseId) {
        return getExam_list_dataw(courseId, "6049aaaac97845d1a8a790f397962b0a");
    }

    //考试
    public static String getExam_list_data_ew(String courseId) {
        return getExam_list_dataw(courseId, "81805198bddb4cdc84afbc3a1774f576");
    }


    //附件
    static String homeworkw="https://course.icve.com.cn/homework-api/student/homework/list";

    public static String getHomeworkw(String courseId){
        String data = "{\"params\":{\"groupId\":\""+courseId+"\"," +
                "\"columnId\":\"1fb6d397336642eca759b285074fb63e\"}}";
        String resp = newZjyHttp.post(homeworkw, data);
        return resp;
    }


    //获取 考试 作业 测试 的id详情
    private static String studentExam_getPaperStructure="https://spoc-exam.icve.com.cn/student/exam/studentExam_getPaperStructure.action";

    public static String getExamPaperStructure(String examIds) {

        //{"retCode":"0","data":{"402883ab82fee62c0183204009171f77":{"isTemplatePaper":"0","courseNameAlias":"课程","courseName":"789456","examId":"402883ab82fee62c0183204009171f77","examScore":100,"structure":[{"id":"6d39be8eb4e447759ec62e8cf75d9389","name":"单选题","count":2,"total":100}],"paperId":"3960ed80fd384bf9a1ace9f7e23ddbd9"}}}
        String data = "examIds="+examIds;
        String resp = newZjyHttp.post(studentExam_getPaperStructure, data);
        return resp;
    }

   //recoid获取
    static String examflow_index="https://spoc-exam.icve.com.cn/exam/examflow_index.action";
    public static String getExamflow_index(String batchId){
        String data="batchId="+batchId;
        String resp = newZjyHttp.get(examflow_index, data);
        return resp;
    }


    //学习状况
    static String zhzjStudent_checkAssess="https://user.icve.com.cn/zhzj/zhzjStudent_checkAssess.action";
    public static String getZhzjStudent_checkAssess(){
        String data = "token=MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0Q%3D&classId=957639a938cc4e63b0953e132e0df096&loginId=venomms";
        String resp = newZjyHttp.post(studentExam_getPaperStructure, data);
        return resp;
    }

    //进度详情
    static String learnRecord_stuLearnRecord="https://course.icve.com.cn/learnspace/course/study/learnRecord_stuLearnRecord.action";
    public static String getLearnRecord_stuLearnRecord(String courseId,String userId){

        String data="courseId="+courseId+"___" +
                "&userId=" +userId+
                "&isShowHistory=1&templateType=8";
        String resp = newZjyHttp.get(learnRecord_stuLearnRecord, data);
        return resp;
    }

    public static void upUseridHd(){

        String host = "spoc-exam.icve.com.cn";
        String Origin = "https://course.icve.com.cn";
        String defaultAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42";
        String ctype = "application/x-www-form-urlencoded";

        HashMap<String, Object> header = new HashMap<>();
        header.put("Connection", "Keep-Alive");
        header.put("Content-Type", ctype);
        header.put("Referer ","https://course.icve.com.cn/");
        header.put("Origin", Origin);
        header.put("User-Agent", defaultAgent);
        header.put("sec-ch-ua-platform", "Windows");
        header.put("Upgrade-Insecure-Requests", "1");
        // header.put("Sec-Fetch-Dest", "empty");
        header.put("Sec-Fetch-Mode", "navigate");
        header.put("sec-ch-ua-mobile", "?0");
        //header.put("Accept", "application/json, text/javascript, */*; q=0.01");
        // header.put("Accept-Encoding", "gzip, deflate, br");
        //header.put("X-Requested-With", "XMLHttpRequest");
        header.put("Host", host);
        header.put("sec-ch-ua","\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\", \"Microsoft Edge\";v=\"108\"");
        newZjyHttp.addHeader(header);
    }

    //USERSESSIONID
    static String usersessionid="https://spoc-exam.icve.com.cn/platformwebapi/student/exam/studentExam_studentExamInfoThird.action";
    //老师api
    static String usersessionidt="https://spoc-exam.icve.com.cn/platformwebapi/college/exam/examBatch_intoExamListThird.action";

    public static String getUsersessionidw(){

        String data="params.kckjToExamFlag=kckjToExamFlag" +
                "&params.examCodes=" +
                "&apiKey=zhzj_platform&_name=%E9%AD%8F%E6%B5%B7%E6%97%AD" +
                "&params.configTypeId=6049aaaac97845d1a8a790f397962b0a" +
                "&params.zhzjAlias=2034060125&params.examConfigTypeId=2" +
                "&_loginId=venomms" +
                "&Signature=MCwCFACqD5kSlZgNsuN1lm1VDbMC%2BsTEAhQICPiFnI15s0MtiBZKG9zOtuZ8Ng%3D%3D" +
                "&params.courseId=39e272199dab487ba6f8f76115cbfd2c" +
                "&_roleCode=student" +
                "&timestamp="+System.currentTimeMillis()+"&";

        String ck="qBankId3960ed80fd384bf9a1ace9f7e23ddbd9=fc4103d4ff60426fb2fa8a9cff0eac7f; %26%2Fcollege%2Fexam%2FexamBatch_loadExamListData.action%262=null%26%26%2639e272199dab487ba6f8f76115cbfd2c%261%2610; %26%2Fcollege%2Fexam%2FexamBatch_loadExamListData.action%261=null%26%26%2639e272199dab487ba6f8f76115cbfd2c%261%2610; %26%2Fcollege%2Fexam%2FexamBatch_loadExamListData.action%263=null%26%26%2639e272199dab487ba6f8f76115cbfd2c%261%2610; qBankIdbcd8841d680946ddb1387e06cc61af6e=85c246a67da74212b0258efa12413552; ORSESSIONID=null; aliyungf_tc=7f6580f4828d7e0b46259aaf48766c14dd5bce820bac1c1cd46f3c88fa6e90b5; token=3dbd5841-caa0-4dec-ae96-991784191eb1; SERVERID=f7f48d88576f2594f65fbfc6df2be025|1670599271|1670594510; JSESSIONID=7FA9DD01EBDD4F045BAEC453FA6E943D; JSESSIONID=7FA9DD01EBDD4F045BAEC453FA6E943D; USERSESSIONID=402883ab82fee62c018307a26e3e1f1d#interface#20221110213841312314#attachData";

        newZjyHttp.addCookie(ck);


        upUseridHd();

        //printHeader();
        httpRespnose resp = newZjyHttp.post(usersessionid,data,null,null);

        newZjyHttp.addHeader(yktHeaders.getNewZjyMHeader());

        printHeader(resp.getHearderFileds());


        String ret=resp.getSetCookie();
        return ret;


    }

    static String courseExamAction_stuIntoExamConfirm="https://course.icve.com.cn/learnspace/course/exam/courseExamAction_stuIntoExamConfirm.json";
    public static String getUsersessionidw2(){
        String data ="params.classId=" +
                "&params.courseId=a41689a28e5442a39410e348fce1bd71___" +
                "&params.parentId=" +
                "&params.itemId=&params.audit=" +
                "&params.templateType=8&params.templateStyleType=0" +
                "&params.kckjWinHeight=529&_t=2022-12-11%2000:01:31";

        String ck="token=3dbd5841-caa0-4dec-ae96-991784191eb1; UNTYXLCOOKIE=\"dXNlci5pY3ZlLmNvbS5jbnx8NjUxN2ZkMjhlNTQxNWIxNzdjMzEwYzY0OTFiOGU0YTZ8fHZlbm9tbXN8fHpoemo=\"";
        newZjyHttp.addCookie(ck);
        //printHeader();

        upUseridHd();

        httpRespnose resp = newZjyHttp.get(courseExamAction_stuIntoExamConfirm,null,data);

        printHeader(resp.getHearderFileds());

        newZjyHttp.addHeader(yktHeaders.getNewZjyMHeader());
        return "";
    }

    /**个人进度详情
    get 请求
    https://course.icve.com.cn/learnspace/course/study/learnRecord_stuLearnRecord.action
    courseId=39e272199dab487ba6f8f76115cbfd2c___&userId=2w7jafiswazbrev468vb5q&isShowHistory=1&templateType=8


     获取 Signature
    <input type='hidden' name='_loginId' value='venomms' />
           <input type='hidden' name='apiKey' value='zhzj_platform' />
          <input type='hidden' name='_name' value='魏海旭' />
          <input type='hidden' name='Signature' value='MCwCFFPxxgHGhly8mrUZEnP/Tm0kTqS4AhQZeJCKF394bKzePcKVgeybaBRu7Q==' />
     */

    //获取 Signature name
    static String courseExamAction="https://course.icve.com.cn/learnspace/course/exam/courseExamAction_intoCourseExamList.action";
    public static String getCourseExamAction(String courseId,String columnTypeId){
        String data="params.courseId="+courseId+"___" +
                "&params.columnTypeId="+columnTypeId+
                "&params.examConfigTypeId=20";
        String resp = newZjyHttp.get(courseware_index,data);
        return resp;
    }


    //web登陆测试
    static String findUser = "https://user.icve.com.cn/patch/nlpx/findUser.action";
    static String auth = "https://spoc-sso.icve.com.cn/auth";
    static String userLogin="https://sso.icve.com.cn/data/userLogin";
    static String login_logout="https://user.icve.com.cn/learning/sso/login_logout.action";
    static String cms="https://user.icve.com.cn/cms/";

    public static String webLogin() {
        String data = "{\"userName\":\"venomms\",\"password\":\"Poor2579988653\",\"type\":1}";
        //newZjyHttp.addCookie("whatysns=b9db789f2acaccfcdbfe216e44e16a70");
        httpRespnose resp = newZjyHttp.post(userLogin, data, null, null);
        newZjyHttp.addCookie(resp.getSetCookie());
        printHeader();
        String tk = resp.getCookieV("token");
        System.out.println(tk);
        data="token="+tk;
        resp = newZjyHttp.get(login_logout, null,data);
        newZjyHttp.addCookie(resp.getSetCookie());
        printHeader();
        String rt= newZjyHttp.get(cms);
        System.out.println(rt);
        //newZjyHttp.addHeader("Host", "spoc-sso.icve.com.cn");
        //newZjyHttp.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36 Edg/105.0.1343.27");
        //data = "";
        //printHeader();
       // resp = newZjyHttp.post(auth, data, null, null);
        //print(resp.getResp());
        //resp = newZjyHttp.post(resp.getLocation(), null, null, null);
        //printHeader(resp.getHearderFileds());

        return "";
    }


    //疑似 USERSESSIONID

    static String enterMain = "https://spoc-exam.icve.com.cn/platformwebapi/testpaper/bankcontent_enterMain.action";

    public static String getEnterMain() {
        String data = "Signature=MC0CFQCUBcyPDTwxJYuYcyB83%2Fj8HBCyEAIUdY1WmRJq%2FK9%2Bef46NEFTeD0RE0w%3D&params.sourceDomain=spoc&_loginId=Debug&params.bankCode=Debug&apiKey=zhzj_platform&_name=%E5%90%B4%E4%BA%A6&params.bankName=%E6%88%91%E7%9A%84%E7%A9%BA%E9%97%B4&params.roleBusinessId=a603905470e2a5b8c13e96b579ef0dba&params.businessType=init&params.parentBankCode=persional_space&_roleCode=teacher&timestamp=1662690451047&";
        String resp = newZjyHttp.get(enterMain, data);
        return resp;
        /*
         *
         * https://spoc-exam.icve.com.cn/platformwebapi/student/exam/studentExam_studentExamInfoThird.action
         * */
    }


    private static String ExamUSERSESSIONID="https://spoc-exam.icve.com.cn/platformwebapi/student/exam/studentExam_studentExamInfoThird.action";
    public static String ExamUSERSESSIONID(){
        String data="params.kckjToExamFlag=kckjToExamFlag&params.examConfigTypeId=2&params.examCodes=20220903232323367559%2C2022100116441943245&_loginId=venomms&apiKey=zhzj_platform&_name=%E9%AD%8F%E6%B5%B7%E6%97%AD&Signature=MCwCFFPxxgHGhly8mrUZEnP%2FTm0kTqS4AhQZeJCKF394bKzePcKVgeybaBRu7Q%3D%3D&params.configTypeId=6049aaaac97845d1a8a790f397962b0a&params.courseId=39e272199dab487ba6f8f76115cbfd2c&_roleCode=student&timestamp=1665067551683";
        httpRespnose resp = newZjyHttp.post(ExamUSERSESSIONID, data, null, null);
        printHeader(resp.getHearderFileds());
        return "";
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

    public static String getQrCode(String classId) {
        String data = "url={\"code\":\"1001\",\"service\":\"enterClass\",\"param\":" +
                "{\"url\":\"qrCodeResult.html?classId="+classId+"\"}}" +
                "&pic=";
        String resp = newZjyHttp.post(QrCode, data);
        return resp;
    }

    //添加课堂
    static String saveClassroom = "https://user.icve.com.cn/t/m/zhzjPeMobileCourse_saveClassroom.action";

    public static String getSaveClassroom(String courseId,String title,String tk) {

        String classId=Tool.md5(title);
        String data = "classId=" +classId+
                "&courseId="+courseId+"&title=" +title+
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
                "page.searchItem.courseId="+courseId+"&" +
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

    public static String getSaveCourseNotice(String courseId,String title,String note) {
        String data = "params.courseId="+courseId+"___&params.title=" +title+
                "&params.note="+note+"&params.isTop=true";
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
    private static String PaperStructureForPreview = "https://spoc-exam.icve.com.cn/testpaper/paper_getPaperStructureForPreview.action";

    public static String getPaperStructureForPreview(String paperId) {
        String data = "params.paperId="+paperId;
        String resp = newZjyHttp.post(PaperStructureForPreview, data);
        return resp;
    }

    //答案
    static String PaperStructure = "https://spoc-exam.icve.com.cn/exam/statistics/examPaperContentStatistics_getPaperStructure.action";

    public static String getPaperStructure(String paperId) {
        String data = "paperId="+ paperId;
        //newZjyHttp.addCookie("USERSESSIONID=402883e682f4d8ea0182f7123f521677#interface#batchCode#attachData;");
        String resp = newZjyHttp.post(PaperStructure, data);
        return resp;
    }

    //
    static String ExamPaperStatisticsDetail = "https://spoc-exam.icve.com.cn/exam/statistics/examPaperContentStatistics_getExamPaperStatisticsDetail.action";

    public static String getExamPaperStatisticsDetail(String examId,String paperId) {
        String data = "examId="+examId +
                "&paperId="+paperId;
        String resp = newZjyHttp.post(ExamPaperStatisticsDetail, data);
        return resp;
    }


    //考试 作业 测试 重批改分
    static String saveScore = "https://spoc-exam.icve.com.cn/teacher/exampaper/papercheck_saveScoreAndFinishCheck.action";

    public static String getSaveScore() {
        String data = "recordId=4e283d8c6d9c474188f0629ef9a8e39a&" +
                "score=4243f48390cf49b29981154fced1dd37%4050&" +
                "score=644ace139b4e4f84b7690c6c7e5a3311%4050";
        String resp = newZjyHttp.post(saveScore, data);
        return resp;
    }

    //附件作业改分
    static String saveCheckHomework="https://course.icve.com.cn/homework-api/teacher/saveCheckHomework";
    public static String getSaveCheckHomework(){
        String data="\n" +
                "{\"params\":{\"isRecommend\":\"0\",\"isTeacherRecommend\":\"0\"," +
                "\"homeworkStuId\":\"402883e6837a403f01837dab80de28ee\"," +
                "\"comments\":\"\",\"homeworkScore\":100}}";
        String resp = newZjyHttp.post(saveCheckHomework, data);
        return resp;

    }

    //修改考试 作业 测试 的时间


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

