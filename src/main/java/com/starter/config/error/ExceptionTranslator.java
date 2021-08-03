package com.starter.config.error;

import com.starter.config.exception.ConstraintViolationException;
import com.starter.config.exception.EntityExistsException;
import com.starter.config.exception.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling {

  @Override
  public ResponseEntity<Problem> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
    BindingResult result = ex.getBindingResult();
    List<FieldErrorVM> fieldErrors =
        result.getFieldErrors().stream()
            .map(f -> new FieldErrorVM(f.getObjectName(), f.getField(), f.getCode()))
            .collect(Collectors.toList());

    AppError error =
        new AppError(
            AppErrorType.CONSTRAINT_VIOLATION_TYPE,
            defaultConstraintViolationStatus(),
            fieldErrors);
    return create(ex, error.buildProblem(), request);
  }

  @Override
  public ResponseEntity<Problem> handleMessageNotReadableException(
      final HttpMessageNotReadableException ex, final NativeWebRequest request) {
    AppError error = new AppError(AppErrorType.BAD_REQUEST_TYPE, Status.BAD_REQUEST);
    return create(ex, error.buildProblem(), request);
  }

  @Override
  public ResponseEntity<Problem> handleNoHandlerFound(
      final NoHandlerFoundException ex, final NativeWebRequest request) {
    AppError error = new AppError(AppErrorType.NOT_FOUND_TYPE, Status.NOT_FOUND);
    return create(ex, error.buildProblem(), request);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Problem> handleEntityNotFoundException(
      EntityNotFoundException ex, NativeWebRequest request) {
    AppError error = new AppError(AppErrorType.ENTITY_NOT_FOUND_EXCEPTION, Status.BAD_REQUEST,
        ex.getMessage());
    return create(ex, error.buildProblem(), request);
  }

  @ExceptionHandler(EntityExistsException.class)
  public ResponseEntity<Problem> handleEntityExistsException(
      EntityExistsException ex, NativeWebRequest request) {
    AppError error = new AppError(AppErrorType.ENTITY_EXIST_EXCEPTION, Status.BAD_REQUEST,
        ex.getMessage());
    return create(ex, error.buildProblem(), request);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Problem> handleConstraintViolationException(
      ConstraintViolationException ex, NativeWebRequest request) {
    AppError error = new AppError(AppErrorType.CONSTRAINT_VIOLATION_TYPE, Status.BAD_REQUEST,
        ex.getMessage());
    return create(ex, error.buildProblem(), request);
  }
}
