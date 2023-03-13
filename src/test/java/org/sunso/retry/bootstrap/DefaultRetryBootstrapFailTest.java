package org.sunso.retry.bootstrap;

import org.junit.Assert;
import org.junit.Test;
import org.sunso.retry.BaseTest;
import org.sunso.retry.callback.ServiceCallback;
import org.sunso.retry.delay.FixedTimeDelayPolicy;
import org.sunso.retry.policy.FixedNumRetryPolicy;

public class DefaultRetryBootstrapFailTest extends BaseTest {
    @Test
    public void bizFailAndReturnFailCallbackTest() throws Exception {
        int successInTimes = 5;
        int maxTryNum = successInTimes - 1;
        Object data = "fail";
        BizResponse failResponse = BizResponse.fail(data);
        BizFailAndRetrySuccessServiceCallback serviceCallback = new BizFailAndRetrySuccessServiceCallback(
                successInTimes);
        BizResponse response = DefaultRetryBootstrap.create().retryPolicy(new FixedNumRetryPolicy(maxTryNum))
                .failReturnCallback(context -> failResponse).retry(serviceCallback);
        checkFailBizResponse(response, data);
        Assert.assertEquals(failResponse, response);
        Assert.assertEquals(serviceCallback.getExpectedException(), serviceCallback.getLastThrowable());
        Assert.assertEquals(maxTryNum, serviceCallback.getRetryCount());
    }

    @Test
    public void bizFailAndFixedTimeDelayPolicyTest() throws Throwable {
        int maxTryNum = 5;
        int delayTime = 1000;
        Object data = "fail";
        BizResponse failResponse = BizResponse.fail(data);
        long start = System.currentTimeMillis();
        BizResponse response = DefaultRetryBootstrap.create()
                .delayPolicy(new FixedTimeDelayPolicy().setDelayTime(delayTime))
                .retryPolicy(new FixedNumRetryPolicy(maxTryNum)).failReturnCallback(context -> failResponse)
                .retry(context -> {
                    int d = 5 / 0;
                    return BizResponse.success(null);
                });
        Assert.assertTrue((System.currentTimeMillis() - start) > (maxTryNum - 1) * delayTime);
        checkFailBizResponse(response, data);

    }
}
