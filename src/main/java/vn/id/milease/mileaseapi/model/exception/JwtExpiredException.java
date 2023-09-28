package vn.id.milease.mileaseapi.model.exception;

import org.springframework.http.HttpStatus;

public class JwtExpiredException extends ApplicationException {
    public static final String ERROR_CODE = ExceptionErrorCodeConstants.EXPIRED_JWT;
    public static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public JwtExpiredException(String jwt, Throwable cause) {
        super(ERROR_CODE, HTTP_STATUS, "The JWT has expired. JWT: " + jwt, cause);
    }
}
