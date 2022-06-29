package com.example.opengl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.render.LightCastersDirectionalRender;

/**
 * @author wuzhanqiao
 * @date 2022/6/28.
 */
public class LightCastersDirectional extends BaseGLSurfaceView<LightCastersDirectionalRender> {
    public LightCastersDirectional(Context context) {
        super(context);
    }

    public LightCastersDirectional(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
