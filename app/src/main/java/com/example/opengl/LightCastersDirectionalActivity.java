package com.example.opengl;

import static com.example.opengl.util.SampleUIUtil.setupView;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.opengl.base.BaseActivity;
import com.example.opengl.render.LightCastersDirectionalRender;
import com.example.opengl.view.LightCastersDirectional;

/**
 * @author wuzhanqiao
 * @date 2022/6/28.
 */
public class LightCastersDirectionalActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView(this,
                new LightCastersDirectional(this),
                new LightCastersDirectionalRender(this));
    }
}
