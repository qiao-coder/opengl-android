package com.example.opengl;

import static android.opengl.GLSurfaceView.RENDERMODE_CONTINUOUSLY;
import static com.example.opengl.util.SampleUIUtil.setupView;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.opengl.base.BaseActivity;
import com.example.opengl.render.MaterialRender;
import com.example.opengl.view.Material;

/**
 * @author wuzhanqiao
 * @date 2022/6/23.
 */
public class MaterialActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView(this,
                new Material(this),
                new MaterialRender(this),
                RENDERMODE_CONTINUOUSLY);
    }
}
