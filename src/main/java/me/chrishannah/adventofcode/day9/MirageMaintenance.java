package me.chrishannah.adventofcode.day9;

import me.chrishannah.adventofcode.AOCUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MirageMaintenance {

    private static long calculateSumOfPredictedNextValues() {
        return AOCUtils.readFile("src/main/resources/day-9-input.txt").lines().map(MirageMaintenance::parseLine).map(MirageMaintenance::predictNextValue).reduce(0L, Long::sum);
    }

    private static long calculateSumOfPredictedPreviousValues() {
        return AOCUtils.readFile("src/main/resources/day-9-input.txt").lines().map(MirageMaintenance::parseLine).map(AOCUtils::reverseList).map(MirageMaintenance::predictPreviousValue).reduce(0L, Long::sum);
    }

    private static long predictNextValue(List<Long> sequence) {
        var sequences = new ArrayList<List<Long>>();

        var current = sequence;
        var buffer = new ArrayList<Long>();

        while (true) {
            for (int i = 0; i < current.size() - 1; i++) {
                buffer.add(current.get(i + 1) - current.get(i));
            }
            sequences.add(current);
            current = buffer;
            if (buffer.stream().allMatch(l -> l == 0L)) {
                sequences.add(buffer);
                break;
            }
            buffer = new ArrayList<>();
        }

        var d = 0L;
        var ss = sequences.size();
        for (int s = ss - 2; s >= 0; s--) {
            var nextSeq = sequences.get(s + 1);
            var last = nextSeq.getLast();
            d = d + last;
        }

        return sequences.getFirst().getLast() + d;
    }

    private static long predictPreviousValue(List<Long> sequence) {
        var sequences = new ArrayList<List<Long>>();

        var current = sequence;
        var buffer = new ArrayList<Long>();

        while (true) {
            for (int i = 0; i < current.size() - 1; i++) {
                buffer.add(current.get(i + 1) - current.get(i));
            }
            sequences.add(current);
            current = buffer;
            if (buffer.stream().allMatch(l -> l == 0L)) {
                sequences.add(buffer);
                break;
            }
            buffer = new ArrayList<>();
        }

        var d = 0L;
        var ss = sequences.size();
        for (int s = ss - 2; s >= 0; s--) {
            var nextSeq = sequences.get(s + 1);
            var first = nextSeq.getFirst();
            d = first - d;
        }

        return sequences.getFirst().getFirst() - d;
    }


    private static List<Long> parseLine(String line) {
        return Arrays.stream(line.split(" ")).map(s -> Long.parseLong(s)).toList();
    }


    public static void main(String[] args) {
        // Part 1
        System.out.println(calculateSumOfPredictedNextValues());

        // Part 2
        System.out.println(calculateSumOfPredictedPreviousValues());
    }
}
