package com.vms.ykt.RuiKey.Model.In;
/*扣点入参*/
public class In_BucklePointArgs extends In_DataBaseArgs {
    /*登录令牌(必填)*/
    public String token="";

    /*卡密或账号(必填)*/
    public String cardnumorusername="";

    /*扣除点数(如果为0，则扣除软件设置的值)*/
    public int bucklevalue;
}
