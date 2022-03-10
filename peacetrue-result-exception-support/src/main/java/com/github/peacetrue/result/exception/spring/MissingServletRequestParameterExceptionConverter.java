package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * 缺少请求参数异常转换器。
 *
 * @author peace
 */
public class MissingServletRequestParameterExceptionConverter
        extends AbstractExceptionConverter<MissingServletRequestParameterException> {

    @Override
    protected Parameter<String, Object> resolveArgs(MissingServletRequestParameterException exception) {
        return new Parameter<>(
                exception.getParameterName(), exception.getParameterType(), null
        );
    }

}
