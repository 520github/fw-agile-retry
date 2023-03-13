package org.sunso.retry.context;

public class TimeoutRetryContext extends RetryContext {
    private long start;
    private long timeout;

    public TimeoutRetryContext(long timeout) {
        this.timeout = timeout;
        this.start = System.currentTimeMillis();
    }

    public boolean isAlive() {
        return System.currentTimeMillis() - start <= timeout;
    }
}
