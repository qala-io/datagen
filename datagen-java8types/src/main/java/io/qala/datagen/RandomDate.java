package io.qala.datagen;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.qala.datagen.RandomShortApi.sample;
import static io.qala.datagen.RandomValue.upTo;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.SECONDS;

@SuppressWarnings("WeakerAccess")
public class RandomDate {
    private Instant from = LocalDateTime.MIN.toInstant(systemOffset());
    private Instant to = LocalDateTime.MAX.toInstant(systemOffset());

    public static RandomDate between(Instant from, Instant to) {
        if (from.isAfter(to))
            throw new IllegalArgumentException("from(" + from + ") cannot be larger than to(" + to + ")");
        RandomDate date = new RandomDate();
        date.from = from;
        date.to = to;
        return date;
    }
    public static RandomDate between(ChronoLocalDateTime from, ChronoLocalDateTime to) {
        return between(from.toInstant(systemOffset()), to.toInstant(systemOffset()));
    }

    public static RandomDate between(ChronoZonedDateTime from, ChronoZonedDateTime to) {
        return between(from.toInstant(), to.toInstant());
    }

    public static RandomDate between(LocalDate from, LocalDate to) {
        return between(from.atStartOfDay(), to.atStartOfDay());
    }

    public static RandomDate between(String from, String to) {
        return between(ZonedDateTime.parse(from), ZonedDateTime.parse(to));
    }

    public static RandomDate before(Instant before) {
        return between(LocalDateTime.MIN.toInstant(systemOffset()), before);
    }
    public static RandomDate before(ChronoLocalDateTime before) {
        return before(before.toInstant(systemOffset()));
    }
    public static RandomDate before(ChronoZonedDateTime before) {
        return before(before.toInstant());
    }

    public static RandomDate beforeNow() {
        return before(Instant.now());
    }

    /** Since specified time till max possible date. */
    public static RandomDate after(Instant after) {
        return between(after, LocalDateTime.MAX.toInstant(systemOffset()));
    }

    /** Since specified time till max possible date. */
    public static RandomDate after(ChronoLocalDateTime after) {
        return after(after.toInstant(systemOffset()));
    }

    /** Since specified time till max possible date. */
    public static RandomDate after(ChronoZonedDateTime after) {
        return after(after.toInstant());
    }

    public static RandomDate yesterday() {
        return between(LocalDate.now().minusDays(1).atStartOfDay(), LocalDate.now().atStartOfDay().minusSeconds(1));
    }

    public static RandomDate thisYear() {
        return between(startOfYear(), startOfYear().plusYears(1).minusSeconds(1));
    }

    /** Since specified time till now. */
    public static RandomDate since(Instant since) {
        return between(since, Instant.now());
    }

    /** Since specified time till now. */
    public static RandomDate since(ChronoLocalDateTime since) {
        return since(since.toInstant(systemOffset()));
    }

    /** Since specified time till now. */
    public static RandomDate since(ChronoZonedDateTime since) {
        return since(since.toInstant());
    }
    public static RandomDate afterNow() {
        return after(Instant.now());
    }

    public static RandomDate plusMinus100Years() {
        return between(yearsAgo(100), inYears(100));
    }
    public Instant instant() {
        int MAX_NANO = 999_999_999;
        long minSecond = from.getEpochSecond();
        long maxSecond = to.getEpochSecond();
        long randomSeconds = RandomValue.between(minSecond, maxSecond).Long();
        int fromNano = from.getNano();
        int toNano = to.getNano();
        long nano = 0;
        if (minSecond == maxSecond) nano = RandomValue.between(fromNano, toNano).Long();
        else if (randomSeconds == maxSecond) nano = upTo(toNano).Long();
        else if (randomSeconds == minSecond) nano = RandomValue.between(fromNano, MAX_NANO).Long();

        return Instant.ofEpochSecond(randomSeconds, nano);
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

    public static LocalDateTime yearAgo() {
        return now().minusYears(1);
    }
    public static LocalDateTime yearsAgo(int n) {
        return now().minusYears(n);
    }

    public static LocalDateTime inYears(int n) {
        return now().plusYears(n);
    }
    public static LocalDateTime dayAgo() {
        return now().minusDays(1);
    }
    public static LocalDateTime daysAgo(int n) {
        return now().minusDays(n);
    }
    public static LocalDateTime hourAgo() {
        return now().minusHours(1);
    }
    public static LocalDateTime secondAgo() {
        return now().minusSeconds(1);
    }
    public static LocalDateTime startOfYear() {
        return startOfMonth().withMonth(1);
    }
    public static LocalDateTime startOfMonth() {
        return startOfDay().withDayOfMonth(1);
    }
    public static LocalDateTime startOfDay() {
        return startOfHour().withHour(0);
    }
    public static LocalDateTime startOfHour() {
        return startOfMinute().truncatedTo(HOURS);
    }
    public static LocalDateTime startOfMinute() {
        return now().withSecond(0).truncatedTo(SECONDS);
    }
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    private static ZoneOffset systemOffset() {
        return ZoneId.systemDefault().getRules().getOffset(Instant.now());
    }
}
