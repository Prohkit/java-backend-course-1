package edu.hw11;

import edu.hw11.task2.ArithmeticUtils;
import edu.hw11.task2.Redefine;
import edu.hw11.task3.Fibonacci;
import java.lang.reflect.Method;
import java.util.Arrays;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Tests {
    @Test
    void task1() throws Exception {
        Class<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .method(named("toString")).intercept(FixedValue.value("Hello, ByteBuddy!"))
            .make()
            .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();

        assertThat(dynamicType.getDeclaredConstructor().newInstance().toString())
            .isEqualTo("Hello, ByteBuddy!");
    }

    @Test
    void task2() {
        ByteBuddyAgent.install();
        new ByteBuddy()
            .redefine(ArithmeticUtils.class)
            .method(named("sum")
                .and(isDeclaredBy(ArithmeticUtils.class))
                .and(returns(int.class)))
            .intercept(MethodDelegation.to(Redefine.class))
            .make()
            .load(
                ArithmeticUtils.class.getClassLoader(),
                ClassReloadingStrategy.fromInstalledAgent()
            );

        assertThat(ArithmeticUtils.sum(5, 5))
            .isEqualTo(25);
    }

    @Test
    void task3() {
        Class<?> dynamicType = new ByteBuddy()
            .subclass(Object.class)
            .name("Fibonacci")
            .defineMethod("fib", long.class, Visibility.PUBLIC)
            .withParameter(int.class, "n")
            .intercept(MethodDelegation.to(Fibonacci.class))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded();
        try {
            Object instance = dynamicType.getDeclaredConstructor().newInstance();
            Method fib = findMethod(dynamicType, "fib");
            long result = (long) fib.invoke(instance, 15);
            assertThat(result)
                .isEqualTo(610);
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    private Method findMethod(Class<?> clazz, String methodName) {
        return Arrays.stream(clazz.getDeclaredMethods())
            .filter(method -> method.getName().equals(methodName))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
