#include <jni.h>
#include <string>
#include <iostream>
#include <android/log.h>


#define LOG_TAG "NativeLib"
#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__))

extern "C" JNIEXPORT jstring JNICALL
Java_com_gabrielly_healthcare_MainActivity_onButtonClick(JNIEnv* env,jobject /* this */) {

    LOGI("Button Add Clicked!");

    return nullptr;
}


