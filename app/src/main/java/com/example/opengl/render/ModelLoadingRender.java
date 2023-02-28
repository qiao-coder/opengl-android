//package com.example.opengl.render;
//
//import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
//import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
//import static android.opengl.GLES20.GL_DEPTH_TEST;
//import static glm_.Java.glm;
//
//import android.content.Context;
//import android.opengl.GLES20;
//import android.util.Log;
//
//import com.example.opengl.R;
//import com.example.opengl.base.BaseRender;
//import com.example.opengl.base.Shader;
//import com.example.opengl.data.Model;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//import javax.microedition.khronos.egl.EGLConfig;
//import javax.microedition.khronos.opengles.GL10;
//
//import glm_.mat4x4.Mat4;
//
///**
// * @author wuzhanqiao
// * @date 2022/6/29.
// */
//public class ModelLoadingRender extends BaseRender {
//
//    private Shader lightingShader;
//    private int width;
//    private int height;
//    private Model ourModel;
//
//    public ModelLoadingRender(Context context) {
//        super(context);
//    }
//
//    @Override
//    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        lightingShader = new Shader.Builder(context)
//                .setVertexShader(R.raw.vertex_model_loading)
//                .setFragShader(R.raw.frag_model_loading)
//                .build();
//        InputStream inputStream = null;
//        FileOutputStream outputStream = null;
//        try {
//            inputStream = context.getAssets().open("nanosuit.obj");
//            String path = context.getCacheDir().getAbsolutePath();
//            File file = new File(path, "nanosuit.obj");
//            outputStream = new FileOutputStream(file);
//            byte[] buf = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buf)) > 0) {
//                outputStream.write(buf, 0, bytesRead);
//            }
//            ourModel = new Model(file.getPath());
//        } catch (IOException e) {
//            Log.e(TAG,"e : " + e.getMessage());
//            try {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
////        try {
////            AssetFileDescriptor afd = context.getAssets().openFd("musics/SleepOceation.mp3");
//
////            Uri uri = Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.nanosuit);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//
//    @Override
//    public void onSurfaceChanged(GL10 gl, int width, int height) {
//        this.width = width;
//        this.height = height;
//        GLES20.glViewport(0, 0, width, height);
//    }
//
//    @Override
//    public void onDrawFrame(GL10 gl) {
//        GLES20.glEnable(GL_DEPTH_TEST);
//        GLES20.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
//        GLES20.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//        //记得先激活着色器
//        lightingShader.use();
//
//        //view/projection transformations
//        Mat4 projection = glm.perspective(glm.radians(camera.getFov()), (float) width / (float) height, 0.1f, 100.0f);
//        lightingShader.setMatrix("projection", projection);
//        lightingShader.setMatrix("view", camera.getViewMatrix());
//
//        // world transformation
//        Mat4 model = new Mat4(1.0f);
//        lightingShader.setMatrix("model", model);
//
//        ourModel.draw(lightingShader);
//    }
//}
