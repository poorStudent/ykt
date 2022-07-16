package com.vms.ykt.yktStuWeb.zjy;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import com.vms.ykt.yktStuMobile.zjy.ExamInfo;
import com.vms.ykt.yktStuMobile.zjy.ExamViewInfo;
import com.vms.ykt.yktStuMobile.zjy.HomeworkInfo;
import com.vms.ykt.yktStuMobile.zjy.homeWorkViewInfo;
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

public class zjyMainW implements Serializable {
    public com.vms.ykt.yktStuWeb.zjy.zjyApiW getZjyApiW() {
        return mZjyApiW;
    }

    public void setZjyApiW(com.vms.ykt.yktStuWeb.zjy.zjyApiW zjyApiW) {
        this.mZjyApiW = zjyApiW;
    }

    private zjyApiW mZjyApiW;

    //获取课件
    public ArrayList<zjyCourseIfno> getCoures() {
/**
 * web所有课程
 * **/
        ArrayList<zjyCourseIfno> zjyCourseInfoList = new ArrayList<>();
        String resp = mZjyApiW.getLearnningCourseList();
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

    public List<zjyModuleListInfo> getProcessList(zjyCourseIfno zjyCourseIfno) {
        List<zjyModuleListInfo> vModuleListInfos = new ArrayList<>();
        String resp = mZjyApiW.getProcessList(zjyCourseIfno);
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

    public List<zjyTopicList> getTopicByModuleId(zjyCourseIfno zjyCourseIfno, zjyModuleListInfo ZjyModuleInfo) {

        List<zjyTopicList> vTopicListList = new ArrayList<>();
        String resp = mZjyApiW.getTopicByModuleId(zjyCourseIfno, ZjyModuleInfo);
        if (resp == null || !resp.contains("topicList")) return vTopicListList;
        JSONArray varArray = JSONObject.parseObject(resp).getJSONArray("topicList");
        for (int i = 0; i < varArray.size(); i++) {
            String json = varArray.getString(i);
            zjyTopicList varTopicInfo = JSONObject.parseObject(json, zjyTopicList.class);
            vTopicListList.add(varTopicInfo);
        }
        return vTopicListList;
    }

    public ArrayList<zjyCellInfo> getCellByTopicId(zjyCourseIfno zjyCourseIfno, zjyTopicList varTopicInfo) {
        ArrayList<zjyCellInfo> varZjyCellInfoList = new ArrayList<>();
        String resp = mZjyApiW.getCellByTopicId(zjyCourseIfno, varTopicInfo);
        if (resp == null || !resp.contains("cellList")) return varZjyCellInfoList;
        JSONArray varArray = JSONObject.parseObject(resp).getJSONArray("cellList");
        if (varArray.size() == 0) return varZjyCellInfoList;
        for (int i = 0; i < varArray.size(); i++) {
            String json = varArray.getString(i);
            zjyCellInfo varZjyCellInfo = JSONObject.parseObject(json, zjyCellInfo.class);
            varZjyCellInfo.setZjyChildCellInfo(null);
            if (varZjyCellInfo.getCategoryName().contains("子节点")) {
                System.out.println("---" + varZjyCellInfo.getCellName() + " *" + varZjyCellInfo.getCategoryName());

                JSONArray varArray1 = JSONObject.parseObject(json).getJSONArray("childNodeList");
                if (varArray1 != null && varArray1.size() != 0) {
                    ArrayList<zjyChildCellInfo> varZjyChildCellInfoList = new ArrayList<>();
                    for (int j = 0; j < varArray1.size(); j++) {
                        String js = varArray1.getString(j);
                        zjyChildCellInfo varZjyChildCellInfo = JSONObject.parseObject(js, zjyChildCellInfo.class);
                        System.out.println("----" + varZjyChildCellInfo.getCellName() + " *" + varZjyChildCellInfo.getCategoryName() + " *" + varZjyChildCellInfo.getStuCellFourPercent());
                        // shuake( zjyCourseIfno,ZjyModuleInfo,varZjyChildCellInfo);
                        //  pl1(zjyCourseIfno,varZjyChildCellInfo,"hao");
                        varZjyChildCellInfoList.add(varZjyChildCellInfo);

                    }
                    varZjyCellInfo.setZjyChildCellInfo(varZjyChildCellInfoList);
                }
            } else {
                System.out.println("---" + varZjyCellInfo.getCellName() + " *" + varZjyCellInfo.getCategoryName() + " *" + varZjyCellInfo.getStuCellFourPercent());
                // shuake( zjyCourseIfno,ZjyModuleInfo,varZjyCellInfo);
                //  pl1(zjyCourseIfno,varZjyCellInfo,"hao");
            }

            varZjyCellInfoList.add(varZjyCellInfo);
        }
        return varZjyCellInfoList;
    }

    //课件评论
    public String pl1(zjyCourseIfno zjyCourseIfno, zjyCellInfo ZjyCellInfo, String content) {
        String resp1 = mZjyApiW.getaddCellActivity1_1(zjyCourseIfno, ZjyCellInfo, content);
        System.out.println(resp1);
        String resp2 = mZjyApiW.getaddCellActivity1_2(zjyCourseIfno, ZjyCellInfo, content);
        System.out.println(resp2);
        String resp3 = mZjyApiW.getaddCellActivity1_3(zjyCourseIfno, ZjyCellInfo, content);
        System.out.println(resp3);
        String resp4 = mZjyApiW.getaddCellActivity1_4(zjyCourseIfno, ZjyCellInfo, content);
        System.out.println(resp4);
        return "";
    }

    public String pl2(ViewDirectory zjyViewDirectory, String content) {
        String resp1 = mZjyApiW.getaddCellActivity2_1(zjyViewDirectory, content);
        System.out.println(resp1);
        String resp2 = mZjyApiW.getaddCellActivity2_2(zjyViewDirectory, content);
        System.out.println(resp2);
        String resp3 = mZjyApiW.getaddCellActivity2_3(zjyViewDirectory, content);
        System.out.println(resp3);
        String resp4 = mZjyApiW.getaddCellActivity2_4(zjyViewDirectory, content);
        System.out.println(resp4);
        return "";
    }

    //打开课件详情
    public ViewDirectory getViewDirectory(zjyCourseIfno zjyCourseIfno, zjyModuleInfo ZjyModuleInfo, zjyCellInfo ZjyCellInfo) {
        String resp = mZjyApiW.getviewDirectory(zjyCourseIfno, ZjyModuleInfo, ZjyCellInfo);
        if (resp == null || !resp.contains("guIdToken")) return null;
        ViewDirectory varViewDirectory = JSONObject.parseObject(resp, ViewDirectory.class);
        return varViewDirectory;
    }

    public ViewDirectory getViewDirectory(zjyCourseIfno zjyCourseIfno, zjyCellList zjyCellList) {
        String resp = mZjyApiW.getviewDirectory(zjyCourseIfno, zjyCellList);
        Log.d("", "getViewDirectory: "+resp);
        if (resp == null || !resp.contains("guIdToken")) return null;
        ViewDirectory varViewDirectory = JSONObject.parseObject(resp, ViewDirectory.class);
        return varViewDirectory;
    }

    //今日课堂
    public List<zjyTeachInfo> getDayfaceTeachInfo() {
        //今日课堂
        List<zjyTeachInfo> varDayTeachInfoList = new ArrayList<>();
        String resp = "";
        SimpleDateFormat varSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar varCalendar = Calendar.getInstance();
        String date = varSimpleDateFormat.format(varCalendar.getTime());
        resp = mZjyApiW.getfaceTeachInfo(date, "");
        if (resp == null || !resp.contains("faceTeachList")) return varDayTeachInfoList;
        JSONArray varArray = JSONObject.parseObject(resp).getJSONArray("faceTeachList");
        for (int i = 0; i < varArray.size(); i++) {
            String json = varArray.getString(i);
            varDayTeachInfoList.add(JSONObject.parseObject(json, zjyTeachInfo.class));
        }
        return varDayTeachInfoList;
    }

    //单个课程所有课堂
    public List<zjyTeachInfo> getAllFaceTeachInfoByCourse(zjyCourseIfno zjyCourseIfno) {
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

            String resp = mZjyApiW.getfaceTeachInfo(date, json);

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
    public String EvaluationSave( zjyCourseIfno zjyCourseIfno, zjyTeachInfo ZjyAllTeachInfo, String stuContent) {
        return mZjyApiW.getevaluationSave(zjyCourseIfno,ZjyAllTeachInfo,stuContent);
    }

    public String SelfratingSave( zjyCourseIfno zjyCourseIfno, zjyTeachInfo ZjyAllTeachInfo, String stuContent) {
        return mZjyApiW.getselfratingSave(zjyCourseIfno,ZjyAllTeachInfo,stuContent);
    }

        //获取作业考试
    public ArrayList<HomeworkInfo> getHomeworkList(zjyCourseIfno zjyCourseIfno) {
        String resp = mZjyApiW.getHomeworkList(zjyCourseIfno);
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

    public ArrayList<ExamInfo> getOnlineExamList(zjyCourseIfno zjyCourseIfno) {
        String resp = mZjyApiW.getOnlineExamList(zjyCourseIfno);
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
    public homeWorkViewInfo getHomeWorkPreview(zjyCourseIfno zjyCourseIfno, HomeworkInfo ZjyHomeworkInfo) {
        /**获取作业题目*/
        homeWorkViewInfo varHomeWorkViewInfo = null;
        String resp = mZjyApiW.getPreview(zjyCourseIfno, ZjyHomeworkInfo);
        if (resp == null || !resp.contains("redisData")) return varHomeWorkViewInfo;
        varHomeWorkViewInfo = JSONObject.parseObject(resp, homeWorkViewInfo.class);
        return varHomeWorkViewInfo;
    }

    public ExamViewInfo getExamViewPreview(zjyCourseIfno zjyCourseIfno, ExamInfo ZjyExamInfo) {
        /**获取考试题目·*/
        ExamViewInfo varExamViewInfo = null;
        String resp = mZjyApiW.getPreviewNew(zjyCourseIfno, ZjyExamInfo);
        if (resp == null || !resp.contains("questions")) return varExamViewInfo;
        varExamViewInfo = JSON.parseObject(resp, ExamViewInfo.class);
        return varExamViewInfo;
    }

}
