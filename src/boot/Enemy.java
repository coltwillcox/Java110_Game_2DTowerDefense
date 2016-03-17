package boot;

/**
 * Created by colt on 3/9/16.
 */

import org.newdawn.slick.opengl.Texture;
import java.util.ArrayList;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.drawQuadTexRot;
import static helpers.Artist.quickLoadTex;
import static helpers.Clock.delta;

public class Enemy implements Entity {

    public boolean first;
    public boolean alive;
    private float x;
    private float y;
    private float speed;
    private float health;
    private float startHealth;
    private int width;
    private int height;
    private int currentCheckpoint;
    private int[] directions;
    private Tile startTile;
    private TileGrid grid;
    private Texture texture;
    private Texture healthBackground;
    private Texture healthForeground;
    private Texture healthBorder;
    private ArrayList<Checkpoint> checkpoints;
    private ArrayList<Float> anglesList;

    //Constructor.
    public Enemy(Texture texture, Tile startTile, TileGrid grid, int width, int height, float speed, float health) {
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.health = health;
        this.startHealth = health;
        this.grid = grid;
        this.first = true;
        this.alive = true;
        this.startTile = startTile;
        this.texture = texture;
        this.checkpoints = new ArrayList<>();
        this.anglesList = new ArrayList<>();
        this.currentCheckpoint = 0;
        this.directions = new int[2];
        directions = findNextDirection(startTile);
        this.directions[0] = 0; //X direction.
        this.directions[1] = 0; //Y direction.
        this.healthBackground = quickLoadTex("healthBackground");
        this.healthForeground = quickLoadTex("healthForeground");
        this.healthBorder = quickLoadTex("healthBorder");
        populateCheckPointList();
    }

    //Update method. Change location x with (delta) time and speed.
    public void update() {
        //Check if it's the first time this class is updated. If so, do nothing.
        if (first)
            first = false;
        else {
            if (checkpointReached()) {
                //Check if there are more checkpoints before moving on.
                if (currentCheckpoint + 1 == checkpoints.size()) {
                    endOfMazeReached();
                } else {
                    currentCheckpoint++;
                }
            } else {
                //If not at checkpoint, continue at current direction.
                x += delta() * checkpoints.get(currentCheckpoint).getxDirection() * speed;
                y += delta() * checkpoints.get(currentCheckpoint).getyDirection() * speed;
            }
        }
    }

    //Draw method.
    public void draw() {
        float healthPercentage = health / startHealth;
        //Enemy texture.
        drawQuadTexRot(texture, x, y, width, height, anglesList.get(currentCheckpoint));
        //Health bar textures.
        drawQuadTex(healthBackground, x + 16, y - 12, 32, 8);
        drawQuadTex(healthForeground, x + 16, y - 12, 32 * healthPercentage, 8);
        drawQuadTex(healthBorder, x + 16, y - 12, 32, 8);
    }

    //Find next checkpoint method.
    private Checkpoint findNextCheckpoint(Tile start, int[] dir) {
        Tile next = null;
        Checkpoint c = null;
        boolean found = false; //Boolean to decide if next checkpoint is found.
        int counter = 1; //Integer to increment each loop (get next tile).
        while (!found) {
            if (start.getColumn() + dir[0] * counter == grid.getTilesWide() || start.getRow() + dir[1] * counter == grid.getTilesHigh() || start.getType() != grid.getTile(start.getColumn() + dir[0] * counter, start.getRow() + dir[1] * counter).getType()) {
                found = true;
                //If different tile found, returns one tile behind.
                counter--;
                next = grid.getTile(start.getColumn() + dir[0] * counter, start.getRow() + dir[1] * counter);
                //Find direction where to rotate enemy, and populate anglesList.
                if (next.getX() > start.getX())
                    anglesList.add(90f);
                if (next.getX() < start.getX())
                    anglesList.add(270f);
                if (next.getY() > start.getY())
                    anglesList.add(180f);
                if (next.getY() < start.getY())
                    anglesList.add(0f);
            }
            counter++;
        }
        c = new Checkpoint(next, dir[0], dir[1]);
        return c;
    }

    //Find direction method.
    private int[] findNextDirection(Tile start) {
        int[] dir = new int[2];
        Tile up = grid.getTile(start.getColumn(), start.getRow() - 1);
        Tile down = grid.getTile(start.getColumn(), start.getRow() + 1);
        Tile left = grid.getTile(start.getColumn() - 1, start.getRow());
        Tile right = grid.getTile(start.getColumn() + 1, start.getRow());
        //Check if current inhabited TileType matches TileType above, down, left or right.
        if (start.getType() == up.getType() && directions[1] != 1) {
            dir[0] = 0;
            dir[1] = -1;
        } else if (start.getType() == down.getType() && directions[1] != -1) {
            dir[0] = 0;
            dir[1] = 1;
        } else if (start.getType() == left.getType() && directions[0] != 1) {
            dir[0] = -1;
            dir[1] = 0;
        } else if (start.getType() == right.getType() && directions[0] != -1) {
            dir[0] = 1;
            dir[1] = 0;
        } else {
            dir[0] = 2;
            dir[1] = 2;
        }
        return dir;
    }

    private void populateCheckPointList() {
        //Add first checkpoint manually based on startTile.
        checkpoints.add(findNextCheckpoint(startTile, directions = findNextDirection(startTile)));
        int counter = 0;
        boolean cont = true;
        while (cont) {
            int[] currentDirection = findNextDirection(checkpoints.get(counter).getTile());
            //Check if next checkpoint exists. End after 20 checkpoints (arbitrary).
            if (currentDirection[0] == 2 || counter == 20) {
                cont = false;
            } else {
                checkpoints.add(findNextCheckpoint(checkpoints.get(counter).getTile(), directions = findNextDirection(checkpoints.get(counter).getTile())));
            }
            counter++;
        }
    }

    private boolean checkpointReached() {
        boolean reached = false;
        Tile t = checkpoints.get(currentCheckpoint).getTile();
        //Check if position reached tile within variance of 3 pixels (arbitrary border).
        if (x > t.getX() - 3 && x < t.getX() + 3 && y > t.getY() - 3 && y < t.getY() + 3) {
            reached = true;
            x = t.getX();
            y = t.getY();
        }
        return reached;
    }

    //Run when last checkpoint is reached by enemy.
    private void endOfMazeReached() {
        die();
        Player.setLives(-1);
    }

    //Take damage from external source (projectile).
    public void damage(int amount) {
        health -= amount;
        if (health <= 0) {
            die();
            Player.setCash(5); //Set static variable. Calling class, not object.
        }
    }

    //DIE!!!
    private void die() {
        alive = false;
    }

    //Getters and setters.
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

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

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

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Tile getStartTile() {
        return startTile;
    }

    public void setStartTile(Tile startTile) {
        this.startTile = startTile;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public TileGrid getGrid() {
        return grid;
    }

    public boolean isAlive() {
        return alive;
    }

}