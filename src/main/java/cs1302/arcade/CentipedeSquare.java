package cs1302.arcade;

import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

import javafx.geometry.Pos;

/**
 * Represents a single square that holds any centipede game objects.
 */
public class CentipedeSquare extends HBox {
    public boolean isBlank;
    public boolean isCentipede;
    public boolean isDeadCentipede;
    public boolean isFlea;
    public boolean isSpider;
    public boolean isScorpion;
    public boolean isBullet;
    public boolean isShip;
    public boolean isMushroom;
    public boolean isPoisonMushroom;

    Rectangle blank = new Rectangle(35, 35, Color.TRANSPARENT);
    Rectangle centipede = new Rectangle(35, 35, Color.GREEN);
    Rectangle deadCentipede = new Rectangle(35, 35, Color.TRANSPARENT);
    Rectangle flea = new Rectangle(35, 35, Color.YELLOW);
    Rectangle spider = new Rectangle(35, 35, Color.TURQUOISE);
    Rectangle scorpion = new Rectangle(35, 35, Color.BROWN);
    Rectangle ship = new Rectangle(35, 35, Color.RED);
    Circle mushroom = new Circle(15, Color.GREEN);
    Circle damagedMushroom = new Circle(15, Color.GREY);
    Circle poisonMushroom = new Circle(15, Color.PURPLE);
    Circle bullet = new Circle(10, Color.BLUE);

    /**
     * Constructs a centipede square with default properties.
     */
    public CentipedeSquare() {
        isBlank = true;
        isCentipede = false;
        isDeadCentipede = false;
        isFlea = false;
        isSpider = false;
        isScorpion = false;
        isBullet = false;
        isShip = false;
        isMushroom = false;
        isPoisonMushroom = false;

        this.setMinWidth(35);
        this.setMinHeight(35);
        this.getChildren().add(blank);
    }

    /**
     * Sets the square to blank.
     */
    public void setBlank() {
        isBlank = true;
        isCentipede = false;
        isDeadCentipede = false;
        isFlea = false;
        isSpider = false;
        isScorpion = false;
        isBullet = false;
        isShip = false;
        isMushroom = false;
        isPoisonMushroom = false;
        this.getChildren().clear();
        this.getChildren().add(blank);
    }

    /**
     * Sets the square to a ship.
     */
    public void setShip() {
        isBlank = false;
        isCentipede = false;
        isDeadCentipede = false;
        isFlea = false;
        isSpider = false;
        isScorpion = false;
        isBullet = false;
        isShip = true;
        isMushroom = false;
        isPoisonMushroom = false;
        this.getChildren().clear();
        this.getChildren().add(ship);
    }

    /**
     * Sets the square to a centipede.
     */
    public void setCentipede() {
        isBlank = false;
        isCentipede = true;
        isDeadCentipede = false;
        isFlea = false;
        isSpider = false;
        isScorpion = false;
        isBullet = false;
        isShip = false;
        isMushroom = false;
        isPoisonMushroom = false;
        this.getChildren().clear();
        this.getChildren().add(centipede);
    }

    /**
     * Sets the square to a flea.
     */
    public void setFlea() {
        isBlank = false;
        isCentipede = false;
        isDeadCentipede = false;
        isFlea = true;
        isSpider = false;
        isScorpion = false;
        isBullet = false;
        isShip = true;
        isMushroom = false;
        isPoisonMushroom = false;
        this.getChildren().clear();
        this.getChildren().add(flea);
    }

    /**
     * Sets the square to a spider.
     */
    public void setSpider() {
        isBlank = false;
        isCentipede = false;
        isDeadCentipede = false;
        isFlea = false;
        isSpider = true;
        isScorpion = false;
        isBullet = false;
        isShip = false;
        isMushroom = false;
        isPoisonMushroom = false;
        this.getChildren().clear();
        this.getChildren().add(spider);
    }

