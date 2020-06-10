//
// Created by 高飞 on 2020-01-19.
//

#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

Java_com_gaofei_app_act_TestAct_stringFromJNI(
        JNIEnv *env,
        jobject ) {
    std::string hello = "Hello from C++";
    return (*env).NewStringUTF(hello.c_str());
}

jstring pointTest(JNIEnv* env) {
    int b = 4;
    int *a = &b;
    jstring c = env->NewStringUTF("aa");
    std::string hello = "Hello from C++";
    return c;
}



