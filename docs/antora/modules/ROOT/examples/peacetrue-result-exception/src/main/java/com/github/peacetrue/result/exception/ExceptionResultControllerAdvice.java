package com.github.peacetrue.result.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制层异常处理器，处理控制层抛出的异常，不处理过滤器层抛出的异常。
 */
@Slf4j
@ControllerAdvice
public class ExceptionResultControllerAdvice {

    private ExceptionResultHandler exceptionResultHandler;

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public Object handleException(Throwable throwable) {
        return exceptionResultHandler.handleException(throwable);
    }

    @Autowired
    public void setExceptionResultHandler(ExceptionResultHandler exceptionResultHandler) {
        this.exceptionResultHandler = exceptionResultHandler;
    }
}
