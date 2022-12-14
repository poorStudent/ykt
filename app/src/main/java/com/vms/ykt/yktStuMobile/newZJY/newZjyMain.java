package com.vms.ykt.yktStuMobile.newZJY;

import android.app.Activity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class newZjyMain {

    private final static String TAG = newZjyMain.class.getSimpleName();

    public static newZjyUser MobileLogin(String mobile, String passwd) {
        String resp = newZjyApi.getMobileLogin(mobile, passwd);
        //System.out.println(resp);
        //Log.d(TAG, resp);
        if (resp == null || !resp.contains("token")) return null;
        newZjyUser vUser = JSONObject.parseObject(resp, newZjyUser.class);
        getSsoUser(vUser);
        return vUser;
    }

    public static void getSsoUser(newZjyUser vUser) {
        //{"success":true,"data":{"sessionId":"797853DA7974764B62A87BC516C2B8DD",
        // "user":{"id":"2w7jafiswazbrev468vb5q","loginId":"venomms","trueName":"魏海旭","password":null,"photoLink":null,"siteCode":"zhzj","roleCode":"0","roleName":"学生","isDelete":null,"alias":null}},"code":"200","message":null}
        String resp = newZjyApi.getSsoUser();
        if (resp == null || !resp.contains("id")) return;
        JSONObject js1 = Tool.parseJsonO(resp, "data");
        if (js1 == null) return;
        String sessionId = js1.getString("sessionId");
        vUser.setSessionId(sessionId);
        JSONObject vJSONObject = js1.getJSONObject("user");
        if (vJSONObject == null) return;
        String id = vJSONObject.getString("id");
        vUser.setId(id);

    }


    public static boolean isLogin(newZjyUser vUser) {
        String CheckUser = newZjyApi.getUserInfo(vUser.getToken());
        if (CheckUser == null || CheckUser.contains("token已过期")) {
            return false;
        }
        return true;
    }


    public static List<newZjyCourse> getMyClassList(newZjyUser user) {
        List<newZjyCourse> CoursesList = new ArrayList<>();
        String resp = newZjyApi.getMyClassList(user.getToken());
        if (resp == null || !resp.contains("data")) return CoursesList;
        CoursesList = Tool.parseJsonA(resp, "data", newZjyCourse.class);
        return CoursesList;
    }


    public static List<ClassRoom> getClassroomByStudent(newZjyCourse vCourse) {
        String resp = newZjyApi.getClassroomByStudent(vCourse.getCourseId());
        return parseClassRoom(resp);
    }

    public static List<ClassRoom> getClassroomByDay() {
        String resp = newZjyApi.getClassroomByDay(Tool.getCurrentData());
        return parseClassRoom(resp);
    }

    public static List<ClassRoom> getAllClassroom() {
        String resp = newZjyApi.getAllClassroom();
        return parseClassRoom(resp);
    }

    public static List<ClassRoom> parseClassRoom(String resp) {
        ArrayList<ClassRoom> classRoom = new ArrayList<>();
        if (resp == null || !resp.contains("records")) return classRoom;
        JSONObject js1 = Tool.parseJsonO(resp, "data");
        js1 = Tool.parseJsonO(js1.toJSONString(), "data");
        classRoom = (ArrayList<ClassRoom>) Tool.parseJsonA(js1.toJSONString(), "records", ClassRoom.class);
        return classRoom;
    }


    public static boolean getRestSsoToken(newZjyUser user) {
         /*{"data":{"userAccessToken":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpbklkIjoidmVub21tcyIsInNpdGVDb2RlIjoiemh6aiIsInVzZXJfbmFtZSI6InZlbm9tbXMiLCJwaG90byI6bnVsbCwic3VJZCI6IjJ3N2phZmlzd2F6YnJldjQ2OHZiNXEiLCJhdXRob3JpdGllcyI6WyJTVFVERU5UIl0sImNsaWVudF9pZCI6InNwb2MtY2xhc3Nyb29tLmljdmUuY29tLmNuIiwidHJ1ZU5hbWUiOiLprY_mtbfml60iLCJyb2xlQ29kZSI6IjAiLCJzY29wZSI6WyJhbGwiXSwicm9sZU5hbWUiOiLlrabnlJ8iLCJleHAiOjE2NjM0MDY3ODgsImp0aSI6ImUxMWFiZjNmLTA1ODgtNDg1MC04ZDQ5LTQzMTA5NmFlODA5MSJ9.APf2pwYL3E3MBAQ0UtsDyzyaDTb4CFHjr2klcyDC_RlkjuNKSbUAK-olAoxtmrLDnbg-KsHdCpjIQ8El5o6Lc3kx82vlYZAWKVwEYnUoh5HyArYXBvlw8oicLGmKltGzGAGicAnLwMCZth45ng_H-drc_qpBWfttnTWrJ4OFv-AonwCXgAz8RXkO1qXNGDqTwjwNlmYM8W-PGW7d3zRRgnTDFtHB1XGOdiPGwV86LbY-CfuxGqzf1HkXXYDI3whmL1v-NwODGu3RjrE1v4aXGCOMwPWE_Xt2woFU_3YMejH8o4zx_G5ujfEuNIqYfOmDCw13MpRDU-yqtLzDc1sRjQ"
        ,"pageToken":"e11abf3f-0588-4850-8d49-431096ae8091"},"errorCode":"200","errorMsg":""}
        * */
        String resp = newZjyApi.getRestSsoToken(user.getToken());
        //System.out.println(resp);
        if (resp == null || !resp.contains("userAccessToken")) return false;
        JSONObject js1 = Tool.parseJsonO(resp, "data");
        String userAccessToken = js1.getString("userAccessToken");
        if (userAccessToken == null || userAccessToken.equals("")) return false;
        String pageToken = js1.getString("pageToken");
        user.setUserAccessToken(userAccessToken);
        user.setPageToken(pageToken);
        return true;
    }

    public static boolean upAuthorization(newZjyUser user) {
        if (getRestSsoToken(user)) {
            newZjyApi.upAuthorization(user.getUserAccessToken());
            return true;
        }
        return false;
    }

    private static boolean getTokenByPageToken(newZjyUser user) {
        String resp = newZjyApi.getTokenByPageToken(user.getPageToken());
        if (resp == null || !resp.contains("refresh_token")) return false;
        JSONObject js1 = Tool.parseJsonO(resp, "data");
        String userAccessToken = js1.getString("refresh_token");
        if (userAccessToken == null || userAccessToken.equals("")) return false;
        String pageToken = js1.getString("jti");
        user.setUserAccessToken(userAccessToken);
        user.setPageToken(pageToken);
        return true;
    }

    public static boolean refreshAuthorization(newZjyUser user) {
        if (getTokenByPageToken(user)) {
            newZjyApi.upAuthorization(user.getUserAccessToken());
            return true;
        }
        return false;
    }

    public static boolean getUNTYXLCOOKIE(newZjyUser user, String courseid) {
        String resp = newZjyApi.getSignLearn(courseid, user.getLoginId());
        if (resp == null || !resp.contains("UNTYXLCOOKIE")) return false;
        user.setUNTYXLCOOKIE(resp);
        return true;
    }

    //UNTYXLCOOKIE
    public static boolean upUNTYXLCOOKIE(newZjyUser user, String courseid) {
        if (getUNTYXLCOOKIE(user, courseid)) {
            newZjyApi.upUNTYXLCOOKIE(user.getUNTYXLCOOKIE());
            return true;
        }
        return false;
    }

    public static boolean getUsersessionidm(newZjyUser user, String courseId, String examCode) {
        String Usersessionid = newZjyApi.getUsersessionidm(courseId, examCode);
        if (Usersessionid.isEmpty() || !Usersessionid.contains("USERSESSIONID")) {
            Usersessionid = "USERSESSIONID=402883ab82fee62c018307a26e3e1f1d#interface#batchCode#attachData; " +
                    "Max-Age=2592000; " +
                    "Domain=spoc-exam.icve.com.cn; Path=/; SameSite=None; Secure";
            Usersessionid = "";
        }

        user.setUSERSESSIONID(Usersessionid);

        return true;
    }

    public static boolean upUsersessionidm(newZjyUser user, String courseId, String examCode) {
        if (getUsersessionidm(user, courseId, examCode)) {
            newZjyApi.upUsersessionid(user.getUSERSESSIONID());
            return true;
        }
        return false;
    }


    public static List<classActivity> getClassActivityQ(ClassRoom vClassRoom) {
        String resp = newZjyApi.getClassActivityQ(vClassRoom.getId());
        return parseClassActivity(resp);
    }

    public static List<classActivity> getClassActivityZ(ClassRoom vClassRoom) {
        String resp = newZjyApi.getClassActivityZ(vClassRoom.getId());
        return parseClassActivity(resp);
    }

    public static List<classActivity> getClassActivityH(ClassRoom vClassRoom) {
        String resp = newZjyApi.getClassActivityH(vClassRoom.getId());
        return parseClassActivity(resp);
    }

    public static List<classActivity> parseClassActivity(String resp) {
        List<classActivity> classActivitys = new ArrayList<>();
        if (resp == null || !resp.contains("items")) return classActivitys;
        JSONObject js1 = Tool.parseJsonO(resp, "data");
        classActivitys = Tool.parseJsonA(js1.toJSONString(), "items", classActivity.class);
        return classActivitys;
    }


    //开启活动
    public static String startActivity(classActivity vActivity) {
        return newZjyApi.getUpdateSignStatus(vActivity.getId(), "1");
    }

    //结束活动
    public static String overActivity(classActivity vActivity) {
        return newZjyApi.getUpdateSignStatus(vActivity.getId(), "2");
    }

    //删除活动
    public static String getDelActivity(String classroomId, String activityId) {
        return newZjyApi.getDelActivity(classroomId, activityId);
    }

    //补签
    public static String patchSign(String RecordId, int type) {
        String signStatus = "";
        //3b9018e3fe3f11ebabc2fa346ba4cb00 已签
        //4404145efe3f11ebabc2fa346ba4cb00 迟到
        //4bbba639fe3f11ebabc2fa346ba4cb00 事假
        //55297083fe3f11ebabc2fa346ba4cb00 公家
        //5d7d6e8afe3f11ebabc2fa346ba4cb00 早退
        //64fb86a0fe3f11ebabc2fa346ba4cb00 病假
        //6da69011fe3f11ebabc2fa346ba4cb00 缺勤
        if (type == 1) {
            signStatus = "3b9018e3fe3f11ebabc2fa346ba4cb00";
        } else if (type == 2) {
            signStatus = "4404145efe3f11ebabc2fa346ba4cb00";
        } else if (type == 3) {
            signStatus = "4bbba639fe3f11ebabc2fa346ba4cb00";
        } else if (type == 4) {
            signStatus = "55297083fe3f11ebabc2fa346ba4cb00";
        } else if (type == 5) {
            signStatus = "5d7d6e8afe3f11ebabc2fa346ba4cb00";
        } else if (type == 6) {
            signStatus = "64fb86a0fe3f11ebabc2fa346ba4cb00";
        } else if (type == 7) {
            signStatus = "6da69011fe3f11ebabc2fa346ba4cb00";
        }
        return newZjyApi.getTeacherSignStatus(RecordId, signStatus);

    }

    public static void patchSign(Activity mContext, SignAndQuestionStu vSignAndQuestionStu, int type) {
        String resp = patchSign(vSignAndQuestionStu.getId(), type);
        String name = vSignAndQuestionStu.getStuName();
        mContext.runOnUiThread(() -> {
            Tool.toast(mContext, name + "\n" + resp);
        });
    }

    //签到
    public static String Sign(classActivity Activitys, String RecordId) {
        String resp = newZjyApi.getSignResult(RecordId);
        //System.out.println(resp);
        if (resp != null) {
            if (resp.contains("已签")) return resp;
        }
        String detailTypeCode = Activitys.getDetailTypeCode();
        if (detailTypeCode.equals("1")) {
            resp = newZjyApi.getStudentSignStatus(RecordId, Activitys.getId());

        } else {
            if (newZjyUserDao.signCode == null || newZjyUserDao.signCode.isEmpty()) {
                resp = newZjyApi.getActivityById(Activitys.getId());
                resp = parseSignCode(resp);
            } else {
                resp = newZjyUserDao.signCode;
            }
            if (resp == null || resp.equals("")) return resp;

            resp = newZjyApi.getStudentSignStatusC(RecordId, Activitys.getId(), resp);

        }

        if (resp == null || !resp.contains("签到成功")) {
            resp = patchSign(RecordId, 1);
            System.out.println(resp);
            return resp;
        }
        // System.out.println(resp);
        return resp;
    }

    public static void Sign(Activity mContext, classActivity vActivity) {
        String name = vActivity.getTypeName();
        String RecordId = vActivity.getRecordId();
        String id = vActivity.getId();
        if (name.contains("签到")) {
            System.out.println("---" + id + " * name " + name + " * getStatus " +
                    vActivity.getStatus() + " * RecordId " + RecordId + " * DetailTypeCode " + vActivity.getDetailTypeCode());
            String resp = vActivity.getClassroomName() + "-" + vActivity.getTypeName() + "\n"
                    + newZjyMain.Sign(vActivity, RecordId);
            mContext.runOnUiThread(() -> {
                Tool.toast(mContext, resp);
            });
        }
    }

    public static void Sign(Activity mContext, classActivity vActivity, SignAndQuestionStu vSignAndQuestionStu) {
        String name = vSignAndQuestionStu.getStuName();
        String RecordId = vSignAndQuestionStu.getId();
        System.out.println("---" + " * name " + name + " * getStatus " +
                vSignAndQuestionStu.getSignStatus() + " * RecordId " + RecordId + " * DetailTypeCode " + vActivity.getDetailTypeCode());
        String resp = name + "-" + RecordId + "\n"
                + newZjyMain.Sign(vActivity, RecordId);
        mContext.runOnUiThread(() -> {
            Tool.toast(mContext, resp);
        });

    }


    public static String parseSignCode(String resp) {
        if (resp == null || !resp.contains("signGesture")) return "";
        String js1 = Tool.parseJsonO(resp, "data").toJSONString();
        String detailTypeName = Tool.parseJsonS(js1, "detailTypeName");
        if (!detailTypeName.contains("手势签到")) return "";
        String signGesture = Tool.parseJsonS(js1, "signGesture");
        if (signGesture.equals("null") || signGesture.equals("")) {
            return "";
        }
        JSONArray signGestures = JSONArray.parseArray(signGesture);
        if (signGestures.size() == 0) return "";
        String codes = "";
        for (int i = 0; i < signGestures.size(); i++) {
            JSONObject js2 = signGestures.getJSONObject(i);
            String code = js2.getString("index");
            codes += code;
        }
        return codes;
    }

    public static void setSignCode() {
        new Thread(() -> {
            String resp = newZjyApi.getActivityById(newZjyUserDao.sClassActivity.getId());
            newZjyUserDao.signCode = parseSignCode(resp);
        }).start();

    }

    public static List<SignAndQuestionStu> getUnSignStudent(String Activityid) {
        String resp = newZjyApi.getUnSignStudent(Activityid);
        return parseSignStudent(resp);
    }

    public static List<SignAndQuestionStu> getSignStudent(String Activityid) {
        String resp = newZjyApi.getSignStudent(Activityid);
        return parseSignStudent(resp);
    }

    public static List<SignAndQuestionStu> getAllSignStudent(String Activityid) {
        List<SignAndQuestionStu> SignStudents = new ArrayList<>();
        SignStudents.addAll(getSignStudent(Activityid));
        SignStudents.addAll(getUnSignStudent(Activityid));
        return SignStudents;
    }

    public static List<SignAndQuestionStu> parseSignStudent(String resp) {
        List<SignAndQuestionStu> SignStudents = new ArrayList<>();
        if (resp == null || !resp.contains("items")) return SignStudents;
        String js1 = Tool.parseJsonS(resp, "data");
        SignStudents = Tool.parseJsonA(js1, "items", SignAndQuestionStu.class);
        return SignStudents;
    }


    public static List<NameCode> getFlagClassroomType() {
        String resp = newZjyApi.getFlagClassroomType();
        return parseNameCode(resp);
    }

    public static List<NameCode> getFlagActivityType() {
        String resp = newZjyApi.getFlagActivityType();
        return parseNameCode(resp);
    }

    public static List<NameCode> getFlagQuestionType() {
        String resp = newZjyApi.getFlagQuestionType();
        return parseNameCode(resp);
    }

    public static List<NameCode> getFlagDiscussType() {
        String resp = newZjyApi.getFlagDiscussType();
        return parseNameCode(resp);
    }

    public static List<NameCode> parseNameCode(String resp) {
        List<NameCode> vNameCodeList = new ArrayList<>();
        if (resp == null || !resp.contains("data")) return vNameCodeList;
        vNameCodeList = Tool.parseJsonA(resp, "data", NameCode.class);
        return vNameCodeList;
    }


    //提问列表

    public static List<SignAndQuestionStu> getQuestionStuListB(String Activityid) {
        String resp = newZjyApi.getQuestionStuListB(Activityid);
        return parseSignStudent(resp);
    }

    //讨论列表
    public static List<SignAndQuestionStu> getPrStuActivityRecord(String Activityid) {
        String resp = newZjyApi.getPrStuActivityRecord(Activityid);
        return parseSignStudent(resp);
    }

    //分组list
    public static List<SignAndQuestionStu> getAllGroups(String activityId) {
        String resp = newZjyApi.getAllGroups(activityId);
        return parseAllGroups(resp);
    }

    public static List<SignAndQuestionStu> parseAllGroups(String resp) {
        List<SignAndQuestionStu> SignStudents = new ArrayList<>();
        if (resp == null || !resp.contains("data")) return SignStudents;
        JSONArray js1 = Tool.parseJsonA(resp, "data");
        for (int i = 0; i < js1.size(); i++) {
            SignAndQuestionStu vSignAndQuestionStu = new SignAndQuestionStu();

            JSONObject vJSONObject = js1.getJSONObject(i);
            String id = vJSONObject.getString("id");
            String title = vJSONObject.getString("title");
            String status = vJSONObject.getString("status");
            String starRating = vJSONObject.getString("starRating");
            String groupMembers = vJSONObject.getString("groupMembers");

            vSignAndQuestionStu.setId(id);
            vSignAndQuestionStu.setStarRating(starRating);
            vSignAndQuestionStu.setTitle(title);
            vSignAndQuestionStu.setStatus(status);
            vSignAndQuestionStu.setGroupMembers(groupMembers);
            SignStudents.add(vSignAndQuestionStu);

        }
        return SignStudents;
    }


    public static List<CellInfo> parseCellItem(String resp) {
        List<CellInfo> CellItemIList = new ArrayList<>();
        if (resp == null || !resp.contains("chapterbox")) return CellItemIList;

        try {
            // File input = new File("E:\\appprojicet\\zjy.html");
            Document doc = Jsoup.parse(resp, "UTF-8");
            Element course_item = doc.getElementById("course_item");
            int size = course_item.childrenSize();
            Element courseitem = null;
            for (int i = 0; i < size; i++) {
                if (course_item.child(i).childrenSize() >= 3) {
                    courseitem = course_item.child(i);
                    break;
                } else {
                    courseitem = course_item.child(1);
                    break;
                }
            }
            if (courseitem != null) {
                Element courseitems = courseitem.getElementById("cTab_con_2");
                if (courseitems != null) {
                    Elements chapterboxs = courseitems.getElementsByClass("chapterbox");
                    int idx = 1;
                    String id = "";
                    for (Element chapterbox : chapterboxs) {

                        //模块名
                        Elements rel_chapterpr_50s = chapterbox.getElementsByClass("rel chapter pr50");
                        if (!rel_chapterpr_50s.isEmpty()) {
                            Element rel_chapterpr_50 = rel_chapterpr_50s.get(0);
                            Elements chapterprspans = rel_chapterpr_50.getElementsByTag("span");
                            for (Element chapterprspan : chapterprspans) {
                                if (chapterprspan.hasText()) {
                                    String name = chapterprspan.text();
                                    System.out.println("-" + name);
                                    break;
                                }
                            }
                        }

                        id = "chapter" + idx + "_con";
                        Element chapter_con = chapterbox.getElementById(id);

                        if (chapter_con != null) {

                            Elements sectionboxs = chapter_con.getElementsByClass("sectionbox");

                            for (Element sectionbox : sectionboxs) {
                                //节名
                                Elements p0_30_bg_eb = sectionbox.getElementsByClass("section p0_30 bg_eb");
                                if (!p0_30_bg_eb.isEmpty()) {
                                    String name = p0_30_bg_eb.get(0).getElementsByTag("span").text();
                                    System.out.println("--" + name);
                                }

                                //节
                                Elements pointboxs = sectionbox.getElementsByClass("pointbox");


                                if (!pointboxs.isEmpty()) {

                                    for (Element pointboxsl : pointboxs) {

                                        // Elements spointboxs = pointboxs.get(0).getAllElements();
                                        Elements spointboxs = pointboxsl.children();


                                        //节下所有小节或资源
                                        for (Element pointbox : spointboxs) {

                                            if (pointbox.hasAttr("waretype")) {

                                                String waretype = pointbox.attr("waretype");

                                                //小节名
                                                if (waretype.contains("section")) {
                                                    String name = pointbox.getElementsByTag("span").get(0).text();
                                                    System.out.println("---" + name + " * " + waretype);

                                                } else {
                                                    //资源解析

                                                    CellItemIList.add(paresCell(pointbox, ""));
                                                }

                                            } else {
                                                //小节所有资源
                                                String courseware_children_b = pointbox.attr("class");
                                                if (courseware_children_b.contains("courseware_children_b")) {
                                                    String pid = pointbox.id();
                                                    Elements ul = pointbox.getElementsByTag("ul");
                                                    if (!ul.isEmpty()) {
                                                        for (Element ulss : ul) {
                                                            for (Element uls : ulss.children()) {
                                                                if (uls.hasAttr("waretype")) {
                                                                    //System.out.println(uls);
                                                                    // System.out.println(uls.html());

                                                                    CellItemIList.add(paresCell(uls, pid));
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                        }

                                    }
                                }

                            }
                        }
                        idx++;
                    }
                }
            }
            // System.out.println(course_item);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return CellItemIList;
    }

    private static CellInfo paresCell(Element uls, String pid) {
        String waretype = uls.attr("waretype");
        String etype = uls.attr("etype");
        String ItemID = uls.id().replace("grandChildItem_", "");
        String videotime = uls.attr("videotime");
        String isok = uls.attr("class");

        CellInfo vCellItemI = new CellInfo();
        if (isok.contains("point_done")) {
            vCellItemI.setOver(1);

        } else {
            vCellItemI.setOver(0);
        }
        vCellItemI.seteType(etype);
        vCellItemI.setWaretype(waretype);
        vCellItemI.setItemId(ItemID);
        vCellItemI.setVideotime(videotime);
        vCellItemI.setParentid(pid);
        vCellItemI.setResID("resourceName_" + ItemID);
        Element spans = uls.getElementsByTag("span").get(0);
        String name = spans.text();
        vCellItemI.setName(name);

        return vCellItemI;
    }

    public static CellItemInfo getItemInfo(String CourseId) {
        String resp = newZjyApi.getQueryLearningItem(CourseId);
        if (resp == null || !resp.contains("data")) return null;
        CellItemInfo vOverCellItems = Tool.parseJsonS(resp, "data", CellItemInfo.class);
        return vOverCellItems;
    }

    //添加课堂
    public static String SaveClassroom(String courseId, String title, String tk) {
        return newZjyApi.getSaveClassroom(courseId, title, tk);
    }


    public static List<SignAndQuestionStu> getStudentsQuestioned(String classroomId) {
        String resp = newZjyApi.getStudentsQuestioned(classroomId);
        return StudentsQuestioned(resp);
    }

    public static List<SignAndQuestionStu> StudentsQuestioned(String resp) {
        List<SignAndQuestionStu> Students = new ArrayList<>();
        if (resp == null || !resp.contains("data")) return Students;
        Students = Tool.parseJsonA(resp, "data", SignAndQuestionStu.class);
        return Students;
    }

    //作业
    public static List<ExamWork> getExam_list_data_w(String courseId) {
        String ret = newZjyApi.getExam_list_data_w(courseId);
        File input = new File("E:\\start Menu\\zjy.html");
        Tool.fw(input, ret);
        return parseExamM(ret);
    }

    //测验
    public static List<ExamWork> getExam_list_data_t(String courseId) {
        String ret = newZjyApi.getExam_list_data_t(courseId);
        return parseExamM(ret);
    }

    //考试
    public static List<ExamWork> getExam_list_data_e(String courseId) {
        String ret = newZjyApi.getExam_list_data_e(courseId);
        return parseExamM(ret);
    }

    //附件作业
    public static String getHomework(String courseId) {
        newZjyApi.upHeader2();
        newZjyHttp.addHeader("Host", "user.icve.com.cn");
        String resp= newZjyApi.getHomework(courseId);

        System.out.println(resp);
        return resp ;
    }


    //examRecordId
    public static String getExamFlow_intoExam(String batchId, String courseId) {

        String resp = newZjyApi.getExamflow_beforeIndex(batchId);
        resp = newZjyApi.getExamFlow_loadingExam(batchId);
        resp = newZjyApi.getExamFlow_intoExam(batchId, courseId);
        resp = getExamRecordId(resp);
        return resp;
    }

    public static String getExamRecordId(String resp) {
        if (resp == null || resp.isEmpty()) return "";
        Document doc = Jsoup.parse(resp, "UTF-8");
        //examRecordId
        String examRecordId = doc.select("input#examRecordId").attr("value");
        ;
        return examRecordId;
    }

    //题目id
    public static ExamWorkQid getExamFlow_getExamPaperInfo(String examRecordId) {

        String resp = newZjyApi.getExamFlow_getExamPaperInfo(examRecordId);
        System.out.println(resp);
        if (resp == null || !resp.contains("questionSeqIdMap")) return null;
        ExamWorkQid vExamWorkQid = new ExamWorkQid();
        JSONObject vJSONObject = Tool.parseJsonO(resp, "data");
        int pageCount = vJSONObject.getIntValue("pageCount");
        int questionCount = vJSONObject.getIntValue("questionCount");
        JSONObject questionSeqIdMap = vJSONObject.getJSONObject("questionSeqIdMap");
        ArrayList<String> qids = new ArrayList();
        Map<String, String> questionIdAndContentIds = new LinkedHashMap<>();
        for (int i = 1; i <= questionCount; i++) {
            String id = questionSeqIdMap.getString(String.valueOf(i));
            qids.add(id);
        }
        JSONObject questionIdAndContentId = vJSONObject.getJSONObject("questionIdAndContentId");
        for (String qid : qids) {
            System.out.println(qid);
            String ContentId = questionIdAndContentId.getString(qid);
            questionIdAndContentIds.put(qid, ContentId);
        }
        vExamWorkQid.setPageCount(pageCount);
        vExamWorkQid.setQuestionCount(questionCount);
        vExamWorkQid.setQuestionIdAndContentIds(questionIdAndContentIds);


        return vExamWorkQid;
    }


    //移动端解析 作业 考试 测验
    public static List<ExamWork> parseExamM(String resp) {
        List<ExamWork> vExamWorks = new ArrayList<>();
        if (resp == null || resp.isEmpty()) {
            return vExamWorks;
        }

        Document doc = Jsoup.parse(resp, "UTF-8");
        Elements list_cards = doc.getElementsByClass("list_card");
        for (Element list_card : list_cards) {
            ExamWork vExamWork = new ExamWork();

            String exam_num = list_card.attr("examcode");
            vExamWork.setExam_num(exam_num);

            String examId = list_card.id();
            vExamWork.setExamId(examId);

            Elements examlist_top = list_card.getElementsByClass("examlist_top");
            //String examStatus = examlist_top.select("span[name*=\"examStatus\"]").first().text();
            String examStatus = examlist_top.select("span[name*=examStatus]").first().text();
            vExamWork.setExamStatus(examStatus);

            //String examName = examlist_top.select("h2[name*=\"examName\"]").first().text();
            String examName = examlist_top.select("h2[name*=examName]").first().text();

            vExamWork.setExamName(examName);

            Elements examlist_score = examlist_top.select("span.examlist_score");
            String score = "0";
            if ( examlist_score.size()!=0) {
                score = examlist_score.first().text().replace("分", "");
            }
            vExamWork.setMy_score(score);
            vExamWorks.add(vExamWork);


        }
        return vExamWorks;
    }


    //web
    //获取课件
    public static String getCourseware_index(String courseId) {
        String resp = newZjyApi.getCourseware_index(courseId);
        System.out.println(resp);
        return resp;
    }


    //作业
    public static List<ExamWork> getExam_list_data_ww(String courseId) {
        String ret = newZjyApi.getExam_list_data_ww(courseId);
        return parseExamW(ret);
    }

    //测验
    public static List<ExamWork> getExam_list_data_tw(String courseId) {
        String ret = newZjyApi.getExam_list_data_tw(courseId);
        return parseExamW(ret);
    }

    //考试
    public static List<ExamWork> getExam_list_data_ew(String courseId) {
        String ret = newZjyApi.getExam_list_data_ew(courseId);
        return parseExamW(ret);
    }

    //附件作业
    public static String getHomeworkw(String courseId) {
        String resp = newZjyApi.getHomeworkw(courseId);
        return resp;
    }


    public static String getExamflow_index(String batchId) {
        String resp = newZjyApi.getExamflow_index(batchId);
        String pattern = "\\s*var examRecordId = \"(.+)\";\\s*var userId =";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(resp);
        String examRecordId = null;
        while (m.find()) {
            examRecordId = m.group(1);
        }

        return examRecordId;
    }


    //web端解析 作业 考试 测验
    public static List<ExamWork> parseExamW(String resp) {
        //File input = new File("E:\\start Menu\\zjy.html");
        //resp=Tool.fr(input);
        List<ExamWork> vExamWorks = new ArrayList<>();
        if (resp == null || resp.isEmpty()) {
            return vExamWorks;
        }

        Document doc = Jsoup.parse(resp, "UTF-8");
        Elements box2s = doc.getElementsByAttributeValue("class", "box2 mt10");
        //System.out.println(box2s.size());
        for (Element box2 : box2s) {
            //Element vElement=box2.select("input[name*=structure]").first();
            //Element vElement=box2.getElementsByAttributeValue("name","structure").get(0);
            ExamWork vExamWork = new ExamWork();
            Element vElement = box2.previousElementSibling();
            String value = vElement.attr("value");

            vExamWork.setValueId(value);
            Elements exam_generals = box2.getElementsByClass("exam_general");
            for (Element exam_general : exam_generals) {
                String examId = exam_general.id();

                vExamWork.setExamId(examId);
                String marking_lock = exam_general.select("span.marking_lock").first().text();

                vExamWork.setMarking_lock(marking_lock);
                String exam_nums = exam_general.getElementsByClass("exam_general_head")
                        .first().select("div.exam_general_num").text();
                vExamWork.setExam_nums(exam_nums);

                String exam_num = exam_nums.replace("编号：", "");
                vExamWork.setExam_num(exam_num);

                Elements exam_general_tits = exam_general.getElementsByClass("exam_general_tit");
                for (Element exam_general_tit : exam_general_tits) {
                    String examName = exam_general_tit.getElementsByClass("examName").attr("title");
                    vExamWork.setExamName(examName);

                    String lab_normal = exam_general_tit.getElementsByAttributeValue("class", "lab_normal ml20").text();
                    vExamWork.setExamStatus(lab_normal);
                }
                String exam_general_sub = exam_general.select("div.exam_general_sub").first().text();

                vExamWork.setExam_general_sub(exam_general_sub);

                String my_score = exam_general.select("div.my_score").text().replace("分", "");
                vExamWork.setMy_score(my_score);

            }
            vExamWorks.add(vExamWork);

        }
        return vExamWorks;
    }


    //获取 PaperId
    public static ExamWorkInfo getExamConfirm(String ExamId) {
        String ret = newZjyApi.getConfirmPagePaperStructure(ExamId);
        //System.out.println(ret);
        if (ret == null || ret.isEmpty()) {
            ret = newZjyApi.getExamPaperStructure(ExamId);
            //System.out.println(ret);
        }

        ExamWorkInfo vExamWorkInfo = newZjyMain.parseExamInfo(ret, ExamId);
        return vExamWorkInfo;
    }


    public static ExamWorkInfo parseExamInfo(String resp, String ExamId) {
        ExamWorkInfo vExamWorkInfo = new ExamWorkInfo();
        if (resp == null || resp.isEmpty() || !resp.contains("paperId")) return vExamWorkInfo;
        JSONObject data = Tool.parseJsonO(resp, "data");
        if (resp.contains(ExamId)) {
            data = data.getJSONObject(ExamId);
        }
        String structure = data.getString("structure");
        vExamWorkInfo.setStructure(structure);
        String paperId = data.getString("paperId");
        vExamWorkInfo.setPaperId(paperId);
        return vExamWorkInfo;
    }


    public static examAnsw getPaperAnsw1(String paperId) {
        String resp = newZjyApi.getPaperStructureForPreview(paperId);

        return parseAnsw(resp, 1);
    }


    public static examAnsw getPaperAnsw2(String paperId) {
        String resp = newZjyApi.getPaperStructure(paperId);

        return parseAnsw(resp, 2);
    }


    public static examAnsw parseAnsw(String resp, int type) {

        examAnsw vExamAnsw = new examAnsw();
        if (resp == null || resp.isEmpty()) return vExamAnsw;

        //File input = new File("E:\\start Menu\\zjy.html");
        //resp = Tool.fr(input);
        Document doc = Jsoup.parse(resp, "UTF-8");
        doc.prepend("<h1 style=background-color:rgb(0,255,0)> 标绿的为正确答案！！！！</h1>");
        Map<String, String> answMap = new LinkedHashMap<>();
        StringBuilder answStr = new StringBuilder();
        List<String> choice = new ArrayList();

        int index = 0;
        String answ = "";
        String css_class = "";

        if (type == 1) {

            Elements exam_items = doc.getElementsByClass("exam_item");
            for (Element exam_item : exam_items) {

                String sectionid = exam_item.attr("sectionid");


                Elements questionContents = exam_item.children();

                for (Element questionContent : questionContents) {


                    String id = questionContent.id();

                    Element exam_answer_tits = questionContent.getElementsByClass("exam_answer_tit").first();
                    String exam_answer_tit = exam_answer_tits.text();

                    answStr.append("\n");
                    answStr.append(exam_answer_tit);


                    String answertype = questionContent.attr("answertype");


                    choice.clear();

                    if (answertype.contains("singlechoice")) {
                        css_class = "label_radio r_on";
                        choice.add("A");
                        choice.add("B");
                        choice.add("C");
                        choice.add("D");
                        choice.add("E");
                    } else if (answertype.contains("multichoice")) {
                        css_class = "label_check c_on";
                        choice.add("A");
                        choice.add("B");
                        choice.add("C");
                        choice.add("D");
                        choice.add("E");
                    } else if (answertype.contains("bijudgement")) {
                        css_class = "label_radio r_on";
                        choice.add("0");
                        choice.add("1");
                    }


                    answStr.append("\n 答案：\n  ");

                    if (answertype.contains("textarea")) {
                        Element exam_keys_con = questionContent.select("div.showHide").select("div.exam_keys")
                                .select("div.exam_keys_con").first();
                        exam_keys_con.attr("style", "background-color:rgb(0,255,0)");
                        answ = exam_keys_con.text().replace("参考答案：", "");

                        answStr.append(answ);

                    } else {
                        Element exam_forms = questionContent.select("div.exam_form").first();

                        Elements labels = exam_forms.select("label");


                        //if (answertype.contains("singlechoice") || answertype.contains("bijudgement") || answertype.contains("multichoice"));

                        for (Element label : labels) {
                            String vClassName = label.className();
                            if (css_class.equals(vClassName)) {

                                String answs = label.text();
                                answStr.append(answs);
                                answStr.append("\n  ");

                                label.attr("style", "background-color:rgb(0,255,0)");

                                answ = answ + "_|_" + index;

                            }
                            index++;
                        }

                        answ = answ.replaceFirst("_\\|_", "");
                    }


                    answMap.put(id, answ);
                    index = 0;
                    answ = "";
                }
            }

        } else {

            Elements assRow_cons = doc.getElementsByClass("assRow_con");
            for (Element assRow_con : assRow_cons) {
                Elements assItems = assRow_con.getElementsByClass("assItem");
                for (Element assItem : assItems) {
                    String id = assItem.id();
                    String answertype = assItem.attr("answertype");
                    Element assItem_tit = assItem.getElementsByClass("assItem_tit").first();
                    String Item_tit = assItem_tit.text();

                    answStr.append("\n");

                    answStr.append(Item_tit);

                    choice.clear();


                    if (answertype.contains("singlechoice")) {
                        choice.add("A");
                        choice.add("B");
                        choice.add("C");
                        choice.add("D");
                        choice.add("E");
                        css_class = "ass_correct";

                    } else if (answertype.contains("multichoice")) {
                        choice.add("A");
                        choice.add("B");
                        choice.add("C");
                        choice.add("D");
                        choice.add("E");
                        css_class = "ass_correct";
                    } else if (answertype.contains("bijudgement")) {
                        css_class = "ass_correct";
                        choice.add("0");
                        choice.add("1");
                    }

                    answStr.append("\n 答案：\n  ");


                    if (answertype.contains("textarea")) {
                        Element assItem_subTit = assItem.select("div.assItem_subTit").first();
                        answ = assItem_subTit.text().replace("参考答案：", "");
                        answStr.append(answ);
                        assItem_subTit.attr("style", "background-color:rgb(0,255,0)");

                    } else {

                        Element assItem_cons = assItem.getElementsByClass("assItem_con").first();
                        Element tbodys = assItem_cons.select("div.paper_table").first().select("table>tbody").first();
                        Elements trs = tbodys.select("tr[name=optionContent]");


                        for (Element tr : trs) {
                            if (tr.hasClass(css_class)) {
                                String answs = tr.text();

                                answStr.append(answs);
                                answStr.append("\n  ");

                                tr.attr("style", "background-color:rgb(0,255,0)");

                                answ = answ + "_|_" + index;

                            }
                            index++;
                        }
                        answ = answ.replaceFirst("_\\|_", "");
                    }

                    answMap.put(id, answ);
                    index = 0;
                    answ = "";
                }

            }

        }

        vExamAnsw.setAnswMap(answMap);
        vExamAnsw.setAnswStr(answStr.toString());
        vExamAnsw.setAnswHtml(doc.html());
        return vExamAnsw;
    }

    //答案组装
    public static Map<String, Object> CompleteAnsw(int seq, String qid, String answ, String saveStatus) {
        Map<String, Object> vMap = new LinkedHashMap<>();
        vMap.put("qSeq", seq);
        vMap.put("qId", qid);
        vMap.put("answer", answ);
        vMap.put("saveStatus", saveStatus);
        return vMap;

    }


    public static String CompleteAnsw1(int seq, String qid, String answ) {
        List<Map<String, Object>> vMaps = new ArrayList<>();
        Map<String, Object> vMap = CompleteAnsw(seq, qid, answ, "todo");
        vMaps.add(vMap);
        return JSONObject.toJSONString(vMaps);
    }


    public static String CompleteAnsw1(Map<String, String> map) {
        int seq = 1;
        List<Map<String, Object>> vMaps = new ArrayList<>();
        for (String qid : map.keySet()) {
            Map<String, Object> vMap = CompleteAnsw(seq, qid, map.get(qid), "todo");
            vMaps.add(vMap);
            seq++;
        }
        return JSONObject.toJSONString(vMaps);
    }

    //做答
    public static String getExamflow_sendManyAnswer(String examRecordId, String studentAnswers, String countDown) {
        String resp = newZjyApi.getExamflow_sendManyAnswer(examRecordId, studentAnswers, countDown);
        return resp;
    }

    //合并结果
    public static String getExamflow_getCompleteQuestionSeq(String examRecordId, String batchId) {
        String resp = newZjyApi.getExamflow_getCompleteQuestionSeq(examRecordId, batchId);
        return resp;
    }

    //提交

    public static String getExamflow_complete(String examRecordId) {

        String resp = newZjyApi.getExamflow_complete(examRecordId);
        return resp;
    }

    public static void doMain() {
        //aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA==
        //9b933b5e625e459ba9df0ea29e9e50ed
        //MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0Q=
        //22befd2145494c4cb15b772c0d66ab07


        String token = "d8baeb4ea0d945d1a34e2ed408be5723";
        //String token = "7d4322bbfaee4cef83fd76cd96e27131";
        //xnzy2113418
        //20030517lei@
        //newZjyUser vUser =MobileLogin("venomms","Poor2579988653");
        //newZjyUser vUser =MobileLogin("xnzy2113418","20030517lei@");
        //ljq1ab-oz7hbmaardmy-2q
        //402883e682d05a190182d3edda901971
        //7d4322bbfaee4cef83fd76cd96e27131
        //String uid = "ljq1ab-oz7hbmaardmy-2q";
        newZjyUser vUser = new newZjyUser();
        vUser.setToken(token);
        vUser.setLoginId("venomms");

        System.out.println(vUser.getToken());
        System.out.println(vUser.getLoginId());


        //测试api
        //System.out.println(testApi.getSaveClassroom());
        //System.out.println(newZjyApi.getPaperStructureForPreview(""));
        //System.out.println(newZjyApi.getExamPaperStatisticsDetail());
        //System.out.println(newZjyApi.getQuestionManage(""));

        //newZjyApi.getUsersessionidw2();
        //      System.exit(0);
        //new RuiKey().Demo();

        if (!isLogin(vUser)) {
            System.out.println("登陆失效");
            return;
        }

        //

        if (!upAuthorization(vUser)) {
            System.out.println("upAuthorization erro");
        }

        if (!upUNTYXLCOOKIE(vUser, "")) {
            System.out.println("upUNTYXLCOOKIE erro");
        }

        if (!upUsersessionidm(vUser, "", "")) {
            System.out.println("upUsersessionidm erro");
        }

        newZjyApi.printHeader();



        //System.out.println(Tool.parseDataTime("1662134400000"));
        //newZjyTestApi.getAuth();
        //newZjyApi.webLogin();

        List<newZjyCourse> CoursesList = getMyClassList(vUser);
        for (newZjyCourse vCourse : CoursesList) {
            String ClassId = vCourse.getClassId();
            String CourseId = vCourse.getCourseId();
            String CourseName = vCourse.getCourseName();
            System.out.println(vCourse.getClassName() + " * " + ClassId + " * " +
                    CourseName + " * " + CourseId);


            // if (!CourseName.contains("789")) continue;
            if (!CourseName.contains("中国传统文化与哲学")) continue;




            //getExamflow_index(ExamId);

            //getCourseware_index(CourseId);

            //newZjyApi.upHeader2();

            //System.exit(0);

            //6049aaaac97845d1a8a790f397962b0a

           //System.out.println(newZjyApi.getCourseExamAction(CourseId,"6049aaaac97845d1a8a790f397962b0a"));

            getHomework(CourseId);

            //doExamWork(CourseId);



            //newZjyApi.upHeader1();
            //  newZjyApi.printHeader();
            System.exit(0);

            List<ClassRoom> ClassRooms = getClassroomByStudent(vCourse);

            for (ClassRoom vClassRoom : ClassRooms) {


                System.out.println("--" + vClassRoom.getTitle() + " * " + vClassRoom.getId());

                String cid = vClassRoom.getId();

                //System.out.println(newZjyApi.getSaveActivityTw1(cid, CourseId, "111111", "1"));

                // getStudentsQuestioned(cid);


                List<classActivity> ClassActivities = getClassActivityZ(vClassRoom);


                for (classActivity vActivity : ClassActivities) {
                    String name = vActivity.getTypeName();
                    String RecordId = vActivity.getRecordId();
                    String id = vActivity.getId();
                    //System.out.println(newZjyApi.getDelActivity(cid, id));
                    if (vActivity.getTypeName().contains("小组")) {
                        getAllGroups(id);
                        //newZjyApi.getUpdateStudentSignStatus();

                    }
                }

                //System.out.println(newZjyApi.getSaveActivityTw1(cid, CourseId, "1111", "1"));
                // System.out.println(newZjyApi.getSaveActivityTw2(cid, CourseId, "1111", "1"));
                // System.out.println(newZjyApi.getSaveActivityTl(cid, CourseId, "1111"));
                // System.out.println(newZjyApi.getCreateGroupActivity(cid, CourseId, ""));

                return;
            }

            return;
            //resp=newZjyApi.getScormCourseItemByName(CourseId);


            //newZjyApi.print(resp);


            //System.out.println(newZjyApi.getModifyClassAuditStatus(ClassId,"1","0"));//允许
            //System.out.println(newZjyApi.getModifyClassAuditStatus(ClassId,"0","0"));//不允许
            //System.out.println(newZjyTestApi.getSaveAssessment(CourseId,ClassId));
            //System.out.println(newZjyTestApi.getCreateStuAssess(CourseId,ClassId));


            // if (!CourseName.contains("三维软件基础"))continue;

            //  System.out.println(newZjyApi.getStuLearnRecord(CourseId,uid));
            //System.out.println(newZjyApi.getLearnRecord(CourseId));
            // sk(vUser, CourseId);


            // String resp;
            //grandChildItem_dd9433f5384f40ab9e77e9f08436ed19
            //grandChildItem_2ac3ca7f6bd7477b9ac43e84d6fa86dc
            //String itid = "dd9433f5384f40ab9e77e9f08436ed19";

        }


    }


    private static void sk(newZjyUser vUser, String CourseId) {


        String resp;

        resp = newZjyApi.getLearnspace(CourseId);//

        List<CellInfo> CellItemIs = parseCellItem(resp);


        //  newZjyHttp.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        for (CellInfo vCellItemI : CellItemIs) {


            String itid = vCellItemI.getItemId();

            System.out.println("----*" + vCellItemI.getName() + " * " + vCellItemI.getWaretype()
                    + " * " + itid);

            CourseSaveLearningTime(CourseId);


            if (vCellItemI.getOver() == 1) {
                System.out.println("已刷跳过");
                continue;
            }

            resp = newZjyApi.getUpdateLearningItem(itid);
            System.out.println(resp);


            if (vCellItemI.getWaretype().contains("video")) {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                resp = newZjyApi.getSaveVideoForBatch(itid, CourseId);
                System.out.println(resp);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                resp = newZjyApi.getSaveLearningItem("999", CourseId, itid);
                System.out.println(resp);

                postlearn(itid, CourseId);
            } else {

                postlearn(itid, CourseId);
                // resp = newZjyApi.getQueryCourseItemInfo(itid);
                //System.out.println(resp);

                //resp = newZjyApi.getSaveCourseItem(CourseId, "999", itid);
                //System.out.println(resp);
            }
        }

    }


    public static void postlearn(String itid, String CourseId) {


        String resp;
        CellItemInfo info = getItemInfo(CourseId);

        //resp = newZjyApi.getQueryVideo(itid);
        //System.out.println(resp);

        //resp = newZjyApi.getQueryCourseItemInfo(itid);
        //System.out.println(resp);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resp = newZjyApi.getSaveCourseItem(CourseId, "9999", itid);
        System.out.println(resp);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resp = newZjyApi.getSaveCourseItem(CourseId, "66", info.getParentId());
        System.out.println(resp);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resp = newZjyApi.getSaveCourseItem(CourseId, "66", info.getSecId());
        System.out.println(resp);


        //CourseSaveLearningTime(CourseId);

    }


    public static void CourseSaveLearningTime(String CourseId) {
        String resp;
        resp = newZjyApi.getQueryLearningTime(CourseId);
        System.out.println(resp);

        resp = newZjyApi.getSaveLearningTime(CourseId, "300");
        System.out.println(resp);
    }


    public static void testAct(newZjyCourse vCourse) {

        List<ClassRoom> ClassRooms = getClassroomByStudent(vCourse);

        for (ClassRoom vClassRoom : ClassRooms) {

            if (!vClassRoom.getTitle().equals("2022-09-04周日的课堂教学2")) continue;

            System.out.println("--" + vClassRoom.getTitle() + " * " + vClassRoom.getId());

            List<classActivity> ClassActivities = getClassActivityZ(vClassRoom);


            for (classActivity vActivity : ClassActivities) {
                String name = vActivity.getTypeName();
                String RecordId = vActivity.getRecordId();
                String id = vActivity.getId();

                if (name.contains("签到")) {
                    System.out.println("---" + id + " * name " + name + " * getStatus " +
                            vActivity.getStatus() + " * RecordId " + RecordId + " * DetailTypeCode " + vActivity.getDetailTypeCode());
                    for (SignAndQuestionStu vSignStudent : getAllSignStudent(id)) {
                        System.out.println("---" + vSignStudent.getStuName() + " * " + vSignStudent.getSignStatus());
                    }
                    //break;
                    //System.out.println(patchSign(RecordId,3));
                    //Sign(vActivity,RecordId);
                    //System.out.println(overActivity(vActivity));

                }
                if (name.contains("提问")) {
                    System.out.println("---" + id + " * name " + name + " * getStatus " +
                            vActivity.getStatus() + " * RecordId " + RecordId + " * DetailTypeCode " + vActivity.getDetailTypeCode());
                    System.out.println(newZjyApi.getUpdateStudentSignStatus("50", RecordId));
                    for (SignAndQuestionStu vSignStudent : getQuestionStuListB(id)) {
                        System.out.println("---" + vSignStudent.getStuName() + " * " + vSignStudent.getSignStatus() + " * " + vSignStudent.getScore());
                    }
                    break;
                }
                if (name.contains("讨论")) {
                    System.out.println("---" + id + " * name " + name + " * getStatus " +
                            vActivity.getStatus() + " * RecordId " + RecordId + " * DetailTypeCode " + vActivity.getDetailTypeCode());
                    //System.out.println(newZjyApi.getUpdateStudentSignStatus("9000",RecordId));

                    break;

                }
            }
        }
    }

    public static void doExamWork(String CourseId){
        List<ExamWork> vExamWorks;
        examAnsw vExamAnsw;
        String resp;
        String PaperId = "";
        ExamWorkInfo vExamWorkInfo;
        Map<String, String> vMap;
        //System.out.println(ret);
        //System.exit(0);
        vExamWorks = getExam_list_data_e(CourseId);

        for (ExamWork vExamWork : vExamWorks) {

            //String ExamId="4028813884ea68b20184f9ec6a39673d";


            String ExamId = vExamWork.getExamId();
            String Exam_num = vExamWork.getExam_num();
            String ExamName = vExamWork.getExamName();
            String ExamStatus = vExamWork.getExamStatus();
            String My_score = vExamWork.getMy_score();

            System.out.println("\n" + "考试  " + ExamId + "  " + Exam_num + "\n"
                    + PaperId + "   " + ExamName + "   " + ExamStatus + "   " + My_score + "\n");

            if (My_score.contains("100"))continue;

            vExamWorkInfo = getExamConfirm(ExamId);
            PaperId = vExamWorkInfo.getPaperId();


            vExamAnsw = getPaperAnsw2(PaperId);
            vMap = vExamAnsw.getAnswMap();


            String examRecordId = getExamFlow_intoExam(ExamId, CourseId);


            resp = CompleteAnsw1(vMap);
            System.out.println(resp);
            //resp=CompleteAnsw1();


            resp = getExamflow_sendManyAnswer(examRecordId, resp, "100000");
            System.out.println(resp);

            resp = getExamflow_getCompleteQuestionSeq(examRecordId, ExamId);
            System.out.println(resp);

            ///resp = getExamflow_complete(examRecordId);
            //System.out.println(resp);






  /*
  ExamWorkQid vExamWorkQid =getExamFlow_getExamPaperInfo(examRecordId);
                vMap=vExamWorkQid.getQuestionIdAndContentIds();
                for (String key : vMap.keySet()) {
                    System.out.println(key + " " + vMap.get(key));
                }*/




            // System.out.println(newZjyApi.getExamPaperStatisticsDetail(ExamId,PaperId));

            //System.out.println(newZjyApi.getPaperStructure(PaperId));

            //System.out.println(newZjyApi.getPaperStructureForPreview(PaperId));
            //break;
        }
    }
}
