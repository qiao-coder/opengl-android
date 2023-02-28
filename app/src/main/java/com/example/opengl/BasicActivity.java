package com.example.opengl;

import android.os.Bundle;
import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.opengl.base.SampleAdapter;
import com.example.opengl.databinding.ActBasicBinding;
import com.example.opengl.view.Window;

/**
 * @author Q
 * @date 2023/2/28.
 */
public class BasicActivity extends AppCompatActivity {
    private Pair<String, Class>[] data = new Pair[]{
            new Pair<>("创建窗口", WindowActivity.class),
            new Pair<>("三角形", TriangleActivity.class),
            new Pair<>("矩形", RectangleActivity.class),
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
