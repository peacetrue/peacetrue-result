package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.ClassifiedResultCode;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * 缺少路径变量异常转换器。
 *
 * @author peace
 */
public class MissingPathVariableExceptionConverter
        extends AbstractExceptionConverter<MissingPathVariableException>
        implements ClassifiedResultCode {

    @Override
    protected Parameter<Class<?>, Object> resolveArgs(MissingPathVariableException exception) {
        return new Parameter<>(exception.getVariableName(), exception.getParameter().getParameterType(), null);
    }

    @Override
    public String getSupperCode() {
        return ResultTypes.PARAMETER_MISSING.getCode();
    }

}
