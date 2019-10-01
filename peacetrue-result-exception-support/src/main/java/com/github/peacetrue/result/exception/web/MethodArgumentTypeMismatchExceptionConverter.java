package com.github.peacetrue.result.exception.web;

import com.github.peacetrue.result.ResultType;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.payload.Parameter;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * support {@link MethodArgumentTypeMismatchException}
 *
 * @author xiayx
 */
public class MethodArgumentTypeMismatchExceptionConverter extends
        AbstractExceptionConverter<MethodArgumentTypeMismatchException, Parameter<Class, Object>> {


    @Override
    protected String resolveCode(MethodArgumentTypeMismatchException exception) {
        return ResultType.parameter_format_error.getCode();
    }

    @Override
    protected String resolveMessage(MethodArgumentTypeMismatchException exception) {
        return ResultType.parameter_format_error.getMessage();
    }

    @Override
    protected Parameter<Class, Object> resolveData(MethodArgumentTypeMismatchException exception) {
        return new Parameter<>(exception.getName(), exception.getRequiredType(), exception.getValue());
    }

}
