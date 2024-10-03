package com.autorepair.service.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {

  private final String message;

  @Override
  public String getMessage() {
    return this.message;
  }
}
