package Model.In;

/**修改订单标记内容入参*/
public class In_UpdOrderFlagArgs extends In_DataBaseArgs {

    /** 必填,订单号*/
    public String orderid;
    /**
     * 必填,订单标识内容(使用场景：比如终端客户已成功付完款，并且已发货完毕，那么可以将此值修改，说明此订单已发货)
     * 字符长度不能大于500
     * */
    public  String orderflag;
}
