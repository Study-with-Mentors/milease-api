package vn.id.milease.mileaseapi.model.exception;

import org.springframework.http.HttpStatus;
import vn.id.milease.mileaseapi.model.entity.user.User;

public class AccountLockedException extends ApplicationException {
    public static final String ERROR_CODE = ExceptionErrorCodeConstants.ACCOUNT_LOCKED;
    public static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    public AccountLockedException(User user) {
        super(ERROR_CODE, HTTP_STATUS, "Account is locked. Id: " + user.getId());
        super.setPayload(user.getEmail());
    }

    public AccountLockedException(String email, Throwable cause) {
        super(ERROR_CODE, HTTP_STATUS, "Account is locked. Email: " + email, cause);
    }
}
