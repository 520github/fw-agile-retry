package org.sunso.retry.bootstrap;

import org.junit.Assert;
import org.junit.Test;
import org.sunso.retry.BaseTest;
import org.sunso.retry.delay.FixedTimeDelayPolicy;
import org.sunso.retry.policy.FixedNumRetryPolicy;

public class DefaultRetryBootstrapSuccessTest extends BaseTest {

    @Test
    public void defaultConfigAndFailReturnBizSuccessTest() {
        String response = DefaultRetryBootstrap.create()
                .delayPolicy(new FixedTimeDelayPolicy().setDelayTime(1000))
                .retryPolicy(new FixedNumRetryPolicy(3))
                .failReturnCallback(context -> {
                    String failReturn = context.getRetryCount() + "-fail";
                    return failReturn;
                })
                .retry(context -> {
                    String result = context.getRetryCount() + "-success";
                    return result;
                });
        Assert.assertEquals("0-success", response);
        this.print("response", response);
    }

    @Test
    public void defaultConfigBizSuccessTest() {
        String response = DefaultRetryBootstrap.create()
                .retry(context -> {
                    String result = context.getRetryCount() + "-success";
                    return result;
                });
        Assert.assertEquals("0-success", response);
        this.print("response", response);
    }

    @Test
    public void bizSuccessTest() {
        String successData = "success";
        BizResponse response = DefaultRetryBootstrap.create().failReturnCallback(context -> BizResponse.fail(null))
                .retry(context -> BizResponse.success(successData));
        checkSuccessBizResponse(response, successData);
    }

    @Test
    public void bizFailAndRetrySuccessTest() throws Exception {
        int successInTimes = 5;
        BizFailAndRetrySuccessServiceCallback serviceCallback = new BizFailAndRetrySuccessServiceCallback(
                successInTimes);
        BizResponse response = DefaultRetryBootstrap.create().retryPolicy(new FixedNumRetryPolicy(successInTimes))
                .failReturnCallback(context -> BizResponse.fail(null)).retry(serviceCallback);
        checkSuccessBizResponse(response, null);
        Assert.assertEquals(serviceCallback.getExpectedException(), serviceCallback.getLastThrowable());
        Assert.assertEquals(successInTimes - 1, serviceCallback.getRetryCount());
    }
}
