package com.vms.ykt.Ruikey;

import Model.In.In_AccountRegisterArgs;
import Model.In.In_BucklePointArgs;
import Model.In.In_BuyGoodsArgs;
import Model.In.In_CardLoginArgs;
import Model.In.In_CreateCardArgs;
import Model.In.In_HeartBeatArgs;
import Model.In.In_LoginOutArgs;
import Model.In.In_OpenRenewAccountArgs;
import Model.In.In_OpenRenewCardNumArgs;
import Model.In.In_UpdOrderFlagArgs;
import Model.In.In_UpdPwdArgs;
import Model.In.In_accountDetailArgs;
import Model.In.In_cardDetailArgs;
import Model.In.In_getGoodsPriceArgs;
import Model.In.In_getRemoteVarArgs;
import Model.In.In_getSoftPriceListArgs;
import Model.In.In_unbundMacArgs;
import Model.Out.BuyGoods.Out_BuyGoods;
import Model.Out.CreateCard.Out_CreateCard;
import Model.Out.GetRemoteVar.Out_getRemoteVar;
import Model.Out.GoodsPrice.Out_GoodsPrice;
import Model.Out.HeartBeat.Out_heartBeat;
import Model.Out.IniInfo.Out_iniSoftInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.util.JSONObject1O;
import com.vms.ykt.R;
import com.vms.ykt.UI.LoginActivity;
import com.vms.ykt.Util.AppStatus;
import com.vms.ykt.Util.Tool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import Help.NetworkVerHelp;
import Model.BusinessType;
import Model.EncryptType;
import Model.In.In_AccountLoginArgs;
import Model.In.In_IniSoftInfoArgs;
import Model.Out.Login.Out_Login;
import Model.Out.BucklePoint.Out_bucklePoint;
import Model.Out.OpenRenewAccount.Out_OpenRenewAccount;
import Model.Out.OpenRenewCard.Out_OpenRenewCardNum;
import Model.Out.Out_DataBaseArgs;
import Model.Out.SearchOrder.Out_SearchOrder;
import Model.Out.SoftPriceList.Out_SoftPriceList;
import Model.Out.UnbundMac.Out_unbundMacArgs;
import Model.Out.accountDetail.Out_accountDetail;
import Model.Out.cardDetail.Out_cardDetail;
import Model.Result;

public class RuiKey {

    //瑞科网络验证：https://www.rukeyz.com

    /*平台类型  1:网络验证平台  2:自动发货平台*/
    static int platformtypeid = 1;
    /*
     * 加密的key
     * 注意：如果软件设置了DES加密通讯那么此值必填(此参数是在软件列表里面进行设置)
     * */
    static String encryptKey = "16bffdb7";
    /*
     * 签名盐(此参数是在软件列表里面进行设置)
     * 注意：如果软件设置了加密通讯，那么此值必填
     * */
    static String signSalt = "d7fb2cb1";

    /*请求接口时，业务数据data参数加密码通讯类型(此参数是在软件列表里面进行设置)  无加密:0   1：des加密*/
    static int encrypttypeid = 1;

    /*软件编码*/
    static String goodscode = "b99d3683d7aaa59e";
    /*
     * 个人中心里面可以查看得到。代理商的话，那么在：代理管理--》代理商列表，可以查看得到
     * 注意：如果是作者自己就填写自己的platformUserCode,如果是代理商的，得填写代理商的Code
     * */
    static String platformUserCode = "b5d0d950751f971e";


    static String versionname = "v1.0";//当前软件版本号
    public static String maccode = "";//机器码


    //此账号是测试的账号，已过期了。请您在验证平台手动注册一个，或者通过接口注册一个用来测试
    static String userName = "test3";
    static String userpwd = "111111";
    static String newUserPwd = "222222";

    //此卡号是测试的卡号，已过期了。您自己请从“卡密登录->>卡密列表,生成一个卡密，然后拿做测试”
    public static String cardnum = "ykt-255e7090d3e2fa64";
    static String TestVer = "";//获取软件的变量，如果变量名称为空的话，那么接口返回来的是此软件所有变量，否则就是此变量名所以应的变量值

    static String heartbeatkey;//心跳Key,每次心跳此值都会变
    static String token;//登录成功后的令牌

    public static boolean RkInit = false;


    static Handler sHandler = new Handler();

    public static void initRK(Context context) {

            RuiKey.RkInit = RuiKey.IniSoftInfo();
            if (RuiKey.RkInit) {
                sHandler.post(() -> {
                    Tool.toast(context, "初始化失败请重初始化");
                });
            } else {
                sHandler.post(() -> {
                    Tool.toast(context, "初始化ok");
                });
            }
    }

    public static String gxrz(){
        if (NetworkVerHelp.iniSoftInfoData == null) return "null";
        String Msg = "发现新版本：" + NetworkVerHelp.iniSoftInfoData.softInfo.newversionnum + "\n";
        Msg = Msg + "新版本下载地址:" + NetworkVerHelp.iniSoftInfoData.softInfo.networkdiskurl + "\n";
        Msg = Msg + "提取码:" + NetworkVerHelp.iniSoftInfoData.softInfo.diskpwd + "\n";
        return Msg;
    }

    public static void Demo() {

        //maccode = "222222";//NetworkVerHelp.GetMacCode(MainActivity.this.getApplicationContext());
        if (IniSoftInfo()) {

            // //订单查询
            // String orderid = "b016fe8478ea07c81e2";
            // SearchOrder(orderid);

            int businessType = BusinessType.getremoteVar;
            switch (businessType) {
                case BusinessType.softPriceList://获取软件价格列表示例
                    getSoftPriceList();
                    break;
                case BusinessType.goodsPrice://获取商品价格示例
                    getGoodsPrice();
                    break;
                case BusinessType.cardLogin://卡密登录示例
                    LoginByCard(cardnum);
                    break;
                case BusinessType.openRenewCardNum://卡开通/续费卡密示例
                    OpenRenewCardNum();

                    break;
                case BusinessType.cardDetail://卡密详情示例
                    CardDetail();
                    break;
                case BusinessType.accountRegister://账号注册示例
                    AccountRegister(userName, userpwd);
                    break;
                case BusinessType.accountLogin://账号登录示例
                    LoginAccount(userName, userpwd, true);
                    break;
                case BusinessType.openRenewAccount://在线支付开通续费账号示例
                    OpenRenewAccount();
                    break;
                case BusinessType.accountDetail://账号详情示例
                    AccountDetail();
                    break;
                case BusinessType.updPwd://修改用户密码示例
                    updPwd(userName, userpwd, newUserPwd);
                    break;
                case BusinessType.bucklePoint://扣点示例
                    //先登录
                    if (LoginByCard(cardnum)) {
                        //然后再扣点,注意，如果指定的扣点数为0，那么就会默认扣除后台设置的数值
                        BucklePoint(cardnum, 1, token);
                    }
                    break;
                case BusinessType.getremoteVar://获取软件变量示例
                    //先登录
                    if (LoginByCard(cardnum)) {
                        //然后再获取变量(卡密示例),如果变量名为空，那么接口返回来的是此软件所有的变量，否则是此变量名所对应的变量值
                        GetremoteVar(cardnum, token, TestVer);
                    }
                    ////先登录
                    //if (LoginAccount(userName, userpwd, false))
                    //{
                    //	//然后再获取变量(账号示例),如果变量名为空，那么接口返回来的是此软件所有的变量，否则是此变量名所对应的变量值
                    //	GetremoteVar(userName, token);
                    //}
                    break;
                case BusinessType.unbundMac://解绑机器码示例
                    //先登录
                    if (LoginByCard(cardnum)) {
                        //然后解除绑定机器码(卡密示例)
                        unbundMac(cardnum, token);
                    }
                    ////先登录
                    //if (LoginAccount(userName, userpwd, false))
                    //{
                    //	//然后解除绑定机器码(账号示例)
                    //	unbundMac(userName, token);
                    //}
                    break;
                case BusinessType.buyGoods://在线支付购买商品
                    BuyGoods();
                    break;
                case BusinessType.updOrderFlag:
                    String orderid = "";
                    String orderflag = "";
                    UpdOrderFlag(orderid, orderflag);
                    break;
            }
        }

    }

