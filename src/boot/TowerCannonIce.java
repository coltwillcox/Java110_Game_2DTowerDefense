package boot;

/**
 * Created by colt on 3/13/16.
 */

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCannonIce extends Tower {

    private Audio projectileIce;

    //Constructor.
    public TowerCannonIce(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(TowerType.cannonIce, startTile, enemies);
        try {
            projectileIce = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/projectileIce.wav"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shoot(Enemy target, float angle) {
        super.projectiles.add(new ProjectileIce(super.target, super.getX(), super.getY(), 32, 32, angle));
        if (!projectileIce.isPlaying())
            projectileIce.playAsSoundEffect(1.0f, 0.1f, false);
    }

}