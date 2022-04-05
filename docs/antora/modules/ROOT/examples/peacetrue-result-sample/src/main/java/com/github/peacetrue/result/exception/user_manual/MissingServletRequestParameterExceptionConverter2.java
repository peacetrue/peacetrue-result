package com.github.peacetrue.result.exception.user_manual;

import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * @author peace
 **/
//tag::class[]

public class MissingServletRequestParameterExceptionConverter2
        extends AbstractExceptionConverter<MissingServletRequestParameterException> {

    @Override
    protected Parameter<String, Object> resolveArgs(MissingServletRequestParameterException exception) {
        return new Parameter<>(
                exception.getParameterName(), exception.getParameterType(), null
        );
    }
}
//end::class[]

