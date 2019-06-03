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

        // 2-е задание
        PhoneDictionary phoneDict = new PhoneDictionary();
        phoneDict.add("Ivanov", "+7 123 456 789");
        phoneDict.add("Ivanov", "+7 123 654 789");
        phoneDict.add("Petrov", "+7 321 456 789");
        phoneDict.add("Petrov", "+7 321 456 987");
        System.out.println("Ivanov: "+phoneDict.get("Ivanov"));
        System.out.println("Petrov: "+phoneDict.get("Petrov"));
        System.out.println("Ok");
    }
}
