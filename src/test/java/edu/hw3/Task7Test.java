package edu.hw3;

import edu.hw3.task7.Task7Comparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.TreeMap;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {
    @Test
    @DisplayName("The method check that key in TreeMap can be null using its own comparator")
    void treeMapKeyIsNull() {
        // given
        TreeMap<String, String> tree = new TreeMap<>(new Task7Comparator());

        // when
        tree.put(null, "test");

        // then
        assertThat(tree.containsKey(null)).isTrue();
    }
}
