package vn.id.milease.mileaseapi.model.exception;

import org.springframework.http.HttpStatus;

public class InvalidJwtException extends ApplicationException {
    public static final String ERROR_CODE = ExceptionErrorCodeConstants.INVALID_JWT;
    public static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public InvalidJwtException(String jwt, Throwable cause) {
        super(ERROR_CODE, HTTP_STATUS, "The JWT is invalid. JWT: " + jwt, cause);
    }
}
