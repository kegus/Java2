package Lesson3;

import java.util.*;

public class Program3 {
    public static void main(String[] args) {
        // 1-е задание
        String[] arrStr = {"one", "two", "three", "four", "one", "two", "three", "four",
                "five", "six", "three", "four", "one", "two", "five", "four", "one", "two", "one", "two"};
        Set<String> checkSet = new HashSet<>();
        Map<String, Integer> countMap = new HashMap<>();
        for (int i = 0; i < arrStr.length; i++) {
            checkSet.add(arrStr[i]);

            if (countMap.containsKey(arrStr[i])) {
                int count = countMap.get(arrStr[i]);
                countMap.put(arrStr[i], ++count);
            } else {
                countMap.put(arrStr[i], 1);
            }
        }
        System.out.println(Arrays.toString(arrStr));
        System.out.println(checkSet);
        System.out.println(countMap);
        System.out.println();
        System.out.println("Ok");
    }
}
