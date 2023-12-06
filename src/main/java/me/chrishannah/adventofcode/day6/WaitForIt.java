package me.chrishannah.adventofcode.day6;

import me.chrishannah.adventofcode.AOCUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class WaitForIt {

    private static int calculateNumberOfPossibleWinningSolutions(int time, int recordDistance) {
        var winningSolutions = 0;
        for (int i = 1; i < time; i++) {
            var distance = (time - i) * i;
            if (distance > recordDistance) {
                winningSolutions++;
            }
        }
        return winningSolutions;
    }

    private static int calculateNumberOfPossibleWinningSolutions(long time, long recordDistance) {
        var winningSolutions = 0;
        for (long i = 1; i < time; i++) {
            var distance = (time - i) * i;
            if (distance > recordDistance) {
                winningSolutions++;
            }
        }
        return winningSolutions;
    }

    private static int calculateProductOfTotalWinningSolutions() {
        var reader = AOCUtils.readFile("src/main/resources/day-6-input.txt");
        var solutions = new ArrayList<Integer>();
        try {
            var times = Arrays.stream(reader.readLine().substring(5).split(" ")).filter(s -> !s.isEmpty()).toList();
            var distances = Arrays.stream(reader.readLine().substring(9).split(" ")).filter(s -> !s.isEmpty()).toList();
            for (int i = 0; i < times.size(); i++) {
                var t = Integer.parseInt(times.get(i));
                var d = Integer.parseInt(distances.get(i));
                solutions.add(calculateNumberOfPossibleWinningSolutions(t, d));
            }
        } catch (IOException ignored) {
        }
        return solutions.stream().reduce((a, b) -> a * b).orElse(0);
    }

    private static int calculateWinningSolutionToSingleRace() {
        var reader = AOCUtils.readFile("src/main/resources/day-6-input.txt");
        try {
            var time = Long.parseLong(reader.readLine().substring(5).replace(" ", ""));
            var distance = Long.parseLong(reader.readLine().substring(9).replace(" ", ""));
            return calculateNumberOfPossibleWinningSolutions(time, distance);
        } catch (IOException ignored) {
            return 0;
        }
    }

    public static void main(String[] args) {

        // Part 1
        System.out.println(calculateProductOfTotalWinningSolutions());

        // Part 2
        System.out.println(calculateWinningSolutionToSingleRace());
    }
}
