package io.qala.datagen.examples;

class ObjectNotFoundException extends RuntimeException {
    ObjectNotFoundException(Class<?> objectType, String id) {
        super("Couldn't find " + objectType.getSimpleName() + " with id " + id);
    }
}
