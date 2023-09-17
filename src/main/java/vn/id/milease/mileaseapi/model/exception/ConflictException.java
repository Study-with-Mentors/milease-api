package vn.id.milease.mileaseapi.model.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApplicationException {
    public static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;
    private static final String messageFormat = "Error %s %s.\n%s";

    public ConflictException(Class<?> clazz, ActionConflict action, String causeMessage, Object... args) {
        super(ExceptionErrorCodeConstants.CONFLICT,
                HTTP_STATUS,
                messageFormat.formatted(action.getName(), clazz.getSimpleName(), causeMessage));
        this.setPayload(args);
    }

    public ConflictException(Class<?> clazz, ActionConflict action, String causeMessage, Throwable cause, Object... args) {
        super(ExceptionErrorCodeConstants.CONFLICT,
                HTTP_STATUS,
                messageFormat.formatted(action.getName(), clazz.getSimpleName(), causeMessage),
                cause);
        this.setPayload(args);
    }
}
