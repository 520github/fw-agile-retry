package org.sunso.retry;

import org.sunso.retry.callback.FailReturnCallback;
import org.sunso.retry.callback.ServiceCallback;

public interface Retry {
    <R, E extends Throwable> R execute(ServiceCallback<R, E> serviceCallback) throws E;

    <R, E extends Throwable> R execute(ServiceCallback<R, E> serviceCallback, FailReturnCallback failReturnCallback)
            throws E;
}
