package com.github.peacetrue.result.exception.web;

import com.github.peacetrue.result.ResultType;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.payload.Parameter;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;

/**
 * support {@link MethodArgumentConversionNotSupportedException}
 *
 * @author xiayx
 */
public class MethodArgumentConversionNotSupportedExceptionConverter
        extends AbstractExceptionConverter<MethodArgumentConversionNotSupportedException, Parameter<Class, Object>> {

    @Override
    protected String resolveCode(MethodArgumentConversionNotSupportedException exception) {
        return ResultType.parameter_format_error.getCode();
    }

    @Override
    protected String resolveMessage(MethodArgumentConversionNotSupportedException exception) {
        return ResultType.parameter_format_error.getMessage();
    }

    @Override
    protected Parameter<Class, Object> resolveData(MethodArgumentConversionNotSupportedException exception) {
        return new Parameter<>(exception.getName(), exception.getRequiredType(), exception.getValue());
    }

}
