package com.vms.ykt.hookTest;

import android.app.Application;
import android.app.Dialog;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.ContextWrapper;
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

public class hookMain extends hookTool implements IXposedHookLoadPackage, IXposedHookZygoteInit {
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
            addClassLoader(mOtherClassLoader);

            hookTool.log("========================handleLoadPackage===========================\n");

            if (lpparam.isFirstApplication) {
                hookTool.log("yes");
            }

            Context vContext=hookTool.getCurrentApplication();

            hookTool.log(hookTool.ProcName);
            hookTool.log(hookTool.mOtherClassLoader);
            hookTool.log(vContext);
            hookTool.log(vContext.getClassLoader());
            hookTool.log(getCurProcessName(vContext));

            hookTool.log(hookTool.mOtherClassLoader.getClass().getName());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                hookTool.log(hookTool.mOtherClassLoader.getClass().getTypeName());
            }
            hookTool.log(hookTool.mOtherClassLoader.getClass().getSuperclass().getName());

            hookTest(lpparam);
            hookTool.log("======================handleLoadPackage=============================\n");
        }

    }


    private void hookTest(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XposedHelpers.findAndHookMethod("com.stub.StubApp", loadPackageParam.classLoader, "attachBaseContext", Context.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                hookTool.mOtherContext=(Context)param.args[0];
                hookTool.mOtherClassLoader=hookTool.mOtherContext.getClassLoader();
                hookTool.addClassLoader(hookTool.mOtherClassLoader);
                super.beforeHookedMethod(param);

            }
        });
        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Context vContext=(Context)param.args[0];
                ClassLoader vClassLoader=vContext.getClassLoader();
                addClassLoader(vClassLoader);
                hookTool.log(vContext);
                hookTool.log(vClassLoader);
                super.beforeHookedMethod(param);
            }
        });
        XposedHelpers.findAndHookMethod(ContextWrapper.class, "attachBaseContext", Context.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Context vContext=(Context)param.args[0];
                ClassLoader vClassLoader=vContext.getClassLoader();
                addClassLoader(vClassLoader);
                hookTool.log(vContext);
                hookTool.log(vClassLoader);
                super.beforeHookedMethod(param);
            }
        });
        Class<?> clas = findClass("com.zjy.libraryframework.utils");
        if(clas!=null){
            log(clas.getClasses());
            XposedHelpers.findAndHookMethod(clas, "getSecretMd5", String.class,new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    log(param.args[0].toString());
                    PrintStack("");
                    super.beforeHookedMethod(param);
                }
            });
        }
    }

}
