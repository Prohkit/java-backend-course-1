package edu.hw3.task5;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class Contact implements Comparable<Contact> {
    private String lastName;
    private final String firstName;

    public Contact(String lastName, String firstName) {
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Contact(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return Objects.equals(lastName, contact.lastName) && Objects.equals(firstName, contact.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName);
    }

    @Override
    public int compareTo(@NotNull Contact contact) {
        String first = lastName;
        String second = contact.lastName;

        if (first.isEmpty()) {
            first = firstName;
        }
        if (second.isEmpty()) {
            second = contact.firstName;
        }

        return first.compareTo(second);
    }
}
