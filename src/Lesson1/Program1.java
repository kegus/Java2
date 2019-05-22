package Lesson1;

import java.util.Random;

public class Program1 {
    public static void main(String[] args) {
        int maxRun;
        int maxJump;
        // создание полосы препятствий
        Barrier[] trace_road = new Barrier[5];
        for (int i = 0; i < trace_road.length; i++) {
            int val;
            if (rnd.nextInt(2) == 0) {
                val = rnd.nextInt(100);
                trace_road[i] = new RunningRoad(val);
            } else {
                val = rnd.nextInt(10);
                trace_road[i] = new Wall(val);
            }
        }
        // присвоение макс. бег и прыжок
        maxRun = rnd.nextInt(100)+1;
        maxJump = rnd.nextInt(10)+1;
        Human h = new Human(maxRun, maxJump);
        maxRun = rnd.nextInt(10)+1;
        maxJump = rnd.nextInt(5)+1;
        Cat c = new Cat(maxRun, maxJump);
        maxRun = rnd.nextInt(200)+1;
        maxJump = rnd.nextInt(25)+1;
        Bot b = new Bot(maxRun, maxJump);
        // преодоление полосы
        for (int i = 0; i < trace_road.length; i++) {
            h.doAction(trace_road[i]);
            c.doAction(trace_road[i]);
            b.doAction(trace_road[i]);
        }
        // результат
        System.out.println("Человек "+(h.canMove()?" ":" не ")+"дошёл до финиша");
        System.out.println("Кот "+(c.canMove()?" ":" не ")+"дошёл до финиша");
        System.out.println("Робот "+(b.canMove()?" ":" не ")+"дошёл до финиша");
        System.out.println("ok");
    }

    static Random rnd = new Random();
}
