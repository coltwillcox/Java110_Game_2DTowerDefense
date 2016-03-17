package helpers;

/**
 * Created by colt on 3/11/16.
 */

import boot.Tile;
import boot.TileGrid;
import boot.TileType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Leveler {

    //Save map.
    public static void saveMap(String mapName, TileGrid grid) {
        String mapData = "";
        //Nested for loop.
        for (int r = 0; r < grid.tilesHigh; r++)
            for (int c = 0; c < grid.tilesWide; c++)
                mapData += getTileID(grid.getTile(c, r)); //Append to mapData (create a String map file).
        //Create file and write to it.
        try {
            File file = new File(mapName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(mapData); //Write String to file.
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Get tile ID with tile type.
    public static String getTileID(Tile t) {
        String ID = "E"; //Some kind of error character. Will never show in map file, anyway.
        switch (t.getType()) {
            case tileGrass:
                ID = "0";
                break;
            case tileDirt:
                ID = "1";
                break;
            case tileWater:
                ID = "2";
                break;
            case NULL:
                ID = "3";
                break;
        }
        return ID;
    }

    //Load map. Read from text file and make it a TileGrid object.
    public static TileGrid loadMap(String mapName) {
        TileGrid grid = new TileGrid();
        try {
            BufferedReader br = new BufferedReader(new FileReader(mapName));
            String data = br.readLine();
            //Nested loop. Substring one by one character from data, convert them to TileType, and put them into grid.
            for (int r = 0; r < grid.getTilesHigh(); r++) {
                for (int c = 0; c < grid.getTilesWide(); c++) {
                    grid.setTile(c, r, getTileType(data.substring(r * grid.getTilesWide() + c, r * grid.getTilesWide() + c + 1)));
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grid;
    }

    //Get tile type with tile ID (inverted from getTileID method).
    public static TileType getTileType(String ID) {
        TileType type = TileType.NULL;
        switch (ID) {
            case "0":
                type = TileType.tileGrass;
                break;
            case "1":
                type = TileType.tileDirt;
                break;
            case "2":
                type = TileType.tileWater;
                break;
            case "3":
                type = TileType.NULL;
                break;
        }
        return type;
    }

}