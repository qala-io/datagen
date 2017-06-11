package io.qala.datagen.junit5;

import io.qala.datagen.adaptors.DatagenRandom;
import io.qala.datagen.Seed;
import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.Optional;

public class DatagenUtils {
    static boolean passCaseNameToTestMethod(ContainerExtensionContext context) {
        Optional<Method> testMethod = context.getTestMethod();
        return testMethod.filter(method -> method.getParameterCount() == 2).isPresent();
    }

    public static void setCurrentSeedIfNotSetYet(ExtensionContext context) {
        ExtensionContext.Store classStore = getClassStore(context);
        if(classStore != null) {
            Long currentClassSeed = classStore.get("current_class_seed", Long.class);
            if (currentClassSeed == null && context.getTestClass().isPresent()) {
                Seed classAnnotation = context.getTestClass().get().getAnnotation(Seed.class);
                if (classAnnotation != null) DatagenRandom.overrideSeed(classAnnotation.value());
                classStore.put("current_class_seed", DatagenRandom.getCurrentSeed());
            }
        }

        ExtensionContext.Store methodStore = getMethodStore(context);
        if(methodStore != null) {
            Long currentMethodSeed = methodStore.get("current_method_seed", Long.class);
            if (currentMethodSeed == null && context.getTestMethod().isPresent()) {
                Seed methodAnnotation = context.getTestMethod().get().getAnnotation(Seed.class);
                if (methodAnnotation != null) DatagenRandom.overrideSeed(methodAnnotation.value());
                methodStore.put("current_method_seed", DatagenRandom.getCurrentSeed());
            }
        }
    }

    public static void logCurrentSeeds(ExtensionContext context) {
        ExtensionContext.Store methodStore = getMethodStore(context);
        ExtensionContext.Store classStore = getClassStore(context);
        String logLine = "";
        if (methodStore != null) logLine = " Method Seed: [" + methodStore.get("current_method_seed", Long.class) + "]";
        if (classStore != null) logLine += " Class Seed: [" + classStore.get("current_class_seed", Long.class) + "]";
        if (!logLine.isEmpty()) logLine = "Datagen" + logLine + " for " + context.getUniqueId();
        System.out.println(logLine);
    }

    private static ExtensionContext.Store getMethodStore(ExtensionContext context) {
        Optional<Method> m = context.getTestMethod();
        if (m.isPresent()) return context.getStore(ExtensionContext.Namespace.create(DatagenUtils.class, m.get()));
        return null;
    }

    private static ExtensionContext.Store getClassStore(ExtensionContext context) {
        Optional<Class<?>> c = context.getTestClass();
        if (c.isPresent()) return context.getStore(ExtensionContext.Namespace.create(DatagenUtils.class, c.get()));
        return null;
    }
}
