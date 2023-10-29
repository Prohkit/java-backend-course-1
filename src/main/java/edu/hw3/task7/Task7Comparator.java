package edu.hw3.task7;

import java.util.Comparator;

public class Task7Comparator<T extends Comparable<T>> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        if (o1 != null && o2 != null) {
            return o1.compareTo(o2);
        } else if (o1 == null && o2 != null) {
            return -1;
        } else if (o1 != null) {
            return 1;
        } else {
            return 0;
        }
    }
}
