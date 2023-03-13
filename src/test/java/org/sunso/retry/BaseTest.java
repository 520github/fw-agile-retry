package org.sunso.retry;

import org.junit.Assert;
import org.sunso.retry.bootstrap.BizResponse;

public abstract class BaseTest {

    protected void checkSuccessBizResponse(BizResponse bizResponse, Object data) {
        Assert.assertEquals(true, bizResponse.isSuccess());
        Assert.assertEquals(BizResponse.CODE_SUCCESS, bizResponse.getCode());
        Assert.assertEquals(data, bizResponse.getData());
    }

    protected void checkFailBizResponse(BizResponse bizResponse, Object data) {
        Assert.assertEquals(false, bizResponse.isSuccess());
        Assert.assertEquals(BizResponse.CODE_FAIL, bizResponse.getCode());
        Assert.assertEquals(data, bizResponse.getData());
    }

    protected void print(String title, Object value) {
        System.out.println(title + "->" + value);
    }
}
