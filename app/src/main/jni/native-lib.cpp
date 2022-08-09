#include <jni.h>
#include <unwind.h>
#include <dlfcn.h>
#include <vector>
#include <string>
#include <android/log.h>
#include <unistd.h>
#include <sys/ptrace.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <sys/user.h>
#include <sys/reg.h>
#include <stdio.h>
#include "common.h"
using namespace std;

static _Unwind_Reason_Code unwindCallback(struct _Unwind_Context* context, void* arg)
{
    std::vector<_Unwind_Word> &stack = *(std::vector<_Unwind_Word>*)arg;
    stack.push_back(_Unwind_GetIP(context));
    return _URC_NO_REASON;
}

void callstackDump(std::string &dump) {
    std::vector<_Unwind_Word> stack;
    _Unwind_Backtrace(unwindCallback, (void*)&stack);
    dump.append("                                                               \n"
                "*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***\n"
                "pid: 17980, tid: 17980, name: callstack.dump  >>> callstack.dump <<<\n"
                "signal 11 (SIGSEGV), code 1 (SEGV_MAPERR), fault addr 0x0\n"
                "r0 00000000  r1 00000000  r2 00000001  r3 00000001\n"
                "r4 e8efe008  r5 e0537b99  r6 ff970b88  r7 ff970a98\n"
                "r8 ff970de0  r9 e7904400  sl e790448c  fp ff970b14\n"
                "ip e8ef985c  sp ff970a60  lr e8eca00f  pc e0537d86  cpsr 200b0030\n"
                "backtrace:\n");

    char buff[256];
    for (size_t i = 0; i < stack.size(); i++) {
        Dl_info info;
        if (!dladdr((void*)stack[i], &info)) {
            continue;
        }
        int addr = (char*)stack[i] - (char*)info.dli_fbase - 1;
        if (info.dli_sname == NULL || strlen(info.dli_sname) == 0) {
            sprintf(buff, "#%02x pc %08x  %s\n", i, addr, info.dli_fname);
        } else {
            sprintf(buff, "#%02x pc %08x  %s (%s+00)\n", i, addr, info.dli_fname, info.dli_sname);
        }
        dump.append(buff);
    }
}

void callstackLogcat(int prio, const char* tag) {
    std::string dump;
    callstackDump(dump);
    __android_log_print(prio, tag, "%s", dump.c_str());
}



static void ki(int num) {
    int status;
    status = kill(num, SIGKILL);
    if (status == -1)
        printf("kill faild\n");
    wait(&status);
    if (WIFSIGNALED(status))
        printf("chile process receive signal %d\n", WTERMSIG(status));
}

int has_debugger() {
    char buff1[24], buff2[26];
    FILE *f;

    snprintf(buff1, 24, "/proc/%d/status", getppid());
    f = fopen(buff1, "r");
    // the first line in status is name
    if (f == NULL) {
        printf("can not load file!");
        return 1;
    }
    while (!feof(f)) {
        fgets(buff2, 26, f);
        int has_gdb = (strstr(buff2, "gdb") || strstr(buff2, "ltrace") || strstr(buff2, "strace"));
        if (has_gdb != 0) {
            printf("debugger attached!\n");
            ki(getpid());
            return has_gdb;
        }
    }
    fclose(f);

    return 0;
}

static int debugger_present = -1;

static void sigtrap_handler(int signum) {
    debugger_present = 0;
    signal(SIGTRAP, SIG_IGN);
}

int has_debugger2() {
    if (-1 == debugger_present) {
        debugger_present = 1;
        signal(SIGTRAP, sigtrap_handler);
        raise(SIGTRAP);
    }

    return debugger_present;
}

int has_debugger3() {
    int debugger;
    if (ptrace(PTRACE_TRACEME, 0, NULL, NULL) == 0) {
        // return 0 means success, so no debugger is attached
        debugger = 0;
    } else {
        debugger = 1;
    }
    return debugger;
}


