package edu.hw7.task3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersonDatabaseImpl implements PersonDatabase {
    private final Map<Integer, Person> personMap;
    private final Map<String, List<Person>> personNames;
    private final Map<String, List<Person>> personAddresses;
    private final Map<String, List<Person>> personPhones;

    public PersonDatabaseImpl(
        Map<Integer, Person> personMap,
        Map<String, List<Person>> personNames,
        Map<String, List<Person>> personAddresses,
        Map<String, List<Person>> personPhones
    ) {
        this.personMap = personMap;
        this.personNames = personNames;
        this.personAddresses = personAddresses;
        this.personPhones = personPhones;
    }

    @Override
    public synchronized void add(Person person) {
        personMap.put(person.getId(), person);
        personNames.computeIfAbsent(person.getName(), k -> new ArrayList<>()).add(person);
        personNames.computeIfAbsent(person.getAddress(), k -> new ArrayList<>()).add(person);
        personNames.computeIfAbsent(person.getPhoneNumber(), k -> new ArrayList<>()).add(person);

    }

    @Override
    public synchronized void delete(int id) {
        Person person = personMap.get(id);
        if (person != null) {
            personNames.get(person.getName()).remove(person);
            personAddresses.get(person.getAddress()).remove(person);
            personPhones.get(person.getPhoneNumber()).remove(person);
        }
        personMap.remove(id);
    }

    @Override
    public synchronized List<Person> findByName(String name) {
        return personNames.getOrDefault(name, new ArrayList<>());
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        return personAddresses.getOrDefault(address, new ArrayList<>());
    }

    @Override
    public synchronized List<Person> findByPhone(String phone) {
        return personPhones.getOrDefault(phone, new ArrayList<>());
    }
}
