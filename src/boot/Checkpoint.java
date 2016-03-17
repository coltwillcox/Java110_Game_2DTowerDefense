package boot;

/**
 * Created by colt on 3/9/16.
 */

public class Checkpoint {

    private Tile tile;
    private int xDirection;
    private int yDirection;

    //Constructor.
    public Checkpoint(Tile tile, int xDirection, int yDirection) {
        this.tile = tile;
        this.xDirection = xDirection;
        this.yDirection = yDirection;
    }

    //Getters and setters.
    public Tile getTile() {
        return tile;
    }

    public int getxDirection() {
        return xDirection;
    }

    public int getyDirection() {
        return yDirection;
    }

}