JNIEXPORT jstring JNICALL gck(JNIEnv *jniEnv, jobject thi, jobject thiz) {
    std::string pk = base64d(base64e("com.vms.zjy",sizeof("com.vms.zjy")));
    std::string si = base64d(base64e("pk",sizeof("pk")));

    jclass jclass1 = jniEnv->GetObjectClass(thiz);
    jmethodID jmethodId = jniEnv->GetMethodID(jclass1, "getPackageName", "()Ljava/lang/String;");
    jobject jobject1 = jniEnv->CallObjectMethod(thiz, jmethodId);
    if (jobject1 == NULL) return jniEnv->NewStringUTF("0");
    auto jstring1 = (jstring) jobject1;
    jniEnv->DeleteLocalRef(jobject1);

    jmethodID jmethodId1 = jniEnv->GetMethodID(jclass1, "getPackageManager",
                                               "()Landroid/content/pm/PackageManager;");
    jobject jobject2 = jniEnv->CallObjectMethod(thiz, jmethodId1);
    if (jobject2 == NULL) return jniEnv->NewStringUTF("0");
    jclass jclass2 = jniEnv->GetObjectClass(jobject2);
    jmethodID jmethodId2 = jniEnv->GetMethodID(jclass2, "getPackageInfo",
                                               "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jobject2 = jniEnv->CallObjectMethod(jobject2, jmethodId2, jniEnv->NewStringUTF(pk.c_str()),
                                        0x40);
    if (jobject2 == NULL) return jniEnv->NewStringUTF("0");
    jclass2 = jniEnv->GetObjectClass(jobject2);

    jfieldID jfieldId = jniEnv->GetFieldID(jclass2, "signatures",
                                           "[Landroid/content/pm/Signature;");
    jobjectArray jarray1 = (jobjectArray) jniEnv->GetObjectField(jobject2, jfieldId);
    jobject2 = jniEnv->GetObjectArrayElement(jarray1, 0);
    jclass2 = jniEnv->GetObjectClass(jobject2);
    jmethodId2 = jniEnv->GetMethodID(jclass2, "toCharsString", "()Ljava/lang/String;");
    jstring jstring2 = (jstring) jniEnv->CallObjectMethod(jobject2, jmethodId2);

    LOGD("jstring1",jstring1);
    LOGD("jstring2",jstring2);
    if (strcmp(pk.c_str(), jniEnv->GetStringUTFChars(jstring1, NULL)) != 0 ||
        strcmp(si.c_str(), jniEnv->GetStringUTFChars(jstring2, NULL)) != 0) {
        return jniEnv->NewStringUTF("1");
    }
    return jniEnv->NewStringUTF("-1");
}

JNIEXPORT jstring JNICALL gothread(JNIEnv *jniEnv, jobject thiz) {
    has_debugger2();
    if (has_debugger3() || debugger_present) {
        ki(getpid());
        return jniEnv->NewStringUTF("1");
    }
    has_debugger();
   return jniEnv->NewStringUTF("-1");

}


static JNINativeMethod mMethods[] = {
        {"ck", "(Landroid/content/Context;)Ljava/lang/String;", (void *) gck},
        {"thread", "()Ljava/lang/String;", (void *) gothread},
};

JavaVM *vm = nullptr;

static int
registerNativeMethods(JNIEnv *env, const char *className, const JNINativeMethod *gMethods,
                      int numMethods) {
    jclass clazz;
    clazz = env->FindClass(className);
    if (clazz == NULL) {
        return -1;
    }

    if (env->RegisterNatives(clazz, gMethods, numMethods) < 0) {
        return -1;
    }

    return 0;
}

static int registerNative(JNIEnv *env) {
    return registerNativeMethods(env, "com/vms/ykt/Util/Tool", mMethods,
                                 sizeof(mMethods) / sizeof(mMethods[0]));
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm, void *reserved) {
    LOGE("JNI_OnLoad ------------------------------ ");

    vm=jvm;

    JNIEnv *env = NULL;

    jint result = -1;
    /*
     * JavaVM::GetEnv 原型为 jint (*GetEnv)(JavaVM*, void**, jint);
     * GetEnv()函数返回的  Jni 环境对每个线程来说是不同的，
     * 由于Dalvik虚拟机通常是Multi-threading的。每一个线程调用JNI_OnLoad()时，
     * 所用的JNI Env是不同的，因此我们必须在每次进入函数时都要通过vm->GetEnv重新获取
     */
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return result;
    }

    if (registerNative(env) != JNI_OK) {
        return result;
    }

    result = JNI_VERSION_1_6;
    return result;
}





