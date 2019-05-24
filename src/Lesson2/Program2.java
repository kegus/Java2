package Lesson2;

public class Program2 {
    public static void main(String[] args) throws Exception {
//        String[][] arr = new String[4][3];
        String[][] arr = {{"2s","3","4"},{"5","6","7","8"},{"9","10","11","12"},{"13","14","15","16a"}};
        MyArray myArray = new MyArray(arr);
        try {
            myArray.checkArr();
            System.out.println("Размер массива нормальный");
        } catch (MyArraySizeException e) {
            System.out.println(e.getMessage());
//            throw e;
        }
        while (true)
        try {
            myArray.checkCast();
            break;
        } catch (MyArrayDataException e) {
            System.out.println(e.getMessage());
            System.out.println("ячейка "+e.getI()+" "+e.getJ()+" исправлена с "+arr[e.getI()][e.getJ()]+" на 0");
            arr[e.getI()][e.getJ()] = "0";
        } catch (MyArraySizeException e) {
            System.out.println(e.getMessage());
            break;
        }

        System.out.println("Ok");
    }
}
