package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.ResultUtils;
import com.github.peacetrue.result.builder.ResultMessageBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * 兜底的异常转换器，直接返回操作失败。
 *
 * @author peace
 **/
@Slf4j
public class FallbackExceptionConverter implements ExceptionConverter<Throwable> {

    private boolean showStackTrace = true;
    private ResultMessageBuilder resultMessageBuilder;

    @Override
    public Result convert(Throwable throwable) {
        log.warn("could not find a valid ExceptionConverter or ConditionalExceptionConverter", throwable);
        String code = ResultTypes.FAILURE.getCode();
        Object data = showStackTrace ? Arrays.toString(throwable.getStackTrace()) : null;
        return ResultUtils.build(code, resultMessageBuilder.build(code), data);
    }

    @Autowired
    public void setResultMessageBuilder(ResultMessageBuilder resultMessageBuilder) {
        this.resultMessageBuilder = resultMessageBuilder;
    }
}
