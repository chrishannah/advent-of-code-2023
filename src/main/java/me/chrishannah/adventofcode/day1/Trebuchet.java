package me.chrishannah.adventofcode.day1;

import me.chrishannah.adventofcode.AOCUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Day 1: https://adventofcode.com/2023/day/1
 */
public class Trebuchet {

    private static final Map<String, String> alphaDigits = new HashMap<>() {{
        put("one", "one1one");
        put("two", "two2two");
        put("three", "three3three");
        put("four", "four4four");
        put("five", "five5five");
        put("six", "six6six");
        put("seven", "seven7seven");
        put("eight", "eight8eight");
        put("nine", "nine9nine");
    }};

    private static Integer processLineWithWords(String input) {
        var formatted = input;

        for (String number : alphaDigits.keySet()) {
            formatted = formatted.replace(number, alphaDigits.get(number));
        }

        formatted = formatted.replaceAll("\\D", "");
        var f = formatted.substring(0, 1);
        var l = formatted.substring(formatted.length() - 1);
        return Integer.parseInt(f + l);
    }

    private static Integer processLine(String input) {
        var formatted = input.replaceAll("\\D", "");
        var f = formatted.substring(0, 1);
        var l = formatted.substring(formatted.length() - 1);
        return Integer.parseInt(f + l);
    }

    private static Integer calculateTotalCalibrationValue() {
        var reader = AOCUtils.readFile("src/main/resources/day-1-input.txt");
        return reader.lines()
                .map(Trebuchet::processLine)
                .reduce(0, Integer::sum);
    }

    private static Integer calculateTotalCalibrationValueWithWords() {
        var reader = AOCUtils.readFile("src/main/resources/day-1-input.txt");
        return reader.lines()
                .map(Trebuchet::processLineWithWords)
                .reduce(0, Integer::sum);
    }

    public static void main(String[] args) {
        // Part One
        System.out.println(calculateTotalCalibrationValue());

        // Part Two
        System.out.println(calculateTotalCalibrationValueWithWords());
    }

}

