package io.qala.datagen.examples;

import org.apache.commons.lang3.StringUtils;

class Person {
    private final String username;
    private String firstName;
    private String lastName;

    public Person(String username) { this.username = username; }

    String greeting() {
        if (StringUtils.isAnyBlank(firstName, lastName))
            return username;
        return firstName + " " + lastName;
    }

    Person firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    Person lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    String username() { return username; }
}
