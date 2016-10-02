package io.qala.datagen.examples;

class EditingReadOnlyExperimentException extends RuntimeException {
    EditingReadOnlyExperimentException(String id) {
        super("Experiment with id " + id + " is in read-only state, you cannot edit it");
    }
}
