package com.vms.ykt.RuiKey.Model.Out.SearchOrder;

import com.vms.ykt.RuiKey.Model.Out.Out_DataBaseArgs;

public class Out_SearchOrder extends Out_DataBaseArgs {
    /**
     *订单状态 0:等待支付  1:支付成功 2:支付未完成 3:支付失败
     * 注意：支付成功，说明终端用户已成功付完款了】
    */
    public int orderstate;

    /**
     *订单业务完成状态 0:等待完成 1:成功  2:失败
     *【注意：如果订单状态是支付成功，但业务完成状态非成功，需要具体到后台找到此订单查看未成功的原因】
    */
    public int businesscompletetype;

    /**
     *支付类型 0:微信 1：支付宝
    */
    public int paytypeid;

    /**售价*/
    public double price;


    /**实收金额*/
    public double revicemoney;


    /**
     *卡密
     *【注意：如果此订单号属于“网络验证平台”产生的，并且是购买卡密，那么支付状态和订单业务完成状态都成功后，会把购买的卡密返回来】
     * */
    public String cardnum;

    /**
     * 商品下载的网盘地址
     * 【如果此订单号属于“自动发货平台”产生的，那么支付状态和订单业务完成状态都成功后，会把商品的下载地址和提取码返回来】
     * */
    public String goodsnetworkdiskurl;

    /**
     *网盘提取码
    */
    public String diskpwd;


    /**
     *机器码【如果此订单号属于“自动发货平台”产生的，那么此订单在哪台设备上生成的机器码会返回来】
    */
    public String maccode;

    /**
     *订单标识，此订单是否发货的一个标记
     *【如果此订单号属于“自动发货平台”产生的，那么此订单标识会返回来】
    */
    public String orderflag;

    /**
     * 订单创建的时间
    */
    public String createtime;
}
