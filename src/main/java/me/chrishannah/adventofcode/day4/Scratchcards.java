package me.chrishannah.adventofcode.day4;

import me.chrishannah.adventofcode.AOCUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Day 4: https://adventofcode.com/2023/day/4
 */
public class Scratchcards {

    private static int calculateTotalNumberofScratchcardsWithCopies() {
        var reader = AOCUtils.readFile("src/main/resources/day-4-input.txt");
        List<Integer> winningNumberCounts = reader.lines()
                .map(Scratchcards::calculateWinningNumbersCount)
                .collect(Collectors.toList());

        // Set each copy count to 1
        List<Integer> scratchcardCopies = new ArrayList<>();
        for (int i = 0; i < winningNumberCounts.size(); i++) {
            scratchcardCopies.add(1);
        }

        // Now count extra copies
        for (int i = 0; i < scratchcardCopies.size(); i++) {
            var winningNumbers = winningNumberCounts.get(i);
            var copies = scratchcardCopies.get(i);
            if (winningNumbers == 0) { continue; }
            for (int j = 1; j <= winningNumbers; j++) {
                var index = i + j;
                var card = scratchcardCopies.get(index);
                if (card != null) {
                    card += copies;
                    scratchcardCopies.remove(index);
                    scratchcardCopies.add(index, card);
                }
            }
        }

        return scratchcardCopies.stream().reduce(0, Integer::sum);
    }

    private static int calculateTotalPoints() {
        var reader = AOCUtils.readFile("src/main/resources/day-4-input.txt");
        return reader.lines()
                .map(Scratchcards::calculateScratchcardPoints)
                .reduce(0, Integer::sum);

    }

    private static int calculateWinningNumbersCount(String input) {
        Set<Integer> scratchcardNumbers = parseNumberSetFromString(input.substring(input.indexOf(":"), input.indexOf("|")));
        Set<Integer> winningNumbers = parseNumberSetFromString(input.substring(input.indexOf("|")));
        var winningNumbersCount = 0;
        for (Integer number : scratchcardNumbers) {
            if (winningNumbers.contains(number)) {
                winningNumbersCount++;
            }
        }
        return winningNumbersCount;
    }

    private static int calculateScratchcardPoints(String input) {
        var winningNumbersCount = calculateWinningNumbersCount(input);
        if (winningNumbersCount == 0) {
            return 0;
        }
        return (int) Math.pow(2, winningNumbersCount - 1);
    }

    private static Set<Integer> parseNumberSetFromString(String input) {
        Set<Integer> set = new HashSet<>();
        for (String in : input.split(" ")) {
            try {
                var number = Integer.parseInt(in);
                set.add(number);
            } catch (NumberFormatException ignored) {
            }
        }
        return set;
    }

    public static void main(String[] args) {
        // Part 1
        System.out.println(calculateTotalPoints());

        // Part 2
        System.out.println(calculateTotalNumberofScratchcardsWithCopies());
    }
}
