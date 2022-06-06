package com.example.opengl;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.opengl.databinding.ActCameraKeyboardBinding;

/**
 * @author wuzhanqiao
 * @date 2022/5/23.
 */
public class CameraKeyboardActivity extends AppCompatActivity {
    private ActCameraKeyboardBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActCameraKeyboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        switch (event.getKeyCode()) {
//            case KeyEvent.KEYCODE_DPAD_UP:
//                binding.cameraKeyboard.move(UP);
//                return true;
//            case KeyEvent.KEYCODE_DPAD_DOWN:
//                binding.cameraKeyboard.move(DOWN);
//                return true;
//            case KeyEvent.KEYCODE_DPAD_LEFT:
//                binding.cameraKeyboard.move(LEFT);
//                return true;
//            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                binding.cameraKeyboard.move(RIGHT);
//                return true;
//        }
//        return super.dispatchKeyEvent(event);
//    }
}
