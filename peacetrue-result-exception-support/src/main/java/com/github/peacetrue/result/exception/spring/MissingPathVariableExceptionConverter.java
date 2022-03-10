package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import org.springframework.web.bind.MissingPathVariableException;

/**
 * 缺少路径变量异常转换器。
 *
 * @author peace
 */
public class MissingPathVariableExceptionConverter
        extends AbstractExceptionConverter<MissingPathVariableException> {

    @Override
    protected Parameter<Class<?>, Object> resolveArgs(MissingPathVariableException exception) {
        return new Parameter<>(exception.getVariableName(), exception.getParameter().getParameterType(), null);
    }

}
