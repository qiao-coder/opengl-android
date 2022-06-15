package com.example.opengl.view;

import static com.example.opengl.data.AngleType.PITCH;
import static com.example.opengl.data.AngleType.YAW;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.opengl.data.AngleType;

/**
 * @author wuzhanqiao
 * @date 2022/6/10.
 */
public class AngleView extends ControlView {
    private float yaw = -90.0f;
    private float pitch = 0.0f;
    private OnAdjustListener onAdjustListener;

    public AngleView(Context context) {
        this(context, null);
    }

    public AngleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AngleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onMove(float cx, float cy, float x, float y) {
        if (onAdjustListener == null) return;
        float dx = x - cx;
        float dy = y - cy;
        float yawOffset = getAngleOffset(dx);
        if (yawOffset != 0) {
            yaw += yawOffset;
            onAdjustListener.adjust(YAW, yaw);
        }
        float pitchOffset = getAngleOffset(dy);
        if (pitchOffset != 0) {
            pitch += pitchOffset;
            if (pitch > 89) {
                pitch = 89;
            } else if (pitch < -89) {
                pitch = -89;
            }
            onAdjustListener.adjust(PITCH, pitch);
        }
    }

    private float getAngleOffset(float d) {
        int sign = d > 0 ? 1 : -1;
        d = Math.abs(d);
        float speed = 0.0f;
        if (d > scopeCircleRadius / 5 && d <= scopeCircleRadius * 2 / 5) {
            speed = 0.1f;
        } else if (d > scopeCircleRadius * 2 / 5 && d <= scopeCircleRadius * 3 / 5) {
            speed = 0.2f;
        } else if (d > scopeCircleRadius * 3 / 5 && d <= scopeCircleRadius * 4 / 5) {
            speed = 0.4f;
        } else if (d > scopeCircleRadius * 4 / 5) {
            speed = 0.8f;
        }
        return speed * sign;
    }

    public void setOnAdjustListener(OnAdjustListener listener) {
        onAdjustListener = listener;
    }

    public interface OnAdjustListener {
        void adjust(AngleType type, float angle);
    }
}
