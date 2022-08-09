# 定义模块当前路径LOCAL_PATH := $(call my-dir)
#清空当前环境变量include $(CLEAR_VARS)
 # 引入头文件等LOCAL_xxx       := xxx
 #编译生成的文件名  LOCAL_MODULE    := hello
#编译该模块所需的源码LOCAL_SRC_FILES := hello.c
#引入jar包等LOCAL_xxx       := xxx
 #编译生成文件的类型 #LOCAL_MODULE_CLASS  、JAVA_LIBRARIES#APPS 、 SHARED_LIBRARIES#EXECUTABLES 、 ETC include $(BUILD_EXECUTABLE)

LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := native-lib
LOCAL_SRC_FILES := .*cpp #$(call all-c-files-under, ${LOCAL_PATH})
LOCAL_C_INCLUDES := .*h
LOCAL_SHARED_LIBRARIES :=liblog
LOCAL_SHARED_LIBRARIES += libutils

#LOCAL_C_INCLUDES = $(INCLUDES)
#LOCAL_CFLAGS = $(L_CFLAGS)
#LOCAL_COPY_HEADERS_TO := libhello
#LOCAL_COPY_HEADERS := hello.h
#编译动态库 BUILD_SHARED_LIBRARY

include $(BUILD_SHARED_LIBRARY)


