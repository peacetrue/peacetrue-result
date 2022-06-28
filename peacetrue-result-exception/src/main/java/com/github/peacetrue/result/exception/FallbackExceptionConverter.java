package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.ResultUtils;
import com.github.peacetrue.result.builder.ResultMessageBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * 兜底的异常转换器，直接返回操作失败。
 *
 * @author peace
 **/
@Slf4j
public class FallbackExceptionConverter implements ExceptionConverter<Throwable> {

    private final boolean includeStackTrace = false;
    private ResultMessageBuilder resultMessageBuilder;

    @Override
    public Result convert(Throwable exception) {
        log.debug("fallback convert exception: {}", exception.getClass().getName());
        String code = ResultTypes.FAILURE.getCode();
        Object data = includeStackTrace ? Arrays.toString(exception.getStackTrace()) : null;
        return ResultUtils.build(code, resultMessageBuilder.build(code), data);
    }

    @Autowired
    public void setResultMessageBuilder(ResultMessageBuilder resultMessageBuilder) {
        this.resultMessageBuilder = resultMessageBuilder;
    }
}