    /**
     * Sets the square to a scorpion.
     */
    public void setScorpion() {
        isBlank = false;
        isCentipede = false;
        isDeadCentipede = false;
        isFlea = false;
        isSpider = false;
        isScorpion = true;
        isBullet = false;
        isShip = false;
        isMushroom = false;
        isPoisonMushroom = false;
        this.getChildren().clear();
        this.getChildren().add(scorpion);
    }

    /**
     * Sets the square to a dead centipede (hidden blank).
     */
    public void setDeadCentipede() {
        isBlank = false;
        isCentipede = false;
        isDeadCentipede = true;
        isFlea = false;
        isSpider = false;
        isScorpion = false;
        isBullet = false;
        isShip = false;
        isMushroom = false;
        isPoisonMushroom = false;
        this.getChildren().clear();
        this.getChildren().add(deadCentipede);
    }

    /**
     * Sets the square to a bullet.
     */
    public void setBullet() {
        isBlank = false;
        isCentipede = false;
        isDeadCentipede = false;
        isFlea = false;
        isSpider = false;
        isScorpion = false;
        isBullet = true;
        isShip = false;
        isMushroom = false;
        isPoisonMushroom = false;
        this.getChildren().clear();
        this.getChildren().add(bullet);
    }

    /**
     * Sets the square to a mushroom.
     */
    public void setMushroom() {
        isBlank = false;
        isCentipede = false;
        isDeadCentipede = false;
        isFlea = false;
        isSpider = false;
        isScorpion = false;
        isBullet = false;
        isShip = false;
        isMushroom = true;
        isPoisonMushroom = false;
        this.getChildren().clear();
        this.getChildren().add(mushroom);
    }

    /**
     * Sets the square to damaged mushroom.
     */
    public void setDamagedMushroom() {
        isBlank = false;
        isCentipede = false;
        isDeadCentipede = false;
        isFlea = false;
        isSpider = false;
        isScorpion = false;
        isBullet = false;
        isShip = false;
        isMushroom = true;

        this.getChildren().clear();
        this.getChildren().add(damagedMushroom);
    }

    /**
     * Sets the square to a poison mushroom.
     */
    public void setPoisonMushroom() {
        isBlank = false;
        isCentipede = false;
        isDeadCentipede = false;
        isFlea = false;
        isSpider = false;
        isScorpion = false;
        isBullet = false;
        isShip = false;

        // Keep both mushroom and poisoned mushroom as true
        isMushroom = true;
        isPoisonMushroom = true;
        this.getChildren().clear();
        this.getChildren().add(poisonMushroom);
    }

    /**
     * Returns the square status.
     * @return true if the square is a blank. 
     */
    public boolean getIsBlank() {
        return isBlank;
    }
    
    /**
     * Returns the square status.
     * @return true if the square is a centipede.
     */
    public boolean getIsCentipede() {
        return isCentipede;
    }

    /**
     * Returns the square status.
     * @return true if the square is a dead centipede.
     */
    public boolean getIsDeadCentipede() {
        return isDeadCentipede;
    }

    /**
     * Returns the square status.
     * @return true if the square is a flea. 
     */
    public boolean getIsFlea() {
        return isFlea;
    }

    /**
     * Returns the square status.
     * @return true if the square is a spider.
     */
    public boolean getIsSpider() {
        return isSpider;
    }
    
    /**
     * Returns the square status.
     * @return true if the square is a scorpion. 
     */
    public boolean getIsScorpion() {
        return isScorpion;
    }

    /**
     * Returns the square status.
     * @return true if the square is a bullet.
     */
    public boolean getIsBullet() {
        return isBullet;
    }

    /**
     * Returns the square status.
     * @return true if the square is a ship.
     */
    public boolean getIsShip() {
        return isShip;
    }

    /**
     * Returns the square status.
     * @return true if the square is a mushroom.
     */
    public boolean getIsMushroom() {
        return isMushroom;
    }

    /**
     * Returns the square status.
     * @return true if the square is a poison mushroom.
     */
    public boolean getIsPoisonMushroom() {
        return isPoisonMushroom;
    }
}