    /**
     * 第一步，先初始化软件信息
     */
    public static boolean IniSoftInfo() {
        boolean IniResult = false;
        //构建初化软件入参
        In_IniSoftInfoArgs IniInfoArgs = new In_IniSoftInfoArgs();
        IniInfoArgs.maccode = maccode;//必填
        IniInfoArgs.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        IniInfoArgs.requestflag = IniInfoArgs.timestamp.toString();//必填
        IniInfoArgs.versionname = versionname;//必填
        String Msg = "";
        //请求接口，获取结果
        Result result = NetworkVerHelp.iniSoftInfo(IniInfoArgs, platformtypeid, encryptKey, signSalt, encrypttypeid, goodscode, platformUserCode);


        if (result.code == 0) {
            Msg = Msg + "初始化软件成功\n";
            //Msg = Msg + "接口返回的数据：\n";
            //Msg = Msg + "编码：" + result.code + "\n";
            //Msg = Msg + "信息：" + result.msg + "\n";
           // Msg = Msg + "data数据：" + result.data + "\n";

            switch (platformtypeid) {
                case 1://网络验证平台
                    //iniSoftInfoData:初始化完后的具体结果
                    if (NetworkVerHelp.iniSoftInfoData != null) {
                        if (NetworkVerHelp.iniSoftInfoData.requestflag.equals(IniInfoArgs.requestflag) == false) {
                            IniResult = false;
                            //Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                        } else {
                            if (NetworkVerHelp.iniSoftInfoData.softInfo.newversionnum != versionname) {
                                Msg = Msg + "发现新版本：" + NetworkVerHelp.iniSoftInfoData.softInfo.newversionnum + "\n";
                                Msg = Msg + "新版本下载地址:" + NetworkVerHelp.iniSoftInfoData.softInfo.networkdiskurl + "\n";
                                Msg = Msg + "提取码:" + NetworkVerHelp.iniSoftInfoData.softInfo.diskpwd + "\n";
                            }
                            IniResult = true;
                            if (encrypttypeid == EncryptType.DES) {
                               // Msg = Msg + "data解密后的数据：" + NetworkVerHelp.DesDecrypt(result.data, encryptKey) + "\n";
                            }
                        }
                    }

                    break;
                case 2://自动发货平台
                    //iniGoodsInfoData:初始化完后的具体结果
                    if (NetworkVerHelp.iniGoodsInfoData != null) {
                        if (NetworkVerHelp.iniGoodsInfoData.requestflag.equals(IniInfoArgs.requestflag) == false) {
                            IniResult = false;
                            //Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                        } else {
                            IniResult = true;
                            if (encrypttypeid == EncryptType.DES) {
                               // Msg = Msg + "data解密后的数据：" + NetworkVerHelp.DesDecrypt(result.data, encryptKey) + "\n";
                            }
                        }
                    }
                    break;
            }


        } else {
            Msg = Msg + "初始化软件失败\n";
            Msg = Msg + "接口返回的数据：\n";
            Msg = Msg + "编码：" + result.code + "\n";
            Msg = Msg + "信息：" + result.msg + "\n";
            Msg = Msg + "data数据：" + result.data + "\n";
            //软件初始化错误信息【软件】
            //1001:未知错误
            //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
            //1007:提交的参数Json不正确
            //1002:系统出错
            //1015:data业务参数不能为空
            //1009:商品Code-goodscode,不存在
            //1032:提交的加密类型参数，与后台设置加密类型不一致
            //1018:data业务参数需要加密
            //1022:签名-sign,不能为空
            //1023:签名-sign,不正确
            //1030:没有此加密类型
            //1031:加密类型不对或加密不对
            //1016:data业务参数加密不正确
            //1019:data业务参数不需要加密
            //1017:data业务参数Json不正确
            //1053:此商品已被禁用
            //1006:data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
            //1003:作者软件或商品销售后台到期
            //1011:平台用户Code-platformUserCode,不存在
            //1010:此软件未分配给代理商
            //2001:版本号-versionname，不能为空


            //软件初始化错误信息【商品】
            //1001:未知错误
            //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
            //1007:提交的参数Json不正确
            //1002:系统出错
            //1015:data业务参数不能为空
            //1009:商品Code-goodscode,不存在
            //1032:提交的加密类型参数，与后台设置加密类型不一致
            //1018:data业务参数需要加密
            //1022:签名-sign,不能为空
            //1023:签名-sign,不正确
            //1030:没有此加密类型
            //1031:加密类型不对或加密不对
            //1016:data业务参数加密不正确
            //1019:data业务参数不需要加密
            //1017:data业务参数Json不正确
            //1053:此商品已被禁用
            //1006:data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
            //1011:平台用户Code-platformUserCode,不存在

        }


        final String finalMsg = Msg;
        System.out.println(finalMsg);
        return IniResult;
    }

    /**
     * 获取软件价格列表
     */
    static void getSoftPriceList() {
        //构建获取软件价格列表入参
        In_getSoftPriceListArgs args = new In_getSoftPriceListArgs();
        args.maccode = maccode;//必填
        args.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        args.requestflag = args.timestamp.toString();//必填

        String Msg = "";
        try {
            //请求接口，获取结果
            Result result = NetworkVerHelp.GetRequestResult(args, BusinessType.softPriceList);//请求接口
            Msg = Msg + "----------获取价格列表操作----------\n";
            if (result.code == 0) {
                //软件价格具体数据
                Out_SoftPriceList out_SoftPriceList = NetworkVerHelp.DecryptData(result.data, Out_SoftPriceList.class);
                if (out_SoftPriceList.requestflag.equals(args.requestflag) == false) {
                    Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                } else {
                    Msg = Msg + "获取价格列表成功\n";
                    Msg = Msg + "接口返回的数据：\n";
                    Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                    Msg = Msg + "信息：" + result.msg + "\n";
                    Msg = Msg + "data数据：" + result.data + "\n";

                    if (encrypttypeid == EncryptType.DES) {
                        Msg = Msg + "data解密后的数据：" + NetworkVerHelp.DesDecrypt(result.data, encryptKey) + "\n";
                    }
                }

            } else {
                Msg = Msg + "获取价格列表失败\n";
                Msg = Msg + "接口返回的数据：\n";
                Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                Msg = Msg + "信息：" + result.msg + "\n";
                Msg = Msg + "data数据：" + result.data + "\n";
                //获取软件价格列表
                //1001:未知错误
                //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
                //1007:提交的参数Json不正确

                //1002:系统出错
                //1015:data业务参数不能为空
                //1009:商品Code-goodscode,不存在
                //1032:提交的加密类型参数，与后台设置加密类型不一致
                //1018:data业务参数需要加密
                //1022:签名-sign,不能为空
                //1023:签名-sign,不正确
                //1030:没有此加密类型
                //1031:加密类型不对或加密不对
                //1016:data业务参数加密不正确
                //1019:data业务参数不需要加密
                //1017:data业务参数Json不正确
                //1053:此商品已被禁用


                //1066:请求标识不能为空
                //1006:data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
                //1011:平台用户Code-platformUserCode,不存在
            }

            Msg = Msg + "--------------------\n\n\n";
        } catch (Exception e) {
            e.printStackTrace();
            Msg = Msg + "注册账号出错：" + e.getMessage() + "\n";
        }

        final String finalMsg = Msg;

    }

    /**
     * 获取商品价格
     */
    static void getGoodsPrice() {
        //构建获取商品价格入参
        In_getGoodsPriceArgs args = new In_getGoodsPriceArgs();
        args.maccode = maccode;//必填
        args.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        args.requestflag = args.timestamp.toString();//必填

        String Msg = "";
        try {
            //请求接口，获取结果
            Result result = NetworkVerHelp.GetRequestResult(args, BusinessType.goodsPrice);//请求接口
            Msg = Msg + "----------获取商品价格操作----------\n";
            if (result.code == 0) {
                //商品价格具体数据
                Out_GoodsPrice out_GoodsPrice = NetworkVerHelp.DecryptData(result.data, Out_GoodsPrice.class);
                if (out_GoodsPrice.requestflag.equals(args.requestflag) == false) {
                    Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                } else {
                    Msg = Msg + "获取商品价格成功\n";
                    Msg = Msg + "接口返回的数据：\n";
                    Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                    Msg = Msg + "信息：" + result.msg + "\n";
                    Msg = Msg + "data数据：" + result.data + "\n";

                    if (encrypttypeid == EncryptType.DES) {
                        Msg = Msg + "data解密后的数据：" + NetworkVerHelp.DesDecrypt(result.data, encryptKey) + "\n";
                    }
                }

            } else {
                Msg = Msg + "获取商品价格失败\n";
                Msg = Msg + "接口返回的数据：\n";
                Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                Msg = Msg + "信息：" + result.msg + "\n";
                Msg = Msg + "data数据：" + result.data + "\n";
                //获取商品价格
                //1001:未知错误
                //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
                //1007:提交的参数Json不正确

                //1002:系统出错
                //1015:data业务参数不能为空
                //1009:商品Code-goodscode,不存在
                //1032:提交的加密类型参数，与后台设置加密类型不一致
                //1018:data业务参数需要加密
                //1022:签名-sign,不能为空
                //1023:签名-sign,不正确
                //1030:没有此加密类型
                //1031:加密类型不对或加密不对
                //1016:data业务参数加密不正确
                //1019:data业务参数不需要加密
                //1017:data业务参数Json不正确
                //1053:此商品已被禁用

                //1066:请求标识不能为空
                //1006:data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
                //1011:平台用户Code-platformUserCode,不存在
            }

            Msg = Msg + "--------------------\n\n\n";
        } catch (Exception e) {
            e.printStackTrace();
            Msg = Msg + "注册账号出错：" + e.getMessage() + "\n";
        }

        final String finalMsg = Msg;

    }


