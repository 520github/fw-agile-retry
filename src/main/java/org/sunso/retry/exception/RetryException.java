package org.sunso.retry.exception;

public class RetryException extends RuntimeException {
    public RetryException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RetryException(String msg) {
        super(msg);
    }
}
