package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 方法参数类型不匹配异常转换器。
 *
 * @author peace
 */
public class MethodArgumentTypeMismatchExceptionConverter extends
        AbstractExceptionConverter<MethodArgumentTypeMismatchException> {

    @Override
    protected Parameter<Class<?>, Object> resolveArgs(MethodArgumentTypeMismatchException exception) {
        return new Parameter<>(exception.getName(), exception.getRequiredType(), exception.getValue());
    }

}
