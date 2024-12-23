package com.athosfs.todosimple.exceptions;

import com.athosfs.todosimple.services.exceptions.DataBindingViolationException;
import com.athosfs.todosimple.services.exceptions.ObjectNotFoundException;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j(topic = "GlobalExceptionHandler")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Value("${server.error.include-exception}")
  private boolean printStackTrace;

  @Override
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException methodArgumentNotValidException,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    ErrorResponse errorResponse =
        new ErrorResponse(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            "Erro de validação. Verifique o campo 'errors' para mais detalhes.");
    for (FieldError fieldError :
        methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
      errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return ResponseEntity.unprocessableEntity().body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Object> handleAllUncaughtException(
      Exception exception, WebRequest request) {
    final String errorMessage = "Ocorreu um erro desconhecido";
    log.error(errorMessage, exception);
    return buildErrorResponse(exception, errorMessage, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Object> handleDataIntegrityViolationException(
      DataIntegrityViolationException dataIntegrityViolationException, WebRequest request) {
    String errorMessage = dataIntegrityViolationException.getMostSpecificCause().getMessage();
    log.error(
        "Falha ao salvar entidade com problemas de integridade: " + errorMessage,
        dataIntegrityViolationException);
    return buildErrorResponse(
        dataIntegrityViolationException, errorMessage, HttpStatus.CONFLICT, request);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException constraintViolationException, WebRequest request) {
    log.error("Falha ao validar o elemento", constraintViolationException);
    return buildErrorResponse(
        constraintViolationException, HttpStatus.UNPROCESSABLE_ENTITY, request);
  }

  @ExceptionHandler(ObjectNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> handleObjectNotFoundException(
      ObjectNotFoundException objectNotFoundException, WebRequest request) {
    log.error("Falha ao encontrar o elemento solicitado", objectNotFoundException);
    return buildErrorResponse(objectNotFoundException, HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler(DataBindingViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDataBindingViolationException(
            DataBindingViolationException dataBindingViolationException,
            WebRequest request) {
        log.error("Falha ao salvar entidade com dados associados", dataBindingViolationException);
        return buildErrorResponse(
                dataBindingViolationException,
                HttpStatus.CONFLICT,
                request);
    }

  private ResponseEntity<Object> buildErrorResponse(
      Exception exception, HttpStatus httpStatus, WebRequest request) {
    return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
  }

  private ResponseEntity<Object> buildErrorResponse(
      Exception exception, String message, HttpStatus httpStatus, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
    if (this.printStackTrace) {
      errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
    }
    return ResponseEntity.status(httpStatus).body(errorResponse);
  }
}
