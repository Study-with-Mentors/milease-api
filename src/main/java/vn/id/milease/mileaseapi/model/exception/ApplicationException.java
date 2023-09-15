package vn.id.milease.mileaseapi.model.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ApplicationException extends RuntimeException {
    private Object payload;
    private final HttpStatus status;
    private final String errorCode;

    public ApplicationException(String errorCode, HttpStatus status, String message) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public ApplicationException(String errorCode, HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.status = status;
    }
}
