package com.example.opengl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opengl.databinding.ActivityMainBinding;

import java.util.PriorityQueue;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    private int[][] sizes = new int[][]{
            {500, 500},
            {600, 600},
            {700, 700},
    };
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo =
                activityManager.getDeviceConfigurationInfo();
        String versionText = "Device Supported OpenGL ES Version = " + configurationInfo.getGlEsVersion();
        Toast.makeText(this, versionText, Toast.LENGTH_LONG).show();
        Log.d(TAG, versionText);

        binding.windowView.setOnClickListener(v -> {
            int i = index % sizes.length;
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(sizes[i][0], sizes[i][1]);
            binding.windowView.setLayoutParams(layoutParams);
            index++;
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                Log.d(TAG, "onTouchEvent: ACTION_DOWN : x = " + event.getX() + ", y = " + event.getY());
                break;
            }
            case MotionEvent.ACTION_UP: {
                Log.d(TAG, "onTouchEvent: ACTION_UP : x = " + event.getX() + ", y = " + event.getY());
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                Log.d(TAG, "onTouchEvent: ACTION_POINTER_UP : x = " + event.getX() + ", y = " + event.getY());
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN: {
                Log.d(TAG, "onTouchEvent: ACTION_POINTER_DOWN : x = " + event.getX() + ", y = " + event.getY());
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                for (int i = 0; i < event.getPointerCount(); i++) {
                    int pointerId = event.getPointerId(i);
                    Log.d(TAG, "onTouchEvent: ACTION_MOVE : x = " + event.getX(i) + ", y = " + event.getY(i)
                            + ", actionIndex = " + event.getActionIndex() + ", pointerId = " + pointerId);
                }
                break;
            }
        }
        return super.onTouchEvent(event);
    }
}