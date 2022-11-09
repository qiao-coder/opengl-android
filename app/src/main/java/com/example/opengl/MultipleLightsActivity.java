package com.example.opengl;

import static com.example.opengl.util.SampleUIUtil.setupView;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.opengl.base.BaseActivity;
import com.example.opengl.render.MultipleLightsRender;
import com.example.opengl.view.MultipleLights;

/**
 * @author wuzhanqiao
 * @date 2022/11/8.
 */
public class MultipleLightsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView(this,
                new MultipleLights(this),
                new MultipleLightsRender(this));
    }
}
