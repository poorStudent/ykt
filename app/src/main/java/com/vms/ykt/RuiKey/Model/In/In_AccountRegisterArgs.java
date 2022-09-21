package com.vms.ykt.RuiKey.Model.In;

/*账号注册入参*/
public class In_AccountRegisterArgs extends In_DataBaseArgs {
    /*账号(必填)*/
    public String username="";
    /*密码(必填)*/
    public String userpwd="";

    /*qq(可选)*/
    public String qq="";
    /*wx(可选)*/
    public String wx="";
    /*支付宝(可选)*/
    public String alipay="";
    /*电话(可选)*/
    public String tel="";
    /*邮箱(可选)*/
    public String email="";
}
