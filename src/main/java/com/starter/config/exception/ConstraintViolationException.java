package com.starter.config.exception;

public class ConstraintViolationException extends RuntimeException {

  public ConstraintViolationException() { super(); }

  public ConstraintViolationException(String message) {
    super(message);
  }
}
