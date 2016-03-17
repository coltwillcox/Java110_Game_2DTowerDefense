package boot;

/**
 * Created by colt on 3/10/16.
 */

public class WaveManager {

    private float timeSinceLastWave;
    private float timeBetweenEnemies;
    private int waveNumber;
    private int enemiesPerWave;
    private Enemy enemyType;
    private Wave currentWave;

    public WaveManager(Enemy enemyType, float timeBetweenEnemies, int enemiesPerWave) {
        this.enemyType = enemyType;
        this.timeBetweenEnemies = timeBetweenEnemies;
        this.enemiesPerWave = enemiesPerWave;
        this.timeSinceLastWave = 0;
        this.waveNumber = 0;
        this.currentWave = null;
        newWave();
    }

    public void update() {
        if (!currentWave.isWaveCompleted()) currentWave.update();
        else newWave();
    }

    private void newWave() {
        currentWave = new Wave(enemyType, timeBetweenEnemies, enemiesPerWave);
        waveNumber++;
        System.out.println("Wave " + waveNumber + " starts.");
    }

    public Wave getCurrentWave() {
        return currentWave;
    }

}