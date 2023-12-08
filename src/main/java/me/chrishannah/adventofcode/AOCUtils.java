package me.chrishannah.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AOCUtils {

    public static BufferedReader readFile(String path) {
        try {
            return Files.newBufferedReader(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
