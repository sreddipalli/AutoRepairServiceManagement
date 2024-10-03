package com.autorepair.service.error;

@SuppressWarnings("serial")
public class InvalidRequestArgumentException extends RuntimeException {

  public static final String INVALID_ARGUMENT_EXCEPTION = "Invalid argument passed";

  public InvalidRequestArgumentException(String msg, Throwable cause) {
    super(msg, cause);

  }

  public InvalidRequestArgumentException(String msg) {
    super(msg);

  }

  public InvalidRequestArgumentException() {
    super(INVALID_ARGUMENT_EXCEPTION);

  }

  public String getmessage() {
    return INVALID_ARGUMENT_EXCEPTION;
  }

}
