package com.example.opengl;

import static com.example.opengl.util.SampleUIUtil.setupView;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.opengl.base.BaseActivity;
import com.example.opengl.render.LightingMapsDiffuseRender;
import com.example.opengl.view.LightingMapsDiffuse;

/**
 * @author wuzhanqiao
 * @date 2022/6/27.
 */
public class LightingMapsDiffuseActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView(this,
                new LightingMapsDiffuse(this),
                new LightingMapsDiffuseRender(this));
    }
}
