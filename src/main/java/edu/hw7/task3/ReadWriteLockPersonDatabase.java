package edu.hw7.task3;

import java.util.ArrayList;
import java.util.HashMap;
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

    public ReadWriteLockPersonDatabase() {
        this.personMap = new HashMap<>();
        this.personNames = new HashMap<>();
        this.personAddresses = new HashMap<>();
        this.personPhones = new HashMap<>();
    }

    @Override
    public void add(Person person) {
        lock.writeLock().lock();
        try {
            personMap.put(person.id(), person);
            personNames.computeIfAbsent(person.name(), k -> new ArrayList<>()).add(person);
            personNames.computeIfAbsent(person.address(), k -> new ArrayList<>()).add(person);
            personNames.computeIfAbsent(person.phoneNumber(), k -> new ArrayList<>()).add(person);
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
                personNames.get(person.name()).remove(person);
                personAddresses.get(person.address()).remove(person);
                personPhones.get(person.phoneNumber()).remove(person);
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
