package com.github.peacetrue.result.exception.web;

import com.github.peacetrue.result.ResultType;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.payload.Parameter;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.annotation.Nullable;

/**
 * for {@link MissingServletRequestParameterException}
 *
 * @author xiayx
 */
public class MissingServletRequestParameterExceptionConverter
        extends AbstractExceptionConverter<MissingServletRequestParameterException, Parameter<String, String>> {

    @Override
    protected String resolveCode(MissingServletRequestParameterException exception) {
        return ResultType.parameter_missing.getCode();
    }

    @Override
    protected String resolveMessage(MissingServletRequestParameterException exception) {
        return ResultType.parameter_missing.getMessage();
    }

    @Nullable
    protected Parameter<String, String> resolveData(MissingServletRequestParameterException exception) {
        return new Parameter<>(exception.getParameterName(), exception.getParameterType());
    }

}
