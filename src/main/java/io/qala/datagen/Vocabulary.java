package io.qala.datagen;

public interface Vocabulary {
    /** Can be globally overwritten for your particular project. */
    String SPECIAL_SYMBOLS = "!@#$%^&*()_+{}[]'\"|:?><~`§\\,";

    static char[] specialSymbols() {
        return SPECIAL_SYMBOLS.toCharArray();
    }
}
