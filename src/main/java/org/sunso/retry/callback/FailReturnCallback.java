package org.sunso.retry.callback;

import org.sunso.retry.context.RetryContext;

public interface FailReturnCallback<R> {
    R failReturn(RetryContext context);
}
