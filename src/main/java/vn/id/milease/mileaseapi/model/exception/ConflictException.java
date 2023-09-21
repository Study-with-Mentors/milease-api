package vn.id.milease.mileaseapi.model.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApplicationException {
    public static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;
    private static final String MESSAGE_FORMAT = "Error %s %s.\n%s";

    public ConflictException(Class<?> clazz, ActionConflict action, String causeMessage, Object... args) {
        super(ExceptionErrorCodeConstants.CONFLICT,
                HTTP_STATUS,
                MESSAGE_FORMAT.formatted(action.getName(), clazz.getSimpleName(), causeMessage));
        this.setPayload(args);
    }

    public ConflictException(Class<?> clazz, ActionConflict action, String causeMessage, Throwable cause, Object... args) {
        super(ExceptionErrorCodeConstants.CONFLICT,
                HTTP_STATUS,
                MESSAGE_FORMAT.formatted(action.getName(), clazz.getSimpleName(), causeMessage),
                cause);
        this.setPayload(args);
    }

    public ConflictException(String message) {
        super(ExceptionErrorCodeConstants.CONFLICT, HTTP_STATUS, message);
    }
}
