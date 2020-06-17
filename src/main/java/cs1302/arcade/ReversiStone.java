package cs1302.arcade;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

/**
 * Represents a reversi stone.
 */
public class ReversiStone extends Circle {

    /**
     * Constructs a new ReversiStone object with set radius.
     */
    public ReversiStone() {

        // Construct radius and set paint
        super(50, Color.BLUE);

    }  
}
