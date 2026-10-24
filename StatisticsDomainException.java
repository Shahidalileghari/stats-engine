package com.stats.engine.exception;
/** Thrown when a statistical operation is undefined for the supplied values. */
public class StatisticsDomainException extends IllegalArgumentException {
    public StatisticsDomainException(String message) { super(message); }
}