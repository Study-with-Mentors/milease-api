package vn.id.milease.mileaseapi.model.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApplicationException {
    public UnauthorizedException() {
        super("UNAUTHORIZED", HttpStatus.UNAUTHORIZED, "There is no current user");
    }
}
