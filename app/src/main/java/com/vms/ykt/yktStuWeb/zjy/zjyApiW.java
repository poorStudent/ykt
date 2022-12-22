package com.vms.ykt.yktStuWeb.zjy;

import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.yktStuMobile.zjy.*;

import java.io.Serializable;

public  class zjyApiW  {


    static String loginWeb = "https://zjy2.icve.com.cn/api/common/login/login";
    static String verifycodeUrl = "https://zjy2.icve.com.cn/api/common/VerifyCode/index?t=0.26473944313076414";
    static String FaceTeach = "https://security.zjy2.icve.com.cn/api/study/faceTeachInfo/getFaceTeachSchedule";

    static String ActivityInfo = "https://security.zjy2.icve.com.cn/api/study/faceTeachInfo/faceTeachActivityInfo";

    static String StuInfo = "https://zjy2.icve.com.cn/api/student/stuInfo/getStuInfo";
    static String LearnningCourseList = "https://zjy2.icve.com.cn/api/student/learning/getLearnningCourseList";
    static String ProcessList = "https://zjy2.icve.com.cn/api/study/process/getProcessList";
    static String TopicByModuleId = "https://zjy2.icve.com.cn/api/study/process/getTopicByModuleId";
    static String CellByTopicId = "https://zjy2.icve.com.cn/api/study/process/getCellByTopicId";
    static String ProcessModulePower = "https://zjy2.icve.com.cn/api/study/process/hasProcessModulePower";
    static String viewDirectory = "https://zjy2.icve.com.cn/api/common/Directory/viewDirectory";
    static String changeData = " https://zjy2.icve.com.cn/api/common/Directory/changeStuStudyProcessCellData";
    static String stuProcessCellLog = "https://zjy2.icve.com.cn/api/common/Directory/stuProcessCellLog";
    static String addCellActivity = "https://zjy2.icve.com.cn/api/common/Directory/addCellActivity";
    static String HomeworkList = "https://zjy2.icve.com.cn/api/study/homework/getHomeworkList";
    static String MyHomeworkList = "https://zjy2.icve.com.cn/api/student/myHomework/getMyHomeworkList";
    static String OnlineExamList = "https://zjy2.icve.com.cn/api/study/onlineExam/getOnlineExamList";
    static String evaluationEdit = "https://security.zjy2.icve.com.cn/api/study/faceTeachInfo/evaluationEdit";
    static String evaluationSave = "https://security.zjy2.icve.com.cn/api/study/faceTeachInfo/evaluationSave";
    static String selfratingEdit = "https://security.zjy2.icve.com.cn/api/study/faceTeachInfo/selfratingEdit";
    static String selfratingSave = "https://security.zjy2.icve.com.cn/api/study/faceTeachInfo/selfratingSave";

    /**
     * {"code":1,"avator":"https://file.icve.com.cn/ssykt/239/577/3BC9050A1EA87429401CAC2B6A3F7373.jpg?x-oss-process=image/resize,m_fixed,w_110,h_110,limit_0",
     * "schoolId":"tim5ar6oglbho7fmp6lvia","disPlayName":"魏海旭",
     * "stuNo":"2034060125","UserName":"venomms",
     * "statData":{"unReadMsgCount":0,"faceTeachDayCount":0,"faceTeachWeekCount":14,
     * announcementCount":2,"myCourseCount":28,"myHomeworkCount":164,"myExamCount":7,
     * "onlineActivityCount":3591,"groupTaskCount":0},"stuId":"2w7jafiswazbrev468vb5q",
     * "extraData":{"IsExtraJXSX":0,"IsExtraZYJS":0,"IsExtraJXPJ":0,"IsExtraEXAM":0},
     * integrity":7,
     * "moocServer":"https://mooc.icve.com.cn","isMerge":0,"isOpenLite":"1"}
     **/
    public static String getStuInfo() {
        String resp = "";
        String Referer = "https://zjy2.icve.com.cn/student/stuInfo/stuInfo.html";

        StringBuilder postParam = new StringBuilder();
        String postParams = postParam.toString();

        resp = zjyHttpW.post(zjyApiW.StuInfo, postParams, Referer);

        return resp;
    }

