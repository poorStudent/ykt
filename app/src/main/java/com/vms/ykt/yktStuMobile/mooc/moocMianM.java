package com.vms.ykt.yktStuMobile.mooc;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;


import java.util.ArrayList;
import java.util.List;

public class moocMianM {

    private static String TAG = moocMianM.class.getSimpleName();

    //获取课程
    private static ArrayList<moocCourseInfo> getMyCourseListU(zjyUser ZjyUser, String isFinished) {

        ArrayList<moocCourseInfo> varCourseInfos = new ArrayList<>();
        int pageSize = 1;
        while (true) {
            String resp = moocApi.getMyCourseList(ZjyUser, String.valueOf(pageSize), isFinished);
            if (resp == null) break;
            JSONObject json = JSONObject.parseObject(resp);
            if (!resp.contains("list") || !json.getString("code").equals("1")) break;
            JSONArray varJSONArray = json.getJSONArray("list");
            if (varJSONArray.size() == 0) break;
            for (int i = 0; i < varJSONArray.size(); i++) {
                String js = varJSONArray.getString(i);
                Log.d(TAG, "getMyCourseListU: " + js);
                moocCourseInfo varCourseInfo = JSON.parseObject(js, moocCourseInfo.class);
                varCourseInfos.add(varCourseInfo);
            }
            pageSize++;
        }
        return varCourseInfos;
    }

    private static ArrayList<moocCourseInfo> getMyCourseList0(zjyUser ZjyUser) {
        //进行中课程
        return getMyCourseListU(ZjyUser, "0");
    }

    private static ArrayList<moocCourseInfo> getMyCourseList1(zjyUser ZjyUser) {
        //已结束课程
        return getMyCourseListU(ZjyUser, "1");
    }

    private static ArrayList<moocCourseInfo> getMyCourseList2(zjyUser ZjyUser) {
        //即将课程
        return getMyCourseListU(ZjyUser, "2");
    }

    public static ArrayList<moocCourseInfo> getMyCourseList(zjyUser ZjyUser) {
        ArrayList<moocCourseInfo> varCourseInfos = new ArrayList<>();
        varCourseInfos.addAll(getMyCourseList0(ZjyUser));
        varCourseInfos.addAll(getMyCourseList1(ZjyUser));
        varCourseInfos.addAll(getMyCourseList2(ZjyUser));
        return varCourseInfos;
    }

    //获取课件
    public static ArrayList<moocModInfo> getProcessList(zjyUser ZjyUser, moocCourseInfo CourseInfo) {
        ArrayList<moocModInfo> varMoocModInfos = new ArrayList<>();
        String resp = moocApi.getProcessList(ZjyUser, CourseInfo);
        if (resp == null || !resp.contains("moduleList")) return varMoocModInfos;
        JSONObject json = JSONObject.parseObject(resp);
        if (!json.getString("code").equals("1")) return varMoocModInfos;
        String moduleList = Tool.parseJsonS(resp, "proces");
        JSONArray varJSONArray = Tool.parseJsonA(moduleList, "moduleList");
        if (varJSONArray.size() == 0) return varMoocModInfos;
        for (int i = 0; i < varJSONArray.size(); i++) {
            String js = varJSONArray.getString(i);
            moocModInfo varModInfo = JSONObject.parseObject(js, moocModInfo.class);
            Log.d(TAG, "getProcessList: -" + varModInfo.getName());
            varMoocModInfos.add(varModInfo);
        }
        return varMoocModInfos;
    }

