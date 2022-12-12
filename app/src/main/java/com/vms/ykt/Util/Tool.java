package com.vms.ykt.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.R;

import java.io.*;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.AttributedCharacterIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Tool {

    public static String getStuProcessTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static String getCurrentData() {
        String parse;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            parse = simpleDateFormat.format(new Date());
        } catch (Exception unused) {
            parse = null;
        }
        return (parse);
    }

    public static long parseDataTime(String time) {
        Date parse;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            parse = simpleDateFormat.parse(time);
        } catch (Exception unused) {
            parse = null;
        }
        return (parse.getTime());
    }

    public static String parseDataTime(long time) {
        String parse;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            parse = simpleDateFormat.format(time);
        } catch (Exception unused) {
            parse = null;
        }
        return parse;
    }


    public static String getTime() {
        return String.valueOf(System.currentTimeMillis()).substring(0,10)+"000";
    }

    /**
     * 判断是否有网络
     ***/
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    public static void fw(File file, String dat) {
        if (!file.exists()) {
            //创建BufferedReader读取文件内容
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {

            //创建BufferedWriter对象并向文件写入内容
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            //向文件中写入内容
            bw.write(dat);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();

        }


    }

    public static String fr(File file) {

        StringBuilder vStringBuilder = new StringBuilder();
        if (!file.exists()) {
            //创建BufferedReader读取文件内容
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                vStringBuilder.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vStringBuilder.toString();
    }

    public final static String md5(String s) {
        //16进制下数字到字符的映射数组
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }


    public static String getDevice(String appVersion, String time) {
        String device = md5(getDEVICEModle());
        device = md5(device + "9");
        device = md5(device + appVersion);
        device = md5(device + time);
        return device;

    }


/**
    public static String[] getEmitDevice()  {

        long internetTime = Long.parseLong(Tool.getTime());
        //DateTimeFormatUtil.getInternetTime();//时间戳前10位+000

        if (internetTime == 0) {
            internetTime = System.currentTimeMillis();
            //13位时间戳
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(internetTime);//登录超时会用第一个时间戳
        stringBuilder.append("");
        String stringBuilder2 = stringBuilder.toString();
        //加密的请求头
        String device=getSecretMd5(stringBuilder2);

        return new String[]{internetTime+"",device};
    }

    public static String getSecretMd5(String str) {
        String systemModel = SystemUtil.getSystemModel();//手机名和厂家
        String systemVersion ="9";   //SystemUtil.getSystemVersion();//手机系统版本
        String verion = getVersion();//软件版本 写死了2.8.43
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        String stringBuilder3 = getMd5(systemModel) + systemVersion;
        stringBuilder2.append(getMd5(stringBuilder3));
        stringBuilder2.append(verion);
        stringBuilder.append(getMd5(stringBuilder2.toString()));
        stringBuilder.append(str);
        return getMd5(stringBuilder.toString());
        //标准md5加密
    }

    public static String dateToStamp() throws ParseException {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String format = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        Date parse;
        try {
            parse = simpleDateFormat.parse(format);
        }
        catch (Exception ex) {
            parse = null;
        }
        return String.valueOf(parse.getTime());
    }

    public static String a(String stuid, String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(stuid);
        stringBuilder.append(str2);
        stringBuilder.append(str);
        stringBuilder.append("123456789");
        str = stringBuilder.toString();
        return md5(str).toUpperCase();
    }

    public static String getSecretKey(String UserId, final String time,String cellId) {
        final StringBuilder sb = new StringBuilder();
        sb.append(UserId);
        sb.append(cellId);
        sb.append(time);
        sb.append("123456789");
        String string = sb.toString();
        return getMd5(string).toUpperCase();
    }

    public static String getMd5(String string) {
        try {
            final byte[] digest = MessageDigest.getInstance("MD5").digest(string.getBytes());
            string = "";
            for (int length = digest.length, i = 0; i < length; ++i) {
                String str2;
                final String str = str2 = Integer.toHexString(digest[i] & 0xFF);
                if (str.length() == 1) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append("0");
                    sb.append(str);
                    str2 = sb.toString();
                }
                final StringBuilder sb2 = new StringBuilder();
                sb2.append(string);
                sb2.append(str2);
                string = sb2.toString();
            }
            return string;
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String getVersion() {
        return "2.8.43";
    }

    public static void getTcpCode(){
        new Thread(()->{
            String tcpIpAddress = "101.37.228.98";
            int tcpPort = 7788;
            try {
                // 服务器创建连接
                Socket socket = new Socket(tcpIpAddress,tcpPort);
                // 要发送给服务器的信息
                OutputStream os = socket.getOutputStream();
                os.write("getKey".getBytes());
                os.flush();

                socket.shutdownOutput();

                // 从服务器接收的信息
                byte[] b=new byte[1024];
                InputStream is = socket.getInputStream();
                int a=is.read(b);

                byte[] bArr2 = new byte[a];
                System.arraycopy(b, 0, bArr2, 0, a);
                System.out.println(new String(bArr2, StandardCharsets.UTF_8));

                is.close();
                os.close();
                os.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


    }

**/

    public native static String ck(Context context);

    public native static String thread();

    public native static String sk(String id,String t, String cd);

    public native static String sm(String str,String t);

    public native static String stringFormM(String str);

    /**
     * base64编码
     * @param buf 待编码字节
     * @return 结果
     */
    public static native String string2Base64(byte[] buf) throws UnsupportedEncodingException;

    /**
     * base64解码
     * @param hexString 待解码字符串
     * @return 结果
     */
    public static native byte[] base642Byte(String hexString);

    /**
     * AES加密
     * @param src 待加密字符串
     * @return 结果
     */
    public static native String encrypt(String src) throws UnsupportedEncodingException,
            IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException;

    /**
     * AES解密
     * @param encrypted 待解密串
     * @return 结果
     */
    public static native String decrypt(String encrypted) throws IOException,
            IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException;

    /**
     * 获取native 层密钥
     * @return 密钥
     */
    public static native String getAESKey();

    /**
     * 设置native层密钥
     * @param key 密钥
     */
    public static native void setAESKey(String key);


    public static byte[] compressByte(String param) throws IOException {
        if (param == null || param.length() == 0) {
            return null;
        }
        ByteArrayOutputStream byteOut = null;
        GZIPOutputStream gzipOut = null;
        byte[] outPut = null;
        try {
            // 开启数据输出流,关闭无效
            byteOut = new ByteArrayOutputStream();
            // 开启数据压缩流
            gzipOut = new GZIPOutputStream(byteOut);
            // 将字串转换成字节，然后按照ＵＴＦ－８的形式压缩
            gzipOut.write(param.getBytes("UTF-8"));
            // 压缩完毕
            gzipOut.finish();
            gzipOut.close();
            // 将压缩好的流转换到byte数组中去
            outPut = byteOut.toByteArray();
            byteOut.flush();
            byteOut.close();
        } finally {
            if (byteOut != null) {
                byteOut.close();
            }
        }
        return outPut;
    }

    public static String uncompressByte(byte[] param) throws IOException {

        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        GZIPInputStream gzip = null;
        byte[] b = null;
        try {
            // 创建输出流
            out = new ByteArrayOutputStream();
            // 创建输入流,并把传入的字串参数转码成ISO-8895-1
            in = new ByteArrayInputStream(param);
            // 创建压缩输入流，将大小默认为参数输入流大小
            gzip = new GZIPInputStream(in);
            // 创建byte数组用于接收解压后的流转化成byte数组
            byte[] byteArry = new byte[256];
            int n = -1;
            while ((n = gzip.read(byteArry)) != -1) {
                out.write(byteArry, 0, n);
            }
            // 转换数据
            b = out.toByteArray();
            out.flush();
        } finally {

            // 关闭压缩流资源
            if (out != null)
                out.close();
            if (gzip != null)
                gzip.close();
            if (in != null)
                in.close();
        }
        return new String(b, "UTF-8");
    }


    public static <T> List<T> parseJsonA(String jsons, String key, Class<T> clazz) {
        List<T> varObjectList = new ArrayList<>();
        JSONObject json = JSONObject.parseObject(jsons);
        JSONArray varJSONArray = json.getJSONArray(key);
        if (varJSONArray==null || varJSONArray.size()==0) return varObjectList;
        varObjectList = varJSONArray.toJavaList(clazz);
        return varObjectList;
    }

    public static String parseJsonS(String jsons, String key) {
        String varS = "";
        JSONObject json = JSONObject.parseObject(jsons);
        varS = json.getString(key);
        return varS;
    }

    public static JSONObject parseJsonO(String jsons, String key) {
        JSONObject varS =null;
        JSONObject json = JSONObject.parseObject(jsons);
        varS = json.getJSONObject(key);
        return varS;
    }

    public static <T> T parseJsonS(String jsons, String key, Class<T> clazz) {
        String js = "";
        JSONObject json = JSONObject.parseObject(jsons);
        js = json.getString(key);
        T varO = JSONObject.parseObject(js, clazz);
        return varO;
    }

    public static JSONArray parseJsonA(String jsons, String key) {
        JSONArray varArray = null;
        JSONObject json = JSONObject.parseObject(jsons);
        varArray = json.getJSONArray(key);
        return varArray;
    }



    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String URLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
     * 中文转unicode编码
     */
    public static String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    /*
     * unicode编码转中文
     */
    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int index = 0;
        String str = "";
        String charStr = "";
        final StringBuffer buffer = new StringBuffer();
        while (start < dataStr.length() && start != -1) {
            start = dataStr.indexOf("\\u", start);

            if (start != index && start != -1) {
                str = dataStr.substring(index, start);
            }
            if (start == -1 && index == 0) {
                str = dataStr.substring(0, dataStr.length());
                buffer.append(str);
            } else if (start == -1) {
                str = dataStr.substring(index, dataStr.length());
                buffer.append(str);
            }
            if (start != -1) {
                start += 2;
                charStr = dataStr.substring(start, start + 4);
                start += 4;
                index = start;
                char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
                buffer.append(str);
                buffer.append(Character.toString(letter));
            }
        }
        return buffer.toString();
    }

    public static void toast(Context applicationContext, String s) {
        Toast.makeText(applicationContext, s, Toast.LENGTH_SHORT).show();
    }

    public static void copyFileFromAssets(Context context, String assetsFilePath, String targetFileFullPath) {
        Log.d("Tag", "copyFileFromAssets ");
        InputStream assestsFileImputStream;
        try {
            assestsFileImputStream = context.getAssets().open(assetsFilePath);

            copyFile(assestsFileImputStream, targetFileFullPath);
        } catch (IOException e) {
            Log.d("Tag", "copyFileFromAssets " + "IOException-" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void copyFile(InputStream in, String targetPath) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(targetPath, "lacalData.txt"));
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while ((byteCount = in.read(buffer)) != -1) { // 循环从输入流读取
// buffer字节
                fos.write(buffer, 0, byteCount); // 将读取的输入流写入到输出流
            }
            fos.flush(); // 刷新缓冲区
            in.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    // 制造商 （Manufacturer）
    public static String getMANUFACTURER() {
        String manufacturer = android.os.Build.MANUFACTURER;
        return manufacturer;
    }

    //设备名 （Device）
    public static String getSystemDEVICE() {
        String device = android.os.Build.DEVICE;
        return device;
    }

    //
    public static String getDEVICEModle() {
        String device = getDeviceBrand() + " " + getSystemModel();
        return device;
    }

    //手机ua
    public static String getUA() {
        String userAgent = System.getProperty("http.agent");
        Log.d("getUA", "getUA: " + userAgent);
        return userAgent;
    }

    public static boolean isDestroy(Activity mActivity) {
        if (mActivity == null || mActivity.isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && mActivity.isDestroyed())) {
            return true;
        } else {
            return false;
        }
    }

    public static void waitTime(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    public static <T> T getRandomStr(T[] t) {
        Random rand = new Random();
        return t[rand.nextInt(t.length)];
    }

    public static <T> T getRandomStr(List<T> t) {
        Random rand = new Random();
        return t.get(rand.nextInt(t.size()));
    }

    public static void logi(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            Log.i(tag, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.i(tag, msg);
    }

    /*
     * 将十进制转换成IP地址
     */
    public static String num2ip(int ip) {
        int[] b = new int[4];
        b[0] = (ip >> 24) & 0xff;
        b[1] = (ip >> 16) & 0xff;
        b[2] = (ip >> 8) & 0xff;
        b[3] = ip & 0xff;
        // 拼接 IP
        String x = b[0] + "." + b[1] + "." + b[2] + "." + b[3];
        return x;
    }

    public static String getRandomIp() {

        // 指定 IP 范围
        int[][] range = {
                {607649792, 608174079}, // 36.56.0.0-36.63.255.255
                {1038614528, 1039007743}, // 61.232.0.0-61.237.255.255
                {1783627776, 1784676351}, // 106.80.0.0-106.95.255.255
                {2035023872, 2035154943}, // 121.76.0.0-121.77.255.255
                {2078801920, 2079064063}, // 123.232.0.0-123.235.255.255
                {-1950089216, -1948778497}, // 139.196.0.0-139.215.255.255
                {-1425539072, -1425014785}, // 171.8.0.0-171.15.255.255
                {-1236271104, -1235419137}, // 182.80.0.0-182.92.255.255
                {-770113536, -768606209}, // 210.25.0.0-210.47.255.255
                {-569376768, -564133889}, // 222.16.0.0-222.95.255.255
        };

        Random random = new Random();
        int index = random.nextInt(10);
        String ip = num2ip(range[index][0] + random.nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    public void s(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getImei();//返回IMEI
        String meid = tm.getMeid();//返回MEID
        String simSerialNumber = tm.getSimSerialNumber();//手机SIM卡唯一标识


    }

    public static String getIPAddress(Context context) {

        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                //调用方法将int转换为地址字符串
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {

        }
        return getRandomIp();
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    public static void tvShowBottom(TextView v) {
        int offset = v.getLineCount() * v.getLineHeight();
        if (offset > v.getHeight()) {
            v.scrollTo(0, offset - 3 * v.getLineHeight());
        }
    }

    //随机范围整数
    public static int getRandomInt(int p1, int p2) {
        Random vRandom = new Random();
        int vI = vRandom.nextInt(p2 - p1 + 1) + p1;
        return vI;
    }

    public static ArrayList<String> readLineFile(String filename, String name) {

        ArrayList<String> vArrayList = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            //创建BufferedReader读取文件内容
            try {
                file.mkdirs();
                file = new File(filename+name);
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {

            FileInputStream in = new FileInputStream(filename+name);

            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");

            BufferedReader bufReader = new BufferedReader(inReader);

            String line = null;

            int i = 1;

            while ((line = bufReader.readLine()) != null) {
                vArrayList.add(line);
                i++;

            }

            bufReader.close();

            inReader.close();

            in.close();

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("读取" + filename + "出错！");

        }

        return vArrayList;
    }

    public static File getSD() {
        return Environment.getExternalStorageDirectory();
    }

    public static File getSD(String type) {
        return Environment.getExternalStoragePublicDirectory(type);
    }

    public static String getSD_DownloadPath() {
        return getSD(DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    //直接传一个float的单位为dp的值，然后方法会返回单位为px的数，直接设置即可
    public static int dp2px(Context context,float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public void getAndroiodScreenProperty(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)


        Log.d("h_bl", "屏幕宽度（像素）：" + width);
        Log.d("h_bl", "屏幕高度（像素）：" + height);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("h_bl", "屏幕宽度（dp）：" + screenWidth);
        Log.d("h_bl", "屏幕高度（dp）：" + screenHeight);
    }

    //
    public static View creatDialog(Context mContext,int id){
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(id, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);

        setDeBugDialog.setCancelable(true);
        //创建AlertDiaLog
        setDeBugDialog.create();
        //AlertDiaLog显示
        final AlertDialog customAlert = setDeBugDialog.show();
        //设置AlertDiaLog宽高属性
        WindowManager.LayoutParams params = Objects.requireNonNull(customAlert.getWindow()).getAttributes();
        params.width = 950;
        params.height = 900;
        customAlert.getWindow().setAttributes(params);
        return dialogView;
    }


    public static final int MIN_LEN = 4;

    private static String bufferToHex(final byte[] magnitude) {
        final int length = magnitude.length;
        final String string = new BigInteger(1, magnitude).toString(16);
        final StringBuffer sb = new StringBuffer(32);
        for (int length2 = string.length(), i = 0; i < length * 2 - length2; ++i) {
            sb.append("0");
        }
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(sb.toString());
        sb2.append(string);
        return sb2.toString();
    }

    public static String concat(final String s, final String str) {
        return s.concat(str);
    }

    public static String convert(final String s) {
        return s.replaceAll("[^\\x00-\\xff]", "**");
    }

    public static boolean copyString(final String[] array, final String[] array2) {
        final int n = 0;
        if (array != null && array2 != null) {
            int n2 = 0;
            int n3;
            while (true) {
                n3 = n;
                if (n2 >= array2.length) {
                    break;
                }
                array2[n2] = "";
                ++n2;
            }
            while (n3 < array2.length && n3 < array.length) {
                array2[n3] = array[n3];
                ++n3;
            }
            return true;
        }
        return false;
    }

    public static String cutString(String string, final int n, final String str) {
        if (isEmpty(string)) {
            return "";
        }
        final char[] charArray = string.toCharArray();
        int n2 = 0;
        string = "";
        int n3;
        for (n3 = 0; n2 < charArray.length && n > n3; ++n2) {
            n3 += String.valueOf(charArray[n2]).getBytes().length;
            final StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append(charArray[n2]);
            string = sb.toString();
        }
        if (n != n3) {
            final String string2 = string;
            if (n != n3 - 1) {
                return string2;
            }
        }
        final StringBuilder sb2 = new StringBuilder();
        sb2.append(string);
        sb2.append(str);
        return sb2.toString();
    }

    public static String delString(String s, final String str, final String str2) {
        int n = -str.length();
        String s2 = s;
        int i;
        do {
            i = s2.indexOf(str, n + str.length());
            s = s2;
            if (i > 0) {
                final int index = s2.indexOf(str2, str2.length() + i);
                if (index > 0) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append(s2.substring(0, i));
                    sb.append(s2.substring(index + str2.length()));
                    s = sb.toString();
                }
                else {
                    s = s2.substring(0, i);
                }
            }
            n = i;
            s2 = s;
        } while (i > 0);
        return s;
    }

    public static String delUTF8Dom(final StringBuffer sb) {
        final int length = sb.length();
        int length2 = 4;
        if (length <= 4) {
            length2 = sb.length();
        }
        final int n = 0;
        final String substring = sb.substring(0, length2);
        int n2;
        if ((n2 = substring.indexOf(60)) < 0) {
            n2 = substring.indexOf(123);
        }
        int index;
        if ((index = n2) < 0) {
            index = substring.indexOf(91);
        }
        if (index > 0) {
            for (int i = n; i < index; ++i) {
                sb.setCharAt(i, ' ');
            }
        }
        return sb.toString();
    }

    public static String getContentMD5(final String s) {
        return getContentMD5(s.getBytes());
    }

    private static String getContentMD5(final byte[] input) {
        try {
            final MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(input, 0, input.length);
            return bufferToHex(instance.digest());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String getDateTime(final long date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(date));
    }


    public static String getTime(final long n) {
        final int i = (int)(n / 3600L);
        final int n2 = (int)(n / 60L);
        final int j = (int)(n % 60L);
        if (i > 0) {
            return String.format("%02d:%02d:%02d", i, n2, j);
        }
        if (n2 > 0) {
            return String.format("%02d:%02d", n2, j);
        }
        return String.format("%02d", j);
    }

    public static String getTimeCN(final long n) {
        final int i = (int)(n / 3600L);
        final int n2 = (int)(n / 60L);
        final int j = (int)(n % 60L);
        if (i > 0) {
            return String.format("%02d\u5c0f\u65f6%02d\u5206%02d\u79d2", i, n2, j);
        }
        if (n2 > 0) {
            return String.format("%02d\u5206%02d\u79d2", n2, j);
        }
        return String.format("%02d\u79d2", j);
    }

    public static String getTransforTime(final int n) {
        return new SimpleDateFormat("mm:ss").format(new Date(n));
    }

    public static String getUTF8Dom() {
        try {
            return new String(new byte[] { -17, -69, -65 }, "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String getUrlName(final String s) {
        if (isEmpty(s)) {
            return "";
        }
        final int index = s.indexOf("?");
        String substring = s;
        if (index > 0) {
            substring = s.substring(0, index);
        }
        int n;
        if ((n = substring.lastIndexOf("/")) < 0) {
            n = substring.indexOf("\\");
        }
        if (n < 0) {
            return substring;
        }
        return substring.substring(n + 1);
    }

    public static boolean isASCII(final String s) {
        if (isEmpty(s)) {
            return false;
        }
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) >= '\u0080' || s.charAt(i) < '\0') {
                return false;
            }
        }
        return true;
    }

    public static boolean isChinese(final String s) {
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if ('\u4e00' > char1 || char1 >= '\u9fa5') {
                return false;
            }
        }
        return true;
    }

    public static boolean isContains(final String s, final String s2) {
        try {
            return s.contains(s2);
        }
        catch (Exception ex) {
            return false;
        }
    }

    @SuppressLint({ "DefaultLocale" })
    public static boolean isContainsIgnore(final String s, final String s2) {
        try {
            return s.contains(s2) || s.contains(s2.toUpperCase());
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static boolean isEmpty(final EditText editText) {
        return TextUtils.isEmpty((CharSequence)editText.getText().toString().trim());
    }

    public static boolean isEmpty(final CharSequence charSequence) {
        return TextUtils.isEmpty(charSequence);
    }

    public static boolean isEqual(final String s, final String anObject) {
        try {
            return s.equals(anObject);
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static String join(final List<String> list, final String s) {
        if (list != null && list.size() != 0) {
            final StringBuffer sb = new StringBuffer();
            final Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next());
                sb.append(s);
            }
            sb.delete(sb.lastIndexOf(s), sb.length());
            return sb.toString();
        }
        return "";
    }

    public static boolean parseArrayInt(final int[] array, final String s, final String regex) {
        int n = 0;
        if (array != null && !isEmpty(s)) {
            final String[] split = s.split(regex);
            boolean b = false;
            while (n < array.length && n < split.length) {
                array[n] = Integer.parseInt(split[n]);
                ++n;
                b = true;
            }
            return b;
        }
        return false;
    }

    public static boolean parseArrayString(final String[] array, final String s, final String regex) {
        int n = 0;
        if (array != null && !isEmpty(s)) {
            final String[] split = s.split(regex);
            boolean b = false;
            while (n < array.length && n < split.length) {
                array[n] = split[n];
                ++n;
                b = true;
            }
            return b;
        }
        return false;
    }

    public static Date parseDate(final String source) {
        if (TextUtils.isEmpty((CharSequence)source)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat;
        if (source.length() == 10) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        else if (source.length() == 16) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
        else {
            if (source.length() != 19) {
                return null;
            }
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        try {
            return simpleDateFormat.parse(source);
        }
        catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static double parseDouble(final String s, final double n) {
        try {
            return Double.parseDouble(s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return n;
        }
    }

    public static Float parseFloat(final String s, final float f) {
        try {
            return Float.parseFloat(s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return f;
        }
    }

    public static int parseInt(final String s) {
        try {
            return Integer.parseInt(s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public static int parseInt(final String s, final int n) {
        try {
            return Integer.parseInt(s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return n;
        }
    }

    public static long parseLong(final String s, final long n) {
        try {
            return Long.parseLong(s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return n;
        }
    }

    public static String subString(final String s, final String str, final int n) {
        int index = -str.length();
        int n2 = 0;
        int n3;
        do {
            index = s.indexOf(str, index + str.length());
            if (index < 0) {
                break;
            }
            n3 = n2 + 1;
            if (n3 == n) {
                return s.substring(0, index);
            }
        } while (index > 0 && (n2 = n3) < n);
        return s;
    }

    public static String trimEnd(final String s) {
        return s.replaceAll("[\\s]*$", "");
    }

}
