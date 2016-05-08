package io.qala.datagen;

import java.util.List;

public interface RandomString {
    enum Type {
        ALPHANUMERIC, NUMERIC, UNICODE, ENGLISH, SPECIAL_SYMBOLS;
        public String generate(RandomValue value) {
            switch (this) {
                case ALPHANUMERIC: return value.alphanumeric();
                case NUMERIC: return value.numeric();
                case UNICODE: return value.unicode();
                case ENGLISH: return value.english();
                case SPECIAL_SYMBOLS: return value.specialSymbols();
                default: throw new IllegalStateException("Bug in Datagen: cannot dynamically generate " + this);
            }
        }
    }
    String alphanumeric();

    List<String> alphanumerics();

    List<String> alphanumerics(int nOfElements);

    List<String> alphanumerics(int minNOfElements, int maxNOfElements);

    String numeric();

    List<String> numerics();

    List<String> numerics(int nOfElements);

    String english();

    /**
     * Generates a random string from the specified characters.
     *
     * @param vocabulary characters the resulting string will contain of, cannot be empty
     * @return random string from the specified characters
     */
    String string(char... vocabulary);
    /**
     * Generates a random string from characters present in the specified string.
     *
     * @param vocabulary characters the resulting string will contain of, cannot be empty
     * @return random string from the specified characters
     */
    String string(String vocabulary);
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
