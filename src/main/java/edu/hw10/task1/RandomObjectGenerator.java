package edu.hw10.task1;

import edu.hw10.task1.annotation.Max;
import edu.hw10.task1.annotation.Min;
import edu.hw10.task1.annotation.NotNull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class RandomObjectGenerator implements ObjectGenerator {
    private final Random random;

    public RandomObjectGenerator() {
        random = new Random();
    }

    @Override
    public <T> T nextObject(Class<T> passedClass, String fabricMethodName) {
        if (fabricMethodName != null && !fabricMethodName.isEmpty()) {
            Method fabricMethod = findMethod(passedClass, fabricMethodName);
            String[] parameterNames = Arrays.stream(fabricMethod.getParameters())
                .map(Parameter::getName)
                .toArray(String[]::new);
            Field[] fields = getFieldsFromParameterNames(passedClass, parameterNames);
            Object[] randomParameters = Arrays.stream(fields)
                .map(this::getRandomParameter)
                .toArray();
            try {
                return (T) fabricMethod.invoke(null, randomParameters);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        } else {
            return nextObject(passedClass);
        }
    }

    @Override
    public <T> T nextObject(Class<T> passedClass) {
        Constructor<?> constructor = getConstructor(passedClass);
        String[] parameterNames = Arrays.stream(constructor.getParameters())
            .map(Parameter::getName)
            .toArray(String[]::new);
        Field[] fields = getFieldsFromParameterNames(passedClass, parameterNames);
        Object[] randomParameters = Arrays.stream(fields)
            .map(this::getRandomParameter)
            .toArray();
        try {
            return (T) constructor.newInstance(randomParameters);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private Field[] getFieldsFromParameterNames(Class<?> passedClass, String[] parameterNames) {
        Field[] fields = new Field[parameterNames.length];
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i] = passedClass.getDeclaredField(parameterNames[i]);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException();
            }
        }
        return fields;
    }

    private Method findMethod(Class<?> clazz, String methodName) {
        return Arrays.stream(clazz.getDeclaredMethods())
            .filter(method -> method.getName().equals(methodName))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }

    private Constructor<?> getConstructor(Class<?> passedClass) {
        return Arrays.stream(passedClass.getDeclaredConstructors())
            .max(Comparator.comparing(Constructor::getParameterCount)).orElseThrow();
    }

    private Object getRandomParameter(Field field) {
        int originForNumericTypes =
            field.isAnnotationPresent(Min.class) ? field.getAnnotation(Min.class).value() : Integer.MIN_VALUE;
        int boundForNumericTypes =
            field.isAnnotationPresent(Max.class) ? field.getAnnotation(Max.class).value() : Integer.MAX_VALUE;
        boolean notNull = field.isAnnotationPresent(NotNull.class);
        Class<?> fieldType = field.getType();

        return generateRandomParameter(fieldType, originForNumericTypes, boundForNumericTypes, notNull);
    }

    @SuppressWarnings("MagicNumber")
    private Object generateRandomParameter(
        Class<?> fieldType,
        int originForNumericTypes,
        int boundForNumericTypes,
        boolean notNull
    ) {
        Object result = null;
        boolean isTypeNumeric =
            fieldType.equals(int.class) || fieldType.equals(Integer.class)
                || fieldType.equals(long.class) || fieldType.equals(Long.class)
                || fieldType.equals(double.class) || fieldType.equals(Double.class);

        if (isTypeNumeric) {
            result = generateRandomNumeric(fieldType, originForNumericTypes, boundForNumericTypes);
        }
        if (fieldType.equals(String.class)) {
            int originForRandomStringLength = 0;
            int boundForRandomStringLength = 10;
            if (notNull) {
                originForRandomStringLength = 1;
            }
            result = generateRandomString(random.nextInt(originForRandomStringLength, boundForRandomStringLength));
        } else if (fieldType.equals(char.class) || fieldType.equals(Character.class)) {
            int charCodeOrigin = 97;
            int charCodeBound = 122;
            result = random.nextInt(charCodeOrigin, charCodeBound);
        } else if (fieldType.equals(byte.class) || fieldType.equals(Byte.class)) {
            int byteIntervalOrigin = -128;
            int byteIntervalBound = -128;

            result = (byte) random.nextInt(byteIntervalOrigin, byteIntervalBound);
        } else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
            result = random.nextBoolean();
        }
        return result;
    }

    @SuppressWarnings("MagicNumber")
    private Object generateRandomNumeric(Class<?> fieldType, int originForNumericTypes, int boundForNumericTypes) {
        Object result = null;
        if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
            result = random.nextInt(originForNumericTypes, boundForNumericTypes);
        } else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
            result = random.nextLong(originForNumericTypes, boundForNumericTypes);
        } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
            result = random.nextDouble(originForNumericTypes, boundForNumericTypes);
        }
        return result;
    }

    @SuppressWarnings("MagicNumber")
    private String generateRandomString(int length) {
        int leftLimit = 97;
        int rightLimit = 122;

        return random.ints(leftLimit, rightLimit + 1)
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }
}
