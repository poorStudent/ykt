package Help;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import Model.ApiRequestArgs;
import Model.BusinessType;
import Model.EncryptType;
import Model.In.In_IniSoftInfoArgs;
import Model.In.In_SearchOrderArgs;
import Model.Out.IniInfo.Out_iniGoodsInfo;
import Model.Out.IniInfo.Out_iniSoftInfo;
import Model.Result;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*网络验证帮助类*/
public class NetworkVerHelp {

    //瑞科网络验证：https://www.rukeyz.com
    /*平台类型  1:网络验证平台  2:自动发货平台*/
    static int platformtypeid;

    /*网络验证服务器地址*/
    static String ApiAddress= "http://api.ruikeyz.com/NetVer/webapi";


    /*订单查询地址*/
    static String SearchOrderAddress;

    /*
     * 加密的key
     * 注意：如果软件设置了DES加密通讯那么此值必填(此参数是在软件列表里面进行设置)
     * */
    static String encryptKey;

    /*
     * 签名盐(此参数是在软件列表里面进行设置)
     * 注意：如果软件设置了加密通讯，那么此值必填
     * */
    static String signSalt;

    /*请求接口时，业务数据data参数加密码通讯类型(此参数是在软件列表里面进行设置)*/
    static int encrypttypeid;

    /*商品Code*/
    static String goodscode;

    /*
     * 个人中心里面可以查看得到。代理商的话，那么在：代理管理--》代理商列表，可以查看得到
     * 注意：如果是作者自己就填写自己的platformUserCode,如果是代理商的，得填写代理商的Code
     * */
    static String platformUserCode;

    /*初始化软件Key值(通过软件初始化接口【iniSoftInfo】获取，除了"软件初始化接口"不需要填写，其它接口必填写)**/
    static String inisoftkey = "";

    /*软件初始化返回来的业务数据**/
    public static Out_iniSoftInfo iniSoftInfoData;

