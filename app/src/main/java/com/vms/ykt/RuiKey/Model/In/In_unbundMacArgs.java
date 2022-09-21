package com.vms.ykt.RuiKey.Model.In;

/**
 * 解绑机器码入参
 */
public class In_unbundMacArgs extends In_DataBaseArgs {
    /**
     * 登录令牌(必填)
     */
    public String token="";

    /**
     * 密或账号(必填)
     */
    public String cardnumorusername="";
}