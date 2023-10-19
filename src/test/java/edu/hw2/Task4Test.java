package edu.hw2;

import edu.hw2.task4.CallingInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @Test
    @DisplayName("Возвращает текущий класс и метод")
    void getInfo() {
        // given
        CallingInfo exceptedCallingInfo = new CallingInfo("edu.hw2.Task4Test", "getInfo");

        // when
        CallingInfo callingInfo = CallingInfo.callingInfo();

        // then
        assertThat(callingInfo)
            .isEqualTo(exceptedCallingInfo);
    }
}
