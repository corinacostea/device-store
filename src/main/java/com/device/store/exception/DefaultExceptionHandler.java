package com.device.store.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseBody
@ControllerAdvice
@RequiredArgsConstructor
public class DefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponseElement handle(Exception exception) {
        log.error("Exception caught while servicing request", exception);
        return describe(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponseElement handle(NotFoundException exception) {
        log.debug("Exception caught while servicing request", exception);
        return describe(exception, HttpStatus.NOT_FOUND);
    }

    private ErrorResponseElement describe(Exception exception, HttpStatus status) {
        String message = exception.getMessage();
        if (message == null) {
            message = "Internal server error";
        }
        return new ErrorResponseElement(status.value(), message);
    }

    private ErrorResponseElement describe(BaseException exception, HttpStatus status) {
        String message = exception.getMessage();
        if (message == null) {
            message = "Internal server error";
        }
        return new ErrorResponseElement(status.value(), message);
    }

}
