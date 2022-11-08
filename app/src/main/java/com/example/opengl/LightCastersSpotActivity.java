package com.example.opengl;

import static com.example.opengl.util.SampleUIUtil.setupView;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.opengl.base.BaseActivity;
import com.example.opengl.render.LightCastersSpotRender;
import com.example.opengl.view.LightCastersSpot;

/**
 * @author wuzhanqiao
 * @date 2022/6/30.
 */
public class LightCastersSpotActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView(this,
                new LightCastersSpot(this),
                new LightCastersSpotRender(this));
    }
}