    /**
     * 订单查询示例
     */
    static void SearchOrder(String orderID) {
        //注意：至少30秒请求一次查询
        String Msg = "";
        if (NetworkVerHelp.iniGoodsInfoData == null) {
            final String finalMsg1 = "软件尚未初始化";

            return;
        }

        try {
            String requestflag = NetworkVerHelp.GetTimeStamp().toString();//必填
            Result result = NetworkVerHelp.SearchOrder(orderID, requestflag);
            Msg = Msg + "----------查询订单操作----------\n";
            if (result.code == 0) {
                Out_SearchOrder SearchOrderInfo = NetworkVerHelp.DecryptData(result.data, Out_SearchOrder.class);
                if (SearchOrderInfo != null) {
                    if (SearchOrderInfo.requestflag.equals(requestflag) == false) {
                        Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\r\n";
                    } else {
                        Msg = Msg + "订单状态：" + SearchOrderInfo.orderstate + "\n";
                        Msg = Msg + "订单业务完成状态:" + SearchOrderInfo.businesscompletetype + "\n";
                        Msg = Msg + "支付类型:" + SearchOrderInfo.paytypeid + "\n";
                        Msg = Msg + "售价:" + SearchOrderInfo.price + "\n";
                        Msg = Msg + "实收金额:" + SearchOrderInfo.revicemoney + "\n";
                        Msg = Msg + "卡密:" + SearchOrderInfo.cardnum + "\n";
                        Msg = Msg + "商品下载的网盘地址:" + SearchOrderInfo.goodsnetworkdiskurl + "\n";
                        Msg = Msg + "网盘提取码:" + SearchOrderInfo.diskpwd + "\n";
                        Msg = Msg + "机器码:" + SearchOrderInfo.maccode + "\n";
                        Msg = Msg + "订单标记:" + SearchOrderInfo.orderflag + "\n";
                        Msg = Msg + "接口返回的数据：\n";
                        Msg = Msg + "编码：" + result.code + "\n";
                        Msg = Msg + "信息：" + result.msg + "\n";
                        Msg = Msg + "data数据：" + result.data + "\n";
                        if (encrypttypeid == EncryptType.DES) {
                            Msg = Msg + "data解密后的数据：" + NetworkVerHelp.DesDecrypt(result.data, encryptKey) + "\n";
                        }
                    }

                }

            } else {
                Msg = Msg + "查询订单失败\n";
                Msg = Msg + "接口返回的数据：\n";
                Msg = Msg + "编码：" + result.code + "\n";
                Msg = Msg + "信息：" + result.msg + "\n";
                Msg = Msg + "data数据：" + result.data + "\n";

                //查询订单错误信息：
                //1001:未知错误
                //1002:系统出错
                //1009:商品Code-goodscode,不存在
                //1011:平台用户Code-platformUserCode,不存在

                //1054:平台类型 - platformtypeid,错误
                //1056:订单号不能为空
                //1057:此订单号不存在
                //1058:查询订单速度过快
            }

            Msg = Msg + "--------------------\r\n\r\n\r\n";
        } catch (Exception ex) {
            Msg = Msg + "查询订单操作出错：" + ex.getMessage() + "\r\n";
        }

        final String finalMsg = Msg;


    }


    /**
     * 心跳示例
     */
    static String HeartBeat(String cardnumorusername) throws Exception {
        //注意：心跳是放在死循环里面，并且每隔5分钟请求一次心跳接口。此只是做演示，所以没有做死循环了
        String Msg = "";
        Msg = Msg + "开始心跳：\n";
        //构建心跳入参
        In_HeartBeatArgs HeartBeatArgs = new In_HeartBeatArgs();
        HeartBeatArgs.maccode = maccode;//必填
        HeartBeatArgs.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        HeartBeatArgs.requestflag = HeartBeatArgs.timestamp.toString();//必填
        HeartBeatArgs.cardnumorusername = cardnumorusername;//必填
        HeartBeatArgs.token = token;//必填
        HeartBeatArgs.heartbeatkey = heartbeatkey;//必填(每次把最新的心跳Key赋值)
        //请求接口，获取结果
        Result result = NetworkVerHelp.GetRequestResult(HeartBeatArgs, BusinessType.heartBeat);//请求接口
        Msg = Msg + "心跳返回结果：\n";
        Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
        Msg = Msg + "信息：" + result.msg + "\n";
        if (result.code == 0) {
            //心跳具体数据
            Out_heartBeat heartBeatResult = NetworkVerHelp.DecryptData(result.data, Out_heartBeat.class);
            //这一块相当重要：一定要判断这个“心跳Key(heartbeatkey)”和"请求标识(requestflag)"，防止别人修改你请求的地址，给你返回一个假消息回来
            if (heartBeatResult.heartbeatkey.equals("") == false && heartBeatResult.requestflag.equals(HeartBeatArgs.requestflag)) {
                heartbeatkey = heartBeatResult.heartbeatkey;
                Msg = Msg + "最新的心跳Key：" + heartBeatResult.heartbeatkey + "\n";
                Msg = Msg + "到期时间：" + heartBeatResult.endtime + "\n";
                Msg = Msg + "剩余点数：" + heartBeatResult.surpluspointvalue + "\n\n";
            } else {
                Msg = Msg + "本程序被不法分子修改了跳转地址\n\n";
            }
        } else {
            Msg = Msg + "心跳失败\n\n";
            //心跳错误信息
            //1001:未知错误
            //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
            //1007:提交的参数Json不正确
            //1036:软件初始化的Key-inisoftkey,不能为空
            //1037:软件初始化的Key-inisoftkey,不存在

            //1002:系统出错
            //1015:data业务参数不能为空
            //1018:data业务参数需要加密
            //1022:签名-sign,不能为空
            //1021:token失效或不正确
            //1032:提交的加密类型参数，与后台设置加密类型不一致
            //1023:签名-sign,不正确
            //1019:data业务参数不需要加密
            //1030:没有此加密类型
            //1031:加密类型不对或加密不对
            //1017:data业务参数Json不正确
            //1020:token不能为空
            //1054:平台类型-platformtypeid,错误

            //1033:心跳Key-heartBeatkey,不能为空
            //1034:心跳Key不正确
            //1012:心跳请求频繁

            //6005:卡密被禁用
            //4005:账号被禁用
            //4003:账号到期
            //6003:卡密到期
            //4004:账号点数不足
            //6004:卡密点数不足

            //1006:data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
            //6001:卡密-cardNum,不能为空
            //4007:账号-username,不能为空
        }
        return Msg;
    }

    /**
     * 退出登录示例
     */
    static String LoginOut(String cardnumorusername) throws Exception {
        String Msg = "";
        Msg = Msg + "退出登录：\n";
        //构建退出登录入参
        In_LoginOutArgs LoginOutArgs = new In_LoginOutArgs();
        LoginOutArgs.maccode = maccode;//必填
        LoginOutArgs.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        LoginOutArgs.cardnumorusername = cardnumorusername;//必填
        LoginOutArgs.token = token;//必填
        //请求接口，获取结果
        Result result = NetworkVerHelp.GetRequestResult(LoginOutArgs, BusinessType.loginOut);//请求接口
        Msg = Msg + "退出登录返回结果：\n";
        Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
        Msg = Msg + "信息：" + result.msg + "\n";
        Msg = Msg + "data数据：" + result.data + "\n\n";
        return Msg;

        //退出登录错误信息
        //1001:未知错误
        //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
        //1007:提交的参数Json不正确
        //1036:软件初始化的Key-inisoftkey,不能为空
        //1037:软件初始化的Key-inisoftkey,不存在

        //1002:系统出错
        //1015:data业务参数不能为空
        //1018:data业务参数需要加密
        //1022:签名-sign,不能为空
        //1021:token失效或不正确
        //1032:提交的加密类型参数，与后台设置加密类型不一致
        //1023:签名-sign,不正确
        //1019:data业务参数不需要加密
        //1030:没有此加密类型
        //1031:加密类型不对或加密不对
        //1017:data业务参数Json不正确
        //1020:token不能为空
        //1054:平台类型-platformtypeid,错误


        //1006：data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
        //6001:卡密-cardNum,不能为空
        //4007:账号-username,不能为空
    }


