package com.example.opengl.util;

import android.content.Context;

/**
 * @author wuzhanqiao
 * @date 2022/6/22.
 */
public class ContextUtil {
    public static int dp2px(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static float dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }
}
