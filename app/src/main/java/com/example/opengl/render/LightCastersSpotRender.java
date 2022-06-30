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
 * @date 2022/6/29.
 */
public class LightCastersSpotRender extends BaseRender {

    private final float[] vertices = {
            // positions          // normals           // texture coords
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,
            0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 1.0f, 0.0f,
            0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 1.0f, 1.0f,
            0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 1.0f, 1.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,

            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f,
            -0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,

            -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
            -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
            -0.5f, -0.5f, 0.5f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f,

            0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f,
            0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,

            -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f,
            0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f, 1.0f, 1.0f,
            0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f,
            0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f,
            -0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f,
            -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f,

            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
            0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
            0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f,
            0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f,
            -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f,
            -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f
    };

    //所有箱子的位置
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

    private int[] lightCubeVao;
    private int[] cubeVao;
    private Shader lightingShader;
    private Shader lightCubeShader;
    private int width;
    private int height;
    private final Vec3 lightPos = new Vec3(1.2f, 1.0f, 2.0f);
    private int[] diffuseMap;
    private int[] specularMap;

    public LightCastersSpotRender(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        lightingShader = new Shader.Builder(context)
                .setVertexShader(R.raw.vertex_light_casters_spot)
                .setFragShader(R.raw.frag_light_casters_spot)
                .build();

        lightCubeShader = new Shader.Builder(context)
                .setVertexShader(R.raw.vertex_light_cube)
                .setFragShader(R.raw.frag_light_cube)
                .build();

        diffuseMap = bindSingleTexture2D();
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.container2);
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        GLES20.glGenerateMipmap(GL_TEXTURE_2D);

        specularMap = bindSingleTexture2D();
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        GLES20.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.container2);
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap2, 0);
        GLES20.glGenerateMipmap(GL_TEXTURE_2D);

        lightingShader.use();
        //使用glUniform1i告诉OpenGL每个着色器采样器属于哪个纹理单元
        lightingShader.setInt("material.diffuse", 0);
        lightingShader.setInt("material.specular", 1);

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

        //位置属性
        GLES20.glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * BYTES_PER_FLOAT, 0);
        GLES20.glEnableVertexAttribArray(0);
        //法向量属性
        GLES20.glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * BYTES_PER_FLOAT, 3 * BYTES_PER_FLOAT);
        GLES20.glEnableVertexAttribArray(1);
        //纹理属性
        GLES20.glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * BYTES_PER_FLOAT, 6 * BYTES_PER_FLOAT);
        GLES20.glEnableVertexAttribArray(2);


        //然后，配置光照的VAO(VBO保持不变，光照物体的顶点是相同的，它也是一个3D立方体)
        lightCubeVao = bindSingleVAO();

        //我们只需要绑定到VBO(用glVertexAttribPointer链接它)，不需要填充它;
        //VBO的数据已经包含了我们需要的所有内容(它已经绑定了，但我们出于教育目的再次这样做)
        GLES20.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);

        GLES20.glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * BYTES_PER_FLOAT, 0);
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
        lightingShader.setVec3("light.position", camera.getPosition());
        lightingShader.setVec3("light.direction", camera.getFront());
        //glm.cos(glm.radians(12.5f))等价于(float) Math.cos(Math.toRadians(12.5f)
        lightingShader.setFloat("light.cutOff", glm.cos(glm.radians(12.5f)));
        lightingShader.setVec3("viewPos", camera.getPosition());

        lightingShader.setVec3("light.ambient", 0.2f, 0.2f, 0.2f);
        lightingShader.setVec3("light.diffuse", 0.5f, 0.5f, 0.5f);
        lightingShader.setVec3("light.specular", 1.0f, 1.0f, 1.0f);
        lightingShader.setFloat("light.constant", 1.0f);
        lightingShader.setFloat("light.linear", 0.09f);
        lightingShader.setFloat("light.quadratic", 0.032f);

        //材质属性
        //specular lighting doesn't have full effect on this object's material
        lightingShader.setVec3("material.specular", 0.5f, 0.5f, 0.5f);
        lightingShader.setFloat("material.shininess", 64.0f);


        //view/projection transformations
        Mat4 projection = glm.perspective(glm.radians(camera.getFov()), (float) width / (float) height, 0.1f, 100.0f);
        lightingShader.setMatrix("projection", projection);
        lightingShader.setMatrix("view", camera.getViewMatrix());

        // world transformation
        Mat4 model = new Mat4(1.0f);
        lightingShader.setMatrix("model", model);

        //绑定漫反射贴图
        GLES20.glActiveTexture(GL_TEXTURE0);
        GLES20.glBindTexture(GL_TEXTURE_2D, diffuseMap[0]);

        //绑定镜面光贴图
        GLES20.glActiveTexture(GL_TEXTURE1);
        GLES20.glBindTexture(GL_TEXTURE_2D, specularMap[0]);


        //渲染立方体
        GLES30.glBindVertexArray(cubeVao[0]);
        for (int i = 0; i < 10; i++) {
            model = new Mat4();
            model = glm.translate(model, cubePositions[i]);
            float angle = 20.0f * i;
            model = glm.rotate(model, glm.radians(angle), new Vec3(1.0f, 0.3f, 0.5f));
            lightingShader.setMatrix("model", model);
            GLES20.glDrawArrays(GL_TRIANGLES, 0, 36);
        }

        //同样，当我们只有聚光灯时，灯对象很奇怪，不要渲染灯光对象
//        lightCubeShader.use();
//        lightCubeShader.setMatrix("projection", projection);
//        lightCubeShader.setMatrix("view", camera.getViewMatrix());
//        model = new Mat4(1.0f);
//        model = glm.translate(model, lightPos);
//        //一个更小的立方体
//        model = glm.scale(model, new Vec3(0.2f));
//        lightCubeShader.setMatrix("model", model);
//
//        GLES30.glBindVertexArray(lightCubeVao[0]);
//        GLES20.glDrawArrays(GL_TRIANGLES, 0, 36);
    }
}
