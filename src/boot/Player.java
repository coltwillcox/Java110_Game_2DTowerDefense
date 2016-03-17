package boot;

/**
 * Created by colt on 3/9/16.
 */

import helpers.Clock;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import java.util.ArrayList;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;

public class Player {

    private TileGrid grid;
    private TileType[] types;
    private WaveManager waveManager;
    private ArrayList<Tower> towerList;
    private boolean leftMouseButtonDown;
    private boolean rightMouseButtonDown;
    private boolean holdingTower;
    public static int cash;
    public static int lives;
    public Tower tempTower;

    //Constructor.
    public Player(TileGrid grid, WaveManager waveManager) {
        this.grid = grid;
        this.types = new TileType[3];
        this.types[0] = TileType.tileGrass;
        this.types[1] = TileType.tileDirt;
        this.types[2] = TileType.tileWater;
        this.waveManager = waveManager;
        this.towerList = new ArrayList<>();
        this.leftMouseButtonDown = false;
        this.rightMouseButtonDown = false;
        this.holdingTower = false;
        this.tempTower = null;
        cash = 0;
        lives = 0;
    }

    public void update() {
        //Update holding tower.
        if (holdingTower) {
            tempTower.setX(getMouseTile().getX());
            tempTower.setY(getMouseTile().getY());
            tempTower.draw();
        }
        //Mouse input.
        if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
            placeTower();
        }
        if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {
            System.out.println("Right click");
        }
        leftMouseButtonDown = Mouse.isButtonDown(0);
        rightMouseButtonDown = Mouse.isButtonDown(1);
        //Keyboard input.
        while (Keyboard.next()) {
            switch (Keyboard.getEventKey()) {
                case Keyboard.KEY_RIGHT:
                    Clock.setMultiplier(0.2f);
                    break;
                case Keyboard.KEY_LEFT:
                    Clock.setMultiplier(-0.2f);
                    break;
            }
        }
        //Update all towers in the game.
        for (Tower t : towerList) {
            t.update();
            t.draw();
            t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
        }
    }

    //Player start cash and lives.
    public void setup() {
        cash = 500;
        lives = 10;
    }

    public void pickTower (Tower t) {
        tempTower = t;
        holdingTower = true;
    }

    private void placeTower () {
        if (holdingTower && setCash(-tempTower.getCost()))
            towerList.add(tempTower);
        else if (holdingTower && !setCash(-tempTower.getCost()))
            System.out.println("No money.");
        holdingTower = false;
        tempTower = null;
    }

    private Tile getMouseTile() {
        return grid.getTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE);
    }

    public static boolean setCash(int amount) {
        if (cash + amount >= 0) {
            cash += amount;
            System.out.println(cash);
            return true;
        } else
            return false;
    }

    public static void setLives(int amount) {
        lives += amount;
    }

}