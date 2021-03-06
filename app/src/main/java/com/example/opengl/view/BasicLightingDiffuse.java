package com.example.opengl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.render.BasicLightingDiffuseRender;

/**
 * @author wuzhanqiao
 * @date 2022/4/12.
 */
public class BasicLightingDiffuse extends BaseGLSurfaceView<BasicLightingDiffuseRender> {

    public BasicLightingDiffuse(Context context) {
        super(context);
    }

    public BasicLightingDiffuse(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
