/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.perin.renderEngine;

import br.com.perin.models.RawModel;
import br.com.perin.textures.ModelTexture;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author Joaov
 */
public class Loader implements Singleton {

    /** Instance */
    private static Loader instance;

    private Loader() {

    }

    private final List<Integer> vaos = new ArrayList<>();
    private final List<Integer> vbos = new ArrayList<>();
    private final List<Integer> textures = new ArrayList<>();

    public RawModel loadToVAO(float[] positions, int[] indices) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        vaos.add(vaoID);
        storeDataInAttributeList(0, 3, positions);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public RawModel loadToVAO(float[] positions, float[] texCoords, int[] indices) {
        int vaoID = createVAO();
        vaos.add(vaoID);
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, texCoords);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public void cleanUp() {
        vaos.stream().forEach(GL30::glDeleteVertexArrays);
        vbos.stream().forEach(GL15::glDeleteBuffers);
        textures.stream().forEach(GL11::glDeleteTextures);
    }

    private int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, int coordSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboId = GL15.glGenBuffers();
        vbos.add(vboId);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public int loadTexture(String file) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + file + ".png"), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int id = texture.getTextureID();
        textures.add(id);
        return id;
    }

    public ModelTexture loadModelTexture(String file) {
        return new ModelTexture(loadTexture(file));
    }

    @Override
    public void instantiate() {
        if (instance == null) {
            instance = new Loader();
        }
    }

    public static final Loader get() {
        if (instance == null) {
            instance = new Loader();
        }
        return instance;
    }

}
