package com.runndy.server.error;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

  private final String message;
  private final String devMsg;
}