package com.vms.ykt.RuiKey.Model.In;
/*心跳入参*/
public class In_HeartBeatArgs  extends In_DataBaseArgs {
    /*登录令牌(必填)*/
    public String token= "";
    /*心跳Key,每次心跳此值都会变(必填)*/
    public String heartbeatkey= "";
    /*卡密或账号(必填)*/
    public String cardnumorusername= "";
}
