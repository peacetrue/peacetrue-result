package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 嵌套的条件异常转换器。
 * 异常本身只是包装异常，不包含有效信息，其有效信息在 {@link Throwable#getCause()} 中。
 *
 * @author peace
 */
@Getter
@Slf4j
public class NestConditionalExceptionConverter implements ConditionalExceptionConverter {

    private final Set<Class<? extends Throwable>> nestClasses;
    private ExceptionConvertService exceptionConvertService;

    public NestConditionalExceptionConverter(Set<Class<? extends Throwable>> nestClasses) {
        this.nestClasses = new LinkedHashSet<>(Objects.requireNonNull(nestClasses));
    }

    @Override
    public boolean supports(Throwable throwable) {
        Throwable cause = throwable.getCause();
        if (cause == null || cause.equals(throwable)) return false;
        return nestClasses.contains(throwable.getClass());
    }

    @Override
    public Result convert(Throwable throwable) {
        log.trace("delegate nest exception '{}' to '{}'", getName(throwable), throwable.getCause().getClass());
        return exceptionConvertService.convert(throwable.getCause());
    }

    private String getName(Throwable throwable) {
        return throwable.getClass().getName();
    }

    @Autowired
    public void setExceptionConvertService(ExceptionConvertService exceptionConvertService) {
        this.exceptionConvertService = exceptionConvertService;
    }
}
