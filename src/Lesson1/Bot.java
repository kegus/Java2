package Lesson1;

public class Bot implements IActions {
    private int maxRun;
    private int maxJump;
    private boolean canMove = true;

    public Bot(int maxRun, int maxJump) {
        this.maxRun = maxRun;
        this.maxJump = maxJump;
    }

    @Override
    public void doAction(Barrier barrier) {
        if (canMove) {
            System.out.print("Робот ("+maxRun+","+maxJump+")");
            barrier.printResult(barrier instanceof RunningRoad?run((RunningRoad) barrier):jump((Wall) barrier));
        }
    }

    @Override
    public boolean canMove() { return canMove; }

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
