package com.vms.ykt.yktStuMobile.zjy;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.Util.Tool;

import java.util.ArrayList;
import java.util.List;

public class zjyMain {


    private static String TAG = zjyMain.class.getSimpleName();

    public static List<zjyCourseIfno> geAlltCoures(zjyUser ZjyUser) {
/**获取所有课程**/
        List<zjyCourseIfno> zjyCourseInfoList = new ArrayList<>();
        int pageSize = 1;
        String resp = "";
        while (true) {
            resp = zjyApi.getAllCourseInfo(ZjyUser, String.valueOf(pageSize));
            Log.d(TAG, "geAlltCoures: " + resp);
            if (resp == null || !resp.contains("dataList")) break;
            JSONObject ret = JSONObject.parseObject(resp);
            JSONArray retArr = ret.getJSONArray("dataList");
            if (retArr.size() == 0) break;
            for (int i = 0; i < retArr.size(); i++) {
                String courses = retArr.getJSONObject(i).toString();
                Log.d(TAG, courses);
                zjyCourseInfoList.add(JSON.parseObject(courses, zjyCourseIfno.class));
            }
            pageSize++;
        }
        return zjyCourseInfoList;
    }

    public static List<zjyModuleListInfo> getModuleList(zjyUser zjyUser, zjyCourseIfno zjyAllCourseIfno) {

        String resp = "";
        ArrayList<zjyModuleListInfo> zjyModuleListI = new ArrayList<>();
        resp = zjyApi.getModuleList(zjyUser, zjyAllCourseIfno);
        if (resp == null || !resp.contains("moduleList")) return zjyModuleListI;
        JSONObject getModuleList = JSONObject.parseObject(resp);
        String retCode = getModuleList.getString("code");
        if (!retCode.equals("1")) return zjyModuleListI;
        JSONArray retArr = getModuleList.getJSONArray("moduleList");
        if (retArr.size() == 0) return zjyModuleListI;
        for (int i = 0; i < retArr.size(); i++) {
            String ModuleList = retArr.getJSONObject(i).toString();
            zjyModuleListI.add(JSON.parseObject(ModuleList, zjyModuleListInfo.class));
        }

        return zjyModuleListI;
    }

    public static List<zjyTopicList> getTopicList(zjyUser zjyUser, zjyCourseIfno zjyAllCourseIfno, zjyModuleListInfo zjyModuleListInfo) {

        String resp = "";
        ArrayList<zjyTopicList> zjyTopicList = new ArrayList<>();
        resp = zjyApi.getTopicList(zjyUser, zjyAllCourseIfno, zjyModuleListInfo);
        if (resp == null || !resp.contains("topicList")) return zjyTopicList;
        JSONObject getTopicList = JSONObject.parseObject(resp);
        String retCode = getTopicList.getString("code");
        if (!retCode.equals("1")) return zjyTopicList;
        JSONArray retArr = getTopicList.getJSONArray("topicList");
        if (retArr.size() == 0) return zjyTopicList;
        for (int i = 0; i < retArr.size(); i++) {
            String TopicList = retArr.getJSONObject(i).toString();
            zjyTopicList.add(JSON.parseObject(TopicList, zjyTopicList.class));
        }

        return zjyTopicList;
    }

    public static List<zjyCellList> getCellList(zjyUser zjyUser, zjyCourseIfno zjyAllCourseIfno, zjyTopicList zjyTopicList) {

        ArrayList<zjyCellList> zjyCellList = new ArrayList<>();
        String resp = "";
        resp = zjyApi.getCellList(zjyUser, zjyAllCourseIfno, zjyTopicList);
        JSONObject getCellList = JSONObject.parseObject(resp);

        if (resp == null || !resp.contains("cellList")) return zjyCellList;

        String retCode = getCellList.getString("code");
        if (!retCode.equals("1")) {
            Log.d(TAG, "getCellList: 获取失败");
            return zjyCellList;
        }
        JSONArray retArr = getCellList.getJSONArray("cellList");
        if (retArr.size() == 0) {
            Log.d(TAG, "getCellList: 课件为空");
            return zjyCellList;
        }
        for (int i = 0; i < retArr.size(); i++) {
            String CellList = retArr.getJSONObject(i).toString();
            zjyCellList zjyCellLists = JSON.parseObject(CellList, zjyCellList.class);

            zjyCellLists.setCellChildNodeList(null);

            if (zjyCellLists.getCategoryName().contains("子节点")) {
                JSONArray cellChild = JSONObject.parseObject(CellList).getJSONArray("cellChildNodeList");
                if (cellChild.size() != 0) {
                    ArrayList<zjyCellChildList> zjyCellChildLists = new ArrayList<>();

                    for (int j = 0; j < cellChild.size(); j++) {
                        String Js = cellChild.getJSONObject(j).toString();

                        /**循环获取 子节点下的的每个课件信息 用于刷课 评论 问题 等*/

                        zjyCellChildList varZjyCellChildList = JSONObject.parseObject(Js, zjyCellChildList.class);

                        zjyCellChildLists.add(varZjyCellChildList);

                    }
                    zjyCellLists.setCellChildNodeList(zjyCellChildLists);

                }
            } else {
                /**不是子节点 直接 获取课件信息 用于刷课 评论 问答等*/

            }


            zjyCellList.add(zjyCellLists);

        }
        return zjyCellList;

    }

