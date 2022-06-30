package com.example.opengl.base;

import static glm_.Java.glm;

import android.view.MotionEvent;

import com.example.opengl.data.Direction;

import glm_.mat4x4.Mat4;
import glm_.vec3.Vec3;

/**
 * @author wuzhanqiao
 * @date 2022/6/17.
 */
public class Camera {
    private final static float MIN_FOV = 1.0f;
    private final static float MAX_FOV = 45.0f;
    private final static float MIN_PITCH = -89.0f;
    private final static float MAX_PITCH = 89.0f;
    //cameras属性
    private Vec3 position = new Vec3(0.0f, 0.0f, 0.0f);
    private Vec3 front = new Vec3(0.0f, 0.0f, -1.0f);
    private Vec3 up;
    private Vec3 right;
    private Vec3 worldUp = new Vec3(0.0f, 1.0f, 0.0f);
    //欧拉角
    private float yaw = -90.0f;
    private float pitch = 0.0f;
    //camera选项
    private float fov = 45.0f;
    private float lastDist = 0.0f;

    public Camera(Vec3 position) {
        this.position = position;
        updateCameraVectors();
    }

    public Camera(Vec3 position, Vec3 worldUp) {
        this.position = position;
        this.worldUp = worldUp;
        updateCameraVectors();
    }

    public Camera(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
        updateCameraVectors();
    }

    public Camera(Vec3 position, Vec3 worldUp, float yaw, float pitch) {
        this.position = position;
        this.worldUp = worldUp;
        this.yaw = yaw;
        this.pitch = pitch;
        updateCameraVectors();
    }

    /**
     * 获取使用欧拉角和LookAt矩阵计算的view矩阵
     */
    public Mat4 getViewMatrix() {
        return glm.lookAt(position, position.plus(front), up);
    }

    public void move(Direction d, float cameraSpeed) {
        switch (d) {
            case UP:
                //即position += front * cameraSpeed
                position = position.plus(front.times(cameraSpeed));
                break;
            case DOWN:
                //即position -= front * cameraSpeed;
                position = position.minus(front.times(cameraSpeed));
                break;
            case LEFT:
                //即position -= right * cameraSpeed
                position = position.minus(right.times(cameraSpeed));
                break;
            case RIGHT:
                //即position += right * cameraSpeed;
                position = position.plus(right.times(cameraSpeed));
                break;
        }
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        updateCameraVectors();
    }

    public void setPitch(float pitch) {
        setPitch(pitch, true);
    }

    public void setPitch(float pitch, boolean constrainPitch) {
        if (constrainPitch) {
            if (pitch > MAX_PITCH) {
                pitch = MAX_PITCH;
            } else if (pitch < MIN_PITCH) {
                pitch = MIN_PITCH;
            }
        }
        this.pitch = pitch;
        updateCameraVectors();
    }

    public void setFov(float fov) {
        this.fov = adjustFov(fov);
    }

    private float adjustFov(float fov) {
        if (fov > MAX_FOV) {
            fov = MAX_FOV;
        } else if (fov < MIN_FOV) {
            fov = MIN_FOV;
        }
        return fov;
    }

    public float getFov() {
        return fov;
    }

    public Vec3 getPosition() {
        return position;
    }

    public Vec3 getFront() {
        return front;
    }


    /**
     * 根据摄像机更新的欧拉角，重新计算front等向量
     */
    private void updateCameraVectors() {
        //计算front向量
        Vec3 direction = new Vec3();
        direction.x = (float) Math.cos(glm.radians(yaw)) * (float) Math.cos(glm.radians(pitch));
        direction.y = (float) Math.sin(glm.radians(pitch));
        direction.z = (float) Math.sin(glm.radians(yaw)) * (float) Math.cos(glm.radians(pitch));
        front = glm.normalize(direction, new Vec3());
        //重新计算right和up向量
        //将向量标准化，因为你向上或向下看的次数越多，它们的长度就越接近0，从而导致运动变慢。
        right = glm.normalize(glm.cross(front, worldUp, new Vec3()), new Vec3());
        up = glm.normalize(glm.cross(right, front, new Vec3()), new Vec3());
    }

    public boolean handleZoom(MotionEvent event, int fingersMaximumDistance) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN: {
                //刚好双指
                if (event.getPointerCount() == 2) {
                    lastDist = getTwoFingersDistance(event);
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (event.getPointerCount() == 2) {
                    float dist = getTwoFingersDistance(event);
                    float offset = getFovOffset(lastDist - dist, fingersMaximumDistance);
                    lastDist = dist;
                    float newFov = adjustFov(fov + offset);
                    if (newFov != fov) {
                        setFov(newFov);
                        return true;
                    }
                }
                break;
            }
        }
        return false;
    }

    private float getTwoFingersDistance(MotionEvent event) {
        float x0 = event.getX(0);
        float y0 = event.getY(0);
        float x1 = event.getX(1);
        float y1 = event.getY(1);
        return (float) Math.sqrt(Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2));
    }

    private float getFovOffset(float d, int fingersMaximumDistance) {
        float unit = (MAX_FOV - MIN_FOV) / fingersMaximumDistance;
        return d * unit;
    }
}
