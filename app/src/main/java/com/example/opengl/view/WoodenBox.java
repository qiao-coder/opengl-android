package com.example.opengl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengl.render.WoodenBoxRender;

/**
 * @author wuzhanqiao
 * @date 2022/4/28.
 */
public class WoodenBox extends GLSurfaceView {
    public WoodenBox(Context context) {
        this(context, null);
    }

    public WoodenBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 16, 8);
        setRenderer(new WoodenBoxRender(context));
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
