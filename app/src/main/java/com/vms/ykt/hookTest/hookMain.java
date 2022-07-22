package com.vms.ykt.hookTest;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.Logger;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class hookMain implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    private static String TAG="hookmain";

    final String pkg="me.weishu.exp";

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        XposedBridge.log("========================initZygote===========================s");

        XposedBridge.log(startupParam.modulePath);
        Method setrAcces=Method.class.getMethod("setAccessible",boolean.class);
        Method getDeclaMth=Class.class.getMethod("getDeclaredMethod", String.class, Class[].class);
       // dbg("de.robv.android.xposed.IXposedHookLoadPackage$Wrapper", XposedBridge.class.getClassLoader());
        XposedBridge.log("========================initZygote===========================e");
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals(pkg)) {
            hookTool.ProcName=lpparam.processName;
            hookTool.mOtherClassLoader=lpparam.classLoader;
            if (lpparam.isFirstApplication) {
                XposedBridge.log("yes");
            }

            hookTool.log("========================handleLoadPackage===========================s");
            hookTool.log(lpparam.appInfo.processName);
            hookTool.log(lpparam.processName);
            hookTool.log("======================handleLoadPackage=============================e");
        }

    }


    private void hookTest(XC_LoadPackage.LoadPackageParam loadPackageParam) {

    }


}
