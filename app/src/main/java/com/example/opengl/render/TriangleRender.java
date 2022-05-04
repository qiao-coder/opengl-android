package com.example.opengl.render;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_VERTEX_SHADER;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.Log;

import androidx.annotation.RawRes;

import com.example.opengl.R;
import com.example.opengl.util.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.logging.Handler;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author wuzhanqiao
 * @date 2022/4/12.
 */
public class TriangleRender implements GLSurfaceView.Renderer {
    protected final static int BYTES_PER_FLOAT = 4;
    protected final String TAG = getClass().getName();
    private final Context context;
    protected float[] vertices = {
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.0f, 0.5f, 0.0f
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
    //    private String vertexShaderCode = "layout (location = 0) in vec3 aPos;\n" +
//            "void main() {\n" +
//            "  gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n" +
//            "}";
//    private String fragmentShaderCode = "precision mediump float;\n" +
//            "void main() {\n" +
//            "  gl_FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);;\n" +
//            "}";
    protected int shaderProgram;

    protected int[] vao = new int[1];

    public TriangleRender(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d(TAG, "onSurfaceCreated() called with: thread = [" + Thread.currentThread() + "]");

        bindVAO();

        bindVBO();

        createShaderProgram();

        setVertexData();

        //解绑VAO
        //You can unbind the VAO afterwards so other VAO calls won't accidentally modify this VAO,
        //but this rarely happens. Modifying other VAOs requires a call to glBindVertexArray anyways
        //so we generally don't unbind VAOs (nor VBOs) when it's not directly necessary.
//        GLES30.glBindVertexArray(0);
    }

    protected int getVertexShaderCode() {
        return R.raw.vertex_triangle;
    }

    protected int getFragShaderCode() {
        return R.raw.frag_triangle;
    }


    protected void setVertexData() {
        FloatBuffer verticesBuffer = ByteBuffer
                .allocateDirect(vertices.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertices);
        verticesBuffer.position(0);
        GLES20.glBufferData(GL_ARRAY_BUFFER, vertices.length * BYTES_PER_FLOAT, verticesBuffer, GL_STATIC_DRAW);

        GLES20.glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * BYTES_PER_FLOAT, 0);
        GLES20.glEnableVertexAttribArray(0);
    }

    private void bindVBO() {
        int[] vbo = new int[1];
        GLES20.glGenBuffers(1, vbo, 0);
        GLES20.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
    }
//
//    <div align="center">
//<img src="https://upload.wikimedia.org/wikipedia/commons/5/5c/MipMap_Example_STS101.jpg">
//</div>

    private void bindVAO() {
        GLES30.glGenVertexArrays(1, vao, 0);
        GLES30.glBindVertexArray(vao[0]);
    }

    private int createShader(int type, @RawRes int rawId) {
        int shader = GLES20.glCreateShader(type);
        String shaderCode = ShaderUtil.loadShaderCode(context, rawId);
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

    private void createShaderProgram() {
        int vertexShader = createShader(GL_VERTEX_SHADER, getVertexShaderCode());
        int fragShader = createShader(GL_FRAGMENT_SHADER, getFragShaderCode());

        int shaderProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(shaderProgram, vertexShader);
        GLES20.glAttachShader(shaderProgram, fragShader);
        GLES20.glLinkProgram(shaderProgram);

        int[] result = new int[1];
        GLES20.glGetProgramiv(shaderProgram, GL_LINK_STATUS, result, 0);

        if (result[0] == 0) {
            String errorMsg = GLES20.glGetProgramInfoLog(shaderProgram);
            Log.d(TAG, "ShaderProgram Link failed : " + errorMsg);
        }

        //绘制的时候再激活
//        GLES20.glUseProgram(shaderProgram);

        GLES20.glDeleteShader(vertexShader);
        GLES20.glDeleteShader(fragShader);

        this.shaderProgram = shaderProgram;
    }

    private void checkError(String op) {
        for (int error = GLES20.glGetError(); error > 0; error = GLES20.glGetError()) {
            Log.d(TAG, "checkError " + op + " : " + error);
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG, "onSurfaceChanged() called with: gl = [" + gl + "], width = [" + width + "], height = [" + height + "],thread = [" + Thread.currentThread() + "]");
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        Log.d(TAG, "onDrawFrame() called with: gl = [" + gl + "], thread = [" + Thread.currentThread() + "]");

        // 渲染
        // 清除颜色缓冲
        GLES20.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        GLES20.glClear(GL_COLOR_BUFFER_BIT);

        // 激活着色器
        GLES20.glUseProgram(shaderProgram);

        // 绘制三角形
        GLES30.glBindVertexArray(vao[0]);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
    }
}
