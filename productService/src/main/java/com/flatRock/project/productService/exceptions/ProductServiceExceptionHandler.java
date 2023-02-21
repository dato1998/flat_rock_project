package com.flatRock.project.productService.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.HeuristicCompletionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ProductServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        StringBuilder errorInfo = getErrorInfo(ex.getMessage(), request);
        logger.error(errorInfo, ex);
        return buildResponseEntity(new ProductServiceError(HttpStatus.NOT_FOUND, ex));
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleIncorrectFields(BadCredentialsException ex, HttpServletRequest request) {
        StringBuilder errorInfo = getErrorInfo(ex.getMessage(), request);
        logger.error(errorInfo, ex);
        return buildResponseEntity(new ProductServiceError(HttpStatus.UNAUTHORIZED, ex));
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<Object> handleIOExceptions(IOException ex, HttpServletRequest request) {
        StringBuilder errorInfo = getErrorInfo(ex.getMessage(), request);
        logger.error(errorInfo, ex);
        return buildResponseEntity(new ProductServiceError(HttpStatus.FORBIDDEN, ex));
    }

    @ExceptionHandler(HeuristicCompletionException.class)
    protected ResponseEntity<Object> handleOptimisticLockException(RuntimeException ex, HttpServletRequest request) {
        StringBuilder errorInfo = getErrorInfo(ex.getMessage(), request);
        logger.error(errorInfo, ex);
        return buildResponseEntity(new ProductServiceError(HttpStatus.CONFLICT, ex));
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        StringBuilder errorInfo = getErrorInfo(ex.getMessage(), request);
        logger.error(errorInfo, ex);
        return buildResponseEntity(new ProductServiceError(HttpStatus.CONFLICT, ex));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex, HttpServletRequest request) {
        StringBuilder errorInfo = getErrorInfo(ex.getMessage(), request);
        logger.error(errorInfo, ex);
        return buildResponseEntity(new ProductServiceError(HttpStatus.CONFLICT, ex));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        StringBuilder errorInfo = getErrorInfo(ex.getMessage(), request);
        ProductServiceError productServiceError = new ProductServiceError(HttpStatus.BAD_REQUEST);
        productServiceError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName()));
        productServiceError.setDebugMessage(ex.getMessage());
        logger.error(errorInfo, ex);
        return buildResponseEntity(productServiceError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, HttpServletRequest request) {
        StringBuilder errorInfo = getErrorInfo(ex.getMessage(), request);
        logger.error(errorInfo, ex);
        return new ResponseEntity<Object>("Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        StringBuilder errorInfo = getErrorInfo(ex.getMessage(), servletWebRequest.getRequest());
        String bodyOfResponse = "This should be application specific";
        logger.error(errorInfo, ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    private ResponseEntity<Object> buildResponseEntity(ProductServiceError productServiceError) {
        return new ResponseEntity<>(productServiceError, productServiceError.getStatus());
    }

    private StringBuilder getErrorInfo(String message, HttpServletRequest request) {
        StringBuilder errorInfo = new StringBuilder();
        errorInfo.append("Method: ");
        errorInfo.append(request.getMethod());
        errorInfo.append("; URL: ");
        errorInfo.append(request.getRequestURI());
        errorInfo.append("; Port: ");
        errorInfo.append(request.getLocalPort());
        errorInfo.append("; Arguments: ");
        int count = 0;
        for (String key : request.getParameterMap().keySet()) {
            count++;
            errorInfo.append(key);
            errorInfo.append(": ");
            errorInfo.append(request.getParameter(key));
            if (count != request.getParameterMap().keySet().size()) {
                errorInfo.append(" ");
            }
        }
        errorInfo.append("; Message: ");
        errorInfo.append(message);
        return errorInfo;
    }
}
