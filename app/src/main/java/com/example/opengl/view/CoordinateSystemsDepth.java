package com.example.opengl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengl.render.CoordinateSystemsDepthRender;

/**
 * @author wuzhanqiao
 * @date 2022/5/9.
 */
public class CoordinateSystemsDepth extends GLSurfaceView {
    public CoordinateSystemsDepth(Context context) {
        this(context, null);
    }

    public CoordinateSystemsDepth(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        setRenderer(new CoordinateSystemsDepthRender(context));
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }
}
