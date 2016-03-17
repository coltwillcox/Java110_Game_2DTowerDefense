package boot;

/**
 * Created by colt on 3/14/16.
 */

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.quickLoadTex;

public enum ProjectileType {

    projectileBullet(quickLoadTex("projectileBullet"), 10, 1000),
    projectileIce(quickLoadTex("projectileIce"), 6, 900);

    Texture texture;
    int damage;
    float speed;

    //Constructor
    ProjectileType(Texture texture, int damage, float speed) {
        this.texture = texture;
        this.damage = damage;
        this.speed = speed;
    }

}