static const std::string base64_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                        "abcdefghijklmnopqrstuvwxyz"
                                        "0123456789+/";

static inline bool is_base64(const char c) {
    return (isalnum(c) || (c == '+') || (c == '/'));
}
//加密
string base64e(const char *bytes_to_encode, unsigned int in_len) {
    std::string ret;
    int i = 0;
    int j = 0;
    unsigned char char_array_3[3];
    unsigned char char_array_4[4];

    while (in_len--) {
        char_array_3[i++] = *(bytes_to_encode++);
        if (i == 3) {
            char_array_4[0] = (char_array_3[0] & 0xfc) >> 2;
            char_array_4[1] = ((char_array_3[0] & 0x03) << 4) + ((char_array_3[1] & 0xf0) >> 4);
            char_array_4[2] = ((char_array_3[1] & 0x0f) << 2) + ((char_array_3[2] & 0xc0) >> 6);
            char_array_4[3] = char_array_3[2] & 0x3f;
            for (i = 0; (i < 4); i++) {
                ret += base64_chars[char_array_4[i]];
            }
            i = 0;
        }
    }
    if (i) {
        for (j = i; j < 3; j++) {
            char_array_3[j] = '\0';
        }

        char_array_4[0] = (char_array_3[0] & 0xfc) >> 2;
        char_array_4[1] = ((char_array_3[0] & 0x03) << 4) + ((char_array_3[1] & 0xf0) >> 4);
        char_array_4[2] = ((char_array_3[1] & 0x0f) << 2) + ((char_array_3[2] & 0xc0) >> 6);
        char_array_4[3] = char_array_3[2] & 0x3f;

        for (j = 0; (j < i + 1); j++) {
            ret += base64_chars[char_array_4[j]];
        }

        while ((i++ < 3)) {
            ret += '=';
        }

    }
    return ret;
}

//解密
std::string base64d(std::string const &encoded_string) {
    int in_len = (int) encoded_string.size();
    int i = 0;
    int j = 0;
    int in_ = 0;
    unsigned char char_array_4[4], char_array_3[3];
    std::string ret;

    while (in_len-- && (encoded_string[in_] != '=') && is_base64(encoded_string[in_])) {
        char_array_4[i++] = encoded_string[in_];
        in_++;
        if (i == 4) {
            for (i = 0; i < 4; i++)
                char_array_4[i] = base64_chars.find(char_array_4[i]);

            char_array_3[0] = (char_array_4[0] << 2) + ((char_array_4[1] & 0x30) >> 4);
            char_array_3[1] = ((char_array_4[1] & 0xf) << 4) + ((char_array_4[2] & 0x3c) >> 2);
            char_array_3[2] = ((char_array_4[2] & 0x3) << 6) + char_array_4[3];

            for (i = 0; (i < 3); i++)
                ret += char_array_3[i];
            i = 0;
        }
    }
    if (i) {
        for (j = i; j < 4; j++)
            char_array_4[j] = 0;

        for (j = 0; j < 4; j++)
            char_array_4[j] = base64_chars.find(char_array_4[j]);

        char_array_3[0] = (char_array_4[0] << 2) + ((char_array_4[1] & 0x30) >> 4);
        char_array_3[1] = ((char_array_4[1] & 0xf) << 4) + ((char_array_4[2] & 0x3c) >> 2);
        char_array_3[2] = ((char_array_4[2] & 0x3) << 6) + char_array_4[3];

        for (j = 0; (j < i - 1); j++) ret += char_array_3[j];
    }

    return ret;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_vms_ykt_Util_Tool_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}