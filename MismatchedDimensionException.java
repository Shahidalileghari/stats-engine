package com.stats.engine.exception;
/** Thrown when paired arrays do not have equal lengths. */
public class MismatchedDimensionException extends IllegalArgumentException {
    public MismatchedDimensionException(String message) { super(message); }
}