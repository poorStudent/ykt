package com.vms.ykt.yktStuMobile.newZJY;

public class testApi {
    static String saveClassroom="https://user.icve.com.cn/t/m/zhzjPeMobileCourse_saveClassroom.action";
    public static String getSaveClassroom(){
        String data="classId=957639a938cc4e63b0953e132e0df096&\n" +
                "courseId=39e272199dab487ba6f8f76115cbfd2c&\n" +
                "title=2022-09-04周日的课堂教学&\n" +
                "startDate=2022-09-04&\n" +
                "token=22befd2145494c4cb15b772c0d66ab07";
        String resp=newZjyHttp.post(saveClassroom, data);
        return  resp;
    }



}
