package org.sunso.retry.delay;

import org.sunso.retry.context.RetryContext;
import org.sunso.retry.exception.RetryException;

public interface DelayPolicy {
    void delay(RetryContext context) throws RetryException;
}
