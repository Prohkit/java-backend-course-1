package edu.hw3;

import edu.hw3.task8.BackwardIterator;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task8Test {
    @Test
    @DisplayName("A backward iterator iterate from end to begin")
    void backwardIteratorIterateFromEndToBegin() {
        // given
        BackwardIterator<Integer> iterator = new BackwardIterator<>(List.of(1, 2, 3));
        List<Integer> expectedList = List.of(3, 2, 1);
        List<Integer> list = new ArrayList<>();

        // when
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }

        // then
        assertThat(list)
            .isEqualTo(expectedList);
    }
}
