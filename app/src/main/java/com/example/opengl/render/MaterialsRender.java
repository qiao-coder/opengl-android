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
import com.example.opengl.base.Camera;
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
public class MaterialsRender extends BaseRender {
    private final float vertices[] = {
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
            0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
            0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
            0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,

            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
            0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
            -0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,

            -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
            -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
            -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, -1.0f, 0.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f,

            0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
            0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
            0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,

            -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,
            0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,

            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
            0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f
    };
    private int[] lightCubeVao;
    private int[] cubeVao;
    private Shader lightingShader;
    private Shader lightCubeShader;
    private int width;
    private int height;
    private final Vec3 lightPos = new Vec3(1.2f, 1.0f, 2.0f);

    public MaterialsRender(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        lightingShader = new Shader.Builder(context)
                .setVertexShader(R.raw.vertex_basic_lighting_specular)
                .setFragShader(R.raw.frag_materials)
                .build();

        lightCubeShader = new Shader.Builder(context)
                .setVertexShader(R.raw.vertex_light_cube)
                .setFragShader(R.raw.frag_light_cube)
                .build();

        //???????????????????????????VAO???VBO
        cubeVao = bindSingleVAO();
        int[] vbo = bindSingleVBO();

        FloatBuffer verticesBuffer = ByteBuffer
                .allocateDirect(vertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertices);
        verticesBuffer.position(0);
        GLES20.glBufferData(GL_ARRAY_BUFFER, vertices.length * BYTES_PER_FLOAT, verticesBuffer, GL_STATIC_DRAW);

        //????????????
        GLES20.glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * BYTES_PER_FLOAT, 0);
        GLES20.glEnableVertexAttribArray(0);
        //???????????????
        GLES20.glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * BYTES_PER_FLOAT, 3 * BYTES_PER_FLOAT);
        GLES20.glEnableVertexAttribArray(1);


        //????????????????????????VAO(VBO??????????????????????????????????????????????????????????????????3D?????????)
        lightCubeVao = bindSingleVAO();

        //????????????????????????VBO(???glVertexAttribPointer?????????)?????????????????????;
        //VBO???????????????????????????????????????????????????(???????????????????????????????????????????????????????????????)
        GLES20.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);

        GLES20.glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * BYTES_PER_FLOAT, 0);
        GLES20.glEnableVertexAttribArray(0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        GLES20.glViewport(0, 0, width, height);
    }

    private long startTime = -1;

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glEnable(GL_DEPTH_TEST);
        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        GLES20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        //????????????????????????
        lightingShader.use();
//        lightingShader.setVec3("objectColor", 1.0f, 0.5f, 0.31f);
        //?????????
//        lightingShader.setVec3("lightColor", 1.0f, 1.0f, 1.0f);
//        lightingShader.setVec3("lightPos", lightPos);
        lightingShader.setVec3("light.position", lightPos);
        lightingShader.setVec3("viewPos", camera.getPosition());
//        lightingShader.setVec3("light.ambient", 0.2f, 0.2f, 0.2f);
//        //???????????????????????????????????????
//        lightingShader.setVec3("light.diffuse", 0.5f, 0.5f, 0.5f);
        //????????????
        float time = 0;
        if (startTime == -1) {
            startTime = System.currentTimeMillis();
        } else {
            time = (System.currentTimeMillis() - startTime) / 1000f;
        }
        Vec3 lightColor = new Vec3();
        lightColor.x = (float) Math.sin(time * 2.0f);
        lightColor.y = (float) Math.sin(time * 0.7f);
        lightColor.z = (float) Math.sin(time * 1.3f);
        Vec3 diffuseColor = lightColor.times(new Vec3(0.5f));
        Vec3 ambientColor = diffuseColor.times(new Vec3(0.2f));
        lightingShader.setVec3("light.ambient", ambientColor);
        lightingShader.setVec3("light.diffuse", diffuseColor);
        lightingShader.setVec3("light.specular", 1.0f, 1.0f, 1.0f);

        //????????????
        lightingShader.setVec3("material.ambient", 1.0f, 0.5f, 0.31f);
        lightingShader.setVec3("material.diffuse", 1.0f, 0.5f, 0.31f);
        //specular lighting doesn't have full effect on this object's material
        lightingShader.setVec3("material.specular", 0.5f, 0.5f, 0.5f);
        lightingShader.setFloat("material.shininess", 32.0f);


        //view/projection transformations
        Mat4 projection = glm.perspective(glm.radians(camera.getFov()), (float) width / (float) height, 0.1f, 100.0f);
        lightingShader.setMatrix("projection", projection);
        lightingShader.setMatrix("view", camera.getViewMatrix());

        // world transformation
        Mat4 model = new Mat4(1.0f);
        lightingShader.setMatrix("model", model);

        //???????????????
        GLES30.glBindVertexArray(cubeVao[0]);
        GLES20.glDrawArrays(GL_TRIANGLES, 0, 36);

        lightCubeShader.use();
        lightCubeShader.setMatrix("projection", projection);
        lightCubeShader.setMatrix("view", camera.getViewMatrix());
        model = new Mat4(1.0f);
        model = glm.translate(model, lightPos);
        //????????????????????????
        model = glm.scale(model, new Vec3(0.2f));
        lightCubeShader.setMatrix("model", model);

        GLES30.glBindVertexArray(lightCubeVao[0]);
        GLES20.glDrawArrays(GL_TRIANGLES, 0, 36);
    }
}
