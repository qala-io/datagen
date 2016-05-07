package io.qala.datagen;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.lang.reflect.Array;

public class ContainsNonAlphanumericsMatcher<T> extends BaseMatcher<T> {


    public static <T> ContainsNonAlphanumericsMatcher<T> containsNonAlphanumerics() {
        //noinspection unchecked
        return new ContainsNonAlphanumericsMatcher();
    }

    @Override public boolean matches(Object actual) {
        if (actual == null) return false;

        if (actual instanceof String) {
            String actualString = (String) actual;
            for(int i = 0; i < actualString.length(); i++) {
                if(actualString.charAt(i) > 255) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isArray(Object o) {
        return o.getClass().isArray();
    }

    @Override public void describeTo(Description description) {
        description.appendValue("non alphanumerics to be present");
    }
}
