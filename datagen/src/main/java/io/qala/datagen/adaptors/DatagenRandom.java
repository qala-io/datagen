package io.qala.datagen.adaptors;

import java.util.Random;

@SuppressWarnings("AnonymousHasLambdaAlternative"/*this is compiled for Java5*/)
public class DatagenRandom extends Random {
    //Public Morozov. Need this to be available for classes in this package.
    @Override public int next(int bits) {
        long oldseed = getCurrentSeed(), nextseed;
        do {
            nextseed = (oldseed * multiplier + addend) & mask;
        } while (oldseed == nextseed);

        super.setSeed(nextseed);
        overrideSeed(nextseed);
        return (int)(nextseed >>> (48 - bits));
    }

    public static void overrideSeed(long seed) { SEEDS.set(seed)   ;}
    public static long getCurrentSeed()        { return SEEDS.get();}

    private static final long multiplier = 0x5DEECE66DL;
    private static final long addend = 0xBL;
    private static final long mask = (1L << 48) - 1;
    private static final ThreadLocal<Long> SEEDS = new ThreadLocal<Long>() {
        @Override protected Long initialValue() {
            return System.nanoTime();
        }
    };
}
