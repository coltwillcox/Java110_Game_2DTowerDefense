package boot;

/**
 * Created by colt on 3/8/16.
 */

//Enumerator class.
public enum TileType {

    //type(textureName, buildable)
    tileGrass("tileGrass", true),
    tileDirt("tileDirt", false),
    tileWater("tileWater", false),
    NULL("tileWater", false);

    String textureName;
    boolean buildable;

    //Constructor.
    TileType(String textureName, boolean buildable) {
        this.textureName = textureName;
        this.buildable = buildable;
    }

}