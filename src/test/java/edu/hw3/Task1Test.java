package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    static Arguments[] source() {
        return new Arguments[] {
            Arguments.of(
                "Hello world!",
                "Svool dliow!"
            ),
            Arguments.of(
                "Any fool can write code that a computer can understand. Good programmers write code that"
                    + " humans can understand. ― Martin Fowler",
                "Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm "
                    + "fmwvihgzmw. ― Nzigrm Uldovi")
        };
    }

    @ParameterizedTest
    @MethodSource("source")
    @DisplayName("Encrypt the sentence")
    void encryptTheSentence(String sentenceToEncrypt, String expectedOffer) {
        // when
        String encryptedOffer = Task1.atbash(sentenceToEncrypt);

        // then
        assertThat(encryptedOffer)
            .isEqualTo(expectedOffer);
    }
}
