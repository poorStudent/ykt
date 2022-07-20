package com.vms.ykt.Util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CommonUtil
{
    public static final int DELAY = 800;
    private static long lastClickTime;

    public static String byte2hex(final byte[] array) {
        final StringBuilder sb = new StringBuilder();
        for (int n = 0; array != null && n < array.length; ++n) {
            final String hexString = Integer.toHexString(array[n] & 0xFF);
            if (hexString.length() == 1) {
                sb.append('0');
            }
            sb.append(hexString);
        }
        return sb.toString().toUpperCase();
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


    @Nullable
    private String getCurrentProcessName(final Context context) {
        final int myPid = Process.myPid();
        @SuppressLint("WrongConstant") final ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
        if (activityManager == null) {
            return null;
        }
        for (final ActivityManager.RunningAppProcessInfo activityManager$RunningAppProcessInfo : activityManager.getRunningAppProcesses()) {
            if (activityManager$RunningAppProcessInfo.pid == myPid) {
                return activityManager$RunningAppProcessInfo.processName;
            }
        }
        return null;
    }


    public static String getMd5(String string) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            return "";
        }
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

    public static PackageInfo getPackageInfo(Context BaseApplication) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = BaseApplication.getPackageManager().getPackageInfo(BaseApplication.getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
        }
        return packageInfo;
    }

    public static String getSecretKey(String string, final String str,String UserId,String TcpCode) {
        final StringBuilder sb = new StringBuilder();
        sb.append(UserId);
        sb.append(str);
        sb.append(string);
        sb.append(TcpCode);
        string = sb.toString();
        return getMd5(string).toUpperCase();
    }

    public static String getThumbnailByType(final String anObject, String s) {
        if (!"ppt".equals(anObject) && !"pptx".equals(anObject)) {
            if (!"xls".equals(anObject) && !"xlsx".equals(anObject)) {
                if (!"doc".equals(anObject) && !"docx".equals(anObject)) {
                    if (!"mp3".equals(anObject) && !"m4a".equals(anObject)) {
                        if ("mp4".equals(anObject)) {
                            s = "file:///android_asset/Images/mp4.png";
                        }
                        else if ("zip".equals(anObject)) {
                            s = "file:///android_asset/Images/zip.png";
                        }
                        else if ("pdf".equals(anObject)) {
                            s = "file:///android_asset/Images/pdf.png";
                        }
                    }
                    else {
                        s = "file:///android_asset/Images/mp3.png";
                    }
                }
                else {
                    s = "file:///android_asset/Images/doc.png";
                }
            }
            else {
                s = "file:///android_asset/Images/xls.png";
            }
        }
        else {
            s = "file:///android_asset/Images/ppt.png";
        }
        return s;
    }

    public static String getVersion() {
        return "2.8.43";
    }

    public static int getWindowDpi(final Context context) {
        new DisplayMetrics();
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static int getWindowHeight(final Context referent) {
        final WeakReference<Context> weakReference = new WeakReference<>(referent);
        if (weakReference.get() == null) {
            return 0;
        }
        return weakReference.get().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getWindowWidth(final Context referent) {
        final WeakReference<Context> weakReference = new WeakReference<>(referent);
        if (weakReference.get() == null) {
            return 0;
        }
        return weakReference.get().getResources().getDisplayMetrics().widthPixels;
    }

    public static byte[] hamcSha1(byte[] doFinal, final byte[] key) {
        try {
            final SecretKeySpec key2 = new SecretKeySpec(key, "HmacSHA1");
            final Mac instance = Mac.getInstance("HmacSHA1");
            instance.init(key2);
            doFinal = instance.doFinal(doFinal);
            return doFinal;
        }
        catch (InvalidKeyException ex) {
            ex.printStackTrace();
        }
        catch (NoSuchAlgorithmException ex2) {
            ex2.printStackTrace();
        }
        return null;
    }

    public static boolean isAppOnForeground(final Application BaseApplication) {
        @SuppressLint("WrongConstant") final List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager)BaseApplication.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (final ActivityManager.RunningAppProcessInfo activityManagerRunningAppProcessInfo : runningAppProcesses) {
                if (activityManagerRunningAppProcessInfo.processName.equals(BaseApplication.getPackageName()) && activityManagerRunningAppProcessInfo.importance == 100) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isMatcherFinded(final CharSequence input) {
        return Pattern.compile("^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z~!@#$%^&*()_+`\\-={}:\";'<>?,.]+$)(?![a-z0-9]+$)(?![a-z~!@#$%^&*()_+`\\-={}:\";'<>?,.]+$)(?![0-9W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9~!@#$%^&*()_+`\\-={}:\";'<>?,.]{8,32}$").matcher(input).find();
    }

    public static boolean isNotFastClick() {
        final long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(currentTimeMillis - CommonUtil.lastClickTime) > 800L) {
            CommonUtil.lastClickTime = currentTimeMillis;
            return true;
        }
        return false;
    }

    public static boolean isServiceRunning(final Context context, final String anObject) {
        if (TextUtils.isEmpty((CharSequence)anObject)) {
            return false;
        }
        @SuppressLint("WrongConstant") final ArrayList list = (ArrayList) ((ActivityManager)context.getSystemService("activity")).getRunningServices(30);
        for (int i = 0; i < list.size(); ++i) {
            if (((ActivityManager.RunningServiceInfo)list.get(i)).service.getClassName().equals(anObject)) {
                return true;
            }
        }
        return false;
    }

    public static Boolean isUploadSizeOut(final long n) {
        return n / 1000L / 1000L > 300L;
    }

    public static void setMargins(final View view, final int n, final int n2, final int n3, final int n4) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams)view.getLayoutParams()).setMargins(n, n2, n3, n4);
            view.requestLayout();
        }
    }
}
