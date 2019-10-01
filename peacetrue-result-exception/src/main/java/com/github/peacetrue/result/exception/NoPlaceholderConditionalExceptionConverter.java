package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 无参的异常转换器，使用异常的类名作为异常编码
 *
 * @author xiayx
 */
public class NoPlaceholderConditionalExceptionConverter implements ConditionalExceptionConverter {

    private Map<Class<? extends Throwable>, Result> exceptions;

    public NoPlaceholderConditionalExceptionConverter(Map<Class<? extends Throwable>, Result> exceptions) {
        this.exceptions = new HashMap<>(Objects.requireNonNull(exceptions));
    }

    @Override
    public boolean supports(Throwable exception) {
        return exceptions.keySet().contains(exception.getClass());
    }

    @Override
    public Result convert(Throwable exception) {
        return exceptions.get(exception.getClass());
    }
}
