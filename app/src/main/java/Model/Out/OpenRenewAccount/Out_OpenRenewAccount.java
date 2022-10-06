package Model.Out.OpenRenewAccount;


import Model.Out.Out_DataBaseArgs;

/**
 * 在线支付--开通续费账号出参
 * */
public class Out_OpenRenewAccount extends Out_DataBaseArgs {
    /**
     * 订单号(强烈建议，将此订单号保存到本地，这样如果出现异常情况，可以让用户拿着订单号来查询对单)
     * */
    public String orderid="";
    /**
     * 在线支付页面地址(在您的程序中，直接用WebBrowser或者用其它浏览器打开此支付页面，即可！用户支付成功后，会把卡密直接显示在页面上)
     * */
    public  String payaddress="";

    /**
     * 订单查询页面地址(可以通过订单号查询出已购买过的卡密)
     * */
    public  String ordersearchaddress="";
}
