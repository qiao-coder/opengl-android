package com.example.opengl;

import static com.example.opengl.util.SampleUIUtil.setupView;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.opengl.base.BaseActivity;
import com.example.opengl.render.LightCastersSpotRender;
import com.example.opengl.render.LightCastersSpotSoftRender;
import com.example.opengl.view.LightCastersSpot;
import com.example.opengl.view.LightCastersSpotSoft;

/**
 * @author wuzhanqiao
 * @date 2022/11/8.
 */
public class LightCastersSpotSoftActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView(this,
                new LightCastersSpotSoft(this),
                new LightCastersSpotSoftRender(this));
    }
}
