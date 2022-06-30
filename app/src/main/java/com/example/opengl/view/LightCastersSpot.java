package com.example.opengl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.render.LightCastersSpotRender;

/**
 * @author wuzhanqiao
 * @date 2022/6/30.
 */
public class LightCastersSpot extends BaseGLSurfaceView<LightCastersSpotRender> {
    public LightCastersSpot(Context context) {
        super(context);
    }

    public LightCastersSpot(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
