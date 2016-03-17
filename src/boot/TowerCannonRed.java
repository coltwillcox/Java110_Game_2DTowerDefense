package boot;

/**
 * Created by colt on 3/13/16.
 */

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class TowerCannonRed extends Tower {

    private Audio projectileBullet;

    //Constructor.
    public TowerCannonRed(Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        super(TowerType.cannonRed, startTile, enemies);
        try {
            projectileBullet = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/projectileBullet.wav"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shoot(Enemy target, float angle) {
        super.projectiles.add(new ProjectileBullet(super.target, super.getX(), super.getY(), 32, 32, angle));
        if (!projectileBullet.isPlaying())
            projectileBullet.playAsSoundEffect(1.0f, 0.1f, false);
    }

}