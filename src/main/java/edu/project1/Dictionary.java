package edu.project1;

import java.util.Random;
import org.jetbrains.annotations.NotNull;

public interface Dictionary {
    @NotNull
    static String randomWord() {
        Random random = new Random();
        String[] strings = {"rainbow", "catharsis", "oomph"};
        return strings[random.nextInt(strings.length)];
    }
}