    /**
     * 卡密登录示例
     */
  public static Boolean LoginByCard(String CardNum) {
        Boolean IsLoginOk = false;
        //构建登录入参
        In_CardLoginArgs args = new In_CardLoginArgs();
        args.maccode = maccode;//必填
        args.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        args.requestflag = args.timestamp.toString();//必填
        args.cardnum = CardNum;//必填

        String Msg = "";
        try {
            //请求接口，获取结果
            Result result = NetworkVerHelp.GetRequestResult(args, BusinessType.cardLogin);//请求接口
            Msg = Msg + "----------卡密登录操作----------\n";
            if (result.code == 0) {
                //登录成功后的具体数据
                Out_Login LoginData = NetworkVerHelp.DecryptData(result.data, Out_Login.class);
                if (LoginData != null) {
                    if (LoginData.requestflag.equals(args.requestflag) == false) {
                        IsLoginOk = false;
                        Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                    } else {
                        Msg = Msg + "登录成功\n";
                        //Msg = Msg + "接口返回的数据：\n";
                        //Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                        Msg = Msg + "信息：" + result.msg + "\n";
                        //Msg = Msg + "data数据：" + result.data + "\n";
                        if (encrypttypeid == EncryptType.DES) {
                            //Msg = Msg + "data解密后的数据：" + NetworkVerHelp.DesDecrypt(result.data, encryptKey) + "\n";
                        }
                        IsLoginOk = true;
                        token = LoginData.token;
                        heartbeatkey = LoginData.heartbeatkey;
                        //Msg = Msg + "token令牌：" + LoginData.token + "\n";
                       //Msg = Msg + "登录接口返回的心跳Key：" + LoginData.heartbeatkey + "\n";

                        //Msg = Msg + HeartBeat(CardNum); //心跳示例
                    }

                } else {
                    Msg = Msg + "接口返回来的data数据转对象失败\n";
                }


            } else {
                Msg = Msg + "登录失败\n";
                //Msg = Msg + "接口返回的数据：\n";
                Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                //Msg = Msg + "信息：" + result.msg + "\n";
                //Msg = Msg + "data数据：" + result.data + "\n";
                //卡密登录错误信息
                //1001:未知错误
                //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
                //1007:提交的参数Json不正确
                //1036:软件初始化的Key-inisoftkey,不能为空
                //1037:软件初始化的Key-inisoftkey,不存在

                //1002:系统出错
                //1015:data业务参数不能为空
                //1009:商品Code-goodscode,不存在
                //1032:提交的加密类型参数，与后台设置加密类型不一致
                //1018:data业务参数需要加密
                //1022:签名-sign,不能为空
                //1023:签名-sign,不正确
                //1030:没有此加密类型
                //1031:加密类型不对或加密不对
                //1016:data业务参数加密不正确
                //1019:data业务参数不需要加密
                //1017:data业务参数Json不正确
                //1053:此商品已被禁用
                //1054:平台类型-platformtypeid,错误

                //1006:data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
                //1035:机器码 - maccode,长度不能大于100
                //6001:卡密-cardNum,不能为空
                //1027:此软件不是卡密登录类型
                //1003:作者软件或商品销售后台到期
                //6002:卡密-cardNum,不存在或已被删除
                //6003:卡密到期
                //6004:卡密点数不足
                //6005:卡密被禁用
                //6006:卡密已在线，禁止重复登录
                //6007:卡密尚未开通
                //1013:非绑定电脑上登陆
                //1014:超过最大登录数量

            }
            Msg = Msg + "--------------------\n\n\n";
        } catch (Exception e) {
            e.printStackTrace();
            Msg = Msg + "卡密登录出错：" + e.getMessage() + "\n";
        }

        final String finalMsg = Msg;
        System.out.println(finalMsg);
        return IsLoginOk;
    }

    /**
     * 在线支付开通/续费卡密示例
     */
    static void OpenRenewCardNum() {
        //注意：
        //1.如果需要开通"测试卡密"，那么价格类型需要填写“测试号”的价格类型ID
        //2.如果是开通“测试卡密”,Out_OpenRenewCardNum.orderid,Out_OpenRenewCardNum.payaddress,Out_OpenRenewCardNum.ordersearchaddress为三个值会返回空值回来
        String Msg = "";
        if (NetworkVerHelp.iniSoftInfoData == null) {
            final String finalMsg1 = "软件尚未初始化";

            return;
        }
        if (NetworkVerHelp.iniSoftInfoData.softpricelist == null ||
                (NetworkVerHelp.iniSoftInfoData.softpricelist != null && NetworkVerHelp.iniSoftInfoData.softpricelist.size() == 0)) {
            final String finalMsg1 = "此软件尚未有价格，请先到后台添加价格";

            return;
        }
        //构建开通续费卡密入参
        In_OpenRenewCardNumArgs args = new In_OpenRenewCardNumArgs();
        args.maccode = maccode;//必填
        args.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        args.requestflag = args.timestamp.toString();//必填
        args.priceid = NetworkVerHelp.iniSoftInfoData.softpricelist.get(0).priceid;//必填
        args.cardnum = cardnum;//必填
        try {
            //请求接口，获取结果
            Result result = NetworkVerHelp.GetRequestResult(args, BusinessType.openRenewCardNum);
            Msg = Msg + "----------在线支付开通/续费卡密操作----------\n";
            if (result.code == 0) {
                //开通续费卡密具体数据
                Out_OpenRenewCardNum openRenewCardNum = NetworkVerHelp.DecryptData(result.data, Out_OpenRenewCardNum.class);
                if (openRenewCardNum != null) {
                    if (openRenewCardNum.requestflag.equals(args.requestflag) == false) {
                        Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                    } else {
                        Msg = Msg + "接口返回的数据：\n";
                        Msg = Msg + "编码：" + result.code + "\n";
                        Msg = Msg + "信息：" + result.msg + "\n";
                        Msg = Msg + "data数据：" + result.data + "\n";

                        Msg = Msg + "订单号：" + openRenewCardNum.orderid + "\n";
                        Msg = Msg + "支付页面地址:" + openRenewCardNum.payaddress + "\n";
                        Msg = Msg + "订单查询页面地址:" + openRenewCardNum.ordersearchaddress + "\n";
                        if (encrypttypeid == EncryptType.DES) {
                            Msg = Msg + "data解密后的数据：" + NetworkVerHelp.DesDecrypt(result.data, encryptKey) + "\n";
                        }

                        if (openRenewCardNum.payaddress != "") {
                            //Uri uri = Uri.parse(openRenewCardNum.payaddress);
                            //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            //startActivity(intent);
                        }
                    }

                }

            } else {
                Msg = Msg + "在线支付开通/续费卡密失败\n";
                Msg = Msg + "接口返回的数据：\n";
                Msg = Msg + "编码：" + result.code + "\n";
                Msg = Msg + "信息：" + result.msg + "\n";
                Msg = Msg + "data数据：" + result.data + "\n";

                //开通续费账号错误信息
                //1001:未知错误
                //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
                //1007:提交的参数Json不正确
                //1036:软件初始化的Key-inisoftkey,不能为空
                //1037:软件初始化的Key-inisoftkey,不存在

                //1002:系统出错
                //1015:data业务参数不能为空
                //1009:商品Code-goodscode,不存在
                //1032:提交的加密类型参数，与后台设置加密类型不一致
                //1018:data业务参数需要加密
                //1022:签名-sign,不能为空
                //1023:签名-sign,不正确
                //1030:没有此加密类型
                //1031:加密类型不对或加密不对
                //1016:data业务参数加密不正确
                //1019:data业务参数不需要加密
                //1017:data业务参数Json不正确
                //1053:此商品已被禁用
                //1054:平台类型-platformtypeid,错误

                //1006:data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
                //4001：账号-username,密码-userpwd,不能为空
                //10026:此软件不是账号登录类型
                //1043:价格类型不能为空
                //1011:平台用户Code-platformUserCode,不存在
                //1048:代理商余额不足
                //6002:卡密-cardNum,不存在或已被删除
                //1044:此价格类型，不存在
                //1045:此价格类型，已被禁用
                //1040:操作失败：不能选择价格类型为“测试号”进行续费操作
                //6008:请将卡密激活后再续费(注：登录一次即激活卡密)
                //1063:开通测试卡密或账号：后台设置的“天数”或“开通的数量”等于0,所以禁止开通测试卡密或账号
                //1046:此价格类型，使用值尚未设置，使用值必须大于0
                //1052:后台第三方支付平台的AppId和接口密钥不能为空
                //1010:此软件未分配给代理商
                //1064:开通测试卡密或账号：已超出“设置的开通的数量”
            }

            Msg = Msg + "--------------------\n\n\n";
        } catch (Exception e) {
            Msg = Msg + "在线支付续费卡密出错：" + e.getMessage() + "\n";
        }

        final String finalMsg = Msg;
        System.out.println(finalMsg);

    }

