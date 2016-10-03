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

    @SuppressWarnings("UnusedParameters")//this is an example that doesn't actually save anything
    Person country(Country country) {
        return this;
    }

    private static final int USERNAME_MAX_LENGTH = 50;
    void validate() throws IllegalStateException {
        if (StringUtils.isBlank(username()))
            throw new IllegalStateException("Java Constraints violation: username is mandatory! Passed: [" + username() + "]");
        if (username().length() > USERNAME_MAX_LENGTH)
            throw new IllegalStateException("Java Constraints violation: username [" + username() + "] cannot be more than " + USERNAME_MAX_LENGTH + "!");
    }
}
