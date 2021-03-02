package com.inyaa.base.bean;

/**
 * @author: yuxh
 * @date: 2021/2/27 11:30
 */
public class BaseResult<R> {

    private boolean success;

    private int code;

    private String errorMessage;

    private R data;

    public static <R> BaseResult<R> success() {
        return new BaseResult<R>().setSuccess(true).setErrorMessag("成功");
    }

    public static <R> BaseResult<R> success(R data) {
        return new BaseResult<R>().setSuccess(true).setCode(200).setErrorMessag("成功").setData(data);
    }

    public static <R> BaseResult<R> error(int code, String msg) {
        BaseResult<R> result = new BaseResult<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setErrorMessag(msg);
        return result;
    }

    public static <R> BaseResult<R> error() {
        BaseResult<R> result = new BaseResult<>();
        result.setSuccess(false);
        result.setCode(-1);
        result.setErrorMessag("失败");
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public BaseResult<R> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public int getCode() {
        return code;
    }

    public BaseResult<R> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public BaseResult<R> setErrorMessag(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public R getData() {
        return data;
    }

    public BaseResult<R> setData(R data) {
        this.data = data;
        return this;
    }

}
