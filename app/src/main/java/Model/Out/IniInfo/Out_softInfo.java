package Model.Out.IniInfo;

/*软件信息*/
public class Out_softInfo {
    /*软件名称*/
    public String softname = "";

    /**
     * 软件咨询官网
     */
    public String consultwebsite = "";

    /**
     * 软件咨询qq
     */
    public String consultqq = "";

    /**
     * 软件咨询微信
     */
    public String consultwx = "";

    /**
     * 软件咨询电话
     */
    public String consulttel = "";


    /*软件logo下载地址*/
    public String logourl = "";

    /*
     *开通测试卡密或账号：N天内只能开通测试号的总数量
     */
    public int opentestcount;
    /*
     /*开通测试卡密或账号：天数
     */
    public int opentestday;

    /*公告*/
    public String notice = "";
    /*软件基础数据*/
    public String basedata = "";

    /*最新版本号*/
    public String newversionnum = "";

    /*软件下载的网盘地址*/
    public String networkdiskurl = "";

    /*网盘提取码*/
    public String diskpwd = "";

    /*是否强制更新  0：不强制 1:强制*/
    public int isforceupd;

}
