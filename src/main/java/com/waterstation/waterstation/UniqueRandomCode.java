package com.waterstation.waterstation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class UniqueRandomCode {

    public static void main(String[] args) {
        Set<String> uniqueCodes = generateUniqueRandomCodes(1);
        Iterator<String> iterator = uniqueCodes.iterator();
        if (iterator.hasNext()) {
            String salerOrderId = iterator.next();
            System.out.println("取出的第一个代码: " + salerOrderId);
        }
    }

    public static Set<String> generateUniqueRandomCodes(int count) {
        Set<String> codes = new HashSet<>();
        Random random = new Random();
        while (codes.size() < count) {
            char letter = (char) (random.nextInt(26) + 'a');
            int digit =random.nextInt(1000000000);
            String code = String.valueOf(letter) + digit;
            if (!codes.contains(code)) {
                codes.add(code);
            }
        }
        return codes;
    }
}