    /*商品初始化返回来的业务数据**/
    public static Out_iniGoodsInfo iniGoodsInfoData;

    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 获取设备的id
     * @return
     */
    static String getDeviceId(Context context){
        TelephonyManager  telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        if (deviceId==null){
            //android.provider.Settings;
            deviceId= Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    /*获取机器码*/
    public static String GetMacCode(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String tmDevice, tmSerial, tmPhone, androidId;
            tmDevice = "" + getDeviceId(context);
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            String uniqueId = deviceUuid.toString().replace("-", "");
            return uniqueId;
        } catch (Exception ex) {
            return "";
        }
        //return byte2HexStr((tmDevice + tmSerial + androidId).getBytes("utf-8"));
    }

    /*获取时间戳*/
    public static Long GetTimeStamp() {
        //Long tsLong = System.currentTimeMillis() / 1000;
        //return tsLong;
        return System.currentTimeMillis();
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param b byte数组
     * @return String
     */
    static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            //sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    /**
     * 十六进制字符串转换成bytes
     */
    static byte[] hexStr2Bytes(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }



        return digest;
    }

    /**
     * DES的ECB方式解密
     */
    public static String DesDecrypt(String message, String key) {
        try {
            byte[] bytesrc = hexStr2Bytes(message);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            //IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] retByte = cipher.doFinal(bytesrc);


            return new String(retByte);
        } catch (Exception ex) {
            return "";
        }


    }

    /**
     * DES的ECB方式加密
     */
    static String DesEncrypt(String message, String key) {

        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            //IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(message.getBytes("UTF-8"));
            return byte2HexStr(bytes);
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 解密Data数据(如果软件通讯设置是不需要加密码，则此不需要解密)
     */
    public static final <T> T DecryptData(String data, Class<T> clazz) {
        if (isEmpty(data) == false) {
            String dataTemp = data;
            if (encrypttypeid != EncryptType.NoEncrypt) {
                if (isEmpty(encryptKey)) {
                    return null;
                }
                switch (encrypttypeid) {
                    case EncryptType.NoEncrypt:
                        break;
                    case EncryptType.DES:
                        dataTemp = DesDecrypt(data, encryptKey);
                        if (dataTemp == "") {
                            return null;
                        }
                        break;
                }
            }
            try {
                return JSON.parseObject(dataTemp, clazz);
            } catch (Exception ex) {
                return null;
            }
        } else {
            return null;
        }
    }


    static boolean requestIsOk(String address){
        boolean result=false;
        try {
            Result resultTemp = new Result();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(1, TimeUnit.MINUTES);
            OkHttpClient client = builder.build();

            MediaType JsonMediaType = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
            RequestBody body =   RequestBody.create(JsonMediaType, "");
            Request request = new Request.Builder().url(address).get().build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseResult = response.body().string();
                resultTemp = JSON.parseObject(responseResult, Result.class);
                if(resultTemp.code==0){
                    if (address.indexOf("ruikeyz") != -1)
                    {
                        Log.d("rkyz", "requestIsOk: ");
                    }
                    else if (address.indexOf("157") != -1)
                    {
                        Log.d("rkyz", "157_OK: ");
                    }
                    else if (address.indexOf("77") != -1)
                    {
                        Log.d("rkyz", "77_OK: ");
                    }
                    else if (address.indexOf("233") != -1)
                    {
                        Log.d("rkyz", "233_OK: ");
                    }
                    else if (address.indexOf("12") != -1)
                    {
                        Log.d("rkyz", "12_OK: ");
                    }
                    result = true;
                }
            }
        } catch (Exception e) {
        }
        return  result;
    }

    /**
     * 递归创建目录
     *
     * @param path
     * @return
     */
    public static boolean mkDir(String path) {
        boolean isFinished = false;
        try {
            File f = new File(path);
            if (f.isDirectory()) {
                return true;
            }
            File parent = f.getParentFile();
            if (!parent.exists()) {
                mkDir(parent.getAbsolutePath());
            }
            isFinished = f.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isFinished;
    }

     static StringBuilder readFile(String filePath) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            reader.close();

        } catch (IOException e) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
         return fileContent;
    }

