package Model.Out.IniInfo;


import Model.Out.Out_DataBaseArgs;

/**初始化商品软件出参数*/
public class Out_iniGoodsInfo extends Out_DataBaseArgs {
   /**初始化商品软件Key值(通过软件初始化接口【iniSoftInfo】获取)*/
    public String inisoftkey;

    /**商品信息*/
    public Out_goodsInfo goodsinfo;
}
