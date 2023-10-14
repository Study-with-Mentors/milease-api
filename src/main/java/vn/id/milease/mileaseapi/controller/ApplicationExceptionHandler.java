package vn.id.milease.mileaseapi.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import vn.id.milease.mileaseapi.model.exception.ApplicationException;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorMessage> applicationUnexpectedException(ApplicationException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(ex.getStatus())
                .body(new ErrorMessage(ex.getErrorCode(), ex.getMessage(), ex.getPayload()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("BAD_REQUEST", ex.getMessage(), ex.getFieldErrors()));

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleAllException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError()
                .body(new ErrorMessage("Unexpected error happens", ex.getMessage()));
    }

    @Getter
    @AllArgsConstructor
    public static class ErrorMessage {
        private String errorCode;
        private String message;
        private Object payload;

        ErrorMessage(String errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }
    }
}
