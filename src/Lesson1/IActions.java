package Lesson1;

public interface IActions {
    boolean run(RunningRoad road);
    boolean jump(Wall wall);
    boolean canMove();
    void doAction(Barrier barrier);
}
