package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultUtils;

/**
 * for {@link ResultException}
 *
 * @author xiayx
 */
public class ResultExceptionConverter implements ExceptionConverter<ResultException> {

    @Override
    public Result convert(ResultException exception) {
        return ResultUtils.wrap(exception);
    }
}
