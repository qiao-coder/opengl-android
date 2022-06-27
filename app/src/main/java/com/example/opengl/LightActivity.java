package com.example.opengl;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.opengl.base.SampleAdapter;
import com.example.opengl.databinding.ActCameraBinding;

/**
 * @author wuzhanqiao
 * @date 2022/6/17.
 */
public class LightActivity extends AppCompatActivity {
    private String[] data = {
            "颜色",
            "基本光照(环境光照、漫反射光照)",
            "基本光照(镜面光照)",
            "材质"
    };

    private Class[] activities = {
            LightSampleActivity.class,
            BasicLightingDiffuseActivity.class,
            BasicLightingSpecularActivity.class,
            MaterialsActivity.class,
    };

    private ActCameraBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SampleAdapter sampleAdapter = new SampleAdapter(data, activities);
        binding.rvContent.setAdapter(sampleAdapter);
    }
}
