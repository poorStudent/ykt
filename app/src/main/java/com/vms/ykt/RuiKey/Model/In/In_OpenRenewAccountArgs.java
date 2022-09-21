package com.vms.ykt.RuiKey.Model.In;

/**
 * 开通/续费账号入参
 * */
public class In_OpenRenewAccountArgs extends In_DataBaseArgs {
    /**
     *账号(必填)
     * */
    public  String username="";
    /**
     *密码(必填)
     * */
    public  String userpwd="";
    /**
     *价格类型ID(必填)
     * */
    public  String priceid="";
}
