灵活的java重试框架
======================

#### 前言
* 解决调用三方接口或某些方法，因为网络抖动或超时等原因导致失败，而需要进行重试处理。


#### 概念说明

| 概念                    | 说明        | 接口                                          |
|----------------------- |-----------|---------------------------------------------|
| Retry                 | 重试接口      | org.sunso.retry.Retry                       |
| RetryPolicy           | 重试策略      | org.sunso.retry.policy.RetryPolicy          |
| DelayPolicy           | 延迟策略      | org.sunso.retry.delay.DelayPolicy           |
| ServiceCallback       | 业务执行      | org.sunso.retry.callback.ServiceCallback    |
| FailReturnCallback    | 业务执行失败兜底  | org.sunso.retry.callback.FailReturnCallback |
| RetryContext          | 上下文       | org.sunso.retry.context.RetryContext        |
| DefaultRetryBootstrap | 重试引导入口    | org.sunso.retry.bootstrap.DefaultRetryBootstrap  |


#### Quick Start
###### 例子--默认配置-不做失败兜底处理
~~~
String response = DefaultRetryBootstrap.create()
                    .retry(context -> {  --执行业务
                        String result = context.getRetryCount() + "-success";
                        return result;
                    });
~~~

###### 例子--默认配置-带失败兜底处理
~~~
String response = DefaultRetryBootstrap.create()
                .failReturnCallback(context -> { --重试之后仍然失败返回兜底结果
                    String failReturn = context.getRetryCount() + "-fail";
                    return failReturn;
                })
                .retry(context -> { --执行业务
                    String result = context.getRetryCount() + "-success";
                    return result;
                });
~~~

###### 例子--自定义配置-带失败兜底处理
~~~
String response = DefaultRetryBootstrap.create()
                .delayPolicy(new FixedTimeDelayPolicy().setDelayTime(1000)) --每次等待1000毫秒
                .retryPolicy(new FixedNumRetryPolicy(3)) --重试3次
                .failReturnCallback(context -> {
                    String failReturn = context.getRetryCount() + "-fail";
                    return failReturn;  --重试之后仍然失败返回兜底结果
                })
                .retry(context -> { --执行业务
                    String result = context.getRetryCount() + "-success";
                    return result;
                });
~~~

