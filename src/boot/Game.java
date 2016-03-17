package boot;

/**
 * Created by colt on 3/10/16.
 */

import UI.UI;
import UI.Button;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.quickLoadTex;

public class Game {

    private Texture menuBackground;
    private TileGrid grid;
    private Player player;
    private WaveManager waveManager;
    private UI towerPickerUI;

    public Game(int[][] map) {
        SoundStore.get().setMaxSources(6); //Limit max played channels to 6.
        menuBackground = quickLoadTex("menuBackground");
        grid = new TileGrid(map);
        waveManager = new WaveManager(new Enemy(quickLoadTex("enemyPlane"), grid.getTile(1, 14), grid, TILE_SIZE, TILE_SIZE, 100, 100), 1, 20);
        player = new Player(grid, waveManager);
        player.setup();
        setupUI();
    }

    public void update() {
        drawQuadTex(menuBackground, 1280, 0, 256, 1024); //MEMORY PROBLEM!
        grid.draw();
        waveManager.update();
        player.update();
        updateUI();
    }

    private void setupUI() {
        towerPickerUI = new UI();
        towerPickerUI.createMenu("TowerPicker", 1280, 0, 192, 960, 2, 0);
        towerPickerUI.getMenu("TowerPicker").addButton(new Button("TowerCannonIce", quickLoadTex("towerCannonIceBase"), 0, 0));
        towerPickerUI.getMenu("TowerPicker").addButton(new Button("TowerCannonRed", quickLoadTex("towerCannonRedBase"), 0, 0));
    }

    private void updateUI() {
        towerPickerUI.draw();
        if (Mouse.next()) {
            boolean mouseClicked = Mouse.isButtonDown(0);
            if (mouseClicked) {
                //Pick and draw tower where cursor goes. It will shoot only when placed (added to towerList).
                if (towerPickerUI.getMenu("TowerPicker").isButtonClicked("TowerCannonIce"))
                    player.pickTower(new TowerCannonIce(grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
                if (towerPickerUI.getMenu("TowerPicker").isButtonClicked("TowerCannonRed"))
                    player.pickTower(new TowerCannonRed(grid.getTile(0, 0), waveManager.getCurrentWave().getEnemyList()));
            }
        }
    }

}