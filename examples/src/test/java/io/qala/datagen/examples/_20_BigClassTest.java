package io.qala.datagen.examples;

import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static io.qala.datagen.RandomShortApi.numeric;

public class _20_BigClassTest {
    @Test public void accountNumberValidation_passesForHappyPath() {
        Account account = new Account();
        account.setCreatedTime(ZonedDateTime.now().minus(100, ChronoUnit.DAYS));
        account.setInterestRate(.2);
        account.setOpenedIn(Country.US);
        account.setPrimaryOwner(new Person("username").country(Country.AD));
        account.setSecondaryOwner(new Person("username2").country(Country.AE));
        account.setNumber("0123456789");
        account.validate();
    }
    @Test public void accountNumberValidation_passesForHappyPath_randomization() {
        Account account = Account.random();
        account.setNumber(numeric(10));
        account.validate();
    }
}
