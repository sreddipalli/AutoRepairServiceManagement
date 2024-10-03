package com.autorepair.service.error;

@SuppressWarnings("serial")
public class GenericException extends RuntimeException {
  private final String errorCode;
  private final String errorMessage;

  public GenericException(String errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