    public static ArrayList<moocTopicInfo> getTopicList(zjyUser ZjyUser, moocCourseInfo CourseInfo, moocModInfo MoocModInfo) {
        ArrayList<moocTopicInfo> varTopicInfos = new ArrayList<>();
        String resp = moocApi.getTopicList(ZjyUser, CourseInfo, MoocModInfo);
        if (resp == null || !resp.contains("topicList")) return varTopicInfos;
        JSONObject json = JSONObject.parseObject(resp);
        if (!json.getString("code").equals("1")) return varTopicInfos;
        JSONArray varJSONArray = json.getJSONArray("topicList");
        if (varJSONArray.size() == 0) return varTopicInfos;
        for (int i = 0; i < varJSONArray.size(); i++) {
            String js = varJSONArray.getString(i);
            moocTopicInfo varTopicInfo = JSONObject.parseObject(js, moocTopicInfo.class);
            Log.d(TAG, "getTopicList: " + "--" + varTopicInfo.getName());
            varTopicInfos.add(varTopicInfo);
        }
        return varTopicInfos;
    }

    public static ArrayList<moocCellInfo> getCellList(zjyUser ZjyUser, moocCourseInfo CourseInfo, moocTopicInfo TopicInfo) {
        ArrayList<moocCellInfo> vMoocCellInfoArrayList = new ArrayList<>();
        String resp = moocApi.getCellList(ZjyUser, CourseInfo, TopicInfo);
        // Log.d(TAG, "getCellList: "+resp);
        if (resp == null || !resp.contains("cellList")) return vMoocCellInfoArrayList;
        JSONArray vJSONArrays = Tool.parseJsonA(resp, "cellList");
        for (int i = 0; i < vJSONArrays.size(); i++) {
            ArrayList<moocChildCellInfo> ChildCellInfoList = new ArrayList<>();
            String cellList = vJSONArrays.getString(i);
            moocCellInfo vMoocCellInfo = JSONObject.parseObject(cellList, moocCellInfo.class);
            Log.d(TAG, "getCellList: " + "---" + vMoocCellInfo.getCellName());
            // Log.d(TAG, "getCellList: "+cellList);
            JSONArray vJSONArray = Tool.parseJsonA(cellList, "childNodeList");
            if (vMoocCellInfo.getCellType().equals("4")) {
                for (int j = 0; j < vJSONArray.size(); j++) {
                    cellList = vJSONArray.getString(j);
                    moocChildCellInfo vMoocChildCellInfo = JSONObject.parseObject(cellList, moocChildCellInfo.class);
                    Log.d(TAG, "getCellList: " + "----" + vMoocChildCellInfo.getCellName());
                    ChildCellInfoList.add(vMoocChildCellInfo);
                }
            }

            vMoocCellInfo.setChildNodeList(ChildCellInfoList);
            vMoocCellInfoArrayList.add(vMoocCellInfo);
        }

        return vMoocCellInfoArrayList;
    }

    //课件详情
    public static viewDirectory getviewDirectory(zjyUser ParmsZjyUser, moocCourseInfo ParmsCourseInfo, moocCellInfo ParmsCellInfo) {
        String resp = moocApi.getviewDirectory(ParmsZjyUser, ParmsCourseInfo, ParmsCellInfo);
        if (resp == null || !resp.contains("\"code\":1")) return null;
        viewDirectory varViewDirectory = JSONObject.parseObject(resp, viewDirectory.class);
        return varViewDirectory;
    }

    public static void shuake(zjyUser ZjyUser, moocCourseInfo CourseInfo, moocCellInfo varCellInfo, moocModInfo MoocModInfo, moocTopicInfo
            TopicInfo) {

        if (varCellInfo.getCategoryName().contains("作业")) {
            doWorkexam(ZjyUser, CourseInfo, varCellInfo, "1");
            System.exit(0);
        }
        if (varCellInfo.getIsStudyFinish().equals("false")) {

            // viewDirectory varViewDirectory = getviewDirectory(ZjyUser,CourseInfo,varCellInfo);

            if (varCellInfo.getCategoryName().contains("作业")) {


                doWorkexam(ZjyUser, CourseInfo, varCellInfo, "0");

            } else if (varCellInfo.getCategoryName().contains("测验")) {


                doWorkexam(ZjyUser, CourseInfo, varCellInfo, "1");
            } else if (varCellInfo.getCategoryName().contains("综合测试")) {

                doWorkexam(ZjyUser, CourseInfo, varCellInfo, "2");
            } else {
                //  shuake(ZjyUser, CourseInfo, varCellInfo, MoocModInfo);

                if (varCellInfo.getCategoryName().contains("讨论")) {
                    //  tl(ZjyUser, CourseInfo, varCellInfo);
                }
                if (varCellInfo.getCategoryName().contains("视频")) {
                    // pl(ZjyUser, CourseInfo, varCellInfo);
                }

            }
        }

    }

