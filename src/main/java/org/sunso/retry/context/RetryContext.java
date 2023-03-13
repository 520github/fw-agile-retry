package org.sunso.retry.context;

public class RetryContext {
    private volatile int retryCount;
    private volatile Throwable lastThrow;

    public int getRetryCount() {
        return retryCount;
    }

    public Throwable getLastThrow() {
        return lastThrow;
    }

    public void registerThrowable(Throwable throwable) {
        this.lastThrow = throwable;
        if (throwable != null) {
            retryCount++;
        }
    }
}
