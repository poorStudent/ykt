package Model.Out.accountDetail;


import Model.Out.Out_DataBaseArgs;

/*账号详情出参*/
public class Out_accountDetail extends Out_DataBaseArgs {
    /*
     * 账号到期时间
     *注意：只有账号激活的情况下，服务器才会返回
     */
    public String endtime = "";

    /*
     * 账号剩余点数
     *注意：只有账号激活的情况下，服务器才会返回
     */
    public int surpluspointvalue;

    /**最近一次登录的机器码*/
    public String maccode="";
    /*
     *开通的价格类型ID
     */
    public String priceid = "";

    /*
     *开通的价格类型名称
     */
    public String pricename = "";


    /*
     *是否已付款  0:未付款  1:已付款
     */
    public int ispay;

    /*
     *终端用户的qq
     */
    public String qq = "";

    /*
     *终端用户的微信
     */
    public String wx = "";

    /*
     * 终端用户的支付宝
     */
    public String alipay = "";

    /*
     * 终端用户的手机号
     */
    public String tel = "";

    /*
     * 终端用户的邮箱
     */
    public String email = "";

    /*
     * 注册时间
     */
    public String regtime = "";

    /*
     *开通状态  0:未开通  1:已开通
     */
    public int openstate;

    /*
     *激活状态 0:未激活  1:已激活
     */
    public int activestate;
}
