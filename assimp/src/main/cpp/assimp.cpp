#include <jni.h>
#include <string>
#include <assimp/Importer.hpp>
#include <assimp/port/AndroidJNI/AndroidJNIIOSystem.h>
#include <assimp/port/AndroidJNI/AndroidIOSystem.h>
#include <assimp/DefaultIOSystem.h>
#include "assimp/BlobIOSystem.h"


extern "C" JNIEXPORT void JNICALL
Java_com_qiao_assimp_Assimp_create(
        JNIEnv *env,
        jobject /* this */, jobject assetManager, jstring name, jstring cacheDir) {

    jboolean isCopy;
    const char *c_name = env->GetStringUTFChars(name, &isCopy);
    const char *c_cacheDir = env->GetStringUTFChars(cacheDir, &isCopy);

    Assimp::AndroidIOSystem *ioSystem = new Assimp::AndroidIOSystem(
            AAssetManager_fromJava(env, assetManager));
    ioSystem->ExtractAsset(c_name, c_cacheDir);

//    AAssetManager *aAssetManager = AAssetManager_fromJava(env, assetManager);
//    Importer *importer = new Importer();
}