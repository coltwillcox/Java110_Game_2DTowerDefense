package boot;

/**
 * Created by colt on 3/12/16.
 */

public interface Entity {

    public void update();
    public void draw();
    public float getX();
    public float getY();
    public int getWidth();
    public int getHeight();
    public void setX(float x);
    public void setY(float y);
    public void setWidth(int width);
    public void setHeight(int height);

}