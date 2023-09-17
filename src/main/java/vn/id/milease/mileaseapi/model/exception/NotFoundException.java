package vn.id.milease.mileaseapi.model.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationException{
    public static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException(Class<?> clazz, long id) {
        super(ExceptionErrorCodeConstants.NOT_FOUND,
                HTTP_STATUS,
                String.format("%s not found. Id: %s",
                        clazz.getSimpleName(),
                        id));
        this.setPayload(clazz.getSimpleName() + "#" + id);
    }

    public NotFoundException(Class<?> clazz, int id, Throwable cause) {
        super(ExceptionErrorCodeConstants.NOT_FOUND,
                HTTP_STATUS,
                String.format("%s not found. Id: %s",
                        clazz.getSimpleName(),
                        id),
                cause);
        this.setPayload(clazz.getSimpleName() + "#" + id);
    }
}
