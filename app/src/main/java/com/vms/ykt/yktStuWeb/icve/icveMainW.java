package com.vms.ykt.yktStuWeb.icve;

import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class icveMainW implements Serializable {

    public com.vms.ykt.yktStuWeb.icve.icveApiW getIcveApiW() {
        return mIcveApiW;
    }

    public void setIcveApiW(com.vms.ykt.yktStuWeb.icve.icveApiW icveApiW) {
        this.mIcveApiW = icveApiW;
    }

    private icveApiW mIcveApiW;

    private final String TAG = icveMainW.class.getSimpleName();

    public List<icveCourseInfo> getMyCourseList(zjyUser zjyUser) {
        List<icveCourseInfo> varCourseInfos = new ArrayList<>();

        String resp = mIcveApiW.getStudingCourse(zjyUser);
        varCourseInfos.addAll(parseCourse(resp, 0));
        resp = mIcveApiW.getFinishCourse(zjyUser);
        varCourseInfos.addAll(parseCourse(resp, 0));

        icveCourseInfo footview = new icveCourseInfo();
        footview.setType(2);
        varCourseInfos.add(footview);

        resp = mIcveApiW.getStudingSmallCourse();
        varCourseInfos.addAll(parseCourse(resp, 1));
        resp = mIcveApiW.getFinishSmallCCourse();
        varCourseInfos.addAll(parseCourse(resp, 1));

        return varCourseInfos;
    }

    private List<icveCourseInfo> parseCourse(String resp, int type) {
        List<icveCourseInfo> varCourseInfos = new ArrayList<>();
        if (resp != null) {
            if (resp.contains("list")) {
                JSONArray varJSONArray = Tool.parseJsonA(resp, "list");
                for (int i = 0; i < varJSONArray.size(); i++) {
                    String json = varJSONArray.getString(i);
                    icveCourseInfo vCourseInfo = JSONObject.parseObject(json, icveCourseInfo.class);
                    vCourseInfo.setType(type);
                    varCourseInfos.add(vCourseInfo);
                }
            }
        }
        return varCourseInfos;
    }

    public List<SectionInfo> getSectionList(icveCourseInfo CourseInfo) {
        List<SectionInfo> varSectionInfos = new ArrayList<>();
        String resp = mIcveApiW.getdirectoryList(CourseInfo);
        if (resp == null || !resp.contains("directory")) return varSectionInfos;
        JSONObject json = JSONObject.parseObject(resp);
        JSONArray varJSONArray = json.getJSONArray("directory");
        if (varJSONArray.size() == 0) return varSectionInfos;
        for (int i = 0; i < varJSONArray.size(); i++) {

            json = varJSONArray.getJSONObject(i);
            String js = json.getString("section");
            SectionInfo varSectionInfo = JSONObject.parseObject(js, SectionInfo.class);
            Log.d(TAG, "getSectionList: " + "-" + varSectionInfo.getTitle());
            js = json.getString("chapters");
            varSectionInfo.setChapters(js);
            //  getChaptersList(js, CourseInfo, varSectionInfo);
            varSectionInfos.add(varSectionInfo);

        }
        return varSectionInfos;
    }

    public List<chapterInfo> getChaptersList(String resp) {

        List<chapterInfo> vChapterInfoList = new ArrayList<>();
        if (resp.isEmpty()) return vChapterInfoList;
        JSONArray varJSONArray = JSONArray.parseArray(resp);
        if (varJSONArray.size() == 0) return vChapterInfoList;
        for (int i = 0; i < varJSONArray.size(); i++) {

            JSONObject json = varJSONArray.getJSONObject(i);

            String js = json.getString("chapter");
            String js1 = json.getString("cells");
            String js2 = json.getString("knowleges");
            chapterInfo vChapterInfo = getChapterInfo(js);
            vChapterInfo.setCells(js1);
            vChapterInfo.setKnowleges(js2);

            //getCellsList(js1);
            //getKnowlegesList(js2);
            vChapterInfoList.add(vChapterInfo);

        }
        return vChapterInfoList;
    }

    public List<knowlegeInfo> getKnowlegesList(String resp) {
        List<knowlegeInfo> vKnowlegeInfoList = new ArrayList<>();
        if (resp.isEmpty()) return vKnowlegeInfoList;
        JSONArray varJSONArray = JSONArray.parseArray(resp);
        if (varJSONArray.size() == 0) return vKnowlegeInfoList;
        for (int i = 0; i < varJSONArray.size(); i++) {
            JSONObject json = varJSONArray.getJSONObject(i);

            String js = json.getString("knowlege");
            knowlegeInfo vKnowlegeInfo = getknowlegeInfo(js);
            js = json.getString("cells");
            vKnowlegeInfo.setCells(js);
            //getCellsList(js);
            vKnowlegeInfoList.add(vKnowlegeInfo);

        }
        return vKnowlegeInfoList;
    }

    public List<cellInfo> getCellsList(String resp) {
        List<cellInfo> varCellInfoList = new ArrayList<>();

        if (resp.isEmpty()) return varCellInfoList;
        JSONArray varJSONArray = JSONArray.parseArray(resp);
        if (varJSONArray.size() == 0) return varCellInfoList;
        //  varCellInfoList=JSONArray.parseArray(resp,cellInfo.class);

        for (int i = 0; i < varJSONArray.size(); i++) {

            String json = varJSONArray.getString(i);
            cellInfo varCellInfo = JSONObject.parseObject(json, cellInfo.class);
            Log.d(TAG, "getCellsList: " + "----" + varCellInfo.getTitle() + " *" + varCellInfo.getId()
                    + " *" + varCellInfo.getCellType() + " *" + varCellInfo.getStatus() + " *"
                    + varCellInfo.getResId() + " *" + varCellInfo.getResType());
            varCellInfoList.add(varCellInfo);
            // updateStatus(varCellInfo, CourseInfo);
        }
        return varCellInfoList;
    }

    public chapterInfo getChapterInfo(String resp) {

        chapterInfo varChapterInfo = new chapterInfo();
        if (resp.isEmpty()) return varChapterInfo;
        varChapterInfo = JSONObject.parseObject(resp, chapterInfo.class);
        Log.d(TAG, "getChapterInfo: " + "--" + varChapterInfo.getTitle() + "*" + varChapterInfo.getResId());

        return varChapterInfo;
    }

    public knowlegeInfo getknowlegeInfo(String resp) {
        knowlegeInfo varKnowlegeInfo = new knowlegeInfo();
        if (resp.isEmpty()) return varKnowlegeInfo;
        varKnowlegeInfo = JSONObject.parseObject(resp, knowlegeInfo.class);
        Log.d(TAG, "getknowlegeInfo: " + "---" + varKnowlegeInfo.getTitle() + "*" + varKnowlegeInfo.getStatus());
        return varKnowlegeInfo;
    }

    public ViewInfo getView(String courseId, String cellId) {
        //刷课
        String resp = "";
        resp = mIcveApiW.getView(courseId, cellId);
        if (resp == null || !resp.contains("works")) return null;
        ViewInfo viewInfos = JSONObject.parseObject(resp, ViewInfo.class);
        return viewInfos;
    }

    public void updateStatus(cellInfo CellInfo, icveCourseInfo CourseInfo) {
        String type = CellInfo.getCellType();
        if (CellInfo.getStatus().equals("1")) return;

        String view = mIcveApiW.getView(CourseInfo.getId(), CellInfo.getId());//其他类型课件打开即可
        System.out.println(view);

        if (type.contains("discuss")) {
            tl(CourseInfo.getId(), CellInfo.getResId());//讨论
        } else if (type.contains("question")) {

            doWork(view);//作业

        } else if (type.contains("video")) {

            System.out.println(mIcveApiW.getUpdateStatus(CellInfo.getId()));//视频
        }
    }

    public void tl(String CourseId, String topicId) {
        String ct;
        ct = mIcveApiW.getReplyContext(CourseId, topicId);
        System.out.println(ct);
        System.out.println(mIcveApiW.addReply(CourseId, topicId, ct));
    }

    public void doWork(String view) {
        if (view.isEmpty()) return;
        JSONObject json = JSONObject.parseObject(view);
        String works = json.getJSONObject("works").getString("Id");
        String data = json.getString("data");
        ArrayList<AnswersInfo> answs = getAnswArray(data);
        for (AnswersInfo ans : answs) {
            String id = ans.getId();
            System.out.println(id);
            System.out.println(ans.getContentText());
            for (String answ : ans.getAnswersList()) {
                System.out.println(answ);
                try {
                    Thread.sleep(500);
                } catch (Exception e) {

                }
                System.out.println(mIcveApiW.answerpaper(works, id, answ));
            }
        }
        try {
            Thread.sleep(500);
        } catch (Exception e) {

        }
        System.out.println(mIcveApiW.subPaper(works));
    }

    public List<workExamListInfo> getWorksList(String courseId) {
        //获取所有作业
        List<workExamListInfo> WorksList;
        String resp = mIcveApiW.getWorkseInfo(courseId);
        WorksList = parseWorkExam(resp,1);
        return WorksList;
    }

    public List<workExamListInfo> getExamList(String courseId) {
        //获取所有考试
        List<workExamListInfo> ExamList;
        String resp = mIcveApiW.getExam(courseId);
        ExamList = parseWorkExam(resp,2);
        return ExamList;
    }

    public List<workExamListInfo> parseWorkExam(String resp,int type) {
        List<workExamListInfo> workExamList = new ArrayList<>();
        if (resp == null || !resp.contains("list")) return workExamList;
        JSONArray vJSONArray = Tool.parseJsonA(resp, "list");
        for (int i = 0; i < vJSONArray.size(); i++) {
            String js =vJSONArray.getString(i);
            workExamListInfo vWorkExamListInfo =JSONObject.parseObject(js,workExamListInfo.class);
            vWorkExamListInfo.setType(type);
            workExamList.add(vWorkExamListInfo);
        }
        return workExamList;
    }

    public ArrayList<AnswersInfo> getAnswPaper(String data) {

        ArrayList<AnswersInfo> vArrayList = new ArrayList<>();
        JSONArray vJSONArray = JSONObject.parseArray(data);
        if (data == null || !data.contains("paper")) return vArrayList;
        for (int i = 0; i < vJSONArray.size(); i++) {
            String js = vJSONArray.getString(i);
            JSONObject json = JSONObject.parseObject(js).getJSONObject("paper");
            ArrayList<AnswersInfo> vArrayLists = paserAnsw(json.getString("PaperQuestions"));
            vArrayList.addAll(vArrayLists);
        }
        return vArrayList;
    }

    public ArrayList<AnswersInfo> getAnswArray(String data) {
        ArrayList<AnswersInfo> answ = new ArrayList();
        if (data == null || !data.contains("array")) return answ;
        JSONArray vJSONArray = JSONObject.parseArray(data);
        for (int i = 0; i < vJSONArray.size(); i++) {
            String js = vJSONArray.getString(i);
            String json = JSONObject.parseObject(js).getString("array");
            JSONArray varJSONArray = JSONObject.parseArray(json);
            for (int ii = 0; ii < varJSONArray.size(); ii++) {
                JSONArray varJSONArray1 = varJSONArray.getJSONObject(ii).getJSONArray("Questions");
                ArrayList<AnswersInfo> varAnswersInfos = paserAnsw(varJSONArray1.toJSONString());
                answ.addAll(varAnswersInfos);
            }
        }
        return answ;
    }

    public ArrayList<AnswersInfo> getAnswPapers(String data) {

        ArrayList<AnswersInfo> vArrayList = new ArrayList<>();
        if (data == null || !data.contains("paper")) return vArrayList;
        JSONObject json = JSONObject.parseObject(data).getJSONObject("paper");
        ArrayList<AnswersInfo> vArrayLists = paserAnsw(json.getString("PaperQuestions"));
        vArrayList.addAll(vArrayLists);

        return vArrayList;
    }

    public ArrayList<AnswersInfo> getAnswArrays(String data) {
        ArrayList<AnswersInfo> answ = new ArrayList();
        if (data == null || !data.contains("array")) return answ;
        String json = JSONObject.parseObject(data).getString("array");
        JSONArray varJSONArray = JSONObject.parseArray(json);
        for (int i = 0; i < varJSONArray.size(); i++) {
            JSONArray varJSONArray1 = varJSONArray.getJSONObject(i).getJSONArray("Questions");
            ArrayList<AnswersInfo> varAnswersInfos = paserAnsw(varJSONArray1.toJSONString());
            answ.addAll(varAnswersInfos);
        }
        return answ;
    }

    private ArrayList<AnswersInfo> paserAnsw(String data) {

        ArrayList<AnswersInfo> answ = new ArrayList();
        JSONArray varJSONArray = JSONObject.parseArray(data);
        for (int j = 0; j < varJSONArray.size(); j++) {
            JSONObject json = varJSONArray.getJSONObject(j);
            AnswersInfo varAnswersInfo = JSONObject.parseObject(json.toString(), AnswersInfo.class);
            JSONArray varArrayAnsw = json.getJSONArray("Answers");
            ArrayList<String> varAnswList = new ArrayList<>();
            for (int k = 0; k < varArrayAnsw.size(); k++) {
                varAnswList.add(varArrayAnsw.getString(k));
            }
            varAnswersInfo.setAnswersList(varAnswList);
            answ.add(varAnswersInfo);
        }

        return answ;
    }

    public String paserAnsw(List<AnswersInfo> data) {
        StringBuilder vStringBuilder = new StringBuilder();
        List<String> pd=new ArrayList<>();
        pd.add(0, "");
        pd.add(1, "正确");
        pd.add(2, "错误");
        int size = 1;
        for (AnswersInfo vAnswersInfo:data){
            String type=vAnswersInfo.getBaseType();
            String tn="";
            if(type==null||type.isEmpty())type="5";
            if (type.contains("1")){
                tn="单选";
            }else if (type.contains("2")){
                tn="多选";
            }else if (type.contains("3")){
                tn="填空";
            }else if (type.contains("4")){
                tn="判断";
            }
            vStringBuilder.append("\n 题目"+(size++));
            vStringBuilder.append(" "+vAnswersInfo.getContentText());
            vStringBuilder.append(" ("+tn+")\n");
            vStringBuilder.append("\n   答案：");

            String answ;

            if (type.contains("4")){
                answ=pd.get(Integer.parseInt(vAnswersInfo.getAnswersContent()));
            }else {
                answ=vAnswersInfo.getAnswersContent();
            }

            vStringBuilder.append(answ);
            vStringBuilder.append("\n  ");
        }
        return vStringBuilder.toString();
    }

    //微课
    public List<cellInfo> getWkCellsList(String course) {
        List<cellInfo> vCellInfoList = new ArrayList<>();
        String resp = mIcveApiW.getMicroHeadInfo(course);
        if (resp == null || !resp.contains("cells")) return vCellInfoList;
        JSONArray vJSONArray = Tool.parseJsonA(resp, "cells");
        if (vJSONArray.size() == 0) return vCellInfoList;
        vCellInfoList = Tool.parseJsonA(resp, "cells", cellInfo.class);
        return vCellInfoList;
    }

    public ViewInfo getMicroView(String cellId) {
        //刷课
        String resp = "";
        resp = mIcveApiW.getMicroView(cellId);
        if (resp == null || !resp.contains("works")) return null;
        ViewInfo viewInfos = JSONObject.parseObject(resp, ViewInfo.class);
        return viewInfos;
    }

    public void getJcInfo(zjyUser zjyUser) {
        String resp = mIcveApiW.getJcInfo(zjyUser);
        System.out.println(resp);
    }

}
