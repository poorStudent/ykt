package com.vms.ykt.yktStuMobile.newZJY;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.Util.Tool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class newZjyMain {

    public static newZjyUser MobileLogin(String mobile, String passwd) {
        String resp = newZjyApi.getMobileLogin(mobile, passwd);
        if (resp == null || !resp.contains("token")) return null;
        newZjyUser vUser = JSONObject.parseObject(resp, newZjyUser.class);
        resp = newZjyApi.getUserInfo(vUser.getToken());
        System.out.println(resp);
        if (resp != null && resp.contains("userInfo")) {
            JSONArray vJsonA = Tool.parseJsonA(resp, "userInfo");
            String userid = vJsonA.getString(0);
            System.out.println(userid);
        }
        return vUser;
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

    //UNTYXLCOOKIE
    public static boolean upUNTYXLCOOKIE(newZjyUser user, String courseid) {
        String resp = newZjyApi.getSignLearn(courseid, user.getLoginId());
        if (resp == null || !resp.contains("UNTYXLCOOKIE")) return false;
        JSONArray jsona = Tool.parseJsonA(resp, "Set-Cookie");
        for (int i = 0; i < jsona.size(); i++) {
            String js = jsona.getString(i);
            if (js.contains("UNTYXLCOOKIE")) {
                user.setUNTYXLCOOKIE(js);
                return true;
            }
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

    public static List<QuestionStu> getQuestionStu(classActivity activity) {
        List<QuestionStu> QuestionStus = new ArrayList<>();
        String resp = newZjyApi.getQuestionStuListB(activity.getId());
        if (resp == null || !resp.contains("items")) return QuestionStus;
        String js = Tool.parseJsonO(resp, "data").toJSONString();
        QuestionStus = Tool.parseJsonA(js, "items", QuestionStu.class);
        return QuestionStus;
    }

    //开启活动
    public static String startActivity(classActivity vActivity) {
        return newZjyApi.getUpdateSignStatus(vActivity.getId(), "1");
    }

    //结束活动
    public static String overActivity(classActivity vActivity) {
        return newZjyApi.getUpdateSignStatus(vActivity.getId(), "2");
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

    //签到
    public static int Sign(classActivity Activitys, String RecordId) {
        String resp = newZjyApi.getSignResult(RecordId);
        System.out.println(resp);
        if (resp != null) {
            if (resp.contains("已签")) return 1;
        }
        String detailTypeCode = Activitys.getDetailTypeCode();
        if (detailTypeCode.equals("1")) {
            resp = newZjyApi.getStudentSignStatus(RecordId, Activitys.getId());

        } else {
            resp = newZjyApi.getActivityById(Activitys.getId());
            resp = parseSignCode(resp);
            if (resp == null || resp.equals("")) return 2;
            resp = newZjyApi.getStudentSignStatusC(RecordId, Activitys.getId(), resp);

        }
        System.out.println(resp);
        if (resp == null || !resp.contains("签到成功")) {
            System.out.println("签到sb");
            return 2;
        }
        ;

        return 1;
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

    public static List<SignStudent> getUnSignStudent(String Activityid) {
        String resp = newZjyApi.getUnSignStudent(Activityid);
        return parseSignStudent(resp);
    }

    public static List<SignStudent> getSignStudent(String Activityid) {
        String resp = newZjyApi.getSignStudent(Activityid);
        return parseSignStudent(resp);
    }

    public static List<SignStudent> getAllSignStudent(String Activityid) {
        List<SignStudent> SignStudents = new ArrayList<>();
        SignStudents.addAll(getSignStudent(Activityid));
        SignStudents.addAll(getUnSignStudent(Activityid));
        return SignStudents;
    }

    public static List<SignStudent> parseSignStudent(String resp) {
        List<SignStudent> SignStudents = new ArrayList<>();
        if (resp == null || !resp.contains("items")) return SignStudents;
        String js1 = Tool.parseJsonS(resp, "data");
        SignStudents = Tool.parseJsonA(js1, "items", SignStudent.class);
        return SignStudents;
    }

    //提问列表
    public static List<SignStudent> getQuestionStuListB(String Activityid) {
        String resp = newZjyApi.getQuestionStuListB(Activityid);
        return parseSignStudent(resp);
    }

    //讨论列表
    public static List<SignStudent> getPrStuActivityRecord(String Activityid) {
        String resp = newZjyApi.getPrStuActivityRecord(Activityid);
        return parseSignStudent(resp);
    }


    public static List<CellItemI> parseCellItem(String resp) {
        List<CellItemI> CellItemIList = new ArrayList<>();
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

    private static CellItemI paresCell(Element uls, String pid) {
        String waretype = uls.attr("waretype");
        String etype = uls.attr("etype");
        String ItemID = uls.id().replace("grandChildItem_", "");
        String videotime = uls.attr("videotime");
        String isok = uls.attr("class");

        CellItemI vCellItemI = new CellItemI();
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

    public static void doMain() {
        //aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA==
        //9b933b5e625e459ba9df0ea29e9e50ed
        //MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0Q=
        //22befd2145494c4cb15b772c0d66ab07

        //String token = "22befd2145494c4cb15b772c0d66ab07";
        String token = "7d4322bbfaee4cef83fd76cd96e27131";
        //xnzy2113418
        //20030517lei@
        //newZjyUser vUser =MobileLogin("venomms","Poor2579988653");
        //newZjyUser vUser =MobileLogin("xnzy2113418","20030517lei@");
        //ljq1ab-oz7hbmaardmy-2q
        //402883e682d05a190182d3edda901971
        //7d4322bbfaee4cef83fd76cd96e27131
        String uid="ljq1ab-oz7hbmaardmy-2q";
        newZjyUser vUser = new newZjyUser();
        vUser.setToken(token);
        vUser.setLoginId("xnzy2113418");

        System.out.println(vUser.getToken());
        System.out.println(vUser.getLoginId());

        //测试api
        // System.out.println(testApi.getSaveClassroom());
        System.out.println(newZjyApi.getPaperStructureForPreview());

        System.exit(0);

        String CheckUser = newZjyApi.getCheckUser(vUser.getToken());
        if (!CheckUser.contains("200")) {
            System.out.println("登陆失效");
            return;
        }

        //newZjyTestApi.getAuth();


        List<newZjyCourse> CoursesList = getMyClassList(vUser);
        for (newZjyCourse vCourse : CoursesList) {
            String ClassId = vCourse.getClassId();
            String CourseId = vCourse.getCourseId();
            String  CourseName=vCourse.getCourseName();
            System.out.println(vCourse.getClassName() + " * " + ClassId + " * " +
                    CourseName + " * " + CourseId);

            //System.out.println(newZjyApi.getModifyClassAuditStatus(ClassId,"1","0"));
            //System.out.println(newZjyTestApi.getSaveAssessment(CourseId,ClassId));
            //System.out.println(newZjyTestApi.getCreateStuAssess(CourseId,ClassId));


            if (!CourseName.contains("三维软件基础"))continue;

            if (!upUNTYXLCOOKIE(vUser, CourseId)) {
                System.out.println("获取失效");
                return;
            }

            newZjyApi.addCookie(vUser.getUNTYXLCOOKIE());

          //  System.out.println(newZjyApi.getStuLearnRecord(CourseId,uid));
            System.out.println(newZjyApi.getLearnRecord(CourseId));
           // sk(vUser, CourseId);


           // String resp;
            //grandChildItem_dd9433f5384f40ab9e77e9f08436ed19
            //grandChildItem_2ac3ca7f6bd7477b9ac43e84d6fa86dc
            //String itid = "dd9433f5384f40ab9e77e9f08436ed19";


            // System.exit(0);


            //
            if (!upAuthorization(vUser)) {
                System.out.println("upAuthorization erro");
               // break;
                //
            }
            newZjyApi.spocHeader();

          //  break;
        }


    }

    private static void sk(newZjyUser vUser, String CourseId) {


        String resp;

        resp = newZjyApi.getLearnspace(CourseId);//

        List<CellItemI> CellItemIs = parseCellItem(resp);


        //  newZjyHttp.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        for (CellItemI vCellItemI : CellItemIs) {


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

                postlearn(itid,CourseId);
            } else {

                postlearn(itid,CourseId);
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
                    for (SignStudent vSignStudent : getAllSignStudent(id)) {
                        System.out.println("---" + vSignStudent.getStuName() + " * " + vSignStudent.getSignStatus());
                    }
                    break;
                    //System.out.println(patchSign(RecordId,3));
                    //Sign(vActivity,RecordId);
                    //System.out.println(overActivity(vActivity));

                }
                if (name.contains("提问")) {
                    System.out.println("---" + id + " * name " + name + " * getStatus " +
                            vActivity.getStatus() + " * RecordId " + RecordId + " * DetailTypeCode " + vActivity.getDetailTypeCode());
                    System.out.println(newZjyApi.getUpdateStudentSignStatus("50", RecordId));
                    for (SignStudent vSignStudent : getQuestionStuListB(id)) {
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
}
