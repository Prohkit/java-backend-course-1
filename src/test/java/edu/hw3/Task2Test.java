package edu.hw3;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class Task2Test {
    static Arguments[] source() {
        return new Arguments[] {
            Arguments.of("()()()", List.of("()", "()", "()")),
            Arguments.of("((()))", List.of("((()))")),
            Arguments.of("((()))(())()()(()())", List.of("((()))", "(())", "()", "()", "(()())")),
            Arguments.of("((())())(()(()()))", List.of("((())())", "(()(()()))")),
        };
    }

    @ParameterizedTest
    @MethodSource("source")
    @DisplayName("A bracket sequence clusters correct")
    void clusteringABracketSequence(String bracketSequence, List<String> expectedCluster) {
        // when
        List<String> cluster = Task2.clusterize(bracketSequence);

        // then
        assertThat(cluster)
            .isEqualTo(expectedCluster);
    }

    @Test
    @DisplayName("If a bracket sequence is incorrect returns exception")
    void returnsException() {
        // given
        String bracketSequence = "()()(()";

        // then
        assertThatThrownBy(() -> Task2.clusterize(bracketSequence))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Sequence of brackets is incorrect");
    }
}
