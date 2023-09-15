package vn.id.milease.mileaseapi.model.exception;

import org.springframework.http.HttpStatus;

public class ArgumentsException extends ApplicationException{
    public static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE_FORMAT = "%s %s. \n%s";
    public ArgumentsException(Class<?> objClass, ActionConflict actionConflict, String causeMessage, Object... args) {
        super(ExceptionErrorCodeConstants.BAD_REQUEST,
                HTTP_STATUS,
                MESSAGE_FORMAT.formatted(actionConflict.getName(), objClass.getName(), causeMessage));
        this.setPayload(args);
    }

    public ArgumentsException(String errorCode, HttpStatus status, String message, Throwable cause) {
        super(errorCode, status, message, cause);
    }
}
