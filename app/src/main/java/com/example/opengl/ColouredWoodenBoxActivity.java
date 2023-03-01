package com.example.opengl;

import static com.example.opengl.util.SampleUIUtil.setupView;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.opengl.view.ColouredWoodenBox;

/**
 * @author Q
 * @date 2023/2/28.
 */
public class ColouredWoodenBoxActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView(this, new ColouredWoodenBox(this));
    }
}
