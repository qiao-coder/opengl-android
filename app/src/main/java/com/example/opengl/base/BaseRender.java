package com.example.opengl.base;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_TEXTURE_2D;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.example.opengl.data.Direction;

import glm_.vec3.Vec3;

/**
 * @author wuzhanqiao
 * @date 2022/5/3.
 */
public abstract class BaseRender implements GLSurfaceView.Renderer {
    protected final String TAG = getClass().getName();
    protected Context context;
    protected final static int BYTES_PER_INT = 4;
    protected final static int BYTES_PER_FLOAT = 4;
    protected Camera camera = new Camera(new Vec3(0.0f, 0.0f, 3.0f));

    public BaseRender(Context context) {
        this.context = context;
    }

    protected int[] bindSingleVAO() {
        int[] vao = new int[1];
        GLES30.glGenVertexArrays(1, vao, 0);
        GLES30.glBindVertexArray(vao[0]);
        return vao;
    }

    protected int[] bindSingleVBO() {
        int[] vbo = new int[1];
        GLES20.glGenBuffers(1, vbo, 0);
        GLES20.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
        return vbo;
    }

    protected int[] bindSingleEBO() {
        int[] ebo = new int[1];
        GLES20.glGenBuffers(1, ebo, 0);
        GLES20.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo[0]);
        return ebo;
    }

    protected int[] bindSingleTexture2D() {
        int[] texture = new int[1];
        GLES20.glGenTextures(1, texture, 0);
        GLES20.glBindTexture(GL_TEXTURE_2D, texture[0]);
        return texture;
    }

    public void move(Direction d, float cameraSpeed) {
        camera.move(d, cameraSpeed);
    }

    public void setYaw(float yaw) {
        camera.setYaw(yaw);
    }

    public void setPitch(float pitch) {
        camera.setPitch(pitch);
    }

    public void setFov(float fov) {
        camera.setFov(fov);
    }

    public boolean handleZoom(MotionEvent event, int fingersMaxDistance) {
        return camera.handleZoom(event, fingersMaxDistance);
    }
}
