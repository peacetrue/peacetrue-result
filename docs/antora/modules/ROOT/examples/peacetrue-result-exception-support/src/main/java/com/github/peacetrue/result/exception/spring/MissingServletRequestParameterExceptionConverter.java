package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.ClassifiedResultCode;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * 缺少请求参数异常转换器。
 *
 * @author peace
 */
public class MissingServletRequestParameterExceptionConverter
        extends AbstractExceptionConverter<MissingServletRequestParameterException>
        implements ClassifiedResultCode {

    @Override
    protected Parameter<String, Object> resolveArgs(MissingServletRequestParameterException exception) {
        return new Parameter<>(
                exception.getParameterName(), exception.getParameterType(), null
        );
    }

    @Override
    public String getSupperCode() {
        return ResultTypes.PARAMETER_MISSING.getCode();
    }
}
