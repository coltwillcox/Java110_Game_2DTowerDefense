package boot;

/**
 * Created by colt on 3/11/16.
 */

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import static helpers.Leveler.loadMap;
import static helpers.Leveler.saveMap;

public class Editor {

    private TileGrid grid;
    private TileType[] types;
    private int index;

    //Constructor.
    public Editor() {
        grid = new TileGrid();
        this.types = new TileType[3];
        this.types[0] = TileType.tileGrass;
        this.types[1] = TileType.tileDirt;
        this.types[2] = TileType.tileWater;
        this.index = 0;
    }

    public void update() {
        grid.draw();
        //Mouse input.
        if (Mouse.isButtonDown(0)) {
            setTile();
        }
        //Keyboard input.
        while (Keyboard.next()) {
            switch (Keyboard.getEventKey()) {
                case Keyboard.KEY_G:
                    this.index = 0;
                    break;
                case Keyboard.KEY_D:
                    this.index = 1;
                    break;
                case Keyboard.KEY_W:
                    this.index = 2;
                    break;
                case Keyboard.KEY_S:
                    saveMap("mapTest", grid);
                    break;
                case Keyboard.KEY_L:
                    this.grid = loadMap("mapTest");
                    break;
            }
        }
    }

    private void setTile() {
        grid.setTile(Mouse.getX() / TILE_SIZE, (HEIGHT - Mouse.getY() - 1) / TILE_SIZE, types[index]);
    }

}