     static void write(String path, String content, String encoding) {
        BufferedWriter writer=null;
        try{
            File file = new File(path);
            file.delete();
            file.createNewFile();
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), encoding));
            writer.write(content);
            writer.close();
        }catch (Exception e){
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }
    }

     static int getGapMinutes(String startDate, String endDate) {
         int result=-1;
        long start = 0;
        long end = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            start = df.parse(startDate).getTime();
            end = df.parse(endDate).getTime();
            result = (int) ((end - start) / (1000 * 60));
        } catch (Exception e) {
        }
        return result;
    }

    static synchronized void  ini()
    {
        if(ApiAddress==""){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String path="/mnt/sdcard/rkyz/";
            String fileName=path+"address.txt";
            StringBuilder stringBuilder=readFile(fileName);
            if(stringBuilder!=null){
                String addressContent=  stringBuilder.toString();
                String[] ApiInfo= addressContent.split(",");
                if(ApiInfo.length>=2){
                    int diff=getGapMinutes(ApiInfo[1],simpleDateFormat.format(new Date(System.currentTimeMillis())));
                    if(diff<=1440){
                        if(requestIsOk(ApiInfo[0])){
                            ApiAddress = ApiInfo[0] + "NetVer/webapi";
                            return;
                        }
                    }
                }
            }




            ArrayList<String> ApiArray=new ArrayList<String>();
            ApiArray.add("http://api.ruikeyz.com/");
            ApiArray.add("http://128.14.75.157:8080/");
            ApiArray.add("http://107.148.160.77:8080/");
            ApiArray.add("http://107.148.190.233:8080/");
            ApiArray.add("http://154.38.227.12:8080/");


            for(int i=0;i<ApiArray.size();i++){
                String ApiAddressTemp=ApiArray.get(i);
                if(requestIsOk(ApiAddressTemp)){
                    if(mkDir(path)){
                        Date date = new Date(System.currentTimeMillis());
                        write(fileName,ApiAddressTemp+","+ simpleDateFormat.format(date),"utf-8");
                    }

                    ApiAddress = ApiAddressTemp + "NetVer/webapi";
                    break;
                }
            }
        }


    }


    /**
     * 第一步必须执行的：初始化软件信息
     */
    public static  Result iniSoftInfo(In_IniSoftInfoArgs dataArgs, int _platformtypeid, String _encryptKey, String _signSalt,
                                     int _encrypttypeid, String _goodscode, String _platformUserCode) {

        //ini();
        Log.e("ruikeyz.com", "当前版本号：v2.5 ");
        platformtypeid = _platformtypeid;
        if (ApiAddress.lastIndexOf("/") + 1 == ApiAddress.length()) {
            ApiAddress = ApiAddress.substring(0, ApiAddress.length());
        }
        SearchOrderAddress = ApiAddress.substring(0, ApiAddress.lastIndexOf("/")) + "/SearchOrder";
        encryptKey = _encryptKey;
        signSalt = _signSalt;
        encrypttypeid = _encrypttypeid;
        goodscode = _goodscode;
        platformUserCode = _platformUserCode;

        Result result = NetworkVerHelp.GetRequestResult(dataArgs, BusinessType.iniSoftInfo);//请求接口
        if (result.code == 0) {
            try {
                switch (platformtypeid) {
                    case 1://网络验证平台
                        iniSoftInfoData = DecryptData(result.data, Out_iniSoftInfo.class);
                        inisoftkey = iniSoftInfoData.inisoftkey;//初始化完软件后，把此Key保留下来，其它接口此Key必填写的
                        break;
//                    case 2://自动发货平台
//                        iniGoodsInfoData = DecryptData(result.data, Out_iniGoodsInfo.class);
//                        inisoftkey = iniGoodsInfoData.inisoftkey;//商品初始化完后，把此Key保留下来，其它接口此Key必填写的
//                        break;
                }
            } catch (Exception ex) {
                iniSoftInfoData = null;
                iniGoodsInfoData = null;
            }
        }
        return result;
    }

    /**
     * 请求接口获取接口返回的数据
     *
     * @dataArgs 提交的业务数据
     * @BusinessType 业务类型
     */
    public static synchronized Result GetRequestResult(Object dataArgs, int businessType) {
        Result result = new Result();
        result.code = -999;
        result.msg="服务器返回失败";
        String data = "";
        String sign = "";
        long TimeStamp = GetTimeStamp();
        try {
            data = JSON.toJSONString(dataArgs);
        } catch (Exception e) {
            result.code = -999;
            result.msg = "序列化失败";
            return result;
        }
        if (encrypttypeid != EncryptType.NoEncrypt) {
            //如果软件设置了加密码通讯
            if (isEmpty(signSalt)) {
                result.code = -999;
                result.msg = "签名盐不能为空！";
                return result;
            }
            if (isEmpty(encryptKey)) {
                result.code = -999;
                result.msg = "加密key不能为空！";
                return result;
            }

            switch (encrypttypeid) {
                case EncryptType.NoEncrypt:
                    break;
                case EncryptType.DES:
                    data = DesEncrypt(data, encryptKey);
                    if (data == "") {
                        result.code = -999;
                        result.msg = "des加密出错";
                        return result;
                    }
                    break;
            }

            //注意加密顺序不能变:业务类型+加密类型+平台用户Code+商品Code+inisoftkey+时间戳+业务数据+签名盐+平台类型
            String signData = String.valueOf(businessType) + String.valueOf(encrypttypeid) + platformUserCode +
                    goodscode + inisoftkey + String.valueOf(TimeStamp) + data + signSalt+String.valueOf(platformtypeid);
            sign = Md5Help.md5Sum(signData).trim().toLowerCase();
        }
        ApiRequestArgs ApiArgs = new ApiRequestArgs();
        ApiArgs.businessid = businessType;//必填
        ApiArgs.platformusercode = platformUserCode;//必填
        ApiArgs.goodscode = goodscode;//必填
        ApiArgs.inisoftkey = inisoftkey;//初始化软件Key值(通过软件初始化接口【iniSoftInfo】获取，除了"软件初始化接口"不需要填写，其它接口必填写)
        ApiArgs.timestamp = TimeStamp;//必填
        ApiArgs.data = data;//必填
        ApiArgs.encrypttypeid = encrypttypeid;//必填
        ApiArgs.sign = sign;//登录成功后，并且软件设置了加密码通讯，那么sign是必填项，否则可以为空
        ApiArgs.platformtypeid = platformtypeid;//必填
        //序列化请求接口的json数据
        String Postdata = JSON.toJSONString(ApiArgs);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();//创建OkHttpClient对象。
        //OkHttpClient client =  new OkHttpClient();//创建OkHttpClient对象。

        MediaType JsonMediaType = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        RequestBody body = RequestBody.create(JsonMediaType, Postdata);

        int ReqNum=5;
        int ReqLogOut=0;
        int SleepReqTime=1;
        Response response =null;
       for(int i=0;i<ReqNum;i++){
           result= getReqResult(ApiAddress,client,body);
            if(result.code==-999){
                result= getReqResult("http://api2.ruikeyz.com/NetVer/webapi",client,body);
            }
           if (result.code==-999){
               result= getReqResult("http://api3.ruikeyz.com/NetVer/webapi",client,body);
           }
            if(result.code!=-999){
                break;
            }
           try {
               Thread.sleep(1000*SleepReqTime);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }




        return result;
    }

    public  static  synchronized Result getReqResult(String reqAddress,OkHttpClient client,RequestBody body ){
        Result result = new Result();
        result.code = -999;
        result.msg="服务器返回失败";
        try {
            Request request = new Request.Builder()
                    .url(reqAddress)
                    .post(body)
                    .build();
            Response response =client.newCall(request).execute();
            if (response.isSuccessful()) {
                String  responseResult = response.body().string();
                if(responseResult!=""){
                    result = JSON.parseObject(responseResult, Result.class);
                }
            }
        }catch (Exception ex){
        }

        return  result;
    }

    /**订单查询*/
    public static Result SearchOrder(String orderid,String requestflag) {
        Result result = new Result();
        if(iniGoodsInfoData==null)
        {
            result.code = -999;
            result.msg = "软件尚未初始化";
            return result;
        }

        In_SearchOrderArgs ApiArgs = new In_SearchOrderArgs();
        ApiArgs.encrypttypeid = encrypttypeid;//必填
        ApiArgs.goodscode = goodscode;//必填
        ApiArgs.inisoftkey = inisoftkey;//必填
        ApiArgs.orderid = orderid;//必填
        ApiArgs.platformtypeid = platformtypeid;//必填
        ApiArgs.platformusercode = platformUserCode;//必填
        ApiArgs.requestflag = requestflag;//必填

        //序列化请求接口的json数据
        String Postdata = JSON.toJSONString(ApiArgs);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(3, TimeUnit.MINUTES);
        OkHttpClient client = builder.build();//创建OkHttpClient对象。
        //OkHttpClient client =  new OkHttpClient();//创建OkHttpClient对象。

        MediaType JsonMediaType = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
        RequestBody body = RequestBody.create(JsonMediaType, Postdata);
        Request request = new Request.Builder()
                .url(SearchOrderAddress)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseResult = response.body().string();
                if (responseResult.contains("servertimestamp") == false) {
                    result.code = -999;
                    result.msg = "服务器访问失败";
                    return result;
                }
                result = JSON.parseObject(responseResult, Result.class);
            }
        } catch (Exception e) {
            result.code = -999;
            result.msg = "出错：" + e.getMessage();
            return result;
        }
        return result;
    }

}