    public static String getLearnningCourseList() {
        String resp = "";
        String Referer = "https://zjy2.icve.com.cn/student/learning/courseList.html?type=1";

        StringBuilder postParam = new StringBuilder();
        postParam.append("type=1");
        String postParams = postParam.toString();

        resp = zjyHttpW.post(zjyApiW.LearnningCourseList, postParams, Referer);

        return resp;
    }

    public static String getProcessList(zjyCourseIfno zjyCourseIfno) {
        String resp = "";
        // String Referer = "https://zjy2.icve.com.cn/student/learning/courseList.html?type=1";
/**{"code":1,"courseOpenId":"uoqaoksbixovw0s4anrhq","openClassId":"5zogauaufrvd1yb5ansr4w","openCourseCellCount":2173,
 * "stuStudyCourseOpenCellCount":417,
 * "progress":{"moduleList":
 * [{"id":"712taoksc7ha5seeotsj4w","name":"01毛泽东思想及历史地位","sortOrder":1,"percent":100.0},
 * {"id":"712taoksg5phmvpl39ba","name":"02新民主主义革命理论","sortOrder":2,"percent":100.0},
 * {"id":"712taoksvkjk8r1eeic22w","name":"03社会主义改造理论","sortOrder":3,"percent":100.0},{"id":"712taoksbkjprotfr6ybhq","name":"04社会主义建设道路初步探索的理论成果","sortOrder":4,"percent":3.0},{"id":"812taoksepddopuqq6byvw","name":"05邓小平理论","sortOrder":5,"percent":0.0},{"id":"812taokspz5f3zzowmrlwq","name":"06“三个代表”重要思想 ","sortOrder":6,"percent":0.0},{"id":"812taoksqy9kqe4lhwljvq","name":"07科学发展观","sortOrder":7,"percent":0.0},{"id":"812taoksnktpbqjc4ims9a","name":"08习近平新时代中国特色社会主义思想及其历史地位","sortOrder":8,"percent":0.0},{"id":"812taoksfznd5fsemnhgkq","name":"09坚持和发展中国特色社会主义的总任务","sortOrder":9,"percent":0.0},{"id":"812taoks7lxcferetllyga","name":"10“五位一体”总体布局","sortOrder":10,"percent":0.0},{"id":"812taoks7bnphlup6szzoa","name":"11“四个全面”战略布局","sortOrder":11,"percent":0.0},{"id":"812taokswlnkhwayaishow","name":"12全面推进国防和军队现代化","sortOrder":12,"percent":0.0},{"id":"812taokstivj9ief9csvnq","name":"13中国特色大国外交","sortOrder":13,"percent":0.0},{"id":"812taoksalza9juxowkh8w","name":"14坚持和加强党的领导","sortOrder":14,"percent":0.0},{"id":"bq6vaokssjpfd4co8brrsa","name":"前言 马克思主义中国化及其理论成果","sortOrder":15,"percent":0.0},{"id":"bq6vaoksz6zjodhtmevbw","name":"第一章  毛泽东思想及其历史地位","sortOrder":16,"percent":0.0},{"id":"alwwaoksdifdvfopikyyg","name":"第一章 毛泽东思想的形成及其历史地位","sortOrder":17,"percent":0.0},{"id":"pgohapcsjkhktic948at0g","name":"第二章  新民主主义革命理论","sortOrder":18,"percent":0.0},{"id":"qrahapcsxabitzhwvnv8q","name":"第二章 新民主主义革命理论","sortOrder":19,"percent":0.0},{"id":"dqgeapesvzjlg361scyxnq","name":"第三章 社会主义改造理论","sortOrder":20,"percent":0.0},{"id":"hycapusdzzcvw12yicwa","name":"第八章  习近平新时代中国特色社会主义思想及其历史地位","sortOrder":21,"percent":0.0},{"id":"feyjapusezzdwkjt17ddw","name":"九、习近平新时代中国特色社会主义思想及其历史地位","sortOrder":22,"percent":0.0},{"id":"ycukaqits7fezv6bofokaq","name":"第九章  坚持和发展中国特色社会主义的总任务","sortOrder":23,"percent":0.0},
 * {"id":"orsnabotjqffsyvsvmadwg","name":"第五章 邓小平理论","sortOrder":24,"percent":0.0},{"id":"sgbackteapoyrczzlou0g","name":"第六章 “三个代表”重要思想","sortOrder":25,"percent":0.0},{"id":"ej7aesuw6pn7rrxzxdbq","name":"01毛泽东思想及历史地位","sortOrder":26,"percent":0.0}],"moduleId":"712taoksc7ha5seeotsj4w"}}**/
        StringBuilder postParam = new StringBuilder();
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId());
        String postParams = postParam.toString();

