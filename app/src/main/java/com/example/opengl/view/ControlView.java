package com.example.opengl.view;

import static com.example.opengl.data.Direction.DOWN;
import static com.example.opengl.data.Direction.LEFT;
import static com.example.opengl.data.Direction.RIGHT;
import static com.example.opengl.data.Direction.UP;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.opengl.data.Direction;

/**
 * @author wuzhanqiao
 * @date 2022/5/12.
 */
public abstract class ControlView extends View {
    private static final String TAG = "MoveView";

    private Paint scopePaint = new Paint();
    private Paint fingerPaint = new Paint();
    private float fingerX;
    private float fingerY;
    private float cx;
    private float cy;
    private float x;
    private float y;
    private boolean isMove = false;
    private float fingerCircleRadius;
    protected float scopeCircleRadius;
    private ValueAnimator moveTimer;

    public ControlView(Context context) {
        this(context, null);
    }

    public ControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scopePaint.setStyle(Paint.Style.STROKE);
        scopePaint.setColor(ContextCompat.getColor(context, android.R.color.darker_gray));
        scopePaint.setStrokeWidth(10);

        fingerPaint.setStyle(Paint.Style.FILL);
        fingerPaint.setColor(ContextCompat.getColor(context, android.R.color.white));
    }

    public ControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private float getScopeRadius() {
        return Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2f - scopePaint.getStrokeWidth() / 2f;
    }

    private float getFingerCircleRadius() {
        return Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2f / 3f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        scopeCircleRadius = getScopeRadius();
        fingerCircleRadius = getFingerCircleRadius();
        cx = getMeasuredWidth() / 2f;
        cy = getMeasuredHeight() / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(cx, cy, scopeCircleRadius, scopePaint);
        if (!isMove) return;
        float x = fingerX;
        float y = fingerY;

        double distCenter = Math.sqrt(Math.pow(x - cx, 2) + Math.pow(y - cy, 2));
        //FingerCircle部分逸出到了ScopeCircle外
        if (distCenter + fingerCircleRadius > scopeCircleRadius) {
            //FingerCircle有部分在ScopeCircle外，那就获取线段AB(起点(cx,cy),终点(x,y))与ScopeCircle
            //相交的点(scopeX,scopeY)
            float cos = (float) ((x - cx) / distCenter);
            float sin = (float) ((y - cy) / distCenter);
            float scopeX = cx + scopeCircleRadius * cos;
            float scopeY = cy + scopeCircleRadius * sin;
            //调整FingerCircle落点位置，让FingerCircle刚好与ScopeCircle内切
            x = scopeX + fingerCircleRadius * (-cos);
            y = scopeY + fingerCircleRadius * (-sin);
        }
        canvas.drawCircle(x, y, fingerCircleRadius, fingerPaint);
        this.x = x;
        this.y = y;
    }

    public abstract void onMove(float cx, float cy, float x, float y);


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent: " + event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isMove = true;
                //定时器。每16.6ms获取一次手指落点，用于移动摄像头。
                //这里没有采用Timer，主要是在一些低端设备，尤其是机顶盒上，
                //发现Timer存在不准时的情况(并不是因为Android的Doze模式的影响)。
                moveTimer = ValueAnimator.ofFloat(0, 1);
                moveTimer.addUpdateListener(animation -> {
                    onMove(cx, cy, x, y);
                });
                moveTimer.setDuration(1000);
                moveTimer.setRepeatCount(ValueAnimator.INFINITE);
                moveTimer.setInterpolator(new LinearInterpolator());
                moveTimer.start();
                break;
            case MotionEvent.ACTION_MOVE:
                fingerX = event.getX();
                fingerY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isMove = false;
                invalidate();
                moveTimer.cancel();
                break;
            default:
                break;
        }
        return true;
    }


}
