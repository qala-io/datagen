package io.qala.datagen;

import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import java.time.*;

import static io.qala.datagen.Java8RandomShortApi.*;
import static io.qala.datagen.RandomDate.afterNow;
import static io.qala.datagen.RandomDate.beforeNow;
import static io.qala.datagen.RandomDate.plusMinus100Years;
import static io.qala.datagen.RandomValue.upTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.gen5.api.Assertions.assertTrue;

@RunWith(JUnit5.class)
@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class Java8RandomShortApiTest {
    @Test void instantIsBetweenBoundaries() {
        assertTrue(wideInstant().isAfter(Instant.MIN));
        assertTrue(wideInstant().isBefore(Instant.MAX));
    }

    @Test void instantCanBeBothBeforeAndAfterNow() {
        Instant now = Instant.now();
        assertThat(instants(500), hasItem(greaterThan(now)));
        assertThat(instants(500), hasItem(lessThan(now)));
    }

    @Test void localDateCanBeBothBeforeAndAfterNow() {
        LocalDate now = LocalDate.now();
        assertThat(localDates(500), hasItem(greaterThan(now)));
        assertThat(localDates(500), hasItem(lessThan(now)));
    }

    @Test void localDateTimeCanBeBothBeforeAndAfterNow() {
        LocalDateTime now = LocalDateTime.now();
        assertThat(localDateTimes(500), hasItem(greaterThan(now)));
        assertThat(localDateTimes(500), hasItem(lessThan(now)));
    }

    @Test void zonedDateTimeCanBeBothBeforeAndAfterNow() {
        ZonedDateTime now = ZonedDateTime.now();
        assertThat(zonedDateTimes(500), hasItem(greaterThan(now)));
        assertThat(zonedDateTimes(500), hasItem(lessThan(now)));
    }

    @Test void generatesTemporalsBetween() {
        Instant from = instant();
        Instant to = from.plusSeconds(upTo(100000L).Long());
        assertThat(RandomDate.between(from, to).instant(), greaterThanOrEqualTo(from));
        assertThat(RandomDate.between(from, to).instant(), lessThanOrEqualTo(to));

        assertThat(RandomDate.between(from, to).zonedDateTime(), greaterThanOrEqualTo(toZoned(from)));
        assertThat(RandomDate.between(from, to).zonedDateTime(), lessThanOrEqualTo(toZoned(to)));

        assertThat(RandomDate.between(from, to).localDateTime(), greaterThanOrEqualTo(toLocal(from)));
        assertThat(RandomDate.between(from, to).localDateTime(), lessThanOrEqualTo(toLocal(to)));

        assertThat(RandomDate.between(from, to).localDate(), greaterThanOrEqualTo(toLocalDate(from)));
        assertThat(RandomDate.between(from, to).localDate(), lessThanOrEqualTo(toLocalDate(to)));
    }

    @Test void beforeNowIsActuallyBefore() {
        assertThat(beforeNow().localDateTime(), lessThanOrEqualTo(LocalDateTime.now()));
    }

    @Test void afterNowIsActuallyAfter() {
        assertThat(afterNow().localDateTime(), greaterThanOrEqualTo(LocalDateTime.now()));
    }

    @Test void plusMinus100YearsReturnsDateWithinThisPeriod() {
        ZonedDateTime from = ZonedDateTime.now().minusYears(100);
        ZonedDateTime to = ZonedDateTime.now().plusYears(100);
        assertThat(plusMinus100Years().zonedDateTime(), greaterThanOrEqualTo(from));
        assertThat(plusMinus100Years().zonedDateTime(), lessThanOrEqualTo(to));
    }

    private ZonedDateTime toZoned(Instant instant) {
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    private LocalDateTime toLocal(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    private LocalDate toLocalDate(Instant instant) {
        return toLocal(instant).toLocalDate();
    }
}