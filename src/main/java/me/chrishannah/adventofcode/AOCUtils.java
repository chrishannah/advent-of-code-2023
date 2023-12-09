package me.chrishannah.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AOCUtils {

    public static BufferedReader readFile(String path) {
        try {
            return Files.newBufferedReader(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String reverseString(String input) {
        var b = new StringBuilder(input);
        b.reverse();
        return b.toString();
    }

    public static <T> List<T> reverseList(List<T> list) {
        var newList = new ArrayList<T>();
        for (int i = 0, j = list.size() - 1; i < j; i++) {
            newList.add(i, list.get(i));
        }
        return newList;
    }

    public static long lcm(Long[] numbers, int start, int end) {
        if (end - start == 1) {
            return lcm(numbers[start], numbers[end - 1]);
        }
        return lcm(numbers[start], lcm(numbers, start + 1, end));
    }

    public static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    public static long gcd(long a, long b) {
        if (a < b) {
            return gcd(b, a);
        }
        if (a % b == 0) {
            return b;
        }
        return gcd(b, a % b);
    }

}
