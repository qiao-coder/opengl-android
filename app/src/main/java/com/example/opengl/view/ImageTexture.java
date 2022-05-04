package com.example.opengl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengl.render.RectangleRender;
import com.example.opengl.render.TextureRender;

/**
 * @author wuzhanqiao
 * @date 2022/5/3.
 */
public class ImageTexture extends GLSurfaceView {
    public ImageTexture(Context context) {
        this(context, null);
    }

    public ImageTexture(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        setEGLConfigChooser(8, 8, 8, 8, 16, 8);
        setRenderer(new TextureRender(context));
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
