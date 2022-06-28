package com.example.opengl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.render.LightingMapsSpecularRender;

/**
 * @author wuzhanqiao
 * @date 2022/6/28.
 */
public class LightingMapsSpecular extends BaseGLSurfaceView<LightingMapsSpecularRender> {
    public LightingMapsSpecular(Context context) {
        super(context);
    }

    public LightingMapsSpecular(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
