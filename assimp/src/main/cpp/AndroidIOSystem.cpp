//
// Created by wuzhanqiao on 2023/1/20.
//
#include <assimp/config.h>
#include <assimp/port/AndroidJNI/AndroidIOSystem.h>
#include <android/asset_manager.h>
#include <android/log.h>
#include <libgen.h>
#include <fstream>

using namespace Assimp;

AndroidIOSystem::AndroidIOSystem(AAssetManager *assetManager) {
    mAssetManager = assetManager;
}

AndroidIOSystem::~AndroidIOSystem() {

}

// Create the directory for the extracted resource
static int mkpath(std::string path, mode_t mode) {
    if (mkdir(path.c_str(), mode) == -1) {
        switch(errno) {
            case ENOENT:
                if (mkpath(path.substr(0, path.find_last_of('/')), mode) == -1)
                    return -1;
                else
                    return mkdir(path.c_str(), mode);
            case EEXIST:
                return 0;
            default:
                return -1;
        }
    }

    return 0;
}

bool AndroidIOSystem::ExtractAsset(std::string fileName, std::string cacheDir) {
    std::string cachePath = cacheDir + DefaultIOSystem::getOsSeparator() + fileName;
    DefaultIOSystem io;
    // Do not extract if extracted already
    if (io.Exists(cachePath.c_str())) {
        __android_log_print(ANDROID_LOG_VERBOSE, "Assimp", "Asset already extracted");
        return false;
    }

    // Open file
    AAsset *asset = AAssetManager_open(mAssetManager, fileName.c_str(),
                                       AASSET_MODE_UNKNOWN);
    std::vector<char> assetContent;

    if (asset == nullptr) {
        __android_log_print(ANDROID_LOG_ERROR, "assimp", "Asset not found: %s", fileName.c_str());
        return false;
    }

    // Find size
    off_t assetSize = AAsset_getLength(asset);

    // Prepare input buffer
    assetContent.resize(assetSize);

    // Store input buffer
    AAsset_read(asset, &assetContent[0], assetSize);

    // Close
    AAsset_close(asset);

    // Prepare directory for output buffer
    std::string directoryNewPath = cachePath;
    directoryNewPath = dirname(&directoryNewPath[0]);

    if (mkpath(directoryNewPath, S_IRUSR | S_IWUSR | S_IXUSR) == -1) {
        __android_log_print(ANDROID_LOG_ERROR, "assimp",
                            "Can not create the directory for the output file");
    }

    // Prepare output buffer
    std::ofstream assetExtracted(cachePath.c_str(), std::ios::out | std::ios::binary);
    if (!assetExtracted) {
        __android_log_print(ANDROID_LOG_ERROR, "assimp", "Can not open output file");
    }

    // Write output buffer into a file
    assetExtracted.write(&assetContent[0], assetContent.size());
    assetExtracted.close();

    __android_log_print(ANDROID_LOG_VERBOSE, "Assimp", "Asset extracted");

    return true;
}