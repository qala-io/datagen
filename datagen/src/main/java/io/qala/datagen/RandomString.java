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
     * Generates a {@link #unicode()} that doesn't have leading or trailing whitespaces. A character is a whitespaces
     * if:
     * <ul>
     *     <li>It's a {@link Character#isWhitespace(int)} which is a Java's personal definition of a whitespace.</li>
     *     <li>It's a {@link Character#isSpaceChar(int)}.</li>
     *     <li>It's a control ACII character (<20)</li>
     * </ul>
     * This is done because in Java there is bugs (they call them features) related to whitespaces:
     * <ul>
     *     <li>Their definition is not always compatible with Unicode notion of a whitespace (especially
     *     {@link Character#isWhitespace(int)}, but also {@link Character#isSpaceChar(char)}</li>
     *     <li>{@link String#trim()} ignores all the notions of the whitespace and introduces its own which is not
     *     compatible neither with Unicode nor with Character class. For this method
     *     "whitespace"=="control ASCII symbol".</li>
     * </ul>
     * So to please everyone all these kinds of the whitespaces are trimmed (except for some Unicode characters that
     * Java doesn't recognize as whitespaces at all), see
     * <a href="https://closingbraces.net/2008/11/11/javastringtrim/">Java’s String.trim has a strange idea of whitespace</a>
     * for more details.
     * @return a unicode string that is guaranteed not to have whitespaces both at the beginning and at the end
     */
    String unicodeWithoutBoundarySpaces();
    /**
     * You can customize what "special" means for your project by globally updating {@link Vocabulary#SPECIAL_SYMBOLS}.
     * @return a string consisting of special characters only
     */
    String specialSymbols();
}
