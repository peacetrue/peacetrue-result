package com.github.peacetrue.result.exception.web;

import com.github.peacetrue.result.ExtendResultType;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.payload.Parameter;
import org.springframework.web.bind.MissingPathVariableException;

/**
 * for {@link MissingPathVariableException}
 *
 * @author xiayx
 */
public class MissingPathVariableExceptionConverter
        extends AbstractExceptionConverter<MissingPathVariableException, Parameter<Class, Object>> {

    @Override
    protected String resolveCode(MissingPathVariableException exception) {
        return ExtendResultType.path_variable_missing.getCode();
    }

    @Override
    protected String resolveMessage(MissingPathVariableException exception) {
        return ExtendResultType.path_variable_missing.getMessage();
    }

    protected Parameter<Class, Object> resolveData(MissingPathVariableException exception) {
        return new Parameter<>(exception.getVariableName(), exception.getParameter().getParameterType());
    }

}
