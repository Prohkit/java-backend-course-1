package edu.hw10.task1;

import edu.hw10.task1.annotation.NotNull;

public class Student {
    private String name;
    private String lastName;

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, @NotNull String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }
}
