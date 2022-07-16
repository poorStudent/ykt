package com.vms.ykt.yktStuWeb.Cqooc;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.Util.Tool;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class cqoocMain implements Serializable {

    public cqApi getCqApi() {
        return mCqApi;
    }

    public void setCqApi(cqApi cqApi) {
        mCqApi = cqApi;
    }

    private cqApi mCqApi;

    private static String TAG = cqoocMain.class.getSimpleName();

    public List<cqoocCourseInfo> getAllCourse(userInfo UserInfo) {
        String ownerId = UserInfo.getId();
        List<cqoocCourseInfo> vVarCqoocCourseInfoList = new ArrayList<>();
        List<cqoocCourseInfo> vVarCqoocCourseInfoList2 = new ArrayList<>();
        String resp = "";
        resp = mCqApi.getCourseInfo2(ownerId);
        if (resp != null && resp.contains("data")) {
            List<cqoocCourseInfo> varInfoList = parseCourse(resp);
            vVarCqoocCourseInfoList.addAll(varInfoList);
        }
        resp = mCqApi.getCourseInfo1(ownerId);
        if (resp != null && resp.contains("data")) {
            List<cqoocCourseInfo> varInfoList = parseCourse(resp);
            vVarCqoocCourseInfoList.addAll(varInfoList);
        }
        long endTime;
        for (cqoocCourseInfo vVarCqoocCourseInfo : vVarCqoocCourseInfoList) {
            if (vVarCqoocCourseInfo.getEndDate() != null) {
                endTime = Long.parseLong(vVarCqoocCourseInfo.getEndDate());
                if (endTime < System.currentTimeMillis()) {
                    continue;
                }
            }

            vVarCqoocCourseInfoList2.add(vVarCqoocCourseInfo);

        }

        return vVarCqoocCourseInfoList;
    }

    public ArrayList<String> getFinishaLessons(userInfo UserInfo, cqoocCourseInfo varCqoocCourseInfo) {
        ArrayList<String> FinishaLessons = new ArrayList<>();
        String resps;
        int start = 1;
        while (true) {
            resps = mCqApi.getFlishLessons(varCqoocCourseInfo.getCourseId(), UserInfo.getUsername(), 150, start);
            if (resps == null) break;
            JSONArray varList = Tool.parseJsonA(resps, "data");
            if (varList.size() == 0 || varList.isEmpty()) break;
            for (int i = 0; i < varList.size(); i++) {
                String sectionId = Tool.parseJsonS(varList.getString(i), "sectionId");
                FinishaLessons.add(sectionId);
            }
            start += 150;
        }
        return FinishaLessons;
    }


    public List<cellLessonsInfo> getAlllessons(cqoocCourseInfo cqoocCourseInfo) {
        List<cellLessonsInfo> varCellLessonsList = new ArrayList<>();
        String resps;
        int start = 1;
        while (true) {
            resps = mCqApi.getAlllessons(cqoocCourseInfo.getCourseId(), 250, start);
            if (resps == null) break;
            List<cellLessonsInfo> varList = parseLessons(resps);
            if (varList.size() == 0 || varList.isEmpty()) break;
            varCellLessonsList.addAll(varList);
            start += 250;
        }
        //直接获取全部课件信息
        return varCellLessonsList;
    }

    public List<cellLessonsInfo> parseLessons(String resps) {
        //解析每个课件信息
        List<cellLessonsInfo> varCellLessonsList = new ArrayList<>();
        if (resps == null || resps.isEmpty()) return varCellLessonsList;
        JSONArray varJSONArray = Tool.parseJsonA(resps, "data");
        if (varJSONArray.size() == 0 || varJSONArray.isEmpty()) return varCellLessonsList;
        for (int i = 0; i < varJSONArray.size(); i++) {
            String js = varJSONArray.getString(i);
            cellLessonsInfo varCellLessonsInfo = JSONObject.parseObject(js, cellLessonsInfo.class);
            String resource = Tool.parseJsonS(js, "resource");
            String forum = Tool.parseJsonS(js, "forum");
            varCellLessonsInfo.setCellResourceInfo(JSONObject.parseObject(resource, cellResourceInfo.class));
            varCellLessonsInfo.setCellForumInfo(JSONObject.parseObject(forum, cellForumInfo.class));
            varCellLessonsList.add(varCellLessonsInfo);
        }
        return varCellLessonsList;
    }

    public cellLessonsInfo parseLessonsTests(cellLessonsInfo varCellLessonsInfo) {
        //解析每个课件信息
        String title = Tool.parseJsonS(varCellLessonsInfo.getTest(), "title");
        varCellLessonsInfo.setTitle(title);
        return varCellLessonsInfo;
    }

    public String parseTestAnsw(String resps) {
        if (resps == null || !resps.contains("body")) return null;
        JSONArray data = Tool.parseJsonA(resps, "data");
        JSONArray body = Tool.parseJsonA(data.getString(0), "body");
        if (body.size() == 0) return null;
        StringBuilder answ = new StringBuilder();
        for (int i = 0; i < body.size(); i++) {
            String js = body.getString(i).replace("{", "").replace("}", "");
            answ.append(",");
            answ.append(js);
        }
        return answ.toString().replaceFirst(",", "");
    }


    public List<ModleChaptersInfo> getModleChapters(String CourseId) {
        List<ModleChaptersInfo> varModleChaptersInfoList = new ArrayList<>();
        String resp = mCqApi.getModleChapters(CourseId, 250);
        if (resp == null || resp.isEmpty()) return varModleChaptersInfoList;
        varModleChaptersInfoList = (List<ModleChaptersInfo>) Tool.parseJsonA(resp, "data", ModleChaptersInfo.class);
        return varModleChaptersInfoList;
    }

    public List<ModleChaptersInfo> parseModle(List<ModleChaptersInfo> ParmsList) {
        //模块列表
        List<ModleChaptersInfo> varModlelist = new ArrayList<>();
        for (ModleChaptersInfo varModleChaptersInfo : ParmsList) {
            if (varModleChaptersInfo.getLevel().equals("1")) {
                varModlelist.add(varModleChaptersInfo);
            }
        }
        return varModlelist;
    }

    public List<ModleChaptersInfo> parseChapter(List<ModleChaptersInfo> ParmsList) {
        //章节列表
        List<ModleChaptersInfo> varChapterlist = new ArrayList<>();
        for (ModleChaptersInfo varModleChaptersInfo : ParmsList) {
            if (varModleChaptersInfo.getLevel().equals("2")) {
                varChapterlist.add(varModleChaptersInfo);
            }
        }
        return varChapterlist;
    }

    public List<cellLessonsInfo> getLessonsByChap(cqoocCourseInfo cqoocCourseInfo, ModleChaptersInfo varModleChaptersInfo) {
//根据章节id获取每个课件信息
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ParmsE) {
            ParmsE.printStackTrace();
        }
        String resps = mCqApi.getLessonsByChap(cqoocCourseInfo.getCourseId(), varModleChaptersInfo.getId(), 250);
        if (resps == null) return new ArrayList<cellLessonsInfo>();
        return parseLessons(resps);
    }

    public List<cqoocCourseInfo> parseCourse(String resp) {
        //所有课程
        List<cqoocCourseInfo> vVarCqoocCourseInfoList = new ArrayList<>();
        JSONArray varJSONArray;
        JSONObject varJSONObject;
        String json = "";
        if (!resp.isEmpty()) {
            varJSONArray = Tool.parseJsonA(resp, "data");
            if (varJSONArray != null || !varJSONArray.isEmpty()) {
                if (varJSONArray.size() != 0) {
                    for (int i = 0; i < varJSONArray.size(); i++) {
                        json = varJSONArray.getString(i);
                        Log.d(TAG, "parseCourse: " + json);
                        cqoocCourseInfo vVarCqoocCourseInfo = JSONObject.parseObject(json, cqoocCourseInfo.class);
                        String json1 = Tool.parseJsonS(json, "course");
                        String endtime = Tool.parseJsonS(json1, "endDate");
                        String pic = Tool.parseJsonS(json1, "coursePicUrl").replace("\\", "");
                        String courseManager = Tool.parseJsonS(json1, "courseManager");
                        vVarCqoocCourseInfo.setCourseManager(courseManager);
                        vVarCqoocCourseInfo.setEndDate(endtime);
                        String json2 = Tool.parseJsonS(json, "userInfo");
                        String signNum = Tool.parseJsonS(json2, "signNum");
                        String staytime = Tool.parseJsonS(json2, "staytime");
                        vVarCqoocCourseInfo.setCoursePicUrl("http://www.cqooc.com" + pic);
                        vVarCqoocCourseInfo.setSignNum(signNum);
                        vVarCqoocCourseInfo.setStaytime(staytime);
                        vVarCqoocCourseInfoList.add(vVarCqoocCourseInfo);
                    }
                }
            }
        }
        return vVarCqoocCourseInfoList;
    }

    public String getOtherFourmCt(String courseId, String forumId) {
        String OtherFourmCt = "";
        List<String> cts = new ArrayList<>();
        String resp = mCqApi.getForumAnsw(courseId, forumId);
        if (resp == null) return OtherFourmCt;
        JSONArray varArray = Tool.parseJsonA(resp, "data");
        if (varArray.isEmpty() || varArray.size() == 0) return OtherFourmCt;
        for (int i = 0; i < varArray.size(); i++) {
            String ct = Tool.parseJsonS(varArray.getString(i), "content");
            if (ct.trim().isEmpty()) continue;
            cts.add(ct);
        }
        if (cts.isEmpty() || cts.size() == 0) return OtherFourmCt;
        OtherFourmCt = Tool.getRandomStr(cts);
        return OtherFourmCt;
    }

    public userInfo getUsreInfo(String xsid) {
        String resp = "";
        userInfo varUserInfo = null;
        resp = mCqApi.getUsreInfo1();

        if (resp != null && !resp.isEmpty()) {
            varUserInfo = JSONObject.parseObject(resp, userInfo.class);
        }

        resp = mCqApi.getUsreInfo2(xsid);

        if (resp != null && !resp.isEmpty()) {

            String id = JSONObject.parseObject(resp).getString("id");
            varUserInfo.setId(id);
        }
        return varUserInfo;
    }

}
