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
import com.example.opengl.render.CameraKeyboardRender;

/**
 * @author wuzhanqiao
 * @date 2022/5/11.
 */
public class CameraKeyboard extends GLSurfaceView {

    private CameraKeyboardRender cameraKeyboardRender;

    public CameraKeyboard(Context context) {
        this(context, null);
    }

    public CameraKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        cameraKeyboardRender = new CameraKeyboardRender(context);
        setRenderer(cameraKeyboardRender);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
        setFocusable(true);
    }

    public void move(Direction d) {
        cameraKeyboardRender.move(d);
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
