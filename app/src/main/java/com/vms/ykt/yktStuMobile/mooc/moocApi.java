package com.vms.ykt.yktStuMobile.mooc;


import android.telephony.CellInfo;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class moocApi {
    public static String appv = "";
    static String MyCourseList = "https://moocapp.icve.com.cn/mobile/courseinfo/getMyCourseList";
    static String addCourseStudy="https://moocapp.icve.com.cn/mobile/coursedetail/addCourseStudy";
    static String ProcessList = "https://moocapp.icve.com.cn/mobile/coursedetail/getProcessList";
    static String TopicList = "https://moocapp.icve.com.cn/mobile/coursedetail/getTopicList";
    static String CellList = "https://moocapp.icve.com.cn/mobile/coursedetail/getCellList";
    static String viewDirectory = "https://moocapp.icve.com.cn/mobile/coursestudy/viewDirectory";
    static String StuProcessCell = "https://moocapp.icve.com.cn/mobile/coursestudy/statStuProcessCellLogAndTimeLong";
    static String learningTimeLong = "https://moocapp.icve.com.cn/mobile/coursestudy/computatlearningTimeLong";

    static String deleteTopic="https://moocapp.icve.com.cn/mobile/coursestudy/deleteTopic";
    static String getsaveAllReply = "https://moocapp.icve.com.cn/mobile/coursestudy/saveAllReply";
    static String addPublishTopic="https://moocapp.icve.com.cn/mobile/coursestudy/addPublishTopic";
    static String getDiscussTopicNew="https://moocapp.icve.com.cn/mobile/coursestudy/getDiscussTopicNew";
    static String getCourseNewsNew="https://moocapp.icve.com.cn/mobile/coursedetail/getCourseNewsNew";
    static String getDetailCourseNewsNew="https://moocapp.icve.com.cn/mobile/coursedetail/getDetailCourseNewsNew";
    static String DiscussNewsNew="https://moocapp.icve.com.cn/mobile/coursedetail/getDiscussNewsNew";
    static String CommentData="https://moocapp.icve.com.cn/mobile/coursestudy/getCellCommentData";
    static String addTopicRepl="https://moocapp.icve.com.cn/mobile/coursestudy/addTopicReply";
    static String DiscussReply="https://moocapp.icve.com.cn/mobile/coursestudy/getDiscussReply";

    static String workexam="https://moocapp.icve.com.cn/mobile/workexam/detail";

    static String WorkExamRecord="https://moocapp.icve.com.cn/mobile/workexam/addStudentWorkExamRecord";
    static String ExamPreview="https://moocapp.icve.com.cn/mobile/workexam/workExamPreview_New";
    static String onlineHomeworkAnswer="https://moocapp.icve.com.cn/mobile/workexam/onlineHomeworkAnswer";
    static String onlineHomeworSubkAnswer="https://moocapp.icve.com.cn/mobile/workexam/onlineHomeworSubkAnswer";
    static String onlineHomeworkCheckSpace="https://moocapp.icve.com.cn/mobile/workexam/onlineHomeworkCheckSpace";
    static String onlineHomeworkMatch="https://moocapp.icve.com.cn/mobile/workexam/onlineHomeworkMatch";
    static String workExamSave="https://moocapp.icve.com.cn/mobile/workexam/workExamSave";
    static String onlineExamSave="https://moocapp.icve.com.cn/mobile/workexam/onlineExamSave";
    static String HomeWorkAndTestCorrect="https://moocapp.icve.com.cn/mobile/workexam/getHomeWorkAndTestCorrect";

    public static String getaddCourseStudy(zjyUser zjyUser, String CourseOpenId) {
//添加课程
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseOpenId + "&");
        postParam.append("sourceType=2&");
        postParam.append("equipmentModel=Redmi M2007J17C&");
        postParam.append("equipmentApiVersion=10&");
        postParam.append("newToken=" + zjyUser.getNewToken());
        String postParams = postParam.toString();
        resp = moocHttp.post(addCourseStudy, postParams, null);
        return resp;

    }

    public static String getMyCourseList(zjyUser zjyUser, String page,String isFinished) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("page=" + page + "&");
        postParam.append("platType=2&");
        postParam.append("isFinished="+isFinished+"&");
        postParam.append("sourceType=0&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(MyCourseList, postParams, null);
        return resp;

    }

    public static String getProcessList(zjyUser zjyUser, moocCourseInfo CourseInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        postParam.append("sourceType=2&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(ProcessList, postParams, null);
        return resp;

    }

    public static String getTopicList(zjyUser zjyUser, moocCourseInfo CourseInfo, moocModInfo MoocModInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        postParam.append("moduleId=" + MoocModInfo.getId() + "&");
        postParam.append("sourceType=2&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(TopicList, postParams, null);
        return resp;

    }



    //获取课件
    public static String getCellList(zjyUser zjyUser, moocCourseInfo CourseInfo, moocTopicInfo MoocTopicInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        postParam.append("topicId=" + MoocTopicInfo.getId() + "&");
        postParam.append("sourceType=2&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(CellList, postParams, null);
        return resp;

    }

    //课件详情
    public static String getviewDirectory(zjyUser zjyUser, moocCourseInfo CourseInfo, moocCellInfo CellInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        postParam.append("cellId=" + CellInfo.getId() + "&");
        postParam.append("sourceType=2&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(viewDirectory, postParams, null);
        return resp;

    }

    //刷课
    public static String getStuProcessCell(zjyUser zjyUser, moocCourseInfo CourseInfo, moocCellInfo CellInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        postParam.append("cellId=" + CellInfo.getId()+ "&");
        //postParam.append("cellId=" + ((CellInfo.getResId().isEmpty())?CellInfo.getId():CellInfo.getResId()) + "&");
        postParam.append("auvideoLength=" + 999 + "&");
        postParam.append("videoTimeTotalLong=" + 999 + "&");
        postParam.append("sourceType=2&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(StuProcessCell, postParams, null);
        return resp;

    }
    //未知刷课请求
    public static String getlearningTimeLong(zjyUser zjyUser, moocCourseInfo CourseInfo, moocCellInfo CellInfo, moocModInfo MoocModInfo) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        postParam.append("moduleId=" + MoocModInfo.getId() + "&");
        //postParam.append("cellId=" +((CellInfo.getResId().isEmpty())?CellInfo.getId():CellInfo.getResId())+ "&");
        postParam.append("cellId=" +CellInfo.getId()+ "&");
        postParam.append("auvideoLength=" + 999 + "&");
        //postParam.append("videoTimeTotalLong=" +999+ "&");
        postParam.append("sourceType=2&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(learningTimeLong, postParams, null);
        return resp;

    }

    //
    public static String getCellCommentData(zjyUser zjyUser, moocCourseInfo CourseInfo,moocCellInfo CellInfo,String dType) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("cellId=" + CellInfo.getId()+ "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");

        postParam.append("page=" + 1+ "&");
        postParam.append("dType=" + 2+ "&");

        postParam.append("sourceType=2&");

        postParam.append("equipmentModel=Redmi M2007J17C&");
        postParam.append("equipmentApiVersion=10&");
        postParam.append("newToken=" + zjyUser.getNewToken());
        String postParams = postParam.toString();
        resp = moocHttp.post(CommentData, postParams, null);
        return resp;

    }


    //用于讨论型课件评论以及讨论区信息评价
    public static String getaddTopicReplyb(zjyUser zjyUser, moocCourseInfo CourseInfo,String topicId,String content) {
      //课件讨论提交
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        postParam.append("content=" + content + "&");
        postParam.append("topicId=" + topicId + "&");

        postParam.append("sourceType=2&");
        postParam.append("urlList=&");

        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(addTopicRepl, postParams, null);
        return resp;

    }
    public static String getaddTopicReply(zjyUser zjyUser, moocCourseInfo CourseInfo,moocCellInfo CellInfo,String content) {
      //课件讨论提交

        return getaddTopicReplyb(zjyUser,CourseInfo,CellInfo.getResId(),content);

    }

    //获取他人讨论回复
    private static String getDiscussReply(zjyUser zjyUser, moocCourseInfo CourseInfo, moocCellInfo CellInfo) {
     /**获取课间中的讨论下的评论
      * **/
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        postParam.append("topicId=" + CellInfo.getResId() + "&");

        postParam.append("sourceType=2&");
        postParam.append("page=1&");

        postParam.append("equipmentModel=Redmi M2007J17C&");
        postParam.append("equipmentApiVersion=10&");
        postParam.append("newToken=" + zjyUser.getNewToken());
        String postParams = postParam.toString();
        resp = moocHttp.post(DiscussReply, postParams, null);
        return resp;

    }

    //解析他人讨论回复
    public static String getContents(zjyUser zjyUser, moocCourseInfo CourseInfo, moocCellInfo CellInfo){
        ArrayList<String> conts=new ArrayList<>();
        String resp = "";
        resp=getDiscussReply(zjyUser,CourseInfo,CellInfo);
        if (resp==null)return "";
        JSONObject json=JSONObject.parseObject(resp);
        if (!json.getString("code").equals("1"))return "";
        JSONArray varJSONArray =json.getJSONArray("list");
        if (varJSONArray.size()==0)return "";
        for (int i = 0; i < varJSONArray.size(); i++) {
            String cont=JSONObject.parseObject(varJSONArray.getString(i)).getString("replyContent");
            if (cont.isEmpty()) continue;
            conts.add(cont);
            if (conts.size()>2)break;
        }
        return conts.get(new Random().nextInt(conts.size()));
    }

    //课件评论
    private static String getsaveAllReply(zjyUser zjyUser, moocCourseInfo CourseInfo, moocCellInfo CellInfo, String content, String dType, String categoryId) {
        //课件 问答 评价等提交
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        postParam.append("categoryId=" + categoryId + "&");

        postParam.append("cellId=" + CellInfo.getId() + "&");

        postParam.append("dType=" + dType + "&");
        postParam.append("content=" + content + "&");

        postParam.append("sourceType=2&");
        postParam.append("isOpen=1&");
        postParam.append("star=5&");
        postParam.append("urlList=[]&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(getsaveAllReply, postParams, null);
        return resp;

    }
    public static String getsaveAllReply1_1(zjyUser zjyUser, moocCourseInfo CourseInfo, moocCellInfo CellInfo, String content) {
        return getsaveAllReply(zjyUser, CourseInfo, CellInfo, content, "2", "bbszhtlq-" + CourseInfo.getCourseOpenId());
        //评价
    }
    public static String getsaveAllReply1_2(zjyUser zjyUser, moocCourseInfo CourseInfo, moocCellInfo CellInfo, String content) {
        return getsaveAllReply(zjyUser, CourseInfo, CellInfo, content, "1", "bbslsdyq-" + CourseInfo.getCourseOpenId());
        //问答
    }
    public static String getsaveAllReply1_3(zjyUser zjyUser, moocCourseInfo CourseInfo, moocCellInfo CellInfo, String content) {
        return getsaveAllReply(zjyUser, CourseInfo, CellInfo, content, "3", "bbszhtlq-" + CourseInfo.getCourseOpenId());
        //笔记
    }
    public static String getsaveAllReply1_4(zjyUser zjyUser, moocCourseInfo CourseInfo, moocCellInfo CellInfo, String content) {
        return getsaveAllReply(zjyUser, CourseInfo, CellInfo, content, "4", "bbszhtlq-" + CourseInfo.getCourseOpenId());
        //反馈
    }

    public static String getsaveAllReply3_1(zjyUser zjyUser, moocCourseInfo CourseInfo, moocCellInfo CellInfo, String content) {
        return getsaveAllReply(zjyUser, CourseInfo, CellInfo, content, "1", "bbsktjlq-" + CourseInfo.getCourseOpenId());
        //其他
    }
    public static String getsaveAllReply4_1(zjyUser zjyUser, moocCourseInfo CourseInfo, moocCellInfo CellInfo, String content) {
        return getsaveAllReply(zjyUser, CourseInfo, CellInfo, content, "1", "bbsjhq-" + CourseInfo.getCourseOpenId());
        //其他
    }

    //获取老师名称
    private static String getCourseNewsNew(zjyUser zjyUser, moocCourseInfo CourseInfo) {

        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");

        postParam.append("sourceType=2&");
        postParam.append("page=1&");

        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(getCourseNewsNew, postParams, null);

        return resp;

    }

    private static String getDetailCourseNewsNew(zjyUser zjyUser, moocCourseInfo CourseInfo,String id) {

        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        postParam.append("id=" + id + "&");

        postParam.append("sourceType=2&");


        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(getDetailCourseNewsNew, postParams, null);
        return resp;

    }

    public static String getTeachName(zjyUser zjyUser, moocCourseInfo CourseInfo){
      //获取老师名称 不一定能获取到 基于公告板块获取
       String resp = getCourseNewsNew(zjyUser,  CourseInfo);
        if (resp==null)return "";
        JSONObject json= JSONObject.parseObject(resp);
        if (!json.getString("code").equals("1"))return "";
        JSONArray varArray =json.getJSONArray("list");
        if (varArray.size()==0)return "";
        String varId=varArray.getJSONObject(0).getString("id");
        resp=getDetailCourseNewsNew(zjyUser,CourseInfo,varId);
        if (resp.isEmpty())return "";
        json= JSONObject.parseObject(resp);
        if (!json.getString("code").equals("1"))return "";
        resp = json.getString("teacherName");
        return resp;
    }

    //获取讨论区全部板块
    public static String getDiscussNewsNew(zjyUser zjyUser, moocCourseInfo CourseInfo, String page) {
        //讨论区全部板块
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");

        postParam.append("page=" + page+ "&");


        postParam.append("sourceType=2&");

        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(DiscussNewsNew, postParams, null);
        return resp;

    }
    //解析讨论区全部板块
    public static ArrayList<DiscussInfo> getDiscuss(zjyUser zjyUser, moocCourseInfo CourseInfo){
        ArrayList<DiscussInfo> varDiscussInfos =new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            String resp=getDiscussNewsNew(zjyUser,CourseInfo,String.valueOf(i));
            if (resp==null)continue;
            JSONObject json=JSONObject.parseObject(resp);
            if (!json.getString("code").equals("1"))continue;
            JSONArray varArray=json.getJSONArray("list");
            if (varArray.size()!=0){
                for (int j = 0; j < varArray.size(); j++) {
                    String js=varArray.getString(j);
                    DiscussInfo varDiscussInfo =JSONObject.parseObject(js,DiscussInfo.class);
                    varDiscussInfos.add(varDiscussInfo);
                }
            }
        }
        return varDiscussInfos;
    }

    //讨论区各板块评论信息获取与解析
    public static String getDiscussTopicNew(zjyUser zjyUser, moocCourseInfo CourseInfo, String categoryId,String page) {
        //讨论区交流信息获取
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        postParam.append("categoryId=" + categoryId + "&");
        postParam.append("page=" + page + "&");

        postParam.append("sourceType=2&");
        postParam.append("searchContent=&");
        postParam.append("sort=-displayName&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(getDiscussTopicNew, postParams, null);
        return resp;

    }

    public static ArrayList<DiscussTopInfo> getDiscussTopicNew1(zjyUser zjyUser, moocCourseInfo CourseInfo,String categoryId) {
      //解析前5页信息
        ArrayList<DiscussTopInfo> varDiscussTops=new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            String resp= getDiscussTopicNew(zjyUser,CourseInfo,categoryId,String.valueOf(i));
            if (resp==null)continue;
            JSONObject json= JSONObject.parseObject(resp);
            if (!json.getString("code").equals("1"))continue;
            JSONArray varArray =json.getJSONArray("list");
            if (varArray.size()==0)continue;
            for (int j = 0; j < varArray.size(); j++) {
                String js=varArray.getString(j);
                DiscussTopInfo varDiscussTopInfo =JSONObject.parseObject(js,DiscussTopInfo.class);
                varDiscussTops.add(varDiscussTopInfo);
            }
        }
        return varDiscussTops;
    }

    //讨论区各板块评论提交
    public static String getaddPublishTopic(zjyUser zjyUser, moocCourseInfo CourseInfo, String Title, String content, String categoryId) {
        //讨论区交流提交
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        postParam.append("categoryId=" + categoryId + "&");

        postParam.append("textArea=" + content + "&");
        postParam.append("Title=" + Title + "&");
        postParam.append("sourceType=2&");

        postParam.append("star=5&");
        postParam.append("urlList=[]&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(addPublishTopic, postParams, null);
        return resp;

    }

    //默认四个讨论区板块评论
    public static String addPublishTopic1(zjyUser zjyUser, moocCourseInfo CourseInfo, String Title,  String content) {
    return getaddPublishTopic(zjyUser, CourseInfo,Title, content, "bbszhtlq-" + CourseInfo.getCourseOpenId());
    }
    public static String addPublishTopic2(zjyUser zjyUser, moocCourseInfo CourseInfo, String Title,  String content) {
    return getaddPublishTopic(zjyUser, CourseInfo,Title, content, "bbsktjlq-" + CourseInfo.getCourseOpenId());
    }
    public static String addPublishTopic3(zjyUser zjyUser, moocCourseInfo CourseInfo, String Title,  String content) {
    return getaddPublishTopic(zjyUser, CourseInfo,Title, content, "bbslsdyq-" + CourseInfo.getCourseOpenId());
    }
    public static String addPublishTopic4(zjyUser zjyUser, moocCourseInfo CourseInfo, String Title,  String content) {
    return getaddPublishTopic(zjyUser, CourseInfo,Title, content, "bbsyhq-" + CourseInfo.getCourseOpenId());
    }

    //
    public static String deleteTopic(String userid,String topicId){
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + userid+ "&");
        //postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("topicId=" + topicId + "&");
        postParam.append("sourceType=2&");
        // postParam.append("equipmentAppVersion=" + appv + "&");
        postParam.append("equipmentModel=Redmi M2007J17C&");
        postParam.append("equipmentApiVersion=10&");
        // postParam.append("newToken=" + zjyUser.getNewToken());
        String postParams = postParam.toString();
        resp = moocHttp.post(deleteTopic, postParams, null);
        return resp;
    }

    static String recommendCourseToMe= "https://moocapp.icve.com.cn/portal/Course/recommendCourseToMe";

    //获取老师id
    private static String  getCourseToMe(){

        String resp = "";
        StringBuilder postParam = new StringBuilder();
        postParam.append("page=1&pageSize=15");
        String postParams = postParam.toString();
        resp = moocHttp.get(recommendCourseToMe, postParams);
        return resp;
    }

    public static List<courseInfoForTeach>  getCourseForTeach(){
        List<courseInfoForTeach> varForTeachList=new ArrayList<>();
        String resp= getCourseToMe();
        if (resp.isEmpty())return varForTeachList;
        JSONObject json=JSONObject.parseObject(resp);
        if (!json.getString("code").equals("1"))return varForTeachList;
        JSONArray varArray =json.getJSONArray("list");
        if (varArray.isEmpty())return varForTeachList;
        varForTeachList=JSONArray.parseArray(varArray.toJSONString(),courseInfoForTeach.class);
        return varForTeachList;
    }


    public static String getWorkexamBaseU(String url,zjyUser zjyUser,moocCourseInfo CourseInfo,String workExamId,String workExamType) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");
        // postParam.append("page=" + 1 + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        postParam.append("workExamId=" + workExamId+ "&");
        postParam.append("sourceType=2&");
        postParam.append("workExamType="+workExamType+"&");
        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(url, postParams, null);
        return resp;

    }

    public static String getWorkexamBase(String url,zjyUser zjyUser,moocCourseInfo CourseInfo,moocCellInfo moocCellInfo,String workExamType) {

        return getWorkexamBaseU( url, zjyUser, CourseInfo, moocCellInfo.getResId(), workExamType);
    }

    //获取已做答返回的正确题目及答案
    public static String getHomeWorkAndTestCorrect(zjyUser zjyUser,moocCourseInfo CourseInfo,String ResId,String stuHomeWorkID,String workExamType) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + zjyUser.getUserId() + "&");

        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        postParam.append("workExamId=" +ResId+ "&");
        postParam.append("studentWorkId=" +stuHomeWorkID+ "&");
        postParam.append("sourceType=2&");
        postParam.append("workExamType="+workExamType+"&");

        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(HomeWorkAndTestCorrect, postParams, null);
        return resp;

    }

    public static String getWorkExamSave(zjyUser zjyUser,moocCourseInfo CourseInfo,moocCellInfo CellInfo,String uniqueId,String useTime,String workExamType) {
        /**提交答案**/
        String resp = "";
        StringBuilder postParam = new StringBuilder();
        String url;
        postParam.append("userId=" + zjyUser.getUserId() + "&");
        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        if (workExamType.contains("2")){
            postParam.append("examId=" +CellInfo.getResId()+ "&");
            url=onlineExamSave;
        }else {

            postParam.append("workExamId=" +CellInfo.getResId()+ "&");
            postParam.append("workExamType="+workExamType+"&");
            url=workExamSave;
        }
        postParam.append("uniqueId=" +uniqueId+ "&");
        postParam.append("useTime=" +useTime+ "&");
        postParam.append("sourceType=2&");


        postParam.append(getHeaders(zjyUser));
        String postParams = postParam.toString();
        Log.d("", "getWorkExamSave: "+postParams);
        resp = moocHttp.post(url, postParams, null);
        return resp;

    }

    public static String getonlineHomeworkAnswer(zjyUser ZjyUser,String uniqueId,String answer,String questionId,String workExamType) {
        String resp = "";
        StringBuilder postParam = new StringBuilder();
/**记录每道题的答案**/
        postParam.append("userId=" + ZjyUser.getUserId() + "&");
        postParam.append("uniqueId=" + uniqueId + "&");
        postParam.append("answer=" + answer + "&");
        postParam.append("questionId=" + questionId + "&");

        postParam.append("sourceType=2&");
        postParam.append("online=1&");
        postParam.append("workExamType="+workExamType+"&");

        postParam.append(getHeaders(ZjyUser));
        String postParams = postParam.toString();
        resp = moocHttp.post(onlineHomeworkAnswer, postParams, null);
        return resp;

    }

    public static String getWorkexam(zjyUser zjyUser,moocCourseInfo CourseInfo,moocCellInfo CellInfo,String workExamType) {
        String resp = "";
/**获取作业信息**/
        resp=getWorkexamBase(workexam,zjyUser,CourseInfo,CellInfo,workExamType);

        return resp;

    }

    public static String getExamRecord(zjyUser zjyUser,moocCourseInfo CourseInfo,moocCellInfo CellInfo,String workExamType) {
        String resp = "";
/**作业初始化**/
        resp=getWorkexamBase(WorkExamRecord,zjyUser,CourseInfo,CellInfo,workExamType);

        return resp;

    }

    public static String getExamPreview(zjyUser zjyUser,moocCourseInfo CourseInfo,moocCellInfo CellInfo,String workExamType) {
        String resp = "";
/**获取作业题目**/
        resp=getWorkexamBase(ExamPreview,zjyUser,CourseInfo,CellInfo,workExamType);

        return resp;

    }

    static String preview="https://moocapp.icve.com.cn/mobile/workexam/preview";

    public static String getWorkexamPreviewBase(String UserID,String NewToken,moocCourseInfo CourseInfo,String ResId ) {
        //越权获取作业 测验 等答案及题目 基于老师id
        String resp = "";
        StringBuilder postParam = new StringBuilder();

        postParam.append("userId=" + UserID + "&");

        postParam.append("courseOpenId=" + CourseInfo.getCourseOpenId() + "&");
        postParam.append("workExamId=" +ResId+ "&");
        postParam.append("sourceType=2&");

        postParam.append("equipmentModel=Redmi M2007J17C&");
        postParam.append("equipmentApiVersion=10&");
        postParam.append("newToken=" +NewToken);
        String postParams = postParam.toString();
        resp = moocHttp.post(preview, postParams, null);
        return resp;

    }


    private static String deviceM = Tool.getDEVICEModle();

    //标头
    private static String getHeaders(zjyUser zjyUser) {
        StringBuilder postParam = new StringBuilder();
        postParam.append("equipmentAppVersion=" + zjyUser.getAppv() + "&");
        postParam.append("equipmentModel=" + deviceM + "&");
        postParam.append("equipmentApiVersion=10&");
        postParam.append("newToken=" + zjyUser.getNewToken());
        return postParam.toString();
    }
}
