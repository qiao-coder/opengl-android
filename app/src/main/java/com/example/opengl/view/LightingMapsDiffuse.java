package com.example.opengl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.render.LightingMapsDiffuseRender;

/**
 * @author wuzhanqiao
 * @date 2022/6/27.
 */
public class LightingMapsDiffuse extends BaseGLSurfaceView<LightingMapsDiffuseRender> {
    public LightingMapsDiffuse(Context context) {
        super(context);
    }

    public LightingMapsDiffuse(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
