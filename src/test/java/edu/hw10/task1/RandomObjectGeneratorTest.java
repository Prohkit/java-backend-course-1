package edu.hw10.task1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomObjectGeneratorTest {
    @Test
    void test() {
        RandomObjectGenerator rog = new RandomObjectGenerator();
        var student = rog.nextObject(Student.class);
        var teacher = rog.nextObject(Teacher.class);
        var teacherCreatedByFabricMethod = rog.nextObject(Teacher.class, "create");

        assertThat(student)
            .isInstanceOf(Student.class);
        assertThat(teacher)
            .isInstanceOf(Teacher.class);
        assertEquals(1000L, teacherCreatedByFabricMethod.id());
        assertNotNull(teacher.lastName());
        assertTrue(teacher.min() > 100 && teacher.max() < 500);
    }
}
