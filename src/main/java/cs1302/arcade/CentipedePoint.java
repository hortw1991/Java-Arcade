package cs1302.arcade;

/**
 * Class representing a point on the centipede board.  Standard
 * x and y coordinate getters and setters.
 */
public class CentipedePoint {
    int x;
    int y;
    int prevX;
    int prevY;

    /**
     * Constructs a centipede point.
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public CentipedePoint(int x, int y) {

        this.x = x;
        this.y = y;

    }

    /**
     * Sets the x coordinate.
     * @param x the y coordinate.
     */
    public void setX(int x) {
        this.prevX = this.x;
        this.x = x;
    }

    /**
     * Sets the y coordinate.
     * @param y the y coordinate.
     */
    public void setY(int y) {
        this.prevY = this.y;
        this.y = y;
    }

    /**
     * Returns the x coordinate.
     * @return the x coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the y coordinate.
     * @return the y coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Returns the previous x coordinate.
     * @return the previous y coordinate.
     */
    public int getPrevX() {
        return this.prevX;
    }

    /**
     * Returns the previous y coordinate.
     * @return the previous y coordinate.
     */
    public int getPrevY() {
        return this.prevY;
    }

}
