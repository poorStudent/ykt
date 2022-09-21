package com.vms.ykt.RuiKey.Model;

/*请求接口的入参*/
public class ApiRequestArgs {
    /*
     * 业务ID(必填)*/
    public int businessid;

    /*加密方式(必填)*/
    public int encrypttypeid;

    /*
     * 平台用户Code(必填)
     * 说明：个人中心里面可以查看得到。代理商的话，那么在：代理管理--》代理商列表，可以查看得到
     * */
    public String platformusercode;

    /*商品Code(必填)*/
    public String goodscode;

    /*初始化软件Key值(通过软件初始化接口【iniSoftInfo】获取，除了"软件初始化接口"不需要填写，其它接口必填写)**/
    public String inisoftkey;

    /*时间戳(必填)*/
    public long timestamp;


    /*请求的数据类Json字符串(具体参照API文档)*/
    public String data;
    /**
     * 签名
     * 注意：
     * 1.如果软件设置的是“无加密通讯”，那么此值可以为空，如果设置的是“加密通讯”，那么此值必填写
     * 2.签名算法：md5(businessID+encrypttypeid+platformusercode+goodscode+inisoftkey+timestamp+data+signSalt+platformtypeid)
     */
    public String sign;

    /**
     *平台类型(必填) 1:网络验证平台  2:自动发货平台
     * */
    public int platformtypeid;
}
