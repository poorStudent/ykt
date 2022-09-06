package com.vms.ykt.yktStuWeb.newZJY;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.circularreveal.CircularRevealHelper;
import com.vms.ykt.Util.httpRespnose;
import com.vms.ykt.Util.httpTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class newZjyTestApi {

    public static void  printHeader(){
        System.out.println(JSONObject.toJSONString(newZjyHttp.getHeader()));
    }
    public static void  printHeader(Map<String, List<String>>map){
        System.out.println(JSONObject.toJSONString(map));
    }

    public static String auth="https://spoc-sso.icve.com.cn/auth";
    public static String getAuth(){
        String resp="";
        String data = "siteCode=zhzj&errorPage=https%3A%2F%2Fuser.icve.com.cn%2Fsso%2FssoLogin_loginError.action%3FbackURL%3D%2Findex.jsp&password=af30447f10d38ce427778f5fc1fc31ea&username=Debug";

        printHeader();

        httpRespnose ret = newZjyHttp.post(auth, data,null,null);

       // printHeader(ret.getHearderFileds());

        String rq=ret.getLocation();
        //String url=ret.getLocation().split("\\?")[0];
        //data=ret.getLocation().split("\\?")[1];
        System.out.println(rq);
        String ck = ret.getSetCookie();
        //String ck="JSESSIONID=E88A1B54077C1C2DE34E7DE3ABCC2922.whatysns-41; misdaxquxb5pt3hsoubywpageSize=10; user.icve.com.cn=bgSkErgQEQ; _abfpc=9b6cd6e4c38694deae327b088006c0027cfb49a6_2.0; cna=b34aadbed4ecc0ab4ae724f1f68cbd54; webtrn=49138aaebfd538722b755f1150358144; webtrn_cms=5c12c26f8051b7cdfcb32f28a5e84a5f; whatysns=a18c8128993a7442bb3356db912acdbf; _uid=85032bd4-06e8-435e-acf0-9993ef544d4d; token=s7vnaaovx6zbfy5y7veema; ssxmod_itna=Qq0xcDRDn7G=KYK0=DXWi3jciw9irSgoq4DIQD/GQADnqD=GFDK40o3OP4WeTeqIYYoa0xOiBdaq4=FxrhT5DRopoLFADB3DEx06xYR4YYkDt4DTD34DYDixibzxi5GRD0KDF7dy/1yDYPDE0KDYxDrjOKDRxi7DD5QCx07DQ5kC74k5Rc+c=gCqCYei0KD9aoDsOif8/Afj8Rz533E4I+ODlFUDC91c2vHT4GdXc5z4b24EjDPZiDPKjRDii0W37+53AQeTx+q/CeeqB0KbCR97GRSDDco7YKe4D===; ssxmod_itna2=Qq0xcDRDn7G=KYK0=DXWi3jciw9irSgoq4DKG9i5KQDBu0CD7PYGCjv6hYCXIaWue4HbIO89D+YttqxeHiIxAPEiY3686xruz3o2CToQLrWRauE2pBXiG/I8loUA=cYNHY5HMczIL65=n=aiYoxrTcGTc7FhcWTN8OPW8heeXlYGOzPwyBbaKPn8T6ibZx3IDaweEKn+mRuQBc8EdbFzTa0QKnYA3AIat8Grfad0uFw=GShvt9lpdHPTHSY8FE0XR23uW=YvWx76Ox7NV26Flr8Xa1O6HNfwsaaXe8nn/fACDSB+BT7iUT/4YjfPT3PSYH/RRGQrp7B4yKhlb4qHbBRzmbq8aKTb9SYInu9DhYvnKCxc3TLm4bYEalcdsu3k44+hdYqT7rWGPyiDOCvNFWcC2Kk41ehrkpe/oyzTtKfei4ikDdZcND34z8Np3Q=WTTo1+qiMniV4iOS8DkLbEtL31DEqTprsKaKa6eTlDPMeHynPSl5srBwYp13HogHo6RBKgDixDKkDbD7rxoeBolfpbzO22RboKuRKD7=DYIoeD===; acw_tc=0b32807e16624279550435037e014114e10b2be31cc5ed4c8e28b12eaa58bb; JSESSIONID=BC78E31EA4EBD17537EAFE303CC4288E.webtrn-102; SERVERID=48585c58510becd3419d162580b2075a|1662428001|1662003163";
        newZjyHttp.setUserCookie(ck);
        ret = newZjyHttp.getT(rq,null,null);

        //printHeader();

        System.out.println(ret.getResp());

        printHeader(ret.getHearderFileds());
       // httpTool.toLocationJ(newZjyHttp.getHeader() ,url,ret.getHearderFileds().get("Set-Cookie"), data, 1);
        // resp =resp+"\n"+ret.getCode()+"\n";
        // resp=resp+JSONObject.toJSONString(ret.getmHearderFileds( ));
        return resp;
    }

    static String PersonalInfo = "https://user.icve.com.cn/zhzj/zhzjTeacher_getPersonalInfo.action";

    public static String getPersonalInfo() {
        String ck = "misdaxquxb5pt3hsoubywpageSize=10; user.icve.com.cn=bgSkErgQEQ; _abfpc=9b6cd6e4c38694deae327b088006c0027cfb49a6_2.0; cna=b34aadbed4ecc0ab4ae724f1f68cbd54; webtrn=49138aaebfd538722b755f1150358144; webtrn_cms=5c12c26f8051b7cdfcb32f28a5e84a5f; whatysns=a18c8128993a7442bb3356db912acdbf; _uid=85032bd4-06e8-435e-acf0-9993ef544d4d; token=s7vnaaovx6zbfy5y7veema; ssxmod_itna=eqmxgDymD=0QeDKGHLhCDjx5h7i=DBDbQD3POCx0yGveGzDAxn40iDto=XDtPrBx4Yef49B4hYf0D4qLxdYSxwxaiW1nPGLDmKDyzir4GGUxBYDQxAYDGDDPDogPD1D3qDkD7h6CMy1qGWDm4kDGeDe2IODY5DhxDC20PDwx0Cj6xej1YHdFymuc0hqQDkD7HpDlp5EfgwfR3eM1AAm3A3Ix0kg40Oya5sz=oDUlFsBoBodA4xdYhNIBxd=W+xdDrq7BQoKn04TP2rqG0imwxeo1hSTDxDi=DeFG2P4D; ssxmod_itna2=eqmxgDymD=0QeDKGHLhCDjx5h7i=DBDbQD3PODnF3rw5DsG=DLiCQHx4Z4n4=njxYSZdBisuDIh7bwZYnCoNA32nkinwW1t=MuAFMfWLH2xqXaYu2Q2fKOcXUrHIIMw7OkF3=AXuua8BaKGdxei7DRdPZ2dqWm8rQTHr4Rm9OPxFRqKvZR4D2q4tonm/=mxzgx7dSaAUxR4XmIan0u4tOL1qhdvzUfAVtgfpBBmyZRLzzBQGQeFlGp=YG2DMnDKD1DUzQexDMxU6B7vy+aqR4a0mml2dNufdiiN+wQNE+1i3RwpteeQrqYdYaPqop0TrkK1b=8M1uOFUuqMD7en3w38YkTNRoWnTpkhNLo8D+Dx1mnokEx=07YuNg+aw4OeuTE+=13tBbo33vz+FHEoU7DZ7a2o7ol=Z8HQ2GhoEx8bBS=HQIFx75W=qYEv8FkbqzfxkROSkrh=fc47YiEyG5z7hFgG/0b7aH+wDEDDwoeeAYL=wKDx6EbwjH51tDHqxkGa9HtWwkQGUOG4Hwac+A9+yf6jfXtgxqDwxY6=H+tx4QDGcDG7iiDD=; JSESSIONID=FE2ABC17A385129197BBEDBC0961B0B7.webtrn-102; acw_tc=0bde430e16621716829357932e010281b08dbbd260adea6322cdc9a4cdafbf; SERVERID=48585c58510becd3419d162580b2075a|1662172248|1662003163";
        //newZjyHttp.setUserCookie(ck);
        //aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA==
        //MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0Q=
        String data = "loginId=Debug&token=aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA==";
        String resp = newZjyHttp.post(PersonalInfo, data);
        return resp;
    }
    static String getActivityStudentCount= "https://spoc-classroom.icve.com.cn/classroom-teaching-api/sign/teacher/getActivityStudentCount";
    public static String getActivityStudentCount(){
       HashMap<String,Object> header=new HashMap<>();
        String Authorization="bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpbklkIjoiRGVidWciLCJzaXRlQ29kZSI6InpoemoiLCJ1c2VyX25hbWUiOiJEZWJ1ZyIsInBob3RvIjpudWxsLCJzdUlkIjoiYTYwMzkwNTQ3MGUyYTViOGMxM2U5NmI1NzllZjBkYmEiLCJhdXRob3JpdGllcyI6WyJURUFDSEVSIl0sImNsaWVudF9pZCI6InNwb2MtY2xhc3Nyb29tLmljdmUuY29tLmNuIiwidHJ1ZU5hbWUiOiLlkLTkuqYiLCJyb2xlQ29kZSI6IjEiLCJzY29wZSI6WyJhbGwiXSwicm9sZU5hbWUiOiLmlZnluIgiLCJleHAiOjE2NjM0MDA1NjQsImp0aSI6IjM4MzYzZDZlLTM0N2QtNGNhMi04ZTIzLTlhMDE3OTM4ODFjOSJ9.HuFUeOXU-QAnn0ap7Cx-NSxu75IjUNOOhg_Hcqc3Ub7F77TZ4BKdADdrVib7KUWGCr6ZzrFqhK0ut60xGqKEoFJrAE-Lh2bcRJVuL2qeUzDISAN321LnTkyKdeAFdJ61yT8Kp0ruErf5kaB0TBcIbWIerFWe1C9oUdwHpQas6ybR4JrVHh5BrcgXsHaCEbhsFyb-TT_jhMn4qgEfFpLfgGrM7O0ENLeQ4SnZBV-fYJTVY4UU9RtVfZ6Nt3rCKeE0z73N527YJS8WONxLL9V7LuoaY4kz1nCn0ha4FRLoy-BisqV5sLA2l4KUi-MRhL7iNp8lVONlKl2VsB4bwZi0IQ";
        header.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        header.put("Host","spoc-classroom.icve.com.cn");
       // header.put("Origin","https://spoc-classroom.icve.com.cn");
        header.put("Authorization",Authorization);
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.5112.102 Safari/537.36 Edg/104.0.1293.70");
        newZjyHttp.updataHeader(header);
        String ck = "JSESSIONID=16178FBA45AF39990A7F62E8BD24DE00; misdaxquxb5pt3hsoubywpageSize=10; _abfpc=9b6cd6e4c38694deae327b088006c0027cfb49a6_2.0; cna=b34aadbed4ecc0ab4ae724f1f68cbd54; token=s7vnaaovx6zbfy5y7veema; ssxmod_itna=eqmxgDymD=0QeDKGHLhCDjx5h7i=DBDbQD3POCx0yGveGzDAxn40iDto=XDtPrBx4Yef49B4hYf0D4qLxdYSxwxaiW1nPGLDmKDyzir4GGUxBYDQxAYDGDDPDogPD1D3qDkD7h6CMy1qGWDm4kDGeDe2IODY5DhxDC20PDwx0Cj6xej1YHdFymuc0hqQDkD7HpDlp5EfgwfR3eM1AAm3A3Ix0kg40Oya5sz=oDUlFsBoBodA4xdYhNIBxd=W+xdDrq7BQoKn04TP2rqG0imwxeo1hSTDxDi=DeFG2P4D; ssxmod_itna2=eqmxgDymD=0QeDKGHLhCDjx5h7i=DBDbQD3PODnF3rw5DsG=DLiCQHx4Z4n4=njxYSZdBisuDIh7bwZYnCoNA32nkinwW1t=MuAFMfWLH2xqXaYu2Q2fKOcXUrHIIMw7OkF3=AXuua8BaKGdxei7DRdPZ2dqWm8rQTHr4Rm9OPxFRqKvZR4D2q4tonm/=mxzgx7dSaAUxR4XmIan0u4tOL1qhdvzUfAVtgfpBBmyZRLzzBQGQeFlGp=YG2DMnDKD1DUzQexDMxU6B7vy+aqR4a0mml2dNufdiiN+wQNE+1i3RwpteeQrqYdYaPqop0TrkK1b=8M1uOFUuqMD7en3w38YkTNRoWnTpkhNLo8D+Dx1mnokEx=07YuNg+aw4OeuTE+=13tBbo33vz+FHEoU7DZ7a2o7ol=Z8HQ2GhoEx8bBS=HQIFx75W=qYEv8FkbqzfxkROSkrh=fc47YiEyG5z7hFgG/0b7aH+wDEDDwoeeAYL=wKDx6EbwjH51tDHqxkGa9HtWwkQGUOG4Hwac+A9+yf6jfXtgxqDwxY6=H+tx4QDGcDG7iiDD=; acw_tc=0a5cc91a16621900573257943e0178e2a4bdf6ef85b2893f1a7da093f80bd6; SERVERID=ca3b10f72b71088690a228c06badbb39|1662191317|1662190057";
        newZjyHttp.setUserCookie(ck);
        String data = "{\"params\":{\"id\":\"0f4bec3d29ac11ed8cb21c34da7acf94\"}}";
        String resp = newZjyHttp.post(getActivityStudentCount, data);
        return resp;
    }
    //可用
    static String StudentAssessList= "https://user.icve.com.cn/patch/zhzj/teacherStatistics_getStudentAssessList.action";
    public static String getStudentAssessList(){
        String data = "date=2022-08-3+~+2022-9-3&keyName=&curPage=1&pageSize=10&" +
                "classId=957639a938cc4e63b0953e132e0df096&token=MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0=";
        String resp = newZjyHttp.post(StudentAssessList, data);
        return resp;
    }

    static String StudentStatisticsList="https://user.icve.com.cn/patch/zhzj/teacherStatistics_getStudentStatisticsList.action";
    public static String getStudentStatisticsList(){
        String data = "data=info&date=2022-08-6+~+2022-9-6&keyName=&curPage=1&pageSize=10&" +
                "classId=957639a938cc4e63b0953e132e0df096&token=aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA%3D%3D";
        String resp = newZjyHttp.post(StudentStatisticsList, data);
        return resp;
    }

    static String saveAssessment="https://user.icve.com.cn/zhzj/zhzjTeacher_saveAssessment.action";
    public static String getSaveAssessment(String courseId,String classId){
        String data ="token=aUVxM3RvYWo1N1FTRHVMMkNGRDB4USUzRCUzRA==&courseId="+courseId+"&classId="+classId+"&total_learn_time_rate=10&total_learn_time_full=100&node_rate=10&node_rate_full=100&effective_time_rate=10&effective_time_full=100&test_rate=10&test_rate_full=100&work_rate=10&work_rate_full=100&pop_quiz_rate=10&pop_quiz_full=100&exam_score_rate=10&exam_score=100&questions_rate=10&questions_rate_full=100&sign_num_rate=20&sign_num_full=100";
        String resp = newZjyHttp.post(saveAssessment, data);
        return resp;
    }

    static String createStuAssess="https://user.icve.com.cn/zhzj/zhzjTeacher_createStuAssess.action";
    public static String getCreateStuAssess(String courseId,String classId){
        //
        String data ="token=MWNFWmtHblhqJTJCbGI4M1UlMkJMN0p1T2clM0QlM0Q=&classId="+classId+"&courseId="+courseId;
        String resp = newZjyHttp.post(createStuAssess, data);
        return resp;
    }
//可用
    static String modifyClassAuditStatus="https://user.icve.com.cn/m/zhzjMobile_modifyClassAuditStatus.action";
    public static String getModifyClassAuditStatus(){
        String data ="classId=957639a938cc4e63b0953e132e0df096&statusCode=1&statusCodeCheck=1";
        String resp = newZjyHttp.post(modifyClassAuditStatus, data);
        return resp;
    }

    static String index= "https://user.icve.com.cn/learning/u/student/teaching/index.action";
    public static String getIndex(){
        String ck ="JSESSIONID=5F3452486C1266D4D50E7F88A9B76F1A.whatysns-41; whatysns=a18c8128993a7442bb3356db912acdbf";
        newZjyHttp.setUserCookie(ck);
        String resp=newZjyHttp.get(index);
        System.out.println(resp);
        return resp;
    }

    static String webTrnLogin= "https://user.icve.com.cn/learning/sso/login_webTrnLogin.action";
    public static String getWebTrnLogin(){
        String ck ="JSESSIONID=5F3452486C1266D4D50E7F88A9B76F1A.whatysns-41; misdaxquxb5pt3hsoubywpageSize=10; user.icve.com.cn=bgSkErgQEQ; _abfpc=9b6cd6e4c38694deae327b088006c0027cfb49a6_2.0; cna=b34aadbed4ecc0ab4ae724f1f68cbd54; webtrn=49138aaebfd538722b755f1150358144; webtrn_cms=5c12c26f8051b7cdfcb32f28a5e84a5f; whatysns=a18c8128993a7442bb3356db912acdbf; _uid=85032bd4-06e8-435e-acf0-9993ef544d4d; token=s7vnaaovx6zbfy5y7veema; ssxmod_itna=Qq0xcDRDn7G=KYK0=DXWi3jciw9irSgoq4DIQD/GQADnqD=GFDK40o3OP4WeTeqIYYoa0xOiBdaq4=FxrhT5DRopoLFADB3DEx06xYR4YYkDt4DTD34DYDixibzxi5GRD0KDF7dy/1yDYPDE0KDYxDrjOKDRxi7DD5QCx07DQ5kC74k5Rc+c=gCqCYei0KD9aoDsOif8/Afj8Rz533E4I+ODlFUDC91c2vHT4GdXc5z4b24EjDPZiDPKjRDii0W37+53AQeTx+q/CeeqB0KbCR97GRSDDco7YKe4D===; ssxmod_itna2=Qq0xcDRDn7G=KYK0=DXWi3jciw9irSgoq4DKG9i5KQDBu0CD7PYGCjv6hYCXIaWue4HbIO89D+YttqxeHiIxAPEiY3686xruz3o2CToQLrWRauE2pBXiG/I8loUA=cYNHY5HMczIL65=n=aiYoxrTcGTc7FhcWTN8OPW8heeXlYGOzPwyBbaKPn8T6ibZx3IDaweEKn+mRuQBc8EdbFzTa0QKnYA3AIat8Grfad0uFw=GShvt9lpdHPTHSY8FE0XR23uW=YvWx76Ox7NV26Flr8Xa1O6HNfwsaaXe8nn/fACDSB+BT7iUT/4YjfPT3PSYH/RRGQrp7B4yKhlb4qHbBRzmbq8aKTb9SYInu9DhYvnKCxc3TLm4bYEalcdsu3k44+hdYqT7rWGPyiDOCvNFWcC2Kk41ehrkpe/oyzTtKfei4ikDdZcND34z8Np3Q=WTTo1+qiMniV4iOS8DkLbEtL31DEqTprsKaKa6eTlDPMeHynPSl5srBwYp13HogHo6RBKgDixDKkDbD7rxoeBolfpbzO22RboKuRKD7=DYIoeD===; JSESSIONID=C7E5CF424DD702B495995834861E5508.webtrn-102; acw_tc=0b32821a16623877615976057e00fdd55c948aa2cf781fe147952b0607e22e; SERVERID=48585c58510becd3419d162580b2075a|1662387903|1662003163";
        newZjyHttp.setUserCookie(ck);
        String data = "ssoUser.loginId=Debug&ssoUser.roleType=TEACHER&siteCode=zhzj&pwdlv=null&originalPwd=yes";
        String resp=newZjyHttp.get(webTrnLogin,data);
        System.out.println(resp);
        return resp;
    }


}
