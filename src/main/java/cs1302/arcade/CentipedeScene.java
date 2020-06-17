package cs1302.arcade;

import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import cs1302.arcade.CentipedeGame;

/**
 * Represents the scene for the Centipede game.
 */
public class CentipedeScene extends Scene {

    /**
     * Constructs a new Centipede Scene.
     *
     * @param g the game an {@code CentipedeGame} object.
     */
    public CentipedeScene(CentipedeGame g) {

        super(g, 1000, 720);

    }

}
