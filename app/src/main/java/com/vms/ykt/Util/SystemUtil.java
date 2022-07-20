package com.vms.ykt.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

public class SystemUtil
{
    private static String localTag = "eth0";
    private static String[] platforms;
    private static String wifiTag = "wlan0";

    static {
        SystemUtil.platforms = new String[] { "http://pv.sohu.com/cityjson", "http://pv.sohu.com/cityjson?ie=utf-8" };
    }

    public static String byte2hex(final byte[] array) {
        final StringBuffer obj = new StringBuffer(array.length);
        for (int length = array.length, i = 0; i < length; ++i) {
            final String hexString = Integer.toHexString(array[i] & 0xFF);
            if (hexString.length() == 1) {
                obj.append("0");
                obj.append(hexString);
            }
            else {
                obj.append(hexString);
            }
        }
        return String.valueOf(obj);
    }

    public static String getInNetIp(final Context context) {
        @SuppressLint("WrongConstant") final WifiManager wifiManager = (WifiManager)context.getSystemService("wifi");
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        return intToIp(wifiManager.getConnectionInfo().getIpAddress());
    }

    @SuppressLint("WrongConstant")
    public static String getLocalIpAddress(final Context context) {
        @SuppressLint("WrongConstant") final NetworkInfo activeNetworkInfo = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == 0) {
                try {
                    final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                    while (networkInterfaces.hasMoreElements()) {
                        final Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                        while (inetAddresses.hasMoreElements()) {
                            final InetAddress inetAddress = inetAddresses.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                    return null;
                }
                catch (SocketException ex) {
                    ex.printStackTrace();
                    return null;
                }
            }
            if (activeNetworkInfo.getType() == 1) {
                return intIP2StringIP(((WifiManager)context.getSystemService("wifi")).getConnectionInfo().getIpAddress());
            }
        }
        return null;
    }

    public static String getLocalMacAddressFromIp(final Context context) {
        final String s = "";
        String string;
        try {
            final StringBuilder sb = new StringBuilder();
            final byte[] hardwareAddress = NetworkInterface.getByInetAddress(InetAddress.getByName(getLocalIpAddress(context))).getHardwareAddress();
            for (int length = hardwareAddress.length, i = 0; i < length; ++i) {
                sb.append(String.format("%02X:", hardwareAddress[i]));
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            string = sb.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            string = s;
        }
        return string;
    }

    public static String getLocalMacAddressFromIp(String host) {

        try {
            final StringBuilder sb = new StringBuilder();
            final byte[] hardwareAddress = NetworkInterface.getByInetAddress(InetAddress.getByName(host)).getHardwareAddress();
            for (int length = hardwareAddress.length, i = 0; i < length; ++i) {
                sb.append(String.format("%02X:", hardwareAddress[i]));
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString();

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static String getMacAddr(final Context context) {
        @SuppressLint("WrongConstant") final WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService("wifi");
        final String s = null;
        String localMacAddressFromIp = null;
        final String s2 = null;
        if (wifiManager != null) {
            String localMacAddressFromIp2;
            if (wifiManager.isWifiEnabled()) {
                final List scanResults = wifiManager.getScanResults();
                final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                localMacAddressFromIp2 = s;
                if (scanResults != null) {
                    localMacAddressFromIp2 = s;
                    if (connectionInfo != null) {
                        int n = 0;
                        String bssid = s2;
                        while (true) {
                            localMacAddressFromIp2 = bssid;
                            if (n >= scanResults.size()) {
                                break;
                            }
                            final ScanResult scanResult = (ScanResult) scanResults.get(n);
                            if (connectionInfo.getBSSID().equals(scanResult.BSSID)) {
                                bssid = scanResult.BSSID;
                            }
                            ++n;
                        }
                    }
                }
            }
            else {
                localMacAddressFromIp2 = getLocalMacAddressFromIp(context);
            }
            if ((localMacAddressFromIp = localMacAddressFromIp2) == null) {
                localMacAddressFromIp = getLocalMacAddressFromIp(context);
            }
        }
        return localMacAddressFromIp;
    }

    public static String getOutNetIP(final Context context, final int n) {
        final String[] platforms = SystemUtil.platforms;
        if (n < platforms.length) {
            try {
                final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(platforms[n]).openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                if (httpURLConnection.getResponseCode() == 200) {
                    final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
                    final StringBuilder sb = new StringBuilder();
                    while (true) {
                        final String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line);
                    }
                    bufferedReader.close();
                    httpURLConnection.disconnect();

                    if (n == 0 || n == 1) {
                        return  JSONObject.parseObject(sb.substring(sb.indexOf("{"), sb.indexOf("}") + 1)).getString("cip");
                    }
                    if (n == 2) {
                        return  JSONObject.parseObject(sb.toString()).getString("ip");
                    }
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            return getOutNetIP(context, n + 1);
        }
        return getInNetIp(context);
    }

    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    public static String getSystemModel() {
        final StringBuilder sb = new StringBuilder();
        sb.append(Build.BRAND);
        sb.append(" ");
        sb.append(Build.MODEL);
        return sb.toString();
    }

    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String intIP2StringIP(final int n) {
        final StringBuilder sb = new StringBuilder();
        sb.append(n & 0xFF);
        sb.append(".");
        sb.append(n >> 8 & 0xFF);
        sb.append(".");
        sb.append(n >> 16 & 0xFF);
        sb.append(".");
        sb.append(n >> 24 & 0xFF);
        return sb.toString();
    }

    private static String intToIp(final int n) {
        final StringBuilder sb = new StringBuilder();
        sb.append(n & 0xFF);
        sb.append(".");
        sb.append(n >> 8 & 0xFF);
        sb.append(".");
        sb.append(n >> 16 & 0xFF);
        sb.append(".");
        sb.append(n >> 24 & 0xFF);
        return sb.toString();
    }
}