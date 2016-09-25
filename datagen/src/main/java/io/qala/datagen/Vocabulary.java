package io.qala.datagen;


@SuppressWarnings("WeakerAccess")
public class Vocabulary {
    /** Can be globally overwritten for your particular project. */
    public static String SPECIAL_SYMBOLS = "!@#$%^&*()_+{}[]'\"|:?><~`§\\,/;.";

    static char[] specialSymbols() {
        return SPECIAL_SYMBOLS.toCharArray();
    }
}
