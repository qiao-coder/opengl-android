package com.example.opengl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengl.render.LaughingWoodenBoxRender;

/**
 * @author wuzhanqiao
 * @date 2022/5/3.
 */
public class LaughingWoodenBox extends GLSurfaceView {
    public LaughingWoodenBox(Context context) {
        this(context, null);
    }

    public LaughingWoodenBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 16, 8);
        setRenderer(new LaughingWoodenBoxRender(context));
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
