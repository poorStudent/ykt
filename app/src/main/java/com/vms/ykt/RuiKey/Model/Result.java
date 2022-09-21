package com.vms.ykt.RuiKey.Model;

/**
 * 接口返回来的数据
 * */
public class Result {
    /**
     * 接口返回来的消息
     * */
    public String msg;
    /*接口返回业务类型的具体数据*/
    public String data;
    /*成功：0  失败：不是0都是失败*/
    public  int code;
//    /*服务器时间戳*/
//    public  Long servertimestamp;
}
