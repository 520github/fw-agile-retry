package org.sunso.retry.policy;

import org.sunso.retry.context.RetryContext;

public class FixedNumRetryPolicy implements RetryPolicy {
    public final static int DEFAULT_MAX_TRY_NUM = 3;

    private volatile int maxTryNum;

    public FixedNumRetryPolicy() {
        this(DEFAULT_MAX_TRY_NUM);
    }

    public FixedNumRetryPolicy(int maxTryNum) {
        this.maxTryNum = maxTryNum;
    }

    @Override
    public boolean isCanRetry(RetryContext context) {
        Throwable lastThrow = context.getLastThrow();
        return (lastThrow == null || checkRetryException(lastThrow)) && context.getRetryCount() < maxTryNum;
    }

    private boolean checkRetryException(Throwable t) {
        return true;
    }

    @Override
    public RetryContext initRetryContext() {
        return new RetryContext();
    }

    @Override
    public void registerThrow(RetryContext context, Throwable throwable) {
        context.registerThrowable(throwable);
    }
}
