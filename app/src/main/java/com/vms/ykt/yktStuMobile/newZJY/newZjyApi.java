package com.vms.ykt.yktStuMobile.newZJY;


import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.Util.httpRespnose;
import com.vms.ykt.yktUtil.yktHeaders;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class newZjyApi {


    // https://sso.icve.com.cn/api/captcha/get
    //{"captchaType":"blockPuzzle","clientUid":null,"ts":1671109451781}

    //

    public static String getUserLogin(String userName, String password) {
        String data = "{\"type\":1,\"userName\":\"" + userName + "\"," +
                "\"password\":\"" + password + "\"}";
        httpRespnose vHttpRespnose = newZjyHttp.post(userLogin, data, null, null);
        String resp = vHttpRespnose.getResp();
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


    //更新Content-Type
    public static void upContent2() {

        newZjyHttp.addHeader("Content-Type", "application/json;charset=UTF-8");

        //newZjyHttp.addHeader("Host", "spoc-classroom.icve.com.cn");
        //System.out.println(JSONObject.toJSON(newZjyHttp.getHeader()));
    }


    //更新Content-Type
    public static void upContent1() {

        newZjyHttp.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        // newZjyHttp.addHeader("Host", "user.icve.com.cn");
        //printHeader();
        //System.out.println(JSONObject.toJSON(newZjyHttp.getHeader()));
    }


    //更新authorization
    public static void upAuthorization(String authorization) {
        //newZjyHttp.addHeader("authorization", "Bearer " + authorization);
        newZjyHttp.addHeader("Authorization", "bearer " + authorization);
        //printHeader();
    }

    //更新upAuthorizationHomework
    public static void upAuthorizationHomework(String authorization) {
        newZjyHttp.addHeader("Authorization", "bearer " + authorization);
        //printHeader();
    }

    //更新UNTYXLCOOKIE
    public static void upUNTYXLCOOKIE(String UNTYXLCOOKIE) {
        newZjyHttp.upCookie(UNTYXLCOOKIE);
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

    public static void printHeader2(Map<? extends Object, ? extends Object> map) {
        System.out.println(JSONObject.toJSONString(map));
    }

    public static void print(Object map) {
        System.out.println(map);
    }


    //活动id
    private static String NamespaceAndSiteCode = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/enumConst/getByNamespaceAndSiteCode";

    public static String getNamespaceAndSiteCode(String namespace) {
        String data = "{\"params\":{\"namespace\":\"" + namespace + "\"}}";
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
    public static String getSaveActivityTw1(String classroomId, String courseId, String content, String studentCount) {
        String data = "{\"params\":{\"name\":\"提问\",\"activityType\":\"f7f3cef4fe3e11ebabc2fa346ba4cb00\"," +
                "\"classroomId\":\"" + classroomId + "\"," +
                "\"flagClassroomType\":\"41828108a8f711eca8977c10c99ef73b\"," +
                "\"status\":1,\"detailType\":\"f52334671f3e11ec99cf0c42a14757d6\"," +
                "\"isFilter\":\"0\"," +
                "\"content\":\"" + content + "\"," +
                "\"checkedStu\":[]," +
                "\"studentCount\":" + studentCount + "," +
                "\"courseId\":\"" + courseId + "\"}}";
        String resp = newZjyHttp.post(saveActivity, data);
        return resp;
    }

    //抽人提问
    public static String getSaveActivityTw2(String classroomId, String courseId, String content, String checkedStu) {
        String data = "{\"params\":{\"name\":\"提问\",\"activityType\":\"f7f3cef4fe3e11ebabc2fa346ba4cb00\"," +
                "\"classroomId\":\"" + classroomId + "\"," +
                "\"flagClassroomType\":\"41828108a8f711eca8977c10c99ef73b\"," +
                "\"status\":1,\"detailType\":\"f51be0711f3e11ec99cf0c42a14757d6\"," +
                "\"isFilter\":\"0\"," +
                "\"content\":\"" + content + "\"," +
                "\"checkedStu\":" + checkedStu + "," +
                "\"studentCount\":null," +
                "\"courseId\":\"" + courseId + "\"}}";
        String resp = newZjyHttp.post(saveActivity, data);
        return resp;
    }

    //讨论
    public static String getSaveActivityTl(String classroomId, String courseId, String content) {
        String data = "{\"params\":{\"name\":\"讨论\",\"activityType\":\"fd650823fe3e11ebabc2fa346ba4cb00\"," +
                "\"classroomId\":\"" + classroomId + "\"," +
                "\"flagClassroomType\":\"41828108a8f711eca8977c10c99ef73b\"," +
                "\"status\":1,\"detailType\":\"30ae0e1c2ff811ec99cf0c42a14757d6\"," +
                "\"content\":\"" + content + "\"" +
                ",\"imgPathList\":[],\"deletePathList\":[]," +
                "\"courseId\":\"" + courseId + "\"}}";
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

    public static String getCreateGroupActivity(String classroomId, String courseId, String groupCount) {
        String data = "{\"params\":{\"name\":\"小组PK\",\"activityType\":\"bde4bee12f5c11eda2b7fa346ba4cb00\"," +
                "\"classroomId\":\"" + classroomId + "\"," +
                "\"flagClassroomType\":\"41828108a8f711eca8977c10c99ef73b\",\"status\":1," +
                "\"courseId\":\"" + courseId + "\"," +
                "\"groupCount\":\"" + groupCount + "\",\"isAutomaticAllocation\":1}}";
        String resp = newZjyHttp.post(createGroupActivity, data);
        return resp;
    }

    //讨论组打分
    private static String rateTheGroup = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/grouppk/rateTheGroup";

    public static String getRateTheGroup(String groupId, String score) {
        String data = "{\"params\":{\"groupId\":\"" + groupId + "\",\"score\":" + score + "}}";
        String resp = newZjyHttp.post(rateTheGroup, data);
        return resp;
    }

    //分组讨论详情

    //老师api
    private static String CurrentActiveAllGroups = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/grouppk/getCurrentActiveAllGroups";
    //学生api
    private static String GroupActivityAndStuInfo = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/grouppk/getGroupActivityAndStuInfo";

    public static String getAllGroups(String activityId) {
        String data = "{\"params\":{\"activityId\":\"" + activityId + "\"}}";
        String resp = newZjyHttp.post(GroupActivityAndStuInfo, data);
        return resp;
    }

    //自己分组详情
    private static String GroupInfoByUser = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/grouppk/getGroupInfoByUser";

    public static String getGroupInfoByUser(String activityId) {
        String data = "{\"params\":{\"activityId\":\"" + activityId + "\"}}";
        String resp = newZjyHttp.post(GroupInfoByUser, data);
        return resp;
    }


    //获取课堂内所有学生信息
    private static String StudentsQuestioned = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/question/teacher/getStudentsQuestioned";

    public static String getStudentsQuestioned(String classroomId) {
        String data = "{\"params\":{\"classroomId\":\"" + classroomId + "\"," +
                "\"isFilter\":\"0\",\"searchContent\":\"\"}}";
        String resp = newZjyHttp.post(StudentsQuestioned, data);
        return resp;
    }

    //删除活动
    private static String delActivity = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/peClassroomActivity/teacher/delActivity";

    public static String getDelActivity(String classroomId, String activityId) {
        String data = "{\"params\":{" +
                "\"id\":\"" + activityId + "\"," +
                "\"classroomId\":\"" + classroomId + "\"}}";
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
            , String learn_time, String learn_time_full, String node_rate, String node_rate_full
            , String effective_time, String effective_time_full, String test_rate, String test_rate_full
            , String work_rate, String work_rate_full, String pop_quiz_rate, String pop_quiz_full
            , String exam_score_rate, String exam_score, String questions_rate, String questions_rate_full
            , String sign_num_rate, String sign_num_full, String discuss_rate, String discuss_rate_full
            , String groupPK_rate, String groupPK_rate_full) {
        String data = "token=MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0Q=&courseId=" + courseId +
                "&classId=" + classId +
                "&total_learn_time_rate=" + learn_time + "&total_learn_time_full=" + learn_time_full +
                "&node_rate=" + node_rate + "&node_rate_full=" + node_rate_full +
                "&effective_time_rate=" + effective_time + "&effective_time_full=" + effective_time_full +
                "&test_rate=" + test_rate + "&test_rate_full=" + test_rate_full +
                "0&work_rate=" + work_rate + "&work_rate_full=" + work_rate_full +
                "&pop_quiz_rate=" + pop_quiz_rate + "&pop_quiz_full=" + pop_quiz_full +
                "&exam_score_rate=" + exam_score_rate + "&exam_score=" + exam_score +
                "&questions_rate=" + questions_rate + "&questions_rate_full=" + questions_rate_full +
                "&sign_num_rate=" + sign_num_rate + "&sign_num_full=" + sign_num_full +
                "&discuss_rate=" + discuss_rate + "&discuss_rate_full=" + discuss_rate_full +
                "&groupPK_rate=" + groupPK_rate + "&groupPK_rate_full=" + groupPK_rate_full;
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


    // 老师api 老师信息
    static String Teacher_getUserInfo = "https://user.icve.com.cn/t/m/zhzjPeMobileTeacher_getUserInfo.action";

    public static String getTeacher_getUserInfo(String token) {
        String data = "token=" + token;
        String resp = newZjyHttp.post(Teacher_getUserInfo, data);
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
    private static String renewStuTopicInfo = "https://spoc-classroom.icve.com.cn/classroom-teaching-api/discuss/renewStuTopicInfo";

    public static String getRenewStuTopicInfo(String activityId, String recordId, String content) {
        String data = "{\"params\":{\"id\":\"" + activityId + "\",\"recordId\":\"" + recordId + "\"," +
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

    // web 参数
    //https://course.icve.com.cn/learnspace/sign/signLearn.action?
    // template=blue&courseId=39e272199dab487ba6f8f76115cbfd2c
    // &loginType=true&loginId=venomms&sign=0&siteCode=zhzj&domain=user.icve.com.cn
    public static String getSignLearn(String courseId, String loginId, int type) {

        String switchData = "";
        if (type != 1) {
            switchData = "spoc.icve.com.cn";
        } else {
            switchData = "user.icve.com.cn";
        }

        String data = "sign=mobile&loginType=true&courseId=" + courseId +
                "&loginId=" + loginId + "&siteCode=zhzj&domain=" + switchData;


        httpRespnose ret = newZjyHttp.get(signLearn, null, data);

        //System.out.println(ret.toString());

        //printHeader();

        String UNTYXLCOOKIE = ret.getCookieV("UNTYXLCOOKIE");//.replace("\"", "");
        //String UNTYXLCOOKIE = ret.getSetCookie();
        return UNTYXLCOOKIE;
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
                "\"resourceTotalTime\":\"01:30:00\",\"studyRecords\":[{\"endTime\":\"01:30:00\"," +
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

    //课件方式打开作业等
    static String weixinCourseExamAction = "https://course.icve.com.cn/learnspace/course/exam/weixinCourseExamAction_intoConfirmStudentInfo.action";

    public static String getWeixinCourseExamAction(String itemId) {
        String data = "params.itemId=" + itemId +
                "&params.appIntoType=examInfoConfirm&params.intoDevice=weixin";
        httpRespnose vHttpRespnose = newZjyHttp.get(weixinCourseExamAction, null,
                data);
        String Location = vHttpRespnose.getLocation();
        newZjyHttp.addCookie(vHttpRespnose.getSetCookie());
        vHttpRespnose = newZjyHttp.get(Location, null, null);
        String resp = vHttpRespnose.getResp();
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

    static String Usersessionidm = "https://course.icve.com.cn/learnspace/course/exam/weixinCourseExamAction_examListGoExam.action";

    public static String getUsersessionidm(String courseId, String examCode) {
        String data = "params.appIntoType=examInfoConfirm" +
                "&params.examCode=" + examCode +
                "&params.courseId=" + courseId +
                "&params.intoDevice=weixin&params.filterType=all";


        httpRespnose vHttpRespnose = newZjyHttp.get(Usersessionidm, null, data);

        //printHeader();

        //System.out.println(vHttpRespnose.toString());

        String Location = vHttpRespnose.getLocation();

        //newZjyHttp.addCookie(vHttpRespnose.getSetCookie());

        vHttpRespnose = newZjyHttp.get(Location, null, null);

        //printHeader();

        //System.out.println(vHttpRespnose.toString());
        if (vHttpRespnose.getLocation() != null) {
            Location = vHttpRespnose.getLocation();
            vHttpRespnose = newZjyHttp.get(Location, null, null);
        }

        String USERSESSIONID = vHttpRespnose.getCookieV("USERSESSIONID");
        //String USERSESSIONID = vHttpRespnose.getSetCookie();

        return USERSESSIONID;
    }


    public static void upUsersessionid(String Usersessionid) {
        newZjyHttp.addCookie(Usersessionid);
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


    //
    static String StatisticsAuthCode = "https://course.icve.com.cn/homework-api/statisticsLogTracker/getStatisticsAuthCode";

    public static String getStatisticsAuthCode() {
        String data = "{}";
        String resp = newZjyHttp.post(StatisticsAuthCode, data);
        return resp;
    }


    // 获取 有关附件作业的 rest_token  Authorization
    static String homework_index = "https://course.icve.com.cn/learnspace/learn/weixin/common/homework/homework_index.action";

    public static String getHomework_index(String courseId, int type) {
        String switchData = "";
        if (type != 1) {
            switchData = "___";
        }
        String data = "params.courseId=" + courseId + switchData +
                "&params.columnTypeId=1fb6d397336642eca759b285074fb63e" +
                "&params.curPage=1&params.pageSize=10";
        //System.out.println(data);
        httpRespnose resp = newZjyHttp.get(homework_index, null, data);
        //System.out.println(resp.toString());
        String rest_token = resp.getCookieV("rest_token");
        rest_token = (String) newZjyHttp.parseCookie(rest_token).get("rest_token");
        //System.out.println(rest_token);
        return rest_token;
    }

    //附件作业
    static String homework = "https://course.icve.com.cn/homework-api/mobile/student/homework/list";

    public static String getHomework(String courseId) {
        String data = "{\"params\":{\"groupId\":\"" + courseId + "\"," +
                "\"columnId\":\"1fb6d397336642eca759b285074fb63e\"}," +
                "\"page\":{\"curPage\":1,\"pageSize\":10,\"totalCount\":0,\"totalPage\":0}}";

        String resp = newZjyHttp.post(homework, data);
        return resp;
    }

    //附件作业参考答案
    static String homeworkinfo = "https://course.icve.com.cn/homework-api/student/homework/info";

    public static String getHomeworkinfo(String homeworkStuId) {
        //402883e6837a403f01837dab80de28ee
        String data = "{\"params\":{\"homeworkId\":\"" + homeworkStuId + "\"}}";
        String resp = newZjyHttp.post(homeworkinfo, data);
        if (resp == null || !resp.contains("referenceAnswer")) return "";
        String referenceAnswer = Tool.parseJsonO(resp, "data").getJSONObject("homeworkInfo").getString("referenceAnswer");
        return referenceAnswer;
    }

    //获取hsid 改分用
    static String hwStudent = "https://course.icve.com.cn/homework-api/student/homework/hwStudent";

    public static String getHwStudent(String homeworkStuId) {
        String data = "{\"params\":{\"homeworkId\":\"" + homeworkStuId + "\"}}";
        String resp = newZjyHttp.post(hwStudent, data);
        if (resp == null || !resp.contains("id")) return "";
        String hsid = Tool.parseJsonO(resp, "data").getJSONObject("homeworkStudent").getString("id");
        return hsid;
    }

    //获取大家的附件作业和hsid
    static String hwEvalList = "https://course.icve.com.cn/homework-api/student/homework/evalList";

    public static String getHwEvalList(String homeworkId) {
        String data = "{\"params\":{\"homeworkId\":\"" + homeworkId + "\",\"queryFlag\":\"1\"}," +
                "\"page\":{\"curPage\":1,\"pageSize\":100,\"totalCount\":0,\"totalPage\":0}}\n";
        String resp = newZjyHttp.post(hwEvalList, data);
        return resp;
    }


    //batchId == examID
    //获取 考试 作业 测试 的PaperId
    static String ConfirmPagePaperStructure = "https://spoc-exam.icve.com.cn/student/exam/studentExam_getConfirmPagePaperStructure.action";

    public static String getConfirmPagePaperStructure(String batchId) {
        String data = "params.batchId=" + batchId;
        String resp = newZjyHttp.post(ConfirmPagePaperStructure, data);
        return resp;
    }

    // get ORSESSIONID=7F65745C91698847A2535AE97AC8D653; Domain=spoc-exam.icve.com.cn; Path=/; SameSite=None; Secure

    static String examflow_getStudentStatus = "https://spoc-exam.icve.com.cn/exam/examflow_getStudentStatus.action";

    public static String getExamflow_getStudentStatus(String batchId) {
        String data = "params.batchId=" + batchId;
        String resp = newZjyHttp.post(examflow_getStudentStatus, data);
        return resp;
    }

    //
    static String examflow_beforeIndex = "https://spoc-exam.icve.com.cn/exam/examflow_beforeIndex.action";

    public static String getExamflow_beforeIndex(String batchId) {
        examflow_beforeIndex = examflow_beforeIndex + "?batchId=" + batchId + "&token=" + System.currentTimeMillis();
        String resp = newZjyHttp.post(examflow_getStudentStatus, null);
        return resp;
    }

    //
    static String examFlow_loadingExam = "https://spoc-exam.icve.com.cn/mobile/examFlow_loadingExam.action";

    public static String getExamFlow_loadingExam(String batchId) {
        String data = "batchId=" + batchId;
        String resp = newZjyHttp.post(examFlow_loadingExam, data);
        return resp;
    }

    // get examRecordId=e7f30b7780a84644857773068b45d2ad

    static String examFlow_intoExam = "https://spoc-exam.icve.com.cn/mobile/examFlow_intoExam.action";

    public static String getExamFlow_intoExam(String batchId, String courseId) {
        String data = "params.batchId=" + batchId +
                "&params.courseId=" + courseId +
                "&params.appIntoType=examInfoConfirm";
        String resp = newZjyHttp.get(examFlow_intoExam, data);
        return resp;
    }

    // 考生信息  stuSsoId 402883ab82fee62c018307a26e3e1f1d 试卷锁定
    static String examflow_getUserInfo = "https://spoc-exam.icve.com.cn/exam/examflow_getUserInfo.action";

    public static String getExamflow_getUserInfo() {
        String resp = newZjyHttp.post(examflow_getUserInfo, null);
        return resp;
    }

    //已做记录
    static String examflow_getExamRecord = "https://spoc-exam.icve.com.cn/exam/examflow_getExamRecord.action";

    public static String getExamflow_getExamRecord(String examRecordId) {
        String data = "examRecordId=" + examRecordId;
        String resp = newZjyHttp.post(examflow_getExamRecord, data);
        return resp;
    }

    //考试设置信息
    static String examflow_getExamInfo = "https://spoc-exam.icve.com.cn/exam/examflow_getExamInfo.action";

    public static String getExamflow_getExamInfo(String batchId) {
        String data = "batchId=" + batchId;
        String resp = newZjyHttp.post(examflow_getExamInfo, data);
        return resp;
    }

    //全部题的id信息
    static String examFlow_getExamPaperInfo = "https://spoc-exam.icve.com.cn/mobile/examFlow_getExamPaperInfo.action";

    public static String getExamFlow_getExamPaperInfo(String examRecordId) {
        String data = "examRecordId=" + examRecordId;
        String resp = newZjyHttp.post(examFlow_getExamPaperInfo, data);
        return resp;
    }

    //获取题目
    static String examFlow_getPageData = "https://spoc-exam.icve.com.cn/mobile/examFlow_getPageData.action";

    public static String getExamFlow_getPageData(String examRecordId, String qid, String cid) {
        examFlow_getPageData = examFlow_getPageData + "?examRecordId=" + examRecordId +
                "&params.contentId=" +
                "&params.questionSeq=1" + cid +
                "&params.contentQId=" + qid;
        String resp = newZjyHttp.post(examFlow_getPageData, null);
        return resp;
    }

    //保存用间
    static String examflow_saveCountDown = "https://spoc-exam.icve.com.cn/exam/examflow_saveCountDown.action";

    public static String getExamflow_saveCountDown(String countDown, String examRecordId) {
        String data = "countDown=" + countDown +
                "&examRecordId=" + examRecordId;
        String resp = newZjyHttp.post(examflow_saveCountDown, data);
        return resp;
    }


    //更改考试状态
    static String examFlow_recordUserAction = "https://spoc-exam.icve.com.cn/mobile/examFlow_recordUserAction.action";

    public static String getExamFlow_recordUserAction(String examRecordId, String status) {
        String data = "params.recordId=" + examRecordId +
                "&params.action=" + status;//resume pause
        String resp = newZjyHttp.post(examFlow_recordUserAction, data);
        return resp;
    }


    //是否被锁定
    static String examflow_getPaperLockedStatus = "https://spoc-exam.icve.com.cn/exam/examflow_getPaperLockedStatus.action";

    public static String getExamflow_getPaperLockedStatus(String examRecordId) {
        String data = "examRecordId=" + examRecordId;
        String resp = newZjyHttp.post(examflow_getPaperLockedStatus, data);
        boolean paperIsLocked = Tool.parseJsonO(resp, "data").getBoolean("paperIsLocked");
        if (paperIsLocked) {
            return "已锁";
        }
        return "未锁";
    }


    //保存答案
    static String examflow_sendManyAnswer = "https://spoc-exam.icve.com.cn/exam/examflow_sendManyAnswer.action";

    public static String getExamflow_sendManyAnswer(String examRecordId, String studentAnswers, String countDown) {
        String data = "examRecordId=" + examRecordId +
                "&studentAnswers=" + studentAnswers +
                "&countDown=" + countDown;
        String resp = newZjyHttp.post(examflow_sendManyAnswer, data);
        return resp;
    }


    //合并结果
    static String examflow_getCompleteQuestionSeq = "https://spoc-exam.icve.com.cn/exam/examflow_getCompleteQuestionSeq.action";

    public static String getExamflow_getCompleteQuestionSeq(String examRecordId, String batchId) {
        String data = "examRecordId=" + examRecordId +
                "&batchId=" + batchId;
        String resp = newZjyHttp.post(examflow_getCompleteQuestionSeq, data);
        return resp;
    }

    //提交
    static String examflow_complete = "https://spoc-exam.icve.com.cn/exam/examflow_complete.action";

    public static String getExamflow_complete(String examRecordId) {

        String data = "examRecordId=" + examRecordId;
        String resp = newZjyHttp.post(examflow_complete, data);
        return resp;
    }

    //试卷最后已做详情
    static String examFlow_RecordInfoBeforeExam = "https://spoc-exam.icve.com.cn/mobile/examFlow_getRecordInfoBeforeExam.action";

    public static String getExamFlow_RecordInfoBeforeExam(String batchId) {
//params.batchId=4028824d82feea2d018303f382b2217f
        String data = "params.batchId=" + batchId;
        String resp = newZjyHttp.post(examFlow_RecordInfoBeforeExam, data);
        return resp;
    }

    //试卷已做列表
    static String examRecord_RecordList = "https://spoc-exam.icve.com.cn/mobile/examRecord_getRecordList.action";

    public static String getExamRecord_RecordList(String batchId) {

        String data = "params.batchId=" + batchId;
        String resp = newZjyHttp.post(examRecord_RecordList, data);
        return resp;
    }

    //已做试卷题目 id详情
    static String examRecord_RecordPaperStructure = "https://spoc-exam.icve.com.cn/mobile/examRecord_getRecordPaperStructure.action";

    public static String getExamRecord_RecordPaperStructure(String examRecordId) {

        String data = "recordId=" + examRecordId;
        String resp = newZjyHttp.post(examRecord_RecordPaperStructure, data);
        return resp;
    }

    //已做试卷答案 id详情
    static String examRecord_StudentAnswer = "https://spoc-exam.icve.com.cn/mobile/examRecord_getStudentAnswer.action";

    public static String getExamRecord_StudentAnswer(String examRecordId, String batchId) {

        String data = "recordId=" + examRecordId +
                "&batchId=" + batchId;
        String resp = newZjyHttp.post(examRecord_StudentAnswer, data);
        return resp;
    }

    static String prepareExam_intoConfirmStudentInfo="https://spoc-exam.icve.com.cn/mobile/prepareExam_intoConfirmStudentInfo.action";
    //params.batchId=402883ab82fee62c0183204009171f77&params.prePage=result&params.isSkipResult=1&params.courseId=39e272199dab487ba6f8f76115cbfd2c&params.appIntoType=examInfoConfirm
    //get





    //============================================ 移动 api 老师相关 ============================


    //学生信息列表
    static String ClassTraineeList = "https://user.icve.com.cn/m/zhzjMobile_getClassTraineeList.action";

    public static String getClassTraineeList(String classId, String token) {
        //9b933b5e625e459ba9df0ea29e9e50ed
        String data = "pageSize=500&curPage=1&token=" + token +
                "&classId=" + classId;
        String resp = newZjyHttp.post(ClassTraineeList, data);
        return resp;
    }

    //审核管理相关
    static String ClassTraineeAuditList = "https://user.icve.com.cn/m/zhzjMobile_getClassTraineeAuditList.action";

    public static String getClassTraineeAuditList(String classId, String token) {
        String data = "pageSize=200&curPage=1&token=" + token +
                "&classId=" + classId;
        String resp = newZjyHttp.post(ClassTraineeAuditList, data);
        return resp;
    }

    //
    static String ClassAuditStatus = "https://user.icve.com.cn/m/zhzjMobile_getClassAuditStatus.action";

    public static String getClassAuditStatus(String classId, String token) {
        String data = "token=" + token + "&classId=" + classId;
        String resp = newZjyHttp.post(ClassAuditStatus, data);
        return resp;
    }

    //加课二维码
    static String QrCode = "https://user.icve.com.cn/patch/nlpx/getQrCode.action";

    public static String getQrCode(String classId) {
        String data = "url={\"code\":\"1001\",\"service\":\"enterClass\",\"param\":" +
                "{\"url\":\"qrCodeResult.html?classId=" + classId + "\"}}" +
                "&pic=";
        String resp = newZjyHttp.post(QrCode, data);
        return resp;
    }

    //添加课堂
    static String saveClassroom = "https://user.icve.com.cn/t/m/zhzjPeMobileCourse_saveClassroom.action";

    public static String getSaveClassroom(String courseId, String title, String tk) {

        String classId = Tool.md5(title);
        String data = "classId=" + classId +
                "&courseId=" + courseId + "&title=" + title +
                "&startDate=" + Tool.getCurrentData() + "&token=" + tk;
        String resp = newZjyHttp.post(saveClassroom, data);
        return resp;
    }


    //课程答疑列表
    static String question_list = "https://course.icve.com.cn/learnspace/learn/weixin/common/question/include/question_list.action";

    public static String getQuestion_list(String courseId, String opt) {
        String data = "params.isMyQuestion=&params.curPage=1&params.pageSize=100" +
                "&params.username=" +
                "&params.courseIds=" + courseId +
                "&params.classIds=&params.opt=" + opt;
        String resp = newZjyHttp.post(question_list, data);
        return resp;
    }

    public static String getQuestion_list1(String courseId) {
        String resp = newZjyHttp.post(courseId, "1");
        return resp;
    }

    public static String getQuestion_list2(String courseId) {
        String resp = newZjyHttp.post(courseId, "2");
        return resp;
    }

    public static String getQuestion_list3(String courseId) {
        String resp = newZjyHttp.post(courseId, "3");
        return resp;
    }

    //课程答疑置顶
    static String updateApprovalQuestion = "https://course.icve.com.cn/learnspace/learn/answerInteraction/updateApprovalQuestion.json";

    public static String getUpdateApprovalQuestion(String questionId, String approvalStatus) {
        String data = "params.questionId=" + questionId +
                "&params.approvalStatus=" + approvalStatus;
        String resp = newZjyHttp.post(updateApprovalQuestion, data);
        return resp;
    }

    public static String getUpdateApprovalQuestionZd(String questionId) {
        String resp = getUpdateApprovalQuestion(questionId, "0");
        return resp;
    }

    public static String getUpdateApprovalQuestionQx(String questionId) {
        String resp = getUpdateApprovalQuestion(questionId, "1");
        return resp;
    }

    //删除答疑
    static String deleteQuestion = "https://course.icve.com.cn/learnspace/learn/answerInteraction/deleteQuestion.json";

    public static String getDeleteQuestion(String questionId) {
        String data = "params.questionId=" + questionId;
        String resp = newZjyHttp.post(deleteQuestion, data);
        return resp;
    }


    static String saveIgnoreQuestion = "https://course.icve.com.cn/learnspace/learn/answerInteraction/saveIgnoreQuestion.json";

    public static String getSaveIgnoreQuestion(String questionId, String opt) {
        String data = "params.questionId=" + questionId +
                "&params.opt=" + opt;
        String resp = newZjyHttp.post(saveIgnoreQuestion, data);
        return resp;
    }

    public static String getSaveIgnoreQuestion(String questionId) {
        String resp = getSaveIgnoreQuestion(questionId, "1");
        return resp;
    }

    //答疑评论

    static String saveAnswer = "https://course.icve.com.cn/learnspace/learn/answerInteraction/saveAnswer.json";

    public static String getSaveAnswer(String questionId, String content) {
        String data = "params.questionId=" + questionId +
                "&params.content=" + content +
                "&params.imgUrl=";
        String resp = newZjyHttp.post(saveIgnoreQuestion, data);
        return resp;
    }


    //主题讨论
    //https://course.icve.com.cn/learnspace/learn/weixin/common/topic/topic_teacher_index_list.action
    //params.opts=1&params.courseIds=39e272199dab487ba6f8f76115cbfd2c&params.pageSize=10&params.curPage=1

    //讨论回复
    // https://course.icve.com.cn/learnspace/learn/weixinTopic/savePostReply.json
    //params.courseId=39e272199dab487ba6f8f76115cbfd2c&params.itemId=402883e6831cb93501832040f7c22296&params.loginId=Debug&params.loginType=1&params.nickName=%E5%90%B4%E4%BA%A6&params.userPhoto=&params.detail=%3Cp%3E%E7%89%9B%E9%80%BC%3C%2Fp%3E&params.curPage=1

    //全部附件作业

    //全部
    static String allHomework = "https://course.icve.com.cn/homework-api/mobile/teacher/allHomework";

    public static String getAllHomework(String courseId, String opt) {
        String data = "{\"params\":{\"title\":\"\",\"courseIds\":[\"" + courseId + "\"]," +
                "\"classIds\":[]" + opt + "}," +
                "\"page\":{\"curPage\":1,\"pageSize\":10,\"totalCount\":0,\"totalPage\":0}}";
        String resp = newZjyHttp.post(allHomework, data);
        return resp;
    }

    //待批
    public static String getAllHomeworkDp(String courseId) {
        String data = ",\"hwStatus\":\"hstatus1\",\"sortType\":\"DESC\"";
        String resp = getAllHomework(courseId, data);
        return resp;
    }

    //已批
    public static String getAllHomeworkYp(String courseId) {
        String data = ",\"hwStatus\":\"hstatus2\",\"sortType\":\"DESC\"";
        String resp = getAllHomework(courseId, data);
        return resp;
    }

    //单个附件作业

    //
    static String allHomeworkStudent = "https://course.icve.com.cn/homework-api/mobile/teacher/allHomeworkStudent";

    public static String getAllHomeworkStudent(String homeworkId, String hwStatus) {
        String data = "{\"params\":{\"homeworkId\":\"" + homeworkId + "\"," +
                "\"username\":\"\",\"courseIds\":[],\"classIds\":[],\"scope\":[]," +
                "\"hwStatus\":\"" + hwStatus + "\"},\"page\":{\"curPage\":1,\"pageSize\":10," +
                "\"totalCount\":0,\"totalPage\":0}}\n";

        String resp = newZjyHttp.post(allHomework, data);
        return resp;
    }


    //待批
    public static String getAllHomeworkStudentDp(String homeworkId) {
        String resp = getAllHomeworkStudent(homeworkId, "hstatus1");
        return resp;
    }

    //已批
    public static String getAllHomeworkStudentYp(String homeworkId) {
        String resp = getAllHomeworkStudent(homeworkId, "hstatus2");
        return resp;
    }

    //驳回
    public static String getAllHomeworkStudentBh(String homeworkId) {
        String resp = getAllHomeworkStudent(homeworkId, "hstatus3");
        return resp;
    }


    //附件作业改分
    static String saveCheckHomework = "https://course.icve.com.cn/homework-api/teacher/saveCheckHomework";

    public static String getSaveCheckHomework(String homeworkStuId, String homeworkScore, String comments, String isRecommend) {
        //402883e6837a403f01837dab80de28ee
        /*
        {"params":{"homeworkStuId":"402883e6837a403f01837dab80de28ee",
        "homeworkScore":100,"comments":"","isRecommend":"1"}}
        "isTeacherRecommend":"0",
        {"params":{"homeworkStuId":"402883e6837a403f01837dab80de28ee",
        "homeworkScore":100,"comments":"","isRecommend":"1"}}  isRecommend是否评优
         */
        String data = "{\"params\":{\"isRecommend\":\"" + isRecommend + "\"," +
                "\"homeworkStuId\":\"" + homeworkStuId + "\"," +
                "\"comments\":\"" + comments + "\",\"homeworkScore\":" + homeworkScore + "}}";
        httpRespnose vHttpRespnose = newZjyHttp.post(saveCheckHomework, data, null, null);
        String resp = vHttpRespnose.getResp();
        return resp;

    }











    //============================================== web 学生 api =====================================


    //web登陆测试
    //static String findUser = "https://user.icve.com.cn/patch/nlpx/findUser.action";
    //static String auth = "https://spoc-sso.icve.com.cn/auth";


    static String userLogin = "https://sso.icve.com.cn/data/userLogin";
    static String cms = "https://user.icve.com.cn/cms/";
    static String api_getUserInfo = "https://user.icve.com.cn/patch/zhzj/api_getUserInfo.action";
    static String index = "https://user.icve.com.cn/learning/u/student/teaching/index.action";

    //static String login_logout = "https://user.icve.com.cn/learning/sso/login_logout.action";


    public static String webLogin() {
        String data = "{\"userName\":\"venomms\",\"password\":\"Poor2579988653\",\"type\":1}";
        //newZjyHttp.addCookie("whatysns=b9db789f2acaccfcdbfe216e44e16a70");
        httpRespnose resp = newZjyHttp.post(userLogin, data, null, null);
        newZjyHttp.restCookie(resp.getSetCookie());
        printHeader();
        String token = resp.getCookieV("token");
        newZjyHttp.addCookie(token);
        token = (String) newZjyHttp.parseCookie(token).get("token");

        System.out.println(token);

        data = "token=" + token;

        resp = newZjyHttp.get(cms, null, data);
        newZjyHttp.addCookie(resp.getSetCookie());

        resp = newZjyHttp.post(api_getUserInfo, data, null, null);
        newZjyHttp.addCookie(resp.getSetCookie());
        String ret = resp.getResp();
        System.out.println(ret);

        resp = newZjyHttp.get(index, null, data);

        //resp = newZjyHttp.get(login_logout, null, data);

        printHeader();

        //String rt = newZjyHttp.get(cms);
        //
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


    // 学生api
    static String zhzjMobile_getRestSsoTokenBySNS = "ttps://user.icve.com.cn/m/zhzjMobile_getRestSsoTokenBySNS.action";
    //token:
    //MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0Q=


    //获取课件 limitId
    static String courseware_index = "https://course.icve.com.cn/learnspace/learn/learn/templateeight/courseware_index.action";

    public static String getCourseware_index(String courseId) {
        String data = "params.courseId=" + courseId + "___";
        String resp = newZjyHttp.get(courseware_index, data);
        return resp;
    }

    //获取菜单
    static String templateeight = "https://course.icve.com.cn/learnspace/learn/learn/templateeight/index.action";

    public static String getTemplateeight(String courseId) {
        String data = "params.courseId=" + courseId + "___" +
                "&params.templateType=8&params.templateStyleType=0" +
                "&params.template=templateeight&params.classId=&params.tplRoot=learn";
        String resp = newZjyHttp.get(templateeight, data);
        return resp;
    }

    //
    static String statisticsLearningFrequency = "https://course.icve.com.cn/learnspace/learn/learnCourseItem/statisticsLearningFrequency.json";

    public static String getStatisticsLearningFrequency(String courseId) {
        String data = "params.courseId=" + courseId;
        String resp = newZjyHttp.get(statisticsLearningFrequency, data);
        return resp;
    }

    //未知
    static String SingleItemCompleteCase = "https://course.icve.com.cn/learnspace/learn/learnCourseware/getSingleItemCompleteCase.json";

    public static String getSingleItemCompleteCase(String courseId, String itemId) {
        String data = "params.courseId=" + courseId + "___" +
                "&params.itemId=" + itemId;
        String resp = newZjyHttp.get(SingleItemCompleteCase, data);
        return resp;
    }


    //保存课程学习时长
    public static String getSaveLearningTime2(String courseId, String limitId, String studyTime) {
        String data = "courseId=" + courseId + "___" +
                "&studyTime=" + studyTime +
                "&limitId=" + limitId;
        String resp = newZjyHttp.get(saveLearningTime, data);
        return resp;
    }



    /*    static String cloud_updateVideoTotalTime="https://course.icve.com.cn/learnspace/course/plugins/cloud_updateVideoTotalTime.action";
     * itemId=8de8f1455b54434287da78c836d46954&videoTotalTime=00%3A04%3A43
     *
     * https://course.icve.com.cn/learnspace/learn/learn/templateeight/include/video_learn_record_detail.action
     *params.courseId=39e272199dab487ba6f8f76115cbfd2c___&params.itemId=8de8f1455b54434287da78c836d46954&params.videoTotalTime=00%3A04%3A43
     *  */


    //web 保存视频进度
    static String saveVideoLearnDetailRecord = "https://course.icve.com.cn/learnspace/course/study/learningTime_saveVideoLearnDetailRecord.action";

    public static String getSaveVideoLearnDetailRecord(String studyRecord, String limitId) {
        String data = "studyRecord=" + studyRecord +
                "&limitId=" + limitId;
        String resp = newZjyHttp.get(saveLearningTime, data);
        return resp;
    }
    // 考试 作业 测试

    static String studentExam_studentExamListData = "https://spoc-exam.icve.com.cn/student/exam/studentExam_studentExamListData.action?pager.pageSize=100&pager.curPage=1&pager.searchTotalSize=true";

    public static String getExam_list_dataw(String courseId, String configTypeId, String ConfigTypeId) {
        String data = "params.examName=&params.examType=" +
                "&params.examConfigTypeId=" + ConfigTypeId +
                "&params.courseId=" + courseId +
                "&params.configTypeId=" + configTypeId;


        String resp = newZjyHttp.post(studentExam_studentExamListData, data);

        return resp;
    }

    //测验
    public static String getExam_list_data_tw(String courseId) {
        return getExam_list_dataw(courseId, "ed134338b38640aa89cd34497437a97d", "3");
    }

    //作业
    public static String getExam_list_data_ww(String courseId) {
        return getExam_list_dataw(courseId, "6049aaaac97845d1a8a790f397962b0a", "2");
    }

    //考试
    public static String getExam_list_data_ew(String courseId) {
        return getExam_list_dataw(courseId, "81805198bddb4cdc84afbc3a1774f576", "1");
    }


    // 获取 有关附件作业的 rest_token  Authorization
    static String blue = "https://course.icve.com.cn/learnspace/learn/homework/blue/index.action";

    public static String getBlue(String courseId, int type) {
        String switchData = "";
        if (type != 1) {
            switchData = "___";
        }
        String data = "params.courseId=" + courseId + switchData +
                "&params.columnTypeId=1fb6d397336642eca759b285074fb63e" +
                "&params.curPage=1&params.pageSize=10";
        httpRespnose resp = newZjyHttp.get(blue, null, data);
        //System.out.println(resp.toString());
        String rest_token = resp.getCookieV("rest_token");
        rest_token = (String) (newZjyHttp.parseCookie(rest_token).get("rest_token"));
        return rest_token;
    }

    //附件
    static String homeworkw = "https://course.icve.com.cn/homework-api/student/homework/list";

    public static String getHomeworkw(String courseId) {
        String data = "{\"params\":{\"groupId\":\"" + courseId + "\"," +
                "\"columnId\":\"1fb6d397336642eca759b285074fb63e\"}}";
        String resp = newZjyHttp.post(homeworkw, data);
        return resp;
    }


    //获取 考试 作业 测试 的 PaperId
    private static String studentExam_getPaperStructure = "https://spoc-exam.icve.com.cn/student/exam/studentExam_getPaperStructure.action";

    public static String getExamPaperStructure(String examIds) {

        //{"retCode":"0","data":{"402883ab82fee62c0183204009171f77":{"isTemplatePaper":"0","courseNameAlias":"课程","courseName":"789456","examId":"402883ab82fee62c0183204009171f77","examScore":100,"structure":[{"id":"6d39be8eb4e447759ec62e8cf75d9389","name":"单选题","count":2,"total":100}],"paperId":"3960ed80fd384bf9a1ace9f7e23ddbd9"}}}
        String data = "examIds=" + examIds;
        String resp = newZjyHttp.post(studentExam_getPaperStructure, data);
        return resp;
    }

    //recoid获取
    static String examflow_index = "https://spoc-exam.icve.com.cn/exam/examflow_index.action";

    public static String getExamflow_index(String batchId) {
        String data = "batchId=" + batchId;
        String resp = newZjyHttp.get(examflow_index, data);
        return resp;
    }


    //题目id
    static String examflow_getPaperStructureTree = "https://spoc-exam.icve.com.cn/exam/examflow_getPaperStructureTree.action";

    public static String getExamflow_getPaperStructureTree(String examRecordId) {
        //examRecordId=25940b9793ad476cb8ed8f48c485e560
        String data = "examRecordId=" + examRecordId;
        String resp = newZjyHttp.post(studentExam_getPaperStructure, data);
        return resp;
    }


    //题详情
    static String examflow_getPageHtml = "https://spoc-exam.icve.com.cn/exam/examflow_getPageHtml.action";
    //examRecordId=25940b9793ad476cb8ed8f48c485e560&paperPageSeq=1

    //学习状况
    static String zhzjStudent_checkAssess = "https://user.icve.com.cn/zhzj/zhzjStudent_checkAssess.action";

    public static String getZhzjStudent_checkAssess() {
        String data = "token=MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0Q%3D" +
                "&classId=957639a938cc4e63b0953e132e0df096&loginId=venomms";
        String resp = newZjyHttp.post(studentExam_getPaperStructure, data);
        return resp;
    }

    //进度详情
    static String learnRecord_stuLearnRecord = "https://course.icve.com.cn/learnspace/course/study/learnRecord_stuLearnRecord.action";

    public static String getLearnRecord_stuLearnRecord(String courseId, String userId) {

        String data = "courseId=" + courseId + "___" +
                "&userId=" + userId +
                "&isShowHistory=1&templateType=8";
        String resp = newZjyHttp.get(learnRecord_stuLearnRecord, data);
        return resp;
    }

    /**
     * 个人进度详情
     * get 请求
     * https://course.icve.com.cn/learnspace/course/study/learnRecord_stuLearnRecord.action
     * courseId=39e272199dab487ba6f8f76115cbfd2c___&userId=2w7jafiswazbrev468vb5q&isShowHistory=1&templateType=8

     * <p>
     * <p>
     * 获取 Signature
     * <input type='hidden' name='_loginId' value='venomms' />
     * <input type='hidden' name='apiKey' value='zhzj_platform' />
     * <input type='hidden' name='_name' value='魏海旭' />
     * <input type='hidden' name='Signature' value='MCwCFFPxxgHGhly8mrUZEnP/Tm0kTqS4AhQZeJCKF394bKzePcKVgeybaBRu7Q==' />
     */
    //获取 Signature name
    static String courseExamAction = "https://course.icve.com.cn/learnspace/course/exam/courseExamAction_intoCourseExamList.action";

    public static String getCourseExamAction(String courseId, String columnTypeId) {
        String data = "params.courseId=" + courseId + "___" +
                "&params.columnTypeId=" + columnTypeId +
                "&params.examConfigTypeId=20";
        String resp = newZjyHttp.get(courseware_index, data);
        return resp;
    }


    public static String getCourseExamAction(String courseId) {
        String resp = getCourseExamAction(courseId, "81805198bddb4cdc84afbc3a1774f576");
        return resp;
    }


    public static void upUseridHd() {

        String host = "spoc-exam.icve.com.cn";
        String Origin = "https://course.icve.com.cn";
        String defaultAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36 Edg/108.0.1462.42";
        String ctype = "application/x-www-form-urlencoded";

        Map<String, Object> header = new LinkedHashMap<>();
        header.put("Connection", "Keep-Alive");
        header.put("Content-Type", ctype);
        //header.put("Referer ", "https://course.icve.com.cn/");
        // header.put("Origin", Origin);
        //header.put("User-Agent", defaultAgent);
        header.put("sec-ch-ua-platform", "Windows");
        header.put("Upgrade-Insecure-Requests", "1");
        // header.put("Sec-Fetch-Dest", "empty");
        header.put("Sec-Fetch-Mode", "navigate");
        header.put("sec-ch-ua-mobile", "?0");
        //header.put("Accept", "application/json, text/javascript, */*; q=0.01");
        // header.put("Accept-Encoding", "gzip, deflate, br");
        //header.put("X-Requested-With", "XMLHttpRequest");
        // header.put("Host", host);
        // header.put("sec-ch-ua", "\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\", \"Microsoft Edge\";v=\"108\"");
        newZjyHttp.addHeader(header);
    }

    //USERSESSIONID
    static String usersessionid = "https://spoc-exam.icve.com.cn/platformwebapi/student/exam/studentExam_studentExamInfoThird.action";
    //老师api
    static String usersessionidt = "https://spoc-exam.icve.com.cn/platformwebapi/college/exam/examBatch_intoExamListThird.action";

    public static String getUsersessionidw() {

        String data = "params.kckjToExamFlag=kckjToExamFlag" +
                "&params.examCodes=" +
                "&apiKey=zhzj_platform&_name=%E9%AD%8F%E6%B5%B7%E6%97%AD" +
                "&params.configTypeId=6049aaaac97845d1a8a790f397962b0a" +
                "&params.zhzjAlias=2034060125&params.examConfigTypeId=2" +
                "&_loginId=venomms" +
                "&Signature=MCwCFACqD5kSlZgNsuN1lm1VDbMC%2BsTEAhQICPiFnI15s0MtiBZKG9zOtuZ8Ng%3D%3D" +
                "&params.courseId=39e272199dab487ba6f8f76115cbfd2c" +
                "&_roleCode=student" +
                "&timestamp=" + System.currentTimeMillis();

        String ck = "";

        //newZjyHttp.addCookie(ck);


        Map<String, Object> header = newZjyHttp.getHeader();
        upUseridHd();

        //
        httpRespnose resp = newZjyHttp.post(usersessionid, data, null, null);

        printHeader();

        System.out.println(resp.toString());

        newZjyHttp.setHeader(header);

        String ret = resp.getSetCookie();
        return ret;


    }


    static String courseExamAction_stuIntoExamConfirm = "https://course.icve.com.cn/learnspace/course/exam/courseExamAction_stuIntoExamConfirm.json";

    public static String getUsersessionidw2() {
        String data = "params.classId=" +
                "&params.courseId=a41689a28e5442a39410e348fce1bd71___" +
                "&params.parentId=" +
                "&params.itemId=&params.audit=" +
                "&params.templateType=8&params.templateStyleType=0" +
                "&params.kckjWinHeight=529&_t=";

        String ck = "token=3dbd5841-caa0-4dec-ae96-991784191eb1";
        newZjyHttp.addCookie(ck);
        //printHeader();

        upUseridHd();

        httpRespnose resp = newZjyHttp.get(courseExamAction_stuIntoExamConfirm, null, data);

        System.out.println(resp.toString());

        newZjyHttp.addHeader(yktHeaders.getNewZjyMHeader());
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


    private static String ExamUSERSESSIONID = "https://spoc-exam.icve.com.cn/platformwebapi/student/exam/studentExam_studentExamInfoThird.action";

    public static String ExamUSERSESSIONID() {
        String data = "params.kckjToExamFlag=kckjToExamFlag&params.examConfigTypeId=2&params.examCodes=20220903232323367559%2C2022100116441943245&_loginId=venomms&apiKey=zhzj_platform&_name=%E9%AD%8F%E6%B5%B7%E6%97%AD&Signature=MCwCFFPxxgHGhly8mrUZEnP%2FTm0kTqS4AhQZeJCKF394bKzePcKVgeybaBRu7Q%3D%3D&params.configTypeId=6049aaaac97845d1a8a790f397962b0a&params.courseId=39e272199dab487ba6f8f76115cbfd2c&_roleCode=student&timestamp=1665067551683";
        httpRespnose resp = newZjyHttp.post(ExamUSERSESSIONID, data, null, null);
        printHeader(resp.getHearderFileds());
        return "";
    }


    //======================================================= web 老师 api =============================


    //删除课堂
    static String delClassroom = "https://user.icve.com.cn/zhzj/zhzjTeacher_delClassroom.action";

    public static String getDelClassroom(String Id) {
        String data = "id=" + Id +
                "&loginId=Debug" +
                "&token=aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA%3D%3D";
        String resp = newZjyHttp.post(delClassroom, data);
        return resp;
    }

    //学生成绩  姓名//学号//总学习时长得分 //资源完成情况得分//视频有效时长得分//附件作业得分//题库作业得分//随堂测试得分//题库考试得分//提问得分//签到得分//讨论得分//小组PK//总分
    static String StudentAssessList = "https://user.icve.com.cn/patch/zhzj/teacherStatistics_getStudentAssessList.action";

    public static String getStudentAssessList(String date, String classId) {
        String data = "date=2022-08-3+~+2023-9-3" +
                "&keyName=&curPage=1&pageSize=100" +
                "&classId=" +classId+
                "&token=MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0=";
        String resp = newZjyHttp.post(StudentAssessList, data);
        return resp;
    }

    //学生统计 // 姓名// 行政班级//学号//题库考试平均分//学习进度//在线时长//出勤率//提问//讨论//练习//签到总数//参与签到数//迟到//事假//公假//早退//病假//缺勤
    static String StudentStatisticsList = "https://user.icve.com.cn/patch/zhzj/teacherStatistics_getStudentStatisticsList.action";

    public static String getStudentStatisticsList(String date, String classId) {
        String data = "data=info&date=2022-08-6+~+2023-9-6" +
                "&keyName=&curPage=1&pageSize=10" +
                "&classId=" +classId+
                "&token=aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA%3D%3D";
        String resp = newZjyHttp.post(StudentStatisticsList, data);
        return resp;
    }

    //学生管理详情
    static String Teacher_ClassTrainee = "https://user.icve.com.cn/zhzj/zhzjTeacher_getTeacherClassTraineeListSPOC.action";

    public static String getTeacher_ClassTrainee(String courseId) {
        String data = "token=aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA%3D%3D" +
                "&courseId=" + courseId +
                "&keyName=&pageSize=200&currentPage=1";
        String resp = newZjyHttp.post(Teacher_ClassTrainee, data);
        return resp;
    }


    //学生详情列表
    static String BySqlCode = "https://user.icve.com.cn/learning/u/userDefinedSql/getBySqlCode.json";

    public static String getBySqlCode(String courseId) {
        String data = "data=info&page.searchItem.queryId=getTeacherCLssTraineeListSPOC" +
                "&page.searchItem.courseId=" + courseId +
                "&page.searchItem.keyname=" +
                "&page.curPage=1&page.pageSize=10";
        String resp = newZjyHttp.post(BySqlCode, data);
        return resp;
    }

    //学习进度
    static String learnRecord = "https://course.icve.com.cn/learnspace/course/study/learnRecord_teacherCount.action?pager.pageSize=100&pager.curPage=1";

    public static String getLearnRecord(String courseId) {
        String data = "courseId=" + courseId + "&params.userName=&params.loginId=" +
                "&params.classId=&params.label=";
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

    public static String getSaveCourseNotice(String courseId, String title, String note) {
        String data = "params.courseId=" + courseId + "___" +
                "&params.title=" + title +
                "&params.note=" + note + "&params.isTop=true";
        String resp = newZjyHttp.post(saveCourseNotice, data);
        return resp;
    }

    //老师课件设计
    static String TeachingDesign = "https://course.icve.com.cn/learnspace/course/design/courseTeachingDesign_intoTeachingDesign.action";

    //https://course.icve.com.cn/learnspace/course/design/courseTeachingDesign_intoTeachingDesign.action?courseId=39e272199dab487ba6f8f76115cbfd2c&editState=1
    public static String getTeachingDesign() {
        String data = "courseId=39e272199dab487ba6f8f76115cbfd2c" +
                "&params.cloudRoleId=" +
                "&params.cloudMetaId=&params.examRoleId=";
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
        String data = "itemId=99ffe49b4da24327b98c6f6409868ddb" +
                "&courseId=39e272199dab487ba6f8f76115cbfd2c";
        String resp = newZjyHttp.post(queryChildItem, data);
        return resp;
    }

    // 删除课件

    static String delItemById = "https://course.icve.com.cn/learnspace/course/design/designCourseItem_delItemById.action";

    public static String getDelItemById() {
        String data = "itemId=03fa7ab6e8564cc9baba3f4d90ad5589" +
                "&courseId=39e272199dab487ba6f8f76115cbfd2c";
        String resp = newZjyHttp.post(delItemById, data);
        return resp;
    }

    // 添加节
    static String addSectionCourseItem = "https://course.icve.com.cn/learnspace/course/design/designCourseItem_addSectionCourseItem.action";

    public static String getAddSectionCourseItem() {
        String data = "courseId=39e272199dab487ba6f8f76115cbfd2c" +
                "&parentId=5fe19e246d524044b8af39a2afda14e3&" +
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


    //老师api 题库作业  题库考试 随堂测验
    static String courseExamList = "https://user.icve.com.cn/zhzj/zhzjTeacher_courseExamList.action";

    public static String getCourseExamList(String token) {
        String data = "token=aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA%3D%3D" +
                "&searchName=" +
                "&pageNum=1&type=题库考试" +   //题库作业 随堂测验
                "&courseIdForChoose=" +
                "&examConfigTypeId=1&pageSize=200";

        String resp = newZjyHttp.post(courseExamList, data);
        return resp;
    }


    //未阅的作业 考试 测验
    static String UnCheckPaperList = "https://spoc-exam.icve.com.cn/teacher/exampaper/papercheck_queryUnCheckPaperList.action?pager.pageSize=100&pager.curPage=1&pager.searchTotalSize=true";

    public static String getUnCheckPaperList() {
        String data = "fetchTotalSize=true" +
                "&params.examId=4028824d82feea2d018303f382b2217f" +
                "&params.classId=&params.searchKeyWord=" +
                "&pager.condition.paperOrUserName=" +
                "&pager.condition.ipAddress=" +
                "&pager.condition.examId=4028824d82feea2d018303f382b2217f" +
                "&pager.condition.classId=";
        String resp = newZjyHttp.post(UnCheckPaperList, data);
        return resp;
    }

    //已阅的作业 考试 测验
    static String CheckedPaperList = "https://spoc-exam.icve.com.cn/teacher/exampaper/papercheck_queryCheckedPaperList.action?pager.pageSize=100&pager.curPage=1&pager.searchTotalSize=true";

    public static String getCheckedPaperList() {
        String data = "fetchTotalSize=true" +
                "&params.examId=4028824d82feea2d018303f382b2217f" +
                "params.classId=&pager.condition.examId=4028824d82feea2d018303f382b2217f" +
                "&pager.condition.classId=" +
                "&pager.condition.paperOrUserName=" +
                "&pager.condition.paperStatus=-1";
        String resp = newZjyHttp.post(CheckedPaperList, data);
        return resp;
    }


    //作业 考试 测验的成绩列表
    static String ScorePaperList = "https://spoc-exam.icve.com.cn/teacher/exampaper/papercheck_queryScorePaperList.action?pager.pageSize=100&pager.curPage=1&pager.searchTotalSize=true";

    public static String getScorePaperList() {
        String data = "fetchTotalSize=true" +
                "&params.examId=4028824d82feea2d018303f382b2217f" +
                "&params.classId=&pager.condition.userNameOrLoginId=" +
                "&pager.condition.checkUserName=";
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
        String data = "recordId=4e283d8c6d9c474188f0629ef9a8e39a" +
                "&examBatchId=4028824d82feea2d018303f382b2217f" +
                "&contentIds=6d39be8eb4e447759ec62e8cf75d9389%2C11540cffebd64092940de3701ddcaa69%2C14ef32b18a734650b4aca25836504412" +
                "&params.monitor=";
        String resp = newZjyHttp.post(RecordContent, data);
        return resp;
    }

    //答案
    private static String PaperStructureForPreview = "https://spoc-exam.icve.com.cn/testpaper/paper_getPaperStructureForPreview.action";

    public static String getPaperStructureForPreview(String paperId) {
        String data = "params.paperId=" + paperId;
        String resp = newZjyHttp.post(PaperStructureForPreview, data);
        return resp;
    }

    //答案
    static String PaperStructure = "https://spoc-exam.icve.com.cn/exam/statistics/examPaperContentStatistics_getPaperStructure.action";

    public static String getPaperStructure(String paperId) {
        String data = "paperId=" + paperId;
        //newZjyHttp.addCookie("USERSESSIONID=402883e682f4d8ea0182f7123f521677#interface#batchCode#attachData;");
        String resp = newZjyHttp.post(PaperStructure, data);
        return resp;
    }


    //
    static String ExamPaperStatisticsDetail = "https://spoc-exam.icve.com.cn/exam/statistics/examPaperContentStatistics_getExamPaperStatisticsDetail.action";

    public static String getExamPaperStatisticsDetail(String examId, String paperId) {
        String data = "examId=" + examId +
                "&paperId=" + paperId;
        String resp = newZjyHttp.post(ExamPaperStatisticsDetail, data);
        return resp;
    }

    //试卷学生管理 锁定 解锁
    static String examBatch_ExamStudentList="https://spoc-exam.icve.com.cn/college/exam/examBatch_getExamStudentList.action?pager.pageSize=200&pager.curPage=1&pager.searchTotalSize=true";
    public static String getExamBatch_ExamStudentList(String batchId){
        String data = "batchId="+batchId +
                "&pager.condition.studentName=&pager.condition.loginId=" +
                "&pager.condition.examStatus=&pager.condition.hasPhoto=&pager.sortType=" +
                "&pager.sortColumn=&params.examConfigTypeId=";

        String resp = newZjyHttp.post(examBatch_ExamStudentList, data);
        return resp;
    }


    //试卷锁定 stuSsoId
    static String examBatch_toggleStudentStatus="https://spoc-exam.icve.com.cn/college/exam/examBatch_toggleStudentStatus.action";

    public static String getExamBatch_toggleStudentStatus(String operate,String ersId,String stuSsoId,String batchId){
        String data = "params.operate="+operate +
                "&params.target=lockPaper" +
                "&params.ersId="+ersId +
                "&params.stuSsoId=" +stuSsoId+
                "&params.batchId="+batchId;

        String resp = newZjyHttp.post(examBatch_toggleStudentStatus, data);
        return resp;
    }

      public static String getExamBatch_toggleStudentLock(String ersId,String stuSsoId,String batchId){

        String resp = getExamBatch_toggleStudentStatus("lock",  ersId, stuSsoId, batchId);
        return resp;
    }


      public static String getExamBatch_toggleStudentUnLock(String ersId,String stuSsoId,String batchId){

        String resp = getExamBatch_toggleStudentStatus("unlock",  ersId, stuSsoId, batchId);
        return resp;
    }



    //失效
    //考试 作业 测试 重批改分
    static String saveScore = "https://spoc-exam.icve.com.cn/teacher/exampaper/papercheck_saveScoreAndFinishCheck.action";

    public static String getSaveScore(String recordId,String str) {
        String data = "recordId="+recordId +str;
        String resp = newZjyHttp.post(saveScore, data);
        return resp;
    }


    //获取试卷设置详情
    static String examBatchExt_updateExam="https://spoc-exam.icve.com.cn/college/exam/examBatchExt_updateExam.action";
    public static String getExamBatchExt_updateExam(String batchId){
        String data = "batchId=" +batchId;
        String resp = newZjyHttp.get(examBatchExt_updateExam, data);
        return resp;
    }


    //获取编辑试卷详情
    static String examBatchExt_editExam="https://spoc-exam.icve.com.cn/college/exam/examBatchExt_editExam.action";

    public static String getExamBatchExt_editExam(String batchId){
        String data = "batchId=" +batchId+
                "&params.openDetail=0"+batchId;

        String resp = newZjyHttp.post(examBatchExt_editExam, data);
        return resp;
    }


    //修改考试 作业 测试 的时间
    static String examBatchExt_updateForAjax="https://spoc-exam.icve.com.cn/college/exam/examBatchExt_updateForAjax.action";

    public static String getExamBatchExt_updateForAjax(String batchId){
        //完整版
        //bean.id: 402883ad82fee5a601831397f14c42d5
        //bean.name: 99999999
        //bean.planCloseTime: //2023-01-07 23:59:59
        // bean.planOpenTime://2022-09-07 00:16:49
        //bean.examTime://120
        //bean.forbiddenTime://2023-01-07 23:59:59
        //params.hasCourse:
        //bean.courseId://39e272199dab487ba6f8f76115cbfd2c
        //bean.maxExamTimes://0
        //bean.antiCheatType://{"rq":"1","ro":"1","lplc":"-1"}
        //bean.earlyEnterFdTime://2022-08-31 01:37:49
        //bean.minSubmitPaperTime://0
        //bean.resultViewCondition://{"totalScore":"1","eachScore":"1","scoreLimit":"0","answer":"1","answerLimit":"0"}
        //bean.remark:

        String data = "bean.id="+batchId +
                "&bean.name=ppppppppppppppp" +
                "&bean.planCloseTime=2022-10-29+23%3A59%3A59" +
                "&bean.planOpenTime=2022-10-01+16%3A44%3A27" +
                "&params.hasCourse=" +
                "&bean.courseId=39e272199dab487ba6f8f76115cbfd2c" +
                "&bean.maxExamTimes=0" +
                "&bean.resultViewCondition={\"totalScore\":\"1\",\"eachScore\":\"1\",\"scoreLimit\":\"2\",\"answer\":\"1\",\"answerLimit\":\"0\"}" +
                "&bean.remark=";

        String resp = newZjyHttp.post(examBatchExt_updateForAjax, data);
        return resp;
    }



    //题库答案
    static String questionManage = "https://spoc-exam.icve.com.cn/question/questionManage_getQuestionList.action?pager.pageSize=100&pager.curPage=1&pager.searchTotalSize=true";

    public static String getQuestionManage(String bankId) {
        String data = "params.qTypeIds=&params.qTitle=&params.attrValue=%7B%7D" +
                "&params.bankId=217343996a7546eb9a2d47801791adb3" +
                "&params.tags=&params.courseId=";
        String resp = newZjyHttp.post(questionManage, data);
        return resp;
    }
}

