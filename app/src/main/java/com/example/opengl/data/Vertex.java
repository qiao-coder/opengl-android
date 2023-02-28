package com.example.opengl.data;

import glm_.vec2.Vec2;
import glm_.vec3.Vec3;

/**
 * @author wuzhanqiao
 * @date 2022/11/17.
 */
public class Vertex {
    public Vec3 position = new Vec3();
    public Vec3 normal = new Vec3();
    public Vec2 texCoords = new Vec2();

    public static int size() {
        return 2 * 3 * 4 + 2 * 4;
    }

    public static int normalOffset() {
        return 3 * 4;
    }

    public static int texCoordsOffset() {
        return 6 * 4;
    }
}
