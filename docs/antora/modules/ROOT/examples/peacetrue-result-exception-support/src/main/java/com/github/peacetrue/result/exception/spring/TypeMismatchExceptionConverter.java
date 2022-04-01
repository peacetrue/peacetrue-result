package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import org.springframework.beans.TypeMismatchException;

/**
 * 类型不匹配异常转换器。
 *
 * @author peace
 */
public class TypeMismatchExceptionConverter extends
        AbstractExceptionConverter<TypeMismatchException> {

    @Override
    protected Parameter<Class<?>, Object> resolveArgs(TypeMismatchException exception) {
        return new Parameter<>(exception.getPropertyName(), exception.getRequiredType(), exception.getValue());
    }

}
