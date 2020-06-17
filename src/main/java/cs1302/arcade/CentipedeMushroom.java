package cs1302.arcade;

import cs1302.arcade.CentipedePoint;

/**
 * Class representing a Mushroom.
 */
public class CentipedeMushroom extends CentipedePoint {

    int health;
    boolean isPoisonMushroom;

    /**
     * Constructs a CentipedeMushroom.
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public CentipedeMushroom(int x, int y) {
        super(x, y);
        this.health = 4;
        isPoisonMushroom = false;
    }

    /**
     * Decrements health by 1.
     */
    public void reduceHealth() {
        this.health--;
    }

    /**
     * Returns the amount of health.
     * @return the amount of health.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Sets the mushroom to poisonous.
     */
    public void setPoisonMushroom() {
        this.isPoisonMushroom = true;
    }

    /**
     * Returns if the mushroom is poisonous.
     * @return true if it is a poison mushroom.
     */
    public boolean getIsPoisonMushroom() {
        return this.isPoisonMushroom;
    }




}
