package com.example.opengl;

import static com.example.opengl.util.SampleUIUtil.setupView;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.opengl.base.BaseActivity;
import com.example.opengl.render.BasicLightingAmbientRender;
import com.example.opengl.view.BasicLightingAmbient;

/**
 * @author wuzhanqiao
 * @date 2022/6/17.
 */
public class BasicLightingAmbientActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView(this,
                new BasicLightingAmbient(this),
                new BasicLightingAmbientRender(this));
    }
}
