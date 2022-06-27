package com.example.opengl.base;

import android.content.Context;
import android.graphics.Canvas;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.opengl.data.Direction;

/**
 * @author wuzhanqiao
 * @date 2022/6/22.
 */
public class BaseGLSurfaceView<T extends BaseRender> extends GLSurfaceView {
    private final String TAG = getClass().getSimpleName();

    private T render;

    public BaseGLSurfaceView(Context context) {
        super(context);
    }

    public BaseGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRender(T render,int renderMode) {
        this.render = render;
        setEGLContextClientVersion(3);
        setRenderer(render);
        setRenderMode(renderMode);
    }

    public void move(Direction d) {
        render.move(d, 0.05f);
        requestRender();
    }

    public void move(Direction d, float cameraSpeed) {
        render.move(d, cameraSpeed);
        requestRender();
    }

    public void setYaw(float yaw) {
        render.setYaw(yaw);
        requestRender();
    }

    public void setPitch(float pitch) {
        render.setPitch(pitch);
        requestRender();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int max = Math.min(getWidth(), getHeight());
        boolean zoom = render.handleZoom(event, max);
        if (zoom) requestRender();
        return true;
    }
}