    /**
     * 卡密详情示例
     */
   public static String CardDetail() {
        String Msg = "";
        //构建卡密详情入参
        In_cardDetailArgs args = new In_cardDetailArgs();
        args.maccode = maccode;//必填
        args.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        args.requestflag = args.timestamp.toString();//必填
        args.cardnum = cardnum;//必填
        try {
            //请求接口，获取结果
            Result result = NetworkVerHelp.GetRequestResult(args, BusinessType.cardDetail);
            Msg = Msg + "----------卡密详情操作----------\n";
            if (result.code == 0) {
                //卡密详情具体数据
                Out_cardDetail cardDetail = NetworkVerHelp.DecryptData(result.data, Out_cardDetail.class);
                if (cardDetail != null) {
                    if (cardDetail.requestflag.equals(args.requestflag) == false) {
                       // Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                    } else {
                       /* Msg = Msg + "卡密详情成功\n";
                        Msg = Msg + "接口返回的数据：\n";
                        Msg = Msg + "编码：" + result.code + "\n";
                        Msg = Msg + "信息：" + result.msg + "\n";
                        Msg = Msg + "data数据：" + result.data + "\n";
*/
                        Msg = Msg + "到期时间：" + cardDetail.endtime + "\n";
                        Msg = Msg + "剩余点数:" + cardDetail.surpluspointvalue + "\n";
                        if (encrypttypeid == EncryptType.DES) {
                          //  Msg = Msg + "data解密后的数据：" + NetworkVerHelp.DesDecrypt(result.data, encryptKey) + "\n";
                        }
                    }

                }

            } else {
                Msg = Msg + "卡密详情失败\n";
               // Msg = Msg + "接口返回的数据：\n";
                Msg = Msg + "编码：" + result.code + "\n";
               // Msg = Msg + "信息：" + result.msg + "\n";
                //Msg = Msg + "data数据：" + result.data + "\n";
                //卡密详情错误信息
                //1001:未知错误
                //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
                //1007:提交的参数Json不正确
                //1036:软件初始化的Key-inisoftkey,不能为空
                //1037:软件初始化的Key-inisoftkey,不存在

                //1002:系统出错
                //1015:data业务参数不能为空
                //1009:商品Code-goodscode,不存在
                //1032:提交的加密类型参数，与后台设置加密类型不一致
                //1018:data业务参数需要加密
                //1022:签名-sign,不能为空
                //1023:签名-sign,不正确
                //1030:没有此加密类型
                //1031:加密类型不对或加密不对
                //1016:data业务参数加密不正确
                //1019:data业务参数不需要加密
                //1017:data业务参数Json不正确
                //1053:此商品已被禁用
                //1054:平台类型-platformtypeid,错误

                //1006:data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
                //6001:卡密-cardNum,不能为空
                //1027:此软件不是卡密登录类型
                //1003:作者软件或商品销售后台到期
                //6002:卡密-cardNum,不存在或已被删除
                //1065:查询速度过快
            }

            Msg = Msg + "--------------------\n\n\n";
        } catch (Exception e) {
            Msg = Msg + "在线支付续费卡密出错：" + e.getMessage() + "\n";
        }

        final String finalMsg = Msg;
        return finalMsg;
    }


    /**
     * 注册账号示例
     */
    static void AccountRegister(String _username, String _userpwd) {
        //构建注册账号入参
        In_AccountRegisterArgs args = new In_AccountRegisterArgs();
        args.maccode = maccode;//必填
        args.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        args.requestflag = args.timestamp.toString();//必填
        args.username = _username;//必填
        args.userpwd = _userpwd;//必填
        args.qq = "";//可选
        args.wx = "";//可选
        args.alipay = "";//可选
        args.tel = "";//可选
        args.email = "";//可选

        String Msg = "";
        try {
            Msg = Msg + "----------注册账号操作----------\n";
            //请求接口，获取结果
            Result result = NetworkVerHelp.GetRequestResult(args, BusinessType.accountRegister);//请求接口
            if (result.code == 0) {
                //注册账号具体数据
                Out_DataBaseArgs out_DataBaseArgs = NetworkVerHelp.DecryptData(result.data, Out_DataBaseArgs.class);
                if (out_DataBaseArgs.requestflag.equals(args.requestflag) == false) {
                    Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                } else {
                    Msg = Msg + "注册账号成功\n";
                    Msg = Msg + "接口返回的数据：\n";
                    Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                    Msg = Msg + "信息：" + result.msg + "\n";
                    Msg = Msg + "data数据：" + result.data + "\n";
                }


            } else {
                Msg = Msg + "注册账号失败\n";
                Msg = Msg + "接口返回的数据：\n";
                Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                Msg = Msg + "信息：" + result.msg + "\n";
                Msg = Msg + "data数据：" + result.data + "\n";
                //账号注册错误信息
                //1001:未知错误
                //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
                //1007:提交的参数Json不正确
                //1036:软件初始化的Key-inisoftkey,不能为空
                //1037:软件初始化的Key-inisoftkey,不存在

                //1002:系统出错
                //1015:data业务参数不能为空
                //1009:商品Code-goodscode,不存在
                //1032:提交的加密类型参数，与后台设置加密类型不一致
                //1018:data业务参数需要加密
                //1022:签名-sign,不能为空
                //1023:签名-sign,不正确
                //1030:没有此加密类型
                //1031:加密类型不对或加密不对
                //1016:data业务参数加密不正确
                //1019:data业务参数不需要加密
                //1017:data业务参数Json不正确
                //1053:此商品已被禁用
                //1054:平台类型-platformtypeid,错误

                //1011:平台用户Code-platformUserCode,不存在
                //1006:data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
                //10026:此软件不是账号登录类型
                //1003:作者软件或商品销售后台到期
                //1062:注册的账号或卡密过快，请稍后重试
                //3001:账号-username,密码-pwd,不能为空
                //3002:账号-username,长度不能大于20
                //3003:密码-userpwd,长度不能大于20
                //3007:账号-username,只能字母或数字
                //3005:已超过最大注册数量
                //1010:此软件未分配给代理商
                //3006：账号-username,已存在，请更换一个
            }

            Msg = Msg + "--------------------\n\n\n";
        } catch (Exception e) {
            e.printStackTrace();
            Msg = Msg + "注册账号出错：" + e.getMessage() + "\n";
        }

        final String finalMsg = Msg;

    }

    /**
     * 账号登录示例
     */
    static Boolean LoginAccount(String _username, String _userpwd, boolean IsLoginOut) {
        Boolean IsLoginOk = false;
        //构建账号登录入参
        In_AccountLoginArgs args = new In_AccountLoginArgs();
        args.maccode = maccode;//必填
        args.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        args.requestflag = args.timestamp.toString();//必填
        args.username = _username;//必填
        args.userpwd = _userpwd;//必填

        String Msg = "";
        try {
            //请求接口，获取结果
            Result result = NetworkVerHelp.GetRequestResult(args, BusinessType.accountLogin);//请求接口
            Msg = Msg + "----------账号密码登录操作----------\n";
            if (result.code == 0) {
                //账号登录具体数据
                Out_Login LoginData = NetworkVerHelp.DecryptData(result.data, Out_Login.class);
                if (LoginData != null) {
                    if (LoginData.requestflag.equals(args.requestflag) == false) {
                        IsLoginOk = false;
                        Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\r\n";
                    } else {
                        Msg = Msg + "登录成功\n";
                        Msg = Msg + "接口返回的数据：\n";
                        Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                        Msg = Msg + "信息：" + result.msg + "\n";
                        Msg = Msg + "data数据：" + result.data + "\n";

                        if (encrypttypeid == EncryptType.DES) {
                            Msg = Msg + "data解密后的数据：" + NetworkVerHelp.DesDecrypt(result.data, encryptKey) + "\n";
                        }
                        IsLoginOk = true;
                        token = LoginData.token;
                        heartbeatkey = LoginData.heartbeatkey;
                        Msg = Msg + "token令牌：" + LoginData.token + "\n";
                        Msg = Msg + "登录接口返回的心跳Key：" + LoginData.heartbeatkey + "\n";

                        Msg = Msg + HeartBeat(_username); //心跳示例
                        if (IsLoginOut) {
                            Msg = Msg + LoginOut(_username);//退出登录示例
                        }
                    }

                } else {
                    Msg = Msg + "接口返回来的data数据转对象失败\n";
                }

            } else {
                Msg = Msg + "登录失败\n";
                Msg = Msg + "接口返回的数据：\n";
                Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                Msg = Msg + "信息：" + result.msg + "\n";
                Msg = Msg + "data数据：" + result.data + "\n";
                //账号登录错误信息
                //1001:未知错误
                //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
                //1007:提交的参数Json不正确
                //1036:软件初始化的Key-inisoftkey,不能为空
                //1037:软件初始化的Key-inisoftkey,不存在

                //1002:系统出错
                //1015:data业务参数不能为空
                //1009:商品Code-goodscode,不存在
                //1032:提交的加密类型参数，与后台设置加密类型不一致
                //1018:data业务参数需要加密
                //1022:签名-sign,不能为空
                //1023:签名-sign,不正确
                //1030:没有此加密类型
                //1031:加密类型不对或加密不对
                //1016:data业务参数加密不正确
                //1019:data业务参数不需要加密
                //1017:data业务参数Json不正确
                //1053:此商品已被禁用
                //1054:平台类型-platformtypeid,错误

                //1006:data里面的参数：软件版本号-versionname,机器码-MacCode,时间戳-timestamp,不能为空
                //1035:机器码-maccode,长度不能大于100
                //4001:账号-username,密码-userpwd,不能为空
                //1026:此软件不是账号登录类型
                //1003:作者软件或商品销售后台到期
                //4002:账号或密码错误或被删除
                //4009:账号尚未开通
                //4003:账号到期
                //4004:账号点数不足
                //4005:账号被禁用
                //4002:账号或密码错误或被删除
                //4006:账号已在线，禁止重复登录
                //1013:非绑定电脑上登陆
                //1014:超过最大登录数量

            }
            Msg = Msg + "--------------------\n\n\n";
        } catch (Exception e) {
            e.printStackTrace();
            Msg = Msg + "卡密登录出错：" + e.getMessage() + "\n";
        }

        final String finalMsg = Msg;

        return IsLoginOk;
    }


