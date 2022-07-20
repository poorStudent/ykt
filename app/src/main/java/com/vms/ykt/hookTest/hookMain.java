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
    private static String ProcName="";
    final String pkg="me.weishu.exp";
    static StringBuilder hasDbg=new StringBuilder();
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
            ProcName=lpparam.processName;
            XposedBridge.log("========================handleLoadPackage===========================s");

            XposedBridge.log(lpparam.classLoader.toString());
            XposedBridge.log(lpparam.classLoader.getParent().toString());
            XposedBridge.log(lpparam.classLoader.getClass().getSimpleName());
            XposedBridge.log(lpparam.classLoader.getClass().getName());
            if (lpparam.isFirstApplication == true) {
                XposedBridge.log("yes");

            }

            XposedBridge.log(lpparam.appInfo.processName);
            XposedBridge.log(lpparam  .processName);
            XposedBridge.log("======================handleLoadPackage=============================e");

            // hookVpn(lpparam);
            //hookDialog(lpparam);
            //hookToast(lpparam);

        }
    }

    private void hookTest(XC_LoadPackage.LoadPackageParam loadPackageParam) {

    }

    private void hookToast(final XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod(Toast.class, "show", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                PrintStack(lpparam.packageName);
            }
        });
    }

    private void PrintStack(String packageName) {
        Object object = null;
        StringBuffer varStringBuffer = new StringBuffer();
        StringBuffer varStringBuffer1 = new StringBuffer();
        for (StackTraceElement varStackTraceElement : new Exception().getStackTrace()) {
            if (object == null) {
                if (varStackTraceElement.getMethodName().equals("show")) {
                    object = 0;
                    varStringBuffer.append(">>>>>>").append(packageName).append("-------").append("-show()");
                    XposedBridge.log(varStringBuffer.toString());
                    varStringBuffer1.append(varStackTraceElement);
                    XposedBridge.log(varStringBuffer1.toString());
                }
            }

        }
    }

    private void hookVpn(XC_LoadPackage.LoadPackageParam lpparam) {
        Object[] varO = new Object[2];
        varO[0] = XposedHelpers.findClass("java.net. NetworkInterface", lpparam.classLoader);
        try {
            XposedHelpers.findAndHookMethod(Class.forName("java.net. NetworkInterface"), "getName", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    param.setResult("rins_11");
                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void hookDialog(final XC_LoadPackage.LoadPackageParam lpparam) {

        try {

            XposedHelpers.findAndHookMethod(Class.forName("android.app.Dialog").getName(), lpparam.classLoader, "show", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Dialog varDialog = (Dialog) param.thisObject;
                    varDialog.dismiss();

                }
            });
            String varName = Class.forName("android.app.Dialog").getName();
            Class varClass = Class.forName("android.view.KeyEvent");
            XposedHelpers.findAndHookMethod(varName, lpparam.classLoader, "onKeyDown", Integer.TYPE, varClass, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    super.afterHookedMethod(param);
                    Dialog varDialog = (Dialog) param.thisObject;
                    PrintStack(lpparam.packageName);
                    varDialog.dismiss();
                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static void log(String str) {
        String str2 = "||"+ProcName +" [ "+ str+" ] ||";
        Log.i(TAG, str2);
        XposedBridge.log(str2);

    }

    public static Context getCurrentApplication() {
        Context context2;
        Exception e;
        try {
            log("getCurrentApplication start");
            Class<?> cls = Class.forName("android.app.ActivityThread");
            log("forName: activityThread: " + cls);
            log("activityThread.getMethod(\"currentApplication\").invoke(null); " + cls.getMethod("currentApplication", new Class[0]).invoke(null, new Object[0]));
            Object callStaticMethod = XposedHelpers.callStaticMethod(cls, "currentApplication", new Object[0]);
            log("application: " + callStaticMethod);
            context2 = (Context) XposedHelpers.callMethod(callStaticMethod, "getApplicationContext", new Object[0]);
            try {
                log("Context: " + context2);
                log("getCurrentApplication end");
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
            context2 = null;
            printError("getCurrentApplication", e);
            return context2;
        }
        return context2;
    }

    public static void printError(String str, Exception exc) {
       log(TAG+ProcName + "[" + str + "] ************************[START]************************"+exc+ProcName + "[" + str + "] ************************[E N D]************************");
    }

    public static void dbg(String cls,ClassLoader classLoader){
        log(classLoader.toString());
        Class<?>clszz=XposedHelpers.findClass(cls,classLoader);
        String clsV=String.valueOf(clszz.hashCode());
        if (hookMain.hasDbg.indexOf(clsV)!=-1){
            return;
        }
        hasDbg.append(clsV);
        hasDbg.append("||");
        ArrayList<Class<?>>vClasses=new ArrayList<>();
        for (Class<?> cls1:clszz.getDeclaredClasses()) {
            log("==========class==========s");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                log(cls1.getTypeName());
            }
            log(cls1.getName());
            log("==========class==========e");
            vClasses.add(cls1);
        }
        ArrayList<Field>vFields=new ArrayList<>();

        for (Field f:clszz.getDeclaredFields()) {
            log("==========field==========s");
            vFields.add(f);
            log(f.getName());
            log(f.getType().toString());
            log(f.getGenericType().toString());
            log("==========field==========e");
        }

        Method[] vMethods= clszz.getDeclaredMethods();
        for (int v = 0; v < vMethods.length; v++) {
            log("==========method==========s");
            log(vMethods[v].getName());
            log(vMethods[v].getReturnType().getName());
            log(String.valueOf(vMethods[v].getModifiers()));
            for (Class<?> vType:vMethods[v].getParameterTypes()
                 ) {
                log(vType.getName());
            }
            log("==========method==========e");
            if (v==0){
            if (ifhook(vMethods[v])){
               XposedBridge.hookMethod(vMethods[v], new XC_MethodHook() {
                   @Override
                   protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                       log("==========methodhook==========b");
                       if (param.args!=null){
                           for (Object object:param.args){
                               log(object.toString());

                           }
                       }

                       super.beforeHookedMethod(param);

                   }

                   @Override
                   protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                       log("==========methodhook==========a");
                       log(param.getResult().toString());
                       super.afterHookedMethod(param);

                   }
               });
            }
            }
        }
        Constructor<?>[] vConstructor =clszz.getDeclaredConstructors();
        for (Constructor<?> c:vConstructor
             ) {
            int v = 0;
            log("==========constructor==========e");
            log(c.getName());
            log(String.valueOf(c.getModifiers()));
            for (Class<?> vClass:
                 c.getParameterTypes()) {
                log(vClass.getName());
            }
            log("==========constructor==========s");

            if (v==0){
                if (ifhook(vMethods[v])){
                    XposedBridge.hookMethod(vMethods[v], new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            log("==========constructor==========b");
                            if (param.args!=null){
                                for (Object object:param.args){
                                    log(object.toString());

                                }
                            }

                            super.beforeHookedMethod(param);

                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            log("==========constructor==========a");
                            log(param.getResult().toString());
                            super.afterHookedMethod(param);

                        }
                    });
                }
            }
            v++;
        }

    }

    public static boolean ifhook(Method method){

        return !Modifier.isAbstract(method.getModifiers())&&!Modifier.isNative(method.getModifiers());
    }

    public static void getThreadStackTrace(){

        StackTraceElement[] vElements=Thread.currentThread().getStackTrace();
        for (int i = 0; i < vElements.length; i++) {
            XposedBridge.log(String.valueOf(vElements[i].getLineNumber()));
            XposedBridge.log(vElements[i].getMethodName());
            XposedBridge.log(vElements[i].getClassName());
            XposedBridge.log(vElements[i].getFileName());
        }
    }
}
