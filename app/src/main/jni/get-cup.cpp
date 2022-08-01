//
// Created by ms on 2022/7/30.
//
#include <jni.h>
#include <string>
#include <android/log.h>

#define Log(...)  __android_log_print(ANDROID_LOG_INFO, "In C/C++:", __VA_ARGS__);
extern "C"
JNIEXPORT jstring JNICALL
Java_com_vms_ykt_TestActivity_testActivity_getCpu(JNIEnv *env, jobject thiz, jint i, jdouble d,
                                                  jfloat f) {

    std::string string = "111111111";
    jstring jstr = env->NewStringUTF(string.c_str());
    const char *s = string.data();
    const char *stri = env->GetStringUTFChars(jstr, 0);
    return  env->NewStringUTF(stri);
    // TODO: implement getCpu()
}


