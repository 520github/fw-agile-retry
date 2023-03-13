package org.sunso.retry.bootstrap;

import org.sunso.retry.callback.ServiceCallback;
import org.sunso.retry.context.RetryContext;

import java.util.HashMap;
import java.util.Map;

public class BizFailAndRetrySuccessServiceCallback implements ServiceCallback<BizResponse, Exception> {
    private Exception defaultException = new Exception();
    Map<Integer, Exception> throwableMap;
    private RetryContext context;
    private int successInTimes;

    public BizFailAndRetrySuccessServiceCallback(int successInTimes) {
        this(successInTimes, getDefaultThrowableMap());
    }

    public BizFailAndRetrySuccessServiceCallback(int successInTimes, Map<Integer, Exception> throwableMap) {
        this.successInTimes = successInTimes;
        this.throwableMap = throwableMap;
    }

    @Override
    public BizResponse doService(RetryContext context) throws Exception {
        this.context = context;
        if (context.getRetryCount() < successInTimes - 1) {
            throw getExceptionByRetryCount(context.getRetryCount());
        }
        return BizResponse.success(null);
    }

    public Throwable getLastThrowable() {
        return context.getLastThrow();
    }

    public Exception getExpectedException() {
        if (getRetryCount() < successInTimes) {
            return getExceptionByRetryCount(getRetryCount() - 1);
        }
        return getExceptionByRetryCount(successInTimes - 1);
    }

    public int getRetryCount() {
        return context.getRetryCount();
    }

    private Exception getExceptionByRetryCount(int retryCount) {
        if (throwableMap.containsKey(retryCount)) {
            return throwableMap.get(retryCount);
        }
        return defaultException;
    }

    public static Map<Integer, Exception> getDefaultThrowableMap() {
        Map<Integer, Exception> throwableMap = new HashMap<>();
        throwableMap.put(1, new NullPointerException());
        throwableMap.put(2, new IllegalArgumentException());
        throwableMap.put(3, new IllegalStateException());
        throwableMap.put(4, new ArrayIndexOutOfBoundsException());
        return throwableMap;
    }
}
