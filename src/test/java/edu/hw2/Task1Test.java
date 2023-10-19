package edu.hw2;
import edu.hw1.EvenArrayUtils;
import edu.hw2.task1.Addition;
import edu.hw2.task1.Constant;
import edu.hw2.task1.Exponent;
import edu.hw2.task1.Multiplication;
import edu.hw2.task1.Negate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @Test
    @DisplayName("Полиморфизм")
    void calculate() {
        // given
        var two = new Constant(2);
        var four = new Constant(4);

        // when
        var negOne = new Negate(new Constant(1));
        var sumTwoFour = new Addition(two, four);
        var mult = new Multiplication(sumTwoFour, negOne);
        var exp = new Exponent(mult, 2);
        var res = new Addition(exp, new Constant(1));

        // then
        assertThat(negOne.evaluate())
            .isEqualTo(-1);

        assertThat(sumTwoFour.evaluate())
            .isEqualTo(2 + 4);

        assertThat(mult.evaluate())
            .isEqualTo(-6);

        assertThat(exp.evaluate())
            .isEqualTo(36);

        assertThat(res.evaluate())
            .isEqualTo(37);
    }
}
