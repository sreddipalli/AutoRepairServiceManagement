package com.autorepair.service.error;

import java.text.MessageFormat;
import java.util.Properties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
  private final String errorCode;
  private final String errorMessage;

  public ResourceNotFoundException(String errorCode, String... params) {
    super(getErrorMessage(errorCode, params));
    this.errorCode = errorCode;
    this.errorMessage = getErrorMessage(errorCode, params);
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  private static String getErrorMessage(String errorCode, String... params) {
    // Load properties file
    Properties properties = new Properties();
    try {
      properties.load(ResourceNotFoundException.class.getClassLoader()
          .getResourceAsStream("error-codes.properties"));
    } catch (Exception e) {
      e.printStackTrace();
      return "Error message not found";
    }

    // Get the error message from the properties file based on the error code
    String errorMessage = properties.getProperty("error-codes." + errorCode);
    if (errorMessage != null && params != null && params.length > 0) {
      // Substitute parameters in the error message using MessageFormat
      errorMessage = MessageFormat.format(errorMessage, (Object[]) params);
    }
    return errorMessage;
  }
}