    /**
     * 在线支付开通/续费账号示例
     */
    static void OpenRenewAccount() {
        //注意：
        //1.如果需要开通"测试账号"，那么价格类型需要填写“测试号”的价格类型ID
        //2.如果是开通“测试账号”,Out_OpenRenewCardNum.orderid,Out_OpenRenewCardNum.payaddress,Out_OpenRenewCardNum.ordersearchaddress为三个值会返回空值回来
        String Msg = "";
        if (NetworkVerHelp.iniSoftInfoData == null) {
            final String finalMsg1 = "软件尚未初始化";

            return;
        }
        if (NetworkVerHelp.iniSoftInfoData.softpricelist == null ||
                (NetworkVerHelp.iniSoftInfoData.softpricelist != null && NetworkVerHelp.iniSoftInfoData.softpricelist.size() == 0)) {
            final String finalMsg1 = "此软件尚未有价格，请先到后台添加价格";

            return;
        }
        //构建开通续费账号入参
        In_OpenRenewAccountArgs args = new In_OpenRenewAccountArgs();
        args.maccode = maccode;//必填
        args.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        args.requestflag = args.timestamp.toString();//必填
        args.priceid = NetworkVerHelp.iniSoftInfoData.softpricelist.get(0).priceid;//必填
        args.username = userName;//必填
        args.userpwd = userpwd;//必填
        try {
            //请求接口，获取结果
            Result result = NetworkVerHelp.GetRequestResult(args, BusinessType.openRenewAccount);
            Msg = Msg + "----------在线支付开通续费账号操作----------\n";
            if (result.code == 0) {
                //开通续费账号具体数据
                Out_OpenRenewAccount openRenewAccount = NetworkVerHelp.DecryptData(result.data, Out_OpenRenewAccount.class);
                if (openRenewAccount != null) {
                    if (openRenewAccount.requestflag.equals(args.requestflag) == false) {
                        Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                    } else {
                        Msg = Msg + "接口返回的数据：\n";
                        Msg = Msg + "编码：" + result.code + "\n";
                        Msg = Msg + "信息：" + result.msg + "\n";
                        Msg = Msg + "data数据：" + result.data + "\n";

                        Msg = Msg + "订单号：" + openRenewAccount.orderid + "\n";
                        Msg = Msg + "支付页面地址:" + openRenewAccount.payaddress + "\n";
                        Msg = Msg + "订单查询页面地址:" + openRenewAccount.ordersearchaddress + "\n";
                        if (encrypttypeid == EncryptType.DES) {
                            Msg = Msg + "data解密后的数据：" + NetworkVerHelp.DesDecrypt(result.data, encryptKey) + "\n";
                        }
                        if (openRenewAccount.payaddress != "") {
                            //Uri uri = Uri.parse(openRenewAccount.payaddress);
                            //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            //startActivity(intent);
                        }
                    }
                }

            } else {
                Msg = Msg + "在线支付开通续费账号失败\n";
                Msg = Msg + "接口返回的数据：\n";
                Msg = Msg + "编码：" + result.code + "\n";
                Msg = Msg + "信息：" + result.msg + "\n";
                Msg = Msg + "data数据：" + result.data + "\n";
                //开通续费账号错误信息
                //1001:未知错误
                //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
                //1007:提交的参数Json不正确
                //1036:软件初始化的Key-inisoftkey,不能为空
                //1037:软件初始化的Key-inisoftkey,不存在

                //1002:系统出错
                //1015:data业务参数不能为空
                //1009:商品Code-goodscode,不存在
                //1032:提交的加密类型参数，与后台设置加密类型不一致
                //1018:data业务参数需要加密
                //1022:签名-sign,不能为空
                //1023:签名-sign,不正确
                //1030:没有此加密类型
                //1031:加密类型不对或加密不对
                //1016:data业务参数加密不正确
                //1019:data业务参数不需要加密
                //1017:data业务参数Json不正确
                //1053:此商品已被禁用
                //1054:平台类型-platformtypeid,错误

                //1006:data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
                //4001：账号-username,密码-userpwd,不能为空
                //10026:此软件不是账号登录类型
                //1043:价格类型不能为空
                //1011:平台用户Code-platformUserCode,不存在
                //1048:代理商余额不足
                //4002:账号或密码错误或被删除
                //1044:此价格类型，不存在
                //1045:此价格类型，已被禁用
                //1040:操作失败：不能选择价格类型为“测试号”进行续费操作
                //4010:请将账号激活后再续费(注：登录一次即激活账号)
                //1063:开通测试卡密或账号：后台设置的“天数”或“开通的数量”等于0,所以禁止开通测试卡密或账号
                //1046:此价格类型，使用值尚未设置，使用值必须大于0
                //1052:后台第三方支付平台的AppId和接口密钥不能为空
                //1010:此软件未分配给代理商
                //1046:此价格类型，未授权给当前代理商
                //1064:开通测试卡密或账号：已超出“设置的开通的数量”
            }

            Msg = Msg + "--------------------\n\n\n";
        } catch (Exception e) {
            Msg = Msg + "在线支付续费卡密出错：" + e.getMessage() + "\n";
        }

        final String finalMsg = Msg;


    }

    /**
     * 账号详情示例
     */
    static void AccountDetail() {
        String Msg = "";
        //构建账号详情入参
        In_accountDetailArgs args = new In_accountDetailArgs();
        args.maccode = maccode;//必填
        args.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        args.requestflag = args.timestamp.toString();//必填
        args.username = userName;//必填
        args.userpwd = userpwd;//必填
        try {
            //请求接口，获取结果
            Result result = NetworkVerHelp.GetRequestResult(args, BusinessType.accountDetail);
            Msg = Msg + "----------账号详情操作----------\n";
            if (result.code == 0) {
                //账号详情具体数据
                Out_accountDetail accountDetail = NetworkVerHelp.DecryptData(result.data, Out_accountDetail.class);
                if (accountDetail != null) {
                    if (accountDetail.requestflag.equals(args.requestflag) == false) {
                        Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                    } else {
                        Msg = Msg + "账号详情成功\n";
                        Msg = Msg + "接口返回的数据：\n";
                        Msg = Msg + "编码：" + result.code + "\n";
                        Msg = Msg + "信息：" + result.msg + "\n";
                        Msg = Msg + "data数据：" + result.data + "\n";

                        Msg = Msg + "到期时间：" + accountDetail.endtime + "\n";
                        Msg = Msg + "剩余点数:" + accountDetail.surpluspointvalue + "\n";
                        if (encrypttypeid == EncryptType.DES) {
                            Msg = Msg + "data解密后的数据：" + NetworkVerHelp.DesDecrypt(result.data, encryptKey) + "\n";
                        }
                    }

                }

            } else {
                Msg = Msg + "账号详情失败\n";
                Msg = Msg + "接口返回的数据：\n";
                Msg = Msg + "编码：" + result.code + "\n";
                Msg = Msg + "信息：" + result.msg + "\n";
                Msg = Msg + "data数据：" + result.data + "\n";
                //账号详情错误信息
                //1001:未知错误
                //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
                //1007:提交的参数Json不正确
                //1036:软件初始化的Key-inisoftkey,不能为空
                //1037:软件初始化的Key-inisoftkey,不存在

                //1002:系统出错
                //1015:data业务参数不能为空
                //1009:商品Code-goodscode,不存在
                //1032:提交的加密类型参数，与后台设置加密类型不一致
                //1018:data业务参数需要加密
                //1022:签名-sign,不能为空
                //1023:签名-sign,不正确
                //1030:没有此加密类型
                //1031:加密类型不对或加密不对
                //1016:data业务参数加密不正确
                //1019:data业务参数不需要加密
                //1017:data业务参数Json不正确
                //1053:此商品已被禁用
                //1054:平台类型-platformtypeid,错误

                //1006:data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
                //4001:账号-username,密码-userpwd,不能为空
                //1026:此软件不是账号登录类型
                //1003:作者软件或商品销售后台到期
                //4002:账号或密码错误或被删除
                //1065:查询速度过快
            }

            Msg = Msg + "--------------------\n\n\n";
        } catch (Exception e) {
            Msg = Msg + "在线支付续费卡密出错：" + e.getMessage() + "\n";
        }

        final String finalMsg = Msg;

    }


