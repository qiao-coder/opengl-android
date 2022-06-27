package com.example.opengl;

import static com.example.opengl.util.SampleUIUtil.setupView;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.opengl.base.BaseActivity;
import com.example.opengl.render.LightSampleRender;
import com.example.opengl.view.LightSample;

/**
 * @author wuzhanqiao
 * @date 2022/6/16.
 */
public class LightSampleActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActLightSampleBinding binding = ActLightSampleBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

//        binding.lightSample.setRender(new LightSampleRender(this));

//        binding.moveView.setOnMoveListener((d, speed) -> {
//            binding.lightSample.move(d, speed);
//        });
//        binding.angleView.setOnAdjustListener((type, angle) -> {
//            if (type == AngleType.YAW) {
//                binding.lightSample.setYaw(angle);
//            } else {
//                binding.lightSample.setPitch(angle);
//            }
//        });

        //效果等同于上面注释的代码。后续示例都采用setupView，动态设置布局。不再一一说明。
        //主要是设置示例代码的页面：renderView + MoveView + AngleView
        //renderView：一个GLSurfaceView，用于OpenGL渲染
        //MoveView：方向盘，用于摄像机移动
        //AngleView：欧拉盘，用于调整摄像机的偏航角、俯仰角
        setupView(this,
                new LightSample(this),
                new LightSampleRender(this));
    }
}
