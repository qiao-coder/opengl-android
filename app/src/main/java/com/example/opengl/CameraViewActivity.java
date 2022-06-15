package com.example.opengl;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.opengl.base.BaseActivity;
import com.example.opengl.data.AngleType;
import com.example.opengl.databinding.ActCameraViewBinding;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author wuzhanqiao
 * @date 2022/6/8.
 */
public class CameraViewActivity extends BaseActivity {
    private ActCameraViewBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActCameraViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.moveView.setOnMoveListener((d, speed) -> {
            binding.cameraView.move(d, speed);
        });
        binding.angleView.setOnAdjustListener((type,angle)->{
            if(type == AngleType.YAW){
                binding.tvYawValue.setText(String.format("%s", angle));
                binding.cameraView.setYaw(angle);
            }else{
                binding.tvPitchValue.setText(String.format("%s", angle));
                binding.cameraView.setPitch(angle);
            }
        });
    }
}
