package com.example.opengl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengl.render.DynamicColorTriangleRender;


/**
 * @author wuzhanqiao
 * @date 2022/4/12.
 */
public class DynamicColorTriangle extends GLSurfaceView {
    public DynamicColorTriangle(Context context) {
        this(context, null);
    }

    public DynamicColorTriangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        setRenderer(new DynamicColorTriangleRender(context));
        //为了观察颜色变化，采用持续渲染模式
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }
}
