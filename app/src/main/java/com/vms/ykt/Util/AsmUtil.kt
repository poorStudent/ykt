package com.vms.ykt.Util

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.lang.reflect.Type

object AsmUtil {
        fun isAndroid(vmName: String): Boolean {
            val lowerVMName = vmName.toLowerCase()
            return lowerVMName.contains("dalvik") || lowerVMName.contains("lemur")
        }

        val isAndroid: Boolean
            get() =isAndroid(System.getProperty("java.vm.name"))

        @Throws(ClassNotFoundException::class)
        fun findClass(
            clazz: String?,
            classLoader: ClassLoader?
        ): Class<*> {
            return Class.forName(clazz, true, classLoader)
        }

        @Throws(ClassNotFoundException::class)
        fun findClass(clazz: String?): Class<*> {
            return Class.forName(clazz)
        }

        @Throws(Exception::class)
        fun getAllMethodSgin(clazz: String?, Method: String?, classLoader: ClassLoader?): String {
            val vClass = findClass(clazz, classLoader)
            return getAllMethodSgin(vClass, Method)
        }

        @Throws(Exception::class)
        fun getAllMethodSgin(clazz: String?, Method: String?): String {
            val vClass = findClass(clazz)
            return getAllMethodSgin(vClass, Method)
        }

        @Throws(Exception::class)
        fun getAllMethodSgin(clazz: Class<*>, Method: String?): String {
            val vm = StringBuilder()
            for (vMethod in clazz.declaredMethods) {
                if (Method != null) {
                    if (vMethod.name != Method) {
                        continue
                    }
                }
                vm.append(
                    getMethodSgin(
                        clazz,
                        Method,
                        arrayOfNulls<Class<*>>(0)
                    )
                )
                vm.append("\n")
            }
            return vm.toString()
        }

        @Throws(Exception::class)
        fun getMethodSgin(
            clazz: String?,
            Method: String?,
            classes: Array<Class<*>?>?,
            classLoader: ClassLoader?
        ): String {
            val vClass = findClass(clazz, classLoader)
            return getMethodSgin(vClass, Method, classes)
        }

        @Throws(Exception::class)
        fun getMethodSgin(clazz: String?, Method: String?, classes: Array<Class<*>?>?): String {
            val vClass = findClass(clazz)
            return getMethodSgin(vClass, Method, classes)
        }

        @Throws(Exception::class)
        fun getMethodSgin(clazz: Class<*>?, Method: String?, cls: Array<String?>?): String {
            var vClasses: Array<Class<*>?>? = null
            if (cls != null) {
                vClasses= arrayOfNulls(cls.size)
                for (i in cls.indices) {
                    vClasses[i] = Class.forName(cls[i])
                }
            }
            return getMethodSgin(clazz!!, Method, vClasses)
        }



    @Throws(NoSuchMethodException::class)
        fun getMethodSgin(vClass: Class<*>, Method: String?, classes: Array<Class<*>?>?): String {
            var classes = classes
            if (classes == null) {
                classes = arrayOfNulls<Class<*>?>(0)
            }
            val vMethod = vClass.getDeclaredMethod(Method, *classes)
            return getClassSgin(vClass) + getDesc(
                vMethod
            )
        }

        fun getDesc(method: Method): String {
            val buf = StringBuffer()
            buf.append("(")
            val types = method.parameterTypes
            for (i in types.indices) {
                buf.append(getDesc(types[i]))
            }
            buf.append(")")
            buf.append(getDesc(method.returnType))
            return buf.toString()
        }

        fun getDesc(returnType: Class<*>): String {
            if (returnType.isPrimitive) {
                return getPrimitiveLetter(returnType)
            }
            return if (returnType.isArray) {
                "[" + getDesc(returnType.componentType)
            } else "L" + getType(returnType) + ";"
        }

        fun getType(parameterType: Class<*>): String {
            if (parameterType.isArray) {
                return "[" + getDesc(parameterType.componentType)
            }
            if (!parameterType.isPrimitive) {
                val clsName = parameterType.name
                return clsName.replace("\\.".toRegex(), "/")
            }
            return getPrimitiveLetter(parameterType)
        }

        fun getPrimitiveLetter(type: Class<*>): String {
            if (Integer.TYPE == type) {
                return "I"
            }
            if (Void.TYPE == type) {
                return "V"
            }
            if (java.lang.Boolean.TYPE == type) {
                return "Z"
            }
            if (Character.TYPE == type) {
                return "C"
            }
            if (java.lang.Byte.TYPE == type) {
                return "B"
            }
            if (java.lang.Short.TYPE == type) {
                return "S"
            }
            if (java.lang.Float.TYPE == type) {
                return "F"
            }
            if (java.lang.Long.TYPE == type) {
                return "J"
            }
            if (java.lang.Double.TYPE == type) {
                return "D"
            }
            throw IllegalStateException("Type: " + type.canonicalName + " is not a primitive type")
        }

        fun getMethodType(clazz: Class<*>, classes: Array<Class<*>?>, methodName: String?): Type? {
            return try {
                val method = clazz.getMethod(methodName, *classes)
                method.genericReturnType
            } catch (ex: Exception) {
                null
            }
        }

        fun getFieldType(clazz: Class<*>, fieldName: String?): Type? {
            return try {
                val field = clazz.getDeclaredField(fieldName)
                field.genericType
            } catch (ex: Exception) {
                null
            }
        }

        fun getFieldSgin(clazz: Class<*>, fieldName: String?): String {
            val vm = StringBuilder()
            for (field in clazz.declaredFields) {
                if (fieldName != null) {
                    if (field.name != fieldName) {
                        continue
                    }
                    vm.append(getDesc(clazz, field))
                }
            }
            return vm.toString()
        }

        fun getDesc(clazz: Class<*>?, field: Field): String {
            return clazz?.let { getClassSgin(it) } + field.name + ":" + getDesc(field.type)
        }

        fun getClassSgin(clazz: Class<*>): String {
            val a = if (clazz.isArray) "[" else ""
            return a + "L" + clazz.name.replace("\\.".toRegex(), "/") + "->;"
        }

        fun getModifier(mod: Int): String {
            if (Modifier.isAbstract(mod)) {
                return "Abstract"
            } else if (Modifier.isFinal(mod)) {
                return "Final"
            } else if (Modifier.isInterface(mod)) {
                return "Interface"
            } else if (Modifier.isNative(mod)) {
                return "Native"
            } else if (Modifier.isPrivate(mod)) {
                return "Private"
            } else if (Modifier.isProtected(mod)) {
                return "Protected"
            } else if (Modifier.isPublic(mod)) {
                return "Public"
            } else if (Modifier.isStatic(mod)) {
                return "Static"
            } else if (Modifier.isStrict(mod)) {
                return "Strict"
            } else if (Modifier.isSynchronized(mod)) {
                return "Synchronized"
            } else if (Modifier.isTransient(mod)) {
                return "Transient"
            } else if (Modifier.isVolatile(mod)) {
                return "Volatile"
            }
            when(mod){
                1+8->return "Public Static"
                2+8->return "Private Static"
                4+8->return "Protected Static"
                1+16->return "Public Final"
                2+16->return "Private Final"
                4+16->return "Protected Final"
                1+16+8->return "Public Static Final"
                2+16+8->return "Private Static Final"
                4+16+8->return "Protected Static Final"
                16+8->return "Static Final"
                64+8->return "Volatile Static"
            }
            return ""
        } /*
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
    
}