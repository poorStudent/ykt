package Model.Out.HeartBeat;


import Model.Out.Out_DataBaseArgs;

/*心跳出参*/
public class Out_heartBeat extends Out_DataBaseArgs {
    /*心跳Key,每次心跳此值都会变*/
    public String heartbeatkey="";

    /**账号或卡密到期时间*/
    public String endtime= "";

    /**账号或卡密剩余点数*/
    public int surpluspointvalue;
}
