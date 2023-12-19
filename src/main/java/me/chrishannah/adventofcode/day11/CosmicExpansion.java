package me.chrishannah.adventofcode.day11;

import me.chrishannah.adventofcode.AOCUtils;

import java.util.*;

public class CosmicExpansion {

    private static Long calculateSumOfLengths() {
        var map = AOCUtils.readFile("src/main/resources/day-11-input.txt").lines().toList();
        var expandedMap = expandMap(map);
        var galaxyLocations = extractGalaxyLocations(expandedMap);
        printMap(expandedMap);
        var galaxyPairs = calculateGalaxyPairs(galaxyLocations);
        return galaxyPairs.stream().map(pair -> {
            var g1 = galaxyLocations.get(pair.get(0));
            var g2 = galaxyLocations.get(pair.get(1));
            return (long) (Math.abs(g1.y - g2.y) + Math.abs(g1.x - g2.x));
        }).reduce(0L, Long::sum);
    }

    private static Set<List<Integer>> calculateGalaxyPairs(HashMap<Integer, Location> galaxyLocations) {
        var galaxyPairs = new HashSet<List<Integer>>();
        var s = galaxyLocations.size();
        for (int b = 1; b <= s; b++) {
            for (int e = 1; e <= s; e++) {
                if (e != b) {
                    if (e < b) {
                        galaxyPairs.add(List.of(e, b));
                    } else {
                        galaxyPairs.add(List.of(b, e));
                    }
                }
            }
        }
        return galaxyPairs;
    }

    private static HashMap<Integer, Location> extractGalaxyLocations(ArrayList<String> expandedMap) {
        var galaxyLocations = new HashMap<Integer, Location>();
        var counter = 1;
        for (int i = 0; i < expandedMap.size(); i++) {
            for (int f = 0; f < expandedMap.get(0).length(); f++) {
                var r = expandedMap.get(i).substring(f, f + 1);
                if (r.equals("#")) {
                    galaxyLocations.put(counter, new Location(f, i));
                    counter++;
                }
            }
        }
        return galaxyLocations;
    }

    private static ArrayList<String> expandMap(List<String> map) {
        var expandedHMap = new ArrayList<String>();
        var expandedYMap = new ArrayList<String>();
        for (String line : map) {
            expandedYMap.add("");
            expandedHMap.add(line);
            if (!line.contains("#")) {
                expandedYMap.add("");
                expandedHMap.add(line);
            }
        }
        for (int i = 0; i < expandedHMap.get(0).length(); i++) {
            var hasGalaxy = false;
            for (String line : expandedHMap) {
                if (line.substring(i, i + 1).contains("#")) {
                    hasGalaxy = true;
                }
            }
            for (int y = 0; y < expandedYMap.size(); y++) {
                var a = expandedHMap.get(y).substring(i, i + 1);
                if (!hasGalaxy) {
                    a = "..";
                }
                var c = expandedYMap.get(y);
                expandedYMap.set(y, c + a);
            }
        }
        return expandedYMap;
    }

    private static void printMap(List<String> map) {
        System.out.println();
        for (String line : map) {
            System.out.println(line);
        }
        System.out.println();

    }

    public static void main(String[] args) {
        // Part 1
        System.out.println(calculateSumOfLengths());
    }

    private record Location(int x, int y) {
    }
}

