package helpers;

/**
 * Created by colt on 3/9/16.
 */

import org.lwjgl.Sys;

public class Clock {

    public static long lastFrame;
    public static long totalTime;
    public static float d = 0;
    public static float multiplier = 1;
    private static boolean paused = false;

    public static long getTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

    //Get delta method. Calculating time betwen two updates (last and now).
    public static float getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
        if (delta * 0.001f > 0.05f) return 0.05f; //Keeps enemies in the path, if programs start to lag (to rise delta time) in stupid Windows. Linux ftw!
        else return delta * 0.001f;
    }

    public static float delta() {
        if (paused) return 0;
        else return d * multiplier;
    }

    //Update method. Calculate total time.
    public static void update() {
        d = getDelta();
        totalTime += d;
    }

    public static void pause() {
        paused = !paused;
    }

    public static float getTotalTime() {
        return totalTime;
    }

    public static float getMultiplier() {
        return multiplier;
    }

    public static void setMultiplier(float change) {
        if (multiplier + change >= -1 && multiplier + change <= 7) multiplier += change;
    }

}