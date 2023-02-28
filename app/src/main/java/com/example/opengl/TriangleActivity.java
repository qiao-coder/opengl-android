package com.example.opengl;

import static com.example.opengl.util.SampleUIUtil.setupView;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.opengl.view.Triangle;

/**
 * @author Q
 * @date 2023/2/28.
 */
public class TriangleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加控件Window到Activity中
        setupView(this, new Triangle(this));
    }
}
