package com.example.opengl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.example.opengl.render.CameraCircleRender;

/**
 * @author wuzhanqiao
 * @date 2022/5/11.
 */
public class CameraCircle extends GLSurfaceView {

    private CameraCircleRender cameraCircleRender;

    public CameraCircle(Context context) {
        this(context, null);
    }

    public CameraCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        cameraCircleRender = new CameraCircleRender(context);
        setRenderer(cameraCircleRender);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    public void setRadius(float radius) {
        cameraCircleRender.setRadius(radius);
    }

        public void setCameraY(float y) {
        cameraCircleRender.setCameraY(y);
    }

    public void setCenterX(float x) {
        cameraCircleRender.setCenterX(x);
    }

    public void setCenterY(float y) {
        cameraCircleRender.setCenterY(y);
    }

    public void setCenterZ(float z) {
        cameraCircleRender.setCenterZ(z);
    }

    public void setUpX(float x) {
        cameraCircleRender.setUpX(x);
    }

    public void setUpY(float y) {
        cameraCircleRender.setUpY(y);
    }

    public void setUpZ(float z) {
        cameraCircleRender.setUpZ(z);
    }
}
