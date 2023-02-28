//
// Created by wuzhanqiao on 2023/1/20.
//
#if __ANDROID__ and __ANDROID_API__ > 9 and defined(AI_CONFIG_ANDROID_JNI_ASSIMP_MANAGER_SUPPORT)
#ifndef AI__ANDROIDIOSYSTEM_H
#define AI__ANDROIDIOSYSTEM_H

#include <assimp/DefaultIOSystem.h>
#include <android/asset_manager.h>

namespace Assimp {

    class ASSIMP_API AndroidIOSystem : public DefaultIOSystem {

    public:
        AAssetManager* mAssetManager;

        AndroidIOSystem(AAssetManager *assetManager);

        ~AndroidIOSystem();

        bool ExtractAsset(std::string fileName, std::string cacheDir);
    };
}

#endif //AI__ANDROIDIOSYSTEM_H
#endif //__ANDROID__ and __ANDROID_API__ > 9 and defined(AI_CONFIG_ANDROID_JNI_ASSIMP_MANAGER_SUPPORT)
