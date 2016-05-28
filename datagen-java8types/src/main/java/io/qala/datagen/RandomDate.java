package io.qala.datagen;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import static io.qala.datagen.RandomShortApi.sample;

public class RandomDate {
    private Instant from = LocalDateTime.MIN.toInstant(ZoneOffset.UTC);
    private Instant to = LocalDateTime.MAX.toInstant(ZoneOffset.UTC);

    public static RandomDate between(Instant from, Instant to) {
        if (from.isAfter(to)) throw new IllegalArgumentException("'from' cannot be larger than 'to'");
        RandomDate date = new RandomDate();
        date.from = from;
        date.to = to;
        return date;
    }
    public static RandomDate between(ChronoLocalDateTime from, ChronoLocalDateTime to) {
        return between(from.toInstant(ZoneOffset.UTC), to.toInstant(ZoneOffset.UTC));
    }

    public static RandomDate before(Instant before) {
        return between(LocalDateTime.MIN.toInstant(ZoneOffset.UTC), before);
    }
    public static RandomDate before(ChronoLocalDateTime before) {
        return before(before.toInstant(ZoneOffset.UTC));
    }
    public static RandomDate before(ChronoZonedDateTime before) {
        return before(before.toInstant());
    }

    public static RandomDate beforeNow() {
        return before(Instant.now());
    }
    public static RandomDate after(Instant after) {
        return between(after, LocalDateTime.MAX.toInstant(ZoneOffset.UTC));
    }
    public static RandomDate after(ChronoLocalDateTime after) {
        return after(after.toInstant(ZoneOffset.UTC));
    }
    public static RandomDate after(ChronoZonedDateTime after) {
        return after(after.toInstant());
    }
    public static RandomDate afterNow() {
        return after(Instant.now());
    }
    public static RandomDate plusMinus100Years() {
        return between(LocalDateTime.now().minusYears(100), LocalDateTime.now().plusYears(100));
    }
    public Instant instant() {
        long minSecond = from.getLong(ChronoField.INSTANT_SECONDS);
        long maxSecond = to.getLong(ChronoField.INSTANT_SECONDS);
        return Instant.now().
                with(ChronoField.INSTANT_SECONDS, RandomValue.between(minSecond, maxSecond).Long()).
                with(ChronoField.NANO_OF_SECOND, RandomValue.upTo(to.getNano()).Long());
    }
    public List<Instant> instants(int n) {
        return multiply(n, this::instant);
    }

    public LocalDateTime localDateTime() {
        return LocalDateTime.ofInstant(instant(), ZoneId.systemDefault());
    }
    public List<LocalDateTime> localDateTimes(int nOfTimes) {
        return multiply(nOfTimes, this::localDateTime);
    }

    public ZonedDateTime zonedDateTime() {
        return ZonedDateTime.ofInstant(instant(), ZoneId.of(sample(ZoneId.getAvailableZoneIds())));
    }
    public List<ZonedDateTime> zonedDateTimes(int nOfTimes) {
        return multiply(nOfTimes, this::zonedDateTime);
    }

    public LocalDate localDate() {
        return localDateTime().toLocalDate();
    }
    public List<LocalDate> localDates(int nOfTimes) {
        return multiply(nOfTimes, this::localDate);
    }

    private static <T> List<T> multiply(int nOfTimes, java.util.function.Supplier<T> function) {
        List<T> result = new ArrayList<>(nOfTimes);
        for (int i = 0; i < nOfTimes; i++) {
            result.add(function.get());
        }
        return result;
    }
}