    public static zjyInfoByCelld getCellInfoByCelld(zjyUser zjyUser, zjyCourseIfno zjyAllCourseIfno, zjyCellList zjyCellList) {
        String resp = "";
        resp = zjyApi.getCellInfoByCelld(zjyUser, zjyAllCourseIfno, zjyCellList);
        zjyInfoByCelld varZjyInfoByCelld = null;
        if (resp == null || !resp.contains("cellInfo")) return varZjyInfoByCelld;
        JSONObject InfoByCelld = JSONObject.parseObject(resp);
        varZjyInfoByCelld = JSONObject.parseObject(InfoByCelld.getJSONObject("cellInfo").toString(), zjyInfoByCelld.class);
        return varZjyInfoByCelld;
    }

    public static String getStuProcessCellLog(zjyUser zjyUser, zjyCourseIfno zjyAllCourseIfno, zjyCellList zjyCellList, zjyInfoByCelld zjyInfoByCelld, long time) {

        return zjyApi.getStuProcessCellLog(zjyUser, zjyAllCourseIfno, zjyCellList, zjyInfoByCelld, time);
    }


    public static ArrayList<zjyTeachInfo> getDayTeachInfo(zjyUser ZjyUser) {

        ArrayList<zjyTeachInfo> varDayInfoList = new ArrayList<>();
        String resp = zjyApi.getStuFaceTeachList(ZjyUser);
        if (resp == null || !resp.contains("dataList")) return varDayInfoList;
        JSONObject json = JSONObject.parseObject(resp);
        JSONArray varJSONArray = json.getJSONArray("dataList");
        //今日课堂
        for (int i = 0; i < varJSONArray.size(); i++) {
            String js = varJSONArray.getJSONObject(i).toJSONString();
            zjyTeachInfo varInfo = JSON.parseObject(js, zjyTeachInfo.class);
            varDayInfoList.add(varInfo);

        }

        /**  varDayInfoList.forEach((zjyTeachInfo ZjyTeachInfo)-> {
         System.out.println(ZjyTeachInfo.getCourseName());
         if (ZjyTeachInfo.getCourseName().contains("222")) {
         String resps = zjyApi.getCourseActivityList2(ZjyUser, ZjyTeachInfo);//获取课中活动
         // System.out.println(resps);
         ArrayList<zjyCouresActivitInfo> varCouresActivitInfos = parseActInfo(resps);
         for (zjyCouresActivitInfo varInfo1 : varCouresActivitInfos) {

         }
         }
         });**/

        return varDayInfoList;

    }

    //课堂活动
    public static List<zjyCouresActivitInfo> getCourseActivityList1(zjyUser zjyUser, zjyTeachInfo zjyDayTeachInfo) {
        String resp= zjyApi.getCourseActivityList1(zjyUser, zjyDayTeachInfo);
        return  parseActInfo(resp);
    }

    public static List<zjyCouresActivitInfo> getCourseActivityList2(zjyUser zjyUser, zjyTeachInfo zjyDayTeachInfo) {
        String resp= zjyApi.getCourseActivityList2(zjyUser, zjyDayTeachInfo);
        return  parseActInfo(resp);
    }

    public static List<zjyCouresActivitInfo> getCourseActivityList3(zjyUser zjyUser, zjyTeachInfo zjyDayTeachInfo) {
        String resp= zjyApi.getCourseActivityList3(zjyUser, zjyDayTeachInfo);
        return  parseActInfo(resp);
    }

