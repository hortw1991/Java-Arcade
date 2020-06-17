package cs1302.arcade;

import cs1302.arcade.CentipedePoint;

/**
 * Class representing a scorpion.
 */
public class CentipedeScorpion extends CentipedePoint {

    boolean west;

    /**
     * Constructs a centipede scorpion.
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param west indicates if the scorpion is going west or not.
     */
    public CentipedeScorpion(int x, int y, boolean west) {
        super(x, y);
        this.west = west;
    }

    /**
     * Returns if the scorpion is going west.
     * @return true if the scorpion is going west.
     */
    public boolean getWest() {
        return this.west;
    }

    /**
     * Sets the indicator for if the scorpion is going west.
     * @param west true if the scorpion should be going west.
     */
    public void setWest(boolean west) {
        this.west = west;
    }




}
