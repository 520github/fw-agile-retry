package org.sunso.retry.delay;

import org.sunso.retry.context.RetryContext;

public class NoDelayPolicy implements DelayPolicy {
    @Override
    public void delay(RetryContext context) {

    }
}
