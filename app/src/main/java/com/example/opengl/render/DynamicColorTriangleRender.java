package com.example.opengl.render;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;

import com.example.opengl.R;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author wuzhanqiao
 * @date 2022/4/29.
 */
public class DynamicColorTriangleRender extends TriangleRender {
    public DynamicColorTriangleRender(Context context) {
        super(context);
    }

    @Override
    protected int getFragShaderCode() {
        return R.raw.frag_dynamic_color_triangle;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //覆盖父类的绘制实现
//        super.onDrawFrame(gl);

        // 渲染
        // 清除颜色缓冲
        GLES20.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        GLES20.glClear(GL_COLOR_BUFFER_BIT);

        // 激活着色器
        GLES20.glUseProgram(shaderProgram);

        // 更新uniform颜色
        long timeValue = System.currentTimeMillis() / 1000;
        float greenValue = (float) (Math.sin(timeValue) / 2.0f + 0.5f);
        int vertexColorLocation = GLES20.glGetUniformLocation(shaderProgram, "ourColor");
        GLES20.glUniform4f(vertexColorLocation, 0.0f, greenValue, 0.0f, 1.0f);

        // 绘制三角形
        GLES30.glBindVertexArray(vao[0]);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }
}
