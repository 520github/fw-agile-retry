package org.sunso.retry.context;

public class NeverRetryContext extends RetryContext {
    private boolean finished;

    public boolean isFinished() {
        return finished;
    }

    public void setFinished() {
        finished = true;
    }
}
