package com.example.opengl.base;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opengl.databinding.ItemCameraBinding;

/**
 * @author wuzhanqiao
 * @date 2022/6/17.
 */
public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {

    private final String[] data;
    private final Class[] activities;
    private ItemCameraBinding binding;

    public SampleAdapter(String[] data, Class[] activities) {
        this.data = data;
        this.activities = activities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemCameraBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        binding.tvTitle.setText(data[position]);
        binding.tvTitle.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            context.startActivity(new Intent(context, activities[position]));
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
