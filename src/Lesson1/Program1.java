package Lesson1;

import java.util.Random;

public class Program1 {
    public static void main(String[] args) {
        int maxRun;
        int maxJump;
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
        maxRun = rnd.nextInt(100)+1;
        maxJump = rnd.nextInt(10)+1;
        Human h = new Human(maxRun, maxJump);
        maxRun = rnd.nextInt(10)+1;
        maxJump = rnd.nextInt(5)+1;
        Cat c = new Cat(maxRun, maxJump);
        maxRun = rnd.nextInt(200)+1;
        maxJump = rnd.nextInt(25)+1;
        Bot b = new Bot(maxRun, maxJump);

        for (int i = 0; i < trace_road.length; i++) {
            if (h.canMove()) {
                System.out.print("Человек ("+h.getMaxRun()+","+h.getMaxJump()+")");
                if (trace_road[i] instanceof RunningRoad) {
                    ((Barrier) trace_road[i]).printResult(h.run((RunningRoad) trace_road[i]));
                } else {
                    ((Barrier) trace_road[i]).printResult(h.jump((Wall) trace_road[i]));
                }
            }
            if (c.canMove()) {
                System.out.print("Кот ("+c.getMaxRun()+","+c.getMaxJump()+")");
                if (trace_road[i] instanceof RunningRoad) {
                    ((Barrier) trace_road[i]).printResult(c.run((RunningRoad) trace_road[i]));
                } else {
                    ((Barrier) trace_road[i]).printResult(c.jump((Wall) trace_road[i]));
                }
            }
            if (b.canMove()) {
                System.out.print("Робот ("+b.getMaxRun()+","+b.getMaxJump()+")");
                if (trace_road[i] instanceof RunningRoad) {
                    ((Barrier) trace_road[i]).printResult(b.run((RunningRoad) trace_road[i]));
                } else {
                    ((Barrier) trace_road[i]).printResult(b.jump((Wall) trace_road[i]));
                }
            }
        }
        System.out.println("Человек "+(h.canMove()?" ":" не ")+"дошёл до финиша");
        System.out.println("Кот "+(c.canMove()?" ":" не ")+"дошёл до финиша");
        System.out.println("Робот "+(b.canMove()?" ":" не ")+"дошёл до финиша");
        System.out.println("ok");
    }

    static Random rnd = new Random();
}
