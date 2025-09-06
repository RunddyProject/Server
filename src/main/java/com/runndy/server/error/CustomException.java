package com.runndy.server.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

  private final HttpStatus httpStatus;
  private final String devMsg;

  public CustomException(HttpStatus httpStatus, String devMsg) {
    this.httpStatus = httpStatus;
    this.devMsg = devMsg;
  }
}
