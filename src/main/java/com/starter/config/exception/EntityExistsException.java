package com.starter.config.exception;

public class EntityExistsException extends RuntimeException {

  public EntityExistsException() { super(); }

  public EntityExistsException(String message) {
    super(message);
  }
}
