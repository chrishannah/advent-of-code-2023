package me.chrishannah.adventofcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AOCUtils {

    public static BufferedReader readFile(String path) {
        try {
            return Files.newBufferedReader(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
