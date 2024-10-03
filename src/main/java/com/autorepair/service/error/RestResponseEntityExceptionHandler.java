package com.autorepair.service.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.autorepair.constants.ServiceConstants;
import com.autorepair.service.result.Response;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String EXCEPTION_OCCURRED = "Exception occurred :";

  @ExceptionHandler(value = {InvalidRequestArgumentException.class})
  protected ResponseEntity<Object> handleConflict(RuntimeException exception, WebRequest request) {

    Error error = new Error();
    error.setCode(HttpStatus.BAD_REQUEST.value());
    error.setDescription(exception.getMessage());

    log.error(EXCEPTION_OCCURRED, exception);

    return handleExceptionInternal(exception,
        Response.failure("400", exception.getMessage(), exception.getMessage()), new HttpHeaders(),
        HttpStatus.BAD_REQUEST, request);
  }

  @SuppressWarnings("unchecked")
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Object> handleBusinessException(BusinessException exception,
      WebRequest request) {
    log.error(EXCEPTION_OCCURRED, exception);
    if (ServiceConstants.INVALID_INPUT.equals(exception.getStatusCode())) {
      exception.setHttpStatus(HttpStatus.BAD_REQUEST);
    }
    return handleExceptionInternal(exception, Response.failure(exception.getErrorCode(),
        exception.getMessage(), exception.getDescription()), new HttpHeaders(),
        exception.getHttpStatus(), request);
  }

  @SuppressWarnings("unchecked")
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGenericException(RuntimeException exception,
      WebRequest request) {
    log.error(EXCEPTION_OCCURRED, exception);
    return handleExceptionInternal(exception,
        Response.failure("500", exception.getMessage(), exception.getMessage()), new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

}