        resp = zjyHttpW.post(zjyApiW.ProcessList, postParams, null);

        return resp;
    }

    public static String getTopicByModuleId(zjyCourseIfno zjyCourseIfno, zjyModuleListInfo zjyModuleListInfo) {

        //courseOpenId=uoqaoksbixovw0s4anrhq&moduleId=712taoksc7ha5seeotsj4w
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("moduleId=" + zjyModuleListInfo.getId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId());
        String postParams = postParam.toString();

        resp = zjyHttpW.post(zjyApiW.TopicByModuleId, postParams, null);

        return resp;
    }

    public static String getCellByTopicId(zjyCourseIfno zjyCourseIfno, zjyTopicList TopicInfos) {

        //courseOpenId=uoqaoksbixovw0s4anrhq&moduleId=712taoksc7ha5seeotsj4w
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("topicId=" + TopicInfos.getId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId());
        String postParams = postParam.toString();

        resp = zjyHttpW.post(zjyApiW.CellByTopicId, postParams, null);

        return resp;
    }

    //课件是否公开
    public static  String getProcessModulePower(zjyCourseIfno zjyCourseIfno, zjyModuleListInfo zjyModuleListInfo, zjyCellList zjyCellList) {

        //courseOpenId=uoqaoksbixovw0s4anrhq&openClassId=5zogauaufrvd1yb5ansr4w&cellId=812taoksmzzivwfcyf1qca&flag=s&moduleId=812taoksepddopuqq6byvw
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + zjyCellList.getId() + "&");
        postParam.append("moduleId=" + zjyModuleListInfo.getId() + "&");
        postParam.append("flag=s" + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId());
        String postParams = postParam.toString();

        resp = zjyHttpW.post(zjyApiW.ProcessModulePower, postParams, null);

        return resp;
    }

    //获取课件详情
    public static String getviewDirectory(zjyCourseIfno zjyCourseIfno, zjyModuleListInfo zjyModuleListInfo, zjyCellList zjyCellList) {

        //courseOpenId=uoqaoksbixovw0s4anrhq&openClassId=5zogauaufrvd1yb5ansr4w&cellId=712taoksfq5dulkk00ljw&flag=s&moduleId=712taoksvkjk8r1eeic22w
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + zjyCellList.getId() + "&");
        postParam.append("moduleId=" + zjyModuleListInfo.getId() + "&");
        postParam.append("flag=s" + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId());
        String postParams = postParam.toString();

        resp = zjyHttpW.post(zjyApiW.viewDirectory, postParams, null);

        return resp;
    }

   public static String getviewDirectory(zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellList) {

        //courseOpenId=uoqaoksbixovw0s4anrhq&openClassId=5zogauaufrvd1yb5ansr4w&cellId=712taoksfq5dulkk00ljw&flag=s&moduleId=712taoksvkjk8r1eeic22w
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + zjyCellList.getId() + "&");
        postParam.append("moduleId=" + zjyCellList.getModID() + "&");
        postParam.append("flag=s" + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId());
        String postParams = postParam.toString();
        resp = zjyHttpW.post(zjyApiW.viewDirectory, postParams, null);

        return resp;
    }

    //切换课件
    public static String getChangeData(zjyCourseIfno zjyCourseIfno, zjyModuleListInfo zjyModuleListInfo, zjyCellList zjyCellList) {

        /**courseOpenId=uoqaoksbixovw0s4anrhq&openClassId=5zogauaufrvd1yb5ansr4w&
         * moduleId=812taoksepddopuqq6byvw&cellId=812taoksmzzivwfcyf1qca&
         * cellName=5.1+%E5%AF%BC%E8%AE%BA+%E9%82%93%E5%B0%8F%E5%B9%B3%E6%98%AF%E4%B8%AD%E5%9B%BD%E7%89%B9%E8%89%B2%E7%A4%BE%E4%BC%9A%E4%B8%BB%E4%B9%89%E7%90%86%E8%AE%BA%E7%9A%84%E5%BC%80%E5%88%9B%E8%80%85 **/
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + zjyCellList.getId() + "&");
        postParam.append("moduleId=" + zjyModuleListInfo.getId() + "&");
        postParam.append("cellName=" + zjyCellList.getCellName() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId());
        String postParams = postParam.toString();

        resp = zjyHttpW.post(zjyApiW.changeData, postParams, null);

        return resp;
    }

    public static String getChangeData(zjyCourseIfno zjyCourseIfno,zjyCellList zjyCellList) {

        /**courseOpenId=uoqaoksbixovw0s4anrhq&openClassId=5zogauaufrvd1yb5ansr4w&
         * moduleId=812taoksepddopuqq6byvw&cellId=812taoksmzzivwfcyf1qca&
         * cellName=5.1+%E5%AF%BC%E8%AE%BA+%E9%82%93%E5%B0%8F%E5%B9%B3%E6%98%AF%E4%B8%AD%E5%9B%BD%E7%89%B9%E8%89%B2%E7%A4%BE%E4%BC%9A%E4%B8%BB%E4%B9%89%E7%90%86%E8%AE%BA%E7%9A%84%E5%BC%80%E5%88%9B%E8%80%85 **/
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + zjyCellList.getCellId() + "&");
        postParam.append("moduleId=" + zjyCellList.getModID() + "&");
        postParam.append("cellName=" + zjyCellList.getCellName() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId());
        String postParams = postParam.toString();
        resp = zjyHttpW.post(zjyApiW.changeData, postParams, null);

        return resp;
    }

    //刷课
    public static String getProcessCellLog(ViewDirectory zjyViewDirectory, String time) {

        /**courseOpenId=uoqaoksbixovw0s4anrhq&openClassId=5zogauaufrvd1yb5ansr4w&
         * cellId=712taoksfq5dulkk00ljw&cellLogId=&picNum=1&studyNewlyTime=0&studyNewlyPicNum=999&
         * token=ucjkaicu9l9kl8hpeddva**/
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + zjyViewDirectory.getCellId() + "&");
        postParam.append("cellLogId=" + zjyViewDirectory.getCellLogId() + "&");
        postParam.append("token=" + zjyViewDirectory.getGuIdToken() + "&");
        postParam.append("studyNewlyTime=" + time + "&");
        postParam.append("picNum=" + zjyViewDirectory.getPageCount()+ "&");
        postParam.append("studyNewlyPicNum=" + zjyViewDirectory.getPageCount() + "&");
        postParam.append("openClassId=" + zjyViewDirectory.getOpenClassId() + "&");
        postParam.append("courseOpenId=" + zjyViewDirectory.getCourseOpenId());
        String postParams = postParam.toString();

        resp = zjyHttpW.post(zjyApiW.stuProcessCellLog, postParams, null);

        return resp;
    }

    public static String getProcessCellLog( ViewDirectory zjyViewDirectory,zjyCellList zjyCellList, String time) {

        /**courseOpenId=uoqaoksbixovw0s4anrhq&openClassId=5zogauaufrvd1yb5ansr4w&
         * cellId=712taoksfq5dulkk00ljw&cellLogId=&picNum=1&studyNewlyTime=0&studyNewlyPicNum=999&
         * token=ucjkaicu9l9kl8hpeddva**/
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + zjyCellList.getCellId() + "&");
        postParam.append("cellLogId=" + zjyViewDirectory.getCellLogId() + "&");
        postParam.append("token=" + zjyViewDirectory .getGuIdToken() + "&");
        postParam.append("studyNewlyTime=" + time + "&");
        postParam.append("picNum=" + zjyViewDirectory.getPageCount()+ "&");
        postParam.append("studyNewlyPicNum=" + zjyViewDirectory.getPageCount() + "&");
        postParam.append("openClassId=" + zjyViewDirectory .getOpenClassId() + "&");
        postParam.append("courseOpenId=" + zjyViewDirectory .getCourseOpenId());
        String postParams = postParam.toString();

        resp = zjyHttpW.post(zjyApiW.stuProcessCellLog, postParams, null);

        return resp;
    }


    //课件评价
    public static String getaddCellActivity(String OpenClassId, String CourseOpenId, String zjyCellList, String content, String activityType) {
//课件评论
        //courseOpenId=myhoaf2rm5rmavmm2x5ig&openClassId=wobtaugukybmifjjvaxwta&
        //cellId=5h13awrhjdmgnhnxpv3kg&content=%E5%A5%BD&docJson=&star=0&activityType=4
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("cellId=" + zjyCellList + "&");
        postParam.append("content=" + content + "&");
        postParam.append("docJson=" + "&");
        postParam.append("star=5" + "&");
        postParam.append("activityType=" + activityType + "&");
        postParam.append("openClassId=" + OpenClassId + "&");
        postParam.append("courseOpenId=" + CourseOpenId);
        String postParams = postParam.toString();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ParmsE) {
            ParmsE.printStackTrace();
        }

        resp = zjyHttpW.post(zjyApiW.addCellActivity, postParams, null);

        return resp;
    }

    //课件评价
    public static String getaddCellActivity1_1(zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellList, String content) {
        return getaddCellActivity(zjyCourseIfno.getOpenClassId(), zjyCourseIfno.getCourseOpenId(), zjyCellList.getId(), content, "1");
    }

    public static String getaddCellActivity1_2(zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellList, String content) {
        return getaddCellActivity(zjyCourseIfno.getOpenClassId(), zjyCourseIfno.getCourseOpenId(), zjyCellList.getId(), content, "2");
    }

    public static String getaddCellActivity1_3(zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellList, String content) {
        return getaddCellActivity(zjyCourseIfno.getOpenClassId(), zjyCourseIfno.getCourseOpenId(), zjyCellList.getId(), content, "3");
    }

    public static String getaddCellActivity1_4(zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellList, String content) {
        return getaddCellActivity(zjyCourseIfno.getOpenClassId(), zjyCourseIfno.getCourseOpenId(), zjyCellList.getId(), content, "4");
    }

    //课件评价
    public static String getaddCellActivity2_1(ViewDirectory zjyViewDirectory, String content) {
        return getaddCellActivity(zjyViewDirectory.getOpenClassId(), zjyViewDirectory.getCourseOpenId(), zjyViewDirectory.getCellId(), content, "1");
    }

    public static String getaddCellActivity2_2(ViewDirectory zjyViewDirectory, String content) {
        return getaddCellActivity(zjyViewDirectory.getOpenClassId(), zjyViewDirectory.getCourseOpenId(), zjyViewDirectory.getCellId(), content, "2");
    }

    public static String getaddCellActivity2_3(ViewDirectory zjyViewDirectory, String content) {
        return getaddCellActivity(zjyViewDirectory.getOpenClassId(), zjyViewDirectory.getCourseOpenId(), zjyViewDirectory.getCellId(), content, "3");
    }

    public static String getaddCellActivity2_4(ViewDirectory zjyViewDirectory, String content) {
        return getaddCellActivity(zjyViewDirectory.getOpenClassId(), zjyViewDirectory.getCourseOpenId(), zjyViewDirectory.getCellId(), content, "2");
    }

