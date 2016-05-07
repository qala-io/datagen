package io.qala.datagen;

import java.util.List;

public interface RandomString {
    String alphanumeric();

    List<String> alphanumerics();

    List<String> alphanumerics(int nOfElements);

    List<String> alphanumerics(int minNOfElements, int maxNOfElements);

    String numeric();

    List<String> numerics();

    List<String> numerics(int nOfElements);

    String english();

    /**
     * Generates unicode string of variable length that includes characters from different languages, weird symbols
     * and <a href="https://docs.oracle.com/javase/tutorial/i18n/text/supplementaryChars.html">Supplementary Characters</a>
     * that are comprised of multiple chars.
     *
     * @return unicode characters including different languages and weird symbols
     */
    String unicode();

    /**
     * You can customize what "special" means for your project by globally updating {@link Vocabulary#SPECIAL_SYMBOLS}.
     * @return a string consisting of special characters only
     */
    String specialSymbols();
}
