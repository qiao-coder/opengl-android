package com.example.opengl.view;

import static com.example.opengl.data.Direction.DOWN;
import static com.example.opengl.data.Direction.LEFT;
import static com.example.opengl.data.Direction.RIGHT;
import static com.example.opengl.data.Direction.UP;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

import com.example.opengl.data.Direction;
import com.example.opengl.render.CameraViewRender;

/**
 * @author wuzhanqiao
 * @date 2022/6/8.
 */
public class CameraView extends GLSurfaceView {

    private CameraViewRender cameraViewRender;

    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        cameraViewRender = new CameraViewRender(context);
        setRenderer(cameraViewRender);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        setFocusable(true);
    }

    public void move(Direction d) {
        cameraViewRender.move(d, 0.05f);
        requestRender();
    }

    public void move(Direction d, float cameraSpeed) {
        cameraViewRender.move(d, cameraSpeed);
        requestRender();
    }

    public void setYaw(float yaw) {
        cameraViewRender.setYaw(yaw);
        requestRender();
    }

    public void setPitch(float pitch) {
        cameraViewRender.setPitch(pitch);
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
