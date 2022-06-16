package com.example.opengl;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.opengl.base.BaseActivity;
import com.example.opengl.data.AngleType;
import com.example.opengl.databinding.ActCameraFovBinding;

/**
 * @author wuzhanqiao
 * @date 2022/6/16.
 */
public class CameraFovActivity extends BaseActivity {
    private ActCameraFovBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActCameraFovBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.moveView.setOnMoveListener((d, speed) -> {
            binding.cameraFov.move(d, speed);
        });
        binding.angleView.setOnAdjustListener((type, angle) -> {
            if (type == AngleType.YAW) {
                binding.tvYawValue.setText(String.format("%s", angle));
                binding.cameraFov.setYaw(angle);
            } else {
                binding.tvPitchValue.setText(String.format("%s", angle));
                binding.cameraFov.setPitch(angle);
            }
        });
    }
}