    public static ArrayList<zjyCouresActivitInfo> parseActInfo(String resp) {
        //解析课堂活动
        ArrayList<zjyCouresActivitInfo> varList = new ArrayList<>();
        if (resp == null || !resp.contains("dataList")) return varList;
        JSONObject json = JSONObject.parseObject(resp);
        JSONArray ActivityList = json.getJSONArray("dataList");
        for (int i = 0; i < ActivityList.size(); i++) {
            String Activityinfo = ActivityList.getString(i);
            Log.d(TAG, "parseActInfo:" + Activityinfo);
            zjyCouresActivitInfo varActivitInfo = JSONObject.parseObject(Activityinfo, zjyCouresActivitInfo.class);
            varList.add(varActivitInfo);
        }
        return varList;
    }

    public static List<zjyTeachInfo> getAllFaceTeach(zjyUser zjyUser, zjyCourseIfno zjyAllCourseIfno) {
/**所有课堂**/
        ArrayList<zjyTeachInfo> zjyAllTeachList = new ArrayList<>();
        String resp = "";
        int pageSize = 1;
        while (true) {
            resp = zjyApi.getAllFaceTeachList(zjyUser, zjyAllCourseIfno, pageSize);
            if (resp == null) continue;
            JSONObject ret = JSONObject.parseObject(resp);
            String retCode = ret.getString("code");
            if (!retCode.equals("1")) break;
            JSONArray retArr = ret.getJSONArray("dataList");
            if (retArr.size() == 0) break;
            for (int i = 0; i < retArr.size(); i++) {
                String courses = retArr.getJSONObject(i).toString();
                zjyAllTeachList.add(JSON.parseObject(courses, zjyTeachInfo.class));
            }

            pageSize++;
        }


        return zjyAllTeachList;
    }


    public static List<HomeworkInfo> getAllHomeWork(zjyUser ZjyUser, zjyCourseIfno CourseIfno) {
        List<HomeworkInfo> varHomeworkInfoList = new ArrayList<>();
        int pageSize = 1;
        String resp = "";
        while (true) {
            resp = zjyApi.getHomeworkList_us(ZjyUser, CourseIfno, String.valueOf(pageSize));
            if (resp == null || !resp.contains("homeworkList")) break;
            JSONObject json = JSONObject.parseObject(resp);
            if (!json.getString("code").equals("1")) break;
            if (json.getJSONArray("homeworkList").size() == 0)
                break;
            String js = json.getString("homeworkList");
            List<HomeworkInfo> varList = JSONArray.parseArray(js, HomeworkInfo.class);
            varHomeworkInfoList.addAll(varList);
            pageSize++;
        }
        return varHomeworkInfoList;
    }


    public static List<ExamInfo> getAllExamWork(zjyUser ZjyUser, zjyCourseIfno CourseIfno) {
        List<ExamInfo> varExamInfoList = new ArrayList<>();
        int pageSize = 1;
        String resp = "";
        while (true) {
            resp = zjyApi.getExamList_us(ZjyUser, CourseIfno, String.valueOf(pageSize));
            if (resp == null || !resp.contains("examList")) break;
            JSONObject json = JSONObject.parseObject(resp);
            if (!json.getString("code").equals("1")) break;
            if (json.getJSONArray("examList").size() == 0)
                break;
            String js = json.getString("examList");
            List<ExamInfo> varList = JSONArray.parseArray(js, ExamInfo.class);
            varExamInfoList.addAll(varList);
            pageSize++;
        }
        return varExamInfoList;
    }


    public homeWorkViewInfo getHomeWorkPreview(zjyUser mZjyUser, zjyCourseIfno mCourseIfno, HomeworkInfo mHomeworkInfo) {
        /**获取作业题目*/
        homeWorkViewInfo varHomeWorkViewInfo = null;
        String resp;
        resp = zjyApi.getHomeworkStuRecord(mZjyUser, mCourseIfno, mHomeworkInfo);
        resp = zjyApi.getPreview(mZjyUser, mCourseIfno, mHomeworkInfo);
        if (resp == null || !resp.contains("data")) return varHomeWorkViewInfo;
        JSONObject json = JSONObject.parseObject(resp);
        String termId = json.getString("termId");
        String hkResId = json.getString("hkResId");
        JSONObject data = json.getJSONObject("data");
        varHomeWorkViewInfo = JSONObject.parseObject(data.toJSONString(), homeWorkViewInfo.class);
        varHomeWorkViewInfo.setTermId(termId);
        return varHomeWorkViewInfo;
    }

