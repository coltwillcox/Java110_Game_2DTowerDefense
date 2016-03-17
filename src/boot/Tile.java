package boot;

/**
 * Created by colt on 3/8/16.
 */

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.quickLoadTex;

public class Tile {

    private float x;
    private float y;
    private int width;
    private int height;
    private Texture texture;
    private TileType type;

    //Constructor.
    public Tile(float x, float y, int width, int height, TileType type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.texture = quickLoadTex(type.textureName); //Load texture (in enumerator TileType).
    }

    public void draw() {
        drawQuadTex(texture, x, y, width, height);
    }

    //Getters and setters.
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public int getColumn() {
        return (int) x / TILE_SIZE;
    }

    public int getRow() {
        return (int) y / TILE_SIZE;
    }

}