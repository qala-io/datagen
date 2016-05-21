package io.qala.datagen;

/**
 * This is something like {@link java.util.function.Function} - we need this "wannabe" to exist to keep the lib
 * compatible with older versions of Java.
 */
public interface Function {
    void call();
}
