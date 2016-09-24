package io.qala.datagen.adaptors;

import io.qala.datagen.NumberOutOfBoundaryException;

import java.util.Random;

public class CommonsMath4 {
    /**
     * Mostly copied from Commons Math.
     * <p>
     * Generates a uniformly distributed random integer between {@code lower}
     * and {@code upper} (endpoints included).
     * <p> The generated integer will be random, but not cryptographically secure. </p>
     *
     * @return a random integer greater than or equal to {@code lower}
     * and less than or equal to {@code upper}
     * @throws NumberOutOfBoundaryException if previously specified boundaries are greater/smaller than integer
     *                                      boundaries
     */
    public static int nextInt(Random random, int upper, int lower) {
        int max = (upper - lower) + 1;
        if (max <= 0) {
            // The range is too wide to fit in a positive int (larger
            // than 2^31); as it covers more than half the integer range,
            // we use a simple rejection method.
            while (true) {
                int r = random.nextInt();
                if (r >= lower && r <= upper) return r;
            }
        } else {
            // We can shift the range and directly generate a positive int.
            return lower + random.nextInt(max);
        }
    }

    /**
     * Generates a uniformly distributed random long integer between {@code lower}
     * and {@code upper} (endpoints included).
     *
     * @param lower lower bound for generated long integer
     * @param upper upper bound for generated long integer
     * @return a random long integer greater than or equal to {@code lower}
     * and less than or equal to {@code upper}
     */
    public static long nextLong(Random random, final long lower, final long upper) {
        if(lower == upper) return lower;
        if (lower > upper) throw new IllegalArgumentException();
        final long max = (upper - lower) + 1;
        if (max <= 0) {
            // the range is too wide to fit in a positive long (larger than 2^63); as it covers
            // more than half the long range, we use directly a simple rejection method
            while (true) {
                final long r = random.nextLong();
                if (r >= lower && r <= upper) {
                    return r;
                }
            }
        } else if (max < Integer.MAX_VALUE) {
            // we can shift the range and generate directly a positive int
            return lower + random.nextInt((int) max);
        } else {
            // we can shift the range and generate directly a positive long
            return lower + nextLong(random, max);
        }
    }

    /**
     * Returns a pseudorandom, uniformly distributed {@code long} value
     * between 0 (inclusive) and the specified value (exclusive), drawn from
     * this random number generator's sequence.
     *
     * @param rng random generator to use
     * @param n   the bound on the random number to be returned.  Must be
     *            positive.
     * @return a pseudorandom, uniformly distributed {@code long}
     * value between 0 (inclusive) and n (exclusive).
     * @throws IllegalArgumentException if n is not positive.
     */
    private static long nextLong(final Random rng, final long n) throws IllegalArgumentException {
        if (n > 0) {
            final byte[] byteArray = new byte[8];
            long bits;
            long val;
            do {
                rng.nextBytes(byteArray);
                bits = 0;
                for (final byte b : byteArray) {
                    bits = (bits << 8) | (((long) b) & 0xffL);
                }
                bits &= 0x7fffffffffffffffL;
                val = bits % n;
            } while (bits - val + (n - 1) < 0);
            return val;
        }
        throw new IllegalStateException("Not strictly positive" + n);
    }

    public static double nextUniform(JavaRandom random, double lower, double upper, boolean lowerInclusive) throws IllegalArgumentException {
        if (lower >= upper) {
            throw new IllegalArgumentException("lower bound " + lower + " must be strictly less than upper bound " + upper);
        }

        if (Double.isInfinite(lower)) {
            throw new IllegalArgumentException("interval bounds must be finite " + lower);
        }
        if (Double.isInfinite(upper)) {
            throw new IllegalArgumentException("interval bounds must be finite " + upper);
        }

        if (Double.isNaN(lower) || Double.isNaN(upper)) {
            throw new IllegalArgumentException("Not-a-number was specified");
        }

        // ensure nextDouble() isn't 0.0
        double u = nextDouble(random);
        while (!lowerInclusive && u <= 0.0) {
            u = nextDouble(random);
        }

        return u * upper + (1.0 - u) * lower;
    }

    private static double nextDouble(JavaRandom random) {
        final long high = ((long) random.next(26)) << 26;
        final int low = random.next(26);
        return (high | low) * 0x1.0p-52d;
    }
}
