package me.chrishannah.adventofcode.day2;

import me.chrishannah.adventofcode.AOCUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * Day 2: https://adventofcode.com/2023/day/2
 */
public class CubeCondundrum {

    private static int validateGame(String input) {
        var game = Integer.parseInt(input.substring(5, input.indexOf(":")));
        var sets = input.substring(input.indexOf(":") + 2).split("; ");
        Map<String, Integer> gameData = processGameData(sets);
        if (isGamePossible(gameData)) {
            return game;
        }
        return 0;
    }

    private static int calculateGamePower(String input) {
        var sets = input.substring(input.indexOf(":") + 2).split("; ");
        Map<String, Integer> gameData = processGameData(sets);
        var blue = gameData.get("blue");
        var red = gameData.get("red");
        var green = gameData.get("green");
        return red * green * blue;
    }

    private static boolean isGamePossible(Map<String, Integer> gameData) {
        var blue = gameData.get("blue");
        if (blue == null || blue > 14) {
            return false;
        }
        var red = gameData.get("red");
        if (red == null || red > 12) {
            return false;
        }
        var green = gameData.get("green");
        if (green == null || green > 13) {
            return false;
        }
        return true;
    }


    private static Map<String, Integer> processGameData(String[] sets) {
        Map<String, Integer> counts = new HashMap<>();
        counts.put("blue", 0);
        counts.put("red", 0);
        counts.put("green", 0);
        for (String set : sets) {
            var splitSet = set.split(", ");
            for (String singleCount : splitSet) {
                var splitCount = singleCount.split(" ");
                var colour = splitCount[1];
                var colourCount = Integer.parseInt(splitCount[0]);
                if (colourCount > counts.get(colour)) {
                    counts.put(colour, colourCount);
                }
            }
        }
        return counts;
    }

    private static Integer calculateTotalValidGameIds() {
        var reader = AOCUtils.readFile("src/main/resources/day-2-input.txt");
        return reader.lines()
                .map(CubeCondundrum::validateGame)
                .reduce(0, Integer::sum);
    }

    private static Integer calculateTotalPowerOfEachGame() {
        var reader = AOCUtils.readFile("src/main/resources/day-2-input.txt");
        return reader.lines()
                .map(CubeCondundrum::calculateGamePower)
                .reduce(0, Integer::sum);
    }

    public static void main(String[] args) {
        // Part One
        System.out.println(calculateTotalValidGameIds());

        // Part Two
        System.out.println(calculateTotalPowerOfEachGame());
    }

}
