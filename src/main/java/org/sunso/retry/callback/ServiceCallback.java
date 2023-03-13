package org.sunso.retry.callback;

import org.sunso.retry.context.RetryContext;

public interface ServiceCallback<R, E extends Throwable> {
    R doService(RetryContext context) throws E;
}
