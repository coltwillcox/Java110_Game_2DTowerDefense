package boot;

/**
 * Created by colt on 3/10/16.
 */

import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.checkCollision;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.drawQuadTexRot;
import static helpers.Clock.delta;

public abstract class Projectile implements Entity {

    private float x;
    private float y;
    private float xVelocity;
    private float yVelocity;
    private float speed;
    private float angle;
    private int width;
    private int height;
    private int damage;
    private boolean alive;
    private Enemy target;
    private Texture texture;

    // Constructor.
    public Projectile(ProjectileType type, Enemy target, float x, float y, int width, int height, float angle) {
        this.texture = type.texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.speed = type.speed;
        this.damage = type.damage;
        this.target = target;
        this.alive = true;
        this.xVelocity = 0;
        this.yVelocity = 0;
        calculateDirection();
    }

    public void update() {
        if (alive) {
            x += xVelocity * speed * delta();
            y += yVelocity * speed * delta();
            //Check if bullet hit the target.
            if (checkCollision(x, y, width, height, target.getX() + 8, target.getY() + 8, target.getWidth() - 16, target.getHeight() - 16))
                hitEffect();
            draw();
        }
    }

    public void draw() {
        drawQuadTexRot(texture, x, y, width, height, angle);
    }

    //What happens when bullet hits the target. Originaly called damage().
    public void hitEffect() {
        target.damage(damage);
        alive = false;
    }

    //Method to calculate direction in what bullet will fly.
    private void calculateDirection() {
        float totalAllowedMovement = 1.0f;
        float xDistanceFromTarget = Math.abs(target.getX() - x + TILE_SIZE / 2 - TILE_SIZE / 4);
        float yDistanceFromTarget = Math.abs(target.getY() - y + TILE_SIZE / 2 - TILE_SIZE / 4);
        float totalDistanceFromTarget = xDistanceFromTarget + yDistanceFromTarget;
        float xPercentOfMovement = xDistanceFromTarget / totalDistanceFromTarget;
        xVelocity = xPercentOfMovement;
        yVelocity = totalAllowedMovement - xPercentOfMovement;
        //Invert xVelocity if target is left from the projectile.
        if (target.getX() < x)
            xVelocity *= -1;
        //Invert yVelocity if target is up from the projectile.
        if (target.getY() < y)
            yVelocity *= -1;
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

}