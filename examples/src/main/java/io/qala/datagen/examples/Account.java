package io.qala.datagen.examples;

import java.time.ZonedDateTime;

import static io.qala.datagen.RandomDate.beforeNow;
import static io.qala.datagen.RandomShortApi.Double;
import static io.qala.datagen.RandomShortApi.numeric;

@SuppressWarnings("WeakerAccess")
class Account {
    private Person primaryOwner;
    private Person secondaryOwner;
    private String number;
    private Country openedIn;
    private double interestRate;
    private ZonedDateTime createdTime;

    static Account random() {
        Account account = new Account();
        account.setCreatedTime(beforeNow().zonedDateTime());
        account.setInterestRate(Double(.0, 1.0));
        account.setOpenedIn(Country.random());
        account.setNumber(numeric(10));
        account.setPrimaryOwner(Person.random());
        account.setSecondaryOwner(Person.random());
        return account;
    }

    Person getPrimaryOwner() {
        return primaryOwner;
    }

    void setPrimaryOwner(Person primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    Person getSecondaryOwner() {
        return secondaryOwner;
    }

    void setSecondaryOwner(Person secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    String getNumber() {
        return number;
    }

    void setNumber(String number) {
        this.number = number;
    }

    Country getOpenedIn() {
        return openedIn;
    }

    void setOpenedIn(Country openedIn) {
        this.openedIn = openedIn;
    }

    double getInterestRate() {
        return interestRate;
    }

    void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    ZonedDateTime getCreatedTime() {
        return createdTime;
    }

    void setCreatedTime(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
    }

    void validate() {}
}
