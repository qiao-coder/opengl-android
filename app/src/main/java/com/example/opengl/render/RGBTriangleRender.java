package com.example.opengl.render;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_STATIC_DRAW;

import android.content.Context;
import android.opengl.GLES20;

import com.example.opengl.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * @author wuzhanqiao
 * @date 2022/4/29.
 */
public class RGBTriangleRender extends TriangleRender {

    protected float[] vertices = {
            // 位置              // 颜色
            0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f,   // 右下
            -0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f,   // 左下
            0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f    // 顶部
    };

    public RGBTriangleRender(Context context) {
        super(context);
    }

    @Override
    protected int getVertexShaderCode() {
        return R.raw.vertex_rgb_triangle;
    }

    @Override
    protected int getFragShaderCode() {
        return R.raw.frag_rgb_triangle;
    }

    @Override
    protected void setVertexData() {
        FloatBuffer verticesBuffer = ByteBuffer
                .allocateDirect(vertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertices);
        verticesBuffer.position(0);
        GLES20.glBufferData(GL_ARRAY_BUFFER, vertices.length * BYTES_PER_FLOAT, verticesBuffer, GL_STATIC_DRAW);

        //设置位置属性
        GLES20.glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * BYTES_PER_FLOAT, 0);
        GLES20.glEnableVertexAttribArray(0);

        //设置颜色属性
        GLES20.glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * BYTES_PER_FLOAT, 3 * BYTES_PER_FLOAT);
        GLES20.glEnableVertexAttribArray(1);
    }

}
