package com.qiao.assimp;

import android.content.res.AssetManager;

public class Assimp {

    // Used to load the 'assimp' library on application startup.
    static {
        System.loadLibrary("assimp-jni");
    }

    /**
     * A native method that is implemented by the 'assimp' native library,
     * which is packaged with this application.
     */
    public native void create(AssetManager assetManager,String name,String cacheDir);
}