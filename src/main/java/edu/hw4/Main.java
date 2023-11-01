package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Animal> animals = getAnimals();

        List<Animal> task1 = sortByHeightAsc(animals);
        List<Animal> task2 = sortByWeightDesc(animals, new Random().nextInt(6));
        Map<Animal.Type, Long> task3 = getAnimalsCountOfEachType(animals);
        Animal task4 = getAnimalWithTheLongestName(animals);
        Animal.Sex task5 = getDominantGender(animals);
        Map<Animal.Type, Animal> Task6 = getTheHeaviestAnimalOfEachType(animals);
        // k-е самое старое животное, начиная с нуля
        Animal task7 = getTheOldestAnimal(animals, new Random().nextInt(6));

    }

    public static List<Animal> getAnimals() {
        return Stream.of(new Animal[] {
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 65, 21, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 30, 3, true),
            new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 1, 30, 9, true),
            new Animal("Fish", Animal.Type.FISH, Animal.Sex.M, 2, 15, 3, false),
            new Animal("Spider", Animal.Type.SPIDER, Animal.Sex.F, 5, 2, 1, true)
        }).toList();
    }

    public static List<Animal> sortByHeightAsc(List<Animal> animals) {
        return animals.stream().sorted(Comparator.comparing(Animal::height)).toList();
    }

    public static List<Animal> sortByWeightDesc(List<Animal> animals, int k) {
        return animals.stream().sorted(Comparator.comparing(Animal::weight, Comparator.reverseOrder())).limit(k)
            .toList();
    }

    public static Map<Animal.Type, Long> getAnimalsCountOfEachType(List<Animal> animals) {
        return animals.stream().collect(Collectors.groupingBy(Animal::type, Collectors.counting()));
    }

    public static Animal getAnimalWithTheLongestName(List<Animal> animals) {
        return animals.stream().max(Comparator.comparing(Animal::name)).orElseThrow();
    }

    public static Animal.Sex getDominantGender(List<Animal> animals) {
        return animals.stream().collect(Collectors.groupingBy(Animal::sex, Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .orElseThrow().getKey();
    }

    public static Map<Animal.Type, Animal> getTheHeaviestAnimalOfEachType(List<Animal> animals) {
        return animals.stream().collect(Collectors
            .groupingBy(Animal::type, Collectors.collectingAndThen(Collectors
                .reducing(BinaryOperator.maxBy(Comparator.comparingInt(Animal::weight))), Optional::orElseThrow)));
    }

    public static Animal getTheOldestAnimal(List<Animal> animals, int k) {
        return animals.stream().sorted(Comparator.comparing(Animal::age, Comparator.reverseOrder()))
            .skip(k).findFirst().orElseThrow();
    }
}
