package cs1302.arcade;

import cs1302.arcade.CentipedePoint;

/**
 * Represents a centipede piece.
 */
public class CentipedePiece extends CentipedePoint {

    boolean south;
    boolean west;
    boolean dead;

    int prevX;
    int prevY;

    /**
     * Constructs a centipede piece.
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param west if the centipede is going west.
     * @param south if the centipede is going south.
     */
    public CentipedePiece(int x, int y, boolean west, boolean south) {
        super(x, y);
        this.west = west;
        this.south = south;

        this.dead = false;
    }

    /**
     * Sets the x coordinate.
     * @param x the x coordinate.
     */
    @Override
    public void setX(int x) {
        this.prevX = this.x;
        this.x = x;
    }

    /**
     * Sets the y coordinate.
     * @param y the y coordinate.
     */
    @Override
    public void setY(int y) {
        this.prevY = this.y;
        this.y = y;
    }

    /**
     * Returns if the centipede is going west.
     * @return true if the centipede is going west.
     */
    public boolean getWest() {
        return this.west;
    }

    /**
     * Sets the centipede west direction boolean.
     * @param west true if the centipede is going west.
     */
    public void setWest(boolean west) {
        this.west = west;
    }

    /**
     * Returns if the centipede is going south.
     * @return true if the centipede is going south.
     */
    public boolean getSouth() {
        return this.south;
    }
    
    /**
     * Sets the centipede south direction boolean.
     * @param south true if the centipede is going south.
     */
    public void setSouth(boolean south) {
        this.south = south;
    }

    /**
     * Sets the centipede dead indicator boolean.
     * @param dead true if the centipede is dead.
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Returns if the centipede is dead.
     * @return true if the centipede is dead.
     */
    public boolean getDead() {
        return this.dead;
    }
    
    /**
     * Returns the previous x coordinate.
     * @return the previous x coordinate.
     */
    public int getPrevX() {
        return this.prevX;
    }

    /**
     * Sets the previous x coordinate.
     * @param prevX the previous x coordinate.
     */
    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    /**
     * Returns the previous y coordinate.
     * @return the previous y coordinate.
     */
    public int getPrevY() {
        return this.prevY;
    }

    /**
     * Sets the previous y coordinate.
     * @param prevY the previous y coordinate.
     */
    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }
}
