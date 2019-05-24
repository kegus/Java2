package Lesson2;

public class MyArray {
    public static int nRows = 4;
    public static int nCols = 4;
    private String[][] arr;

    public MyArray(String[][] arr) {
        this.arr = arr;
    }

    public void checkArr() throws MyException {
        if (arr.length != nCols) throw new MyArraySizeException();
        for (int i = 0; i < arr.length; i++) if (arr[i].length != nRows) throw new MyArraySizeException();
    }

    public void checkCast() throws MyArrayDataException {
        for (int i = 0; i < arr.length; i++) {
            int j = 0;
            for (String s : arr[i]) {
                if (!canCast(s)) throw new MyArrayDataException();
            }
        }
    }

    private boolean canCast (String s) {
        boolean res = false;;
        try {
            int check = Integer.parseInt(s);
            res = true;
        } catch (MyArrayDataException e)
        { }
        return res;
    }
}
