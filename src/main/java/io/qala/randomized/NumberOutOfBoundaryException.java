package io.qala.randomized;

public class NumberOutOfBoundaryException extends RuntimeException {
    public NumberOutOfBoundaryException(String message) {
        super(message);
    }
}
