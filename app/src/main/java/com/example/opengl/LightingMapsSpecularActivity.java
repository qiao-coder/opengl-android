package com.example.opengl;

import static com.example.opengl.util.SampleUIUtil.setupView;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.opengl.base.BaseActivity;
import com.example.opengl.render.LightingMapsSpecularRender;
import com.example.opengl.view.LightingMapsSpecular;

/**
 * @author wuzhanqiao
 * @date 2022/6/28.
 */
public class LightingMapsSpecularActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView(this,
                new LightingMapsSpecular(this),
                new LightingMapsSpecularRender(this));
    }
}
