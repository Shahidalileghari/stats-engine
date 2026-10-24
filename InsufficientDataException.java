package com.stats.engine.exception;
/** Thrown when an algorithm receives too few observations. */
public class InsufficientDataException extends IllegalArgumentException {
    public InsufficientDataException(String message) { super(message); }
}