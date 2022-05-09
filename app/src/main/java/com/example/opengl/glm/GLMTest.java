package com.example.opengl.glm;

import static glm_.Java.glm;

import android.util.Log;

import glm_.mat4x4.Mat4;
import glm_.vec3.Vec3;
import glm_.vec4.Vec4;

/**
 * @author wuzhanqiao
 * @date 2022/5/6.
 */
public class GLMTest {
    private static final String TAG = "GLMTest";

    /**
     * 把一个向量(1, 0, 0)位移(1, 1, 0)个单位
     */
    public static void testVectorMove() {
        //创建一个向量
        Vec4 vec = new Vec4(1.0f, 0.0f, 0.0f, 1.0f);
        //创建一个矩阵，默认是一个4x4单位矩阵(除了对角线是1以外，都是0)。
        Mat4 trans = new Mat4();
        //创建一个变换矩阵：位移矩阵（利用单位矩阵和位移向量(1, 1, 0)生成)
        trans = glm.translate(trans, new Vec3(1.0f, 1.0f, 0.0f));
        //位移矩阵左乘向量(1, 0, 0)，得到位移后的向量(2, 1, 0)
        vec = trans.times(vec);
        //日志输出：(2, 1, 0)
        Log.d(TAG, "(" + vec.x + "," + vec.y + "," + vec.z + ")");
    }
}
