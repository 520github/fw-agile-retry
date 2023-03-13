package org.sunso.retry.delay;

import org.sunso.retry.context.RetryContext;
import org.sunso.retry.exception.RetryException;

public class FixedTimeDelayPolicy implements DelayPolicy {
    private final static int DEFAULT_DELAY_TIME = 1000;
    private volatile int delayTime = DEFAULT_DELAY_TIME;

    public int getDelayTime() {
        return delayTime;
    }

    public FixedTimeDelayPolicy setDelayTime(int delayTime) {
        this.delayTime = delayTime;
        return this;
    }

    @Override
    public void delay(RetryContext context) throws RetryException {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            throw new RetryException("thread interrupted when sleeping", e);
        }
    }
}
