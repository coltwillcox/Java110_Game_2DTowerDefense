package boot;

/**
 * Created by colt on 3/13/16.
 */

public class ProjectileIce extends Projectile {

    //Constructor.
    public ProjectileIce(Enemy target, float x, float y, int width, int height, float angle) {
        super(ProjectileType.projectileIce, target, x, y, width, height, angle);
    }

    @Override
    public void hitEffect() {
        super.getTarget().setSpeed(4f);
        super.hitEffect();
    }

}