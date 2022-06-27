package com.example.opengl.render;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.GL_TRIANGLES;
import static glm_.Java.glm;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;

import com.example.opengl.R;
import com.example.opengl.base.BaseRender;
import com.example.opengl.base.Shader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import glm_.mat4x4.Mat4;
import glm_.vec3.Vec3;

/**
 * @author wuzhanqiao
 * @date 2022/6/16.
 */
public class LightSampleRender extends BaseRender {
    private final float[] vertices = {
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,

            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,

            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,

            0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,

            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
    };
    private int[] lightCubeVao;
    private int[] cubeVao;
    private Shader lightingShader;
    private Shader lightCubeShader;
    private int width;
    private int height;
    private final Vec3 lightPos = new Vec3(1.2f, 1.0f, 2.0f);

    public LightSampleRender(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        lightingShader = new Shader.Builder(context)
                .setVertexShader(R.raw.vertex_light_sample)
                .setFragShader(R.raw.frag_light_sample)
                .build();

        lightCubeShader = new Shader.Builder(context)
                .setVertexShader(R.raw.vertex_light_cube)
                .setFragShader(R.raw.frag_light_cube)
                .build();

        //首先，配置立方体的VAO和VBO
        cubeVao = bindSingleVAO();
        int[] vbo = bindSingleVBO();

        FloatBuffer verticesBuffer = ByteBuffer
                .allocateDirect(vertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertices);
        verticesBuffer.position(0);
        GLES20.glBufferData(GL_ARRAY_BUFFER, vertices.length * BYTES_PER_FLOAT, verticesBuffer, GL_STATIC_DRAW);

        GLES20.glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * BYTES_PER_FLOAT, 0);
        GLES20.glEnableVertexAttribArray(0);

        //然后，配置光照的VAO(VBO保持不变，光照物体的顶点是相同的，它也是一个3D立方体)
        lightCubeVao = bindSingleVAO();

        //我们只需要绑定到VBO(用glVertexAttribPointer链接它)，不需要填充它;
        //VBO的数据已经包含了我们需要的所有内容(它已经绑定了，但我们出于教育目的再次这样做)
        GLES20.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);

        GLES20.glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * BYTES_PER_FLOAT, 0);
        GLES20.glEnableVertexAttribArray(0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glEnable(GL_DEPTH_TEST);
        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        GLES20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        //记得先激活着色器
        lightingShader.use();
        lightingShader.setVec3("objectColor", 1.0f, 0.5f, 0.31f);
        lightingShader.setVec3("lightColor", 1.0f, 1.0f, 1.0f);

        //view/projection transformations
        Mat4 projection = glm.perspective(glm.radians(camera.getFov()), (float) width / (float) height, 0.1f, 100.0f);
        lightingShader.setMatrix("projection", projection);
        lightingShader.setMatrix("view", camera.getViewMatrix());

        // world transformation
        Mat4 model = new Mat4(1.0f);
        lightingShader.setMatrix("model", model);

        //渲染立方体
        GLES30.glBindVertexArray(cubeVao[0]);
        GLES20.glDrawArrays(GL_TRIANGLES, 0, 36);

        lightCubeShader.use();
        lightCubeShader.setMatrix("projection", projection);
        lightCubeShader.setMatrix("view", camera.getViewMatrix());
        model = new Mat4(1.0f);
        model = glm.translate(model, lightPos);
        //一个更小的立方体
        model = glm.scale(model, new Vec3(0.2f));
        lightCubeShader.setMatrix("model", model);

        GLES30.glBindVertexArray(lightCubeVao[0]);
        GLES20.glDrawArrays(GL_TRIANGLES, 0, 36);
    }
}
