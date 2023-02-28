package com.example.opengl.render;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author Q
 * @date 2023/2/28.
 */
public class WindowRender implements GLSurfaceView.Renderer {
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }
}
