package com.example.opengl;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.opengl.databinding.ActCameraCircleBinding;

/**
 * @author wuzhanqiao
 * @date 2022/5/16.
 */
public class CameraCircleActivity extends AppCompatActivity {

    private ActCameraCircleBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActCameraCircleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSeekBar(binding.sbRadius, binding.tvRadiusValue, binding.cameraCircle::setRadius);
        setSeekBar(binding.sbCameraY, binding.tvCameraYValue, binding.cameraCircle::setCameraY);
        setSeekBar(binding.sbCenterX, binding.tvCenterXValue, binding.cameraCircle::setCenterX);
        setSeekBar(binding.sbCenterY, binding.tvCenterYValue, binding.cameraCircle::setCenterY);
        setSeekBar(binding.sbCenterZ, binding.tvCenterZValue, binding.cameraCircle::setCenterZ);
        setSeekBar(binding.sbUpX, binding.tvUpXValue, binding.cameraCircle::setUpX);
        setSeekBar(binding.sbUpY, binding.tvUpYValue, binding.cameraCircle::setUpY);
        setSeekBar(binding.sbUpZ, binding.tvUpZValue, binding.cameraCircle::setUpZ);

        binding.btnReset.setOnClickListener(v -> {
            resetSeekBar(binding.sbRadius, 50);
            resetSeekBar(binding.sbCameraY, 50);
            resetSeekBar(binding.sbCenterX, 50);
            resetSeekBar(binding.sbCenterY, 50);
            resetSeekBar(binding.sbCenterZ, 50);
            resetSeekBar(binding.sbUpX, 50);
            resetSeekBar(binding.sbUpY, 55);
            resetSeekBar(binding.sbUpZ, 50);
        });
    }

    private void resetSeekBar(SeekBar sb, int progress) {
        sb.setProgress(progress);
    }

    interface Callback {
        void onAdjust(float v);
    }

    private void setSeekBar(SeekBar sb, TextView tvValue, Callback callback) {
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float value;
                if (seekBar == binding.sbRadius) {
                    value = getValue(0, 20, progress);
                } else {
                    value = getValue(-10, 10, progress);
                }
                tvValue.setText(String.valueOf(value));
                callback.onAdjust(value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private float getValue(float min, float max, int progress) {
        float v = min + (max - min) * progress / 100;
        return (float) Math.round(v * 100) / 100;
    }
}