    private static void shuake(zjyUser ZjyUser, moocCourseInfo CourseInfo, moocCellInfo varCellInfo, moocModInfo MoocModInfo) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(moocApi.getStuProcessCell(ZjyUser, CourseInfo, varCellInfo));
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(moocApi.getlearningTimeLong(ZjyUser, CourseInfo, varCellInfo, MoocModInfo));

    }

    private static void pl(zjyUser ZjyUser, moocCourseInfo CourseInfo, moocCellInfo varCellInfo) {

        System.out.println(moocApi.getsaveAllReply1_1(ZjyUser, CourseInfo, varCellInfo, "wu"));
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(moocApi.getsaveAllReply1_3(ZjyUser, CourseInfo, varCellInfo, "wu"));
        System.out.println(moocApi.getsaveAllReply1_4(ZjyUser, CourseInfo, varCellInfo, "wu"));
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(moocApi.getsaveAllReply1_2(ZjyUser, CourseInfo, varCellInfo, "wu"));

    }

    private static void tl(zjyUser zjyUser, moocCourseInfo CourseInfo, moocCellInfo CellInfo) {
        String ct = moocApi.getContents(zjyUser, CourseInfo, CellInfo);

        System.out.println(CellInfo.getResId());
        System.out.println(ct);
        if (ct.isEmpty()) ct = "hao";
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(moocApi.getaddTopicReply(zjyUser, CourseInfo, CellInfo, ct));

    }

    private static void tlqpl(zjyUser zjyUser, moocCourseInfo CourseInfo) {
        ArrayList<DiscussInfo> varDiscusslist = moocApi.getDiscuss(zjyUser, CourseInfo);
        String tn = moocApi.getTeachName(zjyUser, CourseInfo);
        if (varDiscusslist.size() != 0) {
            for (DiscussInfo varDiscussInfo : varDiscusslist) {
                System.out.println("-" + varDiscussInfo.getTitle());
                if (!varDiscussInfo.getTitle().equals("课堂交流区")) continue;
                System.out.println(moocApi.getaddPublishTopic(zjyUser, CourseInfo, "11", "11", varDiscussInfo.getId()));//讨论区添加信息
                ArrayList<DiscussTopInfo> varDiscussTopInfos = moocApi.getDiscussTopicNew1(zjyUser, CourseInfo, varDiscussInfo.getId());

                for (DiscussTopInfo varDiscussTopInfo : varDiscussTopInfos) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (varDiscussTopInfo.getDisplayName().equals(tn)) continue;
                    System.out.println("--" + varDiscussTopInfo.getDisplayName() + " *-" + varDiscussTopInfo.getTitle());
                    //System.out.println(moocApi.deleteTopic(varDiscussTopInfo.getUserId(), varDiscussTopInfo.getTopicId()));
                    System.out.println(moocApi.getaddTopicReplyb(zjyUser, CourseInfo, varDiscussTopInfo.getTopicId(), "11"));//讨论区信息评价
                }
            }
        }
    }


    public static String doWorkexam(zjyUser ZjyUser, moocCourseInfo CourseInfo, moocCellInfo varCellInfo, String workExamType) {

        String resp = "";
        resp = moocApi.getWorkexam(ZjyUser, CourseInfo, varCellInfo, workExamType);
        //moocWorkexamInfo varWorkexamInfo=parseWorkexam(resp);
        String unid = getExamRecord(ZjyUser, CourseInfo, varCellInfo, workExamType);
        Log.d(TAG, "doWorkexam: " + unid);
        String Preview = getExamPreview(ZjyUser, CourseInfo, varCellInfo, workExamType);
        if (unid == null || unid.isEmpty()) {
            unid = Tool.parseJsonS(Preview, "uniqueId");
        }
        List<homeWorkAnswInfo> StuAnsw;

        StuAnsw = getStuAnsw(ZjyUser, CourseInfo, varCellInfo.getResId());
        if (!StuAnsw.isEmpty() && !unid.isEmpty() && !StuAnsw.get(0).getAnswer().isEmpty()) {
            return postHomeWork(StuAnsw, ZjyUser, unid, CourseInfo, varCellInfo, workExamType);
        }

        StuAnsw = getStuAnsw2(ZjyUser, CourseInfo, varCellInfo, Preview, workExamType);
        if (!StuAnsw.isEmpty() && !unid.isEmpty() && !StuAnsw.get(0).getAnswer().isEmpty()) {
            return postHomeWork(StuAnsw, ZjyUser, unid, CourseInfo, varCellInfo, workExamType);
        }
        return "异常未作答";
    }

    public static List<homeWorkAnswInfo> getStuAnsw(zjyUser ZjyUser, moocCourseInfo CourseInfo, String ResId) {
        //越权获取答案
        List<homeWorkAnswInfo> answ = new ArrayList<>();
        String teachId = ZjyUser.getTeachId();
        String resp = "";

        if (teachId != null && !teachId.isEmpty()) {
            resp = moocApi.getWorkexamPreviewBase(teachId, ZjyUser.getNewToken(), CourseInfo, ResId);
            Tool.logi("",  resp);
            if (resp == null || !resp.contains("paperData")) return answ;
            JSONObject json = JSONObject.parseObject(resp);
            if (json.getString("code").equals("2")) {
                json = json.getJSONObject("paperData");
                answ = parseAnsw(json);

            }
        } else {
            Log.d(TAG, "getStuAnsw: " + "hqsb");
        }
        return answ;
    }

    public static List<homeWorkAnswInfo> getStuAnsw2(zjyUser ZjyUser, moocCourseInfo CourseInfo, moocCellInfo varCellInfo, String Preview, String workExamType) {
        //越权失效时 备用 正常获取答案
        //先加课程 用小号做 然后获取 全程只用usreid就行 没有在线验证；
        List<homeWorkAnswInfo> answ;
        zjyUser tmpUs;

        if (ZjyUser.getOtherZjyUser() == null) {
            tmpUs = new zjyUser();
            tmpUs.setUserId("utbzaikuaovc89bj200x1a");
            tmpUs.setNewToken(ZjyUser.getNewToken());
        } else {
            tmpUs = ZjyUser.getOtherZjyUser();
        }
        answ = parseAnswByTmpUs(tmpUs, CourseInfo, varCellInfo, workExamType);
        if (answ.size() != 0) return answ;
        answ = doWorkByTmpUs(tmpUs, CourseInfo, varCellInfo, Preview, workExamType);
        return answ;
    }

    public static List<homeWorkAnswInfo> doWorkByTmpUs(zjyUser tmpUs, moocCourseInfo CourseInfo, moocCellInfo varCellInfo, String Preview, String workExamType) {
        String resp = "";
        List<homeWorkAnswInfo> answ = new ArrayList<>();
        resp = moocApi.getaddCourseStudy(tmpUs, CourseInfo.getCourseOpenId());
        Log.d(TAG, "getStuAnsw2: " + resp);
        String unid = "";
        unid = getExamRecord(tmpUs, CourseInfo, varCellInfo, workExamType);
        if (Preview==null||Preview.isEmpty())
            Preview = getExamPreview(tmpUs, CourseInfo, varCellInfo, workExamType);
        if (unid == null || unid.isEmpty()) {
            Preview = getExamPreview(tmpUs, CourseInfo, varCellInfo, workExamType);
            unid = Tool.parseJsonS(Preview, "uniqueId");
        }
        Log.d(TAG, "getStuAnsw2: " + unid);

        if (unid != null && Preview != null) {
            if (!Preview.contains("getHTEKey(workExamType)")) return answ;
            JSONObject varJSONObject = JSON.parseObject(Preview);
            varJSONObject = varJSONObject.getJSONObject(getHTEKey(workExamType));
            List<homeWorkAnswInfo> varWorkAnswInfoList = parseAnsw(varJSONObject);
            for (homeWorkAnswInfo varInfo : varWorkAnswInfoList) {
                Log.d(TAG, "getStuAnsw2: " + varInfo.getTitle());
                Log.d(TAG, "getStuAnsw2: " + varInfo.getQuestionId());
                Tool.waitTime(800);
                resp = moocApi.getonlineHomeworkAnswer(tmpUs, unid, "0", varInfo.getQuestionId(), workExamType);
                Log.d(TAG, "getStuAnsw2: " + resp);
            }
            Tool.waitTime(800);
            resp = moocApi.getWorkExamSave(tmpUs, CourseInfo, varCellInfo, unid, "200", workExamType);
            Log.d(TAG, "getStuAnsw2: " + resp);
            answ = parseAnswByTmpUs(tmpUs, CourseInfo, varCellInfo, workExamType);
        } else {
            Log.d(TAG, "getStuAnsw2: " + "hqsb");
        }
        return answ;

    }

    public static List<homeWorkAnswInfo> parseAnswByTmpUs(zjyUser tmpUs, moocCourseInfo CourseInfo, moocCellInfo varCellInfo, String workExamType) {
        String resp = "";
        List<homeWorkAnswInfo> answ = new ArrayList<>();
        resp = moocApi.getWorkexam(tmpUs, CourseInfo, varCellInfo, workExamType);
        if (resp != null) {
            moocWorkexamInfo varWorkexamInfo = parseWorkexam(resp);
            String stuHomeWorkID = varWorkexamInfo.getId();
            if (stuHomeWorkID != null && !stuHomeWorkID.isEmpty()) {
                resp = moocApi.getHomeWorkAndTestCorrect(tmpUs, CourseInfo, varCellInfo.getResId(), stuHomeWorkID, workExamType);
                if (resp != null && resp.contains("questionData")) {
                    JSONObject json = JSONObject.parseObject(resp);
                    json = json.getJSONObject("questionData");
                    answ = parseAnsw(json);
                    return answ;
                }
            }
        }
        return answ;

    }


    private static List<homeWorkAnswInfo> parseAnsw(JSONObject json) {
        List<homeWorkAnswInfo> varWorkAnswInfoList = new ArrayList<>();
        JSONArray varJSONArray = json.getJSONArray("questions");
        if (varJSONArray.size() != 0) {
            varWorkAnswInfoList = JSONArray.parseArray( json.getString("questions"), homeWorkAnswInfo.class);
        }
        return varWorkAnswInfoList;
    }

    public static String parseAnsw(List<homeWorkAnswInfo> list) {

        StringBuffer vStringBuffer = new StringBuffer();
        ArrayList<String> vList = new ArrayList<>();
        vList.add("A");
        vList.add("B");
        vList.add("C");
        vList.add("D");
        vList.add("E");
        vList.add("F");
        vList.add("G");
        vList.add("H");
        vList.add("I");
        ArrayList<String> vList2 = new ArrayList<>();
        vList2.add("正确");
        vList2.add("错误");


        int curind=1;
        for (homeWorkAnswInfo vHomeWorkAnswInfo : list) {
            ArrayList<String> answList = new ArrayList<>();
            JSONArray vJSONArray = JSONObject.parseArray(vHomeWorkAnswInfo.getAnswerList());
            if (!vJSONArray.isEmpty()) {
                for (int i = 0; i < vJSONArray.size(); i++) {
                    answList.add(Tool.parseJsonS(vJSONArray.getString(i).replaceAll("<.+?>",""), "Content"));
                }
            }
            vStringBuffer.append("\n 问题 "+(curind++)+": ");
            vStringBuffer.append(vHomeWorkAnswInfo.getTitleText() + " ");
            vStringBuffer.append("(" + vHomeWorkAnswInfo.getTotalScore() + "分)\n 答案： ");

            if (vHomeWorkAnswInfo.getAnswer()==null||vHomeWorkAnswInfo.getAnswer().isEmpty()){
                vStringBuffer.append(" *_*答案为空 ");
            }else {

                if (vHomeWorkAnswInfo.getQuestionType().equals("1")) {

                    vStringBuffer.append(vList.get(Integer.parseInt(vHomeWorkAnswInfo.getAnswer().replace(",", ""))));
                    vStringBuffer.append("  ");
                    vStringBuffer.append(answList.get(Integer.parseInt(vHomeWorkAnswInfo.getAnswer().replace(",", ""))));

                } else if (vHomeWorkAnswInfo.getQuestionType().equals("2")) {

                    String[] amsw = vHomeWorkAnswInfo.getAnswer().split(",");
                    for (String string : amsw) {
                        vStringBuffer.append(vList.get(Integer.parseInt(string)));
                        vStringBuffer.append("  ");
                        vStringBuffer.append(answList.get(Integer.parseInt(vHomeWorkAnswInfo.getAnswer())));
                        vStringBuffer.append("\n");
                    }
                } else if (vHomeWorkAnswInfo.getQuestionType().equals("3")) {

                    vStringBuffer.append(vList2.get(Integer.parseInt(vHomeWorkAnswInfo.getAnswer())));
                    vStringBuffer.append("  ");
                } else {

                    vStringBuffer.append(vHomeWorkAnswInfo.getAnswer());
                    vStringBuffer.append("  ");
                }
            }
            vStringBuffer.append("\n");
        }
        return vStringBuffer.toString();
    }

    public static moocWorkexamInfo parseWorkexam(String resp) {
//作业详情
        if (resp == null) return null;
        JSONObject jsons = JSONObject.parseObject(resp);
        if (!jsons.getString("code").equals("1")) return null;
        moocWorkexamInfo varWorkexamInfo = JSONObject.parseObject(resp, moocWorkexamInfo.class);
        JSONObject json = jsons.getJSONObject("workExam");
        varWorkexamInfo.setReplyCount(json.getString("replyCount"));
        varWorkexamInfo.setTitle(json.getString("title"));
        varWorkexamInfo.setStuWorkCount(json.getString("stuWorkCount"));
        varWorkexamInfo.setIsLook(json.getString("isLook"));
        json = jsons.getJSONObject("homeStartEndTime");
        varWorkexamInfo.setStartTime(json.getString("StartTime"));
        varWorkexamInfo.setEndTime(json.getString("EndTime"));
        JSONArray varJSONArray = jsons.getJSONArray("list");
        if (varJSONArray.size() != 0) {
            json = varJSONArray.getJSONObject(0);
            varWorkexamInfo.setId(json.getString("id"));
        }
        return varWorkexamInfo;
    }

    public static String getExamRecord(zjyUser zjyUser, moocCourseInfo CourseInfo, moocCellInfo CellInfo, String WorkExamType) {
/**初始化作业**/
        String resp = moocApi.getExamRecord(zjyUser, CourseInfo, CellInfo, WorkExamType);
        if (resp == null) return "";
        JSONObject json = JSONObject.parseObject(resp);
        if (!json.getString("code").equals("1")) return "";
        return json.getString("uniqueId");
    }

    public static String getExamPreview(zjyUser zjyUser, moocCourseInfo CourseInfo, moocCellInfo CellInfo, String workExamType) {
        String resp = "";
/**获取作业题目**/
        resp = moocApi.getExamPreview(zjyUser, CourseInfo, CellInfo, workExamType);

        return resp;

    }

    private static String postHomeWork(List<homeWorkAnswInfo> StuAnsw, zjyUser ZjyUser, String unid, moocCourseInfo CourseInfo, moocCellInfo varCellInfo, String workExamType) {
        String resp = "";
        for (homeWorkAnswInfo varInfo : StuAnsw) {
            if (varInfo.getAnswer().isEmpty()) continue;
            Log.d(TAG, "postHomeWork: " + varInfo.getTitle());
            Log.d(TAG, "postHomeWork: " + varInfo.getAnswer());
            Log.d(TAG, "postHomeWork: " + varInfo.getQuestionId());
            resp = moocApi.getonlineHomeworkAnswer(ZjyUser, unid, varInfo.getAnswer(), varInfo.getQuestionId(), workExamType);
            Log.d(TAG, "postHomeWork: " + resp);
        }

        resp = moocApi.getWorkExamSave(ZjyUser, CourseInfo, varCellInfo, unid, "1200", workExamType);
        Log.d(TAG, "postHomeWork: " + resp);
        return resp;
    }

    public static String getTeachId(zjyUser zjyUser, moocCourseInfo CourseInfo) {
        //获取老师id 讨论区枚举
        ArrayList<DiscussInfo> varDiscusslist = moocApi.getDiscuss(zjyUser, CourseInfo);
        String tn = moocApi.getTeachName(zjyUser, CourseInfo);
        String teachId = "";
        if (tn.isEmpty()) {
            return teachId;
        }
        if (varDiscusslist.size() != 0) {
            for (DiscussInfo varDiscussInfo : varDiscusslist) {
                ArrayList<DiscussTopInfo> varDiscussTopInfos = moocApi.getDiscussTopicNew1(zjyUser, CourseInfo, varDiscussInfo.getId());
                for (DiscussTopInfo varDiscussTopInfo : varDiscussTopInfos) {
                    if (varDiscussTopInfo.getDisplayName().equals(tn)) {
                        teachId = varDiscussTopInfo.getUserId();
                        System.out.println(varDiscussTopInfo.getDisplayName());
                        System.out.println(teachId);
                        zjyUser.setTeachId(teachId);
                        return teachId;
                    }
                }
            }
        }
        return teachId;
    }

    public static String getTeachId2(zjyUser ZjyUser, moocCourseInfo CourseInfo) {
        //基于web版 获取
        String teachID = "";
        List<courseInfoForTeach> varForTeachList = moocApi.getCourseForTeach();
        for (courseInfoForTeach varForTeach : varForTeachList) {
            if (varForTeach.getId().contains(CourseInfo.getId())) {
                teachID = varForTeach.getUserId();
                Log.d(TAG, "getTeachId3: " + varForTeach.getMainTeacherName());
                Log.d(TAG, "getTeachId3: " + varForTeach.getUserId());
                break;
            }
        }
        ZjyUser.setTeachId(teachID);
        return teachID;
    }

    public static String getTeachId3(zjyUser ZjyUser, moocCourseInfo CourseInfo, List<courseInfoForTeach> varForTeachList) {
        //基于web版 获取
        String teachID = "";
        for (courseInfoForTeach varForTeach : varForTeachList) {
            if (varForTeach.getId().contains(CourseInfo.getId())) {
                teachID = varForTeach.getUserId();
                Log.d(TAG, "getTeachId3: " + varForTeach.getMainTeacherName());
                Log.d(TAG, "getTeachId3: " + varForTeach.getUserId());
                break;
            }
        }
        ZjyUser.setTeachId(teachID);
        return teachID;
    }

    private static String getHTEKey(String WorkExamType) {
        switch (WorkExamType) {
            case "0":
            case "2":
                return "questionData";
            case "1":
                return "workExamData";
        }
        return "";
    }

}