package com.device.store.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class PreconditionFailedException extends BaseException{
    public PreconditionFailedException (String message) {
        super(message);
    }
}
