package boot;

/**
 * Created by colt on 3/9/16.
 */

import static helpers.Artist.TILE_SIZE;

public class TileGrid {

    public Tile[][] map; //Make 2D array of Tile(s) class for map.
    public int tilesWide;
    public int tilesHigh;

    //Constructor.
    public TileGrid() {
        this.tilesWide = 20;
        this.tilesHigh = 15;
        map = new Tile[tilesHigh][tilesWide];
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                //Arrays go row * col, but graphics goes col * row.
                map[r][c] = new Tile(c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.tileGrass);
            }
        }
    }

    //Constructor 2.
    public TileGrid(int[][] newMap) {
        this.tilesWide = newMap[0].length;
        this.tilesHigh = newMap.length;
        map = new Tile[tilesHigh][tilesWide];
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                switch (newMap[r][c]) {
                    case 0:
                        map[r][c] = new Tile(c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.tileGrass);
                        break;
                    case 1:
                        map[r][c] = new Tile(c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.tileDirt);
                        break;
                    case 2:
                        map[r][c] = new Tile(c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.tileWater);
                        break;
                }
            }
        }
    }

    //Draw method.
    public void draw() {
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[r].length; c++) {
                map[r][c].draw();
            }
        }
    }

    //Set tile method.
    public void setTile (int x, int y, TileType type) {
        //Remember, arrays go row * col, but graphics goes col * row.
        map[y][x] = new Tile(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, type);
    }

    //Get tile type method.
    public Tile getTile(int column, int row) {
        if ((column > -1) && (column < tilesWide) && (row > -1) && (row < tilesHigh))
            return map[row][column];
        else
            return new Tile(0, 0, 0, 0, TileType.NULL);
    }

    //Getters and setters.
    public int getTilesWide() {
        return tilesWide;
    }

    public void setTilesWide(int tilesWide) {
        this.tilesWide = tilesWide;
    }

    public int getTilesHigh() {
        return tilesHigh;
    }

    public void setTilesHigh(int tilesHigh) {
        this.tilesHigh = tilesHigh;
    }

}