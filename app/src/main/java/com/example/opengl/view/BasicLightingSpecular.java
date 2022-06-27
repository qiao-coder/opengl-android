package com.example.opengl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.render.BasicLightingSpecularRender;

/**
 * @author wuzhanqiao
 * @date 2022/4/12.
 */
public class BasicLightingSpecular extends BaseGLSurfaceView<BasicLightingSpecularRender> {

    public BasicLightingSpecular(Context context) {
        super(context);
    }

    public BasicLightingSpecular(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
