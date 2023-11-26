package edu.hw7.task3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockPersonDatabase implements PersonDatabase {
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final Map<Integer, Person> personMap;
    private final Map<String, List<Person>> personNames;
    private final Map<String, List<Person>> personAddresses;
    private final Map<String, List<Person>> personPhones;

    public ReadWriteLockPersonDatabase(
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
    public void add(Person person) {
        lock.writeLock().lock();
        try {
            personMap.put(person.getId(), person);
            personNames.computeIfAbsent(person.getName(), k -> new ArrayList<>()).add(person);
            personNames.computeIfAbsent(person.getAddress(), k -> new ArrayList<>()).add(person);
            personNames.computeIfAbsent(person.getPhoneNumber(), k -> new ArrayList<>()).add(person);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        lock.writeLock().lock();
        try {
            Person person = personMap.get(id);
            if (person != null) {
                personNames.get(person.getName()).remove(person);
                personAddresses.get(person.getAddress()).remove(person);
                personPhones.get(person.getPhoneNumber()).remove(person);
            }
            personMap.remove(id);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        lock.readLock().lock();
        try {
            return personNames.getOrDefault(name, new ArrayList<>());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        lock.readLock().lock();
        try {
            return personAddresses.getOrDefault(address, new ArrayList<>());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        lock.readLock().lock();
        try {
            return personPhones.getOrDefault(phone, new ArrayList<>());
        } finally {
            lock.readLock().unlock();
        }
    }
}
