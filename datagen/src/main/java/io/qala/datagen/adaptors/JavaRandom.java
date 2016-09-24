package io.qala.datagen.adaptors;

import java.util.Random;

public class JavaRandom extends Random {
    //Public Morozov. Need this to be available for classes in this package.
    @Override public int next(int bits) {
        return super.next(bits);
    }
}
