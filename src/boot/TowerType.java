package boot;

/**
 * Created by colt on 3/13/16.
 */

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.quickLoadTex;

public enum TowerType {

    cannonRed(new Texture[]{quickLoadTex("towerCannonRedBase"), quickLoadTex("towerCannonRedGun")}, ProjectileType.projectileBullet,  1, 10000, 0.5f, 30),
    cannonIce(new Texture[]{quickLoadTex("towerCannonIceBase"), quickLoadTex("towerCannonIceGun")}, ProjectileType.projectileIce, 1, 10000, 3, 20);

    Texture[] textures;
    ProjectileType projectileType;
    int damage;
    int range;
    float firingSpeed;
    int cost;

    //Constructor
    TowerType(Texture[] textures, ProjectileType projectileType, int damage, int range, float firingSpeed, int cost) {
        this.textures = textures;
        this.projectileType = projectileType;
        this.damage = damage;
        this.range = range;
        this.firingSpeed = firingSpeed;
        this.cost = cost;
    }

}