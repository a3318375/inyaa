package com.yuxh.inyaa.web.system.interceptor;

import com.qftx.base.bean.BaseJsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 缺失请求参数处理
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public BaseJsonResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String message = "缺失请求参数" + e.getParameterName();
        return ackTransfer(message, e);
    }

    /**
     * 请求参数类型错误处理
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public BaseJsonResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String message = "请求参数" + e.getName() + "类型错误";
        return ackTransfer(message, e);
    }

    /**
     * 参数类型错误异常类型处理
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseBody
    public BaseJsonResult handleHttpMessageNotReadableException(HttpMessageConversionException e) {
        String message = "参数类型错误";
        return ackTransfer(message, e);
    }

    /**
     * 空指针异常处理
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public BaseJsonResult handleNullPointerException(NullPointerException e) {
        String message = "空指针异常";
        return ackTransfer(message, e, true);
    }

    /**
     * MethodArgumentNotValidException 异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseJsonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder errorMsg = new StringBuilder();
        BindingResult re = e.getBindingResult();
        for (ObjectError error : re.getAllErrors()) {
            errorMsg.append(error.getDefaultMessage()).append(",");
        }
        errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
        return ackTransfer(errorMsg.toString(), e, false);
    }

    /**
     * 绑定异常处理
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public BaseJsonResult handleBindException(BindException e) {
        BindingResult result = e.getBindingResult();
        StringBuilder errorMsg = new StringBuilder();
        for (ObjectError error : result.getAllErrors()) {
            errorMsg.append(error.getDefaultMessage()).append(",");
        }
        errorMsg.delete(errorMsg.length() - 1, errorMsg.length());
        return ackTransfer(errorMsg.toString(), e, false);
    }

    /**
     * 处理运行时异常
     */
    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public BaseJsonResult handleRuntimeException(RuntimeException e) {
        return ackTransfer(e.getMessage(), e, true);
    }

    /**
     * 默认异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseJsonResult handleException(Exception e) {
        return ackTransfer(e.getMessage(), e, true);
    }

    private BaseJsonResult ackTransfer(String message, Exception e, boolean printStackTrace) {
        BaseJsonResult result = BaseJsonResult.error(message);
        if (printStackTrace) {
            log.error(message, e);
        } else {
            log.error(message);
        }
        return result;
    }

    private BaseJsonResult ackTransfer(String message, Exception e) {
        return ackTransfer(message, e, false);
    }
}