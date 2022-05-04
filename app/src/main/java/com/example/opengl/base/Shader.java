package com.example.opengl.base;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_TRUE;
import static android.opengl.GLES20.GL_VERTEX_SHADER;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES32;

import androidx.annotation.RawRes;

import com.example.opengl.util.ShaderUtil;

/**
 * @author wuzhanqiao
 * @date 2022/5/2.
 */
public class Shader {
    private static final String TAG = "Shader";
    private final Context context;
    private final int vertexShaderRawId;
    private final int fragShaderRawId;
    private final int program;
    private int geometryShaderRawId = -1;


    private Shader(Builder builder) {
        context = builder.context;
        vertexShaderRawId = builder.vertexShaderRawId;
        fragShaderRawId = builder.fragShaderRawId;
        geometryShaderRawId = builder.geometryShaderRawId;
        program = createShaderProgram();
    }

    public void use() {
        GLES20.glUseProgram(program);
    }

    public void setBoolean(String name, boolean value) {
        int location = GLES20.glGetUniformLocation(program, name);
        checkLocation(location);
        GLES20.glUniform1i(location, value ? 1 : 0);
    }

    public void setInt(String name, int value) {
        int location = GLES20.glGetUniformLocation(program, name);
        checkLocation(location);
        GLES20.glUniform1i(location, value);
    }

    public void setFloat(String name, float value) {
        int location = GLES20.glGetUniformLocation(program, name);
        checkLocation(location);
        GLES20.glUniform1f(location, value);
    }

    private void checkLocation(int location) {
        if (location == -1)
            throw new RuntimeException("The name does not correspond to an active uniform variable in program");
    }

    private int createShader(int type, @RawRes int rawId) {
        int shader = GLES20.glCreateShader(type);
        String shaderCode = ShaderUtil.loadShaderCode(context, rawId);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        int[] result = new int[1];
        GLES20.glGetShaderiv(shader, GL_COMPILE_STATUS, result, 0);

        if (result[0] != GL_TRUE) {
            String errorMsg = GLES20.glGetShaderInfoLog(shader);
            throw new RuntimeException(getShaderName(type) + " compile failed : " + errorMsg);
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

    private int createShaderProgram() {
        int shaderProgram = GLES20.glCreateProgram();

        int vertexShader = createShader(GL_VERTEX_SHADER, vertexShaderRawId);
        int fragShader = createShader(GL_FRAGMENT_SHADER, fragShaderRawId);

        GLES20.glAttachShader(shaderProgram, vertexShader);
        GLES20.glAttachShader(shaderProgram, fragShader);

        int geometryShader = -1;
        if (geometryShaderRawId != -1 && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            geometryShader = createShader(GLES32.GL_GEOMETRY_SHADER, geometryShaderRawId);
            GLES20.glAttachShader(shaderProgram, geometryShader);
        }

        GLES20.glLinkProgram(shaderProgram);

        int[] result = new int[1];
        GLES20.glGetProgramiv(shaderProgram, GL_LINK_STATUS, result, 0);

        if (result[0] != GL_TRUE) {
            String errorMsg = GLES20.glGetProgramInfoLog(shaderProgram);
            throw new RuntimeException("ShaderProgram Link failed : " + errorMsg);
        }

        GLES20.glDeleteShader(vertexShader);
        GLES20.glDeleteShader(fragShader);
        if (geometryShader != -1) GLES20.glDeleteShader(geometryShader);
        return shaderProgram;
    }

    public static class Builder {
        private final Context context;
        private int vertexShaderRawId = -1;
        private int fragShaderRawId = -1;
        private int geometryShaderRawId = -1;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setVertexShader(@RawRes int id) {
            vertexShaderRawId = id;
            return this;
        }

        public Builder setFragShader(@RawRes int id) {
            fragShaderRawId = id;
            return this;
        }

        public Builder setGeometryShader(@RawRes int id) {
            geometryShaderRawId = id;
            return this;
        }

        public Shader build() {
            if (vertexShaderRawId == -1)
                throw new IllegalArgumentException("Vertex Shader must be set!");
            if (fragShaderRawId == -1)
                throw new IllegalArgumentException("Fragment Shader must be set!");
            return new Shader(this);
        }
    }
}
