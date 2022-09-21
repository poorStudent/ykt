package com.vms.ykt.RuiKey.Model.Out.IniInfo;

import java.util.ArrayList;

import com.vms.ykt.RuiKey.Model.Out.Out_DataBaseArgs;


/*软件初始化的data数据*/
public class Out_iniSoftInfo extends Out_DataBaseArgs {

    /*初始化软件Key值(通过软件初始化接口【iniSoftInfo】获取)**/
    public String inisoftkey="";

    /*软件信息*/
    public Out_softInfo softInfo;

    /*软件价格列表*/
    public ArrayList<Out_softPrice> softpricelist;
}
