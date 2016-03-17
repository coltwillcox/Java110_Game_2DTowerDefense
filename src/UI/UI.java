package UI;

/**
 * Created by colt on 3/11/16.
 */

import org.lwjgl.input.Mouse;
import java.util.ArrayList;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.TILE_SIZE;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.quickLoadTex;

public class UI {

    private ArrayList<Button> buttonList;
    private ArrayList<Menu> menuList;

    //Constructor.
    public UI() {
        buttonList = new ArrayList<>();
        menuList = new ArrayList<>();
    }

    //Draw all menus, where every menu draw all its buttons.
    public void draw() {
        for (Button b : buttonList)
            drawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
        for (Menu m : menuList)
            m.draw();
    }

    public void addButton(String name, String textureName, int x, int y) {
        buttonList.add(new Button(name, quickLoadTex(textureName), x, y));
    }

    //Method to check if mouse is inside button borders.
    public boolean isButtonClicked(String buttonName) {
        Button b = getButton(buttonName);
        float mouseY = HEIGHT - Mouse.getY() - 1;
        if ((Mouse.getX() > b.getX()) && (Mouse.getX() < b.getX() + b.getWidth()) && (mouseY > b.getY()) && (mouseY < b.getY() + b.getHeight())) return true;
        else return false;
    }

    private Button getButton(String buttonName) {
        for (Button b : buttonList) {
            if (b.getName().equals(buttonName)) return b;
        }
        return null;
    }

    public void createMenu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight) {
        menuList.add(new Menu(name, x, y, width, height, optionsWidth, optionsHeight));

    }

    //Search for menu with given name.
    public Menu getMenu(String name) {
        for (Menu m : menuList)
            if (name.equals(m.getName()))
                return m;
        return null;
    }

    //New (sub)class.
    public class Menu {

        private String name; //?
        private ArrayList<Button> menuButtons;
        private int x;
        private int y;
        private int width;
        private int height;
        private int optionsWidth;
        private int optionsHeight;
        private int padding;
        private int buttonsAmmount;

        //Constructor of subclass.
        public Menu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.optionsWidth = optionsWidth;
            this.optionsHeight = optionsHeight;
            this.padding = (width - (optionsWidth * TILE_SIZE)) / (optionsWidth + 1);
            this.buttonsAmmount = 0;
            menuButtons = new ArrayList<>();
        }

        public void addButton(Button b) {
            //Create buttons in several rows. Each row has optionsWidth number of buttons.
            b.setX(x + (buttonsAmmount % optionsWidth) * (padding + TILE_SIZE) + padding);
            b.setY(y + (buttonsAmmount / optionsWidth) * TILE_SIZE);
            menuButtons.add(b);
            buttonsAmmount++;
        }

        public void draw() {
            for (Button b : menuButtons)
                drawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
        }

        public boolean isButtonClicked(String buttonName) {
            Button b = getButton(buttonName);
            float mouseY = HEIGHT - Mouse.getY() - 1;
            if ((Mouse.getX() > b.getX()) && (Mouse.getX() < b.getX() + b.getWidth()) && (mouseY > b.getY()) && (mouseY < b.getY() + b.getHeight())) return true;
            else return false;
        }

        private Button getButton(String buttonName) {
            for (Button b : menuButtons) {
                if (b.getName().equals(buttonName)) return b;
            }
            return null;
        }

        public String getName() {
            return name;
        }

    }

}