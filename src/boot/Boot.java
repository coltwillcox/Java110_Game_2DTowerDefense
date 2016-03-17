package boot;

/**
 * Created by colt on 3/8/16.
 */

import helpers.Clock;
import helpers.StateManager;
import org.lwjgl.opengl.Display;
import static helpers.Artist.beginSession;

public class Boot {

    public static void main(String[] args) {
        //Call static method in Artist class to initialize OpenGL calls.
        beginSession();
        //Main game loop.
        while (!Display.isCloseRequested()) {
            Clock.update();
            StateManager.update();
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }

}