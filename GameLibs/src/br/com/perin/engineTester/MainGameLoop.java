/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.perin.engineTester;

import br.com.perin.models.TextureModel;
import br.com.perin.renderEngine.DisplayManager;
import br.com.perin.renderEngine.Loader;
import br.com.perin.renderEngine.RawModel;
import br.com.perin.renderEngine.Renderer;
import br.com.perin.shaders.StaticShader;
import br.com.perin.textures.ModelTexture;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Joaov
 */
public class MainGameLoop implements Runnable {

    /** Vertex Array for image vertices */
    private final float[] vertices = {
        -0.5f, 0.5f, 0f, //P0
        -0.5f, -0.5f, 0f, //P1
        0.5f, -0.5f, 0f, //P2
        0.5f, 0.5f, 0f //P3
    };

    /** Index Array */
    private final int[] indices = {
        // 1st Triangle
        0, 1, 3, //P0, P1, P3
        // 2nd Triangle
        3, 1, 2 //P3, P1, P2
    };

    /** Vertex Array for texture vertices */
    private final float[] texCoords = {
        0, 0, //P0
        0, 1,//P1
        1, 1, //P2
        1, 0 //P3
    };

    /**
     * Main entry point
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new MainGameLoop().run();
    }

    /**
     * Runs the main code
     */
    @Override
    public void run() {
        DisplayManager.createDisplay();

        RawModel model = Loader.get().loadToVAO(vertices, indices);
        ModelTexture modelTexture = new ModelTexture(Loader.get().loadTexture("test-01"));
        TextureModel textureModel = new TextureModel(model, modelTexture);
        StaticShader shader = new StaticShader();
        
        while (!Display.isCloseRequested()) {
            Renderer.get().prepare();
            shader.start();
            Renderer.get().render(textureModel);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        Loader.get().cleanUp();
        shader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
