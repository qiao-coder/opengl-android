package com.example.opengl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengl.render.CoordinateSystemsRender;
import com.example.opengl.render.DynamicColorTriangleRender;

/**
 * @author wuzhanqiao
 * @date 2022/5/9.
 */
public class CoordinateSystems extends GLSurfaceView {
    public CoordinateSystems(Context context) {
        this(context, null);
    }

    public CoordinateSystems(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        setRenderer(new CoordinateSystemsRender(context));
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
