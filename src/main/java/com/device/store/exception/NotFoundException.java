package com.device.store.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super(message);
    }
}
