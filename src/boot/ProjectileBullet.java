package boot;

/**
 * Created by colt on 3/14/16.
 */

public class ProjectileBullet extends Projectile {

    //Constructor.
    public ProjectileBullet(Enemy target, float x, float y, int width, int height, float angle) {
        super(ProjectileType.projectileBullet, target, x, y, width, height, angle);
    }

    @Override
    public void hitEffect() {
        super.hitEffect();
    }

}