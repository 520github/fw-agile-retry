package org.sunso.retry;

import org.sunso.retry.callback.FailReturnCallback;
import org.sunso.retry.callback.ServiceCallback;
import org.sunso.retry.context.RetryContext;
import org.sunso.retry.delay.DelayPolicy;
import org.sunso.retry.exception.RetryException;
import org.sunso.retry.policy.RetryPolicy;

public class DefaultRetry implements Retry {
    private volatile RetryPolicy retryPolicy;
    private volatile DelayPolicy delayPolicy;

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public DefaultRetry setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
        return this;
    }

    public DelayPolicy getDelayPolicy() {
        return delayPolicy;
    }

    public DefaultRetry setDelayPolicy(DelayPolicy delayPolicy) {
        this.delayPolicy = delayPolicy;
        return this;
    }

    @Override
    public <R, E extends Throwable> R execute(ServiceCallback<R, E> serviceCallback) throws E {
        return execute(serviceCallback, null);
    }

    @Override
    public <R, E extends Throwable> R execute(ServiceCallback<R, E> serviceCallback,
            FailReturnCallback failReturnCallback) throws E, RetryException {
        RetryPolicy retryPolicy = this.retryPolicy;
        DelayPolicy delayPolicy = this.delayPolicy;
        RetryContext context = initRetryContext(retryPolicy);
        Throwable lastException = null;
        try {
            while (canRetry(retryPolicy, context)) {
                try {
                    // lastException = null;
                    return serviceCallback.doService(context);
                } catch (Throwable t) {
                    lastException = t;
                    RetryException retryException = handleExceptionCatch(retryPolicy, delayPolicy, context, t);
                    if (retryException != null) {
                        throw retryException;
                    }
                }
            }
            // 最后进行兜底处理
            return handleFailReturn(failReturnCallback, context, lastException);
        } catch (Throwable t) {
            throw DefaultRetry.<E> handleThrow(t);
        }
    }

    private RetryException handleExceptionCatch(RetryPolicy retryPolicy, DelayPolicy delayPolicy, RetryContext context,
            Throwable t) {
        // 注册当前异常
        try {
            registerThrow(retryPolicy, context, t);
        } catch (Exception e) {
            return new RetryException("register throwable fail", e);
        }
        // 可以重试，进行延迟处理
        if (canRetry(retryPolicy, context)) {
            try {
                delayPolicy.delay(context);
            } catch (RetryException e) {
                return e;
            }
        }
        return null;
    }

    private <R> R handleFailReturn(FailReturnCallback failReturnCallback, RetryContext context, Throwable t)
            throws Throwable {
        if (failReturnCallback != null) {
            return (R) failReturnCallback.failReturn(context);
        }
        throw handleThrow(t);
    }

    private static <E extends Throwable> E handleThrow(Throwable t) throws RetryException {
        if (t instanceof Error) {
            throw (Error) t;
        } else if (t instanceof Exception) {
            return (E) t;
        }
        throw new RetryException("retry exception", t);
    }

    private RetryContext initRetryContext(RetryPolicy retryPolicy) {
        return retryPolicy.initRetryContext();
    }

    private boolean canRetry(RetryPolicy retryPolicy, RetryContext context) {
        return retryPolicy.isCanRetry(context);
    }

    private void registerThrow(RetryPolicy retryPolicy, RetryContext context, Throwable t) {
        retryPolicy.registerThrow(context, t);
    }
}
