package Lesson2;

public class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
}

class MyArraySizeException extends MyException {
    public MyArraySizeException() {
        super("Неверный размер массива");
    }
}

class MyArrayDataException extends NumberFormatException {
    private int i = -1;
    private int j = -1;

    public MyArrayDataException(int i, int j) {
        super("Неверный формат числа в "+i+" "+j+" ячейке");
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}