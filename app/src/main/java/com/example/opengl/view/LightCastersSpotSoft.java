package com.example.opengl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.render.LightCastersSpotSoftRender;

/**
 * @author wuzhanqiao
 * @date 2022/6/30.
 */
public class LightCastersSpotSoft extends BaseGLSurfaceView<LightCastersSpotSoftRender> {
    public LightCastersSpotSoft(Context context) {
        super(context);
    }

    public LightCastersSpotSoft(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
