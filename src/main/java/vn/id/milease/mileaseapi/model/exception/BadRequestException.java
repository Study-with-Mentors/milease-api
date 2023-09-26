package vn.id.milease.mileaseapi.model.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApplicationException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message) {
        super(HTTP_STATUS.name(), HTTP_STATUS, message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(HTTP_STATUS.name(), HTTP_STATUS, message, cause);
    }
}
