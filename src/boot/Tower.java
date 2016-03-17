package boot;

/**
 * Created by colt on 3/12/16.
 */

import org.newdawn.slick.opengl.Texture;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.drawQuadTexRot;
import static helpers.Clock.delta;

public abstract class Tower implements Entity {

    private float x;
    private float y;
    private float timeSinceLastShot;
    private float firingSpeed;
    private float angle;
    private int width;
    private int height;
    private int range;
    private int cost;
    private boolean targeting;
    private Texture[] textures;
    private CopyOnWriteArrayList<Enemy> enemies;
    public Enemy target;
    public ArrayList<Projectile> projectiles;
    public TowerType type;

    //Constructor.
    public Tower(TowerType type, Tile startTile, CopyOnWriteArrayList<Enemy> enemies) {
        this.type = type;
        this.x = startTile.getX();
        this.y = startTile.getY();
        this.timeSinceLastShot = 0f;
        this.firingSpeed = type.firingSpeed;
        this.angle = 0f;
        this.width = startTile.getWidth();
        this.height = startTile.getHeight();
        this.range = type.range;
        this.cost = type.cost;
        this.targeting = false;
        this.textures = type.textures; //Get textures array from TowerType.
        this.enemies = enemies;
        this.projectiles = new ArrayList<>();
    }

    @Override
    public void update() {
        if (!targeting) target = acquireTarget();
        else {
            angle = calculateAngle();
            if (timeSinceLastShot > firingSpeed) {
                shoot(target, angle);
                timeSinceLastShot = 0;
            }
        }
        if (target == null || !target.isAlive()) targeting = false;
        timeSinceLastShot += delta();
        for (Projectile p : projectiles) p.update();
        draw();
    }

    @Override
    public void draw() {
        //Draw base first, then rotating elements (guns).
        drawQuadTex(textures[0], x, y, width, height);
        if (textures.length > 1)
            for (int i = 1; i < textures.length; i++)
                drawQuadTexRot(textures[i], x, y, width, height, angle);
    }

    //Search for enemy.
    private Enemy acquireTarget() {
        Enemy closest = null;
        float closestDistance = 10000; //Arbitrary distance (larger than map), to help with sorting Enemy distances.
        //Go through each Enemy in enemies and return nearest one.
        for (Enemy e : enemies) {
            if (isInRange(e) && findDistance(e) < closestDistance && e.isAlive()) {
                closestDistance = findDistance(e);
                closest = e;
            }
        }
        if (closest != null) targeting = true; //If an enemy exists, and is returned, targeting!
        return closest;
    }

    //Check if enemy is in range of tower.
    private boolean isInRange(Enemy e) {
        float xDistance = Math.abs(e.getX() - x);
        float yDistance = Math.abs(e.getY() - y);
        if (xDistance < range && yDistance < range) return true;
        return false;
    }

    //Find distance.
    private float findDistance(Enemy e) {
        float xDistance = Math.abs(e.getX() - x);
        float yDistance = Math.abs(e.getY() - y);
        return xDistance + yDistance;
    }

    //Calculate angle.
    private float calculateAngle() {
        return (float) Math.toDegrees(Math.atan2(target.getY() - y, target.getX() - x)) - 90;
    }

    //Shoot method. Abstract. No body!
    public abstract void shoot(Enemy target, float angle);

    public void updateEnemyList(CopyOnWriteArrayList<Enemy> newList) {
        enemies = newList;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    public Enemy getTarget() {
        return target;
    }

    public int getCost() {
        return cost;
    }

}