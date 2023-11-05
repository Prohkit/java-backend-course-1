package edu.hw4;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnimalUtils {

    private AnimalUtils() {
    }

    public static List<Animal> sortByHeightAsc(List<Animal> animals) {
        return animals.stream().sorted(Comparator.comparing(Animal::height)).toList();
    }

    public static List<Animal> getTheFirstKAnimalsSortedByWeightDesc(List<Animal> animals, int k) {
        return animals.stream().sorted(Comparator.comparing(Animal::weight, Comparator.reverseOrder())).limit(k)
            .toList();
    }

    public static Map<Animal.Type, Integer> getAnimalsCountOfEachType(List<Animal> animals) {
        return animals.stream().collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(value -> 1)));
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

    public static Animal getTheOldestAnimalKInRow(List<Animal> animals, int k) {
        return animals.stream().sorted(Comparator.comparing(Animal::age, Comparator.reverseOrder()))
            .skip(k - 1).findFirst().orElseThrow();
    }

    public static Optional<Animal> getTheHeaviestAnimalBelowKCentimeters(List<Animal> animals, int k) {
        return animals.stream().filter(animal -> animal.height() < k)
            .max(Comparator.comparing(Animal::weight));
    }

    public static Integer getTheSumOfPaws(List<Animal> animals) {
        return animals.stream().mapToInt(Animal::paws).sum();
    }

    public static List<Animal> getAnimalsWhosePawsCountAreNotEqualToAge(List<Animal> animals) {
        return animals.stream().filter(animal -> animal.paws() != animal.age()).toList();
    }

    @SuppressWarnings("MagicNumber")
    public static List<Animal> getAnimalsThatCanBitesAndWhoseHeightMoreThanOneHundredCentimeters(List<Animal> animals) {
        return animals.stream().filter(animal -> animal.bites() && animal.height() > 100).toList();
    }

    public static Integer howManyAnimalsWhoseWeightExceedsTheirHeight(List<Animal> animals) {
        return Math.toIntExact(animals.stream().filter(animal -> animal.weight() > animal.height()).count());
    }

    public static List<Animal> getAnimalsWhoseNamesHaveMoreThanTwoWords(List<Animal> animals) {
        return animals.stream().filter(animal -> Arrays.stream(animal.name().split(" ")).count() > 2).toList();
    }

    public static boolean isThereADogThatIsTallerThanKCentimeters(List<Animal> animals, int k) {
        return animals.stream().anyMatch(animal -> animal.type().equals(Animal.Type.DOG) && animal.height() > k);
    }

    public static Map<Animal.Type, Integer> getTotalWeightOfEachTypeThatAreFromKToLAge(
        List<Animal> animals, int k, int l
    ) {
        return animals.stream().filter(animal -> animal.age() >= k && animal.age() <= l)
            .collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(Animal::weight)));
    }

    public static List<Animal> getAnimalsSortedByTypeThenBySexThenByName(List<Animal> animals) {
        return animals.stream()
            .sorted(Comparator.comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name)).toList();
    }

    public static boolean doSpidersBiteMoreOftenThanDogs(List<Animal> animals) {
        int dogs = (int) animals.stream()
            .filter(animal -> animal.type() == Animal.Type.DOG)
            .count();
        int spiders = (int) animals.stream()
            .filter(animal -> animal.type() == Animal.Type.SPIDER)
            .count();
        if (dogs == 0 || spiders == 0) {
            return false;
        }
        int bitingDogs = (int) animals.stream()
            .filter(animal -> animal.type() == Animal.Type.DOG && animal.bites())
            .count();
        int bitingSpider = (int) animals.stream()
            .filter(animal -> animal.type() == Animal.Type.SPIDER && animal.bites())
            .count();
        return (double) bitingSpider / spiders > (double) bitingDogs / dogs;
    }

    @SafeVarargs
    public static Animal getTheHeaviestFishInMoreThanOneList(List<Animal>... animals) {
        return Stream.of(animals)
            .flatMap(List::stream)
            .filter(animal -> animal.type() == Animal.Type.FISH)
            .max(Comparator.comparing(Animal::weight)).orElse(null);
    }

    public static Map<String, Set<ValidationError>> getAnimalsWithValidationErrors(List<Animal> animals) {
        return animals.stream().collect(Collectors.toMap(Animal::name, ValidationError::validateAnimal));
    }

    public static Map<String, String> getAnimalsWithValidationErrorsMoreReadable(List<Animal> animals) {
        return animals.stream().collect(Collectors.toMap(Animal::name, animal -> {
            Set<ValidationError> errors = ValidationError.validateAnimal(animal);
            Set<String> errorTypes = new HashSet<>();
            for (ValidationError error : errors) {
                errorTypes.add(error.getType().toString());
            }
            return String.join(", ", errorTypes);
        }));
    }
}