    /**
     * 修改用户密码示例
     */
    static void updPwd(String _username, String _userpwd, String _newpwd) {
        //构建修改账号密码入参
        In_UpdPwdArgs args = new In_UpdPwdArgs();
        args.maccode = maccode;//必填
        args.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        args.requestflag = args.timestamp.toString();//必填
        args.username = _username;//必填
        args.userpwd = _userpwd;//必填
        args.newpwd = _newpwd;//必填

        String Msg = "";
        try {
            //请求接口，获取结果
            Result result = NetworkVerHelp.GetRequestResult(args, BusinessType.updPwd);
            Msg = Msg + "----------修改用户密码操作----------\n";
            if (result.code == 0) {
                //修改账号密码具体数据
                Out_DataBaseArgs out_DataBaseArgs = NetworkVerHelp.DecryptData(result.data, Out_DataBaseArgs.class);
                if (out_DataBaseArgs.requestflag.equals(args.requestflag) == false) {
                    Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                } else {
                    Msg = Msg + "修改用户密码成功\n";
                    Msg = Msg + "接口返回的数据：\n";
                    Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                    Msg = Msg + "信息：" + result.msg + "\n";
                    Msg = Msg + "data数据：" + result.data + "\n";
                }


            } else {
                Msg = Msg + "修改用户密码失败\n";
                Msg = Msg + "接口返回的数据：\n";
                Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                Msg = Msg + "信息：" + result.msg + "\n";
                Msg = Msg + "data数据：" + result.data + "\n";
                //修改用户密码错误信息
                //1001:未知错误
                //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
                //1007:提交的参数Json不正确
                //1036:软件初始化的Key-inisoftkey,不能为空
                //1037:软件初始化的Key-inisoftkey,不存在

                //1002:系统出错
                //1015:data业务参数不能为空
                //1009:商品Code-goodscode,不存在
                //1032:提交的加密类型参数，与后台设置加密类型不一致
                //1018:data业务参数需要加密
                //1022:签名-sign,不能为空
                //1023:签名-sign,不正确
                //1030:没有此加密类型
                //1031:加密类型不对或加密不对
                //1016:data业务参数加密不正确
                //1019:data业务参数不需要加密
                //1017:data业务参数Json不正确
                //1053:此商品已被禁用
                //1054:平台类型-platformtypeid,错误

                //1006:data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
                //5001:账号-username,密码-userpwd,新密码-newpwd,不能为空
                //4002:账号或密码错误或被删除
                //4005:账号被禁用
            }

            Msg = Msg + "--------------------\n\n\n";
        } catch (Exception e) {
            e.printStackTrace();
            Msg = Msg + "修改用户密码出错：" + e.getMessage() + "\n";
        }

        final String finalMsg = Msg;

    }


    /**
     * 扣点示例
     */
    static void BucklePoint(String cardnumorusername, int bucklevalue, String _token) {
        //注意：此软件消耗类型是：点数，才可以调用此接口进行扣点
        //构建扣点入参
        In_BucklePointArgs args = new In_BucklePointArgs();
        args.maccode = maccode;//必填
        args.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        args.requestflag = args.timestamp.toString();//必填
        args.cardnumorusername = cardnumorusername;//必填
        args.bucklevalue = bucklevalue;//必填
        args.token = _token;//必填

        String Msg = "";
        try {
            //请求接口，获取结果
            Result result = NetworkVerHelp.GetRequestResult(args, BusinessType.bucklePoint);
            Msg = Msg + "----------扣点操作----------\n";
            if (result.code == 0) {
                //扣点具体数据
                Out_bucklePoint bucklePoint = NetworkVerHelp.DecryptData(result.data, Out_bucklePoint.class);
                if (bucklePoint != null) {
                    if (bucklePoint.requestflag.equals(args.requestflag) == false) {
                        Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                    } else {
                        Msg = Msg + "扣点成功\n";
                        Msg = Msg + "接口返回的数据：\n";
                        Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                        Msg = Msg + "信息：" + result.msg + "\n";
                        Msg = Msg + "data数据：" + result.data + "\n";

                        if (encrypttypeid == EncryptType.DES) {
                            Msg = Msg + "data解密后的数据：" + NetworkVerHelp.DesDecrypt(result.data, encryptKey) + "\n";
                        }
                        Msg = Msg + "剩余点数：" + bucklePoint.surpluspointvalue + "\n";
                    }

                }
            } else {
                Msg = Msg + "扣点失败\n";
                Msg = Msg + "接口返回的数据：\n";
                Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                Msg = Msg + "信息：" + result.msg + "\n";
                Msg = Msg + "data数据：" + result.data + "\n";
                //扣点错误信息
                //1001:未知错误
                //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
                //1007:提交的参数Json不正确
                //1036:软件初始化的Key-inisoftkey,不能为空
                //1037:软件初始化的Key-inisoftkey,不存在

                //1002:系统出错
                //1015:data业务参数不能为空
                //1018:data业务参数需要加密
                //1022:签名-sign,不能为空
                //1021:token失效或不正确
                //1032:提交的加密类型参数，与后台设置加密类型不一致
                //1023:签名-sign,不正确
                //1019:data业务参数不需要加密
                //1030:没有此加密类型
                //1031:加密类型不对或加密不对
                //1017:data业务参数Json不正确
                //1020:token不能为空
                //1054:平台类型-platformtypeid,错误


                //6005:卡密被禁用
                //4005:账号被禁用
                //4003:账号到期
                //6003:卡密到期
                //4004:账号点数不足
                //6004:卡密点数不足

                //1006:data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
                //6001:卡密-cardNum,不能为空
                //4007:账号-username,不能为空
                //1024:此软件不是消耗点数类型
                //6002:卡密-cardNum,不存在或已被删除
                //6004:卡密点数不足
                //4008:账号-username,不存在或已被删除
                //4004:账号点数不足
            }

            Msg = Msg + "--------------------\n\n\n";
        } catch (Exception e) {
            e.printStackTrace();
            Msg = Msg + "扣点出错：" + e.getMessage() + "\n";
        }

        final String finalMsg = Msg;

    }

    //获取软件变量示例
    public static String GetremoteVar(String cardnumorusername, String _token, String _testVer) {
        //构建获取软件变量入参
        In_getRemoteVarArgs args = new In_getRemoteVarArgs();
        args.maccode = maccode;//必填
        args.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        args.requestflag = args.timestamp.toString();//必填
        args.cardnumorusername = cardnumorusername;//必填
        args.token = _token;//必填
        args.varname = _testVer;//必填,如果此值为空，那么接口返回来的是此软件所有的变量，否则是此变量名所对应的变量值

        String Msg = "";
        try {
            //请求接口，获取结果
            Result result = NetworkVerHelp.GetRequestResult(args, BusinessType.getremoteVar);
           // Msg = Msg + "----------获取软件变量操作----------\n";
            if (result.code == 0) {
                //变量具体数据
                Out_getRemoteVar RemoteVar = NetworkVerHelp.DecryptData(result.data, Out_getRemoteVar.class);
                if (RemoteVar != null) {
                    if (RemoteVar.requestflag.equals(args.requestflag) == false) {
                        //Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                    } else {
                       // Msg = Msg + "获取软件变量成功\n";
                        //Msg = Msg + "接口返回的数据：\n";
                        //Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                        //Msg = Msg + "信息：" + result.msg + "\n";
                        //Msg = Msg + "data数据：" + result.data + "\n";

                        if (encrypttypeid == EncryptType.DES) {
                            Msg = Msg + NetworkVerHelp.DesDecrypt(result.data, encryptKey) + "\n";
                        }
                    }

                }

            } else {
                Msg = Msg + "获取软件变量失败\n";
              // Msg = Msg + "接口返回的数据：\n";
                Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                //Msg = Msg + "信息：" + result.msg + "\n";
               // Msg = Msg + "data数据：" + result.data + "\n";
                //获取软件变量错误信息
                //1001:未知错误
                //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
                //1007:提交的参数Json不正确
                //1036:软件初始化的Key-inisoftkey,不能为空
                //1037:软件初始化的Key-inisoftkey,不存在

                //1002:系统出错
                //1015:data业务参数不能为空
                //1018:data业务参数需要加密
                //1022:签名-sign,不能为空
                //1021:token失效或不正确
                //1032:提交的加密类型参数，与后台设置加密类型不一致
                //1023:签名-sign,不正确
                //1019:data业务参数不需要加密
                //1030:没有此加密类型
                //1031:加密类型不对或加密不对
                //1017:data业务参数Json不正确
                //1020:token不能为空
                //1054:平台类型-platformtypeid,错误

                //1006：data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
                //6001:卡密-cardNum,不能为空
                //4007:账号-username,不能为空
            }

            Msg = Msg + "--------------------\n\n\n";
        } catch (Exception e) {
            e.printStackTrace();
            Msg = Msg + "获取软件变量出错：" + e.getMessage() + "\n";
        }

        final String finalMsg = Msg;
        System.out.println(finalMsg);
        return finalMsg;
    }

    
    /**
     * 解绑机器码(如果软件设置解绑扣除相应的时间或点数，那么解绑成功后会自动扣除)
     */
    static void unbundMac(String cardnumorusername, String _token) {
        //构建解绑机器码入参
        In_unbundMacArgs args = new In_unbundMacArgs();
        args.maccode = maccode;//必填
        args.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        args.requestflag = args.timestamp.toString();//必填
        args.cardnumorusername = cardnumorusername;//必填
        args.token = _token;//必填

        String Msg = "";
        try {
            //请求接口，获取结果
            Result result = NetworkVerHelp.GetRequestResult(args, BusinessType.unbundMac);
            Msg = Msg + "----------解绑机器码操作----------\n";
            if (result.code == 0) {
                //解绑机器码具体数据
                Out_unbundMacArgs unbundMacData = NetworkVerHelp.DecryptData(result.data, Out_unbundMacArgs.class);
                if (unbundMacData != null) {
                    if (unbundMacData.requestflag.equals(args.requestflag) == false) {
                        Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                    } else {
                        Msg = Msg + "解绑机器码成功\n";
                        Msg = Msg + "接口返回的数据：\n";
                        Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                        Msg = Msg + "信息：" + result.msg + "\n";
                        Msg = Msg + "data数据：" + result.data + "\n";

                        if (encrypttypeid == EncryptType.DES) {
                            Msg = Msg + "data解密后的数据：" + NetworkVerHelp.DesDecrypt(result.data, encryptKey) + "\n";
                        }
                        Msg = Msg + "到期时间：" + unbundMacData.endtime + "\n";
                        Msg = Msg + "剩余点数：" + unbundMacData.surpluspointvalue + "\n";
                    }

                }


            } else {
                Msg = Msg + "解绑机器码失败\n";
                Msg = Msg + "接口返回的数据：\n";
                Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                Msg = Msg + "信息：" + result.msg + "\n";
                Msg = Msg + "data数据：" + result.data + "\n";
                //解绑机器码错误信息
                //1001:未知错误
                //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
                //1007:提交的参数Json不正确
                //1036:软件初始化的Key-inisoftkey,不能为空
                //1037:软件初始化的Key-inisoftkey,不存在

                //1002:系统出错
                //1015:data业务参数不能为空
                //1018:data业务参数需要加密
                //1022:签名-sign,不能为空
                //1021:token失效或不正确
                //1032:提交的加密类型参数，与后台设置加密类型不一致
                //1023:签名-sign,不正确
                //1019:data业务参数不需要加密
                //1030:没有此加密类型
                //1031:加密类型不对或加密不对
                //1017:data业务参数Json不正确
                //1020:token不能为空
                //1054:平台类型-platformtypeid,错误

                //1006：data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
                //6001:卡密-cardNum,不能为空
                //4007:账号-username,不能为空

                //1009:商品Code-goodscode,不存在
                //6002:卡密-cardNum,不存在或已被删除
                //7004:在线支付中，禁止解绑
                //7001:剩余时间不够扣除解绑的数值
                //7002:剩余点数不够扣除解绑的数值
                //4008:账号-username,不存在或已被删除
                //7003:账号尚未开通，禁止解绑
            }

            Msg = Msg + "--------------------\n\n\n";
        } catch (Exception e) {
            e.printStackTrace();
            Msg = Msg + "解绑机器码出错：" + e.getMessage() + "\n";
        }

        final String finalMsg = Msg;


    }


