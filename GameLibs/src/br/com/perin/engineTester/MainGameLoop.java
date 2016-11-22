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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GLContext;

/**
 *
 * @author Joaov
 */
public class MainGameLoop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        float[] vertices = {
            // 1st Triangle
            -0.5f, 0.5f, 0f,
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            // 2nd Triangle
            0.5f, -0.5f, 0f,
            0.5f, 0.5f, 0f,
            -0.5f, 0.5f, 0f,
            // 3nd Triangle
            0.7f, -0.23f, 0f,
            0.3f, 0.2f, 0f,
            -0.2f, 0.3f, 0f
        };

        RawModel model = loader.loadToVAO(vertices);
        while (!Display.isCloseRequested()) {
            renderer.prepare();
            renderer.render(model);
            DisplayManager.updateDisplay();
        }
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    public void run() {

    }

}
