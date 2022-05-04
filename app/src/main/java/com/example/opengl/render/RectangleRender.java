package com.example.opengl.render;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.GL_VERTEX_SHADER;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author wuzhanqiao
 * @date 2022/4/28.
 */
public class RectangleRender implements GLSurfaceView.Renderer {
    private final static int BYTES_PER_INT = 4;
    private final static int BYTES_PER_FLOAT = 4;
    private static final String TAG = "RectangleRender";
    private float[] vertices = {
            0.5f, 0.5f, 0.0f,   // 右上角
            0.5f, -0.5f, 0.0f,  // 右下角
            -0.5f, -0.5f, 0.0f, // 左下角
            -0.5f, 0.5f, 0.0f   // 左上角
    };

    private int[] indices = { // 注意索引从0开始!
            0, 1, 3, // 第一个三角形
            1, 2, 3  // 第二个三角形
    };

    private String vertexShaderCode = "#version 300 es\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n" +
            "}";

    private String fragmentShaderCode = "#version 300 es\n" +
            "precision mediump float;\n" +
            "out vec4 FragColor;\n" +
            "void main()\n" +
            "{\n" +
            "   FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n" +
            "}";
    private int shaderProgram;

    private int[] vao = new int[1];
    private FloatBuffer verticesBuffer;

    private int[] ebo = new int[1];

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        GLES30.glGenVertexArrays(1, vao, 0);
        GLES30.glBindVertexArray(vao[0]);


        GLES20.glGenBuffers(1, ebo, 0);
        GLES20.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo[0]);

        IntBuffer indicesBuffer = ByteBuffer
                .allocateDirect(indices.length * BYTES_PER_INT)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer()
                .put(indices);
        indicesBuffer.position(0);
        GLES20.glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices.length * 4, indicesBuffer,
                GL_STATIC_DRAW);

        int[] vbo = new int[1];
        GLES20.glGenBuffers(1, vbo, 0);
        GLES20.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);


        int vertexShader = createShader(GL_VERTEX_SHADER, vertexShaderCode);
        int fragShader = createShader(GL_FRAGMENT_SHADER, fragmentShaderCode);
        shaderProgram = createShaderProgram(vertexShader, fragShader);


        verticesBuffer = ByteBuffer
                .allocateDirect(vertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertices);
        verticesBuffer.position(0);
        GLES20.glBufferData(GL_ARRAY_BUFFER, vertices.length * 4, verticesBuffer, GL_STATIC_DRAW);


        GLES20.glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * BYTES_PER_FLOAT, 0);
        GLES20.glEnableVertexAttribArray(0);


        // note that this is allowed, the call to glVertexAttribPointer registered VBO as the vertex attribute's bound vertex buffer object so afterwards we can safely unbind
        GLES20.glBindBuffer(GL_ARRAY_BUFFER, 0);

        // remember: do NOT unbind the EBO while a VAO is active as the bound element buffer object IS stored in the VAO; keep the EBO bound.
//        GLES20.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);


        //解绑VAO
        //You can unbind the VAO afterwards so other VAO calls won't accidentally modify this VAO,
        //but this rarely happens. Modifying other VAOs requires a call to glBindVertexArray anyways
        //so we generally don't unbind VAOs (nor VBOs) when it's not directly necessary.
//        GLES30.glBindVertexArray(0);


    }

    private int createShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        int[] result = new int[1];
        GLES20.glGetShaderiv(shader, GL_COMPILE_STATUS, result, 0);

        if (result[0] == 0) {
            String errorMsg = GLES20.glGetShaderInfoLog(shader);
            Log.d(TAG, getShaderName(type) + " compile failed : " + errorMsg);
        }
        return shader;
    }

    private String getShaderName(int type) {
        String name = "";
        switch (type) {
            case GL_VERTEX_SHADER:
                name = "GL_VERTEX_SHADER";
                break;
            case GL_FRAGMENT_SHADER:
                name = "GL_FRAGMENT_SHADER";
                break;
            case GLES32.GL_GEOMETRY_SHADER:
                name = "GL_GEOMETRY_SHADER";
                break;
        }
        return name;
    }

    private int createShaderProgram(Integer... shaders) {
        int shaderProgram = GLES20.glCreateProgram();
        for (Integer shader : shaders) {
            GLES20.glAttachShader(shaderProgram, shader);
        }
        GLES20.glLinkProgram(shaderProgram);

        int[] result = new int[1];
        GLES20.glGetProgramiv(shaderProgram, GL_LINK_STATUS, result, 0);

        if (result[0] == 0) {
            String errorMsg = GLES20.glGetProgramInfoLog(shaderProgram);
            Log.d(TAG, "ShaderProgram Link failed : " + errorMsg);
        }

        for (Integer shader : shaders) {
            GLES20.glDeleteShader(shader);
        }
        return shaderProgram;
    }

    private void checkError(String op) {
        for (int error = GLES20.glGetError(); error > 0; error = GLES20.glGetError()) {
            Log.d(TAG, "checkError " + op + " : " + error);
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        GLES20.glClear(GL_COLOR_BUFFER_BIT);

        GLES20.glUseProgram(shaderProgram);
        GLES30.glBindVertexArray(vao[0]);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_INT, 0);

        //OpenGL有glPolygonMode来配置线框模式，但OpenGL ES是没有该接口的，可以通过GL_LINE_LOOP来实现
//        GLES30.glDrawElements(GLES20.GL_LINE_LOOP, 6, GLES20.GL_UNSIGNED_INT, 0);

        //no need to unbind it every time
        //GLES30.glBindVertexArray(0);
    }
}
