#include <jni.h>


jstring sk(JNIEnv *jniEnv, jclass cls, jstring jstring1){
    jclass sbclass=jniEnv->FindClass("java/lang/StringBuilder");
    jmethodID sbinit=jniEnv->GetMethodID(sbclass,"<init>","()V");
    jmethodID tostr = jniEnv->GetMethodID(sbclass,"toString", "()Ljava/lang/String;");
    jmethodID apd = jniEnv->GetMethodID(sbclass,"append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;");
    jmethodID m=jniEnv->GetMethodID(cls,"getMd5","(Ljava/lang/String;)Ljava/lang/String;");
    jobject sb=jniEnv->NewObject(sbclass,sbinit);
    sb=jniEnv->CallObjectMethod(sb,apd,jstring1);
    jstring jstring2 = jniEnv->NewStringUTF("123456789");
    sb=jniEnv->CallObjectMethod(sb,apd,jstring2);
    jstring2=(jstring)jniEnv->CallObjectMethod(sb,tostr);
    jstring2=(jstring)jniEnv->CallStaticObjectMethod(cls,m,jstring2);
    jniEnv->DeleteLocalRef(sb);
    return jstring2;

}
jstring sm(JNIEnv *jniEnv, jclass cls, jstring jstring1,jstring jstring2){
    jclass sbclass=jniEnv->FindClass("java/lang/StringBuilder");
    jmethodID sbinit=jniEnv->GetMethodID(sbclass,"<init>","()V");
    jmethodID tostr = jniEnv->GetMethodID(sbclass,"toString", "()Ljava/lang/String;");
    jmethodID apd = jniEnv->GetMethodID(sbclass,"append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;");
    jmethodID m=jniEnv->GetMethodID(cls,"getMd5","(Ljava/lang/String;)Ljava/lang/String;");
    jobject sb=jniEnv->NewObject(sbclass,sbinit);
    sb=jniEnv->CallObjectMethod(sb,apd,jstring1);
    sb=jniEnv->CallObjectMethod(sb,apd,jstring2);
    jstring2=(jstring)jniEnv->CallObjectMethod(sb,tostr);
    jstring2=(jstring)jniEnv->CallStaticObjectMethod(cls,m,jstring2);
    jniEnv->DeleteLocalRef(sb);
    return jstring2;

}