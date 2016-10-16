package io.qala.datagen.examples;

import org.apache.commons.lang3.StringUtils;

import static io.qala.datagen.RandomShortApi.blankOr;
import static io.qala.datagen.RandomShortApi.english;

class Person {
    private String username;
    private String firstName;
    private String lastName;
    private Country country;

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
    String firstName() {
        return firstName;
    }

    Person lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
    String lastName() {
        return lastName;
    }

    String username() { return username; }

    Person country(Country country) {//String - not Country
        this.country = country;
        return this;
    }
    Country country() {
        return country;
    }

    private static final int USERNAME_MAX_LENGTH = 20;
    void validate() throws IllegalStateException {
        if (StringUtils.isBlank(username()))
            throw new IllegalStateException("Java Constraints violation: username is mandatory! Passed: [" + username() + "]");
        if (username().length() > USERNAME_MAX_LENGTH)
            throw new IllegalStateException("Java Constraints violation: username [" + username() + "] cannot be more than " + USERNAME_MAX_LENGTH + "!");
    }

    static Person random() {
        return new Person(english(1, 20)).country(Country.random())
                .firstName(blankOr(english(1, 20))).lastName(blankOr(english(1, 20)));
    }
}
