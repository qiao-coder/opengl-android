package com.example.opengl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opengl.databinding.ActivityMainBinding;

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

}