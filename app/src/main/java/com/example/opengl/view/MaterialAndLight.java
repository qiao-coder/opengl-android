package com.example.opengl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.render.MaterialAndLightRender;

/**
 * @author wuzhanqiao
 * @date 2022/4/12.
 */
public class MaterialAndLight extends BaseGLSurfaceView<MaterialAndLightRender> {

    public MaterialAndLight(Context context) {
        super(context);
    }

    public MaterialAndLight(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
