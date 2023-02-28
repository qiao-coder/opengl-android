package com.example.opengl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opengl.base.SampleAdapter;
import com.example.opengl.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    private Pair<String, Class>[] data = new Pair[]{
            new Pair<>("入门篇", BasicActivity.class),
            new Pair<>("光照篇", LightingActivity.class),
    };

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

        SampleAdapter sampleAdapter = new SampleAdapter(data);
        binding.rvContent.setAdapter(sampleAdapter);
    }
}