package cs1302.arcade;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

import cs1302.arcade.ReversiStone;

/**
 *The visual representation of a reversi square, or one space on a reversi board.
 *A square can be blank, highlighted, or have a piece in it. Pieces can be added to
 *or removed from squares.
 */
public class ReversiSquare extends HBox {

    Boolean hasToken;
    Boolean isRed;
    Boolean isHighlighted;
    Rectangle blank = new Rectangle(75, 75, Color.TRANSPARENT);
    Circle red = new Circle(30, Color.RED);
    Circle green = new Circle(30, Color.GREEN);

    /**
     *Constructs a {@code ReversiSquare} object. A {@code ReversiSquare} represents
     *one space on the board.
     */
    public ReversiSquare() {

        this.setMinWidth(75);
        this.setMinHeight(75);
        blank.setStroke(Color.BLACK);
        this.getChildren().add(blank);

        hasToken = false;
        isRed = false;
        isHighlighted = false;
    } //ReversiSquare

    /**
     *Adds a piece to a square.
     *
     *@param player the active player
     */
    public void addPiece(int player) {
        hasToken = true;

        this.getChildren().clear();

        // Adds a red piece or green piece depending on player move
        if (player == 1) {
            this.getChildren().add(red);
            isRed = true;
        } else {
            this.getChildren().add(green);
            isRed = false;
        }
    } //addPiece

    /**
     *Removes a piece from a square.
     *
     */
    public void removePiece() {
        hasToken = false;
        isRed = false;

        this.getChildren().clear();
        this.getChildren().add(blank);
    } //removePiece

    /**
     *Changes a square to the opposite player's color.
     *
     */
    public void flipPiece() {
        if (this.isRed()) {
            this.getChildren().clear();
            this.getChildren().add(green);
        } else {
            this.getChildren().clear();
            this.getChildren().add(red);
        }
    } //flipPiece

    /**
     *Returns {@code true} if a square has a piece, {@code false} otherwise.
     *
     *@return true if the square has a piece, {@code false} otherwise.
     */
    public boolean hasPiece() {
        return hasToken;
    } //hasPiece

    /**
     *Returns whether or not a space is red.
     *
     *@return true if a space is red.
     */
    public boolean isRed() {
        return isRed;
    } //isRed

    /**
     *Sets a space to be red.
     *
     */
    public void setRed() {
        this.getChildren().clear();
        this.getChildren().add(red);
        isRed = true;
        hasToken = true;
    } //setRed

    /**
     *Sets a space to be green.
     *
     */
    public void setGreen() {
        this.getChildren().clear();
        this.getChildren().add(green);
        isRed = false;
        hasToken = true;
    } //setGreen

    /**
     *Highlights a square.
     *
     */
    public void highlightSquare() {
        blank.setFill(Color.BLUE);
        blank.setStroke(Color.BLUE);
        isHighlighted = true;
    } //highlightSquare

    /**
     *Removes the highlight from a square.
     */
    public void removeHighlight() {
        blank.setFill(Color.TRANSPARENT);
        blank.setStroke(Color.BLACK);
        isHighlighted = false;
    } //removeHighlight

    /**
     *Returns whether or not a square is highlighted.
     *
     *@return true if the square is highlighted.
     */
    public boolean getIsHighlighted() {
        return isHighlighted;
    } //getIsHighlited
} //RevrsiSquare
