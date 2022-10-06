package Model.In;
/*修改用户密码*/
public class In_UpdPwdArgs extends In_DataBaseArgs {
    /*账号(必填)*/
    public String username="";

    /*密码(必填)*/
    public String userpwd="";

    /*新密码*/
    public String newpwd="";
}
