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
import android.util.DisplayMetrics;
import android.util.Log;

import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.AttributedCharacterIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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

    public static String a(String stuid, String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(stuid);
        stringBuilder.append(str2);
        stringBuilder.append(str);
        stringBuilder.append("123456789");
        str = stringBuilder.toString();
        return md5(str).toUpperCase();
    }



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
        String stringBuilder3 = StringUtils.getMd5(systemModel) + systemVersion;
        stringBuilder2.append(StringUtils.getMd5(stringBuilder3));
        stringBuilder2.append(verion);
        stringBuilder.append(StringUtils.getMd5(stringBuilder2.toString()));
        stringBuilder.append(str);
        return StringUtils.getMd5(stringBuilder.toString());
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



    public native static String ck(Context context);

    public native static String thread();

    public native static String sk(String id,String t, String cd);

    public native static String sm(String str,String t);

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


    public native String stringFromJNI();

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

}
