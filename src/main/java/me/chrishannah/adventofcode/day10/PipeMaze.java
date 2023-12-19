package me.chrishannah.adventofcode.day10;

import me.chrishannah.adventofcode.AOCUtils;

import java.util.ArrayList;
import java.util.List;

public class PipeMaze {

    private static long calculateFurthestDistanceFromStart() {
        var lines = AOCUtils.readFile("src/main/resources/day-10-input.txt").lines().toList();
        var columns = lines.get(0).length();
        var rows = lines.size();
        var maze = new String[rows][columns];


        var sC = 0;
        var sR = 0;

        // parse maze
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                var n = lines.get(r).substring(c, c + 1);
                if (n.equals("S")) {
                    sC = c;
                    sR = r;
                }
                maze[r][c] = n;
            }
        }

        // determine starting direction
        var direction = "";
        if (List.of("|", "L", "J").contains(maze[sR + 1][sC])) {
            direction = "S";
        } else if (List.of("|", "7", "F").contains(maze[sR - 1][sC])) {
            direction = "N";
        } else if (List.of("-", "L", "F").contains(maze[sR][sC - 1])) {
            direction = "W";
        } else {
            direction = "E";
        }

        var pipeCount = 0;
        var r = sR;
        var c = sC;
        var pipe = "S";
        while (true) {
            switch (direction) {
                case "N":
                    r--;
                    break;
                case "E":
                    c++;
                    break;
                case "S":
                    r++;
                    break;
                case "W":
                    c--;
                    break;
                default:
                    break;
            }
            pipe = maze[r][c];
            if (pipe.equals("S")) {
                break;
            }
            pipeCount++;
            direction = determineNextDirection(direction, pipe);
        }

        return (pipeCount / 2) + pipeCount % 2;
    }

    private static String determineNextDirection(String prevDirection, String pipeShape) {
        switch (pipeShape) {
            case "|":
                if (prevDirection.equals("N")) {
                    return "N";
                } else if (prevDirection.equals("S")) {
                    return "S";
                }
                break;
            case "-":
                if (prevDirection.equals("W")) {
                    return "W";
                } else if (prevDirection.equals("E")) {
                    return "E";
                }
                break;
            case "L":
                if (prevDirection.equals("S")) {
                    return "E";
                } else if (prevDirection.equals("W")) {
                    return "N";
                }
                break;
            case "J":
                if (prevDirection.equals("S")) {
                    return "W";
                } else if (prevDirection.equals("E")) {
                    return "N";
                }
                break;
            case "7":
                if (prevDirection.equals("N")) {
                    return "W";
                } else if (prevDirection.equals("E")) {
                    return "S";
                }
                break;
            case "F":
                if (prevDirection.equals("N")) {
                    return "E";
                } else if (prevDirection.equals("W")) {
                    return "S";
                }
                break;
            default:
                return null;
        }
        return null;
    }

    private static long calculateWeirdAreaThing() {
        var lines = AOCUtils.readFile("src/main/resources/day-10-input.txt").lines().toList();
        var columns = lines.get(0).length();
        var rows = lines.size();
        var maze = new String[rows][columns];


        var sC = 0;
        var sR = 0;

        // parse maze
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                var n = lines.get(r).substring(c, c + 1);
                if (n.equals("S")) {
                    sC = c;
                    sR = r;
                }
                maze[r][c] = n;
            }
        }

        // determine starting direction
        var direction = "";
        if (List.of("|", "L", "J").contains(maze[sR + 1][sC])) {
            direction = "S";
        } else if (List.of("|", "7", "F").contains(maze[sR - 1][sC])) {
            direction = "N";
        } else if (List.of("-", "L", "F").contains(maze[sR][sC - 1])) {
            direction = "W";
        } else {
            direction = "E";
        }

        var includedPipes = new ArrayList<String>();
        var r = sR;
        var c = sC;
        var pipe = "S";
        while (true) {
            switch (direction) {
                case "N":
                    r--;
                    break;
                case "E":
                    c++;
                    break;
                case "S":
                    r++;
                    break;
                case "W":
                    c--;
                    break;
                default:
                    break;
            }
            pipe = maze[r][c];
            if (pipe.equals("S")) {
                break;
            }
            includedPipes.add(convertToKey(c, r));
            direction = determineNextDirection(direction, pipe);
        }

        // Wipe out any pipe chars that aren't in the maze
        for (int i = 0; i < rows; i++) {
            for (int ii = 0; ii < columns; ii++) {
                var n = lines.get(i).substring(ii, ii + 1);
                if (isPipe(n) && !includedPipes.contains(convertToKey(ii, i))) {
                    maze[i][ii] = ".";
                }
            }
        }

        // clear out dots on edge of maze
        // start from left
        for (int i = 0; i < rows; i++) {
            for (int ii = 0; ii < columns; ii++) {
                var n = maze[i][ii];
                if (n.equals(".") || n.equals("0")) {
                    maze[i][ii] = "0";
                } else {
                    break;
                }
            }
        }
        // then from right
        for (int i = rows - 1; i >= 0; i--) {
            for (int ii = columns - 1; ii >= 0; ii--) {
                var n = maze[i][ii];
                if (n.equals(".") || n.equals("0")) {
                    maze[i][ii] = "0";
                } else {
                    break;
                }
            }
        }
        // from the top make it drop
        for (int ii = 0; ii < columns; ii++) {
            for (int i = 0; i < rows; i++) {
                var n = maze[i][ii];
                if (n.equals(".") || n.equals("0")) {
                    maze[i][ii] = "0";
                } else {
                    maze[i][ii] = n;
                    break;
                }
            }
        }
        // that's some...
        for (int ii = columns - 1; ii >= 0; ii--) {
            for (int i = rows - 1; i >= 0; i--) {
                var n = maze[i][ii];
                if (n.equals(".") || n.equals("0")) {
                    maze[i][ii] = "0";
                } else {
                    break;
                }
            }
        }

        var numberOfDotsInsideLoop = 0;

        var insideLoop = false;
        var lastCorner = "";
        for (int i = 0; i < rows; i++) {
            insideLoop = false;
            for (int ii = 0; ii < columns; ii++) {
                var n = maze[i][ii];
                switch (n) {
                    case "F":
                        if (lastCorner.equals("J")) {
                            insideLoop = !insideLoop;
                        }
                        lastCorner = "F";
                        break;
                    case "J":
                        if (lastCorner.equals("F")) {
                            insideLoop = !insideLoop;
                        }
                        lastCorner = "J";
                        break;
                    case "L":
                        if (lastCorner.equals("7")) {
                            insideLoop = !insideLoop;
                        }
                        lastCorner = "L";
                        break;
                    case "7":
                        if (lastCorner.equals("L")) {
                            insideLoop = !insideLoop;
                        }
                        lastCorner = "7";
                        break;
                    case "|":
                        insideLoop = !insideLoop;
                        lastCorner = "";
                        break;
                    default:
                        lastCorner = "";
                        break;
                }
                if (insideLoop && n.equals(".")) {
                    numberOfDotsInsideLoop++;
                    maze[i][ii] = "█";
                }
            }
        }

        printMaze(maze, rows, columns);


        return numberOfDotsInsideLoop;
    }

    private static void printMaze(String[][] maze, long rows, long columns) {
        System.out.println();
        for (int i = 0; i < rows; i++) {
            for (int ii = 0; ii < columns; ii++) {
                System.out.print(maze[i][ii]);
            }
            System.out.println();
        }
        System.out.println();

    }

    private static String convertToKey(long c, long r) {
        return STR. "\{ c },\{ r }" ;
    }

    private static boolean isPipe(String c) {
        return List.of("F", "|", "-", "L", "J", "7").contains(c);
    }

    public static void main(String[] args) {


        // Part 1
        System.out.println(calculateFurthestDistanceFromStart());

        // Part 2
        System.out.println(calculateWeirdAreaThing());
    }
}
