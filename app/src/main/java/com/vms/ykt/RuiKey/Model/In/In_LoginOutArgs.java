package com.vms.ykt.RuiKey.Model.In;
/*退出登录*/
public class In_LoginOutArgs extends In_DataBaseArgs {
    /*登录令牌(必填)*/
    public String token= "";
    /*卡密或账号(必填)*/
    public String cardnumorusername= "";
}
