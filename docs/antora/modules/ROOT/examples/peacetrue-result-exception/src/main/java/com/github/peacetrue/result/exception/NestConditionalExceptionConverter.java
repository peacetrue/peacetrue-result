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
public class NestConditionalExceptionConverter implements ConditionalExceptionConverter, NestExceptionRegistry {

    private final Set<Class<? extends Throwable>> exceptionClasses;
    private ExceptionConvertService exceptionConvertService;

    public NestConditionalExceptionConverter(Set<Class<? extends Throwable>> exceptionClasses) {
        this.exceptionClasses = new LinkedHashSet<>(Objects.requireNonNull(exceptionClasses));
    }

    @Override
    public boolean supports(Throwable throwable) {
        Throwable cause = throwable.getCause();
        if (cause == null || cause.equals(throwable)) return false;
        return exceptionClasses.contains(throwable.getClass());
    }

    @Override
    public Result convert(Throwable throwable) {
        log.debug("delegate nest exception: '{}' -> '{}'", getName(throwable), getName(throwable.getCause()));
        return exceptionConvertService.convert(throwable.getCause());
    }

    private String getName(Throwable throwable) {
        return throwable.getClass().getName();
    }

    @Autowired
    public void setExceptionConvertService(ExceptionConvertService exceptionConvertService) {
        this.exceptionConvertService = exceptionConvertService;
    }

    public void registerNestException(Class<? extends Throwable> exceptionClass) {
        exceptionClasses.add(exceptionClass);
    }
}
