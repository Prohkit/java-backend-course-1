package edu.hw7.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonDatabaseImpl implements PersonDatabase {
    private final Map<Integer, Person> personMap;
    private final Map<String, List<Person>> personNames;
    private final Map<String, List<Person>> personAddresses;
    private final Map<String, List<Person>> personPhones;

    public PersonDatabaseImpl() {
        this.personMap = new HashMap<>();
        this.personNames = new HashMap<>();
        this.personAddresses = new HashMap<>();
        this.personPhones = new HashMap<>();
    }

    @Override
    public synchronized void add(Person person) {
        personMap.put(person.id(), person);
        personNames.computeIfAbsent(person.name(), k -> new ArrayList<>()).add(person);
        personNames.computeIfAbsent(person.address(), k -> new ArrayList<>()).add(person);
        personNames.computeIfAbsent(person.phoneNumber(), k -> new ArrayList<>()).add(person);

    }

    @Override
    public synchronized void delete(int id) {
        Person person = personMap.get(id);
        if (person != null) {
            personNames.get(person.name()).remove(person);
            personAddresses.get(person.address()).remove(person);
            personPhones.get(person.phoneNumber()).remove(person);
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
