package com.failfeed.service.exception;

public class InvalidFollowOperationException extends RuntimeException {
    public InvalidFollowOperationException(String message) {
        super(message);
    }
}