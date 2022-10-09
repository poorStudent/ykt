package Model.Out.Login;

import java.util.ArrayList;

import Model.Out.Out_DataBaseArgs;


/*登录出参*/
public class Out_Login extends Out_DataBaseArgs {
    /*登录成功后的令牌*/
    public String token="";

    /*心跳Key,每次心跳此值都会变*/
    public String heartbeatkey="";

    /*账号或卡密到期时间*/
    public String endtime="";

    /*账号或卡密剩余点数*/
    public int surpluspointvalue;

    /*此卡密或账号已登录的数量*/
    public int currentloginnum;


    /*开通的价格类型ID*/
    public String priceid = "";

    /*开通的价格类型名称*/
    public String pricename= "";


    /*是否已付款  0:未付款  1:已付款*/
    public int ispay;

    /*角色ID*/
    public String roleid="";

    /*角色名称*/
    public String rolename="";

    /*同一卡密或账号最大登录数量*/
    public int maxloginnum;

    /*角色下的权限列表*/
    public ArrayList<Out_LoginJurisdiction> jurisdictionlist;


    /*账号用户的qq*/
    public String qq="";

    /*账号用户的微信*/
    public String wx="";

    /*账号用户的支付宝*/
    public String alipay="";

    /*账号用户的手机号*/
    public String tel="";

    /*账号用户的邮箱*/
    public String email="";
}
