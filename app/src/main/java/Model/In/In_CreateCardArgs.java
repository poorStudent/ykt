package Model.In;

/*生成卡密入参*/
public class In_CreateCardArgs extends In_DataBaseArgs {
    /* 卡密前缀(1.只能填写数字或字母 2.长度不能大于4)，可选*/
    public String prefix = "";
    /*  qq(可选)*/
    public String qq = "";
    /* wx(可选)*/
    public String wx = "";
    /* 支付宝(可选)*/
    public String alipay = "";
    /* 电话(可选)*/
    public String tel = "";
    /*  邮箱(可选)*/
    public String email = "";
}
