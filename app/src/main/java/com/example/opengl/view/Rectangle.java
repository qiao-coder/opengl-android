package com.example.opengl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengl.render.RectangleRender;

/**
 * @author wuzhanqiao
 * @date 2022/4/28.
 */
public class Rectangle extends GLSurfaceView {
    public Rectangle(Context context) {
        this(context, null);
    }

    public Rectangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 16, 8);
        setRenderer(new RectangleRender());
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
