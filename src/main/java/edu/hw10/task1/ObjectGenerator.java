package edu.hw10.task1;

public interface ObjectGenerator {
    <T> T nextObject(Class<T> passedClass, String fabricMethod);

    <T> T nextObject(Class<T> passedClass);
}
