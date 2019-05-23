package Lesson1;

public class Barrier {
    private String name;

    public Barrier(String name) {
        this.name = name;
    }

    public void printResult(boolean res){
        System.out.println((res?"":" не")+" преодолел "+name);
    }
}
