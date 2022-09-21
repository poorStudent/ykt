package com.vms.ykt.RuiKey.Model;

public class BusinessType {
    /**软件初始化*/
    public static final int iniSoftInfo = 1;

    /**账号注册*/
    public static final int accountRegister = 2;

    /**账号登录*/
    public static final int accountLogin = 3;

    /**卡密登录*/
    public static final int cardLogin = 4;

    /**心跳*/
    public static final int heartBeat = 5;

    /**扣点*/
    public static final int bucklePoint = 6;

    /**退出登录*/
    public static final int loginOut = 7;

    /**获取软件变量*/
    public static final int  getremoteVar = 8;

    /**解绑机器码*/
    public static final int  unbundMac = 9;

    /**修改用户密码*/
    public static final int  updPwd = 10;

    /**生成卡密*/
    public static final int  createCard = 11;

    /**开通/续费卡密*/
    public static final int  openRenewCardNum = 12;

    /**开通/续费账号*/
    public static final int  openRenewAccount = 13;

    /**卡密详情*/
    public static final int  cardDetail = 14;

    /**账号详情*/
    public static final int  accountDetail = 15;

    /**获取软件价格列表*/
    public static final int  softPriceList = 16;


    /**在线支付-购买商品*/
    public static final int  buyGoods = 1001;

    /**
     * 修改订单标识,此订单是否发货的一个标记
     * 【注意：此业务只针对自动发货平台订单的修改】
     * */
    public static final int  updOrderFlag = 1002;

    /**获取商品价格*/
    public static final int  goodsPrice = 1003;
}

