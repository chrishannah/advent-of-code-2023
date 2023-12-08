package me.chrishannah.adventofcode.day8;

import me.chrishannah.adventofcode.AOCUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HauntedWasteland {

    private record Instruction(String left, String right) {
    }

    private static long calculateShortestDistance() {
        var reader = AOCUtils.readFile("src/main/resources/day-8-input.txt");
        var instructions = "";
        try {
            instructions = reader.readLine();
            reader.readLine();
        } catch (IOException ignored) {
        }
        var instructionMap = new HashMap<String, Instruction>();
        reader.lines().forEach(line -> {
            instructionMap.put(line.substring(0, 3), new Instruction(line.substring(7, 10), line.substring(12, 15)));
        });

        var moves = 0;
        var reachedDestinaton = false;
        var currentLocation = "AAA";
        var currentNode = instructionMap.get(currentLocation);
        while (!reachedDestinaton) {
            for (int i = 0; i < instructions.length(); i++) {
                moves++;
                String nextLocation = "";
                switch (instructions.substring(i, i + 1)) {
                    case "L":
                        nextLocation = currentNode.left;
                        break;
                    case "R":
                        nextLocation = currentNode.right;
                        break;
                    default:
                        break;
                }
                currentLocation = nextLocation;
                if (currentLocation.equals("ZZZ")) {
                    reachedDestinaton = true;
                }
                currentNode = instructionMap.get(currentLocation);
            }
        }

        return moves;
    }

    private static long calculateShortestDistanceWithMultipleRoutes() {
        var reader = AOCUtils.readFile("src/main/resources/day-8-input.txt");
        var instructions = "";
        try {
            instructions = reader.readLine();
            reader.readLine();
        } catch (IOException ignored) {
        }
        var instructionMap = new HashMap<String, Instruction>();
        reader.lines().forEach(line -> {
            instructionMap.put(line.substring(0, 3), new Instruction(line.substring(7, 10), line.substring(12, 15)));
        });

        var locations = instructionMap.keySet().stream().filter(key -> key.substring(2, 3).equals("A")).toList();
        var locationCount = locations.size();
        var minMoves = new ArrayList<Long>();
        var moves = 0L;
        while (minMoves.size() != locationCount) {
            for (int i = 0; i < instructions.length(); i++) {
                moves++;
                List<String> newLocations = new ArrayList<>();
                for (String location : locations) {
                    if (location.equals("ZZZ")) {
                        continue;
                    }
                    var node = instructionMap.get(location);
                    var nextLocation = "";
                    switch (instructions.substring(i, i + 1)) {
                        case "L":
                            nextLocation = node.left;
                            break;
                        case "R":
                            nextLocation = node.right;
                            break;
                        default:
                            break;
                    }

                    if (nextLocation.substring(2).equals("Z")) {
                        minMoves.add(moves);
                        newLocations.add("ZZZ");
                    } else {
                        newLocations.add(nextLocation);
                    }
                }

                locations = newLocations;
            }
        }

        var movesArray = minMoves.stream().toArray(Long[]::new);
        return AOCUtils.lcm(movesArray, 0, movesArray.length);

    }

    public static void main(String[] args) {
        // Part 1
        var start = new Date().getTime();
        var answer = calculateShortestDistance();
        var stop = new Date().getTime();
        System.out.println(STR. "Part 1 | Answer: \{ answer } Time: \{ stop - start }ms" );

        // Part 2
        start = new Date().getTime();
        answer = calculateShortestDistanceWithMultipleRoutes();
        stop = new Date().getTime();
        System.out.println(STR. "Part 2 | Answer: \{ answer } Time: \{ stop - start }ms" );
    }
}

