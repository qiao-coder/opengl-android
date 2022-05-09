package com.example.opengl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengl.render.CoordinateSystemsDepthRender;
import com.example.opengl.render.CoordinateSystemsMultipleRender;

/**
 * @author wuzhanqiao
 * @date 2022/5/9.
 */
public class CoordinateSystemsMultiple extends GLSurfaceView {
    public CoordinateSystemsMultiple(Context context) {
        this(context, null);
    }

    public CoordinateSystemsMultiple(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        setRenderer(new CoordinateSystemsMultipleRender(context));
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }
}
