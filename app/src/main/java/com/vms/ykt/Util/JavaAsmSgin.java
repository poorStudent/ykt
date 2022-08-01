package com.vms.ykt.Util;

import android.text.style.ForegroundColorSpan;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Objects;

public class JavaAsmSgin {


    public static boolean isAndroid(final String vmName) {
        final String lowerVMName = vmName.toLowerCase();
        return lowerVMName.contains("dalvik") || lowerVMName.contains("lemur");
    }

    public static boolean isAndroid() {
        return isAndroid(Objects.requireNonNull(System.getProperty("java.vm.name")));
    }

    public static Class<?> findClass(String clazz, ClassLoader classLoader) throws ClassNotFoundException {
        return Class.forName(clazz, true, classLoader);
    }

    public static Class<?> findClass(String clazz) throws ClassNotFoundException {
        return Class.forName(clazz);
    }


    public static String getAllMethodSgin(String clazz, String Method, ClassLoader classLoader) throws Exception {
        Class<?> vClass = findClass(clazz, classLoader);
        return getAllMethodSgin(vClass, Method);
    }


    public static String getAllMethodSgin(String clazz, String Method) throws Exception {
        Class<?> vClass = findClass(clazz);
        return getAllMethodSgin(vClass, Method);
    }

    public static String getAllMethodSgin(Class<?> clazz, String Method) throws Exception {
        StringBuilder vm = new StringBuilder();
        for (Method vMethod : clazz.getDeclaredMethods()) {
            if (Method != null) {
                if (!vMethod.getName().equals(Method)) {
                    continue;
                }
            }
            vm.append(getMethodSgin(clazz, Method, new Class<?>[0]));
            vm.append("\n");
        }
        return vm.toString();
    }


    public static String getMethodSgin(String clazz, String Method, Class<?>[] classes, ClassLoader classLoader) throws Exception {
        Class<?> vClass = findClass(clazz, classLoader);
        return getMethodSgin(vClass, Method, classes);

    }


    public static String getMethodSgin(String clazz, String Method, Class<?>[] classes) throws Exception {
        Class<?> vClass = findClass(clazz);
        return getMethodSgin(vClass, Method, classes);

    }

    public static String getMethodSgin(Class<?> clazz, String Method, String[] cls) throws Exception {
        Class<?>[] vClasses = null;
        if (cls != null) {
            vClasses=new Class<?>[cls.length];
            for (int i = 0; i < cls.length; i++) {
                vClasses[i] = Class.forName(cls[i]);
            }
        }
        return getMethodSgin(clazz, Method, vClasses);

    }


    public static String getMethodSgin(Class<?> vClass, String Method, Class<?>[] classes) throws NoSuchMethodException {
        if (classes == null) {
            classes = new Class<?>[0];
        }
        Method vMethod = vClass.getDeclaredMethod(Method, classes);
        return getClassSgin(vClass) + getDesc(vMethod);
    }

    public static String getDesc(final Method method) {
        final StringBuilder buf = new StringBuilder();
        buf.append("(");
        final Class<?>[] types = method.getParameterTypes();
        for (Class<?> type : types) {
            buf.append(getDesc(type));
        }
        buf.append(")");
        buf.append(getDesc(method.getReturnType()));
        return buf.toString();
    }

    public static String getDesc(final Class<?> returnType) {
        if (returnType.isPrimitive()) {
            return getPrimitiveLetter(returnType);
        }
        if (returnType.isArray()) {
            return "[" + getDesc(returnType.getComponentType());
        }
        return "L" + getType(returnType) + ";";
    }

    public static String getType(final Class<?> parameterType) {
        if (parameterType.isArray()) {
            return "[" + getDesc(parameterType.getComponentType());
        }
        if (!parameterType.isPrimitive()) {
            final String clsName = parameterType.getName();
            return clsName.replaceAll("\\.", "/");
        }
        return getPrimitiveLetter(parameterType);
    }

    public static String getPrimitiveLetter(final Class<?> type) {
        if (Integer.TYPE.equals(type)) {
            return "I";
        }
        if (Void.TYPE.equals(type)) {
            return "V";
        }
        if (Boolean.TYPE.equals(type)) {
            return "Z";
        }
        if (Character.TYPE.equals(type)) {
            return "C";
        }
        if (Byte.TYPE.equals(type)) {
            return "B";
        }
        if (Short.TYPE.equals(type)) {
            return "S";
        }
        if (Float.TYPE.equals(type)) {
            return "F";
        }
        if (Long.TYPE.equals(type)) {
            return "J";
        }
        if (Double.TYPE.equals(type)) {
            return "D";
        }
        throw new IllegalStateException("Type: " + type.getCanonicalName() + " is not a primitive type");
    }


    public static Type getMethodType(final Class<?> clazz, Class<?>[] classes, final String methodName) {
        try {
            final Method method = clazz.getMethod(methodName, classes);
            return method.getGenericReturnType();
        } catch (Exception ex) {
            return null;
        }
    }


    public static Type getFieldType(final Class<?> clazz, final String fieldName) {

        try {
            final Field field = clazz.getDeclaredField(fieldName);
            return field.getGenericType();
        } catch (Exception ex) {
            return null;
        }

    }

    public static String getFieldSgin(final Class<?> clazz, final String fieldName) {

        StringBuilder vm = new StringBuilder();
        for (Field field : clazz.getDeclaredFields()) {
            if (fieldName != null) {
                if (!field.getName().equals(fieldName)) {
                    continue;
                }
                vm.append(getDesc(clazz, field));
            }

        }
        return vm.toString();
    }

    public static String getDesc(final Class<?> clazz, Field field) {
        return getClassSgin(clazz) + field.getName() + ":" + getDesc(field.getType());
    }

    public static String getClassSgin(final Class<?> clazz) {
        String a = clazz.isArray() ? "[" : "";
        return a + "L" + clazz.getName().replaceAll("\\.", "/") + "->;";
    }

    public static String getModifier(int mod) {

        if (Modifier.isAbstract(mod)) {
            return "Abstract";
        } else if (Modifier.isFinal(mod)) {
            return "Final";
        } else if (Modifier.isInterface(mod)) {
            return "Interface";
        } else if (Modifier.isNative(mod)) {
            return "Native";
        } else if (Modifier.isPrivate(mod)) {
            return "Private";
        } else if (Modifier.isProtected(mod)) {
            return "Protected";
        } else if (Modifier.isPublic(mod)) {
            return "Public";
        } else if (Modifier.isStatic(mod)) {
            return "Static";
        } else if (Modifier.isStrict(mod)) {
            return "Strict";
        } else if (Modifier.isSynchronized(mod)) {
            return "Synchronized";
        } else if (Modifier.isTransient(mod)) {
            return "Transient";
        } else if (Modifier.isVolatile(mod)) {
            return "Volatile";
        }
        return "";
    }

    /*
     PUBLIC: 1
    PRIVATE: 2
    PROTECTED: 4
    STATIC: 8
    FINAL: 16
    SYNCHRONIZED: 32
    VOLATILE: 64
    TRANSIENT: 128
    NATIVE: 256
    INTERFACE: 512
    ABSTRACT: 1024
    STRICT: 2048
     */
volatile static boolean sBoolean;
}
