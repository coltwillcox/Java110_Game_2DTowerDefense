package boot;

/**
 * Created by colt on 3/9/16.
 */

import java.util.concurrent.CopyOnWriteArrayList;
import static helpers.Artist.TILE_SIZE;
import static helpers.Clock.delta;

public class Wave {

    private float timeSinceLastSpawn;
    private float spawnTime;
    private Enemy enemyType;
    private CopyOnWriteArrayList<Enemy> enemyList;
    private int enemiesPerWave;
    private int enemiesSpawned;
    private boolean allEnemiesDead;
    private boolean waveCompleted;

    //Constructor.
    public Wave(Enemy enemyType, float spawnTime, int enemiesPerWave) {
        this.enemyType = enemyType;
        this.spawnTime = spawnTime;
        this.enemiesPerWave = enemiesPerWave;
        this.enemiesSpawned = 0;
        this.timeSinceLastSpawn = 0;
        this.waveCompleted = false;
        enemyList = new CopyOnWriteArrayList<>();
        spawn(); //Spawn first enemy, as soon as wave starts.
    }

    //Update method. Adds new enemy to wave in given time, and draw them all.
    public void update() {
        allEnemiesDead = true; //Assume all enemies are dead, until for loop proves otherwise.
        //Create new enemies, while keeping their enemiesPerWave number.
        if (enemiesSpawned < enemiesPerWave) {
            timeSinceLastSpawn += delta();
            if (timeSinceLastSpawn > spawnTime) {
                spawn();
                timeSinceLastSpawn = 0;
            }
        }
        //Draw every enemy in wave (from ArrayList).
        for (Enemy e : enemyList) {
            //First check if enemy is alive. If it is not, do not draw it.
            if (e.isAlive()) {
                allEnemiesDead = false;
                e.update();
                e.draw();
            } else enemyList.remove(e);
        }
        //Complete wave if all enemies are dead.
        if (allEnemiesDead) waveCompleted = true;
    }

    //Spawn method.
    private void spawn() {
        enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyType.getGrid(), TILE_SIZE, TILE_SIZE, enemyType.getSpeed(), enemyType.getHealth()));
        enemiesSpawned++;
    }

    public boolean isWaveCompleted() {
        return waveCompleted;
    }

    public CopyOnWriteArrayList<Enemy> getEnemyList() {
        return enemyList;
    }

}