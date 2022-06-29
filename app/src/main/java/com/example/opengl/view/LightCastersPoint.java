package com.example.opengl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.render.LightCastersPointRender;

/**
 * @author wuzhanqiao
 * @date 2022/6/29.
 */
public class LightCastersPoint extends BaseGLSurfaceView<LightCastersPointRender> {
    public LightCastersPoint(Context context) {
        super(context);
    }

    public LightCastersPoint(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
