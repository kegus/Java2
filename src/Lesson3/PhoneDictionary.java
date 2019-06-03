package Lesson3;

import java.util.*;

public class PhoneDictionary {
    private Map<String, HashSet<String>> dict;

    public PhoneDictionary() {
        dict = new HashMap<>();
    }

    public boolean add(String name, String phone) {
        boolean res;
        HashSet<String> phones;
        if (dict.containsKey(name)) {
            phones = dict.get(name);
            res = false;
        } else {
            phones = new HashSet<>();
            res = true;
        }
        phones.add(phone);
        dict.put(name, phones);
        return res;
    }

    public HashSet get(String name) {
        return dict.get(name);
    }
}
