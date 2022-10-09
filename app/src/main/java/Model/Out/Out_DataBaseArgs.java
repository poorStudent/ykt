package Model.Out;

public class Out_DataBaseArgs {
    /*
    *请求接口时提交上来的一个标识，会原本原样的把此值返回去。如果返回去的值与当前提交的值不一致，
    *说明接口返回的数据有可能被“破解者”截持，那么您的软件可以直接关闭或不做任何处理
    * */
    public String requestflag;

    /*服务器时间戳**/
    public Long servertimestamp;
}