    public ExamViewInfo getExamViewPreview(zjyUser mZjyUser,zjyCourseIfno mCourseIfno, ExamInfo mExamInfo) {
        /**获取考试题目·*/
        ExamViewInfo varExamViewInfo = null;
        Log.d(TAG, "doExamM: "+zjyApi.getExamIsAuthenticate(mZjyUser,mCourseIfno, mExamInfo));
        Log.d(TAG, "doExamM: "+zjyApi.getMqttTokenByExam(mZjyUser,mCourseIfno, mExamInfo));
        String resp = zjyApi.getNewExamPreviewNew(mZjyUser,mCourseIfno, mExamInfo);
         if (resp==null||!resp.contains("data"))return varExamViewInfo;
        JSONObject json = JSONObject.parseObject(resp);
        JSONObject data = json.getJSONObject("data");
         varExamViewInfo = JSONObject.parseObject(data.toJSONString(), ExamViewInfo.class);
        return varExamViewInfo;
    }


    public static String getAskAnswer(String qids) {
        String resp = "";
        String token = zjyApi.getToken();
        if (token == null || !token.contains("data")) return resp;
        JSONObject json = JSONObject.parseObject(token);
        token = json.getJSONObject("data").getString("Code_token");
        if (token == null) return resp;
        resp = zjyApi.getAskAnswer(qids, token);
        Tool.logi("", resp);
        return resp;
    }

    public static String getAskAnswer2(List<questionIfon> varQuestionIfons) {
        StringBuffer varStringBuilder = new StringBuffer();
        String resp = "";
        for (questionIfon varIfon : varQuestionIfons) {
            varStringBuilder.append("," + varIfon.getQuestionId());
        }
        String qids = varStringBuilder.toString().replaceFirst(",", "");
        resp = zjyApi.getAskAnswer(qids);
        if (resp == null) return "失败";
        resp = JSONObject.parseObject(resp).toJSONString();
        return resp;
    }

    public static String showAnsw(ArrayList<answerInfo> varAnswerInfos) {
        StringBuffer vStringBuffer = new StringBuffer();
        for (answerInfo varAnswerInfo : varAnswerInfos) {
            vStringBuffer.append("\n" + varAnswerInfo.getTitle() + " type*" + varAnswerInfo.getType() + "\n");
            vStringBuffer.append("答案：" + "\n");
            if (!varAnswerInfo.getAnswer().isEmpty() || varAnswerInfo.getAnswer() != null)
                vStringBuffer.append(varAnswerInfo.getAnswer() + "\n");
            if (!varAnswerInfo.getAnswerList().isEmpty() || varAnswerInfo.getAnswerList() != null) {
                for (String varS : varAnswerInfo.getAnswerList()) {
                    vStringBuffer.append(varS + "\n");
                }
            }

        }
        return vStringBuffer.toString();

    }

    public static ArrayList<answerInfo> parseAnsw(List<questionIfon> varQuestionIfons) {
        ArrayList<answerInfo> varAnswerInfos = new ArrayList<>();
        StringBuilder varStringBuilder = new StringBuilder();
        for (questionIfon varIfon : varQuestionIfons) {
            varStringBuilder.append("," + varIfon.getQuestionId());
        }
        String qids = varStringBuilder.toString().replaceFirst(",", "");
        Log.d(TAG, "parseAnsw: " + qids);
        String answJson = getAskAnswer(qids);
        if (answJson == null || answJson.isEmpty()) return varAnswerInfos;
        JSONObject asnw = JSONObject.parseObject(answJson);
        JSONArray answs = asnw.getJSONArray("data");
        for (int i = 0; i < answs.size(); i++) {
            answJson = answs.getString(i);
            answerInfo varAnswerInfo = JSONObject.parseObject(answJson, answerInfo.class);
            JSONObject answerList = JSONObject.parseObject(answJson);
            JSONArray answerLists = answerList.getJSONArray("answerList");
            ArrayList<String> answlist = new ArrayList<>();
            if (answerLists.size() != 0) {
                for (int j = 0; j < answerLists.size(); j++) {
                    answJson = answerLists.getString(j);
                    answlist.add(answJson);
                }
            }
            varAnswerInfo.setAnswerList(answlist);
            varAnswerInfos.add(varAnswerInfo);
        }
        return varAnswerInfos;
    }

    public String isUpdate(zjyUser userInfo) {
        String res = "";
        return res;
    }
}
