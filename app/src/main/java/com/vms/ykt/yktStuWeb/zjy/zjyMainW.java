package com.vms.ykt.yktStuWeb.zjy;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import com.vms.ykt.yktStuMobile.zjy.ExamInfo;
import com.vms.ykt.yktStuMobile.zjy.ExamViewInfo;
import com.vms.ykt.yktStuMobile.zjy.HomeworkInfo;
import com.vms.ykt.yktStuMobile.zjy.homeWorkViewInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyCellChildList;
import com.vms.ykt.yktStuMobile.zjy.zjyCellList;
import com.vms.ykt.yktStuMobile.zjy.zjyCourseIfno;
import com.vms.ykt.yktStuMobile.zjy.zjyModuleListInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyTeachInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyTopicList;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class zjyMainW {
  ;

    //获取课件
    public static ArrayList<zjyCourseIfno> getCoures() {
/**
 * web所有课程
 * **/
        ArrayList<zjyCourseIfno> zjyCourseInfoList = new ArrayList<>();
        String resp = zjyApiW.getLearnningCourseList();
        if (resp == null || !resp.contains("courseList")) return zjyCourseInfoList;
        JSONObject ret = JSONObject.parseObject(resp);
        JSONArray retArr = ret.getJSONArray("courseList");
        if (retArr.size() == 0) return zjyCourseInfoList;
        for (int i = 0; i < retArr.size(); i++) {
            String courses = retArr.getJSONObject(i).toString();
            zjyCourseInfoList.add(JSON.parseObject(courses, zjyCourseIfno.class));
        }


        /** for (zjyCourseIfno zjyCourseInfos : zjyCourseInfoList) {
         //  System.out.println(zjyCourseInfos.getCourseName());
         if (zjyCourseInfos.getCourseName().equals("毛泽东思想和中国特色社会主义理论体系")) {
         //getProcessList(zjyCourseInfos);
         }
         //   getProcessList(zjyCourseInfos);


         }**/

        return zjyCourseInfoList;
    }

    public static List<zjyModuleListInfo> getProcessList(zjyCourseIfno zjyCourseIfno) {
        List<zjyModuleListInfo> vModuleListInfos = new ArrayList<>();
        String resp = zjyApiW.getProcessList(zjyCourseIfno);
        if (resp == null || !resp.contains("moduleList")) return vModuleListInfos;
        JSONObject json = JSONObject.parseObject(resp);
        JSONArray varArray = json.getJSONObject("progress").getJSONArray("moduleList");
        for (int i = 0; i < varArray.size(); i++) {
            String jsons = varArray.getString(i);
            zjyModuleListInfo varModuleInfo = JSONObject.parseObject(jsons, zjyModuleListInfo.class);
            vModuleListInfos.add(varModuleInfo);
        }
        return vModuleListInfos;
    }

    public static List<zjyTopicList> getTopicByModuleId(zjyCourseIfno zjyCourseIfno, zjyModuleListInfo zjyModuleListInfo) {

        List<zjyTopicList> vTopicListList = new ArrayList<>();
        String resp = zjyApiW.getTopicByModuleId(zjyCourseIfno, zjyModuleListInfo);
        if (resp == null || !resp.contains("topicList")) return vTopicListList;
        JSONArray varArray = JSONObject.parseObject(resp).getJSONArray("topicList");
        for (int i = 0; i < varArray.size(); i++) {
            String json = varArray.getString(i);
            zjyTopicList varTopicInfo = JSONObject.parseObject(json, zjyTopicList.class);
            vTopicListList.add(varTopicInfo);
        }
        return vTopicListList;
    }

    public static ArrayList<zjyCellList> getCellByTopicId(zjyCourseIfno zjyCourseIfno, zjyTopicList varTopicInfo) {
        ArrayList<zjyCellList> varzjyCellListList = new ArrayList<>();
        String resp = zjyApiW.getCellByTopicId(zjyCourseIfno, varTopicInfo);
        if (resp == null || !resp.contains("cellList")) return varzjyCellListList;
        JSONArray varArray = JSONObject.parseObject(resp).getJSONArray("cellList");
        if (varArray.size() == 0) return varzjyCellListList;
        for (int i = 0; i < varArray.size(); i++) {
            String json = varArray.getString(i);
            zjyCellList varzjyCellList = JSONObject.parseObject(json, zjyCellList.class);
            varzjyCellList.setCellChildNodeList(null);
            if (varzjyCellList.getCategoryName().contains("子节点")) {
                System.out.println("---" + varzjyCellList.getCellName() + " *" + varzjyCellList.getCategoryName());

                JSONArray varArray1 = JSONObject.parseObject(json).getJSONArray("childNodeList");
                if (varArray1 != null && varArray1.size() != 0) {
                    ArrayList<zjyCellChildList> varzjyCellChildListList = new ArrayList<>();
                    for (int j = 0; j < varArray1.size(); j++) {
                        String js = varArray1.getString(j);
                        zjyCellChildList varzjyCellChildList = JSONObject.parseObject(js, zjyCellChildList.class);
                        System.out.println("----" + varzjyCellChildList.getCellName() + " *" + varzjyCellChildList.getCategoryName() + " *" + varzjyCellChildList.getStudyCellPercent());
                        // shuake( zjyCourseIfno,zjyModuleListInfo,varzjyCellChildList);
                        //  pl1(zjyCourseIfno,varzjyCellChildList,"hao");
                        varzjyCellChildListList.add(varzjyCellChildList);

                    }
                    varzjyCellList.setCellChildNodeList(varzjyCellChildListList);
                }
            } else {
                System.out.println("---" + varzjyCellList.getCellName() + " *" + varzjyCellList.getCategoryName() + " *" + varzjyCellList.getStudyCellPercent());
                // shuake( zjyCourseIfno,zjyModuleListInfo,varzjyCellList);
                //  pl1(zjyCourseIfno,varzjyCellList,"hao");
            }

            varzjyCellListList.add(varzjyCellList);
        }
        return varzjyCellListList;
    }

    //课件评论
    public static String pl1(zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellList, String content) {
        String resp1 = zjyApiW.getaddCellActivity1_1(zjyCourseIfno, zjyCellList, content);
        System.out.println(resp1);
        String resp2 = zjyApiW.getaddCellActivity1_2(zjyCourseIfno, zjyCellList, content);
        System.out.println(resp2);
        String resp3 = zjyApiW.getaddCellActivity1_3(zjyCourseIfno, zjyCellList, content);
        System.out.println(resp3);
        String resp4 = zjyApiW.getaddCellActivity1_4(zjyCourseIfno, zjyCellList, content);
        System.out.println(resp4);
        return "";
    }

    public static String pl2(ViewDirectory zjyViewDirectory, String content) {
        String resp1 = zjyApiW.getaddCellActivity2_1(zjyViewDirectory, content);
        System.out.println(resp1);
        String resp2 = zjyApiW.getaddCellActivity2_2(zjyViewDirectory, content);
        System.out.println(resp2);
        String resp3 = zjyApiW.getaddCellActivity2_3(zjyViewDirectory, content);
        System.out.println(resp3);
        String resp4 = zjyApiW.getaddCellActivity2_4(zjyViewDirectory, content);
        System.out.println(resp4);
        return "";
    }

    //打开课件详情
    public static ViewDirectory getViewDirectory(zjyCourseIfno zjyCourseIfno, zjyModuleListInfo zjyModuleListInfo, zjyCellList zjyCellList) {
        String resp = zjyApiW.getviewDirectory(zjyCourseIfno, zjyModuleListInfo, zjyCellList);
        if (resp == null || !resp.contains("guIdToken")) return null;
        ViewDirectory varViewDirectory = JSONObject.parseObject(resp, ViewDirectory.class);
        return varViewDirectory;
    }

    public static ViewDirectory getViewDirectory(zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellList) {
        String resp = zjyApiW.getviewDirectory(zjyCourseIfno, zjyCellList);
        Log.d("", "getViewDirectory: "+resp);
        if (resp == null || !resp.contains("guIdToken")) return null;
        ViewDirectory varViewDirectory = JSONObject.parseObject(resp, ViewDirectory.class);
        return varViewDirectory;
    }

    //今日课堂
    public static List<zjyTeachInfo> getDayfaceTeachInfo() {
        //今日课堂
        List<zjyTeachInfo> varDayTeachInfoList = new ArrayList<>();
        String resp = "";
        SimpleDateFormat varSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar varCalendar = Calendar.getInstance();
        String date = varSimpleDateFormat.format(varCalendar.getTime());
        resp = zjyApiW.getfaceTeachInfo(date, "");
        if (resp == null || !resp.contains("faceTeachList")) return varDayTeachInfoList;
        JSONArray varArray = JSONObject.parseObject(resp).getJSONArray("faceTeachList");
        for (int i = 0; i < varArray.size(); i++) {
            String json = varArray.getString(i);
            varDayTeachInfoList.add(JSONObject.parseObject(json, zjyTeachInfo.class));
        }
        return varDayTeachInfoList;
    }

    //单个课程所有课堂
    public static List<zjyTeachInfo> getAllFaceTeachInfoByCourse(zjyCourseIfno zjyCourseIfno) {
        //所有课堂
        List<zjyTeachInfo> varDayTeachInfoList = new ArrayList<>();
        SimpleDateFormat varSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar varCalendar = Calendar.getInstance();
        StringBuilder postParam = new StringBuilder();
        postParam.append("openClassId=" + zjyCourseIfno.getOpenClassId() + "&");
        postParam.append("courseOpenId=" + zjyCourseIfno.getCourseOpenId());
        String json = postParam.toString();

        while (true) {
            String date = varSimpleDateFormat.format(varCalendar.getTime());

            String resp = zjyApiW.getfaceTeachInfo(date, json);

            if (resp == null || !resp.contains("faceTeachList")) {
                break;
            }
            JSONArray varArray = JSONObject.parseObject(resp).getJSONArray("faceTeachList");
            if (varArray.size() != 0) {
                for (int i = 0; i < varArray.size(); i++) {
                    String jsons = varArray.getString(i);
                    zjyTeachInfo varDayTeachInfo = JSONObject.parseObject(jsons, zjyTeachInfo.class);
                    //getevaluationSave(zjyCourseIfno,varDayTeachInfo,"hao");
                    //getselfratingSave(zjyCourseIfno,varDayTeachInfo,"hao");
                    varDayTeachInfoList.add(varDayTeachInfo);
                }
            }
            varCalendar.add(Calendar.DAY_OF_WEEK, -1);

        }
        return varDayTeachInfoList;

    }

    //课后评价
    public static String EvaluationSave( zjyCourseIfno zjyCourseIfno, zjyTeachInfo ZjyAllTeachInfo, String stuContent) {
        return zjyApiW.getevaluationSave(zjyCourseIfno,ZjyAllTeachInfo,stuContent);
    }

    public static String SelfratingSave( zjyCourseIfno zjyCourseIfno, zjyTeachInfo ZjyAllTeachInfo, String stuContent) {
        return zjyApiW.getselfratingSave(zjyCourseIfno,ZjyAllTeachInfo,stuContent);
    }

        //获取作业考试
    public static ArrayList<HomeworkInfo> getHomeworkList(zjyCourseIfno zjyCourseIfno) {
        String resp = zjyApiW.getHomeworkList(zjyCourseIfno);
        if (resp == null) return null;
        JSONObject json = JSONObject.parseObject(resp);
        if (!json.getString("code").equals("1")) {
            return null;
        }
        JSONArray varArray = json.getJSONArray("list");
        if (varArray.size() == 0) {
            return null;
        }
        ArrayList<HomeworkInfo> varHomeworkInfoList = new ArrayList<>();
        for (int i = 0; i < varArray.size(); i++) {
            String js = varArray.getString(i);
            HomeworkInfo varHomeworkInfo = JSONObject.parseObject(js, HomeworkInfo.class);
            varHomeworkInfoList.add(varHomeworkInfo);
        }
        return varHomeworkInfoList;
    }

    public static ArrayList<ExamInfo> getOnlineExamList(zjyCourseIfno zjyCourseIfno) {
        String resp = zjyApiW.getOnlineExamList(zjyCourseIfno);
        if (resp == null) return null;
        JSONObject json = JSONObject.parseObject(resp);
        if (!json.getString("code").equals("1")) {
            return null;

        }
        JSONArray varArray = json.getJSONArray("list");
        if (varArray.size() == 0) {
            return null;
        }
        ArrayList<ExamInfo> varExamInfoList = new ArrayList<>();
        for (int i = 0; i < varArray.size(); i++) {
            String js = varArray.getString(i);
            ExamInfo varHomeworkInfo = JSONObject.parseObject(js, ExamInfo.class);
            varExamInfoList.add(varHomeworkInfo);
        }
        return varExamInfoList;
    }

    //打开作业考试
    public static homeWorkViewInfo getHomeWorkPreview(zjyCourseIfno zjyCourseIfno, HomeworkInfo ZjyHomeworkInfo) {
        /**获取作业题目*/
        homeWorkViewInfo varHomeWorkViewInfo = null;
        String resp = zjyApiW.getPreview(zjyCourseIfno, ZjyHomeworkInfo);
        if (resp == null || !resp.contains("redisData")) return varHomeWorkViewInfo;
        varHomeWorkViewInfo = JSONObject.parseObject(resp, homeWorkViewInfo.class);
        return varHomeWorkViewInfo;
    }

    public static ExamViewInfo getExamViewPreview(zjyCourseIfno zjyCourseIfno, ExamInfo ZjyExamInfo) {
        /**获取考试题目·*/
        ExamViewInfo varExamViewInfo = null;
        String resp = zjyApiW.getPreviewNew(zjyCourseIfno, ZjyExamInfo);
        if (resp == null || !resp.contains("questions")) return varExamViewInfo;
        varExamViewInfo = JSON.parseObject(resp, ExamViewInfo.class);
        return varExamViewInfo;
    }

}
