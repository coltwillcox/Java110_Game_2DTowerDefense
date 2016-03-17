package boot;

/**
 * Created by colt on 3/11/16.
 */

import UI.UI;
import helpers.StateManager;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import static helpers.Artist.*;

public class MainMenu {

    private Texture background;
    private UI menuUI;

    //Constructor;
    public MainMenu() {
        background = quickLoadTex("mainMenuBackground");
        menuUI = new UI();
        menuUI.addButton("Play", "buttonPlay", WIDTH / 2 - 128, (int) (HEIGHT * 0.65f)); //Y location is 65% down the screen.
        menuUI.addButton("Editor", "buttonEditor", WIDTH / 2 - 128, (int) (HEIGHT * 0.75f));
        menuUI.addButton("Quit", "buttonQuit", WIDTH / 2 - 128, (int) (HEIGHT * 0.85f));
    }

    //Update method.
    public void update() {
        drawQuadTex(background, 0, 0, 2048, 1024);
        menuUI.draw();
        updateButtons();
    }

    //Check if a button is clicked by the user. If so, do an action.
    private void updateButtons() {
        if (Mouse.isButtonDown(0)) {
            if (menuUI.isButtonClicked("Play"))
                StateManager.setGameState(StateManager.GameState.GAME);
            if (menuUI.isButtonClicked("Editor"))
                StateManager.setGameState(StateManager.GameState.EDITOR);
            if (menuUI.isButtonClicked("Quit"))
                System.exit(0);
        }
    }

}