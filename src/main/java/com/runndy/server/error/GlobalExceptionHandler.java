package com.runndy.server.error;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
    ErrorResponse errorResponse = ErrorResponse.builder()
                                               .message(ex.getMessage())
                                               .devMsg(ex.getDevMsg())
                                               .build();

    return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
  }

  // Login required
  @ExceptionHandler(AuthorizationDeniedException.class)
  ResponseEntity<ErrorResponse> handleForbiddenException(Exception ex) {
    log.error("Exception {}", ex.getMessage());
    ErrorResponse errorResponse = ErrorResponse.builder()
                                               .message(HttpStatus.FORBIDDEN.toString())
                                               .devMsg(ex.getMessage())
                                               .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<ErrorResponse> handleException(Exception ex) {
    log.error("Exception {}", ex.getMessage());
    ErrorResponse errorResponse = ErrorResponse.builder()
                                               .message(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                                               .devMsg(ex.getMessage())
                                               .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}