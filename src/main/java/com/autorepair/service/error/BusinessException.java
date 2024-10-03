package com.autorepair.service.error;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

  private HttpStatus httpStatus;
  private final String statusCode;
  private final String errorCode;
  private final String message;
  private final String description;

  public BusinessException(String statusCode, String errorCode, String message,
      String description) {
    super(message);
    this.httpStatus = null;
    this.statusCode = statusCode;
    this.errorCode = errorCode;
    this.message = message;
    this.description = description;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getErrorCode() {
    return errorCode;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public String getDescription() {
    return description;
  }

  public String getStatusCode() {
    return statusCode;
  }

}
