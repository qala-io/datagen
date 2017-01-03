package io.qala.datagen;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class ContainsNonAlphanumericsMatcher<T> extends BaseMatcher<T> {
    private static final ContainsNonAlphanumericsMatcher SINGLETON = new ContainsNonAlphanumericsMatcher();

    static <T> ContainsNonAlphanumericsMatcher<T> containsNonAlphanumerics() {
        //noinspection unchecked
        return SINGLETON;
    }

    @Override public boolean matches(Object actual) {
        if (actual == null) return false;

        if (actual instanceof String) {
            String actualString = (String) actual;
            for(int i = 0; i < actualString.length(); i++) {
                if(actualString.codePointAt(i) > 255) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override public void describeTo(Description description) {
        description.appendValue("non alphanumerics to be present");
    }
}
