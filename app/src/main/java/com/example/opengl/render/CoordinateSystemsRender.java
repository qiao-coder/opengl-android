package com.example.opengl.render;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE1;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static glm_.Java.glm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;

import com.example.opengl.R;
import com.example.opengl.base.BaseRender;
import com.example.opengl.base.Shader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import glm_.mat4x4.Mat4;
import glm_.vec3.Vec3;

/**
 * @author wuzhanqiao
 * @date 2022/5/9.
 */
public class CoordinateSystemsRender extends BaseRender {
    float[] vertices = {
//            ---- 位置 ----       - 纹理坐标 -
            0.5f, 0.5f, 0.0f, 1.0f, 1.0f,   // 右上
            0.5f, -0.5f, 0.0f, 1.0f, 0.0f,   // 右下
            -0.5f, -0.5f, 0.0f, 0.0f, 0.0f,   // 左下
            -0.5f, 0.5f, 0.0f, 0.0f, 1.0f    // 左上
    };

    private int[] indices = { // 注意索引从0开始!
            0, 1, 3, // 第一个三角形
            1, 2, 3  // 第二个三角形
    };

    private int[] vao;
    private Shader shader;
    private int[] texture1;
    private int[] texture2;
    private int width;
    private int height;

    public CoordinateSystemsRender(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        vao = bindSingleVAO();
        bindSingleEBO();
        bindSingleVBO();

        shader = new Shader.Builder(context)
                .setVertexShader(R.raw.vertex_coordinate_systems)
                .setFragShader(R.raw.frag_coordinate_systems)
                .build();

        //创建、绑定、填充第一个纹理
        texture1 = bindSingleTexture2D();
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.wooden_container);
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        GLES20.glGenerateMipmap(GL_TEXTURE_2D);

        //创建、绑定、填充第二个纹理
        texture2 = bindSingleTexture2D();
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.awesomeface);
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap2, 0);
        GLES20.glGenerateMipmap(GL_TEXTURE_2D);

        //使用glUniform1i告诉OpenGL每个着色器采样器属于哪个纹理单元
        shader.use();
        shader.setInt("texture1", 0);
        shader.setInt("texture2", 1);

        IntBuffer indicesBuffer = ByteBuffer
                .allocateDirect(indices.length * BYTES_PER_INT)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer()
                .put(indices);
        indicesBuffer.position(0);
        GLES20.glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices.length * 4, indicesBuffer,
                GL_STATIC_DRAW);

        FloatBuffer verticesBuffer = ByteBuffer
                .allocateDirect(vertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertices);
        verticesBuffer.position(0);
        GLES20.glBufferData(GL_ARRAY_BUFFER, vertices.length * 4, verticesBuffer, GL_STATIC_DRAW);


        GLES20.glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * BYTES_PER_FLOAT, 0);
        GLES20.glEnableVertexAttribArray(0);

        GLES20.glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * BYTES_PER_FLOAT, 3 * BYTES_PER_FLOAT);
        GLES20.glEnableVertexAttribArray(1);
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.d(TAG, "onDrawFrame");
        GLES20.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        GLES20.glClear(GL_COLOR_BUFFER_BIT);

        GLES20.glActiveTexture(GL_TEXTURE0);
        GLES20.glBindTexture(GL_TEXTURE_2D, texture1[0]);
        GLES20.glActiveTexture(GL_TEXTURE1);
        GLES20.glBindTexture(GL_TEXTURE_2D, texture2[0]);

        Mat4 model = new Mat4();
        model = glm.rotate(model, glm.radians(-55.0f), new Vec3(1.0f, 0.0f, 0.0f));
        shader.setMatrix("model", model);

        Mat4 view = new Mat4();
        //注意，我们将矩阵向我们要进行移动场景的反方向移动。
        view = glm.translate(view, new Vec3(0.0f, 0.0f, -3.0f));
        shader.setMatrix("view", view);

        Mat4 projection = glm.perspective(glm.radians(45.0f), (float) width / (float) height, 0.1f, 100.0f);
        shader.setMatrix("projection", projection);

        shader.use();
        GLES30.glBindVertexArray(vao[0]);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0);
    }
}
