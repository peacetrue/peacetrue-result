package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.ResultCustomizer;
import com.github.peacetrue.result.ResultImpl;
import com.github.peacetrue.result.ResultTypes;
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
public class ControllerExceptionHandler {

    private ExceptionConvertService exceptionConvertService;
    private ResultCustomizer resultCustomizer = ResultCustomizer.DEFAULT;

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public Object handleException(Throwable throwable) {
        log.warn("handle controller exception", throwable);
        try {
            return resultCustomizer.customize(exceptionConvertService.convert(throwable));
        } catch (Exception exception) {
            log.error("handle ExceptionConvertService exception", exception);//兜底
            return new ResultImpl(ResultTypes.FAILURE.getCode(), "unknown server error");
        }
    }

    @Autowired
    public void setExceptionConvertService(ExceptionConvertService exceptionConvertService) {
        this.exceptionConvertService = exceptionConvertService;
    }

    @Autowired(required = false)
    public void setResultCustomizer(ResultCustomizer resultCustomizer) {
        this.resultCustomizer = resultCustomizer;
    }
}
