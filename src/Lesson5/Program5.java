package Lesson5;

import java.util.Arrays;

public class Program5 {
    static final int size = 6000000;
    static final int divider = 6000001;
    static final int sleepMs = 10;
    static final int h = size / 2;
    static final int h1 = size / 3;
    static final int h2 = size / 4;
    static float[] arr = new float[size];
    static float[] a1 = new float[h];
    static float[] a2 = new float[h];
    static float[] b1 = new float[h1];
    static float[] b2 = new float[h1];
    static float[] b3 = new float[h1];
    static float[] c1 = new float[h2];
    static float[] c2 = new float[h2];
    static float[] c3 = new float[h2];
    static float[] c4 = new float[h2];
    static int cntThreads;
    static Object lock = new Object();
    static Object lock1 = new Object();
    static Object lock2 = new Object();

    public static void main(String[] args) {
        long a;

        System.out.println("1-й метод");
        for (int i = 0; i < size; i++) arr[i] = 1;
        a = System.currentTimeMillis();
        for (int i = 0; i < size; i++)
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        System.out.println(System.currentTimeMillis() - a);

        System.out.println("2-й метод с 2 потоками");
        for (int i = 0; i < size; i++) arr[i] = 1;
        a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        new Thread(() -> calcArr(false)).start();
        new Thread(() -> calcArr(true)).start();
        try {
            int checkCntThreads;
            do {
                Thread.sleep(sleepMs);
                synchronized(lock) { checkCntThreads = cntThreads; }
            } while (checkCntThreads > 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        System.out.println(System.currentTimeMillis() - a);

        System.out.println("2-й метод с 3 потоками");
        for (int i = 0; i < size; i++) arr[i] = 1;
        a = System.currentTimeMillis();
        System.arraycopy(arr, 0, b1, 0, h1);
        System.arraycopy(arr, h1, b2, 0, h1);
        System.arraycopy(arr, 2*h1, b3, 0, h1);
        new Thread(() -> calcArr1(0)).start();
        new Thread(() -> calcArr1(1)).start();
        new Thread(() -> calcArr1(2)).start();
        try {
            int checkCntThreads;
            do {
                Thread.sleep(sleepMs);
                synchronized(lock1) { checkCntThreads = cntThreads; }
            } while (checkCntThreads > 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(b1, 0, arr, 0, h1);
        System.arraycopy(b2, 0, arr, h1, h1);
        System.arraycopy(b3, 0, arr, 2*h1, h1);
        System.out.println(System.currentTimeMillis() - a);

        System.out.println("2-й метод с 4 потоками");
        for (int i = 0; i < size; i++) arr[i] = 1;
        a = System.currentTimeMillis();
        System.arraycopy(arr, 0, c1, 0, h2);
        System.arraycopy(arr, h2, c2, 0, h2);
        System.arraycopy(arr, 2*h2, c3, 0, h2);
        System.arraycopy(arr, 3*h2, c4, 0, h2);
        new Thread(() -> calcArr2(0)).start();
        new Thread(() -> calcArr2(1)).start();
        new Thread(() -> calcArr2(2)).start();
        new Thread(() -> calcArr2(3)).start();
        try {
            int checkCntThreads;
            do {
                Thread.sleep(sleepMs);
                synchronized(lock2) { checkCntThreads = cntThreads; }
            } while (checkCntThreads > 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(c1, 0, arr, 0, h2);
        System.arraycopy(c2, 0, arr, h2, h2);
        System.arraycopy(c3, 0, arr, 2*h2, h2);
        System.arraycopy(c4, 0, arr, 3*h2, h2);
        System.out.println(System.currentTimeMillis() - a);

        System.out.println("Ok");
    }


    public static void calcArr(boolean n) {
        if (n) {
            synchronized(lock) { cntThreads++; }
            for (int i = 0; i < h; i++)
                a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            synchronized(lock) { cntThreads--; }
        } else {
            synchronized(lock) { cntThreads++; }
            for (int i = 0; i < h; i++)
                a2[i] = (float)(a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            synchronized(lock) { cntThreads--; }
        }
    }
    public static void calcArr1(int n) {
        switch (n) {
            case 0:
                synchronized(lock1) { cntThreads++; }
                for (int i = 0; i < h1; i++) {
                    b1[i] = (float) (b1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//                    if ((i+1) % divider == 0)
//                    try{
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                synchronized(lock1) { cntThreads--; }
            case 1:
                synchronized(lock1) { cntThreads++; }
                for (int i = 0; i < h1; i++) {
                    b2[i] = (float)(b2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//                    if ((i+1) % divider == 0)
//                    try{
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                synchronized(lock1) { cntThreads--; }
            default:
                synchronized(lock1) { cntThreads++; }
                for (int i = 0; i < h1; i++) {
                    b3[i] = (float)(b3[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//                    if ((i+1) % divider == 0)
//                    try{
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                synchronized(lock1) { cntThreads--; }
        }
    }
    public static void calcArr2(int n) {
        switch (n) {
            case 0:
                synchronized(lock2) { cntThreads++; }
                for (int i = 0; i < h2; i++) {
                    c1[i] = (float)(c1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//                    if ((i+1) % divider == 0)
//                    try{
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                synchronized(lock2) { cntThreads--; }
            case 1:
                synchronized(lock2) { cntThreads++; }
                for (int i = 0; i < h2; i++) {
                    c2[i] = (float)(c2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//                    if ((i+1) % divider == 0)
//                    try{
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                synchronized(lock2) { cntThreads--; }
            case 2:
                synchronized(lock2) { cntThreads++; }
                for (int i = 0; i < h2; i++) {
                    c3[i] = (float)(c3[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//                    if ((i+1) % divider == 0)
//                    try{
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                synchronized(lock2) { cntThreads--; }
            default:
                synchronized(lock2) { cntThreads++; }
                for (int i = 0; i < h2; i++) {
                    c4[i] = (float)(c4[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//                    if ((i+1) % divider == 0)
//                    try{
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                synchronized(lock2) { cntThreads--; }
        }
    }
}

