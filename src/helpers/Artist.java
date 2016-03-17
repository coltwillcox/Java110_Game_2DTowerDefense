package helpers;

/**
 * Created by colt on 3/8/16.
 */

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import java.io.IOException;
import java.io.InputStream;
import static org.lwjgl.opengl.GL11.*;

public class Artist {

    public static final int WIDTH = 1472;
    public static final int HEIGHT = 960;
    public static final int TILE_SIZE = 64;

    public static void beginSession() {
        //Set title.
        Display.setTitle("Tower Defense");
        //Set window size.
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND); //Enable blending of alpha channel (transparency).
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void drawQuad(float x, float y, float width, float height) {
        //Draw quad (square).
        glBegin(GL_QUADS);
        glVertex2f(x, y); //Top left corner.
        glVertex2f(x + width, y); //Top right corner.
        glVertex2f(x + width, y + height); //Bottom right corner.
        glVertex2f(x, y + height); //Bottom left corner.
        glEnd();
    }

    //Draw 4squared textured shape.
    public static void drawQuadTex(Texture tex, float x, float y, float width, float height) {
        tex.bind();
        glTranslatef(x, y, 0); //Set reference point from where to draw texture. "x" and "y" are now 0,0 coordinates for texture.
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0); //Top left corner.
        glVertex2f(0, 0); //-||-
        glTexCoord2f(1, 0);
        glVertex2f(width, 0);
        glTexCoord2f(1, 1);
        glVertex2f(width, height);
        glTexCoord2f(0, 1);
        glVertex2f(0, height);
        glEnd();
        glLoadIdentity();
    }

    //Draw 4squared textured shape rotated.
    public static void drawQuadTexRot(Texture tex, float x, float y, float width, float height, float angle) {
        tex.bind();
        glTranslatef(x + (width / 2), y + (height / 2), 0); //Make center 0, 0. Use center for rotation.
        glRotatef(angle, 0, 0, 1); //Rotate texture.
        glTranslatef(-width / 2, -height / 2, 0); //Reset reference point.
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0); // Top left corner.
        glVertex2f(0, 0); // -||-
        glTexCoord2f(1, 0);
        glVertex2f(width, 0);
        glTexCoord2f(1, 1);
        glVertex2f(width, height);
        glTexCoord2f(0, 1);
        glVertex2f(0, height);
        glEnd();
        glLoadIdentity();
    }

    //Load texture method.
    public static Texture loadTexture(String path, String fileType) {
        Texture tex = null;
        InputStream in = ResourceLoader.getResourceAsStream(path);
        try {
            tex = TextureLoader.getTexture(fileType, in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tex;
    }

    //Load texture method with filename only, no extension.
    public static Texture quickLoadTex(String name) {
        Texture tex = null;
        tex = loadTexture("res/" + name + ".png", "PNG");
        return tex;
    }

    //Collision detection method.
    public static boolean checkCollision(float x1, float y1, float width1, float height1, float x2, float y2, float width2, float height2) {
        if (x1 + width1 > x2 && x1 < x2 + width2 && y1 + height1 > y2 && y1 < y2 + height2) return true;
        else return false;
    }

}