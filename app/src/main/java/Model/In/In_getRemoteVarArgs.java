package Model.In;
/*获取远程变量入参*/
public class In_getRemoteVarArgs  extends In_DataBaseArgs {
    /*卡密或账号(必填)*/
    public String cardnumorusername= "";

    /*变量名称(可空) 如果为空的话，那么返回此软件的所有变量*/
    public String varname= "";

    /**登录令牌(必填)*/
    public String token= "";
}
