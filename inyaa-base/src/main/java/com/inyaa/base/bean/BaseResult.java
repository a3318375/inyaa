package com.inyaa.base.bean;

/**
 * @author: yuxh
 * @date: 2021/2/27 11:30
 */
public class BaseResult<R> {

    private boolean status;

    private int code;

    private String msg;

    private R data;

    public static <R> BaseResult<R> success() {
        return new BaseResult<R>().setStatus(true).setMsg("成功");
    }

    public static <R> BaseResult<R> success(R data) {
        return new BaseResult<R>().setStatus(true).setMsg("成功").setData(data);
    }

    public static <R> BaseResult<R> error(int code, String msg) {
        BaseResult<R> result = new BaseResult<>();
        result.setStatus(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static <R> BaseResult<R> error() {
        BaseResult<R> result = new BaseResult<>();
        result.setStatus(false);
        result.setCode(-1);
        result.setMsg("失败");
        return result;
    }

    public boolean isStatus() {
        return status;
    }

    public BaseResult<R> setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public int getCode() {
        return code;
    }

    public BaseResult<R> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BaseResult<R> setMsg(String msg) {
        this.msg = msg;
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
