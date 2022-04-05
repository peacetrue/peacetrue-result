package com.github.peacetrue.result.exception.user_manual;

import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.ClassifiedResultCode;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * @author peace
 **/
//tag::class[]
public class MissingServletRequestParameterExceptionConverter3
        extends AbstractExceptionConverter<MissingServletRequestParameterException>
        implements ClassifiedResultCode {

    @Override
    public String getSupperCode() {
        return "parameter_missing";
    }

    @Override
    protected Parameter<String, Object> resolveArgs(MissingServletRequestParameterException exception) {
        return new Parameter<>(
                exception.getParameterName(), exception.getParameterType(), null
        );
    }
}
//end::class[]

