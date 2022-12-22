package com.vms.ykt.yktStuWeb.mooc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuMobile.mooc.DiscussInfo;
import com.vms.ykt.yktStuMobile.mooc.DiscussTopInfo;
import com.vms.ykt.yktStuMobile.mooc.moocApi;
import com.vms.ykt.yktStuMobile.mooc.moocCellInfo;
import com.vms.ykt.yktStuMobile.mooc.moocChildCellInfo;
import com.vms.ykt.yktStuMobile.mooc.moocCourseInfo;
import com.vms.ykt.yktStuMobile.mooc.moocModInfo;
import com.vms.ykt.yktStuMobile.mooc.moocTopicInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.Cqooc.ModleChaptersInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class moocMianW  {



    public static  List<moocCourseInfo> getCourseOpenList(){
        String resp="";
        List<moocCourseInfo> varCourseInfoList=new ArrayList<>();
        resp= moocApiW.getCourseOpenList();
        if (resp==null||!resp.contains("list"))return varCourseInfoList;
        JSONObject json=JSONObject.parseObject(resp);
        if (json==null||!json.getString("code").equals("1"))return varCourseInfoList;
        varCourseInfoList =JSONArray.parseArray(json.getString("list"), moocCourseInfo.class);
        for (moocCourseInfo varCourseInfo: varCourseInfoList) {
        }
        return varCourseInfoList;
    }

    //获取课程
    private static ArrayList<moocCourseInfo> getMyCourseListU(zjyUser ZjyUser,String isFinished){

        ArrayList<moocCourseInfo> varCourseInfos = new ArrayList<>();
        int pageSize = 1;
        while (true) {
            String resp = moocApiW.getMyCourse(String.valueOf(pageSize),isFinished);
            if (resp == null) break;
            JSONObject json = JSONObject.parseObject(resp);
            if (!resp.contains("list") || !json.getString("code").equals("1")) break;
            JSONArray varJSONArray = json.getJSONArray("list");
            if (varJSONArray.size() == 0) break;
            for (int i = 0; i < varJSONArray.size(); i++) {
                String js = varJSONArray.getString(i);
                moocCourseInfo varCourseInfo = JSON.parseObject(js, moocCourseInfo.class);
                varCourseInfos.add(varCourseInfo);
            }
            pageSize++;
        }
        return varCourseInfos;
    }
    private static   ArrayList<moocCourseInfo> getMyCourseList0(zjyUser ZjyUser) {
        //进行中课程
        return getMyCourseListU(ZjyUser,"0");
    }
    private static ArrayList<moocCourseInfo> getMyCourseList1(zjyUser ZjyUser) {
        //已结束课程
        return getMyCourseListU(ZjyUser,"1");
    }
    private static ArrayList<moocCourseInfo> getMyCourseList2(zjyUser ZjyUser) {
        //即将课程
        return getMyCourseListU(ZjyUser,"2");
    }
    public static  ArrayList<moocCourseInfo> getMyCourseList(zjyUser ZjyUser) {
        ArrayList<moocCourseInfo> varCourseInfos = new ArrayList<>();
        varCourseInfos.addAll(getMyCourseList0(ZjyUser));
        varCourseInfos.addAll(getMyCourseList1(ZjyUser));
        varCourseInfos.addAll(getMyCourseList2(ZjyUser));
        return varCourseInfos;
    }

    //获取所有模块
    public static  ArrayList<moocModInfo> getProcessList(moocCourseInfo moocCourseInfo) {
        ArrayList<moocModInfo> ModInfoArrayList =new ArrayList<>();
        String resp=moocApiW.getProcessList(moocCourseInfo);
        if (resp==null||!resp.contains("proces")||!resp.contains("moduleList"))return ModInfoArrayList;
        String moduleList=Tool.parseJsonS(resp, "proces");
        JSONArray vJSONArray =Tool.parseJsonA(moduleList, "moduleList");
        for (int i = 0; i < vJSONArray.size(); i++) {
            moocModInfo vModInfo = JSONObject.parseObject(vJSONArray.getString(i), moocModInfo.class);
            ModInfoArrayList.add(vModInfo );
        }
        return ModInfoArrayList;
    }


    //获取所有章节
    public static  ArrayList<moocTopicInfo> getTopicByModuleId(moocCourseInfo moocCourseInfo, moocModInfo modInfo) {
        ArrayList<moocTopicInfo> vMoocTopicInfoArrayList =new ArrayList<>();
        String resp = moocApiW.getTopicByModuleId(moocCourseInfo,modInfo);
        if (resp==null||!resp.contains("topicList"))return vMoocTopicInfoArrayList;
        JSONArray vJSONArray =Tool.parseJsonA(resp, "topicList");
        for (int i = 0; i < vJSONArray.size(); i++) {
            moocTopicInfo vMoocTopicInfo =JSONObject.parseObject( vJSONArray.getString(i), moocTopicInfo.class);
            vMoocTopicInfoArrayList.add(vMoocTopicInfo);
        }

        return vMoocTopicInfoArrayList;
    }

    //获取所有课件
    public static  ArrayList<moocCellInfo> getCellByTopicId(moocCourseInfo moocCourseInfo, moocTopicInfo topicInfo) {
        ArrayList<moocCellInfo> vMoocCellInfoArrayList =new ArrayList<>();
        String resp = moocApiW.getCellByTopicId(moocCourseInfo,topicInfo);
        if (resp==null||!resp.contains("cellList"))return vMoocCellInfoArrayList;
        JSONArray vJSONArrays =Tool.parseJsonA(resp, "cellList");
        for (int i = 0; i < vJSONArrays.size(); i++) {
            ArrayList<moocChildCellInfo> ChildCellInfoList=  new ArrayList<>();
            String cellList=vJSONArrays.getString(i);
            moocCellInfo vMoocCellInfo =JSONObject.parseObject(cellList, moocCellInfo.class);
            JSONArray  vJSONArray =Tool.parseJsonA(cellList,"childNodeList");
            if (!vJSONArray.isEmpty()) {
                for (int j = 0; j < vJSONArray.size(); j++) {
                    cellList = vJSONArray.getString(j);
                    moocChildCellInfo vMoocChildCellInfo = JSONObject.parseObject(cellList, moocChildCellInfo.class);
                    ChildCellInfoList.add(vMoocChildCellInfo);
                }
            }
            vMoocCellInfo.setChildNodeList(ChildCellInfoList);
            vMoocCellInfoArrayList.add(vMoocCellInfo);
        }

        return vMoocCellInfoArrayList;
    }

    //讨论区所有板块
    public static  ArrayList<DiscussInfo> getCourseCategory(moocCourseInfo moocCourseInfo) {
        ArrayList<DiscussInfo> DiscussInfoArrayList=new ArrayList<>();
        String resp=moocApiW.getCourseCategory(moocCourseInfo);
        if (resp==null||!resp.contains("list"))return DiscussInfoArrayList;
        JSONArray vJSONArray =Tool.parseJsonA(resp, "list");
        for (int i = 0; i < vJSONArray.size(); i++) {
            DiscussInfo vDiscussTopInfo = JSONObject.parseObject(vJSONArray.getString(i), DiscussInfo.class);
            DiscussInfoArrayList.add(vDiscussTopInfo);
        }
        return DiscussInfoArrayList;
    }

    //所有测验 作业 考试
    public static  List<WorkExamList> getAllWorkInfo(String courseOpenId,String Type){
        List<WorkExamList> varWorkExamLists=new ArrayList<>();
        String resp="";
        int page=1;
        JSONObject json=null;
        boolean isOk=false;
        while (!isOk) {
            resp= moocApiW.getWorkExamList(courseOpenId,String.valueOf(page++),Type);
            if (resp==null)return varWorkExamLists;
            json=JSONObject.parseObject(resp);
            if (!json.getString("code").equals("1"))return varWorkExamLists;
            List<WorkExamList>varWorkExamList1=  Tool.parseJsonA(resp,"list", com.vms.ykt.yktStuWeb.mooc.WorkExamList.class);

            if (varWorkExamList1.isEmpty())return varWorkExamLists;
            varWorkExamLists.addAll(varWorkExamList1);
        }
        return varWorkExamLists;
    }

    public static  List<WorkExamList> getAllhomeWork(String courseOpenId){
        return getAllWorkInfo(courseOpenId, "0");
        /**所有作业*/
    }
    public static  List<WorkExamList> getAllTestWork(String courseOpenId){
        return getAllWorkInfo(courseOpenId, "1");
        /**所有测验
         * **/
    }
    public static  List<WorkExamList> getAllonlineExam(String courseOpenId){
        return getAllWorkInfo(courseOpenId, "2");
        /**所有考试
         * **/
    }

    public static   void getMyInfo(){
        String resp="";
        resp= moocApiW.getMyInfo();
        System.out.println(resp);
    }

}
