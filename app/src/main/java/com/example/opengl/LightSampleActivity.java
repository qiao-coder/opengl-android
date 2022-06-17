package com.example.opengl;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.opengl.base.BaseActivity;
import com.example.opengl.data.AngleType;
import com.example.opengl.databinding.ActLightSampleBinding;

/**
 * @author wuzhanqiao
 * @date 2022/6/16.
 */
public class LightSampleActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActLightSampleBinding binding = ActLightSampleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.moveView.setOnMoveListener((d, speed) -> {
            binding.lightSample.move(d, speed);
        });
        binding.angleView.setOnAdjustListener((type, angle) -> {
            if (type == AngleType.YAW) {
                binding.tvYawValue.setText(String.format("%s", angle));
                binding.lightSample.setYaw(angle);
            } else {
                binding.tvPitchValue.setText(String.format("%s", angle));
                binding.lightSample.setPitch(angle);
            }
        });
    }
}
