#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_vms_ykt_UI_LoginActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    // TODO: implement stringFromJNI()
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}