//获取课堂
    public static String getfaceTeachInfo(String date, String json) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("currentTime=" + date + "&");
        postParam.append("calendar=week" + "&");
        postParam.append(json);
        String postParams = postParam.toString();
        resp = zjyHttpW.post(zjyApiW.FaceTeach, postParams, null);

        return resp;
    }
//课后评价
    public static boolean getevaluationEdit(zjyCourseIfno zjyCourseIfno, zjyTeachInfo ZjyAllTeachInfo) {

        return getEdit(zjyApiW.evaluationEdit, zjyCourseIfno, ZjyAllTeachInfo);
    }

    public static String getevaluationSave(zjyCourseIfno zjyCourseIfno, zjyTeachInfo ZjyAllTeachInfo, String stuContent) {

            return getSave(zjyApiW.evaluationSave, zjyCourseIfno, ZjyAllTeachInfo, stuContent);

    }

    public static boolean getselfratingEdit(zjyCourseIfno zjyCourseIfno, zjyTeachInfo ZjyAllTeachInfo) {
        return getEdit(zjyApiW.selfratingEdit, zjyCourseIfno, ZjyAllTeachInfo);

    }

    public static String getselfratingSave(zjyCourseIfno zjyCourseIfno, zjyTeachInfo ZjyAllTeachInfo, String stuContent) {
        return getSave(zjyApiW.selfratingSave, zjyCourseIfno, ZjyAllTeachInfo, stuContent);

    }

    private static  boolean getEdit(String url, zjyCourseIfno zjyCourseIfno, zjyTeachInfo ZjyAllTeachInfo) {
        String resp = "";

        StringBuilder postParam = new StringBuilder();

        postParam.append("activityId=" + ZjyAllTeachInfo.getId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId());
        String postParams = postParam.toString();
        resp = zjyHttpW.post(url, postParams, null);
        if (resp.isEmpty()) return true;
        System.out.println(resp);
        if (JSONObject.parseObject(resp).getString("data").isEmpty()) {
            return true;
        }
        String Content = JSONObject.parseObject(resp).getJSONObject("data").getString("Content");
        String Star = JSONObject.parseObject(resp).getJSONObject("data").getString("Star");
        if (!Content.isEmpty() && Star.equals("5")) {
            return false;
        }
        return true;
    }


    public static String getSave(String url, zjyCourseIfno zjyCourseIfno, zjyTeachInfo ZjyAllTeachInfo, String stuContent) {
        StringBuilder postParam = new StringBuilder();
        // activityId=rvs5axmuq7zbmv8hxrwpw&courseOpenId=myhoaf2rm5rmavmm2x5ig&openClassId=wobtaugukybmifjjvaxwta&stuContent=%E5%A5%BD&star=5
        postParam.append("activityId=" + ZjyAllTeachInfo.getId() + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("stuContent=" + stuContent + "&");
        postParam.append("star=5" + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId());
        String postParams = postParam.toString();
        String resp = zjyHttpW.post(url, postParams, null);
        if (resp==null||!resp.contains("msg")) return "异常";
        resp = JSONObject.parseObject(resp).getString("msg");
        return resp;
    }


    /**
     * 课堂活动
     */
    public static String getActivityInfo(zjyTeachInfo ZjyDayTeachInfo, String type) {
        //  type=3&courseOpenId=z8pjaeuulrbdnflicdpg&openClassId=mty7au2uq6hay0t4yghww&activityId=nmg4ax6u7jvkoqkpqpddyw
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        // activityId=rvs5axmuq7zbmv8hxrwpw&courseOpenId=myhoaf2rm5rmavmm2x5ig&openClassId=wobtaugukybmifjjvaxwta&stuContent=%E5%A5%BD&star=5
        postParam.append("activityId=" + ZjyDayTeachInfo.getId() + "&");
        postParam.append("openClassId=" + ZjyDayTeachInfo.getOpenClassId() + "&");
        postParam.append("type=" + type + "&");
        postParam.append("courseOpenId=" + ZjyDayTeachInfo.getCourseOpenId());
        String postParams = postParam.toString();
        resp = zjyHttpW.post(zjyApiW.ActivityInfo, postParams, null);
        return resp;


    }

    static String stuSign = "https://security.zjy2.icve.com.cn/api/study/faceTeachInfo/stuSign";

    //签到获取
    public static String getSaveSign(String tid, String code, zjyUser zjyUser, zjyTeachInfo zjyDayTeachInfo) {
        //签到
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("signId=" + tid + "&");
        postParam.append("activityId=" + zjyDayTeachInfo.getId() + "&");
        postParam.append("openClassId=" + zjyDayTeachInfo.getOpenClassId() + "&");
        postParam.append("courseOpenId=" + zjyDayTeachInfo.getCourseOpenId() + "&");
        postParam.append("stuId=" + zjyUser.getUserId() + "&");
        postParam.append("checkInCode=" + code);
        String postParams = postParam.toString();
        resp = zjyHttpW.post(stuSign, postParams, null);
        return resp;
    }
//课堂活动
    public static String getActivityType1(zjyTeachInfo ZjyDayTeachInfo) {

        String resp = "";
        resp = getActivityInfo(ZjyDayTeachInfo, "1");
        return resp;


    }

    public static String getActivityType2(zjyTeachInfo ZjyDayTeachInfo) {

        String resp = "";
        resp = getActivityInfo(ZjyDayTeachInfo, "2");
        return resp;


    }

    public static String getActivityType3(zjyTeachInfo ZjyDayTeachInfo) {

        String resp = "";
        resp = getActivityInfo(ZjyDayTeachInfo, "3");
        return resp;

    }

    //获取作业
    public static String getHomeworkList(zjyCourseIfno zjyCourseIfno) {
        String resp = "";
        // String Referer = "https://zjy2.icve.com.cn/student/learning/courseList.html?type=1";

        StringBuilder postParam = new StringBuilder();
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId());
        String postParams = postParam.toString();
        resp = zjyHttpW.post(zjyApiW.HomeworkList, postParams, null);

        return resp;
    }

    //获取考试
    public static String getOnlineExamList(zjyCourseIfno zjyCourseIfno) {
        String resp = "";
        // String Referer = "https://zjy2.icve.com.cn/student/learning/courseList.html?type=1";

        StringBuilder postParam = new StringBuilder();
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId());
        String postParams = postParam.toString();

        resp = zjyHttpW.post(zjyApiW.OnlineExamList, postParams, null);

        return resp;
    }

    //打开作业
    static String preview = "https://security.zjy2.icve.com.cn/api/study/homework/preview";

    public static String getPreviewU(zjyCourseIfno zjyCourseIfno, String homeworkId, String homeworkTermTimeId) {
        /**获取作业题目**/
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("homeworkId=" + homeworkId + "&");
        postParam.append("homeworkTermTimeId=" + homeworkTermTimeId + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("viewType=1" + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId());
        String postParams = postParam.toString();

        resp = zjyHttpW.post(preview, postParams, null);
        return resp;
    }

    public static String getPreview(zjyCourseIfno zjyCourseIfno, HomeworkInfo homework) {

        return getPreviewU(zjyCourseIfno, homework.getHomeworkId(), homework.getHomeworkTermTimeId());
    }

    static String homeworkDetail = "https://security.zjy2.icve.com.cn/api/study/homework/detail";

    public static String homeworkDetail(zjyCourseIfno zjyCourseIfno, String homeworkId, String homeworkTermTimeId) {
        /**获取作业题目**/
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("homeworkId=" + homeworkId + "&");
        postParam.append("homeworkTermTimeId=" + homeworkTermTimeId + "&");
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("viewType=1" + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId());
        String postParams = postParam.toString();

        resp = zjyHttpW.post(homeworkDetail, postParams,null);
        return resp;
    }

    //打开考试
    static String previewNew = "https://security.zjy2.icve.com.cn/api/study/onlineExam/previewNew";

    public static String getPreviewNew(zjyCourseIfno zjyCourseIfno, ExamInfo ZjyExamInfo) {
        return getPreviewNewU(ZjyExamInfo.getExamId(), ZjyExamInfo.getExamTermTimeId(), zjyCourseIfno.getOpenClassId(), zjyCourseIfno.getCourseOpenId());
    }

    public static String getPreviewNewU(String examId, String examTimeId, String openClassId, String courseOpenId) {
        /**获取考试题目**/
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("examId=" + examId + "&");
        postParam.append("examTimeId=" + examTimeId + "&");
        postParam.append("openClassId=" + openClassId + "&");
        postParam.append("courseOpenId=" + courseOpenId);
        String postParams = postParam.toString();
        resp = zjyHttpW.post(previewNew, postParams,null);
        return resp;
    }
}




