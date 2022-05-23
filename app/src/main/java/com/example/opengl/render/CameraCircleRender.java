package com.example.opengl.render;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE1;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.GL_TRIANGLES;
import static glm_.Java.glm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;

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
 * @date 2022/5/11.
 */
public class CameraCircleRender extends BaseRender {
    private float vertices[] = {
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
            0.5f, -0.5f, -0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,

            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 1.0f,
            -0.5f, 0.5f, 0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,

            -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, 1.0f, 0.0f,

            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,

            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,

            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, 0.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f
    };

    private Vec3[] cubePositions = {
            new Vec3(0.0f, 0.0f, 0.0f),
            new Vec3(2.0f, 5.0f, -15.0f),
            new Vec3(-1.5f, -2.2f, -2.5f),
            new Vec3(-3.8f, -2.0f, -12.3f),
            new Vec3(2.4f, -0.4f, -3.5f),
            new Vec3(-1.7f, 3.0f, -7.5f),
            new Vec3(1.3f, -2.0f, -2.5f),
            new Vec3(1.5f, 2.0f, -2.5f),
            new Vec3(1.5f, 0.2f, -1.5f),
            new Vec3(-1.3f, 1.0f, -1.5f)
    };

    private int[] vao;
    private Shader shader;
    private int[] texture1;
    private int[] texture2;
    private int width;
    private int height;

    public CameraCircleRender(Context context) {
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

    private final long duration = 5000;
    private float camAngle = 0.0f;
    private long lastTime = -1L;
    private final double base = 2 * Math.PI / duration;
    private float radius = 10.0f;

    private float cameraY = 0.0f;
    private float centerX = 0.0f;
    private float centerY = 0.0f;
    private float centerZ = 0.0f;
    private float upX = 0.0f;
    private float upY = 1.0f;
    private float upZ = 0.0f;

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glEnable(GL_DEPTH_TEST);
        GLES20.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        GLES20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        GLES20.glActiveTexture(GL_TEXTURE0);
        GLES20.glBindTexture(GL_TEXTURE_2D, texture1[0]);
        GLES20.glActiveTexture(GL_TEXTURE1);
        GLES20.glBindTexture(GL_TEXTURE_2D, texture2[0]);

        shader.use();

        Mat4 projection = glm.perspective(glm.radians(45.0f), (float) width / (float) height, 0.1f, 100.0f);
        shader.setMatrix("projection", projection);


        if (lastTime == -1L) {
            lastTime = System.currentTimeMillis();
        } else {
            long interval = System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
            camAngle += interval * base;
        }
        float camX = glm.sin(camAngle) * radius;
        float camZ = glm.cos(camAngle) * radius;
        Mat4 view = glm.lookAt(new Vec3(camX, cameraY, camZ),
                new Vec3(centerX, centerY, centerZ),
                new Vec3(upX, upY, upZ));
        shader.setMatrix("view", view);

        GLES30.glBindVertexArray(vao[0]);
        for (int i = 0; i < 10; i++) {
            Mat4 model = new Mat4();
            model = glm.translate(model, cubePositions[i]);
            float angle = 20.0f * i;
            model = glm.rotate(model, glm.radians(angle), new Vec3(1.0f, 0.3f, 0.5f));
            shader.setMatrix("model", model);
            GLES20.glDrawArrays(GL_TRIANGLES, 0, 36);
        }

//        Vec3 cameraPos = new Vec3(0.0f, 0.0f, 3.0f);
//        Vec3 cameraTarget = new Vec3(0.0f, 0.0f, 0.0f);
//        cameraTarget = cameraPos.minus(cameraTarget);
//        //第二个参数其实就是用来接收返回结果的，等于cameraDirection
//        Vec3 cameraDirection = glm.normalize(cameraTarget, new Vec3());
//
//        Vec3 up = new Vec3(0.0f, 1.0f, 0.0f);
//        Vec3 cameraRight = glm.normalize(glm.cross(up, cameraDirection, new Vec3()), new Vec3());
//
//        Vec3 cameraUp = glm.cross(cameraDirection, cameraRight,new Vec3());
//
//        Mat4 view = glm.lookAt(new Vec3(0.0f, 0.0f, 3.0f),
//                new Vec3(0.0f, 0.0f, 0.0f),
//                new Vec3(0.0f, 1.0f, 0.0f));
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setCameraY(float y) {
        cameraY = y;
    }

    public void setCenterX(float x) {
        centerX = x;
    }

    public void setCenterY(float y) {
        centerY = y;
    }

    public void setCenterZ(float z) {
        centerZ = z;
    }

    public void setUpX(float x) {
        upX = x;
    }

    public void setUpY(float y) {
        upY = y;
    }

    public void setUpZ(float z) {
        upZ = z;
    }
}
