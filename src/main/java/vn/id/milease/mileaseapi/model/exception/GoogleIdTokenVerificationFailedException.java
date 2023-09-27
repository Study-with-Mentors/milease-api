package vn.id.milease.mileaseapi.model.exception;

import org.springframework.http.HttpStatus;

public class GoogleIdTokenVerificationFailedException extends ApplicationException {
    public static final String ERROR_CODE = ExceptionErrorCodeConstants.GOOGLE_ID_TOKEN_VERIFICATION_FAILED;
    public static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;

    public GoogleIdTokenVerificationFailedException(String idToken) {
        super(ERROR_CODE, HTTP_STATUS, "Cannot verify the ID token. ID token: " + idToken);
    }

    public GoogleIdTokenVerificationFailedException(String idToken, Throwable cause) {
        super(ERROR_CODE, HTTP_STATUS, "Cannot verify the ID token. ID token: " + idToken, cause);
    }
}
