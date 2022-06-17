package com.example.opengl;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.opengl.base.SampleAdapter;
import com.example.opengl.databinding.ActCameraBinding;

/**
 * @author wuzhanqiao
 * @date 2022/5/16.
 */
public class CameraActivity extends AppCompatActivity {

    private String[] data = {
            "摄像头(绕中心点做圆周运动)",
            "自由移动(位置移动)",
            "视角移动(调整偏航角和俯仰角)",
            "视角移动(缩放)",
    };

    private Class[] activities = {
            CameraCircleActivity.class,
            CameraPositionActivity.class,
            CameraViewActivity.class,
            CameraFovActivity.class,
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
