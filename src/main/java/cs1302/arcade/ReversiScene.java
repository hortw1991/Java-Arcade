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
import cs1302.arcade.ArcadeApp;
import cs1302.arcade.ReversiGame;

/**
 *The scene for a {@code ReversiGame}. Default size is 1000 x 720.
 */
public class ReversiScene extends Scene {

    /**
     *Constructs a {@code ReversiScene} object.
     *
     *@param ap an ArcadeApp object.
     */
    public ReversiScene(ArcadeApp ap) {

        super(new ReversiGame(ap), 700, 720);

    } //ReversiScene

} //ReversiScene
