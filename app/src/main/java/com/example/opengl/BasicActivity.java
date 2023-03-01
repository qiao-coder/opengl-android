package com.example.opengl;

import android.os.Bundle;
import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.opengl.base.SampleAdapter;
import com.example.opengl.databinding.ActBasicBinding;

/**
 * @author Q
 * @date 2023/2/28.
 */
public class BasicActivity extends AppCompatActivity {
    private Pair<String, Class>[] data = new Pair[]{
            new Pair<>("创建窗口", WindowActivity.class),
            new Pair<>("三角形", TriangleActivity.class),
            new Pair<>("三角形（矩形）", RectangleActivity.class),
            new Pair<>("着色器（红色三角形）", RedTriangleActivity.class),
            new Pair<>("着色器（变色三角形）", DynamicColorTriangleActivity.class),
            new Pair<>("着色器（三色三角形）", RGBTriangleActivity.class),
            new Pair<>("纹理（木箱）", WoodenBoxActivity.class),
            new Pair<>("纹理（彩色木箱）", ColouredWoodenBoxActivity.class),
            new Pair<>("纹理（会笑的木箱）", LaughingWoodenBoxActivity.class),
            new Pair<>("变换（旋转的木箱）", RotatingWoodenBoxActivity.class),
            new Pair<>("坐标系统（透视）", CoordinateSystemsActivity.class),
            new Pair<>("坐标系统（3D木箱）", CoordinateSystemsDepthActivity.class),
            new Pair<>("坐标系统（多个3D木箱）", CoordinateSystemsMultipleActivity.class),
    };

    private ActBasicBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActBasicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SampleAdapter sampleAdapter = new SampleAdapter(data);
        binding.rvContent.setAdapter(sampleAdapter);
    }
}
