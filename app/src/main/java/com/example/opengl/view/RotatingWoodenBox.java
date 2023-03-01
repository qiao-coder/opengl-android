package com.example.opengl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengl.render.RotatingWoodenBoxRender;

/**
 * @author wuzhanqiao
 * @date 2022/5/6.
 */
public class RotatingWoodenBox extends GLSurfaceView {
    public RotatingWoodenBox(Context context) {
        this(context, null);
    }

    public RotatingWoodenBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 16, 8);
        setRenderer(new RotatingWoodenBoxRender(context));
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }
}
