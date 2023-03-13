package org.sunso.retry.policy;

import org.sunso.retry.context.RetryContext;

public interface RetryPolicy {

    boolean isCanRetry(RetryContext context);

    RetryContext initRetryContext();

    void registerThrow(RetryContext context, Throwable throwable);
}
