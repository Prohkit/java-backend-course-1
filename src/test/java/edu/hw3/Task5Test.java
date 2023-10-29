package edu.hw3;

import edu.hw3.task5.Contact;
import edu.hw3.task5.Task5;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class Task5Test {
    static Arguments[] source() {
        return new Arguments[] {
            Arguments.of(
                new String[] {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"},
                "ASC",
                List.of(
                    new Contact("Aquinas", "Thomas"),
                    new Contact("Descartes", "Rene"),
                    new Contact("Hume", "David"),
                    new Contact("Locke", "John")
                )
            ),

            Arguments.of(
                new String[] {"Paul Erdos", "Leonhard Euler", "Carl Gauss"},
                "DESC",
                List.of(
                    new Contact("Gauss", "Carl"),
                    new Contact("Euler", "Leonhard"),
                    new Contact("Erdos", "Paul")
                )
            ),

            Arguments.of(new String[] {}, "DESC", List.of()),
            Arguments.of(null, "DESC", List.of())
        };
    }

    @ParameterizedTest
    @MethodSource("source")
    @DisplayName("The method returns the sorted contacts list")
    void returnsTheSortedContactsList(String[] names, String sortingOrder, List<Contact> expectedContactList) {
        // when
        List<Contact> contactList = Task5.parseContacts(names, sortingOrder);

        // then
        assertThat(contactList)
            .isEqualTo(expectedContactList);
    }

    @Test
    @DisplayName("The method throw an exception, when sorting order is incorrect")
    void incorrectSortingOrderThrowsAnError() {
        // given
        String[] names = {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"};
        String sortingOrder = "incorrect sorting order";

        // then
        assertThatThrownBy(() -> Task5.parseContacts(names, sortingOrder))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The sort order is incorrect");
    }
}
