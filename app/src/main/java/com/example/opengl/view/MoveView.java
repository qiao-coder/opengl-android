package com.example.opengl.view;

import static com.example.opengl.data.Direction.DOWN;
import static com.example.opengl.data.Direction.LEFT;
import static com.example.opengl.data.Direction.RIGHT;
import static com.example.opengl.data.Direction.UP;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.opengl.data.Direction;

/**
 * @author wuzhanqiao
 * @date 2022/6/10.
 */
public class MoveView extends ControlView {
    private OnMoveListener onMoveListener;

    public MoveView(Context context) {
        this(context, null);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onMove(float cx, float cy, float x, float y) {
        if (onMoveListener == null) return;
        float dx = x - cx;
        float dy = y - cy;
        Direction horizontal = dx >= 0 ? RIGHT : LEFT;
        if (getSpeed(dx) > 0.0f) onMoveListener.move(horizontal, getSpeed(dx));
        Direction vertical = dy >= 0 ? DOWN : UP;
        if (getSpeed(dy) > 0.0f) onMoveListener.move(vertical, getSpeed(dy));
    }

    private float getSpeed(float d) {
        d = Math.abs(d);
        float speed = 0.0f;
        if (d > scopeCircleRadius / 5 && d <= scopeCircleRadius * 2 / 5) {
            speed = 0.02f;
        } else if (d > scopeCircleRadius * 2 / 5 && d <= scopeCircleRadius * 3 / 5) {
            speed = 0.04f;
        } else if (d > scopeCircleRadius * 3 / 5) {
            speed = 0.08f;
        }
        return speed;
    }

    public void setOnMoveListener(OnMoveListener listener) {
        onMoveListener = listener;
    }

    public interface OnMoveListener {
        void move(Direction d, float speed);
    }
}
