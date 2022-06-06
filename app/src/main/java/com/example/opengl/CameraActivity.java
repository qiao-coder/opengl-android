package com.example.opengl;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opengl.databinding.ActCameraBinding;
import com.example.opengl.databinding.ItemCameraBinding;

/**
 * @author wuzhanqiao
 * @date 2022/5/16.
 */
public class CameraActivity extends AppCompatActivity {

    private String[] data = {
            "摄像头(绕中心点做圆周运动)",
            "自由移动"
    };

    private Class[] activities = {
            CameraCircleActivity.class,
            CameraKeyboardActivity.class,
    };

    private ActCameraBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CameraAdapter cameraAdapter = new CameraAdapter(data, activities);
        binding.rvContent.setAdapter(cameraAdapter);
    }

    class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.ViewHolder> {

        private final String[] data;
        private final Class[] activities;
        private ItemCameraBinding binding;

        public CameraAdapter(String[] data, Class[] activities) {
            this.data = data;
            this.activities = activities;
        }

        @NonNull
        @Override
        public CameraAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            binding = ItemCameraBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new CameraAdapter.ViewHolder(binding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull CameraAdapter.ViewHolder holder, int position) {
            binding.tvTitle.setText(data[position]);
            binding.tvTitle.setOnClickListener(v -> {
                startActivity(new Intent(CameraActivity.this, activities[position]));
            });
        }

        @Override
        public int getItemCount() {
            return data.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
