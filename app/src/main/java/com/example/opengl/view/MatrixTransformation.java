package com.example.opengl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengl.render.MatrixTransformRender;

/**
 * @author wuzhanqiao
 * @date 2022/5/6.
 */
public class MatrixTransformation extends GLSurfaceView {
    public MatrixTransformation(Context context) {
        this(context, null);
    }

    public MatrixTransformation(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 16, 8);
        setRenderer(new MatrixTransformRender(context));
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }
}
