package com.example.opengl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.render.LightSampleRender;

/**
 * @author wuzhanqiao
 * @date 2022/4/12.
 */
public class LightSample extends BaseGLSurfaceView<LightSampleRender> {

    public LightSample(Context context) {
        super(context);
    }

    public LightSample(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
