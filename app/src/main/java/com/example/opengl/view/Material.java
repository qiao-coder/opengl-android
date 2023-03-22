package com.example.opengl.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.render.MaterialRender;

/**
 * @author wuzhanqiao
 * @date 2022/4/12.
 */
public class Material extends BaseGLSurfaceView<MaterialRender> {

    public Material(Context context) {
        super(context);
    }

    public Material(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
