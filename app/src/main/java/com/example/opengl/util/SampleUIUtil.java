package com.example.opengl.util;

import static android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY;
import static com.example.opengl.util.ContextUtil.dp2px;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.opengl.base.BaseGLSurfaceView;
import com.example.opengl.base.BaseRender;
import com.example.opengl.data.AngleType;
import com.example.opengl.view.AngleView;
import com.example.opengl.view.MoveView;

/**
 * @author wuzhanqiao
 * @date 2022/6/23.
 */
public class SampleUIUtil {
    public static <T extends BaseRender> void setupView(Activity activity, BaseGLSurfaceView<T> renderView, T render) {
        setupView(activity, renderView, render, RENDERMODE_WHEN_DIRTY);
    }

    /**
     * 设置示例代码的页面：renderView + {@link MoveView} + {@link AngleView}
     * renderView：一个GLSurfaceView，用于OpenGL渲染
     * MoveView：方向盘，用于摄像机移动
     * AngleView：欧拉盘，用于调整摄像机的偏航角、俯仰角
     *
     * @param renderView 继承自BaseGLSurfaceView的View
     * @param render     继承自BaseRender的Render。BaseRender里会默认创建一个{@link com.example.opengl.base.Camera}，
     *                   用于OpenGL的渲染
     */
    public static <T extends BaseRender> void setupView(Activity activity, BaseGLSurfaceView<T> renderView, T render, int renderMode) {
        ConstraintLayout constraintLayout = new ConstraintLayout(activity);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT));


        renderView.setRender(render, renderMode);
        renderView.setId(View.generateViewId());
        renderView.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT));

        MoveView moveView = new MoveView(activity);
        moveView.setId(View.generateViewId());
        moveView.setLayoutParams(new ViewGroup.LayoutParams(
                dp2px(activity, 200),
                dp2px(activity, 200)));

        AngleView angleView = new AngleView(activity);
        angleView.setId(View.generateViewId());
        angleView.setLayoutParams(new ViewGroup.LayoutParams(
                dp2px(activity, 200),
                dp2px(activity, 200)));

        constraintLayout.addView(renderView);
        constraintLayout.addView(moveView);
        constraintLayout.addView(angleView);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        constraintSet.constrainWidth(renderView.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.constrainHeight(renderView.getId(), ConstraintSet.MATCH_CONSTRAINT);
        constraintSet.connect(renderView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
        constraintSet.connect(renderView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
        constraintSet.connect(renderView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
        constraintSet.connect(renderView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);

        constraintSet.connect(moveView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, dp2px(activity, 20));
        constraintSet.connect(moveView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, dp2px(activity, 20));

        constraintSet.connect(angleView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, dp2px(activity, 20));
        constraintSet.connect(angleView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, dp2px(activity, 20));

        constraintLayout.setConstraintSet(constraintSet);


        constraintSet.applyTo(constraintLayout);

        activity.setContentView(constraintLayout);

        moveView.setOnMoveListener(renderView::move);
        angleView.setOnAdjustListener((type, angle) -> {
            if (type == AngleType.YAW) {
                renderView.setYaw(angle);
            } else {
                renderView.setPitch(angle);
            }
        });
    }
}
