package edu.hw4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import static edu.hw4.AnimalUtils.doSpidersBiteMoreOftenThanDogs;
import static edu.hw4.AnimalUtils.getAnimalWithTheLongestName;
import static edu.hw4.AnimalUtils.getAnimalsCountOfEachType;
import static edu.hw4.AnimalUtils.getAnimalsSortedByTypeThenBySexThenByName;
import static edu.hw4.AnimalUtils.getAnimalsThatCanBitesAndWhoseHeightMoreThanOneHundredCentimeters;
import static edu.hw4.AnimalUtils.getAnimalsWhoseNamesHaveMoreThanTwoWords;
import static edu.hw4.AnimalUtils.getAnimalsWhosePawsCountAreNotEqualToAge;
import static edu.hw4.AnimalUtils.getAnimalsWithValidationErrors;
import static edu.hw4.AnimalUtils.getAnimalsWithValidationErrorsMoreReadable;
import static edu.hw4.AnimalUtils.getDominantGender;
import static edu.hw4.AnimalUtils.getTheFirstKAnimalsSortedByWeightDesc;
import static edu.hw4.AnimalUtils.getTheHeaviestAnimalBelowKCentimeters;
import static edu.hw4.AnimalUtils.getTheHeaviestAnimalOfEachType;
import static edu.hw4.AnimalUtils.getTheHeaviestFishInMoreThanOneList;
import static edu.hw4.AnimalUtils.getTheOldestAnimalKInRow;
import static edu.hw4.AnimalUtils.getTheSumOfPaws;
import static edu.hw4.AnimalUtils.getTotalWeightOfEachTypeThatAreFromKToLAge;
import static edu.hw4.AnimalUtils.howManyAnimalsWhoseWeightExceedsTheirHeight;
import static edu.hw4.AnimalUtils.isThereADogThatIsTallerThanKCentimeters;
import static edu.hw4.AnimalUtils.sortByHeightAsc;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Tests {
    @Test
    void task1SortByHeightAsc() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("BigDod", Animal.Type.DOG, Animal.Sex.M, 4, 102, 32, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 30, 3, true),
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true)
        ).toList();

        List<Animal> expected = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 30, 3, true),
            new Animal("BigDod", Animal.Type.DOG, Animal.Sex.M, 4, 102, 32, true)
        ).toList();

        // when
        List<Animal> result = sortByHeightAsc(animals);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task2GetTheFirstKAnimalsSortedByWeightDesc() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("BigDod", Animal.Type.DOG, Animal.Sex.M, 4, 102, 32, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 30, 3, true),
            new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 1, 30, 9, true)
        ).toList();
        int k = 2;

        List<Animal> expected = Stream.of(
            new Animal("BigDod", Animal.Type.DOG, Animal.Sex.M, 4, 102, 32, true),
            new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 1, 30, 9, true)
        ).toList();

        // when
        List<Animal> result = getTheFirstKAnimalsSortedByWeightDesc(animals, k);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task3GetAnimalsCountOfEachType() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 65, 21, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 30, 3, true)
        ).toList();

        Map<Animal.Type, Integer> expected = new HashMap<>();
        expected.put(Animal.Type.DOG, 2);
        expected.put(Animal.Type.CAT, 1);

        // when
        Map<Animal.Type, Integer> result = getAnimalsCountOfEachType(animals);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task4GetAnimalWithTheLongestName() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 65, 21, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 30, 3, true)
        ).toList();

        Animal expected = new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 30, 3, true);

        // when
        Animal result = getAnimalWithTheLongestName(animals);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task5GetDominantGender() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 65, 21, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 30, 3, true)
        ).toList();

        Animal.Sex expected = Animal.Sex.M;

        // when
        Animal.Sex result = getDominantGender(animals);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task6GetTheHeaviestAnimalOfEachType() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Second Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 3, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 65, 21, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 30, 3, true),
            new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 1, 30, 9, true)
        ).toList();

        Map<Animal.Type, Animal> expected = new HashMap<>();
        expected.put(Animal.Type.CAT, new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true));
        expected.put(Animal.Type.DOG, new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 65, 21, true));
        expected.put(Animal.Type.BIRD, new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 1, 30, 9, true));

        // when
        Map<Animal.Type, Animal> result = getTheHeaviestAnimalOfEachType(animals);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task7GetTheOldestAnimalKInRow() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Second Cat", Animal.Type.CAT, Animal.Sex.F, 2, 20, 3, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 65, 21, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 30, 3, true),
            new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 1, 30, 9, true)
        ).toList();
        int k = 2;

        Animal expected = new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true);

        // when
        Animal result = getTheOldestAnimalKInRow(animals, k);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task8GetTheHeaviestAnimalBelowKCentimeters() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Second Cat", Animal.Type.CAT, Animal.Sex.F, 2, 20, 3, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 65, 21, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 25, 3, true),
            new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 1, 30, 9, true)
        ).toList();
        int k = 28;

        Optional<Animal> expected = Optional.of(new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true));

        // when
        Optional<Animal> result = getTheHeaviestAnimalBelowKCentimeters(animals, k);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task9GetTheSumOfPaws() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Second Cat", Animal.Type.CAT, Animal.Sex.F, 2, 20, 3, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 65, 21, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 25, 3, true),
            new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 1, 30, 9, true)
        ).toList();

        Integer expected = 18;

        // when
        Integer result = getTheSumOfPaws(animals);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task10GetAnimalsWhosePawsCountAreNotEqualToAge() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Second Cat", Animal.Type.CAT, Animal.Sex.F, 2, 20, 3, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 65, 21, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 25, 3, true),
            new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 2, 30, 9, true)
        ).toList();

        List<Animal> expected = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Second Cat", Animal.Type.CAT, Animal.Sex.F, 2, 20, 3, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 25, 3, true)
        ).toList();

        // when
        List<Animal> result = getAnimalsWhosePawsCountAreNotEqualToAge(animals);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task11GetAnimalsThatCanBitesAndWhoseHeightMoreThanOneHundredCentimeters() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Second Cat", Animal.Type.CAT, Animal.Sex.F, 2, 20, 3, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 101, 21, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 25, 3, true),
            new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 2, 110, 9, true)
        ).toList();

        List<Animal> expected = Stream.of(
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 101, 21, true),
            new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 2, 110, 9, true)
        ).toList();

        // when
        List<Animal> result = getAnimalsThatCanBitesAndWhoseHeightMoreThanOneHundredCentimeters(animals);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task12HowManyAnimalsWhoseWeightExceedsTheirHeight() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Second Cat", Animal.Type.CAT, Animal.Sex.F, 2, 20, 3, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 65, 21, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 25, 3, true),
            new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 2, 30, 31, true),
            new Animal("Fish", Animal.Type.FISH, Animal.Sex.M, 2, 15, 20, false)
        ).toList();

        Integer expected = 2;

        // when
        Integer result = howManyAnimalsWhoseWeightExceedsTheirHeight(animals);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task13GetAnimalsWhoseNamesHaveMoreThanTwoWords() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Second Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 3, true),
            new Animal("three words Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true)
        ).toList();

        List<Animal> expected = Stream.of(
            new Animal("three words Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true)
        ).toList();

        // when
        List<Animal> result = getAnimalsWhoseNamesHaveMoreThanTwoWords(animals);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task14IsThereADogThatIsTallerThanKCentimeters() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Second Cat", Animal.Type.CAT, Animal.Sex.F, 2, 20, 3, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 4, 65, 21, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 25, 3, true),
            new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 2, 30, 9, true)
        ).toList();
        int k = 26;

        // when
        boolean result = isThereADogThatIsTallerThanKCentimeters(animals, k);

        // then
        assertThat(result)
            .isTrue();
    }

    @Test
    void task15GetTotalWeightOfEachTypeThatAreFromKToLAge() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Second Cat", Animal.Type.CAT, Animal.Sex.F, 2, 20, 3, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 3, 65, 21, true),
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 25, 3, true),
            new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 2, 30, 9, true)
        ).toList();
        int k = 2;
        int l = 4;

        Map<Animal.Type, Integer> expected = new HashMap<>();
        expected.put(Animal.Type.DOG, 21);
        expected.put(Animal.Type.CAT, 7);
        expected.put(Animal.Type.BIRD, 9);

        // when
        Map<Animal.Type, Integer> result = getTotalWeightOfEachTypeThatAreFromKToLAge(animals, k, l);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task16GetAnimalsSortedByTypeThenBySexThenByName() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 2, 30, 9, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 3, 65, 21, true),
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true)
        ).toList();

        List<Animal> expected = Stream.of(
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, 20, 4, true),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 3, 65, 21, true),
            new Animal("Turkey", Animal.Type.BIRD, Animal.Sex.M, 2, 30, 9, true)
        ).toList();

        // when
        List<Animal> result = getAnimalsSortedByTypeThenBySexThenByName(animals);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task17DoSpidersBiteMoreOftenThanDogs() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("Puppy", Animal.Type.DOG, Animal.Sex.M, 1, 30, 3, false),
            new Animal("Dog", Animal.Type.DOG, Animal.Sex.M, 3, 65, 21, true),
            new Animal("Spider", Animal.Type.SPIDER, Animal.Sex.F, 5, 2, 1, true),
            new Animal("Spider", Animal.Type.SPIDER, Animal.Sex.F, 5, 2, 1, true),
            new Animal("Spider", Animal.Type.SPIDER, Animal.Sex.F, 5, 2, 1, false)
        ).toList();

        // when
        boolean result = doSpidersBiteMoreOftenThanDogs(animals);

        // then
        // two out of three spiders bite, one out of two dogs bites
        assertThat(result)
            .isTrue();
    }

    @Test
    void task18GetTheHeaviestFishInMoreThanOneList() {
        // given
        List<Animal> firstFishList = Stream.of(
            new Animal("Fish", Animal.Type.FISH, Animal.Sex.M, 2, 15, 3, false),
            new Animal("AnotherFish", Animal.Type.FISH, Animal.Sex.M, 2, 50, 25, false)
        ).toList();

        List<Animal> secondFishList = Stream.of(
            new Animal("Fish", Animal.Type.FISH, Animal.Sex.M, 2, 30, 9, false),
            new Animal("FishOfTheDream", Animal.Type.FISH, Animal.Sex.M, 2, 80, 50, false)
        ).toList();

        Animal expected = new Animal("FishOfTheDream", Animal.Type.FISH, Animal.Sex.M, 2, 80, 50, false);

        // when
        Animal result = getTheHeaviestFishInMoreThanOneList(firstFishList, secondFishList);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task19GetAnimalsWithValidationErrors() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("turkey", Animal.Type.BIRD, Animal.Sex.M, -1, 30, 9, true),
            new Animal("Dog", null, Animal.Sex.M, 3, 65, 21, true),
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, -2, 4, true)
        ).toList();

        Map<String, Set<ValidationError>> expected = new HashMap<>();
        Set<ValidationError> forTurkey = new HashSet<>();
        forTurkey.add(new ValidationError("Age cannot be negative", ValidationError.ValidationType.AGE));
        forTurkey.add(new ValidationError(
            "The name must begin with a capital letter",
            ValidationError.ValidationType.NAME
        ));

        Set<ValidationError> forCat = new HashSet<>();
        forCat.add(new ValidationError(
            "Height cannot be less than zero or equal to zero",
            ValidationError.ValidationType.HEIGHT
        ));

        Set<ValidationError> forDog = new HashSet<>();
        forDog.add(new ValidationError("Type cannot be null", ValidationError.ValidationType.TYPE));

        expected.put("turkey", forTurkey);
        expected.put("Dog", forDog);
        expected.put("Cat", forCat);

        // when
        Map<String, Set<ValidationError>> result = getAnimalsWithValidationErrors(animals);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }

    @Test
    void task20GetAnimalsWithValidationErrorsMoreReadable() {
        // given
        List<Animal> animals = Stream.of(
            new Animal("turkey", Animal.Type.BIRD, Animal.Sex.M, 3, 30, 9, true),
            new Animal("Dog", null, Animal.Sex.M, 3, 65, 21, true),
            new Animal("Cat", Animal.Type.CAT, Animal.Sex.F, 3, -2, 4, true)
        ).toList();

        Map<String, String> expected = new HashMap<>();
        expected.put("turkey", "NAME");
        expected.put("Dog", "TYPE");
        expected.put("Cat", "HEIGHT");

        // when
        Map<String, String> result = getAnimalsWithValidationErrorsMoreReadable(animals);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }
}
