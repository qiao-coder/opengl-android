package com.example.opengl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengl.render.RedTriangleRender;

/**
 * @author wuzhanqiao
 * @date 2022/4/29.
 */
public class RedTriangle extends GLSurfaceView {
    public RedTriangle(Context context) {
        this(context, null);
    }

    public RedTriangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        setRenderer(new RedTriangleRender(context));
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
