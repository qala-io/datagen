package io.qala.datagen.junit.jupiter;

import io.qala.datagen.adaptors.DatagenRandom;
import io.qala.datagen.Seed;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class DatagenUtils {
    private static final Logger LOG = LoggerFactory.getLogger(DatagenUtils.class);

    static boolean passCaseNameToTestMethod(ExtensionContext context) {
        Optional<Method> testMethod = context.getTestMethod();
        return testMethod.filter(method -> method.getParameterCount() == 2).isPresent();
    }

    public static void setCurrentSeedIfNotSetYet(ExtensionContext context) {
        if(getCurrentLevelSeedFromStore(context) != null) return;

        Optional<Method> testMethod = context.getTestMethod();
        Optional<Class<?>> testClass = context.getTestClass();
        if(testMethod.isPresent()) {
            Seed annotation = testMethod.get().getAnnotation(Seed.class);
            if (annotation != null) DatagenRandom.overrideSeed(annotation.value());
        } else if(testClass.isPresent()) {
            Seed annotation = testClass.get().getAnnotation(Seed.class);
            if (annotation != null) DatagenRandom.overrideSeed(annotation.value());
        }
        putSeedToStoreIfAbsent(context, DatagenRandom.getCurrentSeed());
    }

    public static void logCurrentSeeds(ExtensionContext context) {
        LinkedHashMap<String, Long> seedStrings = new LinkedHashMap<>();
        while(context != null) {
            Long seed = getCurrentLevelSeedFromStore(context);
            Optional<Method> testMethod = context.getTestMethod();
            Optional<Class<?>> testClass = context.getTestClass();
            if(testMethod.isPresent()) seedStrings.putIfAbsent(testMethod.get().getName(), seed);
            else                       testClass.ifPresent((c)->seedStrings.put(c.getSimpleName(), seed));

            context = context.getParent().isPresent() ? context.getParent().get() : null;
        }
        StringBuilder logLine = new StringBuilder();
        for(Map.Entry<String, Long> seed: seedStrings.entrySet()) {
            logLine.append(" ").append(seed.getKey()).append("[").append(seed.getValue()).append("]");
        }
        if(logLine.length() != 0) LOG.info("Random Seeds: {}", logLine);
    }

    private static void putSeedToStoreIfAbsent(ExtensionContext context, Long seed) {
        Store store = getStore(context);
        if(store != null) store.put("seed", seed);
    }
    private static Long getCurrentLevelSeedFromStore(ExtensionContext context) {
        Store store = getStore(context);
        Object seed = null;
        if(store != null) seed = store.get("seed");
        return seed != null ? (Long) seed : null;
    }
    private static Store getStore(ExtensionContext context) {
        Store store = getMethodStore(context);
        if(store == null) store = getClassStore(context);
        return store;
    }

    private static Store getMethodStore(ExtensionContext context) {
        Optional<Method> m = context.getTestMethod();
        if (m.isPresent()) return context.getStore(Namespace.create(DatagenUtils.class, m.get()));
        return null;
    }

    private static Store getClassStore(ExtensionContext context) {
        Optional<Class<?>> c = context.getTestClass();
        if (c.isPresent()) return context.getStore(Namespace.create(DatagenUtils.class, c.get()));
        return null;
    }
}
