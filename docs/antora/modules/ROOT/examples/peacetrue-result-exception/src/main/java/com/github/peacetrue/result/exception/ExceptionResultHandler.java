package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultCustomizer;
import com.github.peacetrue.result.ResultImpl;
import com.github.peacetrue.result.ResultTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 异常响应结果处理器。
 *
 * @see ExceptionResultControllerAdvice
 */
@Slf4j
public class ExceptionResultHandler {

    private ExceptionConvertService exceptionConvertService;
    private ResultCustomizer resultCustomizer = ResultCustomizer.DEFAULT;

    public Object handleException(Throwable throwable) {
        log.warn("handle controller exception", throwable);
        try {
            Result result = exceptionConvertService.convert(throwable);
            return resultCustomizer.customize(result);
        } catch (Exception exception) {
            log.error("handle Result process exception", exception);//兜底
            return new ResultImpl(ResultTypes.FAILURE.getCode(), "The operation failed");
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
