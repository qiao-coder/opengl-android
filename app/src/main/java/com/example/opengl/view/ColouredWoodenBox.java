package com.example.opengl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengl.render.ColouredWoodenBoxRender;
import com.example.opengl.render.WoodenBoxRender;

/**
 * @author wuzhanqiao
 * @date 2022/4/28.
 */
public class ColouredWoodenBox extends GLSurfaceView {
    public ColouredWoodenBox(Context context) {
        this(context, null);
    }

    public ColouredWoodenBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 16, 8);
        setRenderer(new ColouredWoodenBoxRender(context));
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
