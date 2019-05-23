package Lesson1;

public class RunningRoad extends Barrier{
    private int width;

    public int getWidth() {
        return width;
    }

    public RunningRoad(int width) {
        super("Беговую дорожку ("+width+")");
        this.width = width;
    }
}
