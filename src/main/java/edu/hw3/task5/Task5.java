package edu.hw3.task5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Task5 {
    private Task5() {
    }

    public static List<Contact> parseContacts(String[] names, String sortingOrder) {
        if (names == null) {
            return new ArrayList<>();
        }
        List<Contact> contacts = getContacts(names);
        return sortContacts(contacts, sortingOrder);
    }

    private static List<Contact> getContacts(String[] names) {
        List<Contact> contacts = new ArrayList<>();
        for (String name : names) {
            if (name.contains(" ")) {
                String[] splitName = name.split(" ");
                Contact contact = new Contact(splitName[1], splitName[0]);
                contacts.add(contact);
            } else {
                contacts.add(new Contact(name));
            }
        }
        return contacts;
    }

    private static List<Contact> sortContacts(List<Contact> contacts, String sortingOrder) {
        switch (sortingOrder) {
            case "ASC" -> contacts.sort(Contact::compareTo);
            case "DESC" -> contacts.sort(Collections.reverseOrder());
            default -> throw new IllegalArgumentException("The sort order is incorrect");
        }
        return contacts;
    }
}
