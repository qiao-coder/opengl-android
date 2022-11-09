package com.example.opengl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.render.MultipleLightsRender;

/**
 * @author wuzhanqiao
 * @date 2022/6/30.
 */
public class MultipleLights extends BaseGLSurfaceView<MultipleLightsRender> {
    public MultipleLights(Context context) {
        super(context);
    }

    public MultipleLights(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
