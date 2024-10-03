package com.autorepair.service.utils;

import java.text.MessageFormat;
import org.springframework.stereotype.Component;
import com.autorepair.config.ErrorProperties;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorMessage {

  private ErrorProperties errorProperties;

  public String getErrorMessage(String errorCode, String... params) {
    String errorMessage = errorProperties.getCodes().get(errorCode);
    if (errorMessage != null && params != null && params.length > 0) {
      // Substitute parameters in the error message using MessageFormat
      errorMessage = MessageFormat.format(errorMessage, (Object[]) params);
    }
    return errorMessage;
  }

}
