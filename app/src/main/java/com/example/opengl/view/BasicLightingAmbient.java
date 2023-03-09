package com.example.opengl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.render.BasicLightingAmbientRender;

/**
 * @author wuzhanqiao
 * @date 2022/4/12.
 */
public class BasicLightingAmbient extends BaseGLSurfaceView<BasicLightingAmbientRender> {

    public BasicLightingAmbient(Context context) {
        super(context);
    }

    public BasicLightingAmbient(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
