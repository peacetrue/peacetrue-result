package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;

/**
 * 方法参数转换不支持异常转换器。
 *
 * @author peace
 */
public class MethodArgumentConversionNotSupportedExceptionConverter
        extends AbstractExceptionConverter<MethodArgumentConversionNotSupportedException> {

    @Override
    protected Parameter<Class<?>, Object> resolveArgs(MethodArgumentConversionNotSupportedException exception) {
        return new Parameter<>(exception.getName(), exception.getRequiredType(), exception.getValue());
    }

}
