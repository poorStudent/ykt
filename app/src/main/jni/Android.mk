# 定义模块当前路径LOCAL_PATH := $(call my-dir)
#清空当前环境变量include $(CLEAR_VARS)
 # 引入头文件等LOCAL_xxx       := xxx
 #编译生成的文件名  LOCAL_MODULE    := hello
#编译该模块所需的源码LOCAL_SRC_FILES := hello.c
#引入jar包等LOCAL_xxx       := xxx
 #编译生成文件的类型 #LOCAL_MODULE_CLASS  、JAVA_LIBRARIES#APPS 、 SHARED_LIBRARIES#EXECUTABLES 、 ETCinclude $(BUILD_EXECUTABLE)

LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
# 生成libhell.so
LOCAL_MODULE = libhello
LOCAL_CFLAGS = $(L_CFLAGS)
LOCAL_SRC_FILES = hello.c
LOCAL_C_INCLUDES = $(INCLUDES)
LOCAL_SHARED_LIBRARIES := libcutils
LOCAL_COPY_HEADERS_TO := libhello
LOCAL_COPY_HEADERS := hello.h

#编译动态库 BUILD_SHARED_LIBRARY
include $(BUILD_SHARED_LIBRARY)

#引用系统静态库
LOCAL_STATIC_LIBRARIES += libxxxxx
LOCAL_STATIC_LIBRARIES := \
    ...
    libxxx2 \
    libxxx \
#引用系统动态库
LOCAL_SHARED_LIBRARIES += libxxxxx
LOCAL_SHARED_LIBRARIES := liblog libnativehelper libGLESv2

引用第三方库文件
LOCAL_LDFLAGS:=-L/PATH -Lxxx
LOCAL_LDFLAGS := $(LOCAL_PATH)/lib/libtest.a
引用第三方头文件
LOCAL_C_INCLUDES :=path
编译apk
  LOCAL_PATH := $(call my-dir)
  include $(CLEAR_VARS)
  LOCAL_SRC_FILES := $(call all-subdir-java-files)  # 生成hello apk
  LOCAL_PACKAGE_NAME := hello
  include $(BUILD_PACKAGE)
编译jar包
  LOCAL_PATH := $(call my-dir)
  include $(CLEAR_VARS)
  LOCAL_SRC_FILES := $(call all-subdir-java-files)  # 生成 hello
  LOCAL_MODULE := hello
  # 编译生成静态jar包
  include $(BUILD_STATIC_JAVA_LIBRARY)
  #编译生成共享jar
  include $(BUILD_JAVA_LIBRARY)

静态jar包：
include $(BUILD_STATIC_JAVA_LIBRARY)
使用.class文件打包而成的JAR文件，可以在任何java虚拟机运行

动态jar包：
include $(BUILD_JAVA_LIBRARY)
在静态jar包基础之上使用.dex打包而成的jar文件，.dex是android系统使用的文件格式。

APK 依赖jar
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
# 静态jar包
LOCAL_STATIC_JAVA_LIBRARIES := static-library
#动态jar包
LOCAL_JAVA_LIBRARIES := share-library

LOCAL_SRC_FILES := $(call all-subdir-java-files)
LOCAL_PACKAGE_NAME := hello
include $(BUILD_PACKAGE)

预编译jar包
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
#指定编译生成的文件类型
LOCAL_MODULE_CLASS := JAVA_LIBRARIES
LOCAL_MODULE := hello
LOCAL_SRC_FILES :=  $(call all-subdir-java-files)
# 预编译
include $(BUILD_PREBUILT)

1.LOCAL_MODULE_CLASS： 编译文件类型
2.JAVA_LIBRARIES： dex归档文件
3.APPS： APK文件
4.SHARED_LIBRARIES： 动态库文件
5.EXECUTABLES： 二进制文件
6.ETC： 其他文件格式

六、 Android.mk 判断语句

ifeq($(VALUE), x)   #ifneq
  do_yeselse
  do_noendif
ifeq/ifneq：根据判断条件执行相关编译
