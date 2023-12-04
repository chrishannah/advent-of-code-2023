package me.chrishannah.adventofcode.day3;

import me.chrishannah.adventofcode.AOCUtils;

import java.util.*;

/**
 * Day 3: https://adventofcode.com/2023/day/3
 */
public class GearRatios {

    private static int calculateSumOfAllGearRatios() {
        var reader = AOCUtils.readFile("src/main/resources/day-3-input.txt");
        var input = reader.lines().toList();

        Map<String, List<Integer>> gearRatios = new HashMap<>();

        for (int i = 0; i < input.size(); i++) {
            var line = input.get(i);

            var numberIndex = -1;
            var number = "";
            for (int j = 0; j < line.length(); j++) {
                var s = line.substring(j, j + 1);
                // Parse any numbers
                if (isANumber(s)) {
                    number += s;
                    if (numberIndex == -1) {
                        numberIndex = j;
                    }
                }
                if (!isANumber(s) || (isANumber(s) && (numberIndex + number.length()) == line.length())) {
                    // No number found
                    if (numberIndex == -1) {
                        continue;
                    }

                    Set<String> touchingAsterisks = getLocationOfAsterisksOnSameLine(numberIndex, line, number, i);
                    if (i > 0) {
                        var previousLine = input.get(i - 1);
                        touchingAsterisks.addAll(getLocationOfAsterisksOnAdjacentLine(numberIndex, previousLine, number, i - 1));
                    }
                    if (i + 1 < input.size()) {
                        var nextLine = input.get(i + 1);
                        touchingAsterisks.addAll(getLocationOfAsterisksOnAdjacentLine(numberIndex, nextLine, number, i + 1));
                    }

                    for (String asteriskLocation : touchingAsterisks) {
                        var gearRatio = gearRatios.get(asteriskLocation);
                        if (gearRatio == null) {
                            gearRatio = new ArrayList<>();
                        }
                        gearRatio.add(Integer.parseInt(number));
                        gearRatios.put(asteriskLocation, gearRatio);
                    }

                    // Reset number
                    numberIndex = -1;
                    number = "";
                }
            }
        }

         var total = 0;
        for (List<Integer> gearRatio : gearRatios.values()) {
            if (gearRatio.size() != 2) { continue; }
            total += gearRatio.getFirst() * gearRatio.getLast();
        }

        return total;
    }

    private static int calculateSumOfAllPartNumbers() {
        var reader = AOCUtils.readFile("src/main/resources/day-3-input.txt");
        var input = reader.lines().toList();
        var total = 0;

        for (int i = 0; i < input.size(); i++) {
            var line = input.get(i);

            var numberIndex = -1;
            var number = "";
            for (int j = 0; j < line.length(); j++) {
                var s = line.substring(j, j + 1);
                // Parse any numbers
                if (isANumber(s)) {
                    number += s;
                    if (numberIndex == -1) {
                        numberIndex = j;
                    }
                }
                if (!isANumber(s) || (isANumber(s) && (numberIndex + number.length()) == line.length())) {
                    // No number found
                    if (numberIndex == -1) {
                        continue;
                    }

                    // Check for touching special characters
                    if (isNumberTouchingSymbolOnSameLine(numberIndex, line, number)) {
                        total += Integer.parseInt(number);
                        numberIndex = -1;
                        number = "";
                        continue;
                    }
                    // Check the line above
                    if (i > 0) {
                        var previousLine = input.get(i - 1);
                        if (isNumberTouchingSymbolOnAdjacentLine(numberIndex, previousLine, number)) {
                            total += Integer.parseInt(number);
                            numberIndex = -1;
                            number = "";
                            continue;
                        }
                    }
                    // Check the line below
                    if (i + 1 < input.size()) {
                        var nextLine = input.get(i + 1);
                        if (isNumberTouchingSymbolOnAdjacentLine(numberIndex, nextLine, number)) {
                            total += Integer.parseInt(number);
                            numberIndex = -1;
                            number = "";
                            continue;
                        }
                    }
                    // Reset number
                    numberIndex = -1;
                    number = "";
                }
            }

        }
        return total;
    }

    private static boolean isNumberTouchingSymbolOnSameLine(int numberIndex, String line, String number) {
        if (numberIndex > 0) {
            if (isASymbol(line.substring(numberIndex - 1, numberIndex))) {
                return true;
            }
        }
        // After the number
        var endIndex = numberIndex + number.length();
        if (endIndex < line.length()) {
            if (isASymbol(line.substring(endIndex, endIndex + 1))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNumberTouchingSymbolOnAdjacentLine(int numberIndex, String line, String number) {
        var startIndex = numberIndex == 0 ? numberIndex : numberIndex - 1;
        var endNumberIndex = (numberIndex + number.length());
        var endIndex = endNumberIndex < line.length() - 1 ? endNumberIndex + 1 : endNumberIndex;
        for (int i = startIndex; i < endIndex; i++) {
            if (isASymbol(line.substring(i, i + 1))) {
                return true;
            }
        }
        return false;
    }

    private static Set<String> getLocationOfAsterisksOnSameLine(int numberIndex, String line, String number, int lineNumber) {
        Set<String> locations = new HashSet<>();
        if (numberIndex > 0) {
            if ("*".equals(line.substring(numberIndex - 1, numberIndex))) {
                locations.add(lineNumber + "," + (numberIndex - 1));
            }
        }
        // After the number
        var endIndex = numberIndex + number.length();
        if (endIndex < line.length()) {
            if ("*".equals(line.substring(endIndex, endIndex + 1))) {
                locations.add(lineNumber + "," + endIndex);
            }
        }
        return locations;
    }

    private static Set<String> getLocationOfAsterisksOnAdjacentLine(int numberIndex, String line, String number, int lineNumber) {
        Set<String> locations = new HashSet<>();
        var startIndex = numberIndex == 0 ? numberIndex : numberIndex - 1;
        var endNumberIndex = (numberIndex + number.length());
        var endIndex = endNumberIndex < line.length() - 1 ? endNumberIndex + 1 : endNumberIndex;
        for (int i = startIndex; i < endIndex; i++) {
            if ("*".equals(line.substring(i, i + 1))) {
                locations.add(lineNumber + "," + i);
            }
        }
        return locations;
    }

    private static boolean isASymbol(String input) {
        return !isANumber(input) && !input.equals(".");
    }

    private static boolean isANumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        // Part One
        System.out.println(calculateSumOfAllPartNumbers());

        // Part Two
        System.out.println(calculateSumOfAllGearRatios());
    }

}
