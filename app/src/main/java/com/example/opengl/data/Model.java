//package com.example.opengl.data;
//
//import static assimp.AiTexture.Type;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.opengl.GLES20;
//import android.opengl.GLUtils;
//import android.util.Log;
//
//import com.example.opengl.base.Shader;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//import assimp.AiMaterial;
//import assimp.AiMesh;
//import assimp.AiNode;
//import assimp.AiScene;
//import assimp.Importer;
//import assimp.SceneKt;
//import glm_.vec2.Vec2;
//
///**
// * @author wuzhanqiao
// * @date 2022/11/17.
// */
//public class Model {
//    private static final String TAG = "Model";
//    /*  模型数据  */
//    private List<Mesh> meshes = new LinkedList<>();
//    private String directory;
//    private Map<String, Texture> texturesLoaded = new HashMap<>();
//
//    public Model(String path) {
//        loadModel(path);
//    }
//
//    public void draw(Shader shader) {
//        for (int i = 0; i < meshes.size(); i++) meshes.get(i).draw(shader);
//    }
//
//    private void loadModel(String path) {
//        Log.d(TAG, "loadModel() called with: path = [" + path + "]");
//        //通过Assimp读取模型文件
//        Importer importer = new Importer();
//        AiScene scene = importer.readFile(path);
//        //检查错误
//        if (scene == null || (scene.getFlags() & SceneKt.AI_SCENE_FLAGS_INCOMPLETE) == SceneKt.AI_SCENE_FLAGS_INCOMPLETE) {
//            Log.e(TAG, "模型加载错误 :" + importer.getErrorString());
//            return;
//        }
//        //获取模型文件所在的文件夹路径
//        directory = path.substring(0, path.lastIndexOf('/'));
//        //递归地处理Assimp的rootNode
//        processNode(scene.rootNode, scene);
//    }
//
//    //以递归方式处理节点。处理位于节点上的每个网格，并在其子节点(如果有的话)上重复此过程。
//    private void processNode(AiNode node, AiScene scene) {
//        //处理位于当前节点的每个网格
//        for (int i = 0; i < node.getNumMeshes(); i++) {
//            //节点对象只包含在scene中用于索引实际对象的索引。
//            //scene包含了所有的数据，节点只是为了保持物品的组织(如节点之间的关系)。
//            AiMesh mesh = scene.getMeshes().get(node.getMeshes()[i]);
//            meshes.add(processMesh(mesh, scene));
//        }
//        //在我们处理完所有的网格(如果有的话)之后，我们递归地处理每个子节点
//        for (int i = 0; i < node.getNumChildren(); i++) {
//            processNode(node.getChildren().get(i), scene);
//        }
//    }
//
//    private Mesh processMesh(AiMesh mesh, AiScene scene) {
//        //填充数据
//        List<Vertex> vertices = new LinkedList<>();
//        List<Integer> indices = new LinkedList<>();
//
//        //遍历网格的每个顶点
//        for (int i = 0; i < mesh.getNumVertices(); i++) {
//            Vertex vertex = new Vertex();
//            vertex.position = mesh.getVertices().get(i);
//            ;
//            if (mesh.getHasNormals()) {
//                vertex.normal = mesh.getNormals().get(i);
//            }
//            mesh.getTextureCoords();
//            //网格是否包含纹理坐标?
//            if (mesh.getTextureCoords().get(0) != null) {
//                //一个顶点可以包含多达8个不同的纹理坐标。因此，我们假设我们不会使用一个顶点可以有多个纹理坐标的模型，所以我们总是采用第一组(0)。
//                Vec2 vec = new Vec2();
//                vec.x = mesh.getTextureCoords().get(0).get(i)[0];
//                vec.y = mesh.getTextureCoords().get(0).get(i)[1];
//                vertex.texCoords = vec;
////                //tangent
////                vertex.tangent = mesh.getTangents().get(i);
////                //bitangent
////                vertex.bitangent = mesh.getBitangents().get(i);
//            }
//            vertices.add(vertex);
//        }
//        //现在遍历网格的每个面(面是网格的三角形)，并检索相应的顶点索引。
//        for (int i = 0; i < mesh.getNumFaces(); i++) {
//            List<Integer> face = mesh.getFaces().get(i);
//            //检索面部的所有索引并将它们存储在索引数组中
//            for (int j = 0; j < face.size(); j++)
//                indices.add(face.get(0));
//        }
//        //处理材质
//        AiMaterial material = scene.getMaterials().get(mesh.getMaterialIndex());
//        //我们假设着色器中的采样器名称有一个约定。每个漫反射纹理都应该命名为“texture_diffuseN”，
//        //其中N是一个从1到MAX_SAMPLER_NUMBER的连续数字。
//        //同样适用于其他纹理，如下列表总结:
//        // 漫反射: texture_diffuseN
//        // 镜面光: texture_specularN
//        // 法线: texture_normalN
//
//
//        List<Texture> textures = loadMaterialTextures(material);
//
//        //返回从提取的网格数据创建的网格对象
//        return new Mesh(vertices, indices, textures);
//    }
//
//    private String getTypeName(Type type){
//        String typeName = "";
//        switch (type){
//            case diffuse:
//                typeName = "texture_diffuse";
//                break;
//            case specular:
//                typeName = "texture_specular";
//                break;
//            case height:
//                typeName = "texture_normal";
//                break;
//            case ambient:
//                typeName = "texture_height";
//                break;
//            default:
//                break;
//        }
//        return typeName;
//    }
//
//    private List<Texture> loadMaterialTextures(AiMaterial mat) {
//        List<Texture> textures = new LinkedList<>();
//        for (int i = 0; i < mat.getTextures().size(); i++) {
//            AiMaterial.Texture t = mat.getTextures().get(i);
//            String typeName = getTypeName(Objects.requireNonNull(t.getType()));
//            if(typeName.isEmpty()) continue;
//            String path = t.getFile();
//            //检查之前是否加载了纹理，如果是，继续下一次迭代:跳过加载一个新的纹理
//            Texture old = texturesLoaded.get(path);
//            //一个具有相同文件路径的纹理已经被加载，继续下一个。(优化)
//            if (old != null) {
//                textures.add(texturesLoaded.get(path));
//                continue;
//            }
//            //如果纹理还没有加载，加载它
//            Texture texture = new Texture();
//            texture.id = TextureFromFile(path, directory, false);
//            texture.type = typeName;
//            texture.path = path;
//            textures.add(texture);
//            //将其存储为整个模型加载的纹理，以确保我们不会不必要地加载重复的纹理。
//            texturesLoaded.put(path, texture);
//        }
//        return textures;
//    }
//
//    private int TextureFromFile(String path, String directory, boolean gamma) {
//        String filename = directory + '/' + path;
//        int[] textureID = new int[1];
//        GLES20.glGenTextures(1, textureID, 0);
//
//        Bitmap bitmap = BitmapFactory.decodeFile(filename);
//        if (bitmap != null) {
//
//            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID[0]);
//            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
//            GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
//
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
//            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
//            bitmap.recycle();
//        } else {
//            Log.e(TAG, "Texture failed to load at path: " + path);
//        }
//        return textureID[0];
//    }
//}
