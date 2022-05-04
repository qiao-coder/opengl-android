package com.example.opengl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengl.render.DynamicColorTriangleRender;
import com.example.opengl.render.RGBTriangleRender;

/**
 * @author wuzhanqiao
 * @date 2022/4/29.
 */
public class RGBTriangle extends GLSurfaceView {
    public RGBTriangle(Context context) {
        this(context, null);
    }

    public RGBTriangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        setRenderer(new RGBTriangleRender(context));
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
