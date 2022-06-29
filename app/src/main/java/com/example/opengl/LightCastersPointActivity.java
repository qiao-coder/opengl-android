package com.example.opengl;

import static com.example.opengl.util.SampleUIUtil.setupView;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.opengl.base.BaseActivity;
import com.example.opengl.render.LightCastersPointRender;
import com.example.opengl.view.LightCastersPoint;

/**
 * @author wuzhanqiao
 * @date 2022/6/29.
 */
public class LightCastersPointActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView(this,
                new LightCastersPoint(this),
                new LightCastersPointRender(this));
    }
}
