package edu.hw4;

import edu.hw4.Animal.Sex;
import edu.hw4.Animal.Type;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ValidationError {
    private final static Set<ValidationError> ERRORS = new HashSet<>();

    private final String message;

    private final ValidationType type;

    public ValidationType getType() {
        return type;
    }

    enum ValidationType {
        NAME,
        AGE,
        TYPE,
        SEX,
        HEIGHT,
        WEIGHT
    }

    public ValidationError(String message, ValidationType type) {
        this.message = message;
        this.type = type;
    }

    public static Set<ValidationError> validateAnimal(Animal animal) {
        validateName(animal.name());
        validateAge(animal.age());
        validateSex(animal.sex());
        validateType(animal.type());
        validateHeight(animal.height());
        validateWeight(animal.weight());
        Set<ValidationError> result = new HashSet<>(ERRORS);
        ERRORS.clear();
        return result;
    }

    private static void validateName(String name) {
        String[] namePaths = name.split(" ");
        for (String namePath : namePaths) {
            if (!Character.isUpperCase(namePath.charAt(0))) {
                ERRORS.add(new ValidationError("The name must begin with a capital letter", ValidationType.NAME));
                break;
            }
        }
    }

    private static void validateAge(int age) {
        if (age < 0) {
            ERRORS.add(new ValidationError("Age cannot be negative", ValidationType.AGE));
        }
    }

    private static void validateType(Type type) {
        if (type == null) {
            ERRORS.add(new ValidationError("Type cannot be null", ValidationType.TYPE));
        }
    }

    private static void validateSex(Sex sex) {
        if (sex == null) {
            ERRORS.add(new ValidationError("Sex cannot be null", ValidationType.SEX));
        }
    }

    private static void validateHeight(int height) {
        if (height <= 0) {
            ERRORS.add(new ValidationError("Height cannot be less than zero or equal to zero", ValidationType.HEIGHT));
        }
    }

    private static void validateWeight(int weight) {
        if (weight <= 0) {
            ERRORS.add(new ValidationError("Weight cannot be less than zero or equal to zero", ValidationType.WEIGHT));
        }
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ValidationError that = (ValidationError) o;
        return Objects.equals(message, that.message) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, type);
    }
}
