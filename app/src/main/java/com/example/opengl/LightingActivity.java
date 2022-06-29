package com.example.opengl;

import android.os.Bundle;
import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.opengl.base.SampleAdapter;
import com.example.opengl.databinding.ActCameraBinding;

/**
 * @author wuzhanqiao
 * @date 2022/6/17.
 */
public class LightingActivity extends AppCompatActivity {
    private Pair<String, Class>[] data = new Pair[]{
            new Pair<>("颜色", LightSampleActivity.class),
            new Pair<>("基本光照(环境光照、漫反射光照)", BasicLightingDiffuseActivity.class),
            new Pair<>("基本光照(镜面光照)", BasicLightingSpecularActivity.class),
            new Pair<>("材质", MaterialsActivity.class),
            new Pair<>("光照贴图(漫反射贴图)", LightingMapsDiffuseActivity.class),
            new Pair<>("光照贴图(镜面光贴图)", LightingMapsSpecularActivity.class),
            new Pair<>("投光物(定向光)", LightCastersDirectionalActivity.class),
    };

    private ActCameraBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SampleAdapter sampleAdapter = new SampleAdapter(data);
        binding.rvContent.setAdapter(sampleAdapter);
    }
}
