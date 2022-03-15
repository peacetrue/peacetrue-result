package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultImpl;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.builder.ResultMessageBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 兜底的异常转换器，直接返回操作失败。
 *
 * @author peace
 **/
@Slf4j
public class FallbackExceptionConverter implements ExceptionConverter<Throwable> {

    private ResultMessageBuilder resultMessageBuilder;

    @Override
    public Result convert(Throwable throwable) {
        log.warn("Could not find a valid ExceptionConverter", throwable);
        String code = ResultTypes.FAILURE.getCode();
        return new ResultImpl(code, resultMessageBuilder.build(code));
    }

    @Autowired
    public void setResultMessageBuilder(ResultMessageBuilder resultMessageBuilder) {
        this.resultMessageBuilder = resultMessageBuilder;
    }
}
