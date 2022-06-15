package com.example.opengl.view;

import static com.example.opengl.data.Direction.DOWN;
import static com.example.opengl.data.Direction.LEFT;
import static com.example.opengl.data.Direction.RIGHT;
import static com.example.opengl.data.Direction.UP;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.example.opengl.data.Direction;
import com.example.opengl.render.CameraPositionRender;

/**
 * @author wuzhanqiao
 * @date 2022/5/11.
 */
public class CameraPosition extends GLSurfaceView {

    private CameraPositionRender cameraPositionRender;

    public CameraPosition(Context context) {
        this(context, null);
    }

    public CameraPosition(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        cameraPositionRender = new CameraPositionRender(context);
        setRenderer(cameraPositionRender);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        setFocusable(true);
    }

    public void move(Direction d) {
        cameraPositionRender.move(d);
        requestRender();
    }

    public void move(Direction d, float cameraSpeed) {
        cameraPositionRender.move(d, cameraSpeed);
        requestRender();
    }

    /**
     * 在控件里监听dispatchKeyEvent，需要setFocusable(true)
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_UP:
                move(UP);
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                move(DOWN);
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                move(LEFT);
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                move(RIGHT);
                return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
