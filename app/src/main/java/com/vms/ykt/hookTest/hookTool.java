package com.vms.ykt.hookTest;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.vms.ykt.Util.Tool;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class hookTool {
    public static List<ClassLoader> mClassLoaderList = new ArrayList<>();
    public static List<Class<?>> mClassList = new ArrayList<>();
    public static Context mOtherContext;
    public static ClassLoader mOtherClassLoader;
    public static Context mContext;
    public static ClassLoader mClassLoader;
    public static Field stackTrace;
    public static String ProcName="";
    private static String TAG="hookmain";

    static {
        try {
            Field declaredField = Throwable.class.getDeclaredField("stackTrace");
            stackTrace = declaredField;
            declaredField.setAccessible(true);
           /** Method setAccessible = Class.class.getDeclaredMethod("setAccessible", Boolean.class);
            setAccessible.setAccessible(true);
            Method DeclaredMethod = Class.class.getMethod("getDeclaredMethod", String.class);
            DeclaredMethod.setAccessible(true);
            Method DeclaredMethods = Class.class.getMethod("getDeclaredMethods");
            DeclaredMethods.setAccessible(true);
            Method DeclaredField = Class.class.getDeclaredMethod("getDeclaredField", String.class);
            DeclaredField.setAccessible(true);
            Method DeclaredFields = Class.class.getDeclaredMethod("getDeclaredFields");
            DeclaredFields.setAccessible(true);**/
        } catch (Exception e) {
            log(" ", " ");
        }

    }

    public static boolean addClassLoader(ClassLoader classLoader) {
        if (classLoader == null) {
            log("addClassLoader", null);
            return false;
        }
        if (mClassLoaderList.contains(classLoader)) {
            log("addClassLoader", "已添加");
            return false;
        }
        mClassLoaderList.add(classLoader);
        log("addClassLoader", "成功");
        return true;

    }

    public static boolean addClass(Class<?> clazz) {
        if (checkClass(clazz))return false;
        if (clazz == null) {
            log("addClass", null);
            return false;
        }
        if (mClassLoaderList.contains(clazz)) {
            log("addClass", "已添加");
            return false;
        }
        mClassList.add(clazz);
        log("addClass", "成功");
        return true;

    }

    public static Class<?> findClass(String str) {
        for (ClassLoader classLoader2 : mClassLoaderList) {
            try {
                log("findClass", "ok");
                return XposedHelpers.findClass(str, classLoader2);
            } catch (Exception | XposedHelpers.ClassNotFoundError unused) {

            }
        }
        try {
            log("findClass", "ok");
            return XposedHelpers.findClass(str, mOtherClassLoader);
        } catch (Exception | XposedHelpers.ClassNotFoundError unused) {

        }
        for (Class<?> vClass:mClassList){
            if (str.contains(vClass.getSimpleName()))return vClass;
        }

        return null;
    }

    public static Class<?> findClass(String str, ClassLoader classLoader) {
        try {
            log("findClass", "ok");
            return XposedHelpers.findClass(str, classLoader);
        } catch (Exception | XposedHelpers.ClassNotFoundError unused) {

        }

        return null;
    }




    private void hookToast(final XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod(Toast.class, "show", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Toast vToast=(Toast)param.thisObject;
                PrintStack(lpparam.packageName);
            }
        });

    }

    private void hookVpn(XC_LoadPackage.LoadPackageParam lpparam) {


        try {
            Class cls = XposedHelpers.findClass("java.net.NetworkInterface", lpparam.classLoader);
            XposedHelpers.findAndHookMethod(cls, "getName", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    param.setResult("rins_11");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hookDialog(final XC_LoadPackage.LoadPackageParam lpparam) {

        try {


            XposedHelpers.findAndHookMethod(Dialog.class, "show", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Dialog varDialog = (Dialog) param.thisObject;
                    varDialog.dismiss();
                    PrintStack(lpparam.packageName);

                }
            });
            Class varClass = Class.forName("android.view.KeyEvent");
            XposedHelpers.findAndHookMethod(Dialog.class, "onKeyDown", Integer.TYPE, varClass, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    super.afterHookedMethod(param);
                    Dialog varDialog = (Dialog) param.thisObject;

                    varDialog.dismiss();
                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void hookAttach() {
        XposedHelpers.findAndHookMethod(Application.class,
                "attach",
                Context.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        mOtherContext = (Context) param.args[0];
                        mOtherClassLoader = mOtherContext.getClassLoader();
                        addClassLoader(mOtherClassLoader);
                        log("","拿到Classloader ");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });
            //        XposedHelpers.findAndHookMethod(Application.class,
//                "attachBaseContext",
//                Context.class,
//                new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        super.beforeHookedMethod(param);
//
//                    }
//
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//
//                    }
//                });

//        XposedHelpers.findAndHookMethod(Application.class, "onCreate",
//                new XC_MethodHook() {
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//
//                    }
//                });
    }

    public static void hookLoadClass() {

        XposedBridge.hookAllMethods(ClassLoader.class, "loadClass", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                for (Object object : param.args) {
                    log("hookLoadClass", object.toString());
                }
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {

                if (param.hasThrowable()) {
                    return;
                }
                if (param.args.length != 1) {
                    return;
                }
                Class<?> cls = (Class<?>) param.getResult();
                addClass(cls);
                String name = cls.getName();
                log("hookLoadClass", name);
                log("hookLoadClass", cls.getCanonicalName());

            }
        });


    }

    public static void hookSplitPaths(ClassLoader classLoader) {
        final Class<?> DexPathList = XposedHelpers.findClass("dalvik.system.DexPathList", classLoader);
        for (final Method method : DexPathList.getDeclaredMethods()) {
            XposedBridge.hookMethod(method, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    // splitPaths()方法传入的是动态加载进来的类在手机中的路径
                    if (method.getName().equals("splitPaths")) {
                        XposedBridge.log("Baidu: DexPathList中的" + method.getName() + "被调用了，传参为" + param.args[0] + " " + param.args[1]);
                    }
                }
            });
        }
    }


    private void PrintStack(String packageName) {
        Object object = null;
        StringBuffer varStringBuffer = new StringBuffer();
        StringBuffer varStringBuffer1 = new StringBuffer();
        printStack(packageName,  new Exception().getStackTrace());
    }

    public static String printStack(String str, StackTraceElement[] stackTraceElementArr) {
        if (stackTraceElementArr.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(str);
            sb.append("] ************************[START]************************\n");
            for (StackTraceElement stackTraceElement : stackTraceElementArr) {
                sb.append("at ");
                sb.append(stackTraceElement.getClassName());
                sb.append(".");
                sb.append(stackTraceElement.getMethodName());
                sb.append("(");
                sb.append(stackTraceElement.getFileName());
                sb.append(":");
                sb.append(stackTraceElement.getLineNumber());
                sb.append(")\n");
            }
            sb.append(ProcName);
            sb.append("[");
            sb.append(str);
            sb.append("] ************************[E N D]************************\n");
            log("", sb.toString());
            return sb.toString();
        }
        return "";
    }

    public static StackTraceElement[] getThreadStackTrace() {
        String methodName;
        StackTraceElement[] stackTrace2 = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTrace2.length; i++) {
            String fileName = stackTrace2[i].getFileName();
            if (fileName != null && fileName.equals("hookMain.java") && (methodName = stackTrace2[i].getMethodName()) != null && methodName.equals("getThreadStackTrace")) {
                return (StackTraceElement[]) new ArrayList(Arrays.asList(stackTrace2).subList(i + 1, stackTrace2.length)).toArray(new StackTraceElement[0]);
            }
        }
        return stackTrace2;
    }

    public static StackTraceElement[] getThreadStackTrace2() {
        StackTraceElement[] stackTraceElementArr;
        try {
            IllegalThreadStateException illegalThreadStateException = new IllegalThreadStateException("getStackTrace");
            illegalThreadStateException.getStackTrace();
            stackTraceElementArr = (StackTraceElement[]) stackTrace.get(illegalThreadStateException);
        } catch (Exception e) {
            stackTraceElementArr = e.getStackTrace();
        }
        return stackTraceElementArr;
    }



    public static boolean dbgSharedPreferences() {
        Method[] declaredMethods;
        Method[] declaredMethods2;
        try {
            Class<?> cls = Class.forName("android.app.SharedPreferencesImpl");
            Class<?> cls2 = Class.forName("android.app.SharedPreferencesImpl$EditorImpl");
            for (Method method : cls.getDeclaredMethods()) {
                final String name = method.getName();
                if (name.indexOf("get") == 0) {

                    XposedBridge.hookMethod(method, new XC_MethodHook() {
                        protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                            String str;
                            try {
                                str = methodHookParam.args[0].toString();
                            } catch (Exception unused) {
                                str = null;
                            }
                            log("", "[" + name + "] |Key| [" + str + "] |Val| [" + methodHookParam.getResult() + "]");
                        }
                    });
                }
            }
            for (Method method2 : cls2.getDeclaredMethods()) {
                final String name2 = method2.getName();
                if (name2.indexOf("put") == 0) {
                    XposedBridge.hookMethod(method2, new XC_MethodHook() {
                        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                            String str;
                            try {
                                str = methodHookParam.args[0].toString();
                            } catch (Exception unused) {
                                str = null;
                            }
                            log("", "[" + name2 + "] |Key| [" + str + "] |Val| [" + methodHookParam.args[1] + "]");
                        }
                    });
                }
            }
            return true;
        } catch (Exception e) {
            printStack("hookSharedPreferencesImpl", e.getStackTrace());
            return false;
        }
    }

    public static void dbgClass(String cls, ClassLoader classLoader) {
        StringBuilder hasDbg = new StringBuilder();
        log("dbg", classLoader.toString());
        Class<?> clszz = XposedHelpers.findClass(cls, classLoader);
        String clsV = String.valueOf(clszz.hashCode());
        if (hasDbg.indexOf(clsV) != -1) {
            return;
        }
        hasDbg.append(clsV);
        hasDbg.append("||");
        ArrayList<Class<?>> vClasses = new ArrayList<>();

        for (Class<?> cls1 : clszz.getDeclaredClasses()) {
            log("dbg", "==========class==========s");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                log("dbg", cls1.getTypeName());
            }
            log("dbg", cls1.getName());
            log("dbg", cls1.getModifiers());
            log("dbg", "==========class==========e");
            vClasses.add(cls1);
        }

        ArrayList<Field> vFields = new ArrayList<>();

        for (Field f : clszz.getDeclaredFields()) {
            log("dbg", "==========field==========s");
            vFields.add(f);
            log("dbg", f.getName());
            log("dbg", f.getType().toString());
            log("dbg", f.getGenericType().toString());
            log("dbg", "==========field==========e");
        }

        Method[] vMethods = clszz.getDeclaredMethods();
        for (int v = 0; v < vMethods.length; v++) {

            log("dbg", "==========method==========s");
            log("dbg", vMethods[v].getName());
            log("dbg", vMethods[v].getReturnType().getName());
            log("dbg", String.valueOf(vMethods[v].getModifiers()));
            for (Class<?> vType : vMethods[v].getParameterTypes()) {
                log("dbg", vType.getName());
            }
            log("dbg", "==========method==========e");

            if (ifHook(vMethods[v])) {
                vMethods[v].setAccessible(true);
                XposedBridge.hookMethod(vMethods[v], new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        log("dbg", "==========methodhook==========b");
                        if (param.args != null) {
                            for (Object object : param.args) {
                                log("dbg", object.toString());
                            }
                        }
                        log("dbg", "==========methodhook==========b");
                        super.beforeHookedMethod(param);

                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        log("dbg", "==========methodhook==========a");
                        log("dbg", param.getResult().toString());
                        log("dbg", "==========methodhook==========a");
                        super.afterHookedMethod(param);

                    }
                });

            }
        }
        Constructor<?>[] vConstructor = clszz.getDeclaredConstructors();

        for (Constructor<?> c : vConstructor) {

            log("dbg", "==========constructor==========e");
            log("dbg", c.getName());
            log("dbg", String.valueOf(c.getModifiers()));
            for (Class<?> vClass : c.getParameterTypes()) {
                log("dbg", vClass.getName());
            }
            log("dbg", "==========constructor==========s");
            c.setAccessible(true);
            XposedBridge.hookMethod(c, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    log("dbg", "==========constructor==========b");
                    if (param.args != null) {
                        for (Object object : param.args) {
                            log("dbg", object.toString());

                        }
                    }

                    super.beforeHookedMethod(param);

                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    log("dbg", "==========constructor==========a");
                    log("dbg", param.getResult().toString());
                    log("dbg", "==========constructor==========a");
                    super.afterHookedMethod(param);

                }
            });
        }

    }



    public static boolean checkClass(Class<?> cls){
        String string=cls.getCanonicalName();
        if (string.indexOf("android")!=1){
            return true;
        }
        return false;
    }

    public static boolean ifHook(Method method) {

        return !Modifier.isAbstract(method.getModifiers()) && !Modifier.isNative(method.getModifiers());
    }

    public static boolean ifHook(){
        return false;
    }

    public static void checkHook(ClassLoader classLoader){
        XposedHelpers.findAndHookMethod("com.vms.ykt.hookTest.hookTool", classLoader, "", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                return true;
            }
        });
    }

    /**
     *防止重复执行Hook代码
     * @param flag 判断标识,针对不同Hook代码分别进行判断
     * @return 是否已经注入Hook代码
     */
    private boolean isInjecter(String flag) {
        try {
            if (TextUtils.isEmpty(flag)) return false;
            Field methodCacheField = XposedHelpers.class.getDeclaredField("methodCache");
            methodCacheField.setAccessible(true);
            HashMap<String, Method> methodCache = (HashMap<String, Method>) methodCacheField.get(null);
            Method method=XposedHelpers.findMethodBestMatch(Application.class,"onCreate");
            String key=String.format("%s#%s",flag,method.getName());
            if (methodCache.containsKey(key)) return true;
            methodCache.put(key, method);
            return false;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 手动申请SD卡权限
     */
    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mOtherContext.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE")
                    != PackageManager.PERMISSION_GRANTED) {
                XposedHelpers.findAndHookMethod(Activity.class, "onCreate", Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        final Activity firstActivity = (Activity) param.thisObject;

                        ActivityCompat.requestPermissions(firstActivity, new String[]{
                                "android.permission.WRITE_EXTERNAL_STORAGE",
                                "android.permission.ACCESS_COARSE_LOCATION",
                                "android.permission.ACCESS_FINE_LOCATION",
                                "android.permission.READ_PHONE_STATE"}, 0);
                    }
                });
            }
        }
    }

    /**
     * 获得当前进程的名字
     *
     * @return 进程号
     */
    private String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : Objects.requireNonNull(activityManager)
                .getRunningAppProcesses()) {
            log(appProcess.pid);
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public static Context getCurrentApplication() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            log("getCurrentApplication", cls.getCanonicalName());
            return (Application) cls.getMethod("getApplication", new Class[0]).invoke(cls.getMethod("currentActivityThread", new Class[0]).invoke(null, null), null);
        } catch (Exception e) {
            printStack("getCurrentApplication", e.getStackTrace());
            return null;
        }
    }

    public static void printError(String str, Exception exc) {
        log(TAG+ProcName + "[" + str + "] ************************[START]************************\n"+exc+ProcName + "[" + str + "] ************************[E N D]************************\n");
    }

    public static void log(String tag, Object resp) {
        XposedBridge.log(tag + ":--->" + resp + "\n");
        Log.d(tag, ":--->" + resp + "\n");
    }

    public static void log(Object str) {
        String str2 = "||"+ProcName +" [ "+ str+" ] ||";
        Log.i(TAG, str2);
        XposedBridge.log(str2);

    }
}
