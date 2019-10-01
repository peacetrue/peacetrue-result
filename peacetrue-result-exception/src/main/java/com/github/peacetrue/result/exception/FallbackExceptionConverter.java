package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.DataResultImpl;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * default {@link ExceptionConverter} handle all {@link Exception}
 *
 * @author xiayx
 */
public class FallbackExceptionConverter implements ExceptionConverter<Throwable> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Result convert(Throwable exception) {
        logger.warn("you missed processing the exception", exception);
        return new DataResultImpl<>(ResultType.failure, exception.getMessage());
    }

}
