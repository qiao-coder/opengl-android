package com.example.opengl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.render.MaterialWithLightChangeRender;

/**
 * @author wuzhanqiao
 * @date 2022/4/12.
 */
public class MaterialWithLightChange extends BaseGLSurfaceView<MaterialWithLightChangeRender> {

    public MaterialWithLightChange(Context context) {
        super(context);
    }

    public MaterialWithLightChange(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
