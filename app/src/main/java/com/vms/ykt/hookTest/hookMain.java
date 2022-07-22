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
    private static String TAG = "hookmain";

    final String pkg = "com.zjy.ykt";

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        hookTool.log("========================initZygote===========================s");
        hookTool.log(startupParam.modulePath);
        hookTool.log("========================initZygote===========================e");
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals(pkg)) {
            hookTool.ProcName = lpparam.processName;
            hookTool.mOtherClassLoader = lpparam.classLoader;
            if (lpparam.isFirstApplication) {
                hookTool.log("yes");
            }

            hookTool.log("========================handleLoadPackage===========================s");
            hookTool.log(lpparam.appInfo.processName);
            hookTool.log(hookTool.ProcName);
            hookTool.log(hookTool.mClassLoader.getClass());
            hookTool.log(hookTool.mClassLoader.getClass().getCanonicalName());
            hookTool.log(hookTool.mClassLoader.getClass().getName());
            hookTool.log(hookTool.mClassLoader.getClass().getSimpleName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                hookTool.log(hookTool.mClassLoader.getClass().getTypeName());
            }
            hookTool.log(hookTool.mClassLoader.getClass().getSuperclass());
            hookTool.log(hookTool.mClassLoader.getParent().getClass());
            hookTool.log("======================handleLoadPackage=============================e");
        }

    }


    private void hookTest(XC_LoadPackage.LoadPackageParam loadPackageParam) {

    }


}
