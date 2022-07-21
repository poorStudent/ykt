package com.vms.ykt.hookTest;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class hookTool {
    private static List<ClassLoader> mClassLoaderList = new ArrayList<>();
    private static List<Class<?>> mClassList = new ArrayList<>();
    private static Field stackTrace;
    private static String ProcName;

    static {
        try {
            Field declaredField = Throwable.class.getDeclaredField("stackTrace");
            stackTrace = declaredField;
            declaredField.setAccessible(true);
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
        return null;
    }

    public static Class<?> findClass(String str, ClassLoader classLoader2) {
        try {
            log("findClass","ok");
        return XposedHelpers.findClass(str, classLoader2);
        } catch (Exception | XposedHelpers.ClassNotFoundError unused) {
            return null;
        }
    }

    public static Class<?> findClassO(String str) {
        try {
            log("findClass","ok");
        return Class.forName(str);
        } catch (Exception | XposedHelpers.ClassNotFoundError unused) {
            return null;
        }
    }


    public static Context getCurrentApplication() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            return (Application) cls.getMethod("getApplication", new Class[0]).invoke(cls.getMethod("currentActivityThread", new Class[0]).invoke(null, null), null);
        } catch (Exception e) {
            printStack("getCurrentApplication", e.getStackTrace());
            return null;
        }
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
            sb.append("] ************************[E N D]************************");
            log("",sb.toString());
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


    private static void printStack() {
        // 获取线程的StackTraceElement[]
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
                XposedBridge.log("Baidu: Dump Stack" + i + ": " + stackElements[i].getClassName()
                        + "----" + stackElements[i].getFileName()
                        + "----" + stackElements[i].getLineNumber()
                        + "----" + stackElements[i].getMethodName());
            }
        }
        XposedBridge.log("Baidu: Dump Stack: ---------------over----------------");
    }

    public static void hook动态加载(ClassLoader classLoader){

        XposedBridge.hookAllMethods(ClassLoader.class, "loadClass", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {

                if (param.hasThrowable()) return;
                if (param.args.length != 1) return;

                Class<?> cls = (Class<?>) param.getResult();
                String name = cls.getName();

            }
        });

        final Class<?> DexPathList = XposedHelpers.findClass("dalvik.system.DexPathList", classLoader);

        for (final Method method: DexPathList.getDeclaredMethods()){
            XposedBridge.hookMethod(method, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    // splitPaths()方法传入的是动态加载进来的类在手机中的路径
                    if(method.getName().equals("splitPaths")){
                        XposedBridge.log("Baidu: DexPathList中的" + method.getName() + "被调用了，传参为" + param.args[0]+" " + param.args[1]);
                    }
                }
            }) ;
        }
    }
    public static boolean hookSharedPreferencesImpl() {
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
                           log("","[" + name + "] |Key| [" + str + "] |Val| [" + methodHookParam.getResult() + "]");
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
                            log("","[" + name2 + "] |Key| [" + str + "] |Val| [" + methodHookParam.args[1] + "]");
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

    public static void log(String tag, Object resp) {
        XposedBridge.log(tag + ":--->" + resp);
        Log.d(tag, ":--->" + resp);
    }
}
