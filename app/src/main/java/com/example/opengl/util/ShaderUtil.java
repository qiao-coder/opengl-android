package com.example.opengl.util;

import android.content.Context;

import androidx.annotation.RawRes;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author wuzhanqiao
 * @date 2022/4/29.
 */
public class ShaderUtil {
    public static String loadShaderCode(Context context, @RawRes int rawId) {
        String result = null;
        try {
            InputStream in = context.getResources().openRawResource(rawId);
            int ch = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((ch = in.read()) != -1) {
                baos.write(ch);
            }
            baos.close();
            in.close();
            result = baos.toString("UTF-8");
            result = result.replaceAll("\\r\\n", "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
