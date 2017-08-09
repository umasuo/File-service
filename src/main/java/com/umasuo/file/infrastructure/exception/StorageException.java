package com.umasuo.file.infrastructure.exception;

/**
 * Created by umasuo on 17/6/15.
 */
public class StorageException extends RuntimeException {

  private static final long serialVersionUID = -6667432953756664906L;

  /**
   * Instantiates a new Storage exception.
   *
   * @param message the message
   */
  public StorageException(String message) {
    super(message);
  }

  /**
   * Instantiates a new Storage exception.
   *
   * @param message the message
   * @param cause the cause
   */
  public StorageException(String message, Throwable cause) {
    super(message, cause);
  }
}
