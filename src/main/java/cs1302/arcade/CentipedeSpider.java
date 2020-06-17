package cs1302.arcade;

import cs1302.arcade.CentipedePoint;

/**
 * Represents a spider.
 */
public class CentipedeSpider extends CentipedePoint {

    boolean south;
    boolean west;

    /**
     * Constructs a centipede spider.
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param west indicates if the spider is going west.
     * @param south indicates if the spider is going south.
     */
    public CentipedeSpider(int x, int y, boolean west, boolean south) {
        super(x, y);
        this.west = west;
        this.south = south;
    }

    /**
     * Returns if the spider is going west.
     * @return true if the spider is going west.
     */
    public boolean getWest() {
        return this.west;
    }

    /**
     * Sets the spider's west boolean indicator.
     * @param west true if the spider should be going west.
     */
    public void setWest(boolean west) {
        this.west = west;
    }
    
    /**
     * Returns if the spider is going south.
     * @return true if the spider is going south.
     */
    public boolean getSouth() {
        return this.south;
    }
    
    /**
     * Sets the spider's south boolean indicator.
     * @param south true if the spider should be going south.
     */
    public void setSouth(boolean south) {
        this.south = south;
    }


}
