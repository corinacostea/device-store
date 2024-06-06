package com.device.store.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponseElement {
    private final int statusCode;
    private final String message;
}
