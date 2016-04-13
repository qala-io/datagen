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
     * You can customize what "special" means for your project by globally updating {@link Vocabulary#SPECIAL_SYMBOLS}.
     * @return a string consisting of special characters only
     */
    String specialSymbols();
}
