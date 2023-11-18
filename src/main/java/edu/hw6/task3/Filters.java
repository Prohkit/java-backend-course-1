package edu.hw6.task3;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filters {
    private Filters() {
    }

    public static AbstractFilter regularFile = Files::isRegularFile;
    public static AbstractFilter readable = Files::isReadable;
    public static AbstractFilter writable = Files::isWritable;

    public static AbstractFilter largerThan(int size) {
        return path -> Files.size(path) >= size;
    }

    public static AbstractFilter regexContains(String regex) {
        return (Path path) -> {
            Pattern pattern = Pattern.compile(regex);
            String fileName = new File(path.toString()).getName();
            Matcher matcher = pattern.matcher(fileName);
            return matcher.matches();
        };
    }

    public static AbstractFilter globMatches(String glob) {
        return (Path path) -> {
            String regex = "\\.(?=[^\\.]+$)";
            String fileName = new File(path.toString()).getName();
            String fileExtension = fileName.split(regex)[1];
            String inputExtension = glob.split(regex)[1];
            return fileExtension.equals(inputExtension);
        };
    }
}
