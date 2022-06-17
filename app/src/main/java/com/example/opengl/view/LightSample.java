package com.example.opengl.view;

import static com.example.opengl.data.Direction.DOWN;
import static com.example.opengl.data.Direction.LEFT;
import static com.example.opengl.data.Direction.RIGHT;
import static com.example.opengl.data.Direction.UP;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.opengl.data.Direction;
import com.example.opengl.render.LightSampleRender;

/**
 * @author wuzhanqiao
 * @date 2022/4/12.
 */
public class LightSample extends GLSurfaceView {
    private static final String TAG = "LightSample";

    private LightSampleRender lightSampleRender;
    private float lastDist = 0.0f;
    private final static float minFov = 1.0f;
    private final static float maxFov = 45.0f;
    private float fov = 45.0f;

    public LightSample(Context context) {
        this(context, null);
    }

    public LightSample(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        lightSampleRender = new LightSampleRender(context);
        setRenderer(lightSampleRender);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        setFocusable(true);
    }

    public void move(Direction d) {
        lightSampleRender.move(d, 0.05f);
        requestRender();
    }

    public void move(Direction d, float cameraSpeed) {
        lightSampleRender.move(d, cameraSpeed);
        requestRender();
    }

    public void setYaw(float yaw) {
        lightSampleRender.setYaw(yaw);
        requestRender();
    }

    public void setPitch(float pitch) {
        lightSampleRender.setPitch(pitch);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN: {
                //刚好双指
                if (event.getPointerCount() == 2) {
                    lastDist = (float) getTwoFingersDistance(event);
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (event.getPointerCount() == 2) {
                    float dist = getTwoFingersDistance(event);
                    float offset = getFovOffset(lastDist - dist);
                    fov = fov + offset;
                    lastDist = dist;
                    if (fov > maxFov) {
                        fov = maxFov;
                    } else if (fov < minFov) {
                        fov = minFov;
                    }
                    lightSampleRender.setFov(fov);
                    requestRender();
                }
                break;
            }
        }
        return true;
    }

    private float getTwoFingersDistance(MotionEvent event) {
        float x0 = event.getX(0);
        float y0 = event.getY(0);
        float x1 = event.getX(1);
        float y1 = event.getY(1);
        return (float) Math.sqrt(Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2));
    }

    private float getFovOffset(float d) {
        int max = Math.min(getWidth(), getHeight());
        float unit = (maxFov - minFov) / max;
        return d * unit;
    }
}
