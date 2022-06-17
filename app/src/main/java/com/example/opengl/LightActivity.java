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
            "基础光照",
    };

    private Class[] activities = {
            LightSampleActivity.class,
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
