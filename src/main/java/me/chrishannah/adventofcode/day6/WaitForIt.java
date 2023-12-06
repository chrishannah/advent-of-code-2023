package me.chrishannah.adventofcode.day6;

import me.chrishannah.adventofcode.AOCUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class WaitForIt {

    private static long calculateNumberOfPossibleWinningSolutions(long time, long recordDistance) {
        var firstWinningTime = 0L;
        var lastWinningTime = 0L;
        for (long i = 1; i < time; i++) {
            if ((time - i) * i > recordDistance) {
                firstWinningTime = i;
                break;
            }
        }
        for (long i = time; i > 0; i--) {
            if ((time - i) * i > recordDistance) {
                lastWinningTime = i;
                break;
            }
        }
        return lastWinningTime - firstWinningTime;
    }

    private static long calculateProductOfTotalWinningSolutions() {
        var reader = AOCUtils.readFile("src/main/resources/day-6-input.txt");
        var solutions = new ArrayList<Long>();
        try {
            var times = Arrays.stream(reader.readLine().substring(5).split(" ")).filter(s -> !s.isEmpty()).toList();
            var distances = Arrays.stream(reader.readLine().substring(9).split(" ")).filter(s -> !s.isEmpty()).toList();
            for (int i = 0; i < times.size(); i++) {
                var t = Long.parseLong(times.get(i));
                var d = Long.parseLong(distances.get(i));
                solutions.add(calculateNumberOfPossibleWinningSolutions(t, d));
            }
        } catch (IOException ignored) {
        }
        return solutions.stream().reduce((a, b) -> a * b).orElse(0L);
    }

    private static long calculateWinningSolutionToSingleRace() {
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
        var start = new Date().getTime();
        var answer = calculateProductOfTotalWinningSolutions();
        var stop = new Date().getTime();
        System.out.println(STR. "Part 1 | Answer: \{ answer } Time: \{ stop - start }ms" );

        // Part 2
        start = new Date().getTime();
        answer = calculateWinningSolutionToSingleRace();
        stop = new Date().getTime();
        System.out.println(STR. "Part 2 | Answer: \{ answer } Time: \{ stop - start }ms" );
    }
}
