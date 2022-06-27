package com.example.opengl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.render.MaterialsRender;

/**
 * @author wuzhanqiao
 * @date 2022/4/12.
 */
public class Materials extends BaseGLSurfaceView<MaterialsRender> {

    public Materials(Context context) {
        super(context);
    }

    public Materials(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
