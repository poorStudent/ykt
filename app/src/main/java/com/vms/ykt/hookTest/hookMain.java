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

import com.vms.ykt.Util.StringUtils;

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
        logtag("========================initZygote===========================");
        hookTool.log(startupParam.modulePath);
        logtag("========================initZygote===========================");
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        logtag("========================handleLoadPackage===========================");
        if (lpparam.packageName.equals(pkg)) {
            hookTool.ProcName = lpparam.processName;
            hookTool.mOtherClassLoader = lpparam.classLoader;
            addClassLoader(mOtherClassLoader);



            if (lpparam.isFirstApplication) {
                hookTool.log("yes");
            }

            hookTool.log(hookTool.mOtherClassLoader.getClass().getName());
            hookTool.log(hookTool.mOtherClassLoader.getClass().getSuperclass().getName());

            Context vContext = hookTool.getCurrentApplication();
            if (vContext != null) {
                hookTool.log(vContext);
                hookTool.log(vContext.getClassLoader());
                hookTool.log(getCurProcessName(vContext));
            }
            hookTest(lpparam);


        }
        logtag("======================handleLoadPackage=============================");
    }


    private void hookTest(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XposedHelpers.findAndHookMethod("com.stub.StubApp", loadPackageParam.classLoader, "attachBaseContext", Context.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                hookTool.mOtherContext = (Context) param.args[0];
                hookTool.mOtherClassLoader = hookTool.mOtherContext.getClassLoader();
                hookTool.addClassLoader(hookTool.mOtherClassLoader);
                hookTool.log("com.stub.StubApp",mOtherClassLoader);
                hookTool.log("com.stub.StubApp",mOtherClassLoader);
                super.beforeHookedMethod(param);

            }
        });
        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Context vContext = (Context) param.args[0];
                ClassLoader vClassLoader = vContext.getClassLoader();
                addClassLoader(vClassLoader);
                hookTool.log("Application.class",vContext);
                hookTool.log("Application.class",vClassLoader);
                super.beforeHookedMethod(param);
            }
        });
        XposedHelpers.findAndHookMethod(ContextWrapper.class, "attachBaseContext", Context.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Context vContext = (Context) param.args[0];
                ClassLoader vClassLoader = vContext.getClassLoader();
                addClassLoader(vClassLoader);
                hookTool.log("ContextWrapper.class",vContext);
                hookTool.log("ContextWrapper.class",vClassLoader);

                super.beforeHookedMethod(param);
            }
        });

        hookGetSecretMd5(mOtherClassLoader);
    }
    public static void hookGetSecretMd5(ClassLoader classLoader){
        String cls="com.zjy.libraryframework.utils.";

        final Class<?> clas = findClass(cls+"SecretUtil");

        if (clas != null) {
            log("getSecretMd5",clas.getClassLoader());
            XposedHelpers.findAndHookMethod(clas, "getSecretMd5", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    hookTool.log(clas.getName()+param.method.getName(),param.args[0].toString());
                    super.beforeHookedMethod(param);
                }
            });
        }
        final Class<?> clas2 = findClass(cls+"StringUtils");
        if (clas2 != null) {
            log("getMd5",clas.getClassLoader());
            XposedHelpers.findAndHookMethod(clas2, "getMd5", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    hookTool.log(clas.getName()+param.method.getName(),param.args[0].toString());
                    super.beforeHookedMethod(param);
                }
            });
        }
    }
}
