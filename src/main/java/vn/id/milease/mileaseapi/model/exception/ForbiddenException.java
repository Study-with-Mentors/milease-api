package vn.id.milease.mileaseapi.model.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ApplicationException {
    public static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;
    private static final String MESSAGE_FORMAT = "Error %s %s.\n%s";

    public ForbiddenException(Class<?> clazz, ActionConflict action, String causeMessage, Object... args) {
        super(ExceptionErrorCodeConstants.CONFLICT,
                HTTP_STATUS,
                MESSAGE_FORMAT.formatted(action.getName(), clazz.getSimpleName(), causeMessage));
        this.setPayload(args);
    }

    public ForbiddenException(Class<?> clazz, ActionConflict action, String causeMessage, Throwable cause, Object... args) {
        super(ExceptionErrorCodeConstants.CONFLICT,
                HTTP_STATUS,
                MESSAGE_FORMAT.formatted(action.getName(), clazz.getSimpleName(), causeMessage),
                cause);
        this.setPayload(args);
    }

    public ForbiddenException(Class<?> clazz, Long id, Long userId) {
        super(ExceptionErrorCodeConstants.CONFLICT, HTTP_STATUS, "User %s does not have permission to this data. %s %s".formatted(userId, clazz.getSimpleName(), id));
    }

    public ForbiddenException(String message) {
        super(ExceptionErrorCodeConstants.CONFLICT, HTTP_STATUS, message);
    }
}
