package Model.In;

public class In_DataBaseArgs {
    /*
    * 机器码(必填)
    * */
    public String maccode;

    /*
    *请求标识，长度不能大于16个字符(必填)
    *接口返回数据的时候，会原本原样的把此值返回来。
    *注意：接口返回的这个值与您请求接口提交的时值如果不一样，说明被“破解者”截持了返回的数据，
    * 那么您软件可以强制关闭或不做任何处理
    *  */
    public  String requestflag;

    /*
    * 时间戳(必填)
    * */
    public Long timestamp;
}
