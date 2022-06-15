package com.example.opengl;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.opengl.base.BaseActivity;
import com.example.opengl.databinding.ActCameraPositionBinding;

/**
 * @author wuzhanqiao
 * @date 2022/5/23.
 */
public class CameraPositionActivity extends BaseActivity {
    private ActCameraPositionBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActCameraPositionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.moveView.setOnMoveListener((d, speed) -> {
            binding.cameraPosition.move(d, speed);
        });
    }
}
