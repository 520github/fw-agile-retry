package org.sunso.retry.policy;

import org.sunso.retry.context.RetryContext;
import org.sunso.retry.context.TimeoutRetryContext;

public class TimeoutRetryPolicy implements RetryPolicy {

    private static final long DEFAULT_TIMEOUT = 1000;
    private volatile long timeout = DEFAULT_TIMEOUT;

    public long getTimeout() {
        return timeout;
    }

    public TimeoutRetryPolicy setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    @Override
    public boolean isCanRetry(RetryContext context) {
        return ((TimeoutRetryContext) context).isAlive();
    }

    @Override
    public RetryContext initRetryContext() {
        return new TimeoutRetryContext(timeout);
    }

    @Override
    public void registerThrow(RetryContext context, Throwable throwable) {
        context.registerThrowable(throwable);
    }
}
