/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.perin.engineTester;

import br.com.perin.renderEngine.DisplayManager;
import br.com.perin.renderEngine.Loader;
import br.com.perin.renderEngine.RawModel;
import br.com.perin.renderEngine.Renderer;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Joaov
 */
public class MainGameLoop implements Runnable {

    /** Vertex Array */
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
        while (!Display.isCloseRequested()) {
            Renderer.get().prepare();
            Renderer.get().render(model);
            DisplayManager.updateDisplay();
        }
        Loader.get().cleanUp();
        DisplayManager.closeDisplay();
    }

}