    /**
     * 在线支付购买商品示例
     */
    static void BuyGoods() {
        String Msg = "";
        if (NetworkVerHelp.iniGoodsInfoData == null) {
            final String finalMsg1 = "软件尚未初始化";

            return;
        }
        //构建在线支付购买商品入参
        In_BuyGoodsArgs args = new In_BuyGoodsArgs();
        args.maccode = maccode;//必填
        args.timestamp = NetworkVerHelp.GetTimeStamp();//必填
        args.requestflag = args.timestamp.toString();//必填
        try {
            //请求接口，获取结果
            Result result = NetworkVerHelp.GetRequestResult(args, BusinessType.buyGoods);
            Msg = Msg + "----------在线支付购买商品操作----------\n";
            if (result.code == 0) {
                //在线支付购买商品具体数据
                Out_BuyGoods buyCardNumInfo = NetworkVerHelp.DecryptData(result.data, Out_BuyGoods.class);
                if (buyCardNumInfo != null) {
                    if (buyCardNumInfo.requestflag.equals(args.requestflag) == false) {
                        Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                    } else {
                        Msg = Msg + "订单号：" + buyCardNumInfo.orderid + "\n";
                        Msg = Msg + "支付页面地址:" + buyCardNumInfo.payaddress + "\n";
                        Msg = Msg + "订单查询页面地址:" + buyCardNumInfo.ordersearchaddress + "\n";
                        Msg = Msg + "接口返回的数据：\n";
                        Msg = Msg + "编码：" + result.code + "\n";
                        Msg = Msg + "信息：" + result.msg + "\n";
                        Msg = Msg + "data数据：" + result.data + "\n";
                        if (encrypttypeid == EncryptType.DES) {
                            Msg = Msg + "data解密后的数据：" + NetworkVerHelp.DesDecrypt(result.data, encryptKey) + "\n";
                        }

                        //Uri uri = Uri.parse(buyCardNumInfo.payaddress);
                        //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        //startActivity(intent);
                    }

                }

            } else {
                Msg = Msg + "在线支付购买商品失败\n";
                Msg = Msg + "接口返回的数据：\n";
                Msg = Msg + "编码：" + result.code + "\n";
                Msg = Msg + "信息：" + result.msg + "\n";
                Msg = Msg + "data数据：" + result.data + "\n";

                //在线支付-购买商品错误信息
                //1001:未知错误
                //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
                //1007:提交的参数Json不正确
                //1036:软件初始化的Key-inisoftkey,不能为空
                //1037:软件初始化的Key-inisoftkey,不存在

                //1002:系统出错
                //1015:data参数不能为空
                //1009:商品Code-goodscode,不存在
                //1032:提交的加密类型参数，与后台设置加密类型不一致
                //1018:data参数需要加密
                //1022:签名-sign,不能为空
                //1023:签名-sign,不正确
                //1030:没有此加密类型
                //1031:加密类型不对或加密不对
                //1016:data参数加密不正确
                //1019:data参数不需要加密
                //1017:data参数Json不正确
                //1053:此商品已被禁用
                //1054:平台类型-platformtypeid,错误

                //1006:data里面的参数：机器码-MacCode,时间戳-timestamp,不能为空
                //1003:作者软件销售后台到期
                //1011:平台用户Code-platformUserCode,不存在
                //1052:后台第三方支付平台的AppId和接口密钥不能为空
            }

            Msg = Msg + "--------------------\n\n\n";
        } catch (Exception e) {
            Msg = Msg + "在线支付购买卡密操作出错：" + e.getMessage() + "\n";
        }

        final String finalMsg = Msg;


    }

    //修改商品订单标记示例
    static void UpdOrderFlag(String orderid, String orderflag) {
        String Msg = "";
        Boolean isUpd = true;
        if (platformtypeid != 2) {
            if (NetworkVerHelp.iniGoodsInfoData == null ||
                    (NetworkVerHelp.iniGoodsInfoData != null && NetworkVerHelp.iniGoodsInfoData.inisoftkey == "")) {
                Msg = "平台类型选错：请选择“自动发货平台”";
                isUpd = false;
            }
        }

        if (isUpd) {
            In_UpdOrderFlagArgs args = new In_UpdOrderFlagArgs();
            args.maccode = maccode;//必填
            args.timestamp = NetworkVerHelp.GetTimeStamp();//必填
            args.requestflag = args.timestamp.toString();//必填
            args.orderid = orderid;//必填
            args.orderflag = orderflag;//必填


            try {
                Result result = NetworkVerHelp.GetRequestResult(args, BusinessType.updOrderFlag);
                Msg = Msg + "----------修改商品订单标记操作----------\n";
                if (result.code == 0) {
                    Out_DataBaseArgs out_DataBaseArgs = NetworkVerHelp.DecryptData(result.data, Out_DataBaseArgs.class);
                    if (out_DataBaseArgs.requestflag.equals(args.requestflag) == false) {
                        Msg = Msg + "接口返回的数据已被“破解者”截持，您可以强制关闭软件或者不做任何处理\n";
                    } else {
                        Msg = Msg + "修改商品订单标记成功\n";
                        Msg = Msg + "接口返回的数据：\n";
                        Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                        Msg = Msg + "信息：" + result.msg + "\n";
                        Msg = Msg + "data数据：" + result.data + "\n";
                    }

                } else {
                    Msg = Msg + "修改商品订单标记失败\n";
                    Msg = Msg + "接口返回的数据：\n";
                    Msg = Msg + "编码：" + String.valueOf(result.code) + "\n";
                    Msg = Msg + "信息：" + result.msg + "\n";
                    Msg = Msg + "data数据：" + result.data + "\n";
                    //标记订单标识错误信息(注意：是针对“自动发货平台的订单”标识)：
                    //1001:未知错误
                    //1005:平台ID-platformtypeid,业务ID-businessID,加密类型-encrypttypeid,平台用户编码-platformusercode,商品编码-goodscode,时间戳-timestamp,不能为空
                    //1007:提交的参数Json不正确
                    //1036:软件初始化的Key-inisoftkey,不能为空
                    //1037:软件初始化的Key-inisoftkey,不存在

                    //1002:系统出错
                    //1015:data业务参数不能为空
                    //1009:商品Code-goodscode,不存在
                    //1032:提交的加密类型参数，与后台设置加密类型不一致
                    //1018:data业务参数需要加密
                    //1022:签名-sign,不能为空
                    //1023:签名-sign,不正确
                    //1030:没有此加密类型
                    //1031:加密类型不对或加密不对
                    //1016:data业务参数加密不正确
                    //1019:data业务参数不需要加密
                    //1017:data业务参数Json不正确
                    //1053:此商品已被禁用
                    //1054:平台类型-platformtypeid,错误

                    //1056:订单号不能为空
                }

                Msg = Msg + "--------------------\n\n\n";
            } catch (Exception e) {
                e.printStackTrace();
                Msg = Msg + "修改用户密码出错：" + e.getMessage() + "\n";
            }

        }


        final String finalMsg = Msg;
    }

}
