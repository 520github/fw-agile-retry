package org.sunso.retry.bootstrap;

public class BizResponse {
    public static final String CODE_SUCCESS = "1000";
    public static final String CODE_FAIL = "9999";
    private boolean isSuccess;
    private String code;
    private String message;
    private Object data;

    public BizResponse(boolean isSuccess, String code, String message, Object data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static BizResponse success(Object data) {
        return new BizResponse(true, CODE_SUCCESS, "success", data);
    }

    public static BizResponse fail(Object data) {
        return new BizResponse(false, CODE_FAIL, "error", data);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public BizResponse setSuccess(boolean success) {
        isSuccess = success;
        return this;
    }

    public String getCode() {
        return code;
    }

    public BizResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public BizResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public BizResponse setData(Object data) {
        this.data = data;
        return this;
    }
}
