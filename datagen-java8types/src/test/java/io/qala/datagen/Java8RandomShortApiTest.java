package io.qala.datagen;

import org.junit.gen5.api.Test;
import org.junit.gen5.junit4.runner.JUnit5;
import org.junit.runner.RunWith;

import java.time.*;

import static io.qala.datagen.Java8RandomShortApi.*;
import static io.qala.datagen.RandomDate.*;
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
        assertThat(between(from, to).instant(), greaterThanOrEqualTo(from));
        assertThat(between(from, to).instant(), lessThanOrEqualTo(to));

        assertThat(between(from, to).zonedDateTime(), greaterThanOrEqualTo(toZoned(from)));
        assertThat(between(from, to).zonedDateTime(), lessThanOrEqualTo(toZoned(to)));

        assertThat(between(from, to).localDateTime(), greaterThanOrEqualTo(toLocal(from)));
        assertThat(between(from, to).localDateTime(), lessThanOrEqualTo(toLocal(to)));

        assertThat(between(from, to).localDate(), greaterThanOrEqualTo(toLocalDate(from)));
        assertThat(between(from, to).localDate(), lessThanOrEqualTo(toLocalDate(to)));
    }
    @Test void generatesTemporalsBetweenStrings() {
        String from = "2007-12-03T10:15:30+01:00[Europe/Paris]";
        String to = "2007-12-10T00:15:30+01:00[Europe/Paris]";
        assertThat(between(from, to).zonedDateTime(), greaterThanOrEqualTo(toZoned(from)));
        assertThat(between(from, to).zonedDateTime(), lessThanOrEqualTo(toZoned(to)));
    }

    @Test void beforeNowIsActuallyBefore() {
        assertThat(beforeNow().localDateTime(), lessThanOrEqualTo(LocalDateTime.now()));
    }

    @Test void afterNowIsActuallyAfter() {
        assertThat(afterNow().localDateTime(), greaterThanOrEqualTo(LocalDateTime.now()));
    }
    @Test void sinceIsReturningDatesTillNow() {
        LocalDateTime secondAgo = secondAgo();
        assertThat(since(secondAgo).localDateTime(), lessThanOrEqualTo(LocalDateTime.now()));
        assertThat(since(secondAgo).localDateTime(), greaterThanOrEqualTo(secondAgo));
    }

    @Test void plusMinus100YearsReturnsDateWithinThisPeriod() {
        ZonedDateTime from = ZonedDateTime.now().minusYears(100);
        ZonedDateTime to = ZonedDateTime.now().plusYears(100);
        assertThat(plusMinus100Years().zonedDateTime(), greaterThanOrEqualTo(from));
        assertThat(plusMinus100Years().zonedDateTime(), lessThanOrEqualTo(to));
    }

    @Test void betweenForLocalDate() {
        assertThat(between(yearsAgo(2), startOfMonth()).localDateTime(), greaterThanOrEqualTo(yearsAgo(2).minusMinutes(1)));
        assertThat(between(yearsAgo(2), startOfMonth()).localDateTime(), lessThan(now()));
    }

    private ZonedDateTime toZoned(Instant instant) {
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
    private ZonedDateTime toZoned(String string) {
        return ZonedDateTime.parse(string);
    }
    private LocalDateTime toLocal(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    private LocalDate toLocalDate(Instant instant) {
        return toLocal(instant).toLocalDate();
    }
}