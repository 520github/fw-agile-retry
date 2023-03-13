package org.sunso.retry.policy;

import org.sunso.retry.context.NeverRetryContext;
import org.sunso.retry.context.RetryContext;

public class NeverRetryPolicy implements RetryPolicy {
    @Override
    public boolean isCanRetry(RetryContext context) {
        return !((NeverRetryContext) context).isFinished();
    }

    @Override
    public RetryContext initRetryContext() {
        return new NeverRetryContext();
    }

    @Override
    public void registerThrow(RetryContext context, Throwable throwable) {
        ((NeverRetryContext) context).setFinished();
        context.registerThrowable(throwable);
    }
}
