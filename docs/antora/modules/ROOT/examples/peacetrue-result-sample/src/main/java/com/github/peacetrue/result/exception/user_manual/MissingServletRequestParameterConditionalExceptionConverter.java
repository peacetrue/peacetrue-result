package com.github.peacetrue.result.exception.user_manual;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultImpl;
import com.github.peacetrue.result.exception.ConditionalExceptionConverter;
import org.springframework.web.bind.MissingServletRequestParameterException;

//tag::class[]
public class MissingServletRequestParameterConditionalExceptionConverter
        implements ConditionalExceptionConverter {

    @Override
    public boolean supports(Throwable exception) {
        return exception instanceof MissingServletRequestParameterException;
    }

    @Override
    public Result convert(Throwable exception) {
        MissingServletRequestParameterException localException = (MissingServletRequestParameterException) exception;
        return new ResultImpl(
                "parameter_missing",
                "缺少必须的请求参数'" + localException.getParameterName() + "'"
        );
    }
}
//end::class[]
