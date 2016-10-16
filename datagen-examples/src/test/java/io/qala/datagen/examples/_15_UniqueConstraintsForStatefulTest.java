package io.qala.datagen.examples;

import org.junit.Test;

import static io.qala.datagen.RandomShortApi.*;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.junit.Assert.assertEquals;

/**
 * If you have a test that registers users against one of the envs (DEV, TST, UAT, etc) and your unique fields are
 * hardcoded, the 2nd time you run it the test will fail since the records in DB are persisted between multiple test
 * runs.
 */
public class _15_UniqueConstraintsForStatefulTest {
    @Test public void canRegisterNewUser() {
        String username = alphanumeric(1, 20);
        RestService.instance.save(new Person(username));
    }

    @Test public void canRegisterNewUser_2ndRun() {
        String username = alphanumeric(1, 20);
        RestService.instance.save(new Person(username));
    }
}
