/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.perin.engineTester;

import br.com.perin.renderEngine.DisplayManager;
import org.lwjgl.opengl.Display;

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

        while (!Display.isCloseRequested()) {
            DisplayManager.updateDisplay();
        }

        DisplayManager.closeDisplay();
    }

}
