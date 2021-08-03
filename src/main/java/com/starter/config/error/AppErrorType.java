package com.starter.config.error;

import java.net.URI;
import lombok.Getter;

@Getter
public enum AppErrorType {
  CONSTRAINT_VIOLATION_TYPE(
      "/constraint-violation",
      ""),
  BAD_REQUEST_TYPE(
      "/bad-request",
      ""),
  NOT_FOUND_TYPE(
      "/not-found",
      ""),
  INTERNAL_SERVER_ERROR_TYPE(
      "/internal-server-error",
      ""),
  ENTITY_NOT_FOUND_EXCEPTION(
      "/entity-not-found",
      ""),
  ENTITY_EXIST_EXCEPTION(
      "/entity-already-exists",
      "")
  ;


  private URI type;
  private String title;

  AppErrorType(URI type, String title) {
    this.type = type;
    this.title = title;
  }

  AppErrorType(String type, String title) {
    this(URI.create(ErrorConstants.PROBLEM_BASE_URL + type), title);
  }

}
