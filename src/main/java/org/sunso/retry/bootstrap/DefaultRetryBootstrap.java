package org.sunso.retry.bootstrap;

import org.sunso.retry.DefaultRetry;
import org.sunso.retry.callback.FailReturnCallback;
import org.sunso.retry.callback.ServiceCallback;
import org.sunso.retry.delay.DelayPolicy;
import org.sunso.retry.delay.NoDelayPolicy;
import org.sunso.retry.policy.FixedNumRetryPolicy;
import org.sunso.retry.policy.RetryPolicy;

public class DefaultRetryBootstrap {
    private final DefaultRetry defaultRetry = new DefaultRetry();
    private FailReturnCallback failReturnCallback;

    public static DefaultRetryBootstrap create() {
        return new DefaultRetryBootstrap();
    }

    public DefaultRetryBootstrap retryPolicy(RetryPolicy retryPolicy) {
        defaultRetry.setRetryPolicy(retryPolicy);
        return this;
    }

    public DefaultRetryBootstrap delayPolicy(DelayPolicy delayPolicy) {
        defaultRetry.setDelayPolicy(delayPolicy);
        return this;
    }

    public DefaultRetryBootstrap failReturnCallback(FailReturnCallback failReturnCallback) {
        this.failReturnCallback = failReturnCallback;
        return this;
    }

    public <R, E extends Throwable> R retry(ServiceCallback<R, E> serviceCallback) throws E {
        if (defaultRetry.getRetryPolicy() == null) {
            defaultRetry.setRetryPolicy(new FixedNumRetryPolicy());
        }
        if (defaultRetry.getDelayPolicy() == null) {
            defaultRetry.setDelayPolicy(new NoDelayPolicy());
        }
        return defaultRetry.execute(serviceCallback, failReturnCallback);
    }
}
