package com.example.opengl.data;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_STATIC_DRAW;

import android.opengl.GLES20;
import android.opengl.GLES30;

import com.example.opengl.base.Shader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

/**
 * @author wuzhanqiao
 * @date 2022/11/17.
 */
public class Mesh {
    protected final static int BYTES_PER_INT = 4;
    protected final static int BYTES_PER_FLOAT = 4;
    /*  渲染数据  */
    private int[] VAO = new int[1];
    private int[] VBO = new int[1];
    private int[] EBO = new int[1];

    /*  网格数据  */
    public List<Vertex> vertices;
    public List<Integer> indices;
    public List<Texture> textures;

    public Mesh(List<Vertex> vertices, List<Integer> indices, List<Texture> textures) {
        this.vertices = vertices;
        this.indices = indices;
        this.textures = textures;
        setupMesh();
    }

    public void draw(Shader shader) {
        // bind appropriate textures
        int diffuseNr = 1;
        int specularNr = 1;
        int normalNr = 1;
        int heightNr = 1;
        for (int i = 0; i < textures.size(); i++) {
            //在绑定之前激活适当的纹理单元
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + i);
            //检索纹理编号(diffuse_textureN中的N)
            String number = "";
            String name = textures.get(i).type;
            if (name.equals("texture_diffuse"))
                number = diffuseNr++ + "";
            else if (name.equals("texture_specular"))
                number = specularNr++ + "";
            else if (name.equals("texture_normal"))
                number = normalNr++ + "";
            else if (name.equals("texture_height"))
                number = heightNr++ + "";

            //现在设置采样器到正确的纹理单位
            GLES20.glUniform1i(GLES20.glGetUniformLocation(shader.getId(), name + number), i);
            //最后绑定纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures.get(i).id);
        }

        //绘制mesh
        GLES30.glBindVertexArray(VAO[0]);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.size(), GLES20.GL_UNSIGNED_INT, 0);
        GLES30.glBindVertexArray(0);

        //在配置完成后，将所有内容设置回默认值始终是一个好做法。
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
    }

    private void setupMesh() {
        GLES30.glGenVertexArrays(1, VAO, 0);
        GLES30.glBindVertexArray(VAO[0]);


        GLES20.glGenBuffers(1, VBO, 0);
        GLES20.glBindBuffer(GL_ARRAY_BUFFER, VBO[0]);

        GLES20.glGenBuffers(1, EBO, 0);
        GLES20.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO[0]);

        int vSize = vertices.size() * Vertex.size();
        FloatBuffer verticesBuffer = ByteBuffer
                .allocateDirect(vSize)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        float[] verticesData = new float[vSize / BYTES_PER_FLOAT];
        int vIdx = 0;
        for (Vertex v : vertices) {
            verticesData[vIdx++] = v.position.x;
            verticesData[vIdx++] = v.position.y;
            verticesData[vIdx++] = v.position.z;

            verticesData[vIdx++] = v.normal.x;
            verticesData[vIdx++] = v.normal.y;
            verticesData[vIdx++] = v.normal.z;

            verticesData[vIdx++] = v.texCoords.x;
            verticesData[vIdx++] = v.texCoords.y;
        }


        verticesBuffer.put(verticesData);
        verticesBuffer.position(0);
        GLES20.glBufferData(GL_ARRAY_BUFFER, vSize, verticesBuffer, GL_STATIC_DRAW);


        int iSize = indices.size() * BYTES_PER_INT;
        IntBuffer indicesBuffer = ByteBuffer
                .allocateDirect(iSize)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer();
        int[] indicesData = new int[indices.size()];
        for (int i : indices) {
            indicesData[i] = i;
        }
        indicesBuffer.put(indicesData);
        indicesBuffer.position(0);
        GLES20.glBufferData(GL_ELEMENT_ARRAY_BUFFER, iSize, indicesBuffer, GL_STATIC_DRAW);

        // 顶点位置
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.size(), 0);
        // 顶点法线
        GLES20.glEnableVertexAttribArray(1);
        GLES20.glVertexAttribPointer(1, 3, GL_FLOAT, false, Vertex.size(), Vertex.normalOffset());
        // 顶点纹理坐标
        GLES20.glEnableVertexAttribArray(2);
        GLES20.glVertexAttribPointer(2, 2, GL_FLOAT, false, Vertex.size(), Vertex.texCoordsOffset());
    }
}
