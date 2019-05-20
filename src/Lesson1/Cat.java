package Lesson1;

public class Cat implements IActions {
    private int maxRun;
    private int maxJump;
    public boolean canMove = true;

    public Cat(int maxRun, int maxJump) {
        this.maxRun = maxRun;
        this.maxJump = maxJump;
    }

    @Override
    public boolean run(RunningRoad road) {
        return canMove = maxRun >= road.getWidth();
    }

    @Override
    public boolean jump(Wall wall) {
        return canMove = maxJump >= wall.getHeight();
    }

    public int getMaxRun() {
        return maxRun;
    }

    public int getMaxJump() {
        return maxJump;
    }

}
