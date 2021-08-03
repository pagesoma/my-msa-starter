package com.starter.config.error;

import java.util.List;
import lombok.Getter;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.StatusType;

@Getter
public class AppError {

  private static final String FIELD_ERRORS_KEY = "fieldErrors";
  private AppErrorType error;
  private StatusType status;
  private String detail;
  private List<FieldErrorVM> fieldErros;
  private ProblemBuilder problemBuilder = Problem.builder();

  public AppError(AppErrorType error) {
    this.error = error;
  }

  public AppError(AppErrorType error, StatusType status) {
    this(error);
    this.status = status;
  }

  public AppError(AppErrorType error, StatusType status, String detail) {
    this(error);
    this.status = status;
    this.detail = detail;
  }

  public AppError(AppErrorType error, StatusType status, List<FieldErrorVM> fieldErrors) {
    this(error, status);
    this.fieldErros = fieldErrors;
  }

  public Problem buildProblem() {
    if (error == null) {
      throw new IllegalStateException("Error가 존재하지 않습니다.");
    }
    problemBuilder
        .withType(error.getType())
        .withTitle(error.getTitle());
    setProblemDetail();
    setProblemStatus();
    setProblemFieldErrors();

    return problemBuilder.build();
  }

  private ProblemBuilder setProblemDetail() {
    if (detail == null || detail.trim().isEmpty()) {
      return problemBuilder;
    }
    return problemBuilder.withDetail(detail);
  }

  private ProblemBuilder setProblemStatus() {
    if (status == null) {
      return problemBuilder;
    }
    return problemBuilder.withStatus(status);
  }

  private ProblemBuilder setProblemFieldErrors() {
    if (fieldErros == null || fieldErros.size() == 0) {
      return problemBuilder;
    }
    return problemBuilder.with(FIELD_ERRORS_KEY, fieldErros);
  }

}
