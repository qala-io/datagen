package io.qala.datagen;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.List;

import static io.qala.datagen.RandomValue.between;
import static io.qala.datagen.RandomValue.upTo;

public class Java8RandomShortApi {

    /**
     * {@link Instant} is 1 year wider than {@link LocalDateTime}.
     */
    public static Instant wideInstant() {
        long minSecond = Instant.MIN.getLong(ChronoField.INSTANT_SECONDS);
        long maxSecond = Instant.MAX.getLong(ChronoField.INSTANT_SECONDS);
        long maxNanoOfSecond = Instant.MAX.getNano();
        return Instant.now().
                with(ChronoField.INSTANT_SECONDS, between(minSecond, maxSecond).Long()).
                with(ChronoField.NANO_OF_SECOND, upTo(maxNanoOfSecond).Long());
    }

    /**
     * {@link LocalDateTime}'s boundaries are "narrower" than of the {@link Instant}.
     */
    public static Instant instant() {
        return new RandomDate().instant();
    }

    public static List<Instant> instants(int n) {
        return new RandomDate().instants(n);
    }

    public static LocalDateTime localDateTime() {
        return new RandomDate().localDateTime();
    }

    public static List<LocalDateTime> localDateTimes(int nOfTimes) {
        return new RandomDate().localDateTimes(nOfTimes);
    }

    public static ZonedDateTime zonedDateTime() {
        return new RandomDate().zonedDateTime();
    }

    public static List<ZonedDateTime> zonedDateTimes(int nOfTimes) {
        return new RandomDate().zonedDateTimes(nOfTimes);
    }

    public static LocalDate localDate() {
        return new RandomDate().localDate();
    }

    public static List<LocalDate> localDates(int nOfTimes) {
        return new RandomDate().localDates(nOfTimes);
    }
}
