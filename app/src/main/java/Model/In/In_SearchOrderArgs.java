package Model.In;

/**订单查询入参*/
public class In_SearchOrderArgs {

    /**
     * 平台用户Code(必填)
     *说明：个人中心里面可以查看得到。代理商的话，那么在：代理管理--》代理商列表，可以查看得到
    */
    public String platformusercode;

    /*
     *请求标识，长度不能大于16个字符(必填)
     *接口返回数据的时候，会原本原样的把此值返回来。
     *注意：接口返回的这个值与您请求接口提交的时值如果不一样，说明被“破解者”截持了返回的数据，
     * 那么您软件可以强制关闭或不做任何处理
     *  */
    public  String requestflag;

    /**
     * 商品Code(必填)
    */
    public String goodscode;

    /**
     * 加密方式(必填)
    */
    public int encrypttypeid;


    /**
     *初始化软件Key值(通过软件初始化接口【iniSoftInfo】获取)
    */
    public String inisoftkey;

    /**
     * 订单号
    */
    public String orderid;

    /**
     *平台类型(必填) 1:网络验证平台  2:自动发货平台
    */
    public int platformtypeid;
}
