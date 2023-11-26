package edu.hw7;

import edu.hw7.task3.Person;
import edu.hw7.task3.PersonDatabaseImpl;
import java.util.List;
import edu.hw7.task3.ReadWriteLockPersonDatabase;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {

    @Test
    void addPersonTest() {
        PersonDatabaseImpl personDatabase = new PersonDatabaseImpl();

        List<Person> personList = List.of(
            new Person(1, "Julia", "Lenina", "987654321"),
            new Person(2, "Igor", "Chiolkovskogo", "123456789"),
            new Person(3, "Egor", "Gagarina", "192837465")
        );

        Thread first = new Thread(() -> personList.forEach(personDatabase::add));
        Thread second = new Thread(() -> personList.forEach(
            person -> {
                synchronized (personDatabase) {
                    List<Person> names = personDatabase.findByName(person.name());
                    List<Person> addresses = personDatabase.findByAddress(person.address());
                    List<Person> phones = personDatabase.findByPhone(person.phoneNumber());

                    assertThat(names).containsExactlyInAnyOrderElementsOf(addresses);
                    assertThat(addresses).containsExactlyInAnyOrderElementsOf(phones);
                }
            }));

        first.start();
        second.start();

        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    @Test
    void ReadWriteLockTest() {
        ReadWriteLockPersonDatabase personDatabase = new ReadWriteLockPersonDatabase();

        List<Person> personList = List.of(
            new Person(1, "Julia", "Lenina", "987654321"),
            new Person(2, "Igor", "Chiolkovskogo", "123456789"),
            new Person(3, "Egor", "Gagarina", "192837465")
        );

        Thread first = new Thread(() -> personList.forEach(personDatabase::add));
        Thread second = new Thread(() -> personList.forEach(
            person -> {
                synchronized (personDatabase) {
                    List<Person> names = personDatabase.findByName(person.name());
                    List<Person> addresses = personDatabase.findByAddress(person.address());
                    List<Person> phones = personDatabase.findByPhone(person.phoneNumber());

                    assertThat(names).containsExactlyInAnyOrderElementsOf(addresses);
                    assertThat(addresses).containsExactlyInAnyOrderElementsOf(phones);
                }
            }));

        first.start();
        second.start();

        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
