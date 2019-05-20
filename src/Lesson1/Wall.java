package Lesson1;

public class Wall extends Barrier{
    private int height;

    public int getHeight() {
        return height;
    }

    public Wall(int height) {
        super("Стену ("+height+")");
        this.height = height;
    }
}
