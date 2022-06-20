package com.example.opengl;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.opengl.base.BaseActivity;
import com.example.opengl.data.AngleType;
import com.example.opengl.databinding.ActBasicLightingSpecularBinding;

/**
 * @author wuzhanqiao
 * @date 2022/6/17.
 */
public class BasicLightingSpecularActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActBasicLightingSpecularBinding binding = ActBasicLightingSpecularBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.moveView.setOnMoveListener((d, speed) -> {
            binding.basicLightingSpecular.move(d, speed);
        });
        binding.angleView.setOnAdjustListener((type, angle) -> {
            if (type == AngleType.YAW) {
                binding.tvYawValue.setText(String.format("%s", angle));
                binding.basicLightingSpecular.setYaw(angle);
            } else {
                binding.tvPitchValue.setText(String.format("%s", angle));
                binding.basicLightingSpecular.setPitch(angle);
            }
        });
    